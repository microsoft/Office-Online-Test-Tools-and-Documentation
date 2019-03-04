
..  _settings:

Microsoft-configured settings
=============================

Most Office Online behavior is configurable based on properties provided in :ref:`CheckFileInfo`. However, there are
some Office Online settings that must be changed by Microsoft.

..  include:: /_fragments/settings_change_warning.rst


..  _allow list:

WOPI domain allow list
----------------------

Office Online only makes WOPI requests to trusted partner domains. This domain list is called the *WOPI domain allow
list.* It contains entries of the form:

* wopi.contoso.com
* qa-wopi.contoso.com

Entries in the WOPI domain allow list are implicitly wild-carded. In other words, the entry ``wopi.contoso.com`` is
equivalent to ``*.wopi.contoso.com``. This entry will allow WOPI requests to be made to any subdomain under
wopi.contoso.com.

..  tip::
    If you ever generate :term:`WopiSrc` values that point to a subdomain, then it needs to be on the allow list. The
    :term:`WopiSrc` represents the domain that a WOPI client will use to execute WOPI operations against.

    If you don't ever generate :term:`WopiSrc` values that point to a subdomain, then that subdomain does not need to
    be on the WOPI domain allow list (but it may need to be on the :ref:`redirect domains`).

In the Office Online production environment, hosts must use a WOPI-dedicated subdomain for handling WOPI traffic.
This subdomain is typically something like ``wopi.hostdomain.com``, though that is just a name convention and hosts
can use other names if needed. This approach ensures that Office Online cannot make WOPI requests to arbitrary domains.
For testing and development using the Office Online test environment, a WOPI-dedicated subdomain is not required.

..  danger::
    A production WOPI subdomain shouldn't ever surface user-provided content. In other words, a user shouldn't be able
    to upload something to the host and trick Office Online into making WOPI requests to the user-controlled content.
    That would represent a potential security hole.

    For example, consider a service that uses the URL https://www.contosodrive.com for their main website. Users can
    upload and control content that is served out of the ``www.contosodrive.com`` domain. If the |wac| allow list
    contains ``contosodrive.com``, then a nefarious user could upload content and then create a :term:`WOPISrc`
    pointing to it, like this: ``?WOPISrc=https://www.contosodrive.com/my_content/wopi/files/attack.json``. They could
    then provide an arbitrary CheckFile and possibly GetFile response (by using the :term:`FileUrl` property). This
    means that an attacker can abuse the trust between the |wac| service and the host.  In one possible example, the
    attacker could change links in the |wac| UI (like the button controlled by the :term:`FileSharingUrl` property) to lead
    to malicious sites. 

    This threat is mitigated by requiring a dedicated subdomain for WOPI traffic that is separate from the domain used
    when serving user content.

Office Online has different allow lists for the production and test environments. When you are first given access to
the test environment, Microsoft will add the domains you provide to the test-only WOPI domain allow list. For test
purposes you can use broadly-scoped domains such as your top-level domain (e.g. ``contoso.com``); as part of the
:ref:`Launch process <shipping>` you can provide different or additional domains for production.

..  tip::
    All domains on the production allow list are automatically allowed in the test environment. The inverse is not
    true.


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

Some :ref:`WOPI actions` and :ref:`CheckFileInfo` properties require special arrangements with Microsoft in order to be
used. These operations are marked |need_permission|. If you wish to use these actions, you must contact Microsoft and
request that the appropriate settings be adjusted to allow you to use these actions.
