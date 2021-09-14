
.. meta::
    :robots: noindex

..  _ClientBootstrapper:

Bootstrapping OAuth2
====================


An **unauthenticated** request to the bootstrapper endpoint is the first step for the OAuth2 flow from |Office iOS| to your application.

**Unauthenticated** means no access token is attached in the :http:header:`Authorization` HTTP header, or an expired or otherwise invalid token is attached.

..  important:: The bootstrapper URL must be an HTTPS endpoint, and connections to the bootstrapper must be made using :abbr:`TLS (Transport Layer Security)`.

..  important:: The bootstrapper URL must be supplied as the BootstrapUrl property of the on-boarding information described in the section titled :ref:`onboarding`.

For full details, see the page :ref:`wopirest:unauthenticated response`.
