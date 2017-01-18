..  index:: WOPI requests; CheckPolicy, CheckPolicy

..  |operation| replace:: CheckPolicy

..  _CheckPolicy:

|stub-icon| CheckPolicy
=======================

..  default-domain:: http

..  post:: /wopi/files/(file_id)

    ..  include:: ../../../_shared/stub.rst

    .. |issue| issue:: 288

    The |operation| operation checks that an array of strings complies with the WOPI host's policy.

    ..  include:: /_fragments/common_params.rst

    :reqheader X-WOPI-Override:
        The **string** ``CHECK_POLICY``. Required.

    :reqjson string[] Fragments: The array of string fragments that should be checked for policy adherence.
    :reqjson string LoggingString: This string value should be logged by the WOPI host for debugging purposes. It
        must not be used for any other purpose.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 500: Server error
    :code 501: Operation not supported

    ..  include:: /_fragments/common_headers.rst


Response
--------

..  include:: /_fragments/json_response_required.rst

Block
    A **Boolean** value that, when set to ``true``, indicates that the WOPI client must block the
    policy-sensitive operation.

Warn
    A **Boolean** value that, when set to ``true``, indicates that the WOPI client should warn the user that their
    content does not comply with the host's policy. The WOPI client may continue the policy-sensitive operation.

BlockedWords
    An **array of strings** containing the words that were blocked by the WOPI host's policy.


Sample response
---------------

..  literalinclude:: /_fragments/responses/CheckPolicy.json
    :language: JSON
