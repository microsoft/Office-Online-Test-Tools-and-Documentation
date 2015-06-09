
In the case where the file is locked by someone other than Office Online, hosts should still always include the current
lock ID in the **X-WOPI-Lock** response header. However, if the current lock ID is not representable as a WOPI lock
(for example, it is longer than 256 ASCII characters), the **X-WOPI-Lock** response header should be set to the empty
string or omitted completely.
