
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


.. _Files endpoint:

Files endpoint
--------------

The Files endpoint provides access to file-level operations.

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

The following operations are exposed through this endpoint:

* :ref:`GetFile`
* :ref:`PutFile`


.. _Containers endpoint:

|stub-icon| Containers endpoint
-------------------------------

..  todo:: Write this...


.. _Ecosystem endpoint:

|stub-icon| Ecosystem endpoint
------------------------------

The Ecosystem endpoint serves as a bridge for WOPI clients that do not have a File or Container ID that they are
operating on.

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
