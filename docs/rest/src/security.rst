
.. meta::
    :robots: noindex

..  _Security:

Security considerations
=======================


..  _token trading:

Preventing 'token trading'
--------------------------

Some WOPI operations, such as :ref:`GetEcosystem`, :ref:`EnumerateAncestors`, and :ref:`EnumerateChildren`,
return URLs that must include WOPI :term:`access tokens<access token>`. WOPI access tokens must always be treated
as per-resource, per-user by a WOPI client, and they must always be expected to expire after a period of time.
WOPI deliberately does not define a means for a client to refresh a WOPI access token given another WOPI access token.

However, WOPI is also deliberately designed to support navigation from a file or container to the
:ref:`Ecosystem endpoint`, then back to a container or file. This means that it is possible for a client
to 'refresh' their WOPI tokens indefinitely unless the WOPI host is careful when issuing new access tokens to
mitigate these threats. We refer to this threat as 'token trading.'

To illustrate the token trading threat, consider the following scenario:

#.  A WOPI client, on behalf of User A, is issued a WOPI access token, ``TOKEN1``, for the file :file:`Document.docx`.
    The token has a lifetime of 12 hours.
#.  While ``TOKEN1`` is still valid, the client calls the :ref:`EnumerateAncestors` operation using ``TOKEN1`` as the
    access token.
#.  The server responds with a URL to the parent container along with a new WOPI access token for that container,
    ``TOKEN2``.
#.  The client then calls :ref:`EnumerateChildren` using ``TOKEN2`` as the access token.
#.  The server responds with the URL for the children files, including :file:`Document.docx`, along with a new access
    token for :file:`Document.docx`, ``TOKEN3``.

At this point, the client has effectively 'traded' ``TOKEN1`` for ``TOKEN3``. If each token issued has a lifetime of 12
hours, then this means that a client can effectively refresh their access token for a particular file by going through
the container hierarchy, without actually authenticating with the server using a non-WOPI authentication mechanism.

If unmitigated, this scenario greatly increases the potential damage caused by token leakage. If a malicious attacker
gains access to ``TOKEN1``, then they can potentially access :file:`Document.docx` indefinitely, as long as User A
still has access to it. In other words, the attacker can impersonate User A indefinitely.

In addition, an attacker could use :ref:`EnumerateAncestors` and :ref:`EnumerateChildren` to trade ``TOKEN1`` for a
token that is valid for any other document in the container hierarchy that the user has access to, then impersonate
User A indefinitely to access those other documents and containers as well.


Mitigation
~~~~~~~~~~

The preferred way to mitigate this threat is to create new WOPI access tokens with the same token lifetime as the WOPI
access token that was used when the operation was called. In other words, in the scenario described above, the lifetime
of ``TOKEN2`` and ``TOKEN3`` should be the same as ``TOKEN1``. All three tokens should expire at the same time.

Note that this should only apply when a WOPI access token is used to create another WOPI access token. In other cases
where WOPI access tokens are issued, such as from :ref:`Bootstrap` or other OAuth-authenticated :ref:`shortcuts`, or,
in the case of web-based WOPI clients, by visiting a host page that issues the access token, the host-defined default
access token lifetime can apply. This is safe in those cases because there is a separate primary authentication method
that is also in use. In the case of :ref:`Bootstrap`, it is an OAuth token. In the case of web-based WOPI clients,
it is the host's standard web authentication system.
