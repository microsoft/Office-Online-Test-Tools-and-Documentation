
|Office iOS| OAuth2 sign-in URL parameters
==========================================

The HTTPS request to load the OAuth2 Authorization page (:rfc:`6749#section-3.1`) includes standard OAuth2 parameters 
such as ``client_id``, ``redirect_uri``, etc. but may also include Office-specific parameters, documented here.

    ..  important:: Early releases of |Office iOS| may not include some or all of these URL parameters, so callers should fall back to reasonable defaults.

Culture
-------

The request includes the :http:header:`Accept-Language` header. The value of this header is determined by the iOS 
language settings.  In addition, the following URL parameter is appended to the request URL::

    rs=Culture

Culture contains the UI culture of the Office interface, e.g. ``en-US``.

Build
-----

The following URL parameter is appended to the request URL::

    Build=#

"#" will be a string showing the build of the Office application making the request.

Platform
--------

The Platform URL parameter communicates which platform the app is running on.

	Platform=iOS

Example
-------

Here's a request to the sign-in endpoint showing all parameters in use::

    https://contoso.com/api/oauth2/authorize?&rs=en-US&Build=1.21.417&Platform=iOS&client_id=abcdefg&redirect_uri=https%3A%2F%2Flocalhost&response_type=code

