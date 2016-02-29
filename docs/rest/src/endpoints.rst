
..  _Endpoints:

WOPI REST endpoints
===================

..  spelling::

    wopi


A WOPI host needs to provide some information about the files it stores, as well as the binary contents of those files.
Because WOPI is a REST-based callback interface, this information is provided via specific URLs. A WOPI host provides a
small REST API around its files. WOPI clients such as |wac| then use those REST API to work with the files.

Not all operations and endpoints are required. All actions require the :ref:`CheckFileInfo` and :ref:`GetFile`
operations.

Each WOPI endpoint supports a number of operations specified by the caller in the **X-WOPI-Override** request header.
Parameters for each operation are also passed in HTTP headers, which all begin with ``X-WOPI-``. This way, executing a
WOPI operation is as simple as issuing a request to the appropriate REST endpoint and passing appropriate HTTP header
values with the request.

..  seealso::
    :ref:`Common headers`


Endpoint URLs
-------------

All WOPI host endpoints must be located at a URL that *starts* with ``/wopi``. For example, all of the following
URLs are **valid** URLs for the :ref:`file contents endpoint` for a file with the ID ``abc123``:

* ``https://api.contoso.com/modules/wopi/files/abc123/contents``
* ``https://test.wopi.contoso.com/wopi_test/files/abc123/contents``

However, the following URLs are **not valid**:

* ``https://api.contoso.com/api_wopi/files/abc123/contents`` (invalid because the endpoint URL does not *start* with
  ``/wopi``)
* ``https://api.contoso.com/files/abc123/contents`` (invalid because the endpoint URL does not *start* with ``/wopi``)
* ``https://test.wopi.contoso.com/officeonline/files/abc123/contents`` (invalid because the endpoint URL does not
  *start* with ``/wopi``)
* ``https://wopi.contoso.com/wopi/files/ids/abc123/contents`` (invalid because the endpoint URL contains ``/ids``,
  which is not permitted)


.. _Files endpoint:

Files endpoint
--------------

The Files endpoint provides access to file-level operations.

:URL: ``/wopi/files/(file_id)``

The following operations are exposed through this endpoint:

* :ref:`CheckFileInfo`
* :ref:`PutRelativeFile`
* :ref:`Lock`
* :ref:`Unlock`
* :ref:`RefreshLock`
* :ref:`UnlockAndRelock`
* :ref:`DeleteFile`
* :ref:`RenameFile`


.. _File contents endpoint:

File contents endpoint
----------------------

The File contents endpoint provides access to retrieve and update the contents of a file.

:URL: ``/wopi/files/(file_id)/contents``

The following operations are exposed through this endpoint:

* :ref:`GetFile`
* :ref:`PutFile`


.. _Containers endpoint:

|stub-icon| Containers endpoint
-------------------------------

..  todo:: Write this...

:URL: ``/wopi/containers/(container_id)``

The following operations are exposed through this endpoint:

* :ref:`CheckContainerInfo`
* :ref:`CreateChildContainer`
* :ref:`CreateChildFile`
* :ref:`DeleteContainer`
* :ref:`EnumerateAncestors`
* :ref:`EnumerateChildren`
* :ref:`RenameContainer`


.. _Ecosystem endpoint:

|stub-icon| Ecosystem endpoint
------------------------------

The Ecosystem endpoint serves as a bridge for WOPI clients that do not have a File or Container ID that they are
operating on.

:URL: ``/wopi/ecosystem``

The following operations are exposed through this endpoint:

* :ref:`CheckEcosystem`
* :ref:`GetFileWopiSrc`
* :ref:`GetRootContainer`


.. _Bootstrapper endpoint:

|stub-icon| Bootstrapper endpoint
---------------------------------

..  todo:: Write this...

The following operations are exposed through this endpoint:

* :ref:`Bootstrap`
* :ref:`GetNewAccessToken`
* :ref:`shortcut operations`
