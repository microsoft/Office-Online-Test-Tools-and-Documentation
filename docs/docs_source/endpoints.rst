
..  _Endpoints:

WOPI REST endpoints
===================

A WOPI host needs to provide some information about the files it stores, as well as the binary contents of those files.
Because WOPI is a REST-based callback interface, this information is provided via specific URLs. A WOPI host provides a
small REST API around its files. Office Online uses those REST API to work with the files.

The following table shows the structure of the REST API for each resource type.

+----------------+-----------------------------------------------------+-----------------------------------------------+
| Resource       | URL                                                 | Description                                   |
+================+=====================================================+===============================================+
| Files          | HTTP://server/<...>/wopi*/files/<file_id>           | Provides access to information about a file   |
|                |                                                     | and allows for file-level operations.         |
+----------------+-----------------------------------------------------+-----------------------------------------------+
| Folders        | HTTP://server/<...>/wopi*/folders/<file_id>         | Provides access to information about a folder |
|                |                                                     | and allows for folder-level operations.       |
+----------------+-----------------------------------------------------+-----------------------------------------------+
| File contents  | HTTP://server/<...>/wopi*/files/<file_id>/contents  | Provides access to operations that get and    |
|                |                                                     | update the contents of a file.                |
+----------------+-----------------------------------------------------+-----------------------------------------------+
| Folder contents| HTTP://server/<...>/wopi*/folder/<file_id>/children | Provides access to the files and folders in   |
|                |                                                     | a folder.                                     |
+----------------+-----------------------------------------------------+-----------------------------------------------+

Not all operations and endpoints are required. The discovery XML describes which WOPI endpoints and operations are
required for particular actions. All actions require the :ref:`CheckFileInfo` (exposed via the :ref:`Files endpoint`)
and :ref:`GetFile` (exposed via the :ref:`File contents endpoint`) operations.

..  _Executing WOPI operations:

Executing WOPI operations
-------------------------

Each WOPI endpoint supports a number of operations specified by the caller in the **X-WOPI-Override** request header.
Parameters for each operation are also passed in HTTP headers, which all begin with ``X-WOPI-``. This way, executing a
WOPI operation is as simple as issuing a request to the appropriate REST endpoint and passing appropriate HTTP header
values with the request.

The following URI parameters must be included with all WOPI requests.

=============  ===========
URI Parameter  Description
=============  ===========
``token``      A string representing the :term:`Access Token` for the request.
``id``         A string representing the :term:`File ID` for the request.
=============  ===========

The `token` and `id` parameters are a core part of all WOPI requests. The URI syntax for using these parameters is
described in the documentation for each WOPI operation. The host provides both `token` and `id` by transforming the
**urlsrc** value for the action (provided in :ref:`discovery`) and appending parameters to the URL as described in
:ref:`Action URLs`.

The following URI parameters may also be included with all WOPI requests.

=============  ===========
URI Parameter  Description
=============  ===========
``sc``         A string representing the :term:`Session Context` for the request.
=============  ===========

..  _File ID requirements:

File ID requirements
--------------------

The Office files stored on your server must have unique IDs. The WOPI host uses these IDs to find files. The file IDs
must:

* Represent a single Office document.
* Be a URL-safe string, because IDs are sometimes passed in URLs.
* Remain the same when the file is moved, renamed, or edited.
* In the case of shared documents, the ID for a given file must be the same for any user that accesses the file.

..  _Access token requirements:

Access token requirements
-------------------------

A WOPI host needs to provide access tokens that represent users and their permissions. The WOPI host that stores the
file has the information about user permissions, not Office Online. For this reason, the WOPI host must provide a
token that Office Online will then pass back to it to validate. When the WOPI host receives the token, it either
validates it, or responds with an HTTP status code if the token is invalid. The access tokens must:

* Be scoped to a single user and document combination.
* Expire after a period of time.

Office Online requires no understanding of the format or content of the token.
