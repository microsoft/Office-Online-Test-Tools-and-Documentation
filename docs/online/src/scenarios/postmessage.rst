
.. meta::
    :robots: noindex

..  _PostMessage:

Using PostMessage to interact with the |wac| application iframe
===============================================================

..  default-domain:: js

You can integrate your own UI into |wac| applications. This way, you can use your UI for actions on Office
documents, such as sharing.

To integrate with |wac| in this way, implement the
`HTML5 Web Messaging protocol <http://www.w3.org/TR/webmessaging/>`_. The Web Messaging protocol,
also known as PostMessage, allows the |wac| frame to communicate with its parent :term:`host page`, and
vice-versa. The following example shows the general syntax for PostMessage. In this example, ``otherWindow`` is a
reference to another window that ``msg`` will be posted to.

..  function:: otherWindow.postMessage(msg, targetOrigin)

    :param string msg: A string (or JSON object) that contains the message
        data.
    :param string targetOrigin: Specifies what the origin of ``otherWindow`` must be for the event to be dispatched.
        This value will be set to the :term:`PostMessageOrigin` property provided in :ref:`CheckFileInfo`. The literal
        string ``*``, while supported in the PostMessage protocol, is not allowed by |wac|.

Message format
--------------

All messages posted to and from the |wac| application frame are posted using the
:func:`~otherWindow.postMessage` function. Each message (the ``msg`` parameter in the
:func:`~otherWindow.postMessage` function) is a JSON-formatted object of the form:

..  data:: message

    **MessageId** *(string)*
        The name of the message being posted.
    **SendTime** *(long)*
        The time the message was sent, expressed as milliseconds since
        midnight 1 January 1970 UTC.

        ..  tip:: You can get this value in most modern browsers using the ``Date.now()`` method in JavaScript.

    **Values** *(JSON-formatted object)*
        The data associated with the message. This varies per message.

The following example shows the msg parameter for the :data:`Host_PerfTiming` message.

..  code-block:: JSON

    {
        "MessageId": "Host_PerfTiming",
        "SendTime": 1329014075000,
        "Values": {
            "Click": 1329014074800,
            "Iframe": 1329014074950,
            "HostFrameFetchStart": 1329014074970,
            "RedirectCount": 1
        }
    }

Sending messages to the |wac| iframe
------------------------------------

To send messages to the |wac| iframe, you must set the :term:`PostMessageOrigin` property in your WOPI
:ref:`CheckFileInfo` response to the URL of your host page. If you do not do this, |wac| will ignore any
messages you send to its iframe.

You can send the following messages; all others are ignored:

* :data:`App_PopState`
* :data:`Blur_Focus`
* :data:`CanEmbed`
* :data:`Grab_Focus`
* :data:`Host_IsFrameTrusted`
* :data:`Host_PerfTiming`
* :data:`Host_PostmessageReady`

..  data:: App_PopState

    ..  include:: /_fragments/onenote_only.rst

    The App_PopState message signals the |wac| application that state has been popped from the HTML5 History
    API to which the application should navigate to using the URL. This message should be triggered from an
    `onpopstate` listener in the host page.

    ..  attribute:: Values
        :noindex:

        Url *(string)*
            The URL associated with the popped history state.

        State *(JSON-formatted object)*
            The data associated with the state.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "App_PopState",
            "SendTime": 1329014075000,
            "Values": {
                "Url": "https://www.contoso.com/abc123/contents?wdtarget=pagexyz",
                "State": {
                    "Value": 0
                }
            }
        }

..  data:: Blur_Focus

    The Blur_Focus message signals the |wac| application to stop aggressively grabbing focus. Hosts should
    send this message whenever the host application UI is drawn over the |wac| frame, so that the Office
    application does not interfere with the UI behavior of the host.

    This message only affects |wac| edit modes; it does not affect view modes.

    ..  tip::
        When the host application displays UI over |wac|, it should put a full-screen dimming effect over the
        |wac| UI, so that it is clear that the Office application is not interactive.

    ..  attribute:: Values
        :noindex:

        *Empty.*

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "Blur_Focus",
            "SendTime": 1329014075000,
            "Values": { }
        }

..  data:: CanEmbed

    The CanEmbed message is sent by the host in response to a request to create a :term:`HostEmbeddedViewUrl` using the
    :js:data:`UI_FileEmbed` message.

    ..  attribute:: Values
        :noindex:

        **HostEmbeddedViewUrl** *(string)*
            A URI to a web page that provides access to a viewing experience for the file that can be embedded
            in another HTML page. This is equivalent to the :term:`HostEmbeddedViewUrl` in :ref:`CheckFileInfo`.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "CanEmbed",
            "SendTime": 1329014075000,
            "Values": {
                "HostEmbeddedViewUrl": "https://www.contosodrive.com/documents/1234/embedded/"
            }
        }

..  data:: Grab_Focus

    The Grab_Focus message signals the |wac| application to resume aggressively grabbing focus. Hosts should
    send this message whenever the host application UI that is drawn over the |wac| frame is closing. This
    allows the Office application to resume functioning.

    This message only affects |wac| edit modes; it does not affect view modes.

    ..  attribute:: Values
        :noindex:

        *Empty.*

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "Grab_Focus",
            "SendTime": 1329014075000,
            "Values": { }
        }

..  data:: Host_IsFrameTrusted

    The Host_IsFrameTrusted message is sent by the host in response to :js:data:`App_IsFrameTrusted` message.

    ..  attribute:: Values
        :noindex:

        **isTopFrameTrusted** *(boolean)*
            A boolean value providing the indication whether the top frame is trusted.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "Host_IsFrameTrusted",
            "SendTime": 1329014075000,
            "Values": {
                "isTopFrameTrusted": true
            }
        }

..  data:: Host_PerfTiming

    Provides performance related timestamps from the host page. Hosts should send this message when the Office
    Online frame is created so load performance can be more accurately tracked.

    ..  attribute:: Values
        :noindex:

        **Click** *(integer)*
            The timestamp, in ticks, when the user selected a link that launched the |wac| application. For
            example, if the host exposed a link in its UI that launches an |wac| application, this timestamp
            is the time the user originally selected that link.

        **Iframe** *(integer)*
            The timestamp, in ticks, when the host created the |wac| iframe when the user selected the link.

        **HostFrameFetchStart** *(integer)*
            The result of the `PerformanceTiming.fetchStart`_ attribute, if the browser supports the
            `W3C NavigationTiming API`_. If the NavigationTiming API is not supported by the browser, this must be 0.

        **RedirectCount** *(integer)*
            The result of the `PerformanceNavigation.redirectCount`_ attribute, if the browser supports the
            `W3C NavigationTiming API`_. If the NavigationTiming API is not supported by the browser, this must be 0.

.. _W3C NavigationTiming API: http://www.w3.org/TR/navigation-timing/
.. _PerformanceTiming.fetchStart: http://www.w3.org/TR/navigation-timing/#dom-performancetiming-fetchstart
.. _PerformanceNavigation.redirectCount: http://www.w3.org/TR/navigation-timing/#dom-performancenavigation-redirectcount

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "Host_PerfTiming",
            "SendTime": 1329014075000,
            "Values": {
                "Click": 1329014074800,
                "Iframe": 1329014074950,
                "HostFrameFetchStart": 1329014074970,
                "RedirectCount": 1
            }
        }

..  data:: Host_PostmessageReady

    |wac| delay-loads much of its JavaScript code, including most of its PostMessage senders and listeners.
    You might choose to follow this pattern in your WOPI host page. This means that your outer host page and the
    |wac| iframe must coordinate to ensure that each is ready to receive and respond to messages.

    To enable this coordination, |wac| sends the :data:`App_LoadingStatus` message only after all of its message
    senders and listeners are available. In addition, |wac| listens for the :data:`Host_PostmessageReady`
    message from the outer frame. Until it receives this message, some UI, such as the :guilabel:`Share` button, is
    disabled.

    Until your host page receives the :data:`App_LoadingStatus` message, the |wac| frame cannot respond to any
    incoming messages except :data:`Host_PostmessageReady`. |wac| does not delay-load its
    :data:`Host_PostmessageReady` listener; it is available almost immediately upon iframe load.

    If you are delay-loading your PostMessage code, you must ensure that your :data:`App_LoadingStatus` listener is not
    delay-loaded. This will ensure that you can receive the :data:`App_LoadingStatus` message even if your other
    PostMessage code has not yet loaded.

    The following is the typical flow:

    1. Host page begins loading.
    2. |wac| frame begins loading. Some UI elements are disabled, because :data:`Host_PostmessageReady` has
       not yet been sent by the host page.
    3. Host page finishes loading and sends :data:`Host_PostmessageReady`. No other messages are sent because the
       host page hasn't received the :data:`App_LoadingStatus` message from the |wac| frame.
    4. |wac| frame receives :data:`Host_PostmessageReady`.
    5. |wac| frame finishes loading and sends :data:`App_LoadingStatus` to host page.
    6. Host page and |wac| communicate by using other PostMessage messages.

    ..  attribute:: Values
        :noindex:

        *Empty.*

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "Host_PostmessageReady",
            "SendTime": 1329014075000,
            "Values": { }
        }


Listening to messages from the |wac| iframe
-------------------------------------------

The |wac| iframe will send messages to the host page. On the receiving end, the host page will receive a
MessageEvent. The origin property of the MessageEvent is the origin of the message, and the data property is the
message being sent. The following code example shows how you might consume a message.

.. code-block:: javascript

    function handlePostMessage(e) {
        // The actual message is contained in the data property of the event.
        var msg = JSON.parse(e.data);

        // The message ID is now a property of the message object.
        var msgId = msg.MessageId;

        // The message parameters themselves are in the Values
        // parameter on the message object.
        var msgData = msg.Values;

        // Do something with the message here.
    }
    window.addEventListener('message', handlePostMessage, false);

The host page receives the following messages; all others are ignored:

* :data:`App_IsFrameTrusted`
* :data:`App_LoadingStatus`
* :data:`App_PushState`
* :data:`Edit_Notification`
* :data:`File_Rename`
* :data:`UI_Close`
* :data:`UI_Edit`
* :data:`UI_FileEmbed`
* :data:`UI_FileVersions`
* :data:`UI_Sharing`
* :data:`UI_Workflow`


..  _outgoing postmessage common values:

Common Values
~~~~~~~~~~~~~

In addition to message-specific values passed with each message, |wac| sends the following common values with
every outgoing PostMessage:

..  glossary::
    :sorted:

    ui-language *(string)*
        The LCID of the language |wac| was loaded in. This value will not match the value provided using the
        :term:`UI_LLCC` placeholder. Instead, this value will be the numeric LCID value (as a *string*) that
        corresponds to the language used. See :ref:`languages` for more information.

        This value may be needed in the event that |wac| renders using a language different than the one
        requested by the host, which may occur if |wac| is not localized in the language requested. In that
        case, the host may choose to draw its own UI in the same language that |wac| used.

    wdUserSession *(string)*
        The ID of the |wac| session. This value can be logged by host and used when
        :ref:`troubleshooting <troubleshooting>` issues with |wac|. See :ref:`session id` for more
        information about this value.


..  data:: App_IsFrameTrusted

    The App_IsFrameTrusted message is posted by |wac| application frame to initiate handshake flow.
    The host is expected to check whether the top frame is trusted and post :js:data:`Host_IsFrameTrusted`
    message back to |wac| application. The host first needs to include a query string: &sftc=1 to url of POST
    request sent to |wac| application, to indicate to |wac| application that the host supports frame trust post Message.

    ``sftc`` stands for "SupportsFrameTrustedPostMessage." Only when ``&sftc`` is included in the query string,
    the ``App_IsFrameTrusted`` message will be posted by |wac| application frame to initiate handshake flow.


    ..  attribute:: Values
        :noindex:

        :ref:`Common values <outgoing postmessage common values>` only.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "App_IsFrameTrusted",
            "SendTime": 1329014075000,
            "Values": {
                "wdUserSession": "3692f636-2add-4b64-8180-42e9411c4984",
                "ui-language": "1033"
            }
        }

..  data:: App_LoadingStatus

    The App_LoadingStatus message is posted after the |wac| application frame has loaded. Until the host
    receives this message, it must assume that the |wac| frame cannot react to any incoming messages except
    :data:`Host_PostmessageReady`.

    ..  attribute:: Values
        :noindex:

        DocumentLoadedTime *(long)*
            The time that the frame was loaded.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "App_LoadingStatus",
            "SendTime": 1329014075000,
            "Values": {
                "DocumentLoadedTime": 1329014074983,
                "wdUserSession": "3692f636-2add-4b64-8180-42e9411c4984",
                "ui-language": "1033"
            }
        }

..  data:: App_PushState

    ..  include:: /_fragments/onenote_only.rst

    The App_PushState message is posted when the user changes the state of |wac| application in a way
    which the user may wish to return to later, requesting to capture it in the HTML 5 History API. In receiving
    this message, the Host page should using `history.pushState` to capture the state for a potential later
    state pop.

    To send this message, the :term:`AppStateHistoryPostMessage` property in the :ref:`CheckFileInfo` response
    from the host must be set to ``true``. Otherwise |wac| will not send this message.

    ..  attribute:: Values
        :noindex:

        Url *(string)*
            The URL associated with the message.

        State *(JSON-formatted object)*
            The data associated with the state.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "App_PushState",
            "SendTime": 1329014075000,
            "Values": {
                "Url": "https://www.contoso.com/abc123/contents?wdtarget=pagexyz",
                "State": {
                    "Value": 0
                },
                "wdUserSession": "3692f636-2add-4b64-8180-42e9411c4984",
                "ui-language": "1033"
            }
        }

..  data:: Edit_Notification

    The Edit_Notification message is posted when the user first makes an edit to a document, and every five minutes
    thereafter, if the user has made edits in the last five minutes. Hosts can use this message to gauge whether
    users are interacting with |wac|. In coauthoring sessions, hosts cannot use the WOPI calls for
    this purpose.

    To send this message, the :term:`EditNotificationPostMessage` property in the :ref:`CheckFileInfo` response from
    the host must be set to ``true``. Otherwise |wac| will not send this message.

    ..  attribute:: Values
        :noindex:

        :ref:`Common values <outgoing postmessage common values>` only.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "Edit_Notification",
            "SendTime": 1329014075000,
            "Values": {
                "wdUserSession": "3692f636-2add-4b64-8180-42e9411c4984",
                "ui-language": "1033"
            }
        }

..  data:: File_Rename

    The File_Rename message is posted when the user renames the current file in |wac|. The host can use this
    message to optionally update the UI, such as the title of the page.

    ..  note::
        If the host does not return the :term:`SupportsRename` parameter in their :ref:`CheckFileInfo` response, then
        the rename UI will not be available in |wac|.

    ..  attribute:: Values
        :noindex:

        NewName *(string)*
            The new name of the file.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "File_Rename",
            "SendTime": 1329014075000,
            "Values": {
                "NewName": "Renamed Document",
                "wdUserSession": "3692f636-2add-4b64-8180-42e9411c4984",
                "ui-language": "1033"
            }
        }

..  data:: UI_Close

    The UI_Close message is posted when the |wac| application is closing, either due to an error or a user
    action. Typically, the URL specified in the :term:`CloseUrl` property in the :ref:`CheckFileInfo` response is
    displayed. However, hosts can intercept this message instead and navigate in an appropriate way.

    To send this message, the :term:`ClosePostMessage` property in the :ref:`CheckFileInfo` response from the host
    must be set to ``true``. Otherwise |wac| will not send this message.

    ..  attribute:: Values
        :noindex:

        :ref:`Common values <outgoing postmessage common values>` only.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "UI_Close",
            "SendTime": 1329014075000,
            "Values": {
                "wdUserSession": "3692f636-2add-4b64-8180-42e9411c4984",
                "ui-language": "1033"
            }
        }

..  data:: UI_Edit

    The UI_Edit message is posted when the user activates the :guilabel:`Edit` UI in |wac|. This UI is only
    visible when using the :wopi:action:`view` action.

    To send this message, the :term:`EditModePostMessage` property in the :ref:`CheckFileInfo` response from the host
    must be set to ``true``. Otherwise |wac| will not send this message and will redirect the inner iframe to
    an edit action URL instead.

    Hosts may choose to use this message in cases where they want more control over the user's transition to edit
    mode. For example, a host may wish to prompt the user for some additional host-specific information before
    navigating.

    ..  attribute:: Values
        :noindex:

        :ref:`Common values <outgoing postmessage common values>` only.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "UI_Edit",
            "SendTime": 1329014075000,
            "Values": {
                "wdUserSession": "3692f636-2add-4b64-8180-42e9411c4984",
                "ui-language": "1033"
            }
        }

..  data:: UI_FileEmbed

    The UI_FileEmbed message is posted when the user activates the *Embed* UI in |wac|. The host should use
    this message to trigger the creation of a :term:`HostEmbeddedViewUrl`, which the host then passes back to the WOPI
    client using the :js:data:`CanEmbed` message.

    To send this message, the :term:`FileEmbedCommandPostMessage` property in the :ref:`CheckFileInfo` response from
    the host must be set to ``true``, and :term:`FileEmbedCommandUrl` must be provided. Otherwise |wac| will not send
    this message.

    |wac| will also not send the message if a :term:`HostEmbeddedViewUrl` is provided in the :ref:`CheckFileInfo`
    response. In this case, since a :term:`HostEmbeddedViewUrl` is already provided, there is no need to retrieve it
    from the host via PostMessage. See :ref:`embedding` for more details.

    ..  attribute:: Values
        :noindex:

        :ref:`Common values <outgoing postmessage common values>` only.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "UI_FileEmbed",
            "SendTime": 1329014075000,
            "Values": {
                "wdUserSession": "3692f636-2add-4b64-8180-42e9411c4984",
                "ui-language": "en-us"
            }
        }

..  data:: UI_FileVersions

    The UI_FileVersions message is posted when the user activates the :guilabel:`Previous Versions` UI in |wac|. The
    host should use this message to trigger any custom file version history UI.

    To send this message, the :term:`FileVersionPostMessage` property in the :ref:`CheckFileInfo` response from the
    host must be set to ``true``. Otherwise |wac| will not send this message.

    ..  attribute:: Values
        :noindex:

        :ref:`Common values <outgoing postmessage common values>` only.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "UI_FileVersions",
            "SendTime": 1329014075000,
            "Values": {
                "wdUserSession": "3692f636-2add-4b64-8180-42e9411c4984",
                "ui-language": "1033"
            }
        }


..  data:: UI_Sharing

    The UI_Sharing message is posted when the user activates the :guilabel:`Share` UI in |wac|. The host should
    use this message to trigger any custom sharing UI.

    To send this message, the :term:`FileSharingPostMessage` property in the :ref:`CheckFileInfo` response from the
    host must be set to ``true``. Otherwise |wac| will not send this message.

    ..  attribute:: Values
        :noindex:

        :ref:`Common values <outgoing postmessage common values>` only.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "UI_Sharing",
            "SendTime": 1329014075000,
            "Values": {
                "wdUserSession": "3692f636-2add-4b64-8180-42e9411c4984",
                "ui-language": "1033"
            }
        }

..  data:: UI_Workflow

    The UI_Workflow message is posted when the user activates the :guilabel:`Workflow` UI in |wac|. The host
    should use this message to trigger any custom workflow UI.

    To send this message, the :term:`WorkflowPostMessage` property in the :ref:`CheckFileInfo` response from the
    host must be set to ``true``. Otherwise |wac| will not send this message.

    ..  attribute:: Values
        :noindex:

        WorkflowType *(string)*
            The :term:`WorkflowType` associated with the message. This will match one of the values provided by the
            host in the :term:`WorkflowType` property in :ref:`CheckFileInfo`.


    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "UI_Workflow",
            "SendTime": 1329014075000,
            "Values": {
                "WorkflowType": "Submit",
                "wdUserSession": "3692f636-2add-4b64-8180-42e9411c4984",
                "ui-language": "1033"
            }
        }
