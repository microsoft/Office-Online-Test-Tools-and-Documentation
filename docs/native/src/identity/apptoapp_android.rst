
.. _app to app android:

App to app sign in for |Office Android|
=======================================

The normal flow to sign in to your service from Office uses the OS provided WebView where your web sign in experience 
is rendered inside the Office app. Optionally, an additional optimization can be done where the user can sign in using 
your app.

The overall flow involves:

    1. Office invoking your app
    2. Through your app signin to your service as needed
    3. Your app providing desired info (Authcode/token) to Office when signin is complete
 
Signin using your app
---------------------
* When sign in to your service is required (e.g. first time a file is opened from your app into Office, or when the user 
explicitly adds your service as a place in Office), Office calls your bootstrapper to obtain your authorization_uri, which it 
displays in a UIWebView. In addition to the authorization_uri, you should return information needed to invoke your App. This 
information is platform specific.
* Office will first attempt to use provided information to invoke your app. If none are returned, or if none of them are 
registered (i.e. your app is not installed), Office will fall back to the UIWebView.
* If the user does have your app installed, your app will be invoked and the user will be sent to your app to complete 
authentication.  If the user declines to open your app, Office will fall back to the UIWebView.
* Once inside your app, you should do whatever is needed to obtain the user's auth code (e.g. display "Grant permission 
to Office" dialog, for example, or to ask for additional signins).
* Return the user to the Office app with the auth code.

Service Changes: Adding information to the WWW-Authenticate response header on unauthenticated bootstrap requests
-----------------------------------------------------------------------------------------------------------------

|Office Android| will use Intent to invoke your App. The information Office needs are your App's Package name, Auth activity name and Version code. Office will consider provided Version code as the base version and will assume that your App with this Version and above will support App to App authentication.
The information Office needs is passed via the URLScheme parameter in the WWW-Authenticate response header to unauthenticated bootstrap request.

+-------------------------+-------------------------------------------+----------+-------------------------------------------+
|Parameter                | Value                                     | Required | Example                                   |
+=========================+===========================================+==========+===========================================+
| Bearer                  | n/a                                       | Yes      | Bearer                                    |
+-------------------------+-------------------------------------------+----------+-------------------------------------------+
| authorization_uri       | The URL of the OAuth2 Authorization       | Yes      | https://contoso.com/api/oauth2/authorize  |
|                         | Endpoint to begin authentication          |          |                                           |
|                         | against as described at:                  |          |                                           |
|                         | :rfc:`6749#section-3.1`                   |          |                                           |
+-------------------------+-------------------------------------------+----------+-------------------------------------------+
| tokenIssuance_uri       | The URL of the OAuth2 Token Endpoint      | Yes      | https://contoso.com/api/oauth2/token      |
|                         | where authentication code can be          |          |                                           |
|                         | redeemed for an access and (optional)     |          |                                           |
|                         | refresh token. See Token EndPoint at:     |          |                                           |
|                         | :rfc:`6749#section-3.2`                   |          |                                           |
+-------------------------+-------------------------------------------+----------+-------------------------------------------+
| providerId              | A well-known string (as registered with   | No       | TP_CONTOSO                                |
|                         | with Microsoft Office) that uniquely      |          |                                           |
|                         | identifies the host.                      |          |                                           |
|                         | Allowed characters: ``[a-z,A-Z,0-9]``     |          |                                           |
+-------------------------+-------------------------------------------+----------+-------------------------------------------+
| UrlSchemes              | Information used to invoke your app       | No       | {                                         |
|                         | (despite the name of the parameter, this  |          |   "iOS": [                                |
|                         | may not always be URL schemes; e.g. on    |          |    "contoso",                             |
|                         | Android, intent is used).                 |          |    "contoso-EMM"                          |
|                         |                                           |          |   ],                                      |
|                         | This is an ordered list by platform.      |          |   "Android": [                            |
|                         | Omit any platforms you do not support.    |          |     "Package1VersionCode",                |
|                         | Office will attempt to invoke these in    |          |     "Package1Name",                       |
|                         | order before falling back to the          |          |     "Package1AuthActivityName",           |
|                         | webview auth.                             |          |     "Package2VersionCode",                |
|                         |                                           |          |     "Package2Name",                       |
|                         |                                           |          |     "Package2AuthActivityName"            |
|                         |                                           |          |    ],                                     |
|                         |                                           |          |    "UWP": [...]                           |
|                         |                                           |          | }                                         |
|                         |                                           |          |                                           |
+-------------------------+-------------------------------------------+----------+-------------------------------------------+


Client Changes: Invoking your App on Android
--------------------------------------------

1.  Office will create an intent, which will take the package name and auth activity name of your App. We will set
    following two extras to intent:

    * AuthorizeUrlQueryParams: It is exactly same as used in iOS minus action param.
      e.g.: ``client_id=abcdefg&response_type=code&scope=wopi&rs=enUS&build=16.1.1234&platform =android&app=word``
    * UserId: It will be an optional param and will be set whenever we will have it. Third party should use to
      verify that the sign-is requested for the User signed-in to their App.

..  literalinclude:: ../../../../samples/android/App2AppSigninIntent.java
    :caption: Sample code from `App2AppSigninIntent.java`_
    :language: java
    :linenos:
    :lineno-match:
    :dedent: 8
    :lines: 1-8

2.  After this Office will wait for result and will expect following from third party App

    * ResponseUrlQueryParams: Again this is exactly same as what we are getting in iOS minus action param, following are values of it in success and failure

      * ``code=abcdefg&tk=http://contoso.com&sc=xyz`` [during RESULT_OK]
      * ``error=invalied_request&error_decstption="optional human readable message"`` [during RESULT_CANCELLED or
        anything else]

    * UserId: Third party should send which user is authenticated by it. Office will use it to show error in case Userid in request and response mismatch.

..  literalinclude:: ../../../../samples/android/App2AppSigninIntent.java
    :caption: Sample code from `App2AppSigninIntent.java`_
    :language: java
    :linenos:
    :lineno-match:
    :dedent: 8
    :lines: 10-35

..  _App2AppSigninIntent.java: https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/android/App2AppSigninIntent.java


This is the work which your App needs to do

3. Add an intent filter to AndroidManifest.xml

..  literalinclude:: ../../../../samples/android/AndroidManifest.xml
    :caption: Sample code from `AndroidManifest.xml`_
    :language: xml
    :linenos:
    :lineno-match:
    :dedent: 8
    :lines: 1-7

..  _AndroidManifest.xml: https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/android/AndroidManifest.xml

 4. Handle the intent in AuthActivity

..  literalinclude:: ../../../../samples/android/HandleIntent.java
    :caption: Sample code from `HandleIntent.java`_
    :language: xml
    :linenos:
    :lineno-match:
    :dedent: 8
    :lines: 1-7

5. Returning result

..  literalinclude:: ../../../../samples/android/HandleIntent.java
    :caption: Sample code from `HandleIntent.java`_
    :language: xml
    :linenos:
    :lineno-match:
    :dedent: 8
    :lines: 12-19

..  _HandleIntent.java: https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/android/HandleIntent.java

    More details here https://developer.android.com/training/basics/intents/filters.html#ReturnResult
