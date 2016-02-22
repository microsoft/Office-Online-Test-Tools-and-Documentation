
..  index:: WOPI requests; GetEcosystem (files), GetEcosystem (files)

..  |operation| replace:: GetEcosystem

..  _GetEcosystem (files):
..  _GetEcosystem:

GetEcosystem (files)
====================

:Required for: |ios|

..  seealso::

    :ref:`GetEcosystem (containers)`


..  default-domain:: http

..  get:: /wopi/files/(file_id)/ecosystem_pointer

    The |operation| operation returns the URI for the WOPI server's :ref:`Ecosystem endpoint`, given a
    file ID.

    ..  seealso::

        :ref:`GetEcosystem (containers)`


    ..  include:: /_fragments/common_params.rst

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 500: Server error
    :code 501: Unsupported

    ..  include:: /_fragments/common_headers.rst


Response
--------

..  include:: /_fragments/json_response_required.rst

..  include:: /_fragments/ecosystem_url.rst
