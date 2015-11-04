
..  _settings:

Microsoft-configured settings
=============================

..  spelling::

    subdomain
    wopi
    qa

Most Office Online behavior is configurable based on properties provided in :ref:`CheckFileInfo`. However, there are
some Office Online settings that must be changed by Microsoft.

..  include:: /_fragments/settings_change_warning.rst

The test environment, on the other hand, is generally updated with new settings within 24-48 hours.


..  _allow list:

WOPI domain allow list
----------------------

Office Online only makes WOPI requests to trusted partner domains. This domain list is called the WOPI domain allow
list. It contains entries of the form:

* wopi.contoso.com
* qa-wopi.contoso.com

Entries in the WOPI domain allow list are implicitly wild-carded. In other words, the entry ``wopi.contoso.com`` is
equivalent to ``*.wopi.contoso.com``. This entry will allow WOPI requests to be made to any subdomain under
wopi.contoso.com.

As a general rule, we prefer hosts to use a dedicated subdomain for handling WOPI traffic. While not necessary for
testing, this is strongly preferred in production. This approach ensures that Office Online cannot make requests to
arbitrary domains.

When you are first given access to the test environment, Microsoft will add the domains you provide to the test-only
WOPI domain allow list. For test purposes you can use broadly-scoped domains such as your top-level domain (e.g.
``contoso.com``); as part of the :ref:`'Go Live' process <go live>` you can provide different domains for production.


..  _saved to:

'Saved to...' name
------------------

Office Online displays a message in the bottom status bar when saving files.

..  figure:: /images/saved_to.png
    :alt: An image showing the 'Saved to...' UI in Office Online.

    The 'Saved to...' UI for OneDrive

By default, the name listed in this UI will match Office Online's partner ID for your host. In most cases, this is
the appropriate value. However, there may be cases where you wish to use a different name here than your partner ID.
For example, you may have a specific product brand that you want to display here such as 'ContosoDrive' instead of
'Contoso.' In that case, you can provide your preferred name to Microsoft.

..  important:: This value is not localized.


..  _redirect domains:

Redirect domain allow list
--------------------------

..  note:: This setting is only relevant for hosts using the :ref:`business user flow <Business editing>`.

When validating that a business user has an Office 365 subscription, Office Online navigates the user off of the host
site so they can sign in to their Office 365 account. Once the user has signed in, Office Online will navigate back
to the :term:`HostEditUrl` provided by the host initially.

In order for that redirection to happen, the domain of the :term:`HostEditUrl` must be on the redirect domain allow
list. Like the WOPI domain allow list, entries on this list are implicitly wild-carded. The entries on this list may
be different than those on the WOPI domain allow list.

Office Online also uses the :term:`DownloadUrl` as part of the :ref:`business user flow <Business editing>`, and the
domain for this URL must also be included on the redirect domain allow list.


..  _homepage url:

Homepage URL
------------

..  note:: This setting is only relevant for hosts using the :ref:`business user flow <Business editing>`.

As part of the :ref:`business user flow <Business editing>`, Office Online may determine that a user does not have
the Office 365 subscription necessary for editing documents using Office Online. In this case Office Online offers to
redirect the user back to the host. However, navigating the user to the :term:`HostEditUrl` does not make sense in
this case since the user does not have the appropriate subscription to edit, so Office Online will not load properly.

Thus, in this case Office Online navigates the user to a URL determined by the host, called the Homepage URL. This is
a single setting per WOPI host and must be changed by Microsoft.


Action settings
---------------

Some :ref:`WOPI actions` require special arrangements with Microsoft in order to be used. These operations are marked
|need_permission|. If you wish to use these actions, you must contact Microsoft and request that the appropriate
settings be adjusted to allow you to use these actions.
