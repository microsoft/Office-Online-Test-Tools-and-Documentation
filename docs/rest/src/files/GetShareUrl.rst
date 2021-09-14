
.. meta::
    :robots: noindex

..  index:: WOPI requests; GetShareUrl (files), GetShareUrl (files)

..  |operation| replace:: GetShareUrl

..  _GetShareUrl (files):
..  _GetShareUrl:

GetShareUrl (files)
==============================

:Required for: |ios| |android|

..  seealso::

    :ref:`GetShareUrl (containers)`

..  default-domain:: http

..  post:: /wopi/files/(file_id)

    The |operation| operation returns a :term:`Share URL` that is suitable for viewing a shared file when launched
    in a web browser. A host can support multiple Share URL types, as described by the :term:`SupportedShareUrlTypes`
    property. The **X-WOPI-UrlType** request header contains the Share URL type that should be returned.

    If the **X-WOPI-UrlType** header is not present or contains a value that is invalid or not supported by the host,
    the host should respond with a :http:statuscode:`501`.

    ..  include:: /_fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``GET_SHARE_URL``. Required.

    :reqheader X-WOPI-UrlType:
        A **string** indicating what Share URL type to return. Required.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 500: Server error
    :code 501: Operation not supported

    ..  include:: /_fragments/common_headers.rst

Response
--------

..  include:: /_fragments/json_response_required.rst

ShareUrl
    A URI that points to a webpage that allows the user to access the file. Required.

    ..  seealso::

        :term:`Share Url`
