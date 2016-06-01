..  _requirements:

WOPI implementation requirements for |Office iOS| integration
=========================================================================

This section documents specific requirements for your WOPI implementation for integration with |Office iOS| beyond
what is documented in general for WOPI.

Required WOPI Operations for |Office iOS|
-----------------------------------------

The following WOPI operations are required for integration on |Office iOS|. Operations not listed here are not 
currently called by |Office iOS|. 

If some operations are not supported for a specific user/file/container, just make sure to return the correct values 
in :ref:`CheckFileInfo` and :ref:`CheckContainerInfo`.

File Operations
~~~~~~~~~~~~~~~

* :ref:`CheckFileInfo`
* :ref:`GetFile`
* :ref:`Lock`
* :ref:`GetLock`
* :ref:`RefreshLock`
* :ref:`Unlock`
* :ref:`PutFile`
* :ref:`EnumerateAncestors`

Container Operations
~~~~~~~~~~~~~~~~~~~~

* :ref:`CheckContainerInfo`
* :ref:`CreateChildFile`
* :ref:`EnumerateAncestors (containers)`
* :ref:`EnumerateChildren`

Bootstrapper
~~~~~~~~~~~~

* :ref:`Bootstrap`
* :ref:`GetNewAccessToken`
* :ref:`Shortcut operations` (Of these, only :ref:`GetRootContainer (bootstrapper)` is required)

Future Support
~~~~~~~~~~~~~~

It is recommended the following operations also be implemented - it is expected the |Office iOS| integration will use these in the future.

* :ref:`RenameFile`
* :ref:`DeleteFile`
* :ref:`CreateChildContainer`
* :ref:`DeleteContainer`
* :ref:`RenameContainer`

Other Requirements
~~~~~~~~~~~~~~~~~~

* The **X-WOPI-ItemVersion** header must be included on :ref:`PutFile`, :ref:`Lock`, and :ref:`Unlock` responses
* For the :ref:`Bootstrap` operation, the :http:header:`Content-Type` response header must be set to
  ``application/json``
  