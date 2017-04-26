
|Office iOS| will fail to load files with invalid characters in the file name, folder names or user ID
======================================================================================================

|Office iOS| will fail to load files if there are invalid characters in certain :ref:`CheckFileInfo` or
:ref:`CheckContainerInfo` properties. The following characters are not allowed::

    \/:*?"<>|#{}^[]`%

These properties are affected:

CheckFileInfo
    * :term:`BaseFileName`
    * :term:`UserId`
    * :term:`OwnerId`

CheckContainerInfo
    * Name

To work around this issue, hosts should replace these characters with ``-``.
