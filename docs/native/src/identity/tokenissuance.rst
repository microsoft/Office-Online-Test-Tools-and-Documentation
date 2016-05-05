
Token Issuance URL requirements
===============================

This section describes requirements for your token issuance endpoint (:rfc:`6749#section-3.2`):

* :http:header:`Content-Type` header must be set to ``application/json``
* The OAuth2 access token expiration should be specified via the ``expires_in`` property, in seconds.
* If your access tokens do not expire, set it to a value of 0 (zero).


Example Response Body::

    HTTP/1.1 200 OK
    Content-Type: application/json;charset=UTF-8
    
    {
        "access_token":"123dfevewafe",
        "token_type": "bearer",
        "expires_in": "86400"
    }
