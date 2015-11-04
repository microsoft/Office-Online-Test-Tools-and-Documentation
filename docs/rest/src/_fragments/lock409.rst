
If the file is currently locked and the **X-WOPI-Lock** value does not match the lock currently on the file, or if
the file is unlocked, the host must return a "lock mismatch" response (:http:statuscode:`409`) and include an
**X-WOPI-Lock** response header containing the value of the current lock on the file. In the case where the file is
unlocked, the host must set **X-WOPI-Lock** to the empty string.
