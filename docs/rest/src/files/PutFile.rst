
..  index:: WOPI requests; PutFile, PutFile

..  |operation| replace:: PutFile

..  _PutFile:

PutFile
=======

:Required for: |web| |ios| |android|

..  default-domain:: http

..  post:: /wopi/files/(file_id)/contents

    The |operation| operation updates a file's binary contents.

    ..  include:: /_fragments/priorlock.rst

    When a host receives a |operation| request on a file that is not locked, the host must check the current size of the
    file. If it is 0 bytes, the |operation| request should be considered valid and should proceed. If it is any value
    other than 0 bytes, or is missing altogether, the host should respond with a :http:statuscode:`409`. For more
    information, see :ref:`Create New`.

    If the file is currently locked and the **X-WOPI-Lock** value does not match the lock currently on the file the
    host must return a "lock mismatch" response (:http:statuscode:`409`) and include an **X-WOPI-Lock** response
    header containing the value of the current lock on the file. In the case where the file is unlocked, the host
    must set **X-WOPI-Lock** to the empty string.

    ..  include:: /_fragments/no_lock_id.rst


    ..  include:: /_fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``PUT``. Required.
    :reqheader X-WOPI-Lock:
        A **string** provided by the WOPI client in a previous :ref:`Lock` request. Note that this header will not be
        included during :ref:`document creation<Create New>`.

    :request Body: The request body must be the full binary contents of the file.

    :resheader X-WOPI-Lock:
        ..  include:: /_fragments/headers/X-WOPI-Lock.rst

    :resheader X-WOPI-LockFailureReason:
        ..  include:: /_fragments/headers/X-WOPI-LockFailureReason.rst

    :resheader X-WOPI-LockedByOtherInterface:
        ..  include:: /_fragments/headers/X-WOPI-LockedByOtherInterface.rst

    :resheader X-WOPI-ItemVersion:
        ..  include:: /_fragments/headers/X-WOPI-ItemVersion.rst

        ..  tip:: For PutFile responses, this should be the version of the file *after* the PutFile operation.


    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 409: Lock mismatch/locked by another interface; an **X-WOPI-Lock** response header containing the value of
        the current lock on the file must always be included when using this response code
    :code 413: File is too large; the maximum file size is host-specific
    :code 500: Server error
    :code 501: Operation not supported

    ..  include:: /_fragments/common_headers.rst
