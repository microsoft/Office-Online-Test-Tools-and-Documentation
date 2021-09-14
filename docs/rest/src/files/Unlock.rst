
.. meta::
    :robots: noindex

..  index:: WOPI requests; Unlock, Unlock

..  |operation| replace:: Unlock

..  _Unlock:

Unlock
======

:Required for: |web| |ios| |android|

..  default-domain:: http

..  post:: /wopi/files/(file_id)

    The |operation| operation releases the lock on a file.

    ..  include:: /_fragments/priorlock.rst

    ..  include:: /_fragments/lock409.rst

    ..  include:: /_fragments/no_lock_id.rst

    See :term:`Lock` for more general information regarding locks.


    ..  include:: /_fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``UNLOCK``. Required.
    :reqheader X-WOPI-Lock:
        A **string** provided by the WOPI client that the host must use to identify the lock on
        the file. Required.

    :resheader X-WOPI-Lock:
        ..  include:: /_fragments/headers/X-WOPI-Lock.rst

    :resheader X-WOPI-LockFailureReason:
        ..  include:: /_fragments/headers/X-WOPI-LockFailureReason.rst

    :resheader X-WOPI-LockedByOtherInterface:
        ..  include:: /_fragments/headers/X-WOPI-LockedByOtherInterface.rst

    :resheader X-WOPI-ItemVersion:
        ..  include:: /_fragments/headers/X-WOPI-ItemVersion.rst


    :code 200: Success
    :code 400: **X-WOPI-Lock** was not provided or was empty
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 409: Lock mismatch/locked by another interface; an **X-WOPI-Lock** response header containing the value of
        the current lock on the file must always be included when using this response code
    :code 500: Server error
    :code 501: Operation not supported

    ..  include:: /_fragments/common_headers.rst
