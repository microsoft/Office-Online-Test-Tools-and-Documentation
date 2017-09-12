
..  _requirements:

WOPI implementation requirements for |Office iOS Android| integration
=====================================================================

Note: Following section is applicable |Office iOS Android|. Both of these endpoints require the same level of WOPI implementation.

This section documents specific requirements for your WOPI implementation for integration with |Office iOS Android| beyond
what is documented in general for WOPI. You can learn more about how |Office iOS Android| uses these operations in the
:ref:`operational flows` section.


Required WOPI Operations for |Office iOS Android|
-------------------------------------------------

The following WOPI operations are required for integration on |Office iOS Android|. Operations not listed here are not
currently called by |Office iOS Android|.

If some operations are not supported for a specific user/file/container; just make sure to return the correct values
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
* :ref:`GetRootContainer (bootstrapper)`


Future Support
~~~~~~~~~~~~~~

While these WOPI operations are not currently used by |Office iOS Android|, they must be implemented. |Office iOS Android| will use
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
* :term:`IsEduUser` and :term:`LicenseCheckForEditIsEnabled` are required on :ref:`CheckFileInfo` and
  :ref:`CheckContainerInfo`. The values from CheckFileInfo must match that of the file's parent container.
