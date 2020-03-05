
.. _app to app:

App to app sign in for |Office iOS| and |Office Android|
========================================================

The normal flow to sign in to your service from |Office iOS| or |Office Android| uses the OS WebView where your
web sign in experience is rendered inside the |Office iOS| or |Office Android| app. Optionally, an additional
optimization can be done where the user can sign in using your app.

The app to app flow involves |Office iOS| or |Office Android| invoking your app through your app's URL scheme to sign in
to your service, and your app invoking |Office iOS| or |Office Android| when sign in is complete.

Sign in using your app
----------------------

* When signing in to your service is required (i.e. the first time a file is opened from your app into
  Office, or when the user explicitly adds your service as a place in |Office iOS| or |Office Android|), |Office iOS| or
  |Office Android| calls your bootstrapper to obtain the ``authorization_uri``, which it displays in a UIWebView. In
  addition to the ``authorization_uri``, you should return a set of UrlSchemes that can be used to invoke your app.
* |Office iOS| or |Office Android| will first attempt to use the UrlSchemes to invoke your app. If none are returned, or if none of them
  are registered (i.e. your app is not installed), |Office iOS| or |Office Android| will fall back to the UIWebView.
* If the user does have your app installed, your app will be invoked via the UrlSchemes, and the user will be sent
  to your app to complete authentication. If the user declines to open your app, |Office iOS| or |Office Android| will
  fall back to the UIWebView.
* Once inside your app, you should do whatever is needed to obtain the user's auth code (for example, display "Grant
  permission to Office" dialog or ask for additional sign ins).
* Return the user to the |Office iOS| or |Office Android| app with the auth code via the Office URL scheme (see below).


|Office iOS| Specific changes
-----------------------------

URL scheme design
~~~~~~~~~~~~~~~~~

The data passed via the URL scheme is essentially the same as would be passed via ``authorization_uri``.

Here is an example of a normal ``authorization_uri`` with parameters added. The parameters are described in
`RFC6749 <https://tools.ietf.org/html/rfc6749>`_.


Invoking the sign in screen::

    https://contoso.com/api/oauth2/authorize?client_id=abcdefg&redirect_uri=https%3A%2F%2Flocalhost&response_type=code&scope=&rs=en-US&Build=16.1.1234&Platform=iOS

Host's redirect URL that ends the OAuth flow::

    https://localhost?state=&code=abcdefg&tk=https%3A%2F%2Fcontoso.com%2Fapi%2Ftoken%2F%3Fextra%3Dstuff

The same parameters are passed via the URL Schemes, with the addition of "action".

|Office iOS| invoking your app ("To" URL)::

    Contoso:client_id=abcdefg&response_type=code&scope=wopi&rs=enUS&build=16.1.1234&platform=iOS&app=word&action=76d173ad-a43f-4e3c-a5e7-0e7276b4c624

Your app invoking |Office iOS| ("Back" URL)::

    ms-word-tp:code=abcdefg&tk=https%3A%2F%2Fcontoso.com%2Fapi%2Ftoken%2F%3Fextra%3Dstuff&sc=xyz&action=76d173ad-a43f-4e3c-a5e7-0e7276b4c624

The values of the parameters should be URL encoded.

If authentication fails, the error parameters per `RFC6749 <https://tools.ietf.org/html/rfc6749>`_ can also be passed
back to |Office iOS| via the URL schemes, just as they can be passed back to |Office iOS| via the redirect URI in the
UIWebView model.


New URL schemes registered by |Office iOS|
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 * ``ms-word-tp``
 * ``ms-excel-tp``
 * ``ms-powerpoint-tp``
 * ``ms-officemobile-tp``


These are for Word, Excel, and PowerPoint respectively. Use these to invoke |Office iOS| when the user is done with
authentication on your side.


Action parameter
~~~~~~~~~~~~~~~~

Action is a string passed to your app that should be passed back to Office unchanged.


|Office Android| Specific changes
---------------------------------

Service-side Changes
~~~~~~~~~~~~~~~~~~~~

Adding information to the WWW-Authenticate response header on unauthenticated bootstrap requests

|Office Android| will use Intent to invoke your App. The information Office needs are your App's Package name, Auth activity
name and Version code. Office will consider provided Version code as the base version and will assume that your App with this
Version and above will support App to App authentication.

The information Office needs is passed via the URLScheme parameter in the WWW-Authenticate response header to unauthenticated
bootstrap request.

+-------------------------+-------------------------------------------+----------+-------------------------------------------+
|Parameter                | Value                                     | Required | Example                                   |
+=========================+===========================================+==========+===========================================+
| ``Bearer``              | n/a                                       | Yes      | Bearer                                    |
+-------------------------+-------------------------------------------+----------+-------------------------------------------+
| ``authorization_uri``   | The URL of the OAuth2 Authorization       | Yes      | https://contoso.com/api/oauth2/authorize  |
|                         | Endpoint to begin authentication          |          |                                           |
|                         | against as described at:                  |          |                                           |
|                         | :rfc:`6749#section-3.1`                   |          |                                           |
+-------------------------+-------------------------------------------+----------+-------------------------------------------+
| ``tokenIssuance_uri``   | The URL of the OAuth2 Token Endpoint      | Yes      | https://contoso.com/api/oauth2/token      |
|                         | where authentication code can be          |          |                                           |
|                         | redeemed for an access and (optional)     |          |                                           |
|                         | refresh token. See Token EndPoint at:     |          |                                           |
|                         | :rfc:`6749#section-3.2`                   |          |                                           |
+-------------------------+-------------------------------------------+----------+-------------------------------------------+
| ``providerId``          | A well-known string (as registered with   | No       | TP_CONTOSO                                |
|                         | with Microsoft Office) that uniquely      |          |                                           |
|                         | identifies the host.                      |          |                                           |
|                         | Allowed characters: ``[a-z,A-Z,0-9]``     |          |                                           |
+-------------------------+-------------------------------------------+----------+-------------------------------------------+
| ``UrlSchemes``          | Information used to invoke your app       | No       | ..  literalinclude:: url_schemes.json     |
|                         | (despite the name of the parameter, this  |          |                                           |
|                         | may not always be URL schemes; e.g. on    |          |                                           |
|                         | Android, intent is used).                 |          |                                           |
|                         |                                           |          |                                           |
|                         | This is an ordered list by platform.      |          |                                           |
|                         | Omit any platforms you do not support.    |          |                                           |
|                         | Office will attempt to invoke these in    |          |                                           |
|                         | order before falling back to the          |          |                                           |
|                         | WebView auth.                             |          |                                           |
|                         |                                           |          |                                           |
+-------------------------+-------------------------------------------+----------+-------------------------------------------+


Client-side Changes
~~~~~~~~~~~~~~~~~~~

Invoking your App on Android

1.  Office will create an intent, which will take the package name and auth activity name of your App. We will set
    following two extras to intent:

    * ``AuthorizeUrlQueryParams``: It is exactly same as used in iOS without the ``action`` parameter.
      e.g.: ``client_id=abcdefg&response_type=code&scope=wopi&rs=enUS&build=16.1.1234&platform=android&app=word``
    * ``UserId``: It will be an optional parameter and will be set whenever we will have it. Third party should use to
      verify that the sign in requested for the User signed in to their App.

..  literalinclude:: ../../../../samples/android/App2AppSigninIntent.java
    :caption: Sample intent creation code from `App2AppSigninIntent.java`__
    :language: java
    :linenos:
    :lineno-match:
    :dedent: 4
    :lines: 1-8

__  https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/android/App2AppSigninIntent.java

2.  After this Office will wait for result and will expect following from third party App

    * ``ResponseUrlQueryParams``: Again this is exactly same as what we are getting in iOS minus the ``action``
      parameter. The following are values of it in success and failure cases:

      * ``code=abcdefg&tk=http://contoso.com&sc=xyz`` [during RESULT_OK]
      * ``error=invalid_request&error_description="optional human readable message"`` [during RESULT_CANCELLED or
        anything else]

    * ``UserId``: Third party should send which user is authenticated by it. Office will use it to show error in case
      ``UserId`` in request and response mismatch.

..  literalinclude:: ../../../../samples/android/App2AppSigninIntent.java
    :caption: Sample code from `App2AppSigninIntent.java`__
    :language: java
    :linenos:
    :lineno-match:
    :dedent: 4
    :lines: 10-35

__  https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/android/App2AppSigninIntent.java


This is the work which your App needs to do

3. Add an intent filter to AndroidManifest.xml

..  literalinclude:: ../../../../samples/android/AndroidManifest.xml
    :caption: Add an intent filter to `AndroidManifest.xml`__
    :language: xml
    :linenos:
    :lineno-match:
    :dedent: 4
    :lines: 1-7

__  https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/android/AndroidManifest.xml

4. Handle the intent in AuthActivity

..  literalinclude:: ../../../../samples/android/HandleIntent.java
    :caption: Handle the intent using sample code from `HandleIntent.java`__
    :language: java
    :linenos:
    :lineno-match:
    :dedent: 4
    :lines: 2-9

__  https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/android/HandleIntent.java

5. Returning result

..  literalinclude:: ../../../../samples/android/HandleIntent.java
    :caption: Return the result using sample code from `HandleIntent.java`__
    :language: java
    :linenos:
    :lineno-match:
    :dedent: 4
    :lines: 11-18

__  https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/android/HandleIntent.java

More details here https://developer.android.com/training/basics/intents/filters.html#ReturnResult
