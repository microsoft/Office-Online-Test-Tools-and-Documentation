
..  _ui customization:

Customizing |wac| using CheckFileInfo properties
================================================

You can customize the user interface and experience of |wac| by using a combination of :ref:`CheckFileInfo` properties
as well as by implementing the :ref:`PostMessage API <postmessage>`.

CheckFileInfo properties
------------------------

CloseUrl
    If provided, when the *Close* UI is activated, |wac| will navigate the outer page (``window.top.location``) to
    the URI provided.

    Hosts can also use the :term:`ClosePostMessage` property to indicate a PostMessage should be sent when
    the *Close* UI is activated rather than navigate to a URL, or set the :term:`CloseButtonClosesWindow`
    property to indicate that the *Close* UI should close the browser tab or window (``window.top.close``).

    If the :term:`CloseUrl`, :term:`ClosePostMessage`, and :term:`CloseButtonClosesWindow` properties are all
    omitted, the *Close* UI will be hidden in |wac|.

    ..  note:: The *Close* UI will never be displayed when using the :wopi:action:`embedview` action.

    .. seealso:: :term:`CloseUrl` in the WOPI REST documentation.

DownloadUrl
    If a DownloadUrl is not provided, |wac| will hide all UI to download the file.

    If provided, Word and PowerPoint Online will display UI to download the file. When a user attempts to download
    the file, Word and PowerPoint will ensure that the latest document content is saved back to the WOPI host before
    navigating the user to the DownloadUrl to download the file.

    ..  admonition:: Excel Online Note

        Excel Online does not use the DownloadUrl when users click the :guilabel:`Download a Copy` button. Excel Online
        always downloads the file directly from the |wac| server. This has the following side effects:

        #.  Any content that Excel Online does not currently support, such as diagrams, are stripped from the
            downloaded file.
        #.  Excel Online does not guarantee that the latest document content is saved back to the WOPI host before
            downloading the file.
        #.  :guilabel:`Download a Copy` contains all the most recent document edits, even when the DownloadUrl is
            implemented incorrectly and does not point to the latest version of the document.

    .. seealso:: :term:`DownloadUrl` in the WOPI REST documentation.

FileSharingUrl
    If provided, when the *Share* UI is activated, |wac| will open a new browser window to the URI provided.

    Hosts can also use the :term:`FileSharingPostMessage` property to indicate a PostMessage should be sent when
    the *Share* UI is activated rather than navigate to a URL.

    If neither the :term:`FileSharingUrl` nor the :term:`FileSharingPostMessage` properties are set, the *Share*
    UI will be hidden in |wac|.

    .. seealso:: :term:`FileSharingUrl` in the WOPI REST documentation.

HostEditUrl
    This URL is used by |wac| to navigate between view and edit mode.

    .. seealso:: :term:`HostEditUrl` in the WOPI REST documentation.

HostViewUrl
    This URL is used by |wac| to navigate between view and edit mode.

    .. seealso:: :term:`HostViewUrl` in the WOPI REST documentation.

SignoutUrl
     If this property is not provided, no sign out UI will be shown in |wac|.

    .. seealso:: :term:`SignoutUrl` in the WOPI REST documentation.

CloseButtonClosesWindow
    If set to ``true``, |wac| will close the browser window or tab (``window.top.close``) when the *Close* UI
    in |wac| is activated.

    If |wac| displays an error dialog when booting, dismissing the dialog is treated as a close button
    activation with respect to this property.

    Hosts can also use the :term:`CloseUrl` property to indicate that the outer frame should be navigated
    (``window.top.location``) when the *Close* UI is activated rather than closing the browser tab or window, or
    set the :term:`ClosePostMessage` property to indicate a PostMessage should be sent when the *Close* UI is
    activated.

    If the :term:`CloseUrl`, :term:`ClosePostMessage`, and :term:`CloseButtonClosesWindow` properties are all
    omitted, the *Close* UI will be hidden in |wac|.

    ..  note:: The *Close* UI will never be displayed when using the :wopi:action:`embedview` action.

    .. seealso:: :term:`CloseButtonClosesWindow` in the WOPI REST documentation.

Breadcrumb properties
    |wac| displays all of the :ref:`breadcrumb properties` if they are provided.



..  _postmessage properties:

PostMessage properties
----------------------

The PostMessage properties control the behavior of |wac| with respect to incoming PostMessages. Note that if
you are using the PostMessage extensibility features of |wac|, you must set the :term:`PostMessageOrigin`
property to ensure that |wac| accepts messages from your outer frame. You can read more about PostMessage
integration at :ref:`PostMessage`.

In cases where a PostMessage is triggered by the user activating some |wac| UI, such as
:term:`FileSharingPostMessage` or :term:`EditModePostMessage`, |wac| will do nothing when the relevant UI is
activated except send the appropriate PostMessage. Thus, hosts must accept and handle the relevant messages when
the Office Online UI is triggered. Otherwise the |wac| UI will appear to do nothing when activated.

If the PostMessage API is not supported (e.g. the user's browser does not support it, or the browser security
settings prohibit it, etc.), |wac| UI that triggers a PostMessage will be hidden.

..  glossary::
    :sorted:

    ClosePostMessage
        A **Boolean** value that, when set to ``true``, indicates the host expects to receive the :js:data:`UI_Close`
        PostMessage when the *Close* UI in |wac| is activated.

        Hosts should use the :term:`CloseUrl` property to indicate that the outer frame should be navigated
        (``window.top.location``) when the *Close* UI is activated rather than sending a PostMessage, or set the
        :term:`CloseButtonClosesWindow` property to indicate that the *Close* UI should close the browser tab or
        window (``window.top.close``).

        If the :term:`CloseUrl`, :term:`ClosePostMessage`, and :term:`CloseButtonClosesWindow` properties are all
        omitted, the *Close* UI will be hidden in |wac|.

        ..  note:: The *Close* UI will never be displayed when using the :wopi:action:`embedview` action.
        
         ..  important::
             The CloseUrl must always be specified if you want the Close UI to appear in Office Online, even if you're setting
             ClosePostMessage to true. This is because there are some cases where Office Online uses the CloseUrl when it can't
             send the UI_Close post message.

    EditModePostMessage
        A **Boolean** value that, when set to ``true``, indicates the host expects to receive the :js:data:`UI_Edit`
        PostMessage when the *Edit* UI in |wac| is activated.

        If this property is not set to ``true``, |wac| will navigate the inner iframe URL to an edit action
        URL when the *Edit* UI is activated.

    EditNotificationPostMessage
        A **Boolean** value that, when set to ``true``, indicates the host expects to receive the
        :js:data:`Edit_Notification` PostMessage.

    FileSharingPostMessage
        A **Boolean** value that, when set to ``true``, indicates the host expects to receive the
        :js:data:`UI_Sharing` PostMessage when the *Share* UI in |wac| is activated.

        Hosts can also use the :term:`FileSharingUrl` property to indicate that a new browser window should be opened
        when the *Share* UI is activated rather than sending a PostMessage. Note that the :term:`FileSharingUrl`
        property will be ignored completely if the FileSharingPostMessage property is set to ``true``.

        If neither the :term:`FileSharingUrl` nor the :term:`FileSharingPostMessage` properties are set, the *Share*
        UI will be hidden in |wac|.

    FileVersionPostMessage
        A **Boolean** value that, when set to ``true``, indicates the host expects to receive the
        :js:data:`UI_FileVersions` PostMessage when the *Previous Versions* UI
        (:menuselection:`File --> Info --> Previous Versions`) in |wac| is activated.

        Hosts can also use the :term:`FileVersionUrl` property to indicate that a new browser window should be opened
        when the *Previous Versions* UI is activated rather than sending a PostMessage. Note that the
        :term:`FileVersionUrl` property will be ignored completely if the FileVersionPostMessage property is set to
        ``true``.

        If neither the :term:`FileVersionUrl` nor the :term:`FileVersionPostMessage` properties are set, the
        *Previous Versions* UI will be hidden in |wac|.

    PostMessageOrigin
        A **string** value indicating the domain the :term:`host page` will be sending/receiving PostMessages
        to/from. |wac| will only send outgoing PostMessages to this domain, and will only listen to
        PostMessages from this domain.

        ..  admonition:: |wac| Tip

            This value will be used as the *targetOrigin* when |wac| uses the
            `HTML5 Web Messaging protocol <http://www.w3.org/TR/webmessaging/>`_. Therefore, it must include the
            scheme and host name. If you are serving your pages on a non-standard port, you must include the port as
            well. The literal string ``*``, while supported in the PostMessage protocol, is not allowed by |wac|.

    WorkflowPostMessage
        |prerelease|

        A **Boolean** value that, when set to ``true``, indicates the host expects to receive the
        :js:data:`UI_Workflow` PostMessage when the *Workflow* UI in |wac| is activated.

        Hosts can also use the :term:`WorkflowUrl` property to indicate that a new browser window should be opened
        when the *Workflow* UI is activated rather than sending a PostMessage. Note that the :term:`WorkflowUrl`
        property will be ignored completely if the WorkflowPostMessage property is set to ``true``.

        If neither the :term:`WorkflowUrl` nor the :term:`WorkflowPostMessage` properties are set, the *Workflow*
        UI will be hidden in |wac|.

        ..  important::
            This value will be ignored if :term:`WorkflowType` is not provided.


.. _viewer customization:

Customizing the Office Online viewer UI using CheckFileInfo
-----------------------------------------------------------

The following table describes all available buttons and UI in the Office Online viewer and what :ref:`CheckFileInfo`
properties can be used to remove them.

===========================  ==========================================================================================
Button                       How to disable
===========================  ==========================================================================================
Edit in Browser              Two options:

                             #. **(preferred)** Set :term:`UserCanWrite` to ``false`` in the CheckFileInfo response (or
                                omit it since the default for all boolean properties in CheckFileInfo is ``false``)
                             #. Omit the :term:`HostEditUrl` and :term:`EditModePostMessage` properties from the
                                CheckFileInfo response
Share                        Omit the :term:`FileSharingUrl` and :term:`FileSharingPostMessage` properties from the
                             CheckFileInfo response
Download / Download as PDF   Omit the :term:`DownloadUrl` property from the CheckFileInfo response
Print                        Set the :term:`DisablePrint` property to ``true`` in the CheckFileInfo response
Exit / Close                 Omit the :term:`CloseUrl` and :term:`ClosePostMessage` properties from the CheckFileInfo
                             response
Comments                     For Word only, set the :term:`UserCanWrite` property to ``false`` in the CheckFileInfo
                             response (or omit it since the default for all boolean properties in CheckFileInfo is
                             ``false``)

                             Can't be hidden in PowerPoint
Find                         Can't be hidden
Translate                    Can't be hidden
Help                         Can't be hidden
Give Feedback                Can't be hidden
Terms of Use                 Can't be hidden
Privacy and Cookies          Can't be hidden
Accessibility Mode           Can't be hidden
Start Slide Show             Can't be hidden
Embed                        Omit the :term:`HostEmbeddedViewUrl` and :term:`HostEmbeddedEditUrl` properties from the
                             CheckFileInfo response
Refresh Selected Connection  Can't be hidden
Refresh All Connections      Can't be hidden
Calculate Workbook           Can't be hidden
Save a Copy                  Set the :term:`UserCanNotWriteRelative` property to ``true`` in the CheckFileInfo response
===========================  ==========================================================================================
