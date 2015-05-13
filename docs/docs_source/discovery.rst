
..  _Discovery:

WOPI discovery
==============

WOPI discovery is the process by which your application identifies Office Online capabilities, and how to initialize
Office Online applications within your site. To retrieve the discovery XML for the live service, use the
following URL: https://onenote.officeapps.live.com/hosting/discovery.

WOPI hosts use the discovery XML to determine how to interact with Office Online. The WOPI host should cache the
data in the discovery XML. Although this XML does not change often, we recommend that you issue a request for the
XML periodically to ensure that you always have the most up-to-date version.

WOPI discovery actions
----------------------
The **action** element and its attributes in the discovery XML provides some important information about Office Online.

+------------------------+-----------------------------------------------------------------------------------+
| Element or attribute   |  Description                                                                      |
+========================+===================================================================================+
| **action** element     | Represents:                                                                       |
|                        |                                                                                   |
|                        | * Operations that you can perform on an Office document.                          |
|                        | * The file formats (in the form of file extensions) that are supported for        |
|                        |   the action.                                                                     |
+------------------------+-----------------------------------------------------------------------------------+
| **requires** attribute | The WOPI REST endpoints that are required to use the actions.                     |
+------------------------+-----------------------------------------------------------------------------------+
| **urlsrc** attribute   | The URI that you navigate to in order to invoke the action on a particular file.  |
+------------------------+-----------------------------------------------------------------------------------+

The following example shows an **action** element in the Office Online discovery XML:

..  code-block:: xml

    <action name="edit" ext="docx" requires="locks,cobalt,update"
            urlsrc="https://word-edit.officeapps.live.com/we/wordeditorframe.aspx?
            <ui=UI_LLCC&><rs=DC_LLCC&><showpagestats=PERFSTATS&>"/>

This example defines an action called *edit* that is supported for .docx files. The edit action requires the locks,
cobalt, and update capabilities. To invoke the edit action on a file, you navigate to the URI included in the **urlsrc**
attribute. Note that you must parse the urlsrc value and add some parameters. For more information, see
`Invocation URIs`_ later in this article.

For a list of the supported actions, see [MS-WOPI] section 3.1.5.1.1.2.3.1.

Action requirements
-------------------

The WOPI protocol exposes a number of different REST endpoints and operations that you can perform via those endpoints.
You don’t have to implement all of these for all actions. Actions define their requirements as part of the discovery
XML. The requirements themselves are groups of WOPI operations that must be supported in order for the action to work.
For information about the action requirements and the operations that must be supported for each, see
[MS-WOPI] section 3.1.5.1.1.2.3.2.

Invocation URIs
---------------

You need to transform the URI in the **urlsrc** attribute; otherwise, it is invalid. This involves the following:

* Adding an additional query string parameter, *WOPISrc*, which describes where to find the file on the service that you
  are invoking the action on.
* Passing an *access_token* parameter, either on the query string, or in an HTTP POST.

..  note::
    We recommend that you use an HTTP POST, for security reasons. Do not pass access tokens in query strings in a
    production environment.

In addition, the **urlsrc** value might have placeholder values, contained within angle brackets (``<`` and ``>``),
that represent optional query string parameters that can be set on the action URI. The placeholders are replaced as
follows:

* If the PLACEHOLDER_VALUE is unknown, the entire parameter, including the angle brackets, are removed.
* If the PLACEHOLDER_VALUE is known, the angle brackets are removed, and the PLACEHOLDER_VALUE is replaced with an
  appropriate value. For information about supported values, see [MS-WOPI] section 3.1.5.1.1.2.3.3.

After the URL is transformed, it is a valid URL. When the URL is opened, the action will be invoked against the file
indicated by the WOPISrc parameter.

For more information about this process, see [MS-WOPI] section 3.1.5.1.1.2.3.3.
