..  _open files:

Opening files from your app in |Office iOS|
===========================================

..  spelling::

    passback
    Passback

In order to invoke |Office iOS| when opening an Office file from your app, you must use the URL schemes that Word,
Excel, and PowerPoint for iOS register when they are installed.

URL schemes for invoking |Office iOS|
-------------------------------------

The following is the list of scheme names used to invoke |Office iOS|:

* ``ms-word:``
* ``ms-powerpoint:``
* ``ms-excel:``

The following information must be included along with the URL scheme: 

* The mode to open the file in. Valid values are:

  * ``ofv`` for opening as read-only
  * ``ofe`` for opening for editing.

* The :term:`WOPISrc` to the file, denoted by the ``|u|`` parameter 
* The service identifier, denoted by the ``|d|`` parameter
* The user identifier, denoted by the ``|e|`` parameter
* The file name, with extension, denoted by the ``|n|`` parameter
* The source of the open action with ``|a|``. The value of this parameter can be either:

  * ``Web`` – to be used in the case where a website is invoking the protocol handler
  * ``App`` – to be used in the case where a native app is invoking the protocol handler

Example::

    ms-word:ofe|u|https://contoso/wopi/file/12312|d|Contoso|e|a3243d|n|document1.docx|a|App

The URL used should be URL encoded. 

Note that the file is opened directly against your service. Your app essentially passes the URL to the file to
|Office iOS| without passing the actual file. The |Office iOS| app then opens the file directly using the WOPI
protocol.

Identifying supported |Office iOS| versions
-------------------------------------------

Before using the URL schemes specified above to invoke |Office iOS|, you must do the following:

#.  Verify the particular |Office iOS| application (Word, Excel, or PowerPoint) are installed.

    Use the iOS ``canOpenURL`` method to determine whether your application can open the resource. This method takes
    the URL for the resource as a parameter, and returns No if the application that accepts the URL is not available.
    If ``canOpenURL`` returns No, you'll need to prompt the user to install |Office iOS|. Contact Microsoft to obtain
    links to install |Office iOS| specific to you.

#.  Check the version of |Office iOS| installed is a supported version. This is done by verifying whether the following
    URL schemes are registered:

    * ``ms-word-wopi-support-1605``
    * ``ms-powerpoint-wopi-support-1605``
    * ``ms-excel-wopi-support-1605``

    ..  important::

        These URL schemes are only used to check if the currently installed |Office iOS| supports opening files from
        a WOPI host and not for invoking the |Office iOS| apps.


(Optional) Passback protocol
----------------------------
If you want |Office iOS| to return users to your iOS application when they choose the in app back arrow (distinct
from the iOS back control), you will need to include the passback parameter when invoking |Office iOS|. This is
denoted by ``|p|`` followed by your app's registered URL scheme (without a colon).

You must ensure that your application can properly handle the response from |Office iOS|.

..  tip::

    You'll provide your app's registered URL scheme during the initial integration phase.

For security reasons, |Office iOS| only returns users to the referring application if the file opened successfully.
When the user chooses the back arrow, |Office iOS| responds to the invoking application with the passback protocol,
open mode, URL, upload pending status, and ``document context``. The upload pending status uses the descriptor
``|z|``, and is either yes or no.

``document context`` is a string you provide via the ``|c|`` parameter when invoking |Office iOS|. |Office iOS|
doesn't use this parameter; it is purely for your use, as needed by your app.  |Office iOS| does not limit the length
of the string beyond any limits imposed by the operating system.

Schema format invoking your app when user chooses back::

    <app protocol>:ofe|u|<URL>|z|<yes or no>|c|<doc context> 

Example::

    contosodrive:ofe|u|https://contoso/Q4/budget.docx|z|no|c|folderviewQ4

