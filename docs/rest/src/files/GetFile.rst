
.. meta::
    :robots: noindex

..  index:: WOPI requests; GetFile, GetFile

..  |operation| replace:: GetFile

..  _GetFile:

GetFile
=======

:Required for: |web| |ios| |android|

..  default-domain:: http

..  get:: /wopi/files/(file_id)/contents

    The |operation| operation retrieves a file from a host.

    ..  admonition:: |wac| Tip

        By default, |wac| will use the |operation| WOPI request to retrieve files from the host. However, hosts can
        override this behavior by providing a direct URL to the file using the :term:`FileUrl` property in the
        :ref:`CheckFileInfo` response.

    ..  include:: /_fragments/common_params.rst

    :reqheader X-WOPI-MaxExpectedSize:
        An **integer** specifying the upper bound of the expected size of the file being requested. Optional. The
        host should use the maximum value of a 4-byte integer if this value is not set in the request. If the file
        requested is larger than this value, the host must respond with a :http:statuscode:`412`.

    :resheader X-WOPI-ItemVersion:
        ..  include:: /_fragments/headers/X-WOPI-ItemVersion.rst

    :response Body: The response body must be the full binary contents of the file.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 412: File is larger than **X-WOPI-MaxExpectedSize**
    :code 500: Server error

    ..  include:: /_fragments/common_headers.rst
