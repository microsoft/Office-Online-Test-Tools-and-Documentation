
:resheader X-WOPI-Lock:
    A **string** value identifying the current lock on the file. This header must always be included when
    responding to the request with :http:statuscode:`409`. It should not be included when responding to the
    request with :http:statuscode:`200`.

:resheader X-WOPI-LockFailureReason:
    An optional **string** value indicating the cause of the lock failure. This header may be included when
    responding to the request with :http:statuscode:`409`. There is no standard for how this string is
    formatted, and Office Online only uses it for logging purposes. However, we recommend hosts use small strings
    that are consistent. This allows Office Online to easily report to hosts how often locks are failing due to
    particular reasons.

:resheader X-WOPI-LockedByOtherInterface:
    An optional **string** value indicating that the file is currently locked by someone other than Office Online.
    This header is optional, and is only used by Office Online to provide more specific messages to users when
    operations fail. If set, the value of this header must be the string ``true``.
