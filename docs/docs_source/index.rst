
Integrating with Office Online
==============================

..  note::

    This documentation is a work in progress. Topics marked with a |stub-icon| are placeholders that have not been
    written yet. You can track the status of these topics through our public documentation `issue tracker`_. Learn how
    you can contribute on GitHub.

..  _issue tracker: https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/issues

You can integrate with Office Online to enable your users to view and edit Excel, PowerPoint, and Word files directly
in the browser.

If you deliver a web-based experience that allows your users to store Office files or includes Office files as a key
part of your solution, you now have the opportunity to integrate Office Online into your experience. This integration
works directly against files stored by you. Your users won't need a separate storage solution to view and edit Office
files.

..  figure:: images/office_online_browser.*
    :alt: A screenshot that shows a file viewed in Office Online.

    Word file open for editing in Office Online

Viewing Office files
--------------------

You can make viewing available in two ways:

* By using the high-fidelity previews in Office Online as an integrated part of your experience. For example, you can
  use these previews in a light box view of a Word document.

* By offering to show Office files in a full-page interactive preview. Depending on your solution, this might be
  useful for file browsing or showing read-only files to users or in cases where users don't have a license to edit
  files in Office Online.

Editing Office files
--------------------

Editing is a core part of Office Online integration. When you integrate with Office Online, your users can edit
Excel, PowerPoint, and Word files directly in the browser. Right now, one user at a time can edit files; in the
future, collaborative editing scenarios will be available. Here are the key points to note about editing.

===========================================  ==============
Consumers                                    Business users
===========================================  ==============
Do not need an Office 365 subscription.      Do need an Office 365 subscription to edit files, but not to view files.
Do not have to log on to use Office Online.  Are prompted to authenticate with an Office 365 or a Microsoft account to edit.
===========================================  ==============

Integration process
-------------------

Integrating with Office Online is relatively simple. You just need to do some HTML and JavaScript work, and set up a
few simple REST endpoints. If you are familiar with existing Office protocols, note that you don't have to implement
the [MS-FSSHTTP]: File Synchronization via SOAP over HTTP Protocol (Cobalt). At a high level, to integrate with
Office Online, you:

* Read XML from Office Online that describes the capabilities of Office Online.
* Implement REST endpoints that Office Online uses to learn about, fetch, and update files. To do this, you use the
  WOPI protocol.
* Provide an HTML page (or pages) that wrap Office Online.

The following figure shows the WOPI protocol workflow.

..  figure:: images/wopi_flow.*
    :alt: An image that shows the WOPI workfow.

    WOPI protocol workflow

To do this, you will need to ensure that your solution meets a few basic requirements.

Authentication
~~~~~~~~~~~~~~

Authentication is handled by passing Office Online an access token that you generate. Assign this token a reasonable
expiration date. Also, we recommend that tokens be valid for a single user against a single file, to help mitigate
the risk of token leaks.

..  seealso:: :term:`Access token`

Conflict resolution
~~~~~~~~~~~~~~~~~~~

Although Office Online does not support multiuser authoring scenarios, you are responsible for managing conflicts
either with some form of file locking, or by using another type of conflict resolution.

..  todo:: :issue:`8`

File IDs
~~~~~~~~

Ensure that files are represented by a persistent ID. This ID must be URL-safe because it might be passed as part of
the URL at different times. Also, the ID must not change when the file is renamed, moved, or edited. This ensures an
uninterrupted editing experience for your users.

..  seealso:: :term:`File ID`

File Versions
~~~~~~~~~~~~~

You should have a mechanism by which users can clearly identify file versions through the REST APIs. Because files
are cached to improve viewing performance, file versions are extremely helpful. Without them, users can't easily
determine whether they have the latest version of the file.

Desktop application integration
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

We encourage you to provide a way for users to open files in the Excel, PowerPoint, and Word desktop applications
from the browser. Office Online is great for many things, but sometimes your users will want to transition to the
desktop applications to access features that aren't available in Office Online at this time.

Security considerations
-----------------------

Office Online is designed to work for enterprises that have strict security requirements. To make sure your
integration is as secure as possible, ensure that:

* All traffic is SSL encrypted.
* Initial requests to Office Online are made by using POST, where the access token is in the body of the POST request.

Office Online identity can be established by using a public :ref:`proof key <Proof Keys>` to decrypt part of the WOPI
requests. Also, the Office Online file cache indexes stored file contents by using a SHA256 hash. You can pass in
the hash using the :term:`SHA256` property in the :ref:`CheckFileInfo` response. The hash is usually generated from
the file itself, but it might also be generated from the file ID and version. To ensure that users can't force a
cache collision and view the wrong file, no user-provided information is used to generate the hash.

Managing Office 365 subscriptions
---------------------------------

Business users require an Office 365 subscription to edit files in Office Online. The simplest way to implement this
is to have users sign in with a Microsoft account or other valid identity. This establishes that they have the
correct subscription. To limit the number of times a user needs to sign in, Office Online first checks for a cookie.

We are currently working with partners to develop different models for establishing that users have permission to
edit files. These models might involve passing some information back to the partner so that users only have to sign
in occasionally.

Interested?
-----------

If you're interested in integrating your solution with Office Online, take a moment to register at
`Office 365 Cloud Storage Partner Program <http://dev.office.com/programs/officecloudstorage>`_.

Contents:

..  toctree::
    :maxdepth: 2
    :glob:
    :caption: Overview

    intro
    start
    concepts
    discovery
    endpoints
    changelog

..  toctree::
    :maxdepth: 2
    :glob:
    :caption: Core Operations

    wopi/files/CheckFileInfo
    wopi/file_contents/GetFile
    wopi/files/Lock
    wopi/files/GetLock
    wopi/files/RefreshLock
    wopi/files/Unlock
    wopi/files/UnlockAndRelock
    wopi/file_contents/PutFile
    wopi/files/PutRelativeFile
    wopi/files/RenameFile
    wopi/files/PutUserInfo
    wopi/other

..  toctree::
    :maxdepth: 2
    :glob:
    :caption: UI Integration

    hostpage
    postmessage

..  toctree::
    :maxdepth: 2
    :glob:
    :caption: Other Considerations

    security
    performance
    guidelines

..  toctree::
    :maxdepth: 2
    :glob:
    :caption: Scenarios

    scenarios/createnew
    scenarios/business
    scenarios/email
    scenarios/embedding
    scenarios/proofkeys

..  toctree::
    :maxdepth: 2
    :glob:
    :caption: Reference

    troubleshooting
    build_test_ship/validator
    code_samples
    faq
    glossary
    contributing/build_docs
    contributing/style_guide


Indices and tables
==================

* :ref:`genindex`
* :ref:`search`
* `HTTP Routing Table <http-routingtable.html>`_
