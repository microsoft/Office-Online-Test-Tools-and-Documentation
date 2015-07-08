
..  _Troubleshooting:

Troubleshooting interactions with Office Online
===============================================

When integrating with Office Online, it may be necessary to work with Microsoft engineers to diagnose problems.
Following the steps below will help both you and Microsoft diagnose problems more quickly.

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


Session IDs
-----------

Whenever an :ref:`action URL <Action URLs>` is navigated to, Office Online creates a unique session ID. This session
ID allows Microsoft engineers to quickly retrieve all server logs related to that session, including information
about the WOPI calls that were made to the host. The session ID is passed back in the WOPI action URL HTTP response in
the **X-UserSessionId** response header. It is also passed on every subsequent request made by the browser to Office
Online in the **X-UserSessionId** request header.

The easiest way to retrieve the session ID is to use Fiddler, as described previously. However, you can also use the
request tracking features in the Chrome and Internet Explorer developer tools to capture HTTP requests and determine
the value of the **X-WOPI-UserSessionId** response header.

..  figure:: /images/chrome_session_id.png
    :alt: An image showing the Chrome developer tools.

    The Chrome developer tools can be used to retrieve a session ID.

..  figure:: /images/ie_session_id.png
    :alt: An image showing the Internet Explorer developer tools.

    As can the Internet Explorer developer tools.

Full Fiddler traces are always preferred, but in cases where they're not available, session IDs can still be used by
Microsoft engineers to retrieve Office Online server logs.


Correlation IDs
---------------

Every WOPI request Office Online makes to a host will have a unique ID, called the correlation ID. This ID will be
included in the WOPI request using the **X-WOPI-CorrelationId** request header. Hosts should log this ID for each
incoming WOPI request; doing so will allow hosts to easily correlate their own logs with Office Online's server logs.

There are other WOPI request headers that may be useful for hosts to log. See the :ref:`Common headers` for more
information.

..  tip::

    In many cases, a single correlation ID is all that's needed in order for a Microsoft engineer to retrieve
    complete server logs for an Office Online session for analysis. While hosts should provide Fiddler traces or
    session IDs whenever possible, a correlation ID will often suffice if necessary.
