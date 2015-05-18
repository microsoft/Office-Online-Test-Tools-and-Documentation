
..  index:: WOPI requests; PutFile, PutFile

..  _PutFile:

PutFile
=======

..  default-domain:: http

..  post:: /wopi*/files/(id)/contents

    The PutFile operation updates a file's binary contents.

    Office Online will always pass the lock ID in the **X-WOPI-Lock** request header. If the file is currently
    associated with a lock established by the :ref:`Lock` operation or the :ref:`UnlockAndRelock` operation, the
    host must ensure that a "lock mismatch" response (:http:statuscode:`409`) is returned if the lock passed does not
    match the lock currently on the file, or if the file has been locked by someone other than Office Online.

    ..  include:: /fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``PUT``. Required.
    :reqheader X-WOPI-Lock:
        A **string** provided by Office Online in a previous :ref:`Lock` request.
    :reqheader X-WOPI-Size:
        An **integer** specifying the size of the request body. Optional. The host should read the entire request
        body if this value is not set in the request.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 409: Lock mismatch/Locked by another interface
    :code 413: File is too large; the maximum file size is host-specific
    :code 500: Server error
    :code 501: Unsupported
