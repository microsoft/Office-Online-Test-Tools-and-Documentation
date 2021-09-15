
.. meta::
    :robots: noindex

..  index:: WOPI requests; GetFileWopiSrc (bootstrapper), GetFileWopiSrc (bootstrapper)

..  |operation| replace:: GetFileWopiSrc

..  _GetFileWopiSrc (bootstrapper):

|draft-icon| GetFileWopiSrc (bootstrapper)
==========================================

..  include:: /_fragments/future_operation.rst


..  default-domain:: http

..  post:: /wopibootstrapper

    This operation is equivalent to the :ref:`GetFileWopiSrc` operation.

    ..  include:: /_fragments/bootstrapper/shortcut_note.rst


    :reqheader X-WOPI-EcosystemOperation:
        The **string** ``GET_WOPI_SRC_WITH_ACCESS_TOKEN``. Required.

    :reqheader X-WOPI-HostNativeFileName:
        ..  include:: /_fragments/headers/X-WOPI-HostNativeFileName.rst

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


Response
--------

..  include:: /_fragments/json_response_required.rst

Bootstrap
    The contents of this property should be the response to a :ref:`Bootstrap` operation.

WopiSrcInfo
    The contents of this property should be the response to a :ref:`GetFileWopiSrc` operation.


Sample response
---------------

..  literalinclude:: /_fragments/responses/GetFileWopiSrc (bootstrapper).json
    :language: JSON
