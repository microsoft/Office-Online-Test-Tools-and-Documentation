
..  index:: WOPI requests; EnumerateChildren (containers), EnumerateChildren (containers)

..  |operation| replace:: EnumerateChildren

..  _EnumerateChildren (containers):
..  _EnumerateChildren:

EnumerateChildren (containers)
==============================

:Required for: |ios|

..  default-domain:: http

..  get:: /wopi*/containers/(container_id)/children

    ..  todo:: Add note explaining that paging is deliberately not supported.

    The |operation| operation enumerates all the immediate children of a given :term:`container`. Note that paging is
    deliberately not supported by this operation. Responses are expected to include all immediate children of the
    container.

    ..  include:: /_fragments/common_containers_params.rst

    :reqheader X-WOPI-FileExtensionFilterList:
        A **string** value that the host must use to filter the returned child files. This header must be a list of
        comma-separated file extensions with a leading dot (``.``). There must be no whitespace and no trailing
        comma in the string.

        If this header is included, the host must only return child files whose file extensions match the filter
        list. For example, if this header is set to the value ``.doc,.docx``, the host should include only files with
        the ``doc`` or ``docx`` file extension.

        This header value does not have any effect on child containers.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 500: Server error
    :code 501: Unsupported

    ..  include:: /_fragments/common_headers.rst

Response
--------

..  include:: /_fragments/json_response.rst

Required response properties
----------------------------

The following properties must be present in all |operation| responses:


ChildContainers
~~~~~~~~~~~~~~~

An array of JSON-formatted objects containing the following properties:

..  include:: /_fragments/container_pointer.rst

If there are no child containers, this property should be an empty array.


ChildFiles
~~~~~~~~~~

An array of JSON-formatted objects containing the following properties:

..  include:: /_fragments/child_file.rst

If there are no child files, this property should be an empty array.


Sample response
---------------

..  literalinclude:: /_fragments/responses/EnumerateChildren.json
    :language: JSON
