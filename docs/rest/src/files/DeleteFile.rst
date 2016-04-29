..  index:: WOPI requests; DeleteFile, DeleteFile

..  |operation| replace:: DeleteFile

..  _DeleteFile:

DeleteFile
==========

:Required for: |ios|

..  default-domain:: http

..  post:: /wopi/files/(file_id)

    The |operation| operation deletes a file from a host.

    If the file is currently locked, the host should return a :http:statuscode:`409` and include an **X-WOPI-Lock**
    response header containing the value of the current lock on the file. If the current lock ID is not representable
    as a WOPI lock (for example, it is longer than the :ref:`maximum lock length <lock length>`), the host should
    return a :http:statuscode:`409` and set the **X-WOPI-Lock** response header to the empty string or omit
    it completely.

    ..  include:: /_fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``DELETE``. Required.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 409: Lock mismatch/locked by another interface; an **X-WOPI-Lock** response header containing the value of
        the current lock on the file must always be included when using this response code
    :code 500: Server error
    :code 501: Unsupported

    ..  include:: /_fragments/common_headers.rst
