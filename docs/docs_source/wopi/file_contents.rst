
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

Office Online invokes these operations by issuing an HTTP request to the File contents endpoint. The
**X-WOPI-Override** HTTP header in the request contains the name of the operation to be invoked.

..  toctree::
    :maxdepth: 2
    :glob:
    :hidden:

    file_contents/*
