
.. meta::
    :robots: noindex

Why does |wac| pass the access token in both the Authorization HTTP header and as a URL parameter?
==================================================================================================

|wac| passes the WOPI :term:`access token` both as a URL parameter (called ``access_token``) and in the
:http:header:`Authorization` header. This applies to all WOPI requests that originate from |wac|.

This is done primarily for compatibility reasons. Some host rely on the :http:header:`Authorization` header because
they are using an OAuth stack for creating and managing WOPI access tokens. Because WOPI does not define a way for
a host to indicate that they are using OAuth, |wac| passes the access token both ways for maximum compatibility.

..  tip::

    As a best practice, WOPI hosts should use the access token value from the URL parameter. This is the preferred
    way to pass access tokens, and not all WOPI clients will pass it in the :http:header:`Authorization` header.
