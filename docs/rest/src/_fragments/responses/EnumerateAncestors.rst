
..  include:: /_fragments/json_response_required.rst

AncestorsWithRootFirst
    An array of JSON-formatted objects containing the following properties:

    ..  include:: /_fragments/container_pointer.rst

    The array must always be ordered such that the ancestor closest to the root is the first element.

    If there are no ancestor containers, this property should be an empty array.
