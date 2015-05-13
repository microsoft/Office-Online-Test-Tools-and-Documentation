
..  index:: WOPI requests; Unlock, Unlock

..  _Unlock:

Unlock
======

..  default-domain:: http

..  post:: /wopi*/files/(id)

    The Unlock operation releases the lock on a file.

    ..  include:: /fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``UNLOCK``. Required.
    :reqheader X-WOPI-Lock:
        A **string** provided by Office Online that the host must use to identify the lock on
        the file. The maximum length of a lock ID is 256 characters.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 409: Target file already exists
    :code 500: Server error
    :code 501: Unsupported
