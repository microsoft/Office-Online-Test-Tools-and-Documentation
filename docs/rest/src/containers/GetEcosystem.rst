..  index:: WOPI requests; GetEcosystem (containers), GetEcosystem (containers)

..  |operation| replace:: GetEcosystem

..  _GetEcosystem (containers):

GetEcosystem (containers)
=========================

:Required for: |ios| |android|

..  default-domain:: http

..  get:: /wopi/containers/(container_id)/ecosystem_pointer

    The |operation| operation returns the URI for the WOPI server's :ref:`Ecosystem endpoint`, given a
    :term:`container` ID.

    ..  seealso::

        :ref:`GetEcosystem (files)`

    ..  include:: /_fragments/common_containers_params.rst

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 500: Server error
    :code 501: Operation not supported

    ..  include:: /_fragments/common_headers.rst


Response
--------

..  include:: /_fragments/json_response_required.rst

..  include:: /_fragments/ecosystem_url.rst
