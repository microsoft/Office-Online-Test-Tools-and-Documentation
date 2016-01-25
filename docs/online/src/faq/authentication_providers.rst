
What authentication providers does Office Online support?
=========================================================

Office Online does not do any authentication, except as part of the :ref:`business user edit <Business editing>`
flow. Hosts are expected to handle authentication and authorization by providing WOPI
:term:`access tokens <access token>`. All user-related information is provided to Office Online by the host using
properties in :ref:`CheckFileInfo`.
