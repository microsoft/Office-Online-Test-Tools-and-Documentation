
.. meta::
    :robots: noindex

..  index:: WOPI requests; GetRootContainer (bootstrapper), GetRootContainer (bootstrapper)

..  |operation| replace:: GetRootContainer

..  _GetRootContainer (bootstrapper):

GetRootContainer (bootstrapper)
===============================

:Required for: |ios|

..  default-domain:: http

..  post:: /wopibootstrapper

    This operation is equivalent to the :ref:`GetRootContainer` operation.

    ..  include:: /_fragments/bootstrapper/shortcut_note.rst


    :reqheader X-WOPI-EcosystemOperation:
        The **string** ``GET_ROOT_CONTAINER``. Required.

    :reqheader Authorization:
        A **string** in the format ``Bearer: <TOKEN>`` where ``<TOKEN>`` is a Base64-encoded OAuth 2.0 token. If this
        header is missing, or the token provided is invalid, the host must respond with a :statuscode:`401` response
        and include the :header:`WWW-Authenticate` header as described in :ref:`WWW-Authenticate header`.

    :resheader WWW-Authenticate:
        A **string** value formatted as described in :ref:`WWW-Authenticate header`. This header should only be
        included when responding with a :statuscode:`401`.

    :code 200: Success
    :code 401: Authorization failure; when responding with this status code, hosts must include a
               :header:`WWW-Authenticate` response header with values as described in :ref:`WWW-Authenticate header`
    :code 404: Resource not found/user unauthorized
    :code 500: Server error
    :code 501: Operation not supported


Response
--------

..  include:: /_fragments/json_response_required.rst

Bootstrap
    The contents of this property should be the response to a :ref:`Bootstrap` call.

RootContainerInfo
    The contents of this property should be the response to a :ref:`GetRootContainer` call.


Sample response
---------------

..  literalinclude:: /_fragments/responses/GetRootContainer (bootstrapper).json
    :language: JSON
