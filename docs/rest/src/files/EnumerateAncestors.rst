
..  index:: WOPI requests; EnumerateAncestors (files), EnumerateAncestors (files)

..  |operation| replace:: EnumerateAncestors

..  _EnumerateAncestors (files):
..  _EnumerateAncestors:

EnumerateAncestors (files)
==========================

:Required for: |ios| |android|

..  seealso::

    :ref:`EnumerateAncestors (containers)`

..  default-domain:: http

..  get:: /wopi/files/(file_id)/ancestry

    ..  include:: /_fragments/common_params.rst

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

Consider a file in the following container hierarchy: ``/root/grandparent/parent/myfile.docx``. When called on
this file, the |operation| operation should return the following:

..  literalinclude:: /_fragments/responses/EnumerateAncestors.json
    :language: JSON
