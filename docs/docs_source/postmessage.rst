
..  _PostMessage:

Integrating with Office Online by using PostMessage
===================================================

..  default-domain:: js

..  spelling::

    msg


You can integrate your UI into Office Online applications. This way, you can use your UI for actions on Office
documents, such as sharing.

To integrate with Office Online in this way, implement the
`HTML5 Web Messaging protocol <http://www.w3.org/TR/webmessaging/>`_. The Web Messaging protocol,
also known as PostMessage, allows the Office Online frame to communicate with its parent :term:`host page`, and
vice-versa. The following example shows the general syntax for PostMessage. In this example, ``otherWindow`` is a
reference to another window that ``msg`` will be posted to.

..  function:: otherWindow.postMessage(msg, targetOrigin)

    :param string msg: A string (or JSON object) that contains the message
        data.
    :param string targetOrigin: Specifies what the origin of ``otherWindow``
        must be for the event to be dispatched, either as the literal string
        ``*`` (indicating no preference) or as a URI.

Message format
--------------

All messages posted to and from the Office Online application frame are posted using the
:func:`~otherWindow.postMessage` function. Each message (the ``msg`` parameter in the
:func:`~otherWindow.postMessage` function) is a JSON-formatted object of the form:

..  data:: message

    **MessageId** *(string)*
        The name of the message being posted.
    **SendTime** *(long)*
        The time the message was sent, in Unix time (milliseconds since
        midnight 1 January 1970 UTC).
    **Values** *(JSON-formatted object)*
        The data associated with the message. This varies per message.

The following example shows the msg parameter for the :data:`Host_PerfTiming` message.

..  code-block:: JSON

    {
        "MessageId": "Host_PerfTiming",
        "SendTime": 1329014075,
        "Values": {
            "Click": 1329014073,
            "Iframe": 1329014080,
            "HostFrameFetchStart": 1329014173,
            "RedirectCount": 1
        }
    }

Sending messages to the Office Online iframe
--------------------------------------------

To send messages to the Office Online iframe, you must set the PostMessageOrigin property in your WOPI CheckFileInfo
response to the URL of your host page. If you do not do this, Office Online will ignore any messages you send to its
iframe.

You can send the following messages; all others are ignored:

* :data:`Blur_Focus`
* :data:`Grab_Focus`
* :data:`Host_PerfTiming`
* :data:`Host_PostmessageReady`

..  data:: Blur_Focus

    The Blur_Focus message signals the Office Online application to stop aggressively grabbing focus. Hosts should
    send this message whenever the host application UI is drawn over the Office Online frame, so that the Office
    application does not interfere with the UI behavior of the host.

    This message only affects Office Online edit modes; it does not affect view modes.

    ..  tip::
        When the host application displays UI over Office Online, it should put a full-screen dimming effect over the
        Office Online UI, so that it is clear that the Office application is not interactive.

    ..  attribute:: Values
        :noindex:

            *Empty.*

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "Blur_Focus",
            "SendTime": 1329014075,
            "Values": { }
        }

..  data:: Grab_Focus

    The Grab_Focus message signals the Office Online application to resume aggressively grabbing focus. Hosts should
    send this message whenever the host application UI that is drawn over the Office Online frame is closing. This
    allows the Office application to resume functioning.

    This message only affects Office Online edit modes; it does not affect view modes.

    ..  attribute:: Values
        :noindex:

            *Empty.*

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "Grab_Focus",
            "SendTime": 1329014075,
            "Values": { }
        }

..  data:: Host_PerfTiming

    Provides performance related timestamps from the host page. Hosts should send this message when the Office
    Online frame is created so load performance can be more accurately tracked.

    ..  attribute:: Values
        :noindex:

            **Click** *(integer)*
            The timestamp, in ticks, when the user selected a link that launched the Office Online application. For
            example, if the host exposed a link in its UI that launches an Office Online application, this timestamp
            is the time the user originally selected that link.

            **Iframe** *(integer)*
            The timestamp, in ticks, when the host created the Office Online iframe when the user selected the link.

            **HostFrameFetchStart** *(integer)*
            The result of the `PerformanceTiming.fetchStart`_ attribute, if the browser supports the
            `W3C NavigationTiming API`_. If the NavigationTiming API is not supported by the browser, this is 0.

            **RedirectCount** *(integer)*
            The result of the `PerformanceNavigation.redirectCount`_ attribute, if the browser supports the
            `W3C NavigationTiming API`_. If the NavigationTiming API is not supported by the browser, this is 0.

.. _W3C NavigationTiming API: http://www.w3.org/TR/navigation-timing/
.. _PerformanceTiming.fetchStart: http://www.w3.org/TR/navigation-timing/#dom-performancetiming-fetchstart
.. _PerformanceNavigation.redirectCount: http://www.w3.org/TR/navigation-timing/#dom-performancenavigation-redirectcount

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "Host_PerfTiming",
            "SendTime": 1329014075,
            "Values": {
                "Click": 1329014073,
                "Iframe": 1329014080,
                "HostFrameFetchStart": 1329014173,
                "RedirectCount": 1
            }
        }

..  data:: Host_PostmessageReady

    Office Online delay-loads much of its JavaScript code, including most of its PostMessage senders and listeners.
    You might choose to follow this pattern in your WOPI host page. This means that your outer host page and the
    Office Online iframe must coordinate to ensure that each is ready to receive and respond to messages.

    To enable this coordination, Office Online sends the :data:`App_LoadingStatus` message only after all of its message
    senders and listeners are available. In addition, Office Online listens for the :data:`Host_PostmessageReady`
    message from the outer frame. Until it receives this message, some UI, such as the **Share** button, is disabled.

    Until your host page receives the :data:`App_LoadingStatus` message, the Office Online frame cannot respond to any
    incoming messages except :data:`Host_PostmessageReady`. Office Online does not delay-load its
    :data:`Host_PostmessageReady` listener; it is available almost immediately upon iframe load.

    If you are delay-loading your PostMessage code, you must ensure that your :data:`App_LoadingStatus` listener is not
    delay-loaded. This will ensure that you can receive the :data:`App_LoadingStatus` message even if your other
    PostMessage code has not yet loaded.

    The following is the typical flow:

    1. Host page begins loading.
    2. Office Online frame begins loading. Some UI elements are disabled, because :data:`Host_PostmessageReady` has
       not yet been sent by the host page.
    3. Host page finishes loading and sends :data:`Host_PostmessageReady`. No other messages are sent because the
       host page hasn't received the :data:`App_LoadingStatus` message from the Office Online frame.
    4. Office Online frame receives :data:`Host_PostmessageReady`.
    5. Office Online frame finishes loading and sends :data:`App_LoadingStatus` to host page.
    6. Host page and Office Online communicate by using other PostMessage messages.

    ..  attribute:: Values
        :noindex:

            *Empty.*

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "Host_PostmessageReady",
            "SendTime": 1329014075,
            "Values": { }
        }

Listening to messages from the Office Online iframe
---------------------------------------------------

The Office Online iframe will send messages to the host page. On the receiving end, the host page will receive a
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

* :data:`App_LoadingStatus`
* :data:`Edit_Notification`
* :data:`File_Rename`
* :data:`UI_Close`
* :data:`UI_Edit`
* :data:`UI_FileVersions`
* :data:`UI_Sharing`

..  data:: App_LoadingStatus

    The App_LoadingStatus message is posted after the Office Online application frame has loaded. Until the host
    receives this message, it must assume that the Office Online frame cannot react to any incoming messages except
    :data:`Host_PostmessageReady`.

    ..  attribute:: Values
        :noindex:

            **DocumentLoadedTime** *(long)*
            The time that the frame was loaded.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "App_LoadingStatus",
            "SendTime": 1329014075,
            "Values": {
                "DocumentLoadedTime": 1329014073
            }
        }

..  data:: Edit_Notification

    The Edit_Notification message is posted when the user first makes an edit to a document, and every five minutes
    thereafter, if the user has made edits in the last five minutes. Hosts can use this message to gauge whether
    users are interacting with Office Online. In coauthoring sessions, hosts cannot use the WOPI calls for
    this purpose.

    To send this message, the :term:`EditNotificationPostMessage` property in the :ref:`CheckFileInfo` response from
    the host must be set to ``true``. Otherwise Office Online will not send this message.

    ..  attribute:: Values
        :noindex:

            *Empty.*

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "Edit_Notification",
            "SendTime": 1329014075,
            "Values": { }
        }

..  data:: File_Rename

    The File_Rename message is posted when the user renames the current file in Office Online. The host can use this
    message to optionally update the UI, such as the title of the page.

    ..  note::
        If the host does not return the :term:`SupportsRename` parameter in their :ref:`CheckFileInfo` response, then
        the rename UI will not be available in Office Online.

    ..  attribute:: Values
        :noindex:

            **NewName** *(string)*
            The new name of the file.

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "File_Rename",
            "SendTime": 1329014075,
            "Values": {
                "NewName": "Renamed Document"
            }
        }

..  data:: UI_Close

    The UI_Close message is posted when the Office Online application is closing, either due to an error or a user
    action. Typically, the URL specified in the :term:`CloseUrl` property in the :ref:`CheckFileInfo` response is
    displayed. However, hosts can intercept this message instead and navigate in an appropriate way.

    To send this message, the :term:`ClosePostMessage` property in the :ref:`CheckFileInfo` response from the host
    must be set to ``true``. Otherwise Office Online will not send this message.

    ..  attribute:: Values
        :noindex:

                *Empty.*

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "UI_Close",
            "SendTime": 1329014075,
            "Values": { }
        }

..  data:: UI_Edit

    The UI_Edit message is posted when the user activates the *Edit* UI in Office Online. This UI is only visible
    when using the :wopi:action:`view` action.

    To send this message, the :term:`EditModePostMessage` property in the :ref:`CheckFileInfo` response from the host
    must be set to ``true``. Otherwise Office Online will not send this message and will redirect the inner iframe to
    an edit action URL instead.

    Hosts may choose to use this message in cases where they want more control over the user's transition to edit
    mode. For example, a host may wish to prompt the user for some additional host-specific information before
    navigating.

    ..  admonition:: Excel Online Note

        Excel Online does not send this message. When the user activates the *Edit* UI in Excel Online, the inner
        iframe will always be navigated.

    ..  attribute:: Values
        :noindex:

            *Empty.*

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "UI_Edit",
            "SendTime": 1329014075,
            "Values": { }
        }

..  data:: UI_FileVersions

    The UI_FileVersions message is posted when the user activates the *Previous Versions* UI in Office Online. The host
    can use this message to optionally navigate the outer frame to an appropriate URL.

    ..  attribute:: Values
        :noindex:

            *Empty.*

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "UI_FileVersions",
            "SendTime": 1329014075,
            "Values": { }
        }

..  data:: UI_Sharing

    The UI_Sharing message is posted when the user activates the *Share* UI in Office Online. The host should use this
    message to trigger any custom sharing UI.

    To send this message, the :term:`FileSharingPostMessage` property in the :ref:`CheckFileInfo` response from the
    host must be set to ``true``. Otherwise Office Online will not send this message.

    ..  attribute:: Values
        :noindex:

            *Empty.*

    ..  rubric:: Example Message:

    ..  code-block:: JSON

        {
            "MessageId": "UI_Sharing",
            "SendTime": 1329014075,
            "Values": { }
        }
