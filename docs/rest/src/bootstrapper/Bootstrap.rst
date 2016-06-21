
..  |operation| replace:: Bootstrap

..  _Bootstrap:

Bootstrap
=========

..  spelling::

    providerId
    tokenIssuance
    uri


:Required for: |ios|

..  default-domain:: http

..  get:: /wopibootstrapper


    The |operation| operation is used to 'convert' an OAuth token into appropriate WOPI
    :term:`access tokens<access token>` and provides access to WOPI operations for applications using OAuth tokens,
    like |Office iOS|. For more information see :ref:`native`.

    ..  important:: Connections to the bootstrapper must be made using :abbr:`TLS (Transport Layer Security)`.

    :reqheader Authorization:
        A **string** in the format ``Bearer: <TOKEN>`` where ``<TOKEN>`` is a Base64-encoded OAuth 2.0 token. If this
        header is missing, or the token provided is invalid, the host must respond with a :statuscode:`401` response
        and include the :header:`WWW-Authenticate` header as described below.

    :resheader WWW-Authenticate:
        A **string** value formatted as described in :ref:`WWW-Authenticate header`.

    :resheader Content-Type:
        Must be ``application/json`` for :ref:`authenticated responses<authenticated response>`.


    :code 200: Success
    :code 401: Authorization failure; when responding with this status code, hosts must include a
               :header:`WWW-Authenticate` response header with values as described in :ref:`WWW-Authenticate header`
    :code 404: Resource not found/user unauthorized
    :code 500: Server error

Response
--------

The response to a |operation| operation differs based on whether it is **authenticated** or **unauthenticated**. When
possible, the WOPI client will provide authentication state information, as defined by OAuth 2.0 protocol, in the
HTTP header in every request to the :ref:`Bootstrapper endpoint`. This authentication state will be sent in the form of
the OAuth 2.0 Access token and will be contained in the :header:`Authorization` header as described previously.

The host must verify that the provided token is valid. If it is not, the host must respond as described in the
:ref:`unauthenticated response <unauthenticated response>` section below. If the provided token is valid, then the host
must respond as described in the :ref:`authenticated response <authenticated response>` section below.


..  _authenticated response:

Authenticated response
~~~~~~~~~~~~~~~~~~~~~~

When an **authenticated** request (i.e. a valid OAuth 2.0 access token is included in the :header:`Authorization` HTTP
header) is made to this endpoint, it returns a :statuscode:`200` response with a JSON (as specified in :rfc:`4627`)
response body. The response must include a single ``Bootstrap`` property, with the following properties nested within
it as needed.


Required response properties
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The following properties must be present in the ``Bootstrap`` property in all :statuscode:`200` |operation| responses:

..  glossary::

    EcosystemUrl
        A **string** URI for the WOPI server's :ref:`Ecosystem endpoint`, with a WOPI :term:`access token` appended. A
        :method:`GET` request to this URL will invoke the :ref:`CheckEcosystem` operation.

UserId
    A **string** value uniquely identifying the user making the request. This value should match the :term:`UserId`
    value provided in :ref:`CheckFileInfo`. This ID is expected to be unique per user and consistent over time. See
    :ref:`User identity requirements` for more information.

SignInName
    A **string** value identifying the user making the request. This value is used to distinguish a user's
    account in the event a user has multiple accounts with a given host. This value is often an email address, though
    it is not required to be.


Optional response properties
^^^^^^^^^^^^^^^^^^^^^^^^^^^^

UserFriendlyName
    A **string** that is the name of the user. This value should match the :term:`UserFriendlyName` value provided in
    :ref:`CheckFileInfo`.


Sample response
^^^^^^^^^^^^^^^

..  literalinclude:: /_fragments/responses/Bootstrap.json
    :language: JSON


..  _unauthenticated response:

Unauthenticated response
~~~~~~~~~~~~~~~~~~~~~~~~

When an **unauthenticated** request (i.e. no access token is attached in the :header:`Authorization` HTTP header) is
made to this endpoint, it returns a :statuscode:`401` response containing sufficient information to facilitate user
authentication with the host.


..  _WWW-Authenticate header:

WWW-Authenticate response header format
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The response must contain sufficient information for a WOPI client to perform the necessary
authentication/authorization/token issuance flows with the host's identity provider, and result in an authenticated
call to the same Bootstrapper endpoint.

The information for the successful authentication/authorization/token issuance flows must be returned in the
:header:`WWW-Authenticate` header of the :statuscode:`401` response with type "Bearer." The information that must be
returned in a :statuscode:`401` response to an unauthenticated request is as follows:

+-------------------+----------------------------------------+----------+------------------------------------------+
| Parameter         | Value                                  | Required | Example                                  |
+===================+========================================+==========+==========================================+
| Bearer            | n/a                                    | Yes      | ``Bearer``                               |
+-------------------+----------------------------------------+----------+------------------------------------------+
| authorization_uri | The URL of the OAuth2 Authorization    | Yes      | https://contoso.com/api/oauth2/authorize |
|                   | Endpoint to begin authentication       |          |                                          |
|                   | against as described at:               |          |                                          |
|                   | :rfc:`6749#section-3.1`                |          |                                          |
+-------------------+----------------------------------------+----------+------------------------------------------+
| tokenIssuance_uri | The URL of the OAuth2 Token Endpoint   | Yes      | https://contoso.com/api/oauth2/token     |
|                   | where authentication code can be       |          |                                          |
|                   | redeemed for an access and             |          |                                          |
|                   | (optional) refresh token. See Token    |          |                                          |
|                   | Endpoint at: :rfc:`6749#section-3.2`   |          |                                          |
+-------------------+----------------------------------------+----------+------------------------------------------+
| providerId        | A well-known string (as registered     | No       | ``tp_contoso``                           |
|                   | with Microsoft Office) that uniquely   |          |                                          |
|                   | identifies the host.                   |          |                                          |
|                   |                                        |          |                                          |
|                   | Allowed characters: [a-z, A-Z, 0-9]    |          |                                          |
+-------------------+----------------------------------------+----------+------------------------------------------+
| UrlSchemes        | URL scheme your app uses. This is an   | No       | \{"iOS" : ["contoso","contoso-EMM"],     |
|                   | ordered list by platform. Omit any     |          | \ "Android" : ["contoso","contoso-EMM"]  |
|                   | platforms you do not support. Office   |          | "UWP": ["contoso","contoso-EMM"]}        |
|                   | will attempt to invoke these URL       |          |                                          |
|                   | schemes in order before falling back to|          | The value itself must be URL encoded     |
|                   | the webview auth.                      |          |                                          |
+-------------------+----------------------------------------+----------+------------------------------------------+

These parameters and their values must be formatted as follows:

* Values are contained within double-quotes (``"``).
* Contiguous parameters are separated by commas with no comma after the trailing parameter/value pair.
* If no value is known/required for an optional parameter, it may be omitted from the :header:`WWW-Authenticate` header.
* Multiple instances of :header:`WWW-Authenticate` HTTP headers may exist in the response to an unauthenticated
  request to the Bootstrapper endpoint.  However, there must be exactly one instance of the :header:`WWW-Authenticate`
  header with the ``Bearer`` qualifier.

Sample unauthenticated response
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

..  code-block:: text

    HTTP/1.1 401 Unauthorized
    <removed for brevity>
    WWW-Authenticate: Bearer authorization_uri="https://contoso.com/api/oauth2/authorize",tokenIssuance_uri="https://contoso.com/api/oauth2/token",providerId="tp_contoso", UrlSchemes="%7B%22iOS%22%20%3A%20%5B%22contoso%22%2C%22contoso-EMM%22%5D%2C%20%22Android%22%20%3A%20%5B%22contoso%22%2C%22contoso-EMM%22%5D%2C%20%22UWP%22%3A%20%5B%22contoso%22%2C%22contoso-EMM%22%5D%7D"
    Date: Wed, 24 Jun 2015 21:52:44 GMT

