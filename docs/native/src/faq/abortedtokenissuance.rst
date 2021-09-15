
.. meta::
    :robots: noindex

Why does calling the Token Issuance URL return "The request was aborted: Could not create SSL/TLS secure channel"?
==================================================================================================================

If TLS 1.0, TLS 1.1, and TLS 1.2 in Advanced settings are enabled in the browser, but the app is failing to connect
to the Token Issuance URL, this can be caused by an issue with the load balancer changing the SSL stream between the
client and server. Please configure the load balancer to just load balance TCP/443 instead of using the built-in SSL
protocol load balancing. By changing the load balancing to just load balance TCP/443, this should keep the load
balancer from breaking the SSL communications.

..  seealso::

    For more information, please see
    `this article <https://directaccessguide.com/2014/06/01/getting-ip-https-error-code-0x80090326/>`_.
