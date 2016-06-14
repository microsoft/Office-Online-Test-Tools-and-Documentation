
..  _requirements:

WOPI implementation requirements for |Office iOS| integration
=============================================================

This section documents specific requirements for your WOPI implementation for integration with |Office iOS| beyond
what is documented in general for WOPI. You can learn more about how |Office iOS| uses these operations in the :ref:`operational flows` section. 


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
* :ref:`UnlockAndRelock`
* :ref:`PutFile`
* :ref:`EnumerateAncestors (files)`


Container Operations
~~~~~~~~~~~~~~~~~~~~

* :ref:`CheckContainerInfo`
* :ref:`CreateChildFile`
* :ref:`EnumerateAncestors (containers)`
* :ref:`EnumerateChildren (containers)`


Ecosystem Operations
~~~~~~~~~~~~~~~~~~~~

* :ref:`CheckEcosystem`
* :ref:`GetRootContainer (ecosystem)`


Bootstrapper
~~~~~~~~~~~~

* :ref:`Bootstrap`
* :ref:`GetNewAccessToken`
* :ref:`GetRootContainer (bootstrapper)`, if :ref:`GetRootContainer (ecosystem)` isn't implemeneted


Future Support
~~~~~~~~~~~~~~

While these WOPI operations are not current used by |Office iOS|, they must be implemented. |Office iOS| will use
these operations in the future.

* :ref:`RenameFile`
* :ref:`DeleteFile`
* :ref:`CreateChildContainer`
* :ref:`DeleteContainer`
* :ref:`RenameContainer`
* :ref:`GetEcosystem (files)`
* :ref:`GetEcosystem (containers)`


Other Requirements
~~~~~~~~~~~~~~~~~~

* The **X-WOPI-ItemVersion** header must be included on :ref:`PutFile`, :ref:`Lock`, and :ref:`Unlock` responses
* For the :ref:`Bootstrap` operation, the :http:header:`Content-Type` response header must be set to
  ``application/json``
