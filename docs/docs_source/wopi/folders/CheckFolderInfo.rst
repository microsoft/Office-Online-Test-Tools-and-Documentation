
..  index:: WOPI requests; CheckFolderInfo, CheckFolderInfo

..  _CheckFolderInfo:

CheckFolderInfo
===============

..  default-domain:: http

..  get:: /wopi*/folders/(id)

    ..  include:: /fragments/deprecated_warning.rst

    The CheckFolderInfo operation returns information about a file, a user's permissions on that folder, and general
    information about the capabilities that the WOPI host has on the file.

    Returns information about a file.

    ..  include:: /fragments/common_params.rst

    :query string sc:
        An optional :term:`Session Context` string that will be passed back to the host in subsequent
        :ref:`CheckFileInfo` and :ref:`CheckFolderInfo` calls in the **X-WOPI-SessionContext** request
        header.

    :reqheader X-WOPI-SessionContext:
        The value of the :term:`Session Context` URI parameter, if passed in the ``sc`` parameter.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Folder unknown/user unauthorized
    :code 500: Server error

Required response properties
----------------------------

..  default-domain:: js

The following properties must be present in all CheckFolderInfo responses:

..  No glossary since these terms shouldn't be referenceable.

FolderName
    The **string** name of the folder without a path. Used for display in user interface (UI).
    **This is a required value in all CheckFolderInfo responses.**

OwnerId
    A string that uniquely identifies the owner of the file.
    **This is a required value in all CheckFolderInfo responses.**

Response Values
---------------

..  todo:: Add detail.
