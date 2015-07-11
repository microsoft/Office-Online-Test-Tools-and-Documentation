
An optional **string** value indicating the cause of a lock failure. This header may be included when
responding to the request with :http:statuscode:`409`. There is no standard for how this string is
formatted, and it must only be used for logging purposes.

..  tip::

    Hosts are recommended to use short strings that are consistent. This allows WOPI clients such as Office Online to
    easily report to hosts how often locks are failing due to particular reasons.
