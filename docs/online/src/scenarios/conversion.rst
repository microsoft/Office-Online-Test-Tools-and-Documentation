
.. meta::
    :robots: noindex

..  _binary conversion:
..  _conversion:

Editing binary document formats
===============================

|wac| does not support editing files in binary formats such as ``doc``, ``ppt``, and ``xls``, directly.
However, |wac| can convert documents in those formats to modern formats like ``docx``, ``pptx``, and
``xlsx``, so that users can then edit them in |wac|.

..  important::
    Conversion is almost always a lossless process, and there are typically very few, if any, user-visible changes to
    the layout and formatting of the document during conversion. However, this is not *always* the case, and hosts
    should be aware that users may wish to revert back to the previous binary version of the document after it has
    been converted.

The conversion capability is exposed in WOPI discovery as the :wopi:action:`convert` action. In order to support
editing binary file formats, hosts must support the :ref:`PutRelativeFile` WOPI operation. The conversion process is
started by simply invoking the :wopi:action:`convert` action on the binary file. The high level
process is as follows:

#.  The host invokes the :wopi:action:`convert` action on a binary file.
#.  |wac| will retrieve the file from the host and convert it.
#.  |wac| will send the converted document back to the host by issuing a :ref:`PutRelativeFile` request
    against the original file ID.
#.  Hosts can use the **X-WOPI-FileConversion** header on the :ref:`PutRelativeFile` request to determine that the
    request is being made in the context of a file conversion. Hosts can thus treat these requests differently than
    other PutRelativeFile requests.
#.  After the document is successfully converted, |wac| will redirect the user to the :term:`HostEditUrl`
    returned in the PutRelativeFile response. |wac| always redirects the topmost window
    (``window.top``).


Enabling 'convert and edit' from within the |wac| viewer
--------------------------------------------------------

In the basic conversion flow described above, a user will invoke the :wopi:action:`convert` action using some UI on
the host. However, hosts may wish to open a document first in the |wac| viewer and use |wac|'s
in-application :guilabel:`Edit` button to convert and edit the document, as is done with documents in editable formats.

Hosts can support this in the same way that view -> edit transitions are typically supported. Hosts must do the
following when opening binary documents using the :wopi:action:`view` action:

#.  Set the :term:`UserCanWrite` property to ``true``.
#.  Set the :term:`UserCanNotWriteRelative` property to ``false`` (or simply omit it).
#.  Set the :term:`HostEditUrl` property to a host URL that will invoke the :wopi:action:`convert` action when
    loaded.

Following these steps will ensure that the in-application :guilabel:`Edit` button is displayed. When clicked, this
button will navigate the user to the HostEditUrl provided for the binary file, which will in turn begin the
conversion process and eventually redirect the user to the HostEditUrl for the newly converted document.

Hosts may optionally handle the in-application :guilabel:`Edit` button themselves by setting the
:term:`EditModePostMessage` property to ``true`` and handling the :js:data:`UI_Edit` PostMessage.


Customizing the conversion process
----------------------------------

In the basic conversion process, |wac| will create a new file each time a user attempts to edit a file in a
binary file format. For example, consider this scenario:

#.  A user opens a binary file named :file:`File.doc` in the |wac| viewer.
#.  The user clicks the :guilabel:`Edit` button in the |wac| viewer.
#.  The conversion process is started, and |wac| calls :ref:`PutRelativeFile` on the host, creating a newly
    converted file, :file:`File.docx`.
#.  The user edits the newly converted document, then ends the editing session.
#.  Later, the user returns and opens the original binary file, :file:`File.doc`, in the |wac| viewer.

At this point, the user may be confused as to why the changes made earlier are not in the document. If the user
attempts to edit the file again, |wac| will again convert it and create a *second* converted file, for example
:file:`File1.docx`.

This can be very confusing for users depending on how the user experience within the host UI is designed. Thus, it is
important to consider how to manage user confusion around converted documents. There are three basic customization
options that hosts can employ to help manage this.

First, the host can choose to display some UI to the user prior to beginning the conversion process. Because hosts
ultimately control when the :wopi:action:`convert` action is invoked, a host could choose to display a notification
message when a user attempts to edit a binary document, informing them that the document will be converted. This can
also apply to the in-application :guilabel:`Edit` button by setting the :term:`EditModePostMessage` property to
``true`` and handling the :js:data:`UI_Edit` PostMessage.

Second, the host can choose to handle converted documents in a unique way, by handling the :ref:`PutRelativeFile`
operation differently when called from the conversion flow. The **X-WOPI-FileConversion** header tells hosts when the
operation is being called from the conversion flow, so the host can choose how best to handle those requests.

Finally, the host can control where the user is navigated after conversion is complete. |wac| navigates to the
:term:`HostEditUrl` that is returned in the PutRelativeFile response, which the host controls. Thus, hosts can
customize where the user lands after the conversion is finished. This allows hosts to opt not to send the user
directly to the |wac| editor, but to any URL they wish. For example, a host may redirect the user to an
interstitial page that informs them their document has been converted.

The following are some examples illustrating how these options can be used by hosts to change the user experience
around file conversion. Note that these examples are not meant to be exhaustive, and that hosts may opt to customize
the conversion process and flow in ways not described here.


Example 1
~~~~~~~~~

In the following example, the host helps the user understand the conversion process by naming the converted file such
that it is clear that it was converted from a binary file.

#.  A user selects a binary file in the host UI and chooses to edit it using |wac|.
#.  The conversion process is started, and |wac| calls :ref:`PutRelativeFile` with the converted document
    content.
#.  The host creates a new file as part of the PutRelativeFile request and appends ``(Editable)`` to the name of the
    file.
#.  The user is navigated to a page that allows them to edit the newly converted file in |wac|.


Example 2
~~~~~~~~~

In the following example, the host wishes to hide the conversion process from the user to provide the most
frictionless experience possible.

#.  A user selects a binary file in the host UI and chooses to edit it using |wac|.
#.  The conversion process is started, and |wac| calls :ref:`PutRelativeFile` with the converted document
    content.
#.  Rather than create a new file, the host chooses to add the converted file as a new version to the existing binary
    file.
#.  The user is navigated to a page that allows them to edit the newly converted file in |wac|.
#.  The user can restore the binary version of the file by using the 'version history' features within the host.

..  note::

    This approach may not be feasible for all hosts, depending on how file metadata and versions are handled within
    their system. However, it does offer the following benefits:

    * The user only ever sees a single document both before and after the document is converted.
    * Since there is always only a single document, the user always finds the 'right' document. That is, if the user
      edited the file - which is likely since they invoked the conversion process by attempting to edit a binary
      document - then when they open the file a second time, their previous edits will be there, just as they expect.


Example 3
~~~~~~~~~

In the following example, the host has deemed it important to inform users explicitly about the conversion process
and its possible side effects.

#.  A user selects a binary file in the host UI and chooses to edit it using |wac|.
#.  The host displays a notification message with the following text:

        In order to edit **File.doc**, it must be converted to a modern file format. If the document doesn't look the
        same after it's converted, don't worry - you can always get back to the original file if you need to.

    ..  figure:: ../images/conversion_warning_dialog.*
        :alt: An image that shows a sample notification dialog.

        Example conversion notification message

    The user can cancel the conversion operation or choose to continue with it.
#.  If the user chooses to continue, the host navigates them to a page that invokes the :wopi:action:`convert` action
    on the file.
#.  The conversion process is started, and |wac| calls :ref:`PutRelativeFile` with the converted document
    content.
#.  The host returns a special URL in the :term:`HostEditUrl` property in the PutRelativeFile response. |wac|
    navigates the user to that URL once the conversion is complete.
#.  The user lands on the URL specified by the host, and sees the following message:

        Your file, **File.doc**, has been converted to a new file, **File.docx**. The new file is in a modern file
        format, and the file extension has changed. If you don't need the original file any more, you can delete it.

    ..  figure:: ../images/conversion_completed_dialog.*
        :alt: An image that shows a sample notification dialog.

        Example conversion completed message

    The message includes a button that the user can use to delete the original file immediately if they wish.
#.  Once the user clicks :guilabel:`OK`, they're navigated to a page that invokes the :wopi:action:`edit` action on
    the converted file.

Variant 3.1: Display post-conversion message in the |wac| UI
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

In steps 5 and 6, rather than navigating the user to an interstitial page, the host may choose to append some
parameters to the standard HostEditUrl. Then, when that HostEditUrl is navigated to, the host page can use the
parameters that were added to the URL to determine that the dialog described in step 6 should be displayed. The host
can display that notification above the |wac| editor frame. This is similar to what hosts do when handling the
:js:data:`UI_Sharing` PostMessage.

..  tip::
    Hosts must ensure that they properly use the :js:data:`Blur_Focus` and :js:data:`Grab_Focus` messages when
    drawing UI over the |wac| frame.


