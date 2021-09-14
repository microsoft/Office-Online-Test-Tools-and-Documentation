
.. meta::
    :robots: noindex

..  index:: WOPI requests; Lock, Lock

..  |operation| replace:: Lock

..  _Lock:

Lock
====

:Required for: |web| |ios| |android|

..  default-domain:: http

..  post:: /wopi/files/(file_id)

    The |operation| operation locks a file for editing by the WOPI client application instance that requested the
    lock. To support editing files, WOPI clients require that the WOPI host support locking files. When locked, a file
    should not be writable by other applications.

    If the file is currently unlocked, the host should lock the file and return :http:statuscode:`200`.

    If the file is currently locked and the **X-WOPI-Lock** value matches the lock currently on the file, the host
    should treat the request as if it is a :ref:`RefreshLock` request. That is, the host should refresh the lock
    timer and return :http:statuscode:`200`.

    In all other cases, the host must return a "lock mismatch" response (:http:statuscode:`409`) and include an
    **X-WOPI-Lock** response header containing the value of the current lock on the file.

    ..  include:: /_fragments/no_lock_id.rst

    See :term:`Lock` for more general information regarding locks.


    ..  include:: /_fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``LOCK``. Required.
    :reqheader X-WOPI-Lock:
        A **string** provided by the WOPI client that the host must use to identify the lock on
        the file. Required.

    :resheader X-WOPI-Lock:
        ..  include:: /_fragments/headers/X-WOPI-Lock.rst

    :resheader X-WOPI-LockFailureReason:
        ..  include:: /_fragments/headers/X-WOPI-LockFailureReason.rst

    :resheader X-WOPI-LockedByOtherInterface:
        ..  include:: /_fragments/headers/X-WOPI-LockedByOtherInterface.rst

    :resheader X-WOPI-ItemVersion:
        ..  include:: /_fragments/headers/X-WOPI-ItemVersion.rst


    :code 200: Success
    :code 400: **X-WOPI-Lock** was not provided or was empty
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 409: Lock mismatch/locked by another interface; an **X-WOPI-Lock** response header containing the value of
        the current lock on the file must always be included when using this response code
    :code 500: Server error
    :code 501: Operation not supported

    ..  include:: /_fragments/common_headers.rst
