
What authentication providers does |wac| support?
=================================================

|wac| does not do any authentication, except as part of the :ref:`business user edit <Business editing>` flow. Hosts are
expected to handle authentication and authorization by providing WOPI :term:`access tokens <access token>`. All
user-related information is provided to |wac| by the host using properties in :ref:`CheckFileInfo`.
