
..  index:: WOPI requests; RefreshLock, RefreshLock

..  _RefreshLock:

RefreshLock
===========

..  default-domain:: http

..  post:: /wopi*/files/(id)

    The RefreshLock operation refreshes the lock on a file by resetting its automatic expiration timer to 30 minutes.
    The refreshed lock must expire automatically after 30 minutes unless it is modified by a subsequent WOPI
    operation, such as :ref:`Unlock` or :ref:`RefreshLock`.

    ..  include:: /fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``REFRESH_LOCK``. Required.
    :reqheader X-WOPI-Lock:
        A **string** provided by Office Online that the host must use to identify the lock on
        the file. The maximum length of a lock ID is 256 characters.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 409: Target file already exists
    :code 500: Server error
    :code 501: Unsupported
