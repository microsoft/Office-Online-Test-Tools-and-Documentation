
..  _Business editing:

Supporting document editing for business users
==============================================

Business users require an Office 365 subscription to edit files in Office Online. In order to support this scenario,
Office Online requires that hosts specify that a user is a business user when using the :wopi:action:`edit` or
:wopi:action:`editnew` actions.

When business users open documents for editing, Office Online will validate that they have a valid Office 365
subscription. This may require the user to sign in using a valid Microsoft account or an Office 365 business account.
This account must have an attached Office 365 subscription that includes Office applications.


Indicating that a user is a business user
-----------------------------------------

The first requirement in order to support document editing for business users is to indicate that the user is a
business user. Doing this requires the following:

#. Add ``IsLicensedUser=1`` to the Office Online action URL. This parameter must always be on the action URL for
   business users.
#. Include the :term:`LicenseCheckForEditIsEnabled` property in the :ref:`CheckFileInfo` response. This property
   must always be set to ``true`` for business users.
#. Also include the :term:`HostEditUrl` and :term:`DownloadUrl` in the :ref:`CheckFileInfo` response. The
   :term:`HostEditUrl` is used to redirect the user back to the host edit page after the license check is completed.
   The :term:`DownloadUrl` is used to provide a direct download link to the file if the license check fails.


Validating edit capabilities
----------------------------

When Office Online is loaded for business users, it will check that the user is signed in with a Microsoft account or
an Office 365 business account. If the user is not signed in, they'll be prompted to sign in.

Once signed in, Office Online will verify that the user has an Office 365 subscription that includes Office
applications. After this is verified, Office Online will redirect the user back to the :term:`HostEditUrl` and the
user can edit documents.
