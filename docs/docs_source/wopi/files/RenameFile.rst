
..  index:: WOPI requests; RenameFile, RenameFile

..  _RenameFile:

RenameFile
==========

..  default-domain:: http

..  post:: /wopi*/files/(id)

    The RenameFile operation renames an existing file. Office Online includes contains UI that enables users can use to
    rename files. In order to activate this UI in Office Online, you must implement the RenameFile operation, and
    also do the following:

    * Set :term:`SupportsRename` and :term:`UserCanRename` to true in your :ref:`CheckFileInfo` response.
    * Set a :term:`FileNameMaxLength` value if the default value is not correct for your WOPI host.

    If the host cannot rename the file because the name requested is invalid or conflicts with an existing file, the
    host should try to generate a different name based on the requested name that meets the file name requirements.

    If the host cannot generate a different name, it should return an HTTP status code :http:statuscode:`400`. The
    response must include an **X-WOPI-InvalidFileNameError** header that describes why the file name was invalid.

    ..  todo:: :issue:`14`

    ..  include:: /fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``RENAME_FILE``. Required.
    :reqheader X-WOPI-RequestedName:
        A UTF-7 encoded **string** that is a file name, not including the file extension.

    :resheader X-WOPI-InvalidFileNameError:
        A **string** describing the reason the rename operation could not be completed. This header should only be
        included when the response code is :http:statuscode:`400`.

    :code 200: Success
    :code 400: Specified name is illegal
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 409: Target file already exists
    :code 500: Server error
    :code 501: Unsupported

Required response properties
----------------------------

..  default-domain:: js

The following properties must be present in all RenameFile responses:

..  data:: Name
    :noindex:

    The **string** name of the renamed file without a path or file extension.
    **This is a required value in all RenameFile responses.**
