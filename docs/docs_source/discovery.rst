
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
included in the **urlsrc** attribute. Note that you must parse the **urlsrc** value and add some parameters. For a full
description of this process, see :ref:`Action URLs`.


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


..  _Action URLs:

Action URLs
---------------

The URI values provided in the **urlsrc** attribute in the discovery XML are not in a valid format. Simply navigating to
them will result in errors. A WOPI host must transform the URIs provided in order to make them valid action URLs that
can be used to invoke actions on a file. There are two key components to transforming the **urlsrc** attribute:

#. Parsing and replacing :term:`placeholder values` with appropriate values, or discarding them completely
#. Appending a *WOPISrc* value to the URI as a query string parameter

After the URL is transformed, it is a valid URL. When the URL is opened, the action will be invoked against the file
indicated by the *WOPISrc* parameter.

Transforming the urlsrc parameter
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Some WOPI actions expose parameters that hosts can use to customize the behavior of the Office Online application. For
example, most actions support optional query string parameters that tell Office Online what language to render the
application UI in.

These parameters are exposed in the **urlsrc** attribute in the discovery XML. Each of these optional parameters are
contained within angle brackets (``<`` and ``>``), and conform to the pattern ``<name=PLACEHOLDER_VALUE[&]>``, where
``name`` is the name of the query string parameter and ``PLACEHOLDER_VALUE`` is a value that can be replaced by the
host. By convention all placeholder values in Office Online action URIs are capitalized.

The list of all placeholder values used by Office Online and what values are valid replacements for each placeholder are
listed in the :ref:`Placeholder values` section.

The placeholders are replaced as follows:

* If the ``PLACEHOLDER_VALUE`` is unknown to the host, the entire parameter, including the angle brackets, is removed.
* Similarly, if the ``PLACEHOLDER_VALUE`` is known but the host wishes to ignore it or use the default value for that
  parameter, the entire parameter, including the angle brackets, should be removed.
* If the ``PLACEHOLDER_VALUE`` is known, the angle brackets are removed, the ``name`` value is left intact, and the
  ``PLACEHOLDER_VALUE`` string is replaced with an appropriate value. If present, the optional ``&`` must be preserved.

The following section contains a list of all current placeholder values that Office Online exposes in its discovery XML.
Note that Office Online may add new placeholders and actions at any time; hosts must ignore - and thus remove from the
URL per the instructions above - any placeholder values they don't explicitly understand.

..  _Placeholder values:

Placeholder values
^^^^^^^^^^^^^^^^^^

..  glossary::
    :sorted:

    UI_LLCC
        This value represents the language the Office Online application UI should use. Any language can be supplied
        provided it is in the format described in :rfc:`1766`. Note that Office Online does not support all
        languages, and may use a substitute language if the language requested is not supported. If no value is
        provided for this placeholder, Office Online will try to use the browser language setting
        (`navigator.language`). If no valid lanuage can be determined Office Online will default to English.

    DC_LLCC
        This value represents the language that Office Online should use for the purposes of data calculation. Any
        language can be supplied provided it is in the format described in :rfc:`1766`. Typically this value
        should be the same as the value provided for :term:`UI_LLCC`

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

    FULLSCREEN
        ..  note:: This value is used in :term:`broadcast` related actions only.

        This value can be set to ``true`` to load the file type in full-screen mode.

    RECORDING
        ..  note:: This value is used in :term:`broadcast` related actions only.

        This value can be set to ``true`` to load the file type with a minimal user interface.

    THEME_ID
        ..  note:: This value is used in :term:`broadcast` related actions only.

        This value can be set to either ``1`` or ``2`` to designate the a specific user interface appearance.
        ``1`` denotes a light-colored theme and ``2`` denotes a darker colored theme.


Appending a WOPISrc value
~~~~~~~~~~~~~~~~~~~~~~~~~

After parsing and replacing any placeholder values in the **urlsrc** parameter, hosts must add a ``WOPISrc`` query
string parameter to the URL. Once this is done, the URL is a valid action URL and, when loaded by a browser, will
instantiate an Office Online application.

The ```WOPISrc`` parameter tells Office Online the URL of the host's WOPI :ref:`Files endpoint`. In other words,
it is a URL of the form ``http://server/<...>/wopi*/files/(id)``, where ``id`` is the :term:`file id` of the file. The
``WOPISrc`` parameter value must be encoded to a URL-safe string, then the parameter is appended to the action URL.

..  tip::

    It may be useful to think of the ``WOPISrc`` parameter as the :ref:`CheckFileInfo` URL for the file. The
    only difference between the ``WOPISrc`` parameter and the :ref:`CheckFileInfo` URL is that the ``WOPISrc``
    parameter does not include an :term:`access token`.


Additional notes
~~~~~~~~~~~~~~~~

Depending on the specific scenario where action URLs are invoked, there are additional relevant components to action
URLs. Since action URLs are typically invoked from the host frame, these are covered in the
:ref:`Host Frame` section.
