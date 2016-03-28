
..  _Business editing:

Supporting document editing for business users
==============================================

Business users require an Office 365 subscription to edit files in Office Online. In order to support this scenario,
Office Online requires that hosts specify that a user is a business user when using any actions that include the
:term:`BUSINESS_USER` placeholder value, such as :wopi:action:`edit`, :wopi:action:`editnew`, and
:wopi:action:`view`.

When business users open documents for editing, Office Online will validate that they have a valid Office 365
subscription. This may require the user to sign in using a valid Office 365 business account. This account must have
an attached Office 365 subscription that includes Office applications.


Indicating that a user is a business user
-----------------------------------------

The first requirement in order to support document editing for business users is to indicate that the user is a
business user. Doing this requires the following:

#.  Set the :term:`BUSINESS_USER` placeholder value on the Office Online action URL. This parameter must always be on
    the action URL for business users.
#.  Include the :term:`LicenseCheckForEditIsEnabled` property in the :ref:`CheckFileInfo` response. This property
    must always be set to ``true`` for business users.
#.  Include the :term:`HostEditUrl` in the :ref:`CheckFileInfo` response. This property must be included in the
    :ref:`CheckFileInfo` response for business users. The :term:`HostEditUrl` is used to redirect the user back to the
    host edit page after the subscription check is completed.
#.  *(Optional)* You may also optionally include the :term:`DownloadUrl` in the :ref:`CheckFileInfo` response. If
    provided, the :term:`DownloadUrl` is used to provide a direct download link to the file if the user's subscription
    check fails.

    ..  important::
        For security purposes, both of these URLs must be served from domains that are on the :ref:`redirect domains`.
        If either of the URLs is not on the redirect domain allow list, the flow aborts and the user is directed to
        Office.com.

..  important::
    If any of the properties above are not set properly, or if the URL values provided are not on the
    :ref:`redirect domains`, then the license check flow will fail. If the flow fails, users will be redirected to
    Office.com.


Validating edit capabilities
----------------------------

When Office Online is loaded for business users, it will check that the user is signed in with an Office 365 business
account. If the user is not signed in, they'll be prompted to sign in.

Once signed in, Office Online will verify that the user has a valid Office 365 subscription. After this is verified,
Office Online will redirect the user back to the :term:`HostEditUrl` and the user can edit documents.


..  _implement PutUserInfo:

Tracking users' subscription status
-----------------------------------

In the flow described above, the user must always be signed in with a valid Office 365 business account in order to
edit documents. This is not an ideal experience since it might require the user to sign in many times.

To provide a better experience for users with Office 365 subscriptions, hosts can implement the :ref:`PutUserInfo` WOPI
operation. Office Online will use this operation to pass back user information, including subscription status, to the
host. The host can, in turn, pass the UserInfo string back to Office Online on subsequent :ref:`CheckFileInfo`
responses for that user. Office Online will use the data in the UserInfo string to determine whether a subscription
check is needed, and in most cases will not require the user to sign in. Note that hosts must treat the UserInfo
string as an opaque string.

..  important:: Hosts must treat the UserInfo string as an opaque string.

This approach helps ensure that users are required to sign in to validate their Office 365 subscription as
infrequently as possible.


..  _business user testing:

|stub-icon| Testing the business user flow
------------------------------------------

..  include:: ../../../_shared/stub.rst

.. |issue| issue:: 118
