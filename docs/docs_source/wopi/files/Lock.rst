
..  index:: WOPI requests; Lock, Lock

..  _Lock:

Lock
====

..  default-domain:: http

..  post:: /wopi*/files/(id)

    The Lock operation locks a file for editing by the Office Online application instance that requested the lock.
    To support editing files, Office Online requires that the WOPI host support locking files. When locked, a file
    should not be writable by other applications, including Office Online.

    Office Online requires that the host store a string value associated with the locked file. This string value is the
    lock ID. Office Online will always pass the lock ID as a parameter to operations that would modify the
    contents of a file, such as :ref:`PutFile`. The host must ensure that a "lock mismatch" response
    (:http:statuscode:`409`) is returned if the lock passed does not match the lock currently on the file.

    The host should implement locks in such a way that applications other than Office Online do not edit the file while
    it is locked by another editor.

    Locks should expire automatically after 30 minutes. Office Online can reset this timeout by means of a
    :ref:`RefreshLock` request.

    ..  todo:: :issue:`12`

    ..  include:: /fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``LOCK``. Required.
    :reqheader X-WOPI-Lock:
        A **string** provided by Office Online that the host must use to identify the lock on
        the file. The maximum length of a lock ID is 256 characters.

    :resheader X-WOPI-OldLock:
        A **string** provided by Office Online that the host must use to identify the lock on
        the file.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 409: Target file already exists
    :code 500: Server error
    :code 501: Unsupported
