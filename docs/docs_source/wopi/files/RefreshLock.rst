
..  index:: WOPI requests; RefreshLock, RefreshLock

..  _RefreshLock:

RefreshLock
===========

..  default-domain:: http

..  post:: /wopi*/files/(file_id)

    The RefreshLock operation refreshes the lock on a file by resetting its automatic expiration timer to 30 minutes.
    The refreshed lock must expire automatically after 30 minutes unless it is modified by a subsequent WOPI
    operation, such as :ref:`Unlock` or :ref:`RefreshLock`.

    ..  include:: /fragments/priorlock.rst

    ..  include:: /fragments/lock409.rst

    ..  include:: /fragments/no_lock_id.rst

    See :term:`Lock` for more general information regarding locks.


    ..  include:: /fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``REFRESH_LOCK``. Required.
    :reqheader X-WOPI-Lock:
        A **string** provided by Office Online that the host must use to identify the existing lock on
        the file.

    ..  include:: /fragments/common_lock_responses.rst

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 409: Lock mismatch/locked by another interface; an **X-WOPI-Lock** response header containing the value of
        the current lock on the file must always be included when using this response code
    :code 500: Server error
    :code 501: Unsupported

    ..  include:: /fragments/common_headers.rst
