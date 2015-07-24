
..  index:: WOPI requests; PutFile, PutFile

..  _PutFile:

PutFile
=======

..  default-domain:: http

..  post:: /wopi*/files/(file_id)/contents

    The PutFile operation updates a file's binary contents.

    ..  include:: /fragments/priorlock.rst

    ..  include:: /fragments/lock409.rst

    ..  include:: /fragments/no_lock_id.rst

    During :ref:`document creation<Create New>`, Office Online will make a PutFile request without a prior
    :ref:`Lock` request. Thus, when a host receives a PutFile request on a file that is not locked, the host must
    check the current size of the file. If it is 0 bytes, the PutFile request should be considered valid and should
    proceed. If it is any value other than 0 bytes, or is missing altogether, the host should respond with a
    :http:statuscode:`409`. For more information, see :ref:`Create New`.


    ..  include:: /fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``PUT``. Required.
    :reqheader X-WOPI-Lock:
        A **string** provided by Office Online in a previous :ref:`Lock` request. Note that this header will not be
        included during :ref:`document creation<Create New>`.

    :resheader X-WOPI-Lock:
        A **string** value identifying the current lock on the file. This header should always be included when
        responding to the request with :http:statuscode:`409`.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 409: Lock mismatch/locked by another interface; an **X-WOPI-Lock** response header containing the value of
        the current lock on the file should always be included when using this response code
    :code 413: File is too large; the maximum file size is host-specific
    :code 500: Server error
    :code 501: Unsupported

    ..  include:: /fragments/common_headers.rst
