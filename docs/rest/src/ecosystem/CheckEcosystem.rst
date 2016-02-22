
..  index:: WOPI requests; CheckEcosystem, CheckEcosystem

..  |operation| replace:: CheckEcosystem

..  _CheckEcosystem:

CheckEcosystem
==============

:Required for: |ios|

..  default-domain:: http

..  get:: /wopi/ecosystem

    The |operation| operation is similar to the the :ref:`CheckFileInfo` operation, but does not require a file or
    :term:`container` ID.

    ..  include:: /_fragments/access_token_param.rst

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 500: Server error

    ..  include:: /_fragments/common_headers.rst


Response
--------

..  include:: /_fragments/json_response.rst


Optional response properties
----------------------------

SupportsContainers
    Should match the :term:`SupportsContainers` property in :ref:`CheckFileInfo`.

..  note:: Since all properties in the |operation| response are optional, an empty JSON response is valid.
