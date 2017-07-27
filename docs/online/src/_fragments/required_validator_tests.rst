
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
* ProofKeys (applicable only for hosts implementing :ref:`proof key validation<proof keys>`)

..  tip::
    Hosts are *not* required to support :ref:`RenameFile` or use :ref:`proof key validation<proof keys>`. However, if
    you do, those test groups must be passing.
