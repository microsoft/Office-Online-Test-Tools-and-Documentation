
.. meta::
    :robots: noindex

Optional Session Context String
===============================

A Session Context String may be returned from the authentication process. |Office iOS| will pass this string, in an HTTP header, in calls to the
token endpoint URL (:rfc:`6749#section-3.2`) and authenticated calls to the bootstrapper (:ref:`wopirest:GetNewAccessToken`, :ref:`wopirest:shortcuts`).

The Session Context String is optional, and for the use of the storage provider. A possible scenario would be to include a hint about
a "tenant" so endpoints can know where they need to fetch and/or validate tokens.

Returning a Session Context string is done via an ``sc=`` URL parameter appended to the value of the
:http:header:`Location` header from the :http:statuscode:`302` response at the end of the sign-in flow.

    ..  important:: The contents of the ``sc=`` parameter must be URL encoded.

For example, to return the following information:

* Redirection URI is \https://localhost
* Authorization code (:rfc:`6749#section-4.1.2`) is "abcdefg"
* Session Context String is "hello:World"

The :http:header:`Location` header in the :http:statuscode:`302` response would be::

    Location: https://localhost/?code=abcdefg&sc=hello%3AWorld

If present, the session context string will be included as an HTTP header when calls are made to
the token exchange endpoint, and OAuth2 authenticated calls to the bootstrapper
(:ref:`wopirest:GetNewAccessToken`, :ref:`wopirest:shortcuts`) as follows::

    X-WOPI-SessionContext: hello:World
