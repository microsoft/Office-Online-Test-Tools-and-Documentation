
.. meta::
    :robots: noindex

:orphan:

OneNote
=======

|onenote-web| integration is not included in the |cspp|.


.. _Folders endpoint:

Folders endpoint
================

..  include:: /_fragments/deprecated_endpoint.rst

The Folders endpoint provides access to folder-level operations. This endpoint exposes the :ref:`CheckFolderInfo`
operation, which returns information about a folder, the permissions that the user has on that folder, and the
capabilities that the WOPI host has on the folder.


.. _Folder contents endpoint:

Folder contents endpoint
========================

..  include:: /_fragments/deprecated_endpoint.rst

The Folder contents endpoint provides access to folder contents. This endpoint exposes the
:ref:`EnumerateChildren (folders)` operation, which returns the contents of a folder on the WOPI server.
