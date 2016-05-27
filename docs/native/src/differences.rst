..  _differences:

Differences between |Office iOS| and |wac|
==========================================

Office for iOS has two WOPI implementation requirements which are not relevant to Office Online:
 * Containers: Instead of using the ecosystem to traverse the WOPI space, Office for iOS uses containers. A container represents a folder on the cloud storage provider, and a Root Container is a top-level folder. 
 * Bootstrapper: The Bootstrapper allows an app with a valid OAuth 2.0 access token to retrieve WOPI access tokens for WOPI operations rather than have them provided by the host.
 
 You can find more information about these operations in the :ref:`WOPI implementation requirements for Office for iOS integration section. <requirements>`

Office Online has the following WOPI implementation requirements which are not relevant to Office for iOS: 
 * Proof keys
 * Breadcrumbs
 * File Operations: UnlockAndRelock, PutRelativeFile, PutUserInfo 

