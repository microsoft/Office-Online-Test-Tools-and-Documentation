
..  index:: WOPI requests; RenameContainer, RenameContainer

..  |operation| replace:: RenameContainer

..  _RenameContainer:

|operation|
===========

:Required for: |ios|

..  default-domain:: http

..  post:: /wopi/containers/(container_id)

    The |operation| operation renames a container.

    ..  include:: /_fragments/common_containers_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``RENAME_CONTAINER``. Required.

    :reqheader X-WOPI-RequestedName:
        A UTF-7 encoded **string** that is a container name. Required.

    :resheader X-WOPI-InvalidContainerNameError:
        A **string** describing the reason the |operation| operation could not be completed. This header should only be
        included when the response code is :http:statuscode:`400`. This string is only used for logging purposes.

    :code 200: Success
    :code 400: Specified name is illegal
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 409: Target container already exists
    :code 500: Server error
    :code 501: Unsupported

    ..  include:: /_fragments/common_headers.rst

Response
--------

..  include:: /_fragments/json_response_required.rst

Name
    The **string** name of the renamed container.
