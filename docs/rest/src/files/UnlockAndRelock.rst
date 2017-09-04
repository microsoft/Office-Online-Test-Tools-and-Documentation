
..  index:: WOPI requests; UnlockAndRelock, UnlockAndRelock

..  |operation| replace:: UnlockAndRelock

..  _UnlockAndRelock:

UnlockAndRelock
===============

:Required for: |web| |ios| |android|

..  default-domain:: http

..  post:: /wopi/files/(file_id)

    The |operation| operation releases a lock on a file, and then immediately takes a new lock on the file.

    ..  important:: This operation must be atomic.

    |operation| is very similar semantically to the :ref:`Lock` operation. The two operations share the same
    **X-WOPI-Override** value. Thus, hosts must differentiate the two operations based on the presence, or lack of,
    the **X-WOPI-OldLock** request header.

    Unlike the :ref:`Lock` operation, the |operation| operation passes the current expected lock ID in the
    **X-WOPI-OldLock** request header. The **X-WOPI-Lock** value is the lock ID for the new lock.

    If the file is currently locked and the **X-WOPI-OldLock** value does not match the lock currently on the file,
    or if the file is unlocked, the host must return a "lock mismatch" response (:http:statuscode:`409`) and include an
    **X-WOPI-Lock** response header containing the value of the current lock on the file. In the case where the file is
    unlocked, the host must set **X-WOPI-Lock** to the empty string.

    ..  include:: /_fragments/no_lock_id.rst

    See :term:`Lock` for more general information regarding locks.


    ..  include:: /_fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``LOCK``. Required.
    :reqheader X-WOPI-Lock:
        A **string** provided by the WOPI client that the host must use to identify the new lock on
        the file. The maximum length of a lock ID is 1024 ASCII characters. Required.
    :reqheader X-WOPI-OldLock:
        A **string** provided by the WOPI client that is the existing lock on the file. Required.
        Note that if **X-WOPI-OldLock** is not provided, the request is identical to a :term:`Lock` request.

    :resheader X-WOPI-Lock:
        ..  include:: /_fragments/headers/X-WOPI-Lock.rst

    :resheader X-WOPI-LockFailureReason:
        ..  include:: /_fragments/headers/X-WOPI-LockFailureReason.rst

    :resheader X-WOPI-LockedByOtherInterface:
        ..  include:: /_fragments/headers/X-WOPI-LockedByOtherInterface.rst


    :code 200: Success
    :code 400: **X-WOPI-Lock** was not provided or was empty
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 409: Lock mismatch/locked by another interface; an **X-WOPI-Lock** response header containing the value of
        the current lock on the file must always be included when using this response code
    :code 500: Server error
    :code 501: Operation not supported

    ..  include:: /_fragments/common_headers.rst
