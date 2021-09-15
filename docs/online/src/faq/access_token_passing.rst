
.. meta::
    :robots: noindex

|wac| sometimes sends the access token in the query string when making requests to the service. Is this a problem?
==================================================================================================================

If you monitor the traffic between the |wac| browser applications and the |wac| service, you may notice that some
requests include the WOPI access token on the URL. We recognize that this is an issue and have designed our service
|wac| logging infrastructure to deal with it. We scrub the query string from URLs before they are written to our
logs. We have built a scrubber module into our standard server logging system that finds and redacts the
access token in the two or three different forms it can take in a URL.

We believe that zero access tokens are currently being written to the |wac| service logs, and we consider it an urgent
bug if we discover that they are.
