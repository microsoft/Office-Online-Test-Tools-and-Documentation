
..  _operational flows:

Operational flows
=================

..  spelling::

    auth
    Auth
    OAuth

This section outlines the implementation requirements and operational flows for user scenarios outlined in the
:ref:`browse files` section.


Terminology
-----------

User
    Self-explanatory

Office Client
    The |Office iOS| app, i.e. Excel/Word/PowerPoint

Office Identity Service
    The Office Identity service is a service |Office iOS| uses for handling user identity information. In OAuth flows,
    the Office Identity Service behaves as the External User Agent
 
Bootstrapper URL
    URL which the Storage Provider hosts to allow an Office Client app with a valid OAuth 2.0 access token to retrieve
    a WOPI access token
 
Token Endpoint URL
    URL which the Storage Provider hosts and uses in order to generate access or refresh tokens
 
Services Manager
    Office-owned service which stores a Service Catalog, listing all of the available services for an app or endpoint
 
Storage provider
    Refers to a CSPP partner who has integrated their service into |Office iOS|

..  note::
    * Any time during these flows that |Office iOS| tries to hit the bootstrapper and have expired or invalid OAuth
      credentials, |Office iOS| will ask the user to log in again.
    * Any time during these flows that |Office iOS| has a missing, invalid, or expired WOPI access token, |Office iOS|
      will attempt to get a valid WOPI access token by calling :ref:`GetNewAccessToken`.


Add a Place 
-----------

This describes the first time a user adds your storage provider using the :guilabel:`Add a Place` in |Office iOS|. To
correctly authenticate users, we use the following operational flow in order to connect a user's storage provider
account with their |Office iOS| account.

..  figure:: ../images/user_flows.png
    :alt: Image representation of the authentication flow for adding a place.

 
#. When the Office client boots, it calls the Services Manager for a list of available services. The Services Manager
   returns a Service Catalog, which is an alphabetically-sorted list of available providers and places which the user
   can connect to.
#. When the user clicks :guilabel:`Add a Place` in the |Office iOS| backstage, they see your Storage Provider listed
   as an available place.
#. When the users adds the Storage Provider, the Office client makes an unauthenticated :ref:`Bootstrap` call.
#. The :ref:`Bootstrap` response includes the Authorization URI and token issue URI. Note that the Authorization URI
   must be provided as part of your :ref:`onboarding` so we can add it as a trusted domain.
#. |Office iOS| loads the Authorization URI in a web view which prompts the user to sign in with the service
   provider.  Once the user finishes the sign in process, the web view reaches a redirect URI which causes it to close.
   The redirect URI also provides an auth code to |Office iOS|.
#. |Office iOS| sends the auth code and token issue URI to the Office Identity Service.
#. The Office Identity Service sends the auth code and the client secret to the Token endpoint.
#. The Token Endpoint issues an access token and a refresh token (if available) back to the Office Identity Service.
#. The Office Identity Service makes an authenticated :ref:`Bootstrap` call using the tokens to retrieve the user
   profile information.
#. The Office Identity Service sends these tokens and the user profile information to |Office iOS|.
#. The user is now able to see their files under the Storage Provider's Place and open the Storage Provider files
   directly in their |Office iOS| app.


Browsing and opening files
--------------------------

Here is the operational flow for browsing and opening files.   

#. *Get the Root Container URL:* |Office iOS| calls :ref:`GetRootContainer (bootstrapper)` to obtain a Root Container
   URL. If the :ref:`GetRootContainer (bootstrapper)` response does not include ``RootContainerInfo``, |Office iOS|
   will fall back to calling :ref:`GetRootContainer` using the ``EcosystemUrl`` returned in the
   :ref:`GetRootContainer (bootstrapper)` response.
#. *Get the contents of the container:* |Office iOS| calls :ref:`EnumerateChildren` on the Root Container. The
   results are a set of containers and files in the root container. If the user wants to browse to another container
   within the current container, |Office iOS| calls :ref:`CheckContainerInfo` on the other container to check
   permissions, then calls :ref:`EnumerateChildren` on that second container. This step is repeated as the user
   browses the container hierarchy, until the user selects the file they want to open.
#. *Check file permissions:* Once the user selects a file, |Office iOS| calls :ref:`CheckFileInfo` on that file to
   verify that the user has permissions to the file.
#. *Check file lock:*

   * If the earlier :ref:`CheckFileInfo` call returned ``true`` for :term:`SupportsGetLock`, |Office iOS| calls
     :ref:`GetLock`. If the :ref:`GetLock` response is a :http:statuscode:`409` or includes an **X-WOPI-Lock**
     header, the file is locked and |Office iOS| does not continue opening it.
   * If the earlier :ref:`CheckFileInfo` call returned ``true`` for :term:`SupportsGetLock`, |Office iOS| sends a
     :ref:`RefreshLock` request with a known invalid lock ID. If the :ref:`RefreshLock` response is a
     :http:statuscode:`409` with a lock ID in the **X-WOPI-Lock** response header, the file is locked and |Office iOS|
     does not continue opening it.

#. *Take a lock on the file:* |Office iOS| calls :ref:`Lock` on the file, passing a lock ID it wishes to use in the
   **X-WOPI-Lock** request header. If the :ref:`Lock` call returns a :http:statuscode:`200`, the file is locked.
   |Office iOS| will use the same lock ID when making future :Ref:`PutFile` requests.
#. *Download the file:* |Office iOS| makes a :ref:`GetFile` request on the file.


Saving and closing a file
-------------------------

#. *Save the file:* If the user has made changes to the file, |Office iOS| will update the file's contents by calling
   :ref:`PutFile`. The :ref:`PutFile` request will include the current WOPI lock ID previously used by |Office iOS|
   to lock the file.
#. *Unlock the file:* |Office iOS| will make an :ref:`Unlock` request against to unlock the file. This :ref:`Unlock`
   request will include the current WOPI lock ID previously used by |Office iOS| to lock the file.
