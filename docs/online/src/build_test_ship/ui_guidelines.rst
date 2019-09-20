
..  _ui guidelines:

UI guidelines
=============

..  important::

    The guidelines here are not exhaustive. Hosts are expected to follow the terms of the Cloud Storage Partner
    Program contract with respect to |wac| integration.


Requirements
------------

The following guidelines are required as part of |wac| integration.

#.  **Do not display any UI on or around the Office editor.** The |wac| editors must always be displayed
    'edge-to-edge', with no surrounding UI. The editor cannot be 'light-boxed' or integrated as a component in host
    UI. The editor is a standalone application. Note that the |wac| viewer can be 'light-boxed' or otherwise embedded
    in your application. However, if the user transitions to the editor, the editor must be edge-to-edge.
#.  **Use Microsoft-provided application and file type icons.** See :ref:`icons` for more information.
#.  **Provide favicons for the Office applications.** Whenever the editor is displayed or the viewer is
    displayed full-window/full-tab, the favicon for the page should be set to the appropriate favicon. The preferred
    method is to use the URLs provided in WOPI discovery. See :ref:`favicons` for more information.
#.  **Use Office application names in UI that activates Office.** For example, if you have UI in your
    application that reads, :guilabel:`Open`, this UI should read :guilabel:`Open in PowerPoint for the web`,
    :guilabel:`Open in Office for the web`, or :guilabel:`Open in Office in a browser`. See
    `this blog post
    <https://techcommunity.microsoft.com/t5/Office-Apps-Blog/Why-Office-Online-is-Now-Simply-Office/ba-p/760207>`_
    for more information.


Other recommendations
---------------------

While the following guidelines are not required, |wac| strongly recommends partners do the following:

#.  **Provide support for sharing within Office.** |wac| provides a mechanism to share documents with other
    users directly within the |wac| applications. You should take advantage of this capability so that users can access
    sharing controls directly within |wac|. See :term:`FileSharingPostMessage` and :term:`FileSharingUrl` for more
    information.
#.  **Provide breadcrumb and breadcrumb URL values.** Breadcrumbs are very helpful to users, because it helps them
    understand where their document is, as well as how to get back to where they were. See :ref:`breadcrumbs` for
    more information.
#.  **Provide an in-app Edit in Browser button.** If you are using the |wac| viewer and the current user has
    permissions to edit the document, you should always provide a :term:`HostEditUrl` so that the
    :guilabel:`Edit in Browser` button is always displayed. This helps provide a more seamless transition for users.

    ..  seealso::
        You can also use the :term:`EditModePostMessage` property to receive a PostMessage when the
        :guilabel:`Edit in Browser` button is clicked, so you can handle the transition to edit mode yourself.

#.  **Include the document name in the HTML title tag so it displays in the browser tab/window text.** This is
    especially important in cases where users may open multiple documents in different browser tabs/windows.


..  _icons:

Application and file type icons
-------------------------------

As part of the Cloud Storage Partner Program, Microsoft will provide a 'branding toolkit' that includes proper file
type and application icons in various sizes, as well as vector-based image formats for re-sizing.

..  tip::

    The branding toolkit can be found in the Office 365 Cloud Storage Partner Program Yammer group, in the
    *Network Resources* section in the right sidebar. All O365 Cloud Storage partners should have access to this
    Yammer group.

You should use these icons as follows:

#.  When displaying an Office file, either individually or as part of a list of files, use the file type icons. Do
    not use the application icons for this purpose.
#.  When displaying a button or other UI element that opens an |wac| application, use the application icons. For
    example, if you display an :guilabel:`Open in Word on the web` button, you should use the Word application icon.

..  important::

    If you re-size or otherwise modify the provided icons, you must use the vector source files to maintain the
    high image quality of the icons.


..  _breadcrumbs:

Breadcrumbs
-----------

Breadcrumbs are an important navigational tool for users. They dramatically improve the user experience by
providing helpful 'anchors' so users can both understand where the document they are working on is located, as well as
more easily navigate in and out of the |wac| applications.

WOPI supports :ref:`two levels of breadcrumbs <Breadcrumb properties>` only. Thus, the recommended use of these
properties is as follows:

BreadcrumbBrandName/BreadcrumbBrandUrl
    You should set these properties to the 'root' of your navigational hierarchy. A basic rule of thumb is that
    clicking this breadcrumb should take the user to their logical 'home' within your WOPI host.

    In some cases, you may have several different siloed hierarchies within your application. In such cases it may make
    more sense to set these properties to the root of the particular hierarchy in which the current document is
    located.

    Ultimately you should pick a location most appropriate for your users and application structure.


BreadcrumbFolderName/BreadcrumbFolderUrl
    You should set these properties to the container in which the current document is located. A basic rule of thumb
    is that clicking this breadcrumb should take the user back to the same location they were in prior to opening the
    document.

    ..  tip::

        If you support multiple paths to get to a file, you may wish to expose different breadcrumb properties
        depending on how the user navigated to the file. You can achieve this by using the
        :term:`session context <SESSION_CONTEXT>` to customize your :ref:`CheckFileInfo` response.


Example
~~~~~~~

Consider a logical hierarchy like this:

..  code-block:: none

    Documents
    ├── Reviews
    |   ├── Data
    |   |   ├── Aggregate Data.xlsx
    |   |   └── Raw Data.xlsx
    |   └── Monthly Review.pptx
    ├── Deals
    |   ├── Integration Plans.docx
    |   └── Leads.xlsx

In this case, if the user opens :file:`Aggregate Data.xlsx`, BreadcrumbBrandName/BreadcrumbBrandUrl should be set to
:file:`Documents`, while the BreadcrumbFolderName/BreadcrumbFolderUrl should be set to :file:`Data`.

Similarly, if the user opens :file:`Integration Plans.docx`, BreadcrumbBrandName/BreadcrumbBrandUrl should be set to
:file:`Documents`, while the BreadcrumbFolderName/BreadcrumbFolderUrl should be set to :file:`Deals`.
