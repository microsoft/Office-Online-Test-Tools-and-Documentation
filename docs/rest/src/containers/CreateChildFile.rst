
.. meta::
    :robots: noindex

..  index:: WOPI requests; CreateChildFile, CreateChildFile

..  |operation| replace:: CreateChildFile

..  _CreateChildFile:

CreateChildFile
===============

:Required for: |ios| |android|

..  default-domain:: http

..  post:: /wopi/containers/(container_id)

    The |operation| operation creates a new file in the provided :term:`container`. **The resulting file must be
    zero bytes in length.**

    The |operation| operation has two distinct modes: *specific* and *suggested*. The primary difference
    between the two modes is whether the WOPI client expects the host to use the file name provided exactly (*specific*
    mode) or if the host can adjust the file name in order to make the request succeed (*suggested* mode).

    Hosts can determine the mode of the operation based on which of the mutually exclusive **X-WOPI-RelativeTarget**
    (indicates *specific* mode) or **X-WOPI-SuggestedTarget** (indicates *suggested* mode) request headers is used. The
    expected behavior for each mode is described in detail below.

    Note that a file matching the target name might be locked, and in *specific* mode, the host must respond with a
    :statuscode:`409` and include a **X-WOPI-Lock** response header as described below.

    ..  include:: /_fragments/common_containers_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``CREATE_CHILD_FILE``. Required.

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
        A **Boolean** value that specifies whether the host must overwrite the file name if it exists. The default
        value is ``false``. In other words, if **X-WOPI-OverwriteRelativeTarget** is not explicitly included on the
        request, hosts must behave as though its value is ``false``.

        This header is only valid if the **X-WOPI-RelativeTarget** is also included on the request. It must be ignored
        in all other cases.

        If the user is not authorized to overwrite the target file, the host must respond with a :statuscode:`501`.

    :resheader X-WOPI-InvalidFileNameError:
        A **string** describing the reason the |operation| operation could not be completed. This header should only be
        included when the response code is :http:statuscode:`400`. This string is only used for logging purposes.

    :resheader X-WOPI-ValidRelativeTarget:
        A UTF-7 encoded **string** that specifies a full file name including the file extension. This header may be
        used when responding with a :statuscode:`409` because a file with the requested name already exists, or when
        responding with a :statuscode:`400` because the requested name contained invalid characters. If this
        response header is included, the WOPI client should automatically retry the |operation| operation using the
        contents of this header as the **X-WOPI-RelativeTarget** value and should not display an error message to the
        user.

    :code 200: Success
    :code 400: Specified name is illegal
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 409: Target file already exists
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
    The **string** name of the file, including extension, without a path.

Url
    A **string** URI of the form ``http://server/<...>/wopi/files/(file_id)?access_token=(access token)``, of the
    newly created file on the host. This URL is the :term:`WOPISrc` for the new file with an :term:`access token`
    appended. Or, stated differently, it is the URL to the host's :ref:`Files endpoint` for the new file, along with an
    :term:`access token`. A :method:`GET` request to this URL will invoke the :ref:`CheckFileInfo` operation.

    ..  include:: /_fragments/token_trading.rst


Optional response properties
----------------------------

HostViewUrl
    The :term:`HostViewUrl`, as a **string**, for the newly created file. This should match the value returned in
    :ref:`CheckFileInfo`.

..  glossary::

    HostEditNewUrl
        A URI to the :term:`host page` that loads the :wopi:action:`editnew` WOPI action.

HostEditUrl
    The :term:`HostEditUrl`, as a **string**, for the newly created file. This should match the value returned in
    :ref:`CheckFileInfo`.
