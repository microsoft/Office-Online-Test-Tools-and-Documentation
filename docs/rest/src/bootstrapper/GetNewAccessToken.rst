
.. meta::
    :robots: noindex

..  index:: WOPI requests; GetNewAccessToken, GetNewAccessToken

..  |operation| replace:: GetNewAccessToken

..  _GetNewAccessToken:

GetNewAccessToken
=================

:Required for: |ios| |android|

..  default-domain:: http

..  post:: /wopibootstrapper

    The |operation| operation is used to retrieve a fresh WOPI :term:`access token` for a given resource (i.e. a file
    or :term:`container`), provided the caller has a valid OAuth 2.0 token.

    This operation is called by OAuth-capable WOPI clients, such as |Office iOS|, to refresh WOPI access tokens when
    they expire.

    :reqheader X-WOPI-EcosystemOperation:
        The **string** ``GET_NEW_ACCESS_TOKEN``. Required.

    :reqheader X-WOPI-WopiSrc:
        The :term:`WopiSrc` (a **string**) for the file or :term:`container`


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
~~~~~~~~~

The contents of this property should be the response to a :ref:`Bootstrap` call.


AccessTokenInfo
~~~~~~~~~~~~~~~

The contents of this property should be a the nested JSON-formatted object with the following properties.

AccessToken
    A **string** :term:`access token` for the file specified in the **X-WOPI-WopiSrc** request header.

..  glossary::

    AccessTokenExpiry
        A **long** value representing the time that the :term:`access token` provided in the response will expire. See
        :term:`access_token_ttl` for more information on how this value is defined.


Sample response
---------------

..  literalinclude:: /_fragments/responses/GetNewAccessToken (bootstrapper).json
    :language: JSON
