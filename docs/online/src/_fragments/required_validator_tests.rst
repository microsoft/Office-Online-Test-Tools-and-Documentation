
* HostFrameIntegration (ValidLanguagePlaceholderValues may be skipped if the host is not using the :term:`UI_LLCC` or
  :term:`DC_LLCC` placeholder values)
* BaseWopiViewing
* CheckFileInfoSchema
* EditFlows
* Locks
* AccessTokens
* PutRelativeFile **or** PutRelativeFileUnsupported
* RenameFileIfCreateChildFileIsNotSupported **or** RenameFileIfCreateChildFileIsSupported (applicable only if the
  host supports :ref:`RenameFile`)
* FileVersion
* PutUserInfo (applicable only if the host supports :ref:`PutUserInfo`)
* ProofKeys (applicable only for hosts implementing :ref:`proof key validation<proof keys>`)

..  tip::
    Hosts are *not* required to support :ref:`RenameFile`, :ref:`PutRelativeFile` or use :ref:`proof key
    validation<proof keys>`. However, hosts that do support these operations, or use application features that rely on
    them, those test groups must be passing.

    For example, :ref:`binary file conversion<conversion>` requires the :ref:`PutRelativeFile` operation be
    implemented, so hosts that support conversion must also pass the PutRelativeFile tests.
