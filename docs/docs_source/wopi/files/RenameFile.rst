
..  index:: WOPI requests; RenameFile, RenameFile

..  _RenameFile:

RenameFile
==========

..  default-domain:: http

..  post:: /wopi*/files/(file_id)

    The RenameFile operation renames an existing file. Office Online includes contains UI that enables users can use to
    rename files. In order to activate this UI in Office Online, you must implement the RenameFile operation, and
    also do the following:

    * Set :term:`SupportsRename` and :term:`UserCanRename` to true in your :ref:`CheckFileInfo` response.
    * Set a :term:`FileNameMaxLength` value if the default value is not correct for your WOPI host.

    ..  important::
        Renaming the file must not cause the :term:`File ID`, and by extension, the :term:`WOPISrc`, to change.

    If the host cannot rename the file because the name requested is invalid or conflicts with an existing file, the
    host should try to generate a different name based on the requested name that meets the file name requirements.

    If the host cannot generate a different name, it should return an HTTP status code :http:statuscode:`400`. The
    response must include an **X-WOPI-InvalidFileNameError** header that describes why the file name was invalid.

    If the file is currently unlocked, the host should respond with a :statuscode:`200` and proceed with the rename.

    If the file is currently locked and the **X-WOPI-Lock** value does not match the lock currently on the file the
    host must return a "lock mismatch" response (:http:statuscode:`409`) and include an **X-WOPI-Lock** response
    header containing the value of the current lock on the file.

    ..  include:: /fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``RENAME_FILE``. Required.
    :reqheader X-WOPI-Lock:
        A **string** provided by Office Online that the host must use to identify the lock on the file.
    :reqheader X-WOPI-RequestedName:
        A UTF-7 encoded **string** that is a file name, not including the file extension.

    :resheader X-WOPI-InvalidFileNameError:
        A **string** describing the reason the rename operation could not be completed. This header should only be
        included when the response code is :http:statuscode:`400`. Office Online only uses this string for logging
        purposes.

    ..  include:: /fragments/common_lock_responses.rst

    :code 200: Success
    :code 400: Specified name is illegal
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 409: Lock mismatch/locked by another interface; an **X-WOPI-Lock** response header containing the value of
        the current lock on the file must always be included when using this response code
    :code 500: Server error
    :code 501: Unsupported

    ..  include:: /fragments/common_headers.rst

Response
--------

The response to a RenameFile call is JSON (as specified in :rfc:`4627`) containing a single required property:

Name
    The **string** name of the renamed file without a path or file extension. **This is a required value in all
    RenameFile responses.**
