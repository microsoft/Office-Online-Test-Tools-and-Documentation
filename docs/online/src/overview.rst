
..  _office online overview:
..  _overview:

Integrating with |wac|
==============================

..  include:: /_fragments/intended_isv.rst

You can integrate with |wac| to enable your users to view and edit Excel, PowerPoint, and Word files directly
in the browser.

If you deliver a web-based experience that allows your users to store Office files or includes Office files as a key
part of your solution, you now have the opportunity to integrate |wac| into your experience. This integration
works directly against files stored by you. Your users won't need a separate storage solution to view and edit Office
files.

..  figure:: images/office_online_browser.*
    :alt: A screenshot that shows a file viewed in Office.

    Word file open for editing in |wac|


Viewing Office files
--------------------

..  include:: /_fragments/edit_support.rst

You can make viewing available in two ways:

* By using the high-fidelity previews in |wac| as an integrated part of your experience. For example, you can
  use these previews in a light box view of a Word document.

* By offering to show Office files in a full-page interactive preview. Depending on your solution, this might be
  useful for file browsing or showing read-only files to users or in cases where users don't have a license to edit
  files in |wac|.


Editing Office files
--------------------

Editing is a core part of |wac| integration. When you integrate with |wac|, your users can edit
Excel, PowerPoint, and Word files directly in the browser. In addition, users can :ref:`edit documents collaboratively
with other users using Office <coauth>`. In order to edit documents, users require an |Office| license.

..  seealso::

    :ref:`coauth`
    :ref:`Business editing`


Integration process
-------------------

Integrating with |wac| is relatively simple. You just need to do some HTML and JavaScript work, and set up a
few simple REST endpoints. If you are familiar with existing Office protocols, note that you don't have to implement
the [MS-FSSHTTP]: File Synchronization via SOAP over HTTP Protocol (Cobalt). At a high level, to integrate with
|wac|, you:

* Read XML from |wac| that describes the capabilities of |wac|. This is called :ref:`WOPI discovery`.
* Implement :ref:`REST endpoints <endpoints>` that |wac| uses to learn about, fetch, and update files. To do
  this, you implement the server side of the WOPI protocol.
* Provide an HTML page (or pages) that wrap |wac|. This page is called the :ref:`host page<host page>`.

The following figure shows the WOPI protocol workflow.

..  figure:: images/wopi_flow.*
    :alt: An image that shows the WOPI workfow.

    WOPI protocol workflow

To do this, you will need to ensure that your solution meets a few basic requirements.

Authentication
~~~~~~~~~~~~~~

Authentication is handled by passing |wac| an access token that you generate. Assign this token a reasonable
expiration date. Also, we recommend that tokens be valid for a single user against a single file, to help mitigate
the risk of token leaks.

..  seealso:: :term:`Access token`

Conflict resolution
~~~~~~~~~~~~~~~~~~~

|wac| does support multiuser authoring scenarios if all users are using |wac|. However, you are
responsible for managing conflicts that may come from applications other than |wac|, either with some form of
file locking, or by using another type of conflict resolution.

File IDs
~~~~~~~~

Ensure that files are represented by a persistent ID. This ID must be URL-safe because it might be passed as part of
the URL at different times. Also, the ID must not change when the file is renamed, moved, or edited. This ensures an
uninterrupted editing experience for your users.

..  seealso:: :term:`File ID`

File versions
~~~~~~~~~~~~~

You should have a mechanism by which users can clearly identify file versions through the REST APIs. Because files
are cached to improve viewing performance, file versions are extremely helpful. Without them, users can't easily
determine whether they have the latest version of the file.


Security considerations
-----------------------

|wac| is designed to work for enterprises that have strict security requirements. To make sure your
integration is as secure as possible, ensure that:

* All traffic is SSL encrypted.
* Initial requests to |wac| are made by using POST, where the access token is in the body of the POST request.

|wac| identity can be established by using a public :ref:`proof key <Proof Keys>` to decrypt part of the WOPI
requests. Also, the |wac| file cache indexes stored file contents by using a SHA256 hash as the cache key. You
can pass |wac| the hash value using the :term:`SHA256` property in the :ref:`CheckFileInfo` response. If
not provided, |wac| will generate a cache key from the file ID and version. To ensure that users can't force a
cache collision and view the wrong file, no user-provided information is used to generate the cache key.


Managing Office 365 subscriptions
---------------------------------

Business users require an Office 365 subscription to edit files in |wac|. The simplest way to implement this
is to have users sign in with a Microsoft account or other valid identity. This establishes that they have the
correct subscription. To limit the number of times a user needs to sign in, |wac| first checks for a cookie.

To provide a better experience for users with Office 365 subscriptions, hosts can optionally implement the
:ref:`PutUserInfo` WOPI operation. See :ref:`implement PutUserInfo` for more information.


Interested?
-----------

If you're interested in integrating your solution with |wac|, take a moment to register at
`Office 365 Cloud Storage Partner Program <https://developer.microsoft.com/office/cloud-storage-partner-program>`_.
