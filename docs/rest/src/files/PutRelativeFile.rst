
..  index:: WOPI requests; PutRelativeFile, PutRelativeFile

..  |operation| replace:: PutRelativeFile

..  _PutRelativeFile:

PutRelativeFile
===============

..  spelling::

    Url


:Required for: |web|

..  default-domain:: http

..  post:: /wopi/files/(file_id)

    The |operation| operation creates a new file on the host based on the current file. The host must use the
    content in the :method:`post` body to create a new file.

    The |operation| operation has two distinct modes: *specific* and *suggested*. The primary difference
    between the two modes is whether the WOPI client expects the host to use the file name provided exactly (*specific*
    mode) or if the host can adjust the file name in order to make the request succeed (*suggested* mode).

    Hosts can determine the mode of the operation based on which of the mutually exclusive **X-WOPI-RelativeTarget**
    (indicates *specific* mode) or **X-WOPI-SuggestedTarget** (indicates *suggested* mode) request headers is used. The
    expected behavior for each mode is described in detail below.

    The |operation| operation may be called on a file that is not locked, so the **X-WOPI-Lock** request header
    is not included in this operation. An example of when this might occur is if a user uses the *Save As* feature
    when viewing a document in read-only mode. The source file will not be locked in this case, but the
    |operation| operation will be invoked on it.

    Note, however, that a file matching the target name might be locked, and in *specific* mode, the host must
    respond with a :statuscode:`409` and include a **X-WOPI-Lock** response header as described below.

    ..  important::

        If a WOPI host sets the :term:`SupportsUpdate` property in :ref:`CheckFileInfo` to ``true``, then the host is
        expected to implement the |operation| operation. However, a host may choose not to implement this operation
        even though :term:`SupportsUpdate` is ``true``, but they must do the following:

        #.  Set the :term:`UserCanNotWriteRelative` property to ``true`` always.
        #.  Return a :statuscode:`501` to all |operation| requests.

    ..  admonition:: Excel Online Note

        Excel Online uses this operation in the following two ways:

        #.  As part of the *Save As* feature. If |operation| is not supported, the *Save As* feature will not work
            in Excel Online.
        #.  To support editing of some Excel files in Excel Online. Some files may contain content that is not
            currently supported in Excel Online. In this case, Excel Online will prompt the user to save
            an editable copy of the document, removing all unsupported content so that the file can be edited in
            Excel Online. If |operation| is not supported, files with unsupported content will not be editable in Excel
            Online.

    ..  include:: /_fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``PUT_RELATIVE``. Required.

    :reqheader X-WOPI-SuggestedTarget:
        A UTF-7 encoded **string** specifying either a file extension or a full file name, including the file
        extension. Hosts can differentiate between full file names and extensions as follows:

        * If the string begins with a period (``.``), it is a file extension.
        * Otherwise, it is a full file name.

        If only the extension is provided, the name of the initial file without extension should be combined with the
        extension to create the proposed name.

        The response to a request including this header must never result in a :statuscode:`400` or
        :statuscode:`409`. Rather, the host must modify the proposed name as needed to create a new file that is both
        legally named and does not overwrite any existing file, while preserving the file extension.

        This header must be present if **X-WOPI-RelativeTarget** is not present; the two headers are mutually
        exclusive. If both headers are present the host should respond with a :statuscode:`501`.

    :reqheader X-WOPI-RelativeTarget:
        A UTF-7 encoded **string** that specifies a full file name including the file extension. The host must not
        modify the name to fulfill the request.

        If the specified name is illegal, the host must respond with a :statuscode:`400`.

        If a file with the specified name already exists, the host must respond with a :statuscode:`409`, unless the
        **X-WOPI-OverwriteRelativeTarget** request header is set to ``true``. When responding with a
        :statuscode:`409` for this reason, the host may include an **X-WOPI-ValidRelativeTarget** specifying a file
        name that is valid.

        If the **X-WOPI-OverwriteRelativeTarget** request header is set to ``true`` and a file with the specified
        name already exists and is locked, the host must respond with a :statuscode:`409` and include an
        **X-WOPI-Lock** response header containing the value of the current lock on the file.

        This header must be present if **X-WOPI-SuggestedTarget** is not present; the two headers are mutually
        exclusive. If both headers are present the host should respond with a :statuscode:`501`.

    :reqheader X-WOPI-OverwriteRelativeTarget:
        A **Boolean** value that specifies whether the host must overwrite the file name if it exists.

        This header will only be present if the **X-WOPI-RelativeTarget** is included on the request. If
        **X-WOPI-OverwriteRelativeTarget** is not explicitly included on the request, hosts should behave as though its
        value is ``false``.

    :reqheader X-WOPI-Size:
        An **integer** that specifies the size of the file in bytes.

    :body: The request body must be the full binary contents of the file.

    :reqheader X-WOPI-FileConversion:
        A header whose presence indicates that the request is being made in the context of a
        :ref:`binary document conversion<conversion>`. This header will only be included on the request in that case.
        Thus, if **X-WOPI-FileConversion** is not explicitly included on the request, hosts must behave as if the
        |operation| request is not being made as part of a binary document conversion.

        See :ref:`conversion` for more information on the conversion process and how this header can be used.

    :resheader X-WOPI-ValidRelativeTarget:
        A UTF-7 encoded **string** that specifies a full file name including the file extension. This header may be
        used when responding with a :statuscode:`409` because a file with the requested name already exists, or when
        responding with a :statuscode:`400` because the requested name contained invalid characters. If this
        response header is included, the WOPI client should automatically retry the |operation| operation using the
        contents of this header as the **X-WOPI-RelativeTarget** value and will not display an error message to the
        user.

    :resheader X-WOPI-Lock:
        ..  include:: /_fragments/headers/X-WOPI-Lock.rst

    :resheader X-WOPI-LockFailureReason:
        ..  include:: /_fragments/headers/X-WOPI-LockFailureReason.rst

    :resheader X-WOPI-LockedByOtherInterface:
        ..  include:: /_fragments/headers/X-WOPI-LockedByOtherInterface.rst

    :code 200: Success
    :code 400: Specified name is illegal
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 409: Target file already exists or the file is locked; if the target file is locked, an **X-WOPI-Lock**
        response header containing the value of the current lock on the file must always be included
    :code 413: File is too large; the maximum size is host-specific
    :code 500: Server error
    :code 501: Operation not supported; if the host sets the :term:`SupportsUpdate` and :term:`UserCanNotWriteRelative`
        properties to ``true`` in :ref:`CheckFileInfo`, this response code must be used when this operation is called

    ..  include:: /_fragments/common_headers.rst

Response
--------

The response to a |operation| call is JSON (as specified in :rfc:`4627`) containing a number of properties, some of
which are optional.

..  include:: /_fragments/param_types.rst


Required response properties
----------------------------

The following properties must be present in all |operation| responses:

Name
    The **string** name of the newly created file without a path.

Url
    A **string** URI of the form ``http://server/<...>/wopi/files/(file_id)?access_token=(access token)``, of the newly
    created file on the host. This URL is the :term:`WOPISrc` for the new file with an :term:`access token` appended.
    Or, stated differently, it is the URL to the host's :ref:`Files endpoint` for the new file, along with an
    :term:`access token`. A :method:`GET` request to this URL will invoke the :ref:`CheckFileInfo` operation.

Optional response properties
----------------------------

HostViewUrl
    The :term:`HostViewUrl`, as a **string**, for the newly created file.

HostEditUrl
    The :term:`HostEditUrl`, as a **string**, for the newly created file.
