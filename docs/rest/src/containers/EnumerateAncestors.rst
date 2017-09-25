
..  index:: WOPI requests; EnumerateAncestors (containers), EnumerateAncestors (containers)

..  |operation| replace:: EnumerateAncestors

..  _EnumerateAncestors (containers):

EnumerateAncestors (containers)
===============================

:Required for: |ios| |android|

..  default-domain:: http

..  get:: /wopi/containers/(container_id)/ancestry

    The |operation| operation enumerates all the parents of a given :term:`container`, up to and including the
    :term:`root container`.

    ..  include:: /_fragments/common_containers_params.rst

    :resheader X-WOPI-EnumerationIncomplete:
        An optional header indicating that the enumeration of the container's ancestry is incomplete. If set, the value
        of this header must be the string ``true``.

        A WOPI client may choose to issue additional |operation| requests to complete the enumeration.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 500: Server error
    :code 501: Operation not supported

    ..  include:: /_fragments/common_headers.rst

Response
--------

..  include:: /_fragments/responses/EnumerateAncestors.rst


Sample response
---------------

Consider a file in the following container hierarchy: ``/root/grandparent/parent/mycontainer``. When called on
this container, the |operation| operation should return the following:

..  literalinclude:: /_fragments/responses/EnumerateAncestors.json
    :language: JSON
