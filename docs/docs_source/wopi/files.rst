
.. _Files endpoint:

Files endpoint
==============

The Files endpoint provides access to file-level operations.

The following table lists the operations that are exposed through this endpoint.

+------------------------+------------------------------------------------------------------------+
| Operation              | Description                                                            |
+========================+========================================================================+
| :ref:`CheckFileInfo`   | Returns information about a file and the capabilities of the           |
|                        | WOPI host.                                                             |
+------------------------+------------------------------------------------------------------------+
| :ref:`PutRelativeFile` | Creates a copy of a file on the WOPI server.                           |
+------------------------+------------------------------------------------------------------------+
| :ref:`Lock`            | Takes a lock for editing a file.                                       |
+------------------------+------------------------------------------------------------------------+
| :ref:`Unlock`          | Releases a lock for editing a file.                                    |
+------------------------+------------------------------------------------------------------------+
| :ref:`RefreshLock`     | Refreshes a lock for editing a file.                                   |
+------------------------+------------------------------------------------------------------------+
| :ref:`UnlockAndRelock` | Releases and then retakes a lock for editing a file.                   |
+------------------------+------------------------------------------------------------------------+
| :ref:`DeleteFile`      | Removes a file from the WOPI server.                                   |
+------------------------+------------------------------------------------------------------------+
| :ref:`RenameFile`      | Renames a file on the WOPI server.                                     |
+------------------------+------------------------------------------------------------------------+

Office Online invokes these operations by issuing an HTTP request to the Files endpoint. The **X-WOPI-Override** HTTP
header in the request contains the name of the operation to be invoked.

..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:

    files/*
