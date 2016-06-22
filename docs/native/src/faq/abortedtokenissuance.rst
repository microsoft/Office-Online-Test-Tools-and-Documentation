Why does calling the Token Issuance URL return "The request was aborted: Could not create SSL/TLS secure channel"?
==================================================================================================================
Please configure the NetScaler to just load balance TCP/443 instead of using the built-in SSL protocol load balancing. 
This should keep the load balancer from breaking the SSL communications. For more information, please see `this article <https://directaccessguide.com/2014/06/01/getting-ip-https-error-code-0x80090326/>`_. 