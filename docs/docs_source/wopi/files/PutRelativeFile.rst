
..  index:: WOPI requests; PutRelativeFile, PutRelativeFile

..  _PutRelativeFile:

PutRelativeFile
===============

..  spelling::

    Url


..  default-domain:: http

..  post:: /wopi*/files/(id)

    The PutRelativeFile operation creates a new file on the host based on the current file. The host must use the
    content in the :method:`post` body to create a new file.

    The PutRelativeFile operation has two distinct modes: *specific* and *suggested*. The primary difference
    between the two modes is whether Office Online expects the host to use the file name provided exactly (*specific*
    mode) or if the host can adjust the file name in order to make the request succeed (*suggested* mode).

    Hosts can determine the mode of the operation based on which of the mutually exclusive **X-WOPI-RelativeTarget**
    (indicates *specific* mode) or **X-WOPI-SuggestedTarget** (indicates *suggested* mode) request headers is used. The
    expected behavior for each mode is described in detail below.

    The PutRelativeFile operation may be called on a file that is not locked, so the **X-WOPI-Lock** request header
    is not included in this operation. An example of when this might occur is if a user uses the *Save As* feature
    when viewing a document in read-only mode. The source file will not be locked in this case, but the
    PutRelativeFile operation will be invoked on it.

    Note, however, that a file matching the target name might be locked, and in *specific* mode, the host must
    respond with a :statuscode:`409` and include a **X-WOPI-Lock** response header as described below.

    ..  admonition:: Excel Online Note

        Excel Online uses this operation as part of the *Save As* feature. If this operation is not supported, the
        *Save As* feature will not work in Excel Online.

    ..  include:: /fragments/common_params.rst

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

    :formparam body:
        The request body should be the full binary contents of the file.


    :resheader X-WOPI-ValidRelativeTarget:
        A UTF-7 encoded **string** that specifies a full file name including the file extension. This header may be
        used when responding with a :statuscode:`409` because a file with the requested name already exists. If this
        response header is included, Office Online will automatically retry the PutRelativeFile operation using the
        contents of this header as the **X-WOPI-RelativeTarget** value and will not display an error message to the
        user.

    :resheader X-WOPI-Lock:
        A **string** value identifying the current lock on the file. This header must only be included when
        responding to a request attempting to overwrite a currently locked file with a :statuscode:`409`.

    :resheader X-WOPI-LockFailureReason:
        An optional **string** value indicating the cause of a lock failure. This header may be included when
        responding to the request with :http:statuscode:`409`. There is no standard for how this string is
        formatted, and Office Online only uses it for logging purposes. However, we recommend hosts use small strings
        that are consistent. This allows Office Online to easily report to hosts how often locks are failing due to
        particular reasons.

    :resheader X-WOPI-LockedByOtherInterface:
        An optional **string** value indicating that the file is currently locked by someone other than Office Online.
        This header is optional, and is only used by Office Online to provide more specific messages to users when
        operations fail. If set, the value of this header must be the string ``true``.

    :code 200: Success
    :code 400: Specified name is illegal
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 409: Target file already exists or the file is locked; if the target file is locked, an **X-WOPI-Lock**
        response header containing the value of the current lock on the file must always be included
    :code 413: File is too large; the maximum size is host-specific
    :code 500: Server error
    :code 501: Unsupported; the host should

    ..  include:: /fragments/common_headers.rst

Response
--------

The response to a PutRelativeFile call is JSON (as specified in :rfc:`4627`) containing a number of properties, some of
which are optional.

..  include:: /fragments/param_types.rst


Required response properties
----------------------------

The following properties must be present in all PutRelativeFile responses:

Name
    The **string** name of the newly created file without a path. **This is a required value in all PutRelativeFile
    responses.**

Url
    A **string** URI of the form ``http://server/<...>/wopi*/files/(id)?access_token=(access token)``, of the newly
    created file on the host. This URL is the :term:`WOPISrc` for the new file with an :term:`access token` appended.
    Or, stated differently, it is the URL to the host's :ref:`Files endpoint` for the new file, along with an
    :term:`access token`. A :method:`GET` request to this URL will invoke the :ref:`CheckFileInfo` operation.
    **This is a required value in all PutRelativeFile responses.**

Optional response properties
----------------------------

HostViewUrl
    The :term:`HostViewUrl`, as a **string**, for the newly created file.

HostEditUrl
    The :term:`HostEditUrl`, as a **string**, for the newly created file.
