
..  _Troubleshooting:

Troubleshooting interactions with Office Online
===============================================

..  spelling::

    validator

When integrating with Office Online, it may be necessary to work with Microsoft engineers to diagnose problems.
Following the steps below will help both you and Microsoft diagnose problems more quickly.

Before reporting issues
-----------------------

Before reporting any issues to Microsoft, ensure that you have done the following:

#. Check that the :ref:`validator` tests are passing. Most common issues are easily diagnosed using the validator,
   and passing tests are a pre-requisite for any investigations into issues you're encountering.

   ..  tip::
       In cases where the validator tests are not consistent with the documentation, assume that the validator is
       correct. Also please `file an issue`__ so that we can address the gaps in the documentation.
#. Check the :ref:`common issues` to see if you have made one of the more common mistakes in your WOPI implementation.
#. Check the :ref:`known issues` to see if what you're encountering is already known. When possible, workarounds will
   be provided in the issue notes.

..  __: https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/issues


Fiddler traces
--------------

The most useful tool when troubleshooting Office Online integration issues is `Fiddler`_. When you run Fiddler while
reproducing an issue, it will record all HTTP requests and responses. You can then save the Fiddler trace and share
it with Microsoft engineers. Fiddler traces are an invaluable tool when troubleshooting problems because they
provide a full record of the HTTP traffic between the browser and Office Online. As a rule of thumb, hosts should
always provide a Fiddler trace when reporting Office Online integration issues to Microsoft.

..  _Fiddler: http://www.telerik.com/fiddler


..  _Fiddler HTTPS:

Enabling HTTPS decryption in Fiddler
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Because Office Online traffic is encrypted, Fiddler must be configured to decrypt the HTTPS traffic in order to be
useful. In order to enable HTTPS encryption in Fiddler, do the following:

#. From Fiddler, click :menuselection:`Tools --> Fiddler Options...` to open the options dialog.
#. On the :guilabel:`HTTPS` tab, check the :guilabel:`Capture HTTPS CONNECTs` check box.
#. Check the :guilabel:`Decrypt HTTPS traffic` check box. When you do this Fiddler will display a dialog asking if you
   wish to trust the Fiddler Root certificate. Click :guilabel:`Yes`. You may also see some security warnings from the
   operating system asking if you want to install the certificate. Click :guilabel:`Yes` to all of these prompts.
#. In the drop-down, select :guilabel:`...from browsers only`.
#. Click :guilabel:`OK` in the options dialog.
#. Close Fiddler and restart it.

Fiddler is now configured to decrypt HTTPS traffic.

..  figure:: /images/fiddler_https.png
    :alt: An image showing the HTTPS decryption configuration UI in Fiddler.

    Fiddler must be configured to decrypt HTTPS traffic in order to produce useful traces


Using Fiddler to trace a session
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Using Fiddler to trace HTTP activity is straightforward:

#. Open Fiddler.
#. If needed, begin capturing traffic (:menuselection:`File --> Capture Traffic`). Note that Fiddler starts in
   capture mode when it is opened, so this step may not be necessary.
#. Navigate to the host page URL while Fiddler is running, then reproduce the issue if needed.
#. Once the issue is reproduced, save the Fiddler session as an archive
   (:menuselection:`File --> Save --> All sessions...`). The resulting file should have the file extension ``.saz``.


Using Fiddler in Linux or OS X
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Fiddler works very well in Windows, but can also be used in Linux and OS X using Mono. See
http://fiddler.wikidot.com/mono for more information on installing and configuring it.


..  _har:

Alternatives to Fiddler: HTTP Archives (HAR)
--------------------------------------------

If you cannot use Fiddler to get session traces, you can also use the Chrome browser developer tools to save HTTP
Archive (HAR) files containing the HTTP requests made by the browser. To do this, do the following:

#.  Open the Chrome developer tools and select the :guilabel:`Network` tab.
#.  Check the :guilabel:`Preserve log` check box if you wish to retain the request log across multiple page
    navigations. This makes the network tracing behave more like Fiddler, and makes it less likely that you'll lose
    your request log by accidentally refreshing the page or navigating away before you save the log. Office Online
    applications are single-page applications, so you don't *need* to check this if you're only planning to trace a
    single session.

    ..  figure:: /images/chrome_network_tab.png
        :alt: An image showing the :guilabel:`Network` tab in the Chrome developer tools.

        :guilabel:`Network` tab in the Chrome developer tools

#.  After you are done reproducing the issue, right-click in the network view and select the
    :guilabel:`Save as HAR with Content` option.

    ..  figure:: /images/chrome_save_as_har.png
        :alt: An image showing the :guilabel:`Save as HAR with Content` option in the Chrome developer tools.

        :guilabel:`Save as HAR with Content` option in the Chrome developer tools

#.  Zip the resulting HAR file, since they can be quite large and generally compress well.

..  tip::
    Other browsers' developer tools have similar capabilities to Chrome to save session HTTP requests as an HTTP
    Archive.


..  _session id:

Session IDs
-----------

Whenever an :ref:`action URL <Action URLs>` is navigated to, Office Online creates a unique session ID. This session
ID allows Microsoft engineers to quickly retrieve all server logs related to that session, including information
about the WOPI calls that were made to the host. The session ID is passed back in the WOPI action URL HTTP response in
the **X-UserSessionId** response header. It is also passed on every subsequent request made by the browser to Office
Online in the **X-UserSessionId** request header, and it is included in all PostMessages
:ref:`sent from Office Online to the host page <outgoing postmessage common values>` in the
:term:`wdUserSession <wdUserSession (string)>` value.

The easiest way to retrieve the session ID is to use Fiddler, as described previously. However, you can also use the
request tracking features in the Chrome and Internet Explorer developer tools to capture HTTP requests and determine
the value of the **X-UserSessionId** response header.

..  figure:: /images/chrome_session_id.png
    :alt: An image showing the Chrome developer tools.

    The Chrome developer tools can be used to retrieve a session ID.

..  figure:: /images/ie_session_id.png
    :alt: An image showing the Internet Explorer developer tools.

    As can the Internet Explorer developer tools.

Full Fiddler traces are always preferred, but in cases where they're not available, session IDs can still be used by
Microsoft engineers to retrieve Office Online server logs.


..  _fiddler not running:

Getting session IDs after an error has occurred
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

In some cases, you may not be running Fiddler or browser developer tools when your session encounters an error. In
these cases, the |wac| application will display an error either in a modal dialog or in a yellow bar at the top of
the document below the ribbon.

Sometimes the error dialog will include the session ID in the dialog itself:

..  figure:: /images/error_with_session_id_and_exit_button.png
    :alt: An image of an error dialog in Word Online that includes a session ID.

In such cases, you can copy the session ID from the error dialog.

..  tip::
    **Please do not simply send a screen shot of the error dialog.** Copy the session ID as text and send the
    session ID itself to Microsoft engineers. If you send a screen shot, the Microsoft engineer will be forced to
    transcribe the session ID from the image, which is error-prone and tedious. Always provide the session ID as text.

In other cases, the session ID might not be available in the UI.

..  figure:: /images/error_bizbar.png
    :alt: An image of an error in Word Online displayed in a yellow bar under the ribbon.

At this point, it is still often possible to get the session ID by using the following steps:

#.  Before closing the browser, refreshing the page, or clicking any buttons in the dialog or notification bar,
    start Fiddler or open the browser developer tools.
#.  Navigate away from the Office Online application or click a button in the dialog or notification bar.
#.  You should see a request to either `WsaUpload.ashx` or `RemoteUls.ashx`. The response to those requests should
    include the **X-UserSessionId** header with the session ID.


Correlation IDs
---------------

Every WOPI request Office Online makes to a host will have an ID called the correlation ID. This ID will be included
in the WOPI request using the **X-WOPI-CorrelationId** request header. Hosts should log this ID for each incoming WOPI
request; doing so will allow hosts to easily correlate their own logs with Office Online's server logs.

There are other WOPI request headers that may be useful for hosts to log. See the :ref:`Common headers` for more
information.

..  tip::

    In many cases, a single correlation ID is all that's needed in order for a Microsoft engineer to retrieve
    complete server logs for an Office Online session for analysis. While hosts should provide Fiddler traces or
    session IDs whenever possible, a correlation ID will often suffice if necessary.
