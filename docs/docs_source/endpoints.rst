
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
required for particular actions. All actions require the CheckFileInfo (exposed via the Files endpoint) and GetFile
(exposed via the File Contents endpoint) operations.

This section describes some of the most relevant endpoints and operations. For information about all endpoints and
operations, see [MS-WOPI] section 3.3.5.

File ID requirements
--------------------

The Office files stored on your server must have unique IDs. The WOPI host uses these IDs to find files. The file IDs
must:

* Represent a single Office document.
* Be a URL-safe string, because IDs are sometimes passed in URLs.
* Remain the same when the file is moved, renamed, or edited.

Access token requirements
-------------------------

A WOPI host needs to provide access tokens that represent users and their permissions. The WOPI host that stores the
file has the information about user permissions, not Office Online. For this reason, the WOPI host must provide a
token that Office Online will then pass back to it to validate. When the WOPI host receives the token, it either
validates it, or responds with an HTTP status code if the token is invalid. The access tokens must:

* Be scoped to a single user and document combination.
* Expire after a period of time.

Executing WOPI operations
-------------------------

Each WOPI endpoint supports a number of operations specified by the caller in the **X-WOPI-Override** request header.
Parameters for each operation are also passed in HTTP headers, which all begin with ``X-WOPI-``. This way, executing a
WOPI operation is as simple as issuing a request to the appropriate REST endpoint and passing appropriate HTTP header
values with the request. The following sections provide information about the headers that are valid for each
operation. For more details, see [MS-WOPI] section 3.3.5.

Files endpoint
--------------

The Files endpoint provides access to file-level operations.

The following table lists the operations that are exposed through this endpoint.

+-----------------+------------------------------------------------------------------------+
| Operation       | Description                                                            |
+=================+========================================================================+
| CheckfileInfo   | Returns information about a file and the capabilities of the           |
|                 | WOPI host.                                                             |
+-----------------+------------------------------------------------------------------------+
| PutRelativeFile | Creates a copy of a file on the WOPI server.                           |
+-----------------+------------------------------------------------------------------------+
| Lock            | Takes a lock for editing a file.                                       |
+-----------------+------------------------------------------------------------------------+
| Unlock          | Releases a lock for editing a file.                                    |
+-----------------+------------------------------------------------------------------------+
| RefreshLock     | Refreshes a lock for editing a file.                                   |
+-----------------+------------------------------------------------------------------------+
| UnlockAndRelock | Releases and then retakes a lock for editing a file.                   |
+-----------------+------------------------------------------------------------------------+
| DeleteFile      | Removes a file from the WOPI server.                                   |
+-----------------+------------------------------------------------------------------------+
| RenameFile      | Renames a file on the WOPI server.                                     |
+-----------------+------------------------------------------------------------------------+

Office Online invokes these operations by issuing an HTTP request to the Files endpoint. The ``X-WOPI-Override`` HTTP
header in the request contains the name of the operation to be invoked.

For more information about the Files endpoint and the operations that it exposes, see [MS-WOPI] section 3.3.5.1.

CheckFileInfo operation
~~~~~~~~~~~~~~~~~~~~~~~

The CheckFileInfo operation is one of the most important WOPI operations. This operation must be implemented for all
WOPI actions. CheckFileInfo returns information about a file, a user's permissions on that file, and general
information about the capabilities that the WOPI host has on the file. In addition, some CheckFileInfo properties can
influence the appearance and behavior of Office Online.

For a list and descriptions of the properties that the CheckFileInfo operation can contain, see
[MS-WOPI] section 3.3.5.1.1.2. The following sections provide additional information about the most important
properties.

Required properties
^^^^^^^^^^^^^^^^^^^

The following properties must be present in all CheckFileInfo responses:

* BaseFileName
* OwnerId
* Size
* Version

Recommended minimum properties
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Although the CheckFileInfo response only requires you to set a few properties, we recommend that you set the
following properties for most hosts, at a minimum.

HostViewUrl and HostEditUrl properties
""""""""""""""""""""""""""""""""""""""

Set the **HostViewUrl** and **HostEditUrl** properties if you support file viewing or editing, or both. Office Online
uses these values in a number of places; most importantly, the HostEditUrl property contains the URL that is stored
in the recent documents list if a ClientUrl is not provided.

Supports* properties
""""""""""""""""""""

The **Supports\*** properties indicate to Office Online the WOPI capabilities that the host provides for a file. Set all
applicable **Supports\*** properties to ``true``.

The **Supports\*** properties include:

* SupportsCoauth
* SupportsCobalt
* SupportsFolders
* SupportsLocks
* SupportsRename
* SupportsScenariosLinks
* SupportsSecureStore
* SupportsUpdate

CloseUrl property
"""""""""""""""""
Set the **CloseUrl** property to define where users are directed when the application closes, or in the event of an
error.

UserId property
"""""""""""""""

A host is only required to specify the **OwnerId** property, which represents the owner of a given document. However, we
recommend that you set the **UserId** property as well.

SHA256 property
"""""""""""""""
Set the **SHA256** property for view scenarios. This value allows Office Online to take advantage of more performant 
caching strategies. We recommend that you set this property for edit scenarios as well.

UserFriendlyName property
"""""""""""""""""""""""""
The UserFriendlyName property is used to display the user's name in the Office Online UI.

UserCan* properties
"""""""""""""""""""

Office Online always assumes that users have limited permissions to documents. If you do not set the appropriate
**UserCan\*** properties, users will not be able to perform operations such as editing documents in Office Online.

The **UserCan\*** properties include:

* UserCanAttend
* UserCanNotWriteRelative
* UserCanPresent
* UserCanRename
* UserCanWrite

Additional properties that affect Office Online
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The following **CheckFileInfo** properties affect Office Online behavior.

ClientUrl property
""""""""""""""""""

If you set the **ClientUrl** property, Office Online will expose buttons in the UI that enable users to open documents
in Office for Windows, iOS, or Android. To integrate with Office Online, you must use a DAV URL for this property.

FileUrl property
""""""""""""""""

If you set the **FileUrl** property, Office Online will use this URL to retrieve the file, rather than the **GetFile**
operation on the File contents REST endpoint. For example, you might set this property if it is easier or more
performant to serve your files from a different domain.

PostMessage properties
""""""""""""""""""""""

The PostMessage properties control the behavior of Office Online with respect to incoming PostMessages. Note that if
you are using the PostMessage extensibility features of Office Online, you must set the **PostMessageOrigin**
property to ensure that Office Online accepts messages from your outer frame.

The PostMessage properties include:

* ClosePostMessage
* EditNotificationPostMessage
* FileSharingPostMessage
* PostMessageOrigin

Breadcrumb* properties
""""""""""""""""""""""

**Breadcrumb\*** properties determine what is displayed in the breadcrumb area within the Office Online UI. Office
Online does not use the **BreadcrumbDocUrl** property.

The **Breadcrumb\*** properties include:

* BreadcrumbBrandName
* BreadcrumbBrandUrl
* BreadcrumbDocName
* BreadcrumbFolderName
* BreadcrumbFolderUrl

.. _FileNameMaxLength:

FileNameMaxLength property
""""""""""""""""""""""""""

The **FileNameMaxLength** property is an integer that indicates the maximum length for file names that the WOPI host
supports, excluding the file extension. The default value is 250. This property is optional unless you want to
enable file renaming within Office Online, in which case it is required.

PutRelativeFile operation
~~~~~~~~~~~~~~~~~~~~~~~~~

The PutRelativeFile operation creates a new file on the WOPI server based on the current file. For more information
about this operation, see [MS-WOPI] section 3.3.5.1.2.

..  admonition:: Excel Online Note

    Excel Online uses this operation as part of the *Save As* feature. If this operation is not supported, the Save As
    feature will not work in Excel Online.

Lock operation
~~~~~~~~~~~~~~

The **Lock** operation locks a file for editing by the Office Online application instance that requested the lock.
To support editing files, Office Online requires that the WOPI host support locking files. When locked, a file should
not be writable by other applications, including Office Online.

Office Online will always pass the ID of the lock (a string) as a parameter to operations that would modify the
content of a file. The host must ensure that a "lock mismatch" response is returned if the lock passed does not match
the lock currently on the file. The host should implement locks in such a way that applications other than Office
Online do not edit the file while it is locked by another editor. The maximum length of a lock ID is 256 characters.

Locks should expire automatically after 30 minutes. Office Online can reset this timeout by means of a
:ref:`RefreshLock` request.

For more information about the Lock operation, see [MS-WOPI] section 3.3.5.1.3.

Unlock operation
~~~~~~~~~~~~~~~~

The **Unlock** operation releases the lock on a file. For more information, see [MS-WOPI] section 3.3.5.1.4.

..  _RefreshLock:

RefreshLock operation
~~~~~~~~~~~~~~~~~~~~~

The **RefreshLock** operation refreshes the lock on a file by resetting its automatic expiration timer to 30 minutes.
For more information, see [MS-WOPI] section 3.3.5.1.5.

UnlockAndRelock operation
~~~~~~~~~~~~~~~~~~~~~~~~~

The **UnlockAndRelock** operation releases a lock on a file, and then immediately takes a new lock on the file. For more
information, see [MS-WOPI] section 3.3.5.1.6. This operation must be an atomic.

RenameFile operation
~~~~~~~~~~~~~~~~~~~~

Office Online includes contains UI that enables users can use to rename files. In order to activate this UI in Office
Online, you must implement the **RenameFile** operation, and also do the following:

* Set **SupportsRename** and **UserCanRename** to true in your CheckFileInfo response.
* Set a :ref:`FileNameMaxLength` value if the default value is not correct for your WOPI host.

The **RenameFile** operation renames an existing file.

The request for this operation contains the following HTTP headers.

+----------------------+---------------------------------------+-------------------------------------------------+
| Request header       | Usage                                 | Value                                           |
+======================+=======================================+=================================================+
| X-WOPIOverride       | A string that specifies the requested | The string ``RENAME_FILE``.                     |
|                      | operation from the WOPI server.       |                                                 |
|                      | Required.                             |                                                 |
+----------------------+---------------------------------------+-------------------------------------------------+
| X-WOPI-RequestedName | A string that specifies the requested | A UTF-7 encoded string that is a file name, not |
|                      | name of the file.                     | including the file extension.                   |
+----------------------+---------------------------------------+-------------------------------------------------+

Response
^^^^^^^^

The response to this operation can result incontain the following status codes.

===========    ===========
Status code    Description
===========    ===========
200            Success
400            Specified name is illegal
401            Token is invalid
404            File unknown/User unauthorized
409            Lock mismatch/Locked by another interface
500            Server error
501            Unsupported
===========    ===========

The response body is JSON and includes the following parameters.

..  code-block:: text

    {
        "Name": { "type": "string", "optional" :false }
    }

**Name**: the new name of the file, excluding the file extension.

Processing details
^^^^^^^^^^^^^^^^^^

If the host cannot rename the file because the name requested is invalid or conflicts with an existing file, the host
should try to generate a different name based on the requested name that meets the file name requirements.

If the host cannot generate a different name, it should return an HTTP status code 400. The response must include an
**X-WOPI-InvalidFileNameError** header that describes why the file name was invalid.

Folders endpoint
----------------

The Folders endpoint provides access to folder-level operations. This endpoint exposes the **CheckFolderInfo**
operation, which returns information about a folder, the permissions that the user has on that folder, and the
capabilities that the WOPI host has on the folder.

For more information about the Folders endpoint, see [MS-WOPI] section 3.3.5.2. For more information about the
**CheckFolderInfo** operation, see [MS-WOPI] section 3.3.5.2.1.

..  note::

    Only OneNote Online uses the Folders endpoint and the operations that it exposes.

File contents endpoint
----------------------

The File contents endpoint provides access to retrieve and update the contents of a file. For more information about
this endpoint, see [MS-WOPI] section 3.3.5.3.

The following table lists the operations that are exposed through this endpoint.

+-----------------+-----------------------------------------+------------------------------+
| Operation       | Description                             | More information             |
+=================+=========================================+==============================+
| GetFile         | Returns the full binary contents of a   | [MS-WOPI] section 3.3.5.3.1  |
|                 | file.                                   |                              |
+-----------------+------------------------------------------------------------------------+
| PutFile         | Sets the full binary contents of a      | [MS-WOPI] section 3.3.5.3.2  |
|                 | file to the value passed.               |                              |
+-----------------+------------------------------------------------------------------------+

Folder contents endpoint
------------------------

The Folder contents endpoint provides access to folder contents. For more information about this endpoint, see
[MS-WOPI] section 3.3.5.4.

This endpoint exposes the **EnumerateChildren** operation, which returns the contents of a folder on the WOPI server.
For more information, see [MS-WOPI] section 3.3.5.4.1.

..  note::

    Only OneNote Online uses the **EnumerateChildren** operation.
