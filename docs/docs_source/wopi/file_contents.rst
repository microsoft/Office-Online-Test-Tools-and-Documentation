
.. _File contents endpoint:

File contents endpoint
======================

The File contents endpoint provides access to retrieve and update the contents of a file.

The following table lists the operations that are exposed through this endpoint.

+-----------------+-----------------------------------------+
| Operation       | Description                             |
+=================+=========================================+
| :ref:`GetFile`  | Returns the full binary contents of a   |
|                 | file.                                   |
+-----------------+-----------------------------------------+
| :ref:`PutFile`  | Sets the full binary contents of a      |
|                 | file to the value passed.               |
+-----------------+-----------------------------------------+

For general information about invoking WOPI operations, see :ref:`Executing WOPI operations`.

..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:

    file_contents/*
