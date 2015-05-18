
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

For general information about invoking WOPI operations, see :ref:`Executing WOPI operations`.

..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:

    files/*
