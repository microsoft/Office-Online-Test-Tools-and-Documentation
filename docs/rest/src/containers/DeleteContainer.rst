
.. meta::
    :robots: noindex

..  index:: WOPI requests; DeleteContainer, DeleteContainer

..  |operation| replace:: DeleteContainer

..  _DeleteContainer:

DeleteContainer
===============

:Required for: |ios| |android|

..  default-domain:: http

..  post:: /wopi/containers/(container_id)

    The |operation| operation deletes a :term:`container`.

    ..  include:: /_fragments/common_containers_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``DELETE_CONTAINER``. Required.

    :code 200: Success
    :code 404: Resource not found/user unauthorized
    :code 409: Container has child files/containers
    :code 500: Server error
    :code 501: Operation not supported

    ..  include:: /_fragments/common_headers.rst
