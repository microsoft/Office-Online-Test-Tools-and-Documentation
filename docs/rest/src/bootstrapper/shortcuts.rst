
..  _shortcut operations:
..  _shortcuts:

Shortcut operations
===================

The following operations are defined on the :ref:`Bootstrapper endpoint` in addition to the
:ref:`Ecosystem endpoint`. They are provided on the :ref:`Bootstrapper endpoint` as shortcuts to reduce HTTP round trips
for native applications that use the Bootstrapper. They are equivalent to calling :ref:`Bootstrap` then
using the :term:`EcosystemUrl` to immediately execute an operation on the :ref:`Ecosystem endpoint`.

For example, in order to get the root container, a WOPI client might execute the following operations:

1. Call :ref:`Bootstrap`
2. Using the :term:`EcosystemUrl` from the :ref:`Bootstrap` response, call the :ref:`GetRootContainer` operation

However, using the :ref:`GetRootContainer (bootstrapper)` shortcut operation, the WOPI client can skip calling
:ref:`Bootstrap`, and instead get the root container directly using an OAuth 2.0 token.

..  important::

    Implementing shortcut operations is optional. If a host does not implement them, they should simply respond to
    the request as though it is a standard :ref:`Bootstrap` request. In other words, a host that doesn't implement
    shortcut operations can simply ignore the **X-WOPI-EcosystemOperation** header and treat the request as a
    standard :ref:`Bootstrap` request.

    WOPI clients must thus expect that some shortcut operations will not include the data expected, and should fall
    back to calling the appropriate operation on the :ref:`Ecosystem endpoint` in such cases.

With this in mind, the responses for each 'shortcut' operation are a combination of the :ref:`Bootstrap` response
and the response for the corresponding operation on the :ref:`Ecosystem endpoint`. The Bootstrapper response is
contained in the ``Bootstrap`` property, and the operation's response is contained in a separate property with a name
specific to the operation.

For example, the response to the :ref:`GetRootContainer (bootstrapper)` shortcut operation looks like this:

..  literalinclude:: /_fragments/responses/GetRootContainer (bootstrapper).json
    :language: JSON

Note that since these operations are exposed through the :ref:`bootstrapper endpoint`, they will be called using
OAuth 2.0 access tokens, not WOPI access tokens.

..  toctree::
    :glob:
    :titlesonly:

    /bootstrapper/GetFileWopiSrc
    /bootstrapper/GetRootContainer
