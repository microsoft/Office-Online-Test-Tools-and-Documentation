
..  index:: WOPI requests; CheckFileInfo, CheckFileInfo

..  _CheckFileInfo:

CheckFileInfo
=============

..  default-domain:: http

..  get:: /wopi*/files/(id)

    The CheckFileInfo operation is one of the most important WOPI operations. This operation must be implemented
    for all WOPI actions. CheckFileInfo returns information about a file, a user's permissions on that file, and general
    information about the capabilities that the WOPI host has on the file. In addition, some CheckFileInfo properties
    can influence the appearance and behavior of Office Online.

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


Other response properties
-------------------------


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

        ..  seealso::

            :term:`ClosePostMessage`
                You can also use the ClosePostMessage property to indicate you'd like to receive a PostMessage when
                the close button is clicked rather than navigate to a URL.

    DownloadUrl
        A user-accessible URI to the file intended to allow the user to download a copy of the file.

    EditAndReplyUrl
        ..  note:: |future|

    FileSharingUrl
        A URI to a location that allows the user to share the file.

        ..  seealso::

            :term:`FileSharingPostMessage`
                You can also use the FileSharingPostMessage property to indicate you'd like to receive a PostMessage
                when the share button is clicked rather than navigate to a URL.

    FileUrl
        A URI to the file location that the WOPI client uses to get the file. If this is provided, Office Online
        will use this URI to get the file instead of a :ref:`GetFile` request. A host might set this property if it is
        easier or more performant to serve files from a different domain than the one handling standard WOPI requests.

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

    HostViewUrl
        A URI to the :term:`host frame` that loads the :wopi:action:`view` WOPI action. This URL is used by Office
        Online to navigate between view and edit mode.

    LicenseCheckForEditIsEnabled
        A **Boolean** value indicating whether the document being loaded is a :term:`business document` or not. See
        :ref:`` for more information.

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

    EditingCannotSave
        A **Boolean** value that indicates that the host supports editing files without saving them.

        ..  deprecated:: 2014.06.01

    SupportsCoauth
        A **Boolean** value that indicates that the host supports multiple users making changes to this file
        simultaneously. This value must always be ``false``.

        ..  note:: |future|

    SupportsCobalt
        A **Boolean** value that indicates that the host supports :ref:`ExecuteCellStorageRequest` and
        :ref:`ExecuteCellStorageRelativeRequest` operations for this file.

    SupportsFileCreation
        A **Boolean** value that indicates that the host supports creating new files using Office Online.

    SupportsFolders
        A **Boolean** value that indicates that the host supports :ref:`CheckFolderInfo`, :ref:`EnumerateChildren`,
        :ref:`DeleteFile` operations for this file. This implies that the host can use :ref:`WOPI actions` that
        require :wopi:req:`containers` support.

    SupportsLocks
        A **Boolean** value that indicates that the host supports :ref:`Lock`, :ref:`Unlock`, :ref:`RefreshLock`, and
        :ref:`UnlockAndRelock` operations for this file. This implies that the host can use :ref:`WOPI actions` that
        require :wopi:req:`locks` support.

    SupportsRename
        A **Boolean** value that indicates that the host supports :ref:`RenameFile` operations for this file.

    SupportsScenarioLinks
        A **Boolean** value that indicates that thehost supports scenarios where users can operate on files in
        limited ways via restricted URLs.

    SupportsSecureStore
        A **Boolean** value that indicates that the host supports calls to a secure data store utilizing credentials
        stored in the file.

    SupportsUpdate
        A **Boolean** value that indicates that the host supports :ref:`PutFile` and :ref:`PutRelativeFile` operations
        for this file.

.. _User properties:

User properties
~~~~~~~~~~~~~~~

There are several properties hosts can use to provide user ID data to Office Online. Any ID value in the following
properties must meet the following requirements:

* Unique to a single user. The :term:`TenantId` property is the sole exception to this requirement.
* Consistent over time. For example, if a particular user uses Office Online to view a document on Monday, then
  returns and views another document on Tuesday, the value of the user-related properties should match.

Office Online will record these User ID values, but they will be hashed and encrypted in such a way that their
uniqueness is maintained, but the raw values are not. Hosts can opt to pass values that are already hashed/encrypted
as long as the values meet the criteria above.

..  glossary::
    :sorted:

    HostAuthenticationId
        A **string** value uniquely identifying the user currently accessing the file.

        ..  note::

            This property should not be used. Hosts should use the :term:`UserId` property instead.

    PresenceUserId
        A **string** that identifies the user in the context of the :term:`PresenceProvider`.

        ..  note:: |future|

    TenantId
        A **string** value uniquely identifying the user's 'tenant,' or group/organization to which they belong. This
        property is useful for hosts

        ..  caution::

            The presence of this property does not remove the uniqueness and consistency requirements listed above.
            User properties are expected to be unique *per user* and consistent over time regardless of the presence
            of a :term:`TenantId`.

    UserFriendlyName
        A **string** that is the name of the user. If blank, Office Online will use a placeholder string in some
        scenarios, or show no name at all.

    UserId
        A **string** value uniquely identifying the user currently accessing the file.

    UserPrincipalName
        A **string** value uniquely identifying the user currently accessing the file.

        ..  note:: |future|

User permissions properties
~~~~~~~~~~~~~~~~~~~~~~~~~~~

Office Online always assumes that users have limited permissions to documents. If you do not set the appropriate
**UserCan\*** properties, users will not be able to perform operations such as editing documents in Office Online.

Ultimately, the host has final control over whether WOPI operations attempted by Office Online should succeed or fail
based on the :term:`access token` provided in the WOPI request. Thus, these properties do not act as an authorization
mechanism. Rather, these properties help Office Online tailor its UI and behavior to the specific permissions a user
has. For example, Office Online will hide the file renaming UI if the :term:`UserCanRename` property is ``false``.
However, Office Online expects that even if that UI were somehow made available to a user without appropriate
permissions, the WOPI :ref:`RenameFile` request would fail since the host would determine the action was not
permissable based on the :term:`access token` passed in the request.

Note that there is no property that indicates the user has permission to read/view a file. This is because Office
Online expects that the host will respond to any WOPI request, including :ref:`CheckFileInfo`, with an
:http:statuscode:`404` if the access token is invalid or expired.

..  glossary::
    :sorted:

    ReadOnly
        A **Boolean** value that indicates that, for this user, the file cannot be changed.

    RestrictedWebViewOnly:
        A **Boolean** value that, when set to ``true``, will cause Office Online to hide any UI to download the
        file or to open it in another application.

    UserCanAttend
        A **Boolean** value that indicates that the user has permission to view a :term:`broadcast` of this file.

    UserCanNotWriteRelative
        A **Boolean** value that indicates the user does not have sufficient permissions to create new files on the WOPI
        server. Setting this to ``true`` prevents Office Online from calling :ref:`PutRelativeFile` on behalf of the
        user.

    UserCanPresent
        A **Boolean** value that indicates that the user has permission to :term:`broadcast` this file to a set of
        users who have permission to broadcast or view a broadcast of this file.

    UserCanRename
        A **Boolean** value that indicates the user has permission to rename the current file. If set to ``false``,
        Office Online will hide UI related to renaming files.

    UserCanWrite
        A **Boolean** value that indicates that the user has permissions to alter the file. Setting this to ``true``
        enables Office Online to call :ref:`PutFile` on behalf of the user. In addition, Office Online will not load
        documents using the :wopi:action:`edit` action if this value is ``false`` for the user.

    WebEditingDisabled
        A **Boolean** value that indicates that Office Online must not allow the user to edit the file. This does not
        mean that the user doesn't have rights to edit the file.

PostMessage properties
~~~~~~~~~~~~~~~~~~~~~~

The PostMessage properties control the behavior of Office Online with respect to incoming PostMessages. Note that if
you are using the PostMessage extensibility features of Office Online, you must set the :term:`PostMessageOrigin`
property to ensure that Office Online accepts messages from your outer frame. You can read more about PostMessage
integration at :ref:`PostMessage`.

..  glossary::
    :sorted:

    ClosePostMessage
        A **Boolean** value that, if set to ``true``, indicates the host expects to receive the :js:data:`UI_Close`
        PostMessage.

    EditModePostMessage
        A **Boolean** value that, if set to ``true``, indicates the host expects to receive the :js:data:`UI_Edit`
        PostMessage.

    EditNotificationPostMessage
        A **Boolean** value that, if set to ``true``, indicates the host expects to receive the
        :js:data:`Edit_Notification` PostMessage.

    FileSharingPostMessage
        A **Boolean** value that, if set to ``true``, indicates the host expects to receive the
        :js:data:`UI_Sharing` PostMessage.

    PostMessageOrigin
        A **string** value indicating the domain the :term:`host frame` will be sending/receiving PostMessages
        to/from. Office Online will only send outgoing PostMessages to this domain, and will only listen to
        PostMessages from this domain.

Breadcrumb properties
~~~~~~~~~~~~~~~~~~~~~

**Breadcrumb** properties determine what is displayed in the breadcrumb area within the Office Online UI.

..  glossary::
    :sorted:

    BreadcrumbBrandName
        A **string** that Office Online will display to the user that indicates the brand name of the host.

    BreadcrumbBrandUrl
        A URI to a web page that Office Online will navigate to when the user clicks on UI that displays
        :term:`BreadcrumbBrandName`.

    BreadcrumbDocName
        A **string** that Office Online displays to the user that indicates the name of the file. If this is not
        provided, Office Online will use the :term:`BaseFileName` value.

    BreadcrumbDocUrl
        ..  deprecated:: 2014.06.01
            This property is not used by Office Online.

    BreadcrumbFolderName
        A **string** that Office Online will display to the user that indicates the name of the folder that contains
        the file.

    BreadcrumbFolderUrl:
        A URI to a web page that Office Online will navigate to when the user clicks on UI that displays
        :term:`BreadcrumbFolderName`.

Other miscellaneous properties
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

..  glossary::
    :sorted:

    AllowExternalMarketplace
        A **Boolean** value that indicates Office Online may allow connections to external services referenced in
        the file (for example, a marketplace of embeddable JavaScript apps). If this value is ``false``, then
        Office Online will not allow such connections.

    CloseButtonClosesWindow
        A **Boolean** value that, when set to ``true``, will cause Office Online to close the browser window or tab
        when the user activates the close button.

        If Office Online displays an error dialog when booting, dismissing the dialog is treated as a close button
        activation with respect to this setting.

    DisableBrowserCachingOfUserContent
        A **Boolean** value that, when set to ``true``, will cause Office Online to disable caching of file contents
        in the browser cache. Note that this has important performance implications. See :ref:`View performance` for
        more details.

    DisablePrint
        A **Boolean** value that, when set to ``true``, will disable all print functionality provided by Office Online.

    DisableTranslation
        A **Boolean** value that, when set to ``true``, will disable all machine translation functionality provided by
        Office Online.

    FileNameMaxLength
        An **integer** value that indicates the maximum length for file names that the WOPI host
        supports, excluding the file extension. The default value is 250. This property is optional; however, hosts
        wishing to enable file renaming within Office Online should verify that the default value is appropriate and
        set it accordingly if not. See the :ref:`RenameFile` operation for more details.

    HostName
        A **string** provided by the host used to identify it for logging and other informational purposes.

    HostNotes
        A **string** that is used by the host to pass arbitrary information to Office Online. Office Online will
        ignore this string if it does not recognize its contents. A host must not require that Office Online
        understand the contents of this string to operate.

        ..  note:: |future|

    IrmPolicyDescription
        A **string** that Office Online will display to the user indicating the
        :abbr:`IRM (Information Rights Management)` policy for the file. This value should be combined with
        :term:`IrmPolicyTitle`.

    IrmPolicyTitle
        A **string** that the Office Online will display to the user indicating the :abbr:`IRM (Information Rights
        Management)` policy for the file. This value should be combined with :term:`IrmPolicyDescription`.

    PresenceProvider
        A **string** that identifies the provider of information that Office Online may use to discover
        information about the user's online status (for example, whether a user is available via instant messenger).
        Office Online requires knowledge of specific presence providers to be able to take advantage of this value.

        ..  note:: |future|

    ProtectInClient
        A **Boolean** value that indicates that Office Online should take measures to prevent copying and printing of
        the file. This is intended to help enforce :abbr:`IRM (Information Rights Management)` in Office Online.

    SHA256
        A 256 bit SHA-2-encoded [`FIPS 180-2`_] hash
        of the file contents, as a **string**. Used for caching purposes in Office Online. See :ref:`View performance`
        for more details.

    TimeZone
        A **string** that is used to pass time zone information to Office Online in a format chosen by the host.

    UniqueContentId
        In special cases, a host may choose to not provide a :term:`SHA256`, but still have some mechanism for
        identifing that two different files contain the same content in the same manner as the :term:`SHA256` is used.

        This **string** value can be provided rather than a :term:`SHA256` value, if the host can guarantee that two
        different files with the same content will have the same UniqueContentId value. See :ref:`View performance`
        for more details.
