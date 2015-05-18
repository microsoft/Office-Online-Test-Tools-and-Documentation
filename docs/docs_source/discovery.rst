
..  default-domain:: wopi

..  _Discovery:

WOPI discovery
==============

WOPI discovery is the process by which your application identifies Office Online capabilities, and how to initialize
Office Online applications within your site. To retrieve the discovery XML for the live service, use the
following URL: https://onenote.officeapps.live.com/hosting/discovery.

WOPI hosts use the discovery XML to determine how to interact with Office Online. The WOPI host should cache the
data in the discovery XML. Although this XML does not change often, we recommend that you issue a request for the
XML periodically to ensure that you always have the most up-to-date version.

WOPI discovery actions
----------------------

The **action** element and its attributes in the discovery XML provides some important information about Office Online.

+------------------------+-----------------------------------------------------------------------------------+
| Element or attribute   |  Description                                                                      |
+========================+===================================================================================+
| **action** element     | Represents:                                                                       |
|                        |                                                                                   |
|                        | * Operations that you can perform on an Office document.                          |
|                        | * The file formats (in the form of file extensions) that are supported for        |
|                        |   the action.                                                                     |
+------------------------+-----------------------------------------------------------------------------------+
| **requires** attribute | The WOPI REST endpoints that are required to use the actions.                     |
+------------------------+-----------------------------------------------------------------------------------+
| **urlsrc** attribute   | The URI that you navigate to in order to invoke the action on a particular file.  |
+------------------------+-----------------------------------------------------------------------------------+

The following example shows an **action** element in the Office Online discovery XML:

..  code-block:: xml

    <action name="edit" ext="docx" requires="locks,update"
            urlsrc="https://word-edit.officeapps.live.com/we/wordeditorframe.aspx?
            <ui=UI_LLCC&><rs=DC_LLCC&><showpagestats=PERFSTATS&>"/>

This example defines an action called :wopi:action:`edit` that is supported for .docx files. The edit action requires
the :wopi:req:`locks` and :wopi:req:`update` capabilities. To invoke the edit action on a file, you navigate to the URI
included in the **urlsrc** attribute. Note that you must parse the urlsrc value and add some parameters. For more
information, see :ref:`Invocation URIs`.


.. _WOPI Actions:

WOPI actions
------------

..  todo:: :issue:`9`

    Provide some detail about what actions are and how to choose the right one for a given purpose.

..  note:: All WOPI actions require hosts implement :ref:`CheckFileInfo` and :ref:`GetFile`.


..  action:: view

    An action that renders a non-editable view of a document.


..  action:: edit

    An action that allows users to edit a document.

    :requires: :req:`update`, :req:`locks`


..  action:: editnew

    An action that creates a new document using a blank file template appropriate to the file type, then opens that
    file for editing in Office Online.

    :requires: :req:`update`, :req:`locks`


..  action:: interactivepreview

    An action that provides an interactive preview of the file type.


..  action:: mobileView

    An action that renders a non-editable view of a document that is optimized for viewing on mobile devices such as
    smartphones.

    ..  tip::

        Office Online automatically redirects :action:`view` to :action:`mobileview` when needed, so typically hosts
        do not need to use this action directly.


..  action:: embedview

    An action that renders a non-editable view of a document that is optimized for embedding in a web page.


..  action:: imagepreview

    An action that provides a static image preview of the file type.


..  action:: formsubmit

    An action that supports accepting changes to the file type via a form-style interface. For example, a user might
    be able to use this action to change the content of a workbook even if they did not have permission to use the
    :action:`edit` action.


..  action:: formedit

    An action that supports editing the file type in a mode better suited to working with files that have been used
    to collect form data via the :action:`formsubmit` action.


..  action:: rest

    An action that supports interacting with the file type via additional URL parameters that are specific to the
    file type in question.


..  action:: present

    An action that presents a :term:`broadcast` of a document.


..  action:: presentservice

    This action provides the location of a :term:`broadcast` endpoint for broadcast presenters. Interaction with the
    endpoint is described in `\[MS-OBPRS\] <https://msdn.microsoft.com/en-us/library/hh623172(v=office.12).aspx>`_.


..  action:: attend

    An action that attends a :term:`broadcast` of a document.


..  action:: attendservice

    This action provides the location of a :term:`broadcast` endpoint for broadcast attendees. Interaction with the
    endpoint is described in `\[MS-OBPAS\] <https://msdn.microsoft.com/en-us/library/hh642267(v=office.12).aspx>`_.

..  action:: syndicate

    ..  todo:: :issue:`7`


..  action:: legacywebservice

    ..  todo:: :issue:`7`


..  action:: rtc

    ..  todo:: :issue:`7`


..  action:: preloadedit

    An action used to :ref:`preload static content<Preloading static content>` for Office Online edit applications.


..  action:: preloadview

    An action used to :ref:`preload static content<Preloading static content>` for Office Online view applications.

..  _Action requirements:

Action requirements
-------------------

The WOPI protocol exposes a number of different REST endpoints and operations that you can perform via those endpoints.
You don’t have to implement all of these for all actions. Actions define their requirements as part of the discovery
XML. The requirements themselves are groups of WOPI operations that must be supported in order for the action to work.

..  req:: update

    :requires: :ref:`PutFile`, :ref:`PutRelativeFile`

..  req:: locks

    :requires: :ref:`Lock`, :ref:`RefreshLock`, :ref:`Unlock`, :ref:`UnlockAndRelock`

..  req:: cobalt

    ..  include:: /fragments/deprecated_discovery_requirement.rst

    :requires: :ref:`ExecuteCellStorageRequest`, :ref:`ExecuteCellStorageRelativeRequest`

..  req:: containers

    ..  include:: /fragments/deprecated_discovery_requirement.rst

    :requires: :ref:`CheckFolderInfo`, :ref:`DeleteFile`, :ref:`EnumerateChildren`


..  _Invocation URIs:

Invocation URIs
---------------

You need to transform the URI in the **urlsrc** attribute; otherwise, it is invalid. This involves the following:

* Adding an additional query string parameter, *WOPISrc*, which describes where to find the file on the service that you
  are invoking the action on.
* Passing an *access_token* parameter, either on the query string, or in an HTTP POST.

..  important::
    We recommend that you use an HTTP POST, for security reasons. Do not pass access tokens in query strings in a
    production environment. See :ref:`Passing access tokens securely` for more information.

In addition, the **urlsrc** value might have placeholder values, contained within angle brackets (``<`` and ``>``),
that represent optional query string parameters that can be set on the action URI. The placeholders are replaced as
follows:

* If the PLACEHOLDER_VALUE is unknown, the entire parameter, including the angle brackets, are removed.
* If the PLACEHOLDER_VALUE is known, the angle brackets are removed, and the PLACEHOLDER_VALUE is replaced with an
  appropriate value.

After the URL is transformed, it is a valid URL. When the URL is opened, the action will be invoked against the file
indicated by the WOPISrc parameter.

|stub-icon| Placeholder values
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

..  include:: /fragments/stub.rst

.. |issue| issue:: 10

