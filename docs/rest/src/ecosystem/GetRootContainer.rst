
..  index:: WOPI requests; GetRootContainer (ecosystem), GetRootContainer (ecosystem)

..  |operation| replace:: GetRootContainer

..  _GetRootContainer (ecosystem):
..  _GetRootContainer:

GetRootContainer (ecosystem)
============================

:Required for: |ios|

..  default-domain:: http

..  get:: /wopi/ecosystem/root_container_pointer

    The |operation| operation returns the :term:`root container`. A WOPI client can use this operation to get a
    reference to the root container, from which the client can call :ref:`EnumerateChildren` to navigate a container
    hierarchy.

    ..  include:: /_fragments/bootstrapper/shortcut_seealso.rst


    ..  include:: /_fragments/access_token_param.rst

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 500: Server error
    :code 501: Operation not supported

    ..  include:: /_fragments/common_headers.rst

Response
--------

..  include:: /_fragments/json_response.rst


Required response properties
----------------------------

The following properties must be present in all |operation| responses:


ContainerPointer
~~~~~~~~~~~~~~~~

A JSON-formatted object containing the following properties:

..  include:: /_fragments/container_pointer.rst


Other response properties
-------------------------

ContainerInfo
~~~~~~~~~~~~~

Hosts can optionally include the ContainerInfo property, which should match the :ref:`CheckContainerInfo` response
for the root container.

..  include:: /_fragments/container_info.rst


Sample response
---------------

..  literalinclude:: /_fragments/responses/container_pointer_and_info.json
    :language: JSON
