
..  index:: WOPI requests; PutFile, PutFile

..  _PutFile:

PutFile
=======

..  default-domain:: http

..  post:: /wopi*/files/(file_id)/contents

    The PutFile operation updates a file's binary contents.

    ..  include:: /_fragments/priorlock.rst

    During :ref:`document creation<Create New>`, Office Online will make a PutFile request without a prior
    :ref:`Lock` request. Thus, when a host receives a PutFile request on a file that is not locked, the host must
    check the current size of the file. If it is 0 bytes, the PutFile request should be considered valid and should
    proceed. If it is any value other than 0 bytes, or is missing altogether, the host should respond with a
    :http:statuscode:`409`. For more information, see :ref:`Create New`.

    If the file is currently locked and the **X-WOPI-Lock** value does not match the lock currently on the file the
    host must return a "lock mismatch" response (:http:statuscode:`409`) and include an **X-WOPI-Lock** response
    header containing the value of the current lock on the file. In the case where the file is unlocked, the host
    must set **X-WOPI-Lock** to the empty string.

    ..  include:: /_fragments/no_lock_id.rst


    ..  include:: /_fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``PUT``. Required.
    :reqheader X-WOPI-Lock:
        A **string** provided by Office Online in a previous :ref:`Lock` request. Note that this header will not be
        included during :ref:`document creation<Create New>`.

    ..  include:: /_fragments/common_lock_responses.rst

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 409: Lock mismatch/locked by another interface; an **X-WOPI-Lock** response header containing the value of
        the current lock on the file must always be included when using this response code
    :code 413: File is too large; the maximum file size is host-specific
    :code 500: Server error
    :code 501: Unsupported

    ..  include:: /_fragments/common_headers.rst
