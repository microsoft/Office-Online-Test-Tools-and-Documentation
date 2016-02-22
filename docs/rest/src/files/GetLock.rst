
..  index:: WOPI requests; GetLock, GetLock

..  |operation| replace:: GetLock

..  _GetLock:

GetLock
=======

..  default-domain:: http

..  post:: /wopi/files/(file_id)

    ..  admonition:: |wac| Tip

        This operation is not yet called by |wac|. It has been added to the WOPI protocol definition, and
        |wac| will call it in the future, but it does not currently.

    The |operation| operation retrieves a lock on a file. Note that this operation *does not create a new lock.* Rather,
    this operation always returns the current lock value in the **X-WOPI-Lock** response header. Because of this, its
    semantics differ slightly from the other lock-related operations.

    If the file is currently not locked, the host must return a :http:statuscode:`200` and include an **X-WOPI-Lock**
    response header set to the empty string.

    If the file is currently locked, the host should return a :http:statuscode:`200` and include an **X-WOPI-Lock**
    response header containing the value of the current lock on the file. If the current lock ID is not representable
    as a WOPI lock (for example, it is longer than the :ref:`maximum lock length <lock length>`), the host should
    return a :http:statuscode:`409` and set the **X-WOPI-Lock** response header to the empty string or omit
    it completely.

    ..  tip::
        While a :http:statuscode:`409` is technically a valid response to this operation, it is rarely needed in
        practice, and hosts should respond with a :http:statuscode:`200` in most cases.

    See :term:`Lock` for more general information regarding locks.


    ..  include:: /_fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``GET_LOCK``. Required.

    :resheader X-WOPI-Lock:
        A **string** value identifying the current lock on the file. Unlike other lock operations, this header is
        required when responding to the request with either :http:statuscode:`200` or :http:statuscode:`409`.

    :resheader X-WOPI-LockFailureReason:
        ..  include:: /_fragments/headers/X-WOPI-LockFailureReason.rst

    :resheader X-WOPI-LockedByOtherInterface:
        ..  include:: /_fragments/headers/X-WOPI-LockedByOtherInterface.rst


    :code 200: Success; an **X-WOPI-Lock** response header containing the value of the current lock on the file must
        always be included when using this response code
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 409: Lock mismatch/locked by another interface; an **X-WOPI-Lock** response header containing the value of
        the current lock on the file must always be included when using this response code
    :code 500: Server error
    :code 501: Unsupported

    ..  include:: /_fragments/common_headers.rst
