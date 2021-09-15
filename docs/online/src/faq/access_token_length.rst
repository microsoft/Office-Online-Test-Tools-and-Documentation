
.. meta::
    :robots: noindex

What is the maximum length of a WOPI access token?
==================================================

There's no enforced length limit on a WOPI :term:`access token`; however, the overall URL length limit for |wac| is
2000 characters, and the access token is included on some GET requests between the |wac| browser apps and the |wac|
service. This means that at some point, the access token can become so long that requests between the browser and the
service will fail, which manifests as failing sessions. |ppt-web| is particularly susceptible to this problem.
