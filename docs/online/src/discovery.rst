
.. meta::
    :robots: noindex

..  default-domain:: wopi

..  _WOPI discovery:
..  _Discovery:

WOPI discovery
==============

WOPI discovery is the process by which a WOPI host identifies |wac| capabilities and how to initialize
|wac| applications within a site. WOPI hosts use the discovery XML to determine how to interact with
|wac|. The WOPI host should cache the data in the discovery XML. Although this XML does not change often, we
recommend that you issue a request for the XML periodically to ensure that you always have the most up-to-date
version. 12-24 hours is a good cadence to refresh although in practice it is updated much less frequently.

Another more dynamic option is to re-run discovery when :ref:`proof key validation <proof keys>` fails, or when it
succeeds using the old key. That implies that the keys have been rotated, so discovery should definitely be re-run to
obtain the new public key.

Finally, another option is to run discovery whenever one of your machines restarts. All of these approaches, as well
as combinations of them, have been used by hosts in the past; which approach makes the most sense depends on your
infrastructure.

..  important::
    Hosts should not rely on the :http:header:`Expires` HTTP header on the WOPI discovery URL in order to know when
    to re-run WOPI discovery. While this may change in the future, currently the value in the :http:header:`Expires`
    header is not appropriate for this purpose.

..  tip::
    See :ref:`discovery URLs` for URLs you should use to retrieve discovery XML for the |wac| test and
    production environments.


WOPI discovery actions
----------------------

The **action** element and its attributes in the discovery XML provides some important information about |wac|.

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

The following example shows an **action** element in the |wac| discovery XML:

..  code-block:: xml

    <action name="edit" ext="docx" requires="locks,update"
            urlsrc="https://word-edit.officeapps.live.com/we/wordeditorframe.aspx?
            <ui=UI_LLCC&><rs=DC_LLCC&><showpagestats=PERFSTATS&>"/>

This example defines an action called :wopi:action:`edit` that is supported for ``docx`` files. The edit action requires
the :wopi:req:`locks` and :wopi:req:`update` capabilities. To invoke the edit action on a file, you navigate to the URI
included in the **urlsrc** attribute. Note that you must parse the **urlsrc** value and add some parameters. For a full
description of this process, see :ref:`Action URLs`.

Note that some actions are not supported within the |cspp|. These operations are marked |need_permission|.


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
    file for editing in |wac|.

    :requires: :req:`update`, :req:`locks`


..  action:: convert

    An action that converts a document in a binary format, such as ``doc``, into a modern format, like ``docx``, so
    that it can be edited in |wac|. See :ref:`conversion` for more information about this action.

    :requires: :req:`update`, :req:`locks`


..  action:: getinfo

    An action that returns a set of URLs that can be used to execute automated test cases. This action is only used
    by the :ref:`validator` and is meant to be used in an :ref:`automated fashion <automated validation>`.


..  action:: interactivepreview

    |need_permission|

    An action that provides an interactive preview of the file type.


..  action:: mobileView

    An action that renders a non-editable view of a document that is optimized for viewing on mobile devices such as
    smartphones.

    ..  tip::

        |wac| automatically redirects :action:`view` to :action:`mobileView` when needed, so typically hosts
        do not need to use this action directly.


..  action:: embedview

    An action that renders a non-editable view of a document that is optimized for embedding in a web page.


..  action:: imagepreview

    |need_permission|

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

    |need_permission|

    An action that presents a :term:`broadcast` of a document.


..  action:: presentservice

    |need_permission|

    This action provides the location of a :term:`broadcast` endpoint for broadcast presenters. Interaction with the
    endpoint is described in `\[MS-OBPRS\] <https://msdn.microsoft.com/en-us/library/hh623172(v=office.12).aspx>`_.


..  action:: attend

    |need_permission|

    An action that attends a :term:`broadcast` of a document.


..  action:: attendservice

    |need_permission|

    This action provides the location of a :term:`broadcast` endpoint for broadcast attendees. Interaction with the
    endpoint is described in `\[MS-OBPAS\] <https://msdn.microsoft.com/en-us/library/hh642267(v=office.12).aspx>`_.

..  action:: preloadedit

    An action used to :ref:`preload static content<Preloading static content>` for |wac| edit applications.


..  action:: preloadview

    An action used to :ref:`preload static content<Preloading static content>` for |wac| view applications.


..  action:: syndicate

    |need_permission|

    ..  todo:: :issue:`7`


..  action:: legacywebservice

    |need_permission|

    ..  todo:: :issue:`7`


..  action:: rtc

    |need_permission|

    ..  todo:: :issue:`7`


..  action:: collab

    |need_permission|

    ..  todo:: :issue:`7`


..  action:: documentchat

    |need_permission|

    ..  todo:: :issue:`7`


..  _Action requirements:

Action requirements
-------------------

The WOPI protocol exposes a number of different REST endpoints and operations that you can perform via those endpoints.
You don't have to implement all of these for all actions. Actions define their requirements as part of the discovery
XML. The requirements themselves are groups of WOPI operations that must be supported in order for the action to work.

..  req:: update

    :requires: :ref:`PutFile`, :ref:`PutRelativeFile`

..  req:: locks

    :requires: :ref:`Lock`, :ref:`RefreshLock`, :ref:`Unlock`, :ref:`UnlockAndRelock`

..  req:: cobalt

    ..  include:: /_fragments/deprecated_discovery_requirement.rst

    :requires: :ref:`ExecuteCellStorageRequest`, :ref:`ExecuteCellStorageRelativeRequest`

..  req:: containers

    ..  include:: /_fragments/deprecated_discovery_requirement.rst

    :requires: :ref:`CheckFolderInfo`, :ref:`DeleteFile`, :ref:`EnumerateChildren (folders)`


..  _Action URLs:

Action URLs
-----------

The URI values provided in the **urlsrc** attribute in the discovery XML are not in a valid format. Simply navigating to
them will result in errors. A WOPI host must transform the URIs provided in order to make them valid action URLs that
can be used to invoke actions on a file. To transform the **urlsrc** attribute into a proper action URL, the host
must parse and replace :ref:`placeholder values` with appropriate values or discard them completely.

After the URL is transformed, it is a valid URL. When the URL is opened, the action will be invoked against the file
indicated by the :term:`wopirest:WOPISrc`.

Transforming the urlsrc parameter
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Some WOPI actions expose parameters that hosts can use to customize the behavior of the |wac| application. For
example, most actions support optional query string parameters that tell |wac| what language to render the
application UI in.

These parameters are exposed in the **urlsrc** attribute in the discovery XML. Each of these optional parameters are
contained within angle brackets (``<`` and ``>``), and conform to the pattern ``<name=PLACEHOLDER_VALUE[&]>``, where
``name`` is the name of the query string parameter and ``PLACEHOLDER_VALUE`` is a value that can be replaced by the
host. By convention all placeholder values in |wac| action URIs are capitalized.

The list of all placeholder values used by |wac| and what values are valid replacements for each placeholder are
listed in the :ref:`Placeholder values` section.

The placeholders are replaced as follows:

* If the ``PLACEHOLDER_VALUE`` is unknown to the host, the entire parameter, including the angle brackets, is removed.
* Similarly, if the ``PLACEHOLDER_VALUE`` is known but the host wishes to ignore it or use the default value for that
  parameter, the entire parameter, including the angle brackets, should be removed.
* If the ``PLACEHOLDER_VALUE`` is known, the angle brackets are removed, the ``name`` value is left intact, and the
  ``PLACEHOLDER_VALUE`` string is replaced with an appropriate value. If present, the optional ``&`` must be preserved.

The following section contains a list of all current placeholder values that |wac| exposes in its discovery XML.
Note that |wac| may add new placeholders and actions at any time; hosts must ignore - and thus remove from the
URL per the instructions above - any placeholder values they don't explicitly understand.

..  _Placeholder values:

Placeholder values
^^^^^^^^^^^^^^^^^^

..  glossary::
    :sorted:

    UI_LLCC
        This value represents the language the |wac| application UI should use. Note that |wac| does
        not support all languages, and may use a substitute language if the language requested is not supported. For
        a list of currently supported languages, see :ref:`languages`

        In addition to the values provided in the Locale ID column, any language can be supplied provided it is in
        the format described in :rfc:`1766`. If no value is provided for this placeholder, |wac| will try to
        use the browser language setting (``navigator.language``). If no valid language can be determined |wac|
        will default to ``en-US`` (US English).

    DC_LLCC
        This value represents the language that |wac| should use for the purposes of data calculation. For
        a list of currently supported languages, see :ref:`languages`

        In addition to the values provided in the Locale ID column, any language can be supplied provided it is in
        the format described in :rfc:`1766`. Typically this value should be the same as the value provided for
        :term:`UI_LLCC`, and will default to that value if not provided.

    EMBEDDED
        ..  note:: This value is used in :term:`broadcast` related actions only.

        This value can be set to ``true`` to indicate that the output of the action will be embedded in a web page.

    DISABLE_ASYNC
        ..  note:: This value is used in the :wopi:action:`attend` action only.

        This value can be set to ``true`` to prevent a :term:`broadcast` attendee from navigating a file independently.

    DISABLE_BROADCAST
        ..  note:: This value is used in :term:`broadcast` related actions only.

        This value can be set to ``true`` to load a view of a document that does not start or join a :term:`broadcast`
        session. This view looks and behaves like a regular broadcast frame.

    DISABLE_CHAT
        This value can be set to ``1`` to disable in-document chat functionality.

    FULLSCREEN
        ..  note:: This value is used in :term:`broadcast` related actions only.

        This value can be set to ``true`` to load the file type in full-screen mode.

    HOST_SESSION_ID
        This value can be passed by hosts to associate an |wac| session with a host session identifier. This can help
        |wac| engineers more quickly find logs for troubleshooting purposes based on a host-specific session
        identifier.

    RECORDING
        ..  note:: This value is used in :term:`broadcast` related actions only.

        This value can be set to ``true`` to load the file type with a minimal user interface.

    THEME_ID
        ..  note:: This value is used in :term:`broadcast` related actions only.

        This value can be set to either ``1`` or ``2`` to designate the a specific user interface appearance.
        ``1`` denotes a light-colored theme and ``2`` denotes a darker colored theme.

    PERFSTATS
        ..  include:: ../../_shared/stub.rst

        ..  |issue| issue:: 52

    BUSINESS_USER
        This value can be set to ``1`` to indicate that the current user is a business user. This placeholder value
        must be used by hosts that support the business user flow. See :ref:`Business editing` for more information.

    VALIDATOR_TEST_CATEGORY
        ..  note:: This value is used to run the :ref:`validator` in different modes.

        This value can be set to ``All``, ``OfficeOnline`` or ``OfficeNativeClient`` to activate tests specific to
        |wac| and |Office iOS|. If omitted, the default value is ``All``.

        * ``All``: activates all :ref:`validator` tests.
        * ``OfficeOnline``: activates all tests necessary for |wac| integration.
        * ``OfficeNativeClient``: activates all tests necessary for |Office iOS| integration.

    SESSION_CONTEXT
        This placeholder can be replaced by any string value. If provided, this value will be passed back to
        the host in subsequent :ref:`CheckFileInfo` and :ref:`CheckFolderInfo` calls in the **X-WOPI-SessionContext**
        request header. There is no defined limit for the length of this string; however, since it is passed on the
        query string, it is subject to the overall |wac| URL length limit of 2048 bytes.

        ..  versionadded:: 2018.12.15
            Prior to this version, session context was supported but hosts were required to add it to the action URL
            manually using the ``sc`` query parameter. This placeholder enables hosts to handle session context in the
            same way as other URL parameters.

    WOPI_SOURCE
        This placeholder **must** be replaced by a :term:`wopirest:WopiSrc` value.

        ..  important::
            Unlike other placeholders, replacing this placeholder is required.

        ..  versionadded:: 2018.12.15
            Prior to this version, hosts were required to add the :term:`wopirest:WopiSrc` to the action URL for most
            (but not all) actions. This placeholder enables hosts to handle the :term:`wopirest:WopiSrc` in the same
            way as other URL parameters.


Additional notes
~~~~~~~~~~~~~~~~

Depending on the specific scenario where action URLs are invoked, there are additional relevant components to action
URLs. Since action URLs are typically invoked from the host page, these are covered in the
:ref:`Host page` section.


..  _favicons:

Favicon URLs
------------

The discovery XML includes a URL to an appropriate `favicon <https://en.wikipedia.org/wiki/Favicon>`_ for all |wac|
applications in the ``favIconUrl`` attribute of the ``app`` element. For example:

..  code-block:: xml
    :emphasize-lines: 2

    <app name="Excel"
         favIconUrl="https://excel.officeapps.live.com/x/_layouts/resources/FavIcon_Excel.ico"
         checkLicense="true">
        ...
    </app>

Hosts should use this URL as the favicon for their host page, so that the appropriate application icon is shown when
|wac| is used.
