
:orphan:

..  _requirements:

WOPI implementation requirements for Office Online integration
==============================================================

A WOPI host does not need to implement every WOPI operation. WOPI hosts express their capabilities using
`properties in CheckFileInfo <supports properties>`_, such as :term:`SupportsLocks`. In addition, WOPI actions
specify the WOPI operations that must be supported in order to use that action, in the form of
:ref:`Action requirements`.

However, practically speaking, there is a minimum set of operations required in order to support the two major
Office Online WOPI scenarios - viewing and editing.

..  important::

    Note that while the lists below cover the WOPI operations that must be implemented, hosts must also provide
    :term:`file IDs <File ID>` and :term:`access tokens <access token>` as part of a basic WOPI implementation.


View
----

In order to support viewing documents using Office Online, WOPI hosts must implement:

* :ref:`CheckFileInfo`
* :ref:`GetFile`

..  important::

    Note that :ref:`GetFile` must be implemented even if a host is using the :term:`FileUrl` property.


Edit
----

In order to support editing documents using Office Online, WOPI hosts must implement:

* :ref:`CheckFileInfo`
* :ref:`GetFile`
* :ref:`PutFile`
* :ref:`PutRelativeFile`
* :ref:`Lock`
* :ref:`Unlock`
* :ref:`UnlockAndRelock`
* :ref:`RefreshLock`
