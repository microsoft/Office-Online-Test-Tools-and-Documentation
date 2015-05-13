
..  index:: WOPI requests; GetFile, GetFile

..  _GetFile:

GetFile
=======

..  default-domain:: http

..  get:: /wopi*/files/(id)/contents

    The GetFile operation retrieves a file from a host.

    By default, Office Online will use the GetFile WOPI request to retrieve files from the host. However, hosts can
    override this behavior by providing a direct URL to the file using the :term:`FileUrl` property in the
    :ref:`CheckFileInfo` response.

    ..  include:: /fragments/common_params.rst

    :reqheader X-WOPI-MaxExpectedSize:
        An **integer** specifying the upper bound of the expected size of the file being requested. Optional. The
        host should use the maximum value of a 4-byte integer if this value is not set in the request.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 412: File is larger than **X-WOPI-MaxExpectedSize**
    :code 500: Server error
