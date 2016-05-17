
..  index:: WOPI requests; GetFileWopiSrc (ecosystem), GetFileWopiSrc (ecosystem)

..  |operation| replace:: GetFileWopiSrc

..  _GetFileWopiSrc (ecosystem):
..  _GetFileWopiSrc:

GetFileWopiSrc (ecosystem)
==========================

..  default-domain:: http

..  post:: /wopi/ecosystem

    ..  include:: /_fragments/future_operation.rst

    The |operation| operation is used to convert a host-specific file identifier into a :term:`WopiSrc` value.

    The WOPI client passes the host-specific file identifier in the **X-WOPI-HostNativeFileName** header. The host,
    in turn, returns a WopiSrc URL with an :term:`access token` appended.

    This operation is useful in cases where a WOPI client receives a file identifier that is not a proper WopiSrc,
    but can be translated into a valid WopiSrc value by the WOPI host.

    For example, iOS applications can open files in |Office iOS| using URL schemes. However, it may not be feasible
    for an application to generate a WopiSrc value itself. However, as long as it can generate a string value that
    can later be converted to a WopiSrc value by calling this operation, then the application can pass this value and
    rely on |Office iOS| to call this operation to convert the file identifier into a WopiSrc.

    ..  include:: /_fragments/bootstrapper/shortcut_seealso.rst

    ..  include:: /_fragments/access_token_param.rst

    :reqheader X-WOPI-Override:
        The **string** ``GET_WOPI_SRC_WITH_ACCESS_TOKEN``. Required.
    :reqheader X-WOPI-HostNativeFileName:
        ..  include:: /_fragments/headers/X-WOPI-HostNativeFileName.rst

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 500: Server error
    :code 501: Operation not supported

    ..  include:: /_fragments/common_headers.rst

Response
--------

..  include:: /_fragments/json_response_required.rst

Url
    A URI that represents a :term:`WopiSrc` value with an :term:`access token` appended.

Sample response
---------------

..  literalinclude:: /_fragments/responses/GetFileWopiSrc.json
    :language: JSON
