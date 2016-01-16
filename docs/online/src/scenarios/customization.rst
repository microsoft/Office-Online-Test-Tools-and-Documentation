
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

DownloadUrl
    If a DownloadUrl is not provided, |wac| will hide all UI to download the file.

FileSharingUrl
    If provided, when the *Share* UI is activated, |wac| will open a new browser window to the URI provided.

    Hosts can also use the :term:`FileSharingPostMessage` property to indicate a PostMessage should be sent when
    the *Share* UI is activated rather than navigate to a URL.

    If neither the :term:`FileSharingUrl` nor the :term:`FileSharingPostMessage` properties are set, the *Share*
    UI will be hidden in |wac|.

HostEditUrl
    This URL is used by |wac| to navigate between view and edit mode.

HostViewUrl
    This URL is used by |wac| to navigate between view and edit mode.

SignoutUrl
     If this property is not provided, no sign out UI will be shown in |wac|.

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

        Hosts can also use the :term:`CloseUrl` property to indicate that the outer frame should be navigated
        (``window.top.location``) when the *Close* UI is activated rather than sending a PostMessage, or set the
        :term:`CloseButtonClosesWindow` property to indicate that the *Close* UI should close the browser tab or
        window (``window.top.close``).

        If the :term:`CloseUrl`, :term:`ClosePostMessage`, and :term:`CloseButtonClosesWindow` properties are all
        omitted, the *Close* UI will be hidden in |wac|.

        ..  note:: The *Close* UI will never be displayed when using the :wopi:action:`embedview` action.

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

    PostMessageOrigin
        A **string** value indicating the domain the :term:`host page` will be sending/receiving PostMessages
        to/from. |wac| will only send outgoing PostMessages to this domain, and will only listen to
        PostMessages from this domain.

        ..  admonition:: |wac| Tip

            This value will be used as the *targetOrigin* when |wac| uses the
            `HTML5 Web Messaging protocol <http://www.w3.org/TR/webmessaging/>`_. Therefore, it must include the
            scheme and host name. If you are serving your pages on a non-standard port, you must include the port as
            well. The literal string ``*``, while supported in the PostMessage protocol, is not allowed by |wac|.
