
..  index:: WOPI requests; EnumerateChildren, EnumerateChildren

..  _EnumerateChildren:

EnumerateChildren
=================

..  default-domain:: http

..  get:: /wopi*/folders/(id)/children

    ..  include:: /fragments/deprecated_warning.rst

    The EnumerateChildren operation returns the contents of a folder.

    The EnumerateChildren response contains URLs in the response body JSON that Office Online then uses to initiate
    further WOPI operations on the items in the folder.

    ..  include:: /fragments/common_params.rst

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Folder unknown/user unauthorized
    :code 500: Server error
    :code 501: Unsupported

Response
--------

The response to an EnumerateChildren call is JSON (as specified in :rfc:`4627`) containing a single parameter,
``Children``. The ``Children`` parameter should be an array of JSON-formatted objects with the following three
parameters:

..  No glossary since these terms shouldn't be referenceable.

Name
    The name of the child resource.
Url
    The URI of the child resource of the form ``http://server/<...>/wopi*/files/<id>?access_token=<token>`` where
    ``id`` is the WOPI server's unique id of the resource and ``token`` is the token that provides access to the
    resource.
Version
    The current version of the resource based on the host's file versioning schema. This value must change when the
    resource changes, and version values must never repeat for a given resource. This value must match the value that
    would be provided by the :term:`Version` field in the :ref:`CheckFileInfo` response.

Example Response
~~~~~~~~~~~~~~~~

..  code-block:: js

    {
      "Children": [
        {
          "Name": "File1.docx",
          "Url": "http:\/\/wopiserver\/wopi\/files\/file_id_1234?access_token=abcdefg",
          "Version": "5"
        }
      ]
    }
