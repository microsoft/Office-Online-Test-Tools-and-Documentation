
..  index:: WOPI requests; CheckFileInfo, CheckFileInfo

..  _CheckFileInfo:

CheckFileInfo
=============

..  default-domain:: http

..  get:: /wopi*/files/(id)

    The CheckFileInfo operation is one of the most important WOPI operations. This operation must be implemented
    for all WOPI actions. CheckFileInfo returns information about a file, a user's permissions on that file, and general
    information about the capabilities that the WOPI host has on the file. In addition, some CheckFileInfo properties can
    influence the appearance and behavior of Office Online.

    ..  include:: /fragments/common_params.rst

    :query string sc:
        An optional :term:`Session Context` string that will be passed back to the host in subsequent
        :ref:`CheckFileInfo` and :ref:`CheckFolderInfo` calls in the **X-WOPI-SessionContext** request
        header.

    :reqheader X-WOPI-SessionContext:
        The value of the :term:`Session Context` URI parameter, if passed in the ``sc`` parameter.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: File unknown/user unauthorized
    :code 500: Server error

Response
--------

The response to a CheckFileInfo call is JSON (as specified in :rfc:`4627`) containing a number of parameters, most of
which are optional.

All optional values default to the following values based on their type:

=======  ================
Type     Default value
=======  ================
Boolean  ``false``
String   The empty string
=======  ================

Required response properties
----------------------------

The following properties must be present in all CheckFileInfo responses:

..  glossary::

    BaseFileName
        The **string** name of the file without a path. Used for display in user interface (UI), and determining
        the extension of the file. **This is a required value in all CheckFileInfo responses.**

    OwnerId
        A string that uniquely identifies the owner of the file.
        **This is a required value in all CheckFileInfo responses.**


    Size
        The size of the file in bytes, expressed as an **int**.
        **This is a required value in all CheckFileInfo responses.**

    Version
        The current version of the file based on the server’s file versioning schema, as a **string**. This value
        must change when the file changes, and version values must never repeat for a given file.
        **This is a required value in all CheckFileInfo responses.**


Recommended minimum properties
------------------------------

Although the :ref:`CheckFileInfo` response only requires you to set the properties listed above, we recommend that you
set the following properties at a minimum.

Navigation URL properties
~~~~~~~~~~~~~~~~~~~~~~~~~

Hosts can return a number of URLs that Office Online will navigate to in various scenarios.

..  glossary::
    :sorted:

    ClientUrl
        A user-accessible URI directly to the file intended for opening the file through a client. Can be a DAV URL
        (:rfc:`5323`), but may be any URL that can be handled by a client that can open a file of the given
        type. If this property is provided, Office Online will display UI allowing users to open the files in the
        applicable client application.

    CloseUrl
        A URI to a web page that Office Online will navigate to when the application closes, or in the event of an
        unrecoverable error.

        ..  seealso:: :term:`ClosePostMessage`

    DownloadUrl
        A user-accessible URI to the file intended to allow the user to download a copy of the file.

    FileSharingUrl
        A URI to a location that allows the user to share the file.

        ..  seealso:: :term:`FileSharingPostMessage`

    FileUrl
        A URI to the file location that the WOPI client uses to get the file. If this is provided, Office Online
        will use this URI to get the file instead of a :ref:`GetFile` request. A host might set this property if it is
        easier or more performant to serve files from a different domain than the one handling standard WOPI requests.

    HostViewUrl
        A URI to the :term:`host frame` that loads the :wopi:action:`view` WOPI action. This URL is used by Office
        Online to navigate between view and edit mode.

    HostEditUrl
        A URI to the :term:`host frame` that loads the :wopi:action:`edit` WOPI action. This URL is used by Office
        Online to navigate between view and edit mode. In addition, the HostEditUrl property contains the URL that is
        stored in the recent documents list if a :term:`ClientUrl` is not provided.

    HostEmbeddedEditUrl
        A URI to a web page that provides access to an editing experience for the file that can be embedded in
        another HTML page. For example, a page that provides an HTML snippet that can be inserted into the HTML of a
        blog.

    HostEmbeddedViewUrl
        A URI to a web page that provides access to a viewing experience for the file that can be embedded in another
        HTML page. For example, a page that provides an HTML snippet that can be inserted into the HTML of a blog.

    HostRestUrl
        A URI that is the base URI for REST operations for the file.

    PrivacyUrl
        A URI to a webpage that explains the privacy policy of the host.

    SignoutUrl
        A URI that will sign the current user out of the host's authentication system. If this property is not
        provided, no sign out UI will be shown in Office Online.

    TermsOfUseUrl
        A URI to a webpage that explains the terms of use policy of the host.

WOPI capabilities properties
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The **Supports\*** properties indicate to Office Online the WOPI capabilities that the host provides for a file. All
**Supports\*** properties are optional and thus default to ``false``; hosts should set them to ``true`` if their WOPI
implementation meets the requirements for a particular property.

..  glossary::
    :sorted:

    SupportsCoauth
        A Boolean value that indicates that the WOPI server supports multiple users making changes to this file
        simultaneously. It must be false.

        ..  todo:: Figure out how to document this.

    SupportsCobalt
        A **Boolean** value that indicates that the host supports :ref:`ExecuteCellStorageRequest` and
        :ref:`ExcecuteCellStorageRelativeRequest` operations for this file.

    SupportsFolders
        A **Boolean** value that indicates that the host supports :ref:`CheckFolderInfo`, :ref:`EnumerateChildren`,
        :ref:`DeleteFile` operations for this file. This implies that the host can use :ref:`WOPI actions` that
        require :wopi:req:`containers` support.

    SupportsLocks
        A **Boolean** value that indicates that the host supports :ref:`Lock`, :ref:`Unlock`, :ref:`RefreshLock`, and
        :ref:`UnlockAndRelock` operations for this file. This implies that the host can use :ref:`WOPI actions` that
        require :wopi:req:`locks` support.

    SupportsScenariosLinks
        A **Boolean** value that indicates that thehost supports scenarios where users can operate on files in
        limited ways via restricted URLs.

    SupportsSecureStore
        A **Boolean** value that indicates that the host supports calls to a secure data store utilizing credentials
        stored in the file.

    SupportsUpdate
        A **Boolean** value that indicates that the host supports :ref:`PutFile` and :ref:`PutRelativeFile` operations
        for this file.


User properties
~~~~~~~~~~~~~~~

..  glossary::
    :sorted:

A host is only required to specify the **OwnerId** property, which represents the owner of a given document. However, we
recommend that you set the **UserId** property as well.

SHA256 property
~~~~~~~~~~~~~~~

Set the **SHA256** property for view scenarios. This value allows Office Online to take advantage of more performant
caching strategies. We recommend that you set this property for edit scenarios as well.

UserFriendlyName property
~~~~~~~~~~~~~~~~~~~~~~~~~

The UserFriendlyName property is used to display the user's name in the Office Online UI.

User permissions properties
~~~~~~~~~~~~~~~~~~~~~~~~~~~

Office Online always assumes that users have limited permissions to documents. If you do not set the appropriate
**UserCan\*** properties, users will not be able to perform operations such as editing documents in Office Online.

The **UserCan\*** properties include:

* UserCanAttend
* UserCanNotWriteRelative
* UserCanPresent
* UserCanRename
* UserCanWrite

Additional properties that affect Office Online
-----------------------------------------------

The following **CheckFileInfo** properties affect Office Online behavior.

ClientUrl property
~~~~~~~~~~~~~~~~~~

If you set the **ClientUrl** property, Office Online will expose buttons in the UI that enable users to open documents
in Office for Windows, iOS, or Android. To integrate with Office Online, you must use a DAV URL for this property.

PostMessage properties
~~~~~~~~~~~~~~~~~~~~~~

The PostMessage properties control the behavior of Office Online with respect to incoming PostMessages. Note that if
you are using the PostMessage extensibility features of Office Online, you must set the **PostMessageOrigin**
property to ensure that Office Online accepts messages from your outer frame.

The PostMessage properties include:

* ClosePostMessage
* EditNotificationPostMessage
* FileSharingPostMessage
* PostMessageOrigin

Breadcrumb properties
~~~~~~~~~~~~~~~~~~~~~

**Breadcrumb\*** properties determine what is displayed in the breadcrumb area within the Office Online UI. Office
Online does not use the **BreadcrumbDocUrl** property.

..  glossary::
    :sorted:

    BreadcrumbBrandName
        A **string** that Office Online will display to the user that indicates the brand name of the host.


The **Breadcrumb\*** properties include:

* BreadcrumbBrandName
* BreadcrumbBrandUrl
* BreadcrumbDocName
* BreadcrumbFolderName
* BreadcrumbFolderUrl

FileNameMaxLength property
~~~~~~~~~~~~~~~~~~~~~~~~~~

The **FileNameMaxLength** property is an integer that indicates the maximum length for file names that the WOPI host
supports, excluding the file extension. The default value is 250. This property is optional unless you want to
enable file renaming within Office Online, in which case it is required.


Response Values
---------------

..  glossary::
    :sorted:

    AllowExternalMarketplace
        A **Boolean** value that indicates Office Online may allow connections to external services referenced in
        the file (for example, a marketplace of embeddable JavaScript apps). If this value is ``false``, then
        Office Online will not allow such connections.

        Default value: ``false``

