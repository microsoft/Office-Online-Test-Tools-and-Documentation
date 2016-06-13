
..  index:: WOPI requests; CheckContainerInfo, CheckContainerInfo

..  |operation| replace:: CheckContainerInfo

..  _CheckContainerInfo:

CheckContainerInfo
==================

:Required for: |ios|

..  default-domain:: http

..  get:: /wopi/containers/(container_id)

    The |operation| operation is similar to the the :ref:`CheckFileInfo` operation, but operates on
    :term:`containers<container>` instead of files. |operation| returns information about a container and a user's
    permissions on that container.

    ..  include:: /_fragments/common_containers_params.rst

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 500: Server error
    :code 501: Operation not supported

    ..  include:: /_fragments/common_headers.rst

Response
--------

..  include:: /_fragments/json_response.rst

Required response properties
----------------------------

The following properties must be present in all |operation| responses:

Name
    The name of the container without a path. This value will be displayed in the WOPI client UI.

Other response properties
-------------------------

..  glossary::

    HostUrl
        A URI to a webpage for the container.

IsEduUser
    A **Boolean** value indicating whether the user is an education user or not. This should match the value returned
    in :term:`CheckFileInfo <IsEduUser>`.

LicenseCheckForEditIsEnabled
    A **Boolean** value indicating whether the user is a business user or not. This should match the value returned
    in :term:`CheckFileInfo <LicenseCheckForEditIsEnabled>`.

..  glossary::

    SharingUrl
        A URI to a webpage to allow the user to control sharing of the container. This is analogous to the
        :term:`FileSharingUrl` in :ref:`CheckFileInfo`.

    UserCanCreateChildContainer
        A **Boolean** value that indicates the user has permission to create a new container in the container.

    UserCanCreateChildFile
        A **Boolean** value that indicates the user has permission to create a new file in the container.

    UserCanDelete
        A **Boolean** value that indicates the user has permission to delete the container.

    UserCanRename
        A **Boolean** value that indicates the user has permission to rename the container.
