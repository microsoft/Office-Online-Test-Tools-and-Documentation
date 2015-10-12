
..  index:: WOPI requests; Lock, Lock

..  _Lock:

Lock
====

..  default-domain:: http

..  post:: /wopi*/files/(file_id)

    The Lock operation locks a file for editing by the Office Online application instance that requested the lock.
    To support editing files, Office Online requires that the WOPI host support locking files. When locked, a file
    should not be writable by other applications, including Office Online.

    If the file is currently unlocked, the host should lock the file and return :http:statuscode:`200`.

    If the file is currently locked and the **X-WOPI-Lock** value matches the lock currently on the file, the host
    should treat the request as if it is a :ref:`RefreshLock` request. That is, the host should refresh the lock
    timer and return :http:statuscode:`200`.

    In all other cases, the host must return a "lock mismatch" response (:http:statuscode:`409`) and include an
    **X-WOPI-Lock** response header containing the value of the current lock on the file.

    ..  include:: /fragments/no_lock_id.rst

    See :term:`Lock` for more general information regarding locks.


    ..  include:: /fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``LOCK``. Required.
    :reqheader X-WOPI-Lock:
        A **string** provided by Office Online that the host must use to identify the lock on
        the file. Required.

    ..  include:: /fragments/common_lock_responses.rst


    :code 200: Success
    :code 400: X-WOPI-Lock was not provided or was empty
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 409: Lock mismatch/locked by another interface; an **X-WOPI-Lock** response header containing the value of
        the current lock on the file must always be included when using this response code
    :code 500: Server error
    :code 501: Unsupported

    ..  include:: /fragments/common_headers.rst
