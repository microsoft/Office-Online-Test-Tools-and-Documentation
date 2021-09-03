
..  index:: WOPI requests; CheckFileInfo, CheckFileInfo

..  |operation| replace:: CheckFileInfo

..  _CheckFileInfo:

CheckFileInfo
=============

:Required for: |web| |ios| |android|

..  default-domain:: http

..  get:: /wopi/files/(file_id)

    The |operation| operation is one of the most important WOPI operations. This operation must be implemented
    for all WOPI actions. |operation| returns information about a file, a user's permissions on that file, and general
    information about the capabilities that the WOPI host has on the file. In addition, some |operation| properties
    can influence the appearance and behavior of WOPI clients.

    ..  admonition:: |wac| Tip

        Some properties are not supported within the |cspp|. These operations are marked |need_permission|.

    ..  include:: /_fragments/common_params.rst

    :reqheader X-WOPI-SessionContext:
        The value of the :term:`session context <SESSION_CONTEXT>`, if provided on the initial WOPI action URL.

    :code 200: Success
    :code 401: Invalid :term:`access token`
    :code 404: Resource not found/user unauthorized
    :code 500: Server error

    ..  include:: /_fragments/common_headers.rst

Response
--------

The response to a |operation| call is JSON (as specified in :rfc:`4627`) containing a number of properties, most of
which are optional.

..  include:: /_fragments/param_types.rst

Required response properties
----------------------------

The following properties must be present in all |operation| responses:

..  glossary::

    BaseFileName
        The **string** name of the file, including extension, without a path. Used for display in user interface (UI),
        and determining the extension of the file.

    OwnerId
        A **string** that uniquely identifies the owner of the file. In most cases, the user who uploaded or created
        the file should be considered the owner.

        ..  important::
            This ID is subject to uniqueness and consistency requirements. See :ref:`User identity requirements` for
            more information.

    Size
        The size of the file in bytes, expressed as a **long**, a 64-bit signed integer.

    UserId
        A **string** value uniquely identifying the user currently accessing the file.

        ..  important::
            This ID is subject to uniqueness and consistency requirements. See :ref:`User identity requirements` for
            more information.

    Version
        The current version of the file based on the server's file version schema, as a **string**. This value
        must change when the file changes, and version values must never repeat for a given file.

        ..  important:: This value must be a **string**, even if numbers are used to represent versions.


.. _User identity requirements:

Requirements for user identity properties
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Hosts use the :term:`OwnerId` and :term:`UserId` properties to provide user ID data to WOPI clients. User identity
properties are intended for telemetry purposes, and thus should not be shown in any WOPI client UI. These properties
must meet the following requirements:

* Unique to a single user.
* Consistent over time. For example, if a particular user uses a WOPI client to view a document on Monday, then
  returns and views another document on Tuesday, the value of the user identity properties should match.

..  important::

    Hosts should avoid the following characters in user identity properties in order to support the widest range
    of WOPI clients:

    ``<>"#{}^[]`\/``


..  _supports properties:

WOPI host capabilities properties
---------------------------------

The WOPI host capabilities properties indicate to the WOPI client what WOPI capabilities that the host supports for a
file. All of these properties are optional and thus default to ``false``; hosts should set them to ``true`` if their
WOPI implementation meets the requirements for a particular property.

..  important::
    If a WOPI server sets any capabilities properties to ``true``, WOPI clients will assume that all of
    the operations represented by that capability property are supported. Thus, a WOPI host must implement all
    operations represented by that capability property if they set the property to ``true``.

..  glossary::
    :sorted:

    SupportedShareUrlTypes
        An **array** of strings containing the :term:`Share URL` types supported by the host.

        These types can be passed in the **X-WOPI-UrlType** request header to signify which Share URL type
        to return for the :ref:`GetShareUrl (files)` operation.

        Possible Values:

        ReadOnly
            This type of Share URL allows users to view the file using the URL, but does not give them
            permission to edit the file.

        ReadWrite
            This type of Share URL allows users to both view and edit the file using the URL.

    SupportsCobalt
        A **Boolean** value that indicates that the host supports the following WOPI
        operations:

        * :ref:`ExecuteCellStorageRequest`
        * :ref:`ExecuteCellStorageRelativeRequest`

    SupportsContainers
        A **Boolean** value that indicates that the host supports the following WOPI operations:

        * :ref:`CheckContainerInfo`
        * :ref:`CreateChildContainer`
        * :ref:`CreateChildFile`
        * :ref:`DeleteContainer`
        * :ref:`DeleteFile`
        * :ref:`EnumerateAncestors (containers)`
        * :ref:`EnumerateAncestors (files)`
        * :ref:`EnumerateChildren`
        * :ref:`GetEcosystem (containers)`
        * :ref:`RenameContainer`

        ..  tip::
            SupportsContainers is a superset of :term:`SupportsDeleteFile`. However, WOPI hosts should explicitly
            return ``true`` for both properties if SupportsContainers is ``true``.

    SupportsDeleteFile
        A **Boolean** value that indicates that the host supports the :ref:`DeleteFile` operation.

    SupportsEcosystem
        A **Boolean** value that indicates that the host supports the following WOPI operations:

        * :ref:`CheckEcosystem`
        * :ref:`GetEcosystem (containers)`
        * :ref:`GetEcosystem (files)`
        * :ref:`GetRootContainer`

    SupportsExtendedLockLength
        A **Boolean** value that indicates that the host supports lock IDs up to 1024 ASCII characters long. If not
        provided, WOPI clients will assume that lock IDs are limited to 256 ASCII characters.

        ..  important::
            While the 256 ASCII character lock length is currently sufficient, longer lock IDs will likely be
            required to support future scenarios, so we recommend hosts support extended lock lengths as soon as
            possible. See :ref:`lock ID lengths <lock length>` for more information.

    SupportsFolders
        A **Boolean** value that indicates that the host supports the following WOPI operations:

        * :ref:`CheckFolderInfo`,
        * :ref:`EnumerateChildren (folders)`
        * :ref:`DeleteFile`

    SupportsGetFileWopiSrc
        A **Boolean** value that indicates that the host supports the :ref:`GetFileWopiSrc` operation.

    SupportsGetLock
        A **Boolean** value that indicates that the host supports the :ref:`GetLock` operation.

    SupportsLocks
        A **Boolean** value that indicates that the host supports the following WOPI operations:

        * :ref:`Lock`,
        * :ref:`Unlock`
        * :ref:`RefreshLock`
        * :ref:`UnlockAndRelock` operations for this file.

    SupportsRename
        A **Boolean** value that indicates that the host supports the :ref:`RenameFile` operation.

    SupportsUpdate
        A **Boolean** value that indicates that the host supports the following WOPI operations:

        * :ref:`PutFile`
        * :ref:`PutRelativeFile`

    SupportsUserInfo
        A **Boolean** value that indicates that the host supports the :ref:`PutUserInfo` operation.

        ..  versionadded:: 2015.08.03

..  _User metadata properties:

User metadata properties
------------------------

The following properties are used to provide additional information about users.

..  glossary::
    :sorted:

    UserFriendlyName
        A **string** that is the name of the user, suitable for displaying in UI.

    IsAnonymousUser
        A **Boolean** value indicating whether the user is authenticated with the host or not. Hosts should always set
        this to ``true`` for unauthenticated users, so that clients are aware that the user is anonymous.

        When setting this to ``true``, hosts can choose to omit the :term:`UserId` property, but must still set the
        :term:`OwnerId` property.

        ..  versionadded:: 2017.02.15

    LicenseCheckForEditIsEnabled
        A **Boolean** value indicating whether the user is a business user or not.

        ..  seealso:: :ref:`Business editing`

    UserInfo
        A **string** value containing information about the user. This string can be passed from a WOPI client to
        the host by means of a :ref:`PutUserInfo` operation. If the host has a UserInfo string for the user, they
        must include it in this property. See the :ref:`PutUserInfo` documentation for more details.

        ..  versionadded:: 2015.08.03

    IsEduUser
        A **Boolean** value indicating whether the user is an education user or not.

        ..  versionadded:: 2016.01.27


..  _permissions:

User permissions properties
---------------------------

A WOPI client must always assume that users have limited permissions to documents. If a host does not set the
appropriate user permissions properties, users will not be able to perform operations such as editing documents using
a WOPI client.

Ultimately, the host has final control over whether WOPI operations attempted by the client should succeed or fail
based on the :term:`access token` provided in the WOPI request. Thus, these properties do not act as an authorization
mechanism. Rather, these properties help WOPI clients tailor their UI and behavior to the specific permissions a
user has. For example, a WOPI client can hide file renaming UI if the :term:`UserCanRename` property is ``false``.

However, a WOPI client expects that even if that UI were somehow made available to a user without appropriate
permissions, the WOPI :ref:`RenameFile` request would fail since the host would determine the action was not
permissible based on the :term:`access token` passed in the request.

Note that there is no property that indicates the user has permission to read/view a file. This is because WOPI
requires the host to respond to any WOPI request, including :ref:`CheckFileInfo`, with a :http:statuscode:`401` or
:http:statuscode:`404` if the access token is invalid or expired.

..  glossary::
    :sorted:

    ReadOnly
        A **Boolean** value that indicates that, for this user, the file cannot be changed.

    RestrictedWebViewOnly
        A **Boolean** value that indicates that the WOPI client should restrict what actions the user can perform on
        the file. The behavior of this property is dependent on the WOPI client.

    UserCanAttend
        A **Boolean** value that indicates that the user has permission to view a :term:`broadcast` of this file.

    UserCanNotWriteRelative
        A **Boolean** value that indicates the user does not have sufficient permission to create new files on the WOPI
        server. Setting this to ``true`` tells the WOPI client that calls to :ref:`PutRelativeFile` will fail for
        this user on the current file.

    UserCanPresent
        A **Boolean** value that indicates that the user has permission to :term:`broadcast` this file to a set of
        users who have permission to broadcast or view a broadcast of the current file.

    UserCanRename
        A **Boolean** value that indicates the user has permission to rename the current file.

    UserCanWrite
        A **Boolean** value that indicates that the user has permission to alter the file. Setting this to ``true``
        tells the WOPI client that it can call :ref:`PutFile` on behalf of the user.


File URL properties
-------------------

Hosts can return a number of URLs for the file that WOPI clients may navigate to in various scenarios.

..  admonition:: |wac| Tip

    These properties can be used to customize the user experience of the |wac| applications. See
    :ref:`UI customization` for more information about how each property is used in |wac|.

..  glossary::
    :sorted:

    CloseUrl
        A URI to a web page that the WOPI client should navigate to when the application closes, or in the event of an
        unrecoverable error.

    DownloadUrl
        A user-accessible URI to the file intended to allow the user to download a copy of the file.

        ..  important::
            This URI should directly download the file. In other words, WOPI clients expect that when directing users
            to this URL, the file will be immediately downloaded. This URL should not direct the user to some
            separate UI to download the file.

        ..  important::
            This URI should always provide the most recent version of the file.

    FileEmbedCommandUrl
        A URI to a location that allows the user to create an embeddable URI to the file.

    FileSharingUrl
        A URI to a location that allows the user to share the file.

    FileVersionUrl
        A URI to a location that allows the user to view the version history for the file.

        ..  versionadded:: 2017.02.15

    FileUrl
        A URI to the file location that the WOPI client uses to get the file. If this is provided, the WOPI client
        may use this URI to get the file instead of a :ref:`GetFile` request. A host might set this property if it is
        easier or provides better performance to serve files from a different domain than the one handling standard
        WOPI requests. WOPI clients must not add or remove parameters from the URL; no other parameters, including
        the :term:`access token`, should be appended to the FileUrl before it is used.

        ..  important::
            The FileUrl is meant as a performance enhancement. The :ref:`GetFile` operation must still be supported
            for the file even when the FileUrl property is provided.

        ..  note::
            Requests to the :term:`FileUrl` can not be signed using :ref:`proof keys <proof keys>`. The FileUrl is
            used exactly as provided by the host, so it does not necessarily include the access token, which is
            required to construct the expected proof.

    HostEditUrl
        A URI to a :term:`host page` that loads the :wopi:action:`edit` WOPI action.

    HostEmbeddedViewUrl
        A URI to a web page that provides access to a viewing experience for the file that can be embedded in another
        HTML page. This is typically a URI to a :term:`host page` that loads the :wopi:action:`embedview` WOPI action.

    HostViewUrl
        A URI to a :term:`host page` that loads the :wopi:action:`view` WOPI action. This URL is used by Office
        Online to navigate between view and edit mode.

    SignoutUrl
        A URI that will sign the current user out of the host's authentication system.

        ..  seealso:: :term:`SignInUrl`


PostMessage properties for web-based WOPI clients
-------------------------------------------------

CheckFileInfo supports a number of properties that can be used by web-based WOPI clients such as |wac| to
customize the user interface and experience when using those clients. See :ref:`postmessage properties` for more
information on these properties and how to use them.


..  _Breadcrumb properties:

Breadcrumb properties
---------------------

**Breadcrumb** properties are used by some WOPI clients to display breadcrumb-style navigation elements within the
WOPI client UI.

..  glossary::
    :sorted:

    BreadcrumbBrandName
        A **string** that indicates the brand name of the host.

    BreadcrumbBrandUrl
        A URI to a web page that the WOPI client should navigate to when the user clicks on UI that displays
        :term:`BreadcrumbBrandName`.

    BreadcrumbDocName
        A **string** that indicates the name of the file. If this is not provided, WOPI clients may use the
        :term:`BaseFileName` value.

    BreadcrumbFolderName
        A **string** that indicates the name of the container that contains the file.

    BreadcrumbFolderUrl
        A URI to a web page that the WOPI client should navigate to when the user clicks on UI that displays
        :term:`BreadcrumbFolderName`.


Other miscellaneous properties
------------------------------

..  glossary::
    :sorted:

    AllowAdditionalMicrosoftServices
        A **Boolean** value that indicates a WOPI client may connect to Microsoft services to provide end-user
        functionality.

        ..  versionadded:: 2016.06.27

        ..  admonition:: |wac| Tip

            In |wac|, setting this property to ``true`` enables the following features:

            Bing spelling and proofing
                |wac| will use the `Bing Spell Check API`_ to provide better spelling and proofing suggestions for
                supported languages.

            Smart Lookup
                |wac| will use Bing to power the `Smart Lookup`_ feature, which provides quick access to definitions,
                Wiki articles, and top related searches from the web.

            Additional features might be added in the future.

        .. _Bing Spell Check API: https://www.microsoft.com/cognitive-services/en-us/bing-spell-check-api
        .. _Smart Lookup: https://support.office.microsoft.com/article/Get-insights-into-what-you-re-working-on-with-Smart-Lookup-debf2083-5ac0-4739-8667-ae2467bec044

    AllowErrorReportPrompt
        A **Boolean** value that indicates that in the event of an error, the WOPI client is permitted to prompt the
        user for permission to collect a detailed report about their specific error. The information gathered
        could include the user's file and other session-specific state.

        ..  versionadded:: 2017.06.01

        ..  tip::
            This value should be omitted (or explicitly set to ``false``) if no additional collection should be done on
            errors, or if the user has opted out of telemetry collection.

    AllowExternalMarketplace
        A **Boolean** value that indicates a WOPI client may allow connections to external services referenced in
        the file (for example, a marketplace of embeddable JavaScript apps).

    CloseButtonClosesWindow
        A **Boolean** value that indicates the WOPI client should close the window or tab when the user activates any
        :guilabel:`Close` UI in the WOPI client.

        ..  admonition:: |wac| Tip

            This property may not behave as expected in |wac|.

            ..  seealso:: :ref:`avoid CloseButtonClosesWindow`

    ClientThrottlingProtection

        A **string** value offering guidance to the WOPI client as to how to differentiate client throttling behaviors between the
        user and documents combinations from the WOPI host. Under times of stress, the WOPI client may choose to make use of this
        field to vary the level of impact of client side throttling behaviors within the set of active host documents. If the WOPI
        client chooses to differentiate throttling of client behaviors that are not necessarily tied to WOPI calls to the host,
        it may apply the most reduced quality of service to the LeastProtected document/users and the least reduced quality of
        service to the MostProtected documents/users. As in the case of :term:`RequestedCallThrottling`, it is advised that hosts
        sharing this value between responses for distinct users of the same document at any given time may yield more deterministic
        results from the clients.

        Possible Values:

        MostProtected
            The most protected documents/users.

        Protected
            The documents/users that should be protected more than the average ones.

        Normal
            The documents/users with the standard level of protection from throttling.

        LessProtected
            The documents/users that can be throttled more heavily than a typical than the average ones.

        LeastProtected
            The least proteced documents/users.

        If no value is specified, or an invalid value is provided, the default behavior is to assume the WOPI host intended the value
        of None.

    CopyPasteRestrictions
        |prerelease|

        A **string** value indicating whether the WOPI client should disable Copy and Paste functionality within the application.
        The default is to permit all Copy and Paste functionality, i.e. the setting has no effect.

        Possible Values:

        BlockAll
            Copy and Paste are completely disabled within the application.

        CurrentDocumentOnly
            Copy and Paste are enabled but content can only be copied and pasted within the file currently open in the application.

        Any values other than those listed above must be ignored by the WOPI client.

        ..  admonition:: |wac| Tip

            This property is only respected by |excel-web|. It has no effect in |ppt-web| or |word-web|.


    DisablePrint
        A **Boolean** value that indicates the WOPI client should disable all print functionality.

    DisableTranslation
        A **Boolean** value that indicates the WOPI client should disable all machine translation functionality.

    FileExtension
        A **string** value representing the file extension for the file. This value must begin with a ``.``. If
        provided, WOPI clients will use this value as the file extension. Otherwise the extension will be parsed
        from the :term:`BaseFileName`.

        ..  tip::
            While this property is not required, hosts should set it rather than relying on the :term:`BaseFileName`
            parsing.

    FileNameMaxLength
        An **integer** value that indicates the maximum length for file names that the WOPI host supports, excluding
        the file extension. The default value is 250. Note that WOPI clients will use this default value if the
        property is omitted or if it is explicitly set to ``0``.

        ..  admonition:: |wac| Tip

            This property is optional; however, hosts wishing to enable file renaming within |wac| should verify that
            the default value is appropriate and set it accordingly if not. See the :ref:`RenameFile` operation for
            more details.

    LastModifiedTime
        A **string** that represents the last time that the file was modified. This time must always be a must be
        a :abbr:`UTC (Coordinated Universal Time)` time, and must be formatted in ISO 8601 round-trip format. For
        example, ``"2009-06-15T13:45:30.0000000Z"``.

    RequestedCallThrottling

        A **string** value indicating whether the WOPI host is experiencing capacity problems and would like to
        reduce the frequency at which the WOPI clients make calls to the host.  Each WOPI application may choose
        how best to respect the expressed desire from the host.  WOPI applications may respond in manners such
        as reducing the frequency of CheckFileInfo calls and extending the window between when a user makes a
        change and the updated document gets saved back to the WOPI host.  It is advised that hosts sharing this
        value between responses for distinct users of the same document at any given time may yield more
        deterministic results from the clients.

        Possible Values:

        Normal
            The WOPI host is healthy and does not want any additional request throttling.

        Minor
            The WOPI host is requesting a small amount of throttling from the WOPI client.

        Medium
            The WOPI host is requesting a medium amount of throttling from the WOPI client.

        Major
            The WOPI host is requesting a large amount of throttling from the WOPI client.

        Critical
            The WOPI host is requesting the WOPI client to apply the largest amount of throttling possible.

        If no value is specified, or an invalid value is provided, the default behavior is to assume the WOPI host intended the value
        of None.

    SHA256
        A 256 bit SHA-2-encoded [`FIPS 180-2`_] hash of the file contents, as a Base64-encoded **string**. Used for
        caching purposes in WOPI clients.

        ..  admonition:: |wac| Tip

            See :ref:`View performance` for more details on how this property is used in |wac|.

    SharingStatus
        |prerelease|

        A **string** value indicating whether the current document is shared with other users. The value can change
        upon adding or removing permissions to other users. Clients should use this value to help decide when to enable
        collaboration features as a document must be Shared in order to multi-user collaboration on the document. 

        Possible Values:

        Private
            Only the document owner has permission to the file.

        Shared
            At least one other user has access to the file via direct permissions or a sharing link.

        If no value is specified, or an invalid value is provided, the default behavior is to assume the WOPI host intended the value
        of None.

    TemporarilyNotWritable
        A **Boolean** value that indicates that if host is temporarily unable to process writes on a file

    UniqueContentId
        |need_permission|

        In special cases, a host may choose to not provide a :term:`SHA256`, but still have some mechanism for
        identifying that two different files contain the same content in the same manner as the :term:`SHA256` is used.

        This **string** value can be provided rather than a :term:`SHA256` value if and only if the host can guarantee
        that two different files with the same content will have the same UniqueContentId value.

        ..  admonition:: |wac| Tip

            See :ref:`View performance` for more details on how this property is used in |wac|.


Unused and future properties
----------------------------

The following properties are defined as valid |operation| response properties. However, they are not used, either
because they are pending deprecation or they are designated for future features of WOPI clients and WOPI servers.

..  glossary::
    :sorted:

    ClientUrl
        A user-accessible URI directly to the file intended for opening the file through a client. Can be a DAV URL
        (:rfc:`5323`), but may be any URL that can be handled by a client that can open a file of the given
        type.

    SupportsFileCreation
        A **Boolean** value that indicates that the host supports creating new files using the WOPI client.

    SupportsScenarioLinks
        A **Boolean** value that indicates that the host supports scenarios where users can operate on files in
        limited ways via restricted URLs.

    SupportsSecureStore
        A **Boolean** value that indicates that the host supports calls to a secure data store utilizing credentials
        stored in the file.

    TimeZone
        A **string** that is used to pass time zone information to a WOPI client. The format of this value is
        determined by the host.

    HostAuthenticationId
        A **string** value uniquely identifying the user currently accessing the file.

        ..  warning::
            This property should not be used. Hosts should use the :term:`UserId` property instead.

    PresenceUserId
        A **string** that identifies the user in the context of the :term:`PresenceProvider`.

    TenantId
        A **string** value uniquely identifying the user's 'tenant,' or group/organization to which they belong.

        ..  caution::

            The presence of this property does not remove the uniqueness and consistency requirements listed above.
            User properties are expected to be unique *per user* and consistent over time regardless of the presence
            of a :term:`TenantId`.

    UserPrincipalName
        A **string** value uniquely identifying the user currently accessing the file.

    EditAndReplyUrl
        |stub-icon| *Not yet documented.*

    HostNotes
        A **string** that is used by the host to pass arbitrary information to the WOPI client. The client must
        ignore this string if it does not recognize its contents. A host must not require that a client understand
        the contents of this string to operate.

    PresenceProvider
        A **string** that identifies the provider of information that a WOPI client may use to discover
        information about the user's online status (for example, whether a user is available via instant messenger).

    HostRestUrl
        A URI that is the base URI for REST operations for the file.

    SignInUrl
        A URI that will allow the user to sign in using the host's authentication system. This property can be used
        when supporting anonymous users. If this property is not provided, no sign in UI will be shown in Office
        Online.

        ..  seealso:: :term:`SignoutUrl`

    DisableBrowserCachingOfUserContent
        A **Boolean** value that indicates that the WOPI client should disable caching of file contents
        in the browser cache. Note that this has important performance implications for web browser-based WOPI clients.

    IrmPolicyDescription
        A **string** that the WOPI client should  display to the user indicating the
        :abbr:`IRM (Information Rights Management)` policy for the file. This value should be combined with
        :term:`IrmPolicyTitle`.

    IrmPolicyTitle
        A **string** that the WOPI client should display to the user indicating the :abbr:`IRM (Information Rights
        Management)` policy for the file. This value should be combined with :term:`IrmPolicyDescription`.

    ProtectInClient
        A **Boolean** value that indicates that the WOPI client should take measures to prevent copying and printing of
        the file. This is intended to help enforce :abbr:`IRM (Information Rights Management)`.

    HostEmbeddedEditUrl
        A URI to a web page that provides access to an editing experience for the file that can be embedded in
        another HTML page. For example, a page that provides an HTML snippet that can be inserted into the HTML of a
        blog.

    WebEditingDisabled
        A **Boolean** value that indicates that the WOPI client must not allow the user to edit the file.

        ..  note::
            This does not mean that the user doesn't have rights to edit the file. Hosts should use the
            :term:`UserCanWrite` property for that purpose.
    
    CobaltCapabilities
        A **array of strings** that contains the Cobalt capabilities supported by the host. If :term:`SupportsCobalt` 
        is set to ``false``, this property must be ignored by the WOPI client. Any value other than the possible values 
        listed below must be ignored by the WOPI client.

        Possible Values:

        DownloadStreaming
            This type of CobaltCapabilities indicates the host supports Cobalt streaming for download 
            at the moment it handles the request.


Workflow properties
~~~~~~~~~~~~~~~~~~~
..  include:: ../../../_shared/prerelease.rst

..  glossary::
    :sorted:

    WorkflowType
        |prerelease|

        An **array of strings** containing the workflow types available for the document. Possible values are:

        * ``Assign``
        * ``Submit``

        This property must always be specified if :term:`WorkflowUrl` or :term:`WorkflowPostMessage` are provided. If
        this property is *not* supplied, then both :term:`WorkflowUrl` and :term:`WorkflowPostMessage` must be ignored
        by the WOPI client.

        Conversely, if the WorkflowType property is provided but neither :term:`WorkflowUrl` nor
        :term:`WorkflowPostMessage` are provided, then the WorkflowType value must be ignored by the WOPI client.

        ..  important::
            While this property is an array of strings, note that specific values of WorkflowType may be mutually
            exclusive depending on the WOPI client. WOPI clients must use the following guidelines when handling
            values in the WorkflowType array:

            * If no supported values are provided, the WOPI client must behave as though the WorkflowType property was
              not provided.
            * If multiple values are provided and the WOPI client does not support multiple values, the client may
              use the first supported value provided in the array or behave as though the WorkflowType property was
              not provided.

    WorkflowUrl
        |prerelease|

        A URI to a location that allows the user to participate in a workflow for the file.

        ..  important::
            This value will be ignored if :term:`WorkflowType` is not provided.


Deprecated properties
---------------------

The following properties are deprecated and should no longer be used by WOPI clients or servers.

..  glossary::
    :sorted:

    BreadcrumbDocUrl
        ..  deprecated:: 2014.06.01

    EditingCannotSave
        ..  deprecated:: 2014.06.01

    HostName
        ..  deprecated:: 2016.01.12

    PrivacyUrl
        ..  deprecated:: 2015.06.01

    TermsOfUseUrl
        ..  deprecated:: 2015.06.01

    SupportsCoauth
        ..  deprecated:: 2016.01.27
