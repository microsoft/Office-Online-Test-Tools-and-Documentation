.. _app to app: 


App to app sign in
================== 

The normal flow to sign in to your service from |Office iOS| uses the iOS UIWebView where your web sign in 
experience is rendered inside the |Office iOS| app. Optionally, an additional optimization can be done where the user can sign in using your app. 

The app to app flow involves |Office iOS| invoking your app through your app's URL scheme to sign in to your service, and your app 
invoking |Office iOS| when sign in is complete. 

Sign in using your app
----------------------

 * When signing in to your service is required (i.e. the first time a file is opened from your app into 
   Office, or when the user explicitly adds your service as a place in |Office iOS|), |Office iOS| calls your bootstrapper to obtain the 
   authorization_uri, which it displays in a UIWebView. In addition to the authorization_uri, you should return a set of UrlSchemes that can be used to 
   invoke your app.
 * |Office iOS| will first attempt to use the UrlSchemes to invoke your app. If none are returned, or if none of them are 
   registered (i.e. your app is not installed), |Office iOS| will fall back to the UIWebView.
 * If the user does have your app installed, your app will be invoked via the UrlSchemes, and the user will be sent to your 
   app to complete authentication. If the user declines to open your app, |Office iOS| will 
   fall back to the UIWebView.
 * Once inside your app, you should do whatever is needed to obtain the user's auth code (i.e. display “Grant permission 
   to Office” dialog, for example, or to ask for additional signins).
 * Return the user to the |Office iOS| app with the auth code via the Office URL scheme (see below) 
 
URL scheme design 
~~~~~~~~~~~~~~~~~

The data passed via the URL scheme is essentially the same as would be passed via authorization_uri. 

Here is an example of a normal authorization_uri with parameters added. The parameters are described in 
`RFC6749 <https://tools.ietf.org/html/rfc6749>`_. 


Invoking the signin screen::

    https://contoso.com/api/oauth2/authorize?client_id=abcdefg&redirect_uri=https%3A%2F%2Flocalhost&response_type=code&scope=&rs=en-US&Build=16.1.1234&Platform=iOS

Host's redirect URL that ends the oauth flow::

    https://localhost?state=&code=abcdefg&tk=https%3A%2F%2Fcontoso.com%2Fapi%2Ftoken%2F%3Fextra%3Dstuff 

The same parameters are passed via the URL Schemes, with the addition of “action”. 

|Office iOS| invoking your app (“To” URL)::
   
    Contoso:client_id=abcdefg&response_type=code&scope=wopi&rs=enUS&build=16.1.1234&platform=iOS&app=word&action=76d173ad-a43f-4e3c-a5e7-0e7276b4c624 

Your app invoking |Office iOS| (“Back” URL)::
   
    ms-word-tp:code=abcdefg&tk=http://contoso.com&sc=xyz&action=76d173ad-a43f-4e3c-a5e7-0e7276b4c624 

The values of the parameters should be URL encoded. 

If authentication fails, the error parameters per `RFC6749 <https://tools.ietf.org/html/rfc6749>`_ can also be passed back to |Office iOS| via the URL schemes, 
just as they can be passed back to |Office iOS| via the redirect URI in the UIWebView model. 

New URL schemes registered by |Office iOS|
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 

 * ms-word-tp 
 * ms-excel-tp
 * ms-powerpoint-tp 

These are for Word, Excel, and PowerPoint respectively. Use these to invoke |Office iOS| when the user is done with authentication on your side. 

Action parameter 
~~~~~~~~~~~~~~~~

Action is a string passed to your app that should be passed back to Office unchanged. 

