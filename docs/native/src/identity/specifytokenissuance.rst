
Post-Authorization token endpoint
=================================

The token endpoint URL (:rfc:`6749#section-3.2`) is normally obtained from the initial unauthenticated call 
described at :ref:`ClientBootstrapper`.

If the token endpoint URL cannot be determined before the end-user has completed the sign-in process, 
an alternative token endpoint URL may be supplied.

This is done via a tk= URL parameter appended to the value of the
:http:header:`Location` header from the :http:statuscode:`302` response at the end of the sign-in flow.

    ..  important:: The contents of the tk= parameter must be URL encoded.

For example, to return the following information:

* Redirection URI is \https://localhost
* Authorization code (:rfc:`6749#section-4.1.2`) is “abcdefg”
* Token endpoint URL is \https://contoso.com/api/token/?extra=stuff

The :http:header:`Location` header in the :http:statuscode:`302` response would be:

Location: \https://localhost?code=abcdefg&tk=https%3A%2F%2Fcontoso.com%2Fapi%2Ftoken%2F%3Fextra%3Dstuff

As a result, all calls to the token endpoint for obtaining access token via authentication-code exchange, or refresh
flows using the refresh token, will hit this URL instead of the one initially returned as described at :ref:`ClientBootstrapper`.
