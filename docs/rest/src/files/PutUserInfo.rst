
.. meta::
    :robots: noindex

..  index:: WOPI requests; PutUserInfo, PutUserInfo

..  |operation| replace:: PutUserInfo

..  _PutUserInfo:

PutUserInfo
===========
..  default-domain:: http

..  post:: /wopi/files/(file_id)

    The |operation| operation stores some basic user information on the host. When a host receives this request, they
    must store the UserInfo string which is contained in the body of the request. The UserInfo string should be
    associated with a particular user, and should be passed back to the WOPI client in subsequent :ref:`CheckFileInfo`
    responses in the :term:`UserInfo` property.

    The UserInfo string is provided in the body of the request, and has a maximum size of 1024 ASCII characters.

    Note that WOPI clients will only call this WOPI operation if the host sets the :term:`SupportsUserInfo` property
    to ``true`` in the :ref:`CheckFileInfo` response.

    ..  versionadded:: 2015.08.03


    ..  include:: /_fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``PUT_USER_INFO``. Required.

    :body: The request body must be the full UserInfo string.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 500: Server error
    :code 501: Operation not supported
