
..  index:: WOPI requests; PutRelativeFile, PutRelativeFile

..  _PutRelativeFile:

PutRelativeFile
===============

..  spelling::

    Url


..  default-domain:: http

..  post:: /wopi*/files/(id)

    The PutRelativeFile operation creates a new file on the host based on the current file. The host must use the
    content in the :http:method:`post` body to create a new file. The rules for naming the new file and handling
    errors are described below.

    ..  admonition:: Excel Online Note

        Excel Online uses this operation as part of the *Save As* feature. If this operation is not supported, the
        Save As feature will not work in Excel Online.

    ..  include:: /fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``PUT_RELATIVE``. Required.
    :reqheader X-WOPI-SuggestedTarget:
        A **string** specifying either a file extension or a full file name.

        If only the extension is provided, the name of the initial file without extension should be combined with the
        extension to create the proposed name.

        The host must modify the proposed name as needed to create a new file that is both legally named and does
        not overwrite any existing file, while preserving the file extension.

        This header must be present if **X-WOPI-RelativeTarget** is not present.
    :reqheader X-WOPI-RelativeTarget:
        A **string** that specifies a file name. The host must not modify the name to fulfill the request.

        ..  todo:: :issue:`13`
    :reqheader X-WOPI-OverwriteRelativeTarget:
        A **Boolean** value that specifies whether the host must overwrite the file name if it exists.
    :reqheader X-WOPI-Size:
        An **integer** that specifies the size of the file in bytes.

    :formparam body:
        The request body should be the full binary contents of the file.

    :code 200: Success
    :code 400: Specified name is illegal
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 409: Target file already exists
    :code 413: File is too large; the maximum size is host-specific
    :code 500: Server error
    :code 501: Unsupported

Response
--------

The response to a PutRelativeFile call is JSON (as specified in :rfc:`4627`) containing a number of parameters, some of
which are optional.

All optional values default to the following values based on their type:

=======  ================
Type     Default value
=======  ================
Boolean  ``false``
String   The empty string
=======  ================


Required response properties
----------------------------

The following properties must be present in all PutRelativeFile responses:

Name
    The **string** name of the newly created file without a path. **This is a required value in all PutRelativeFile
    responses.**

Url
    A **string** URI of the form ``http://server/<...>/wopi*/files/(id)?access_token=(access token)``, of the newly
    created file on the host. This URL is the :term:`WOPISrc` for the file with an :term:`access token` appended. Or,
    stated differently, it is the URL to the host's :ref:`Files endpoint` for the file, along with an
    :term:`access token`. A :http:get: request to this URL will invoke the :ref:`CheckFileInfo` operation.
    **This is a required value in all PutRelativeFile responses.**

Optional response properties
----------------------------

HostViewUrl
    The :term:`HostViewUrl`, as a **string**, for the newly created file.

HostEditUrl
    The :term:`HostEditUrl`, as a **string**, for the newly created file.
