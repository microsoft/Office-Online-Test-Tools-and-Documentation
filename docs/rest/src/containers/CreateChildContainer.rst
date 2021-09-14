
.. meta::
    :robots: noindex

..  index:: WOPI requests; CreateChildContainer, CreateChildContainer

..  |operation| replace:: CreateChildContainer

..  _CreateChildContainer:

CreateChildContainer
====================

:Required for: |ios| |android|

..  default-domain:: http

..  post:: /wopi/containers/(container_id)

    The |operation| operation creates a new child :term:`container` in the provided parent container.

    The |operation| operation has two distinct modes: *specific* and *suggested*. The primary difference
    between the two modes is whether the WOPI client expects the host to use the container name provided exactly
    (*specific* mode) or if the host can adjust the container name in order to make the request succeed (*suggested*
    mode).

    Hosts can determine the mode of the operation based on which of the mutually exclusive **X-WOPI-RelativeTarget**
    (indicates *specific* mode) or **X-WOPI-SuggestedTarget** (indicates *suggested* mode) request headers is used. The
    expected behavior for each mode is described in detail below.


    ..  include:: /_fragments/common_containers_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``CREATE_CHILD_CONTAINER``. Required.

    :reqheader X-WOPI-SuggestedTarget:
        A UTF-7 encoded **string** that specifies a full container name. Required.

        The response to a request including this header must never result in a :statuscode:`400` or
        :statuscode:`409`. Rather, the host must modify the proposed name as needed to create a new container that is
        legally named.

        This header must be present if **X-WOPI-RelativeTarget** is not present; the two headers are mutually
        exclusive. If both headers are present the host should respond with a :statuscode:`501`.

    :reqheader X-WOPI-RelativeTarget:
        A UTF-7 encoded **string** that specifies a full container name. The host must not modify the name to fulfill
        the request.

        If the specified name is illegal, the host must respond with a :statuscode:`400`.

        If a container with the specified name already exists, the host must respond with a :statuscode:`409`. When
        responding with a :statuscode:`409` for this reason, the host may include an **X-WOPI-ValidRelativeTarget**
        specifying a container name that is valid.

        This header must be present if **X-WOPI-SuggestedTarget** is not present; the two headers are mutually
        exclusive. If both headers are present the host should respond with a :statuscode:`501`.

    :resheader X-WOPI-InvalidContainerNameError:
        A **string** describing the reason the |operation| operation could not be completed. This header
        should only be included when the response code is :http:statuscode:`400`. This string is only used for
        logging purposes.

    :code 200: Success
    :code 400: Specified name is illegal
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 409: Target container already exists
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
for the newly created container.

..  include:: /_fragments/container_info.rst


Sample response
---------------

..  literalinclude:: /_fragments/responses/container_pointer_and_info.json
    :language: JSON
