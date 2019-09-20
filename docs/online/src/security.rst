
..  _Security:

Considerations for security and privacy
=======================================

..  seealso:: :ref:`wopirest:Security`


|wac| and customer data
-----------------------

There are two classes of customer data that flow through |wac| servers: user metadata and customer content
(documents, presentations, workbooks, and notebooks).

User metadata
~~~~~~~~~~~~~

User metadata consists of URLs, email addresses, user IDs, etc. This data lives in memory and travels back and forth
between |wac| and the WOPI host through HTTPS. |wac| goes to great lengths to scrub all personally
identifying information (PII) from its logs. This is regularly audited to ensure |wac| is compliant with
several different privacy standards (such as FedRamp in the USA).


Customer content
~~~~~~~~~~~~~~~~

In the case of customer content, |wac| retrieves it from the host in order to render it for viewing or editing.
In the image below you can see how WOPI is used to fetch content from the host and send content changes to the
host (ContosoDrive in this case).

..  figure:: images/wopi_flow.*
    :alt: An image that shows the WOPI workfow.

    WOPI protocol workflow

With the exception of caching (discussed below), it is reasonable to say that customer content only lives on Office
Online servers during a user session. That is, a user can reasonably expect that when they end an editing session,
their content will no longer live anywhere on |wac| servers once it has been saved to the host. The key
exception here is the |wac| viewing cache.


..  _Viewer Cache:
..  _Cache:

The |wac| viewing cache
~~~~~~~~~~~~~~~~~~~~~~~

In order to optimize view performance for |ppt-web| and |word-web|, |wac| stores rendered documents
in a local disk cache. This way, if more than one person wants to view a document, |wac| only has to fetch it
and render it once. It is important to note that a complete removal of the cache would significantly degrade the
customer experience for many users and dramatically increase the cost of running |wac|.

Documents in the cache are indexed using a :term:`SHA256` hash that is generated based on the contents of the file (or
some other unique attribute of the file). No user information is used to index the file. On every request for a file, if
the WOPI host validates the access token, |wac| uses the SHA256 hash returned by the host (or generated
based on :term:`file id` + :term:`version` value) to check for the file in the document cache. Since the SHA256 hash is
an enormous number that is generated using information that is unique to the file, there is essentially no chance of
the same number being generated twice. Also, since the hash is generated using information unique to the file and not
based on any sort of user data, |wac| cannot retrieve information that is specific to a given user. Office
Online specifically does not log the SHA256 hash when the file is cached so that it is effectively impossible for
|wac| to retrieve information associated with a specific host or user without the participation of the host.

Documents live in the cache until they become unpopular. That is, the cache is not time-based but rather is based on
available space and usage. Unpopular files may expire out of the cache in only a few days while popular documents may
remain in the cache for up to 30 days.

As of May 2016 the contents of the cache is encrypted using a
`FIPS 140-2 <http://csrc.nist.gov/publications/fips/fips140-2/fips1402.pdf>`_
compliant encryption algorithm.
