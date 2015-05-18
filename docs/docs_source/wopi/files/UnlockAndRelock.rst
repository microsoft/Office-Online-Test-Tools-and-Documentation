
..  index:: WOPI requests; UnlockAndRelock, UnlockAndRelock

..  _UnlockAndRelock:

UnlockAndRelock
===============

..  default-domain:: http

..  post:: /wopi*/files/(id)

The UnlockAndRelock operation releases a lock on a file, and then immediately takes a new lock on the file.
**This operation must be atomic.**

Office Online passes the value of the existing lock in the **X-WOPI-OldLock** header. The host should verify that the
value provided in the **X-WOPI-OldLock** header matches the current lock on the file, and if not, respond with a
"lock mismatch" response (:http:statuscode:`409`). If it does match, the host should release the lock and immediately
take a new lock with the lock ID provided in the **X-WOPI-Lock** header.

..  tip::
    The UnlockAndRelock operation is very similar to the :ref:`Lock` operation. The two operations share the same
    **X-WOPI-Override** value. Thus, hosts must differentiate the two operations based on the presence, or lack of,
    the **X-WOPI-OldLock** request header.

    ..  include:: /fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``LOCK``. Required.
    :reqheader X-WOPI-Lock:
        A **string** provided by Office Online that the host must use to identify the lock on
        the file. The maximum length of a lock ID is 256 characters.
    :reqheader X-WOPI-OldLock:
        A **string** provided by Office Online that is the existing lock on the file.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 409: Target file already exists
    :code 500: Server error
    :code 501: Unsupported
