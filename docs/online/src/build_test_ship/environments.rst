
..  _environments:

Office Online environments
==========================

Office Online provides two environments for use by cloud storage partners. The :ref:`discovery URLs` for each
environment are provided below.

Initially you'll be given access to the test environment (also called 'Dogfood'), and you should use that environment
when building and testing your integration.

Once you believe you're ready to release your integration, you can start the :ref:`Launch process <shipping>`. As
part of that process, your application will be given access to the production environment.


..  _dogfood:
..  _test environment:

Test environment
----------------

The test environment is updated frequently - usually at least once a day - and all initial testing should be done
against it. Initially this is the only environment you will be able to access.

The test environment runs the most recent Office Online code available. This does mean that you may see features
there that are not yet available in production. However, the WOPI interactions between Office Online and your WOPI
host should not differ dramatically between test and production. In addition, as part of the
:ref:`Launch process <shipping>` you'll be given production access to verify your integration behaves as expected
in that environment prior to release.

Because builds are deployed frequently to this environment, you may see regressions in behavior. However, the
deployment cadence also allows us to push out changes quickly, so contact Microsoft if you experience any strange or
unexpected behavior when using the test environment.

..  important::
    End users should not use the test environment. If you are making Office Online integration available to end-users,
    you must use the production environment.


..  _production:
..  _production environment:

Production environment
----------------------

The production environment is updated weekly. Once you've started the :ref:`Launch process <shipping>`, you can
request early production access to verify your integration behaves as expected in that environment prior to release.

Primary requirements for production environment
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

..  note:: This is not an exhaustive list.

* Hosts must use HTTPS in the production |wac| environment. HTTP is not supported.
* Domains on the :ref:`allow list` must be a WOPI-dedicated subdomain in production.
* Domains must be owned by the partner.
* Production changes can take **3-4 weeks** to fully roll out; this includes domain changes.


..  _discovery URLs:

WOPI discovery URLs
-------------------

============    =============
Environment     Discovery URL
============    =============
Production      https://onenote.officeapps.live.com/hosting/discovery
Test/Dogfood    https://ffc-onenote.officeapps.live.com/hosting/discovery
============    =============

..  tip::
    These URLs are publicly accessible. However, you will not be able to invoke any :ref:`WOPI Actions`
    successfully unless your WOPI domain has been added to the :ref:`allow list`.
