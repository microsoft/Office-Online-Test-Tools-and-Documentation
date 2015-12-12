
Opening files from your app in |Office iOS|
===========================================

..  spelling::

    passback

In order to invoke |Office iOS| when opening an Office file from your app, you must use the URL schemes that Word,
Excel, and PowerPoint for iOS register when they are installed.

General information on the Office URL schemes can be found here:
https://msdn.microsoft.com/en-us/library/office/dn906146.aspx#sectionSection1

In order to open files on your WOPI host, you must do the following:

* Pass the :term:`WOPISrc` to the file via the URL schemes
* Along with the WOPISrc of the file, you must include:

  * The service identifier, denoted by the ``|d|`` parameter
  * The user identifier, denoted by the ``|e|`` parameter
  * The file name, with extension, denoted by the ``|n|`` parameter

* The source of the open action with ``|a|``. The value of this parameter can be either:

  * ``Web`` – to be used in the case where a website is invoking the protocol handler
  * ``App`` – to be used in the case where a native app is invoking the protocol handler

Example::

    ms-word:ofe|u|https://contoso/wopi/file/12312|d|Contoso|e|a3243d|n|document1.docx|a|App

Note that the file is opened directly against your service. Your app essentially passes the URL to the file to
Office without passing the actual file. The Office app then opens the file directly using the WOPI protocol.

In addition to the parameters described above, you may also include the passback parameters described in
https://msdn.microsoft.com/EN-US/library/office/dn911482.aspx. This enables an in-app back button in Office that
takes the user back to your app. This is in addition to the iOS9 platform-provided back button.


Identifying supported Office versions
-------------------------------------

Before using the above URL schemes to invoke Office, you must do the following:

#.  Check that Office is installed. This can be done by verifying whether the Office URL schemes are registered. See
    https://msdn.microsoft.com/EN-US/library/office/dn911482.aspx for more information.

#.  Check the version of Office installed is a supported version. This is done by verifying whether the following
    Office URL schemes are registered:

    * ``ms-word-wopi-support``
    * ``ms-powerpoint-wopi-support``
    * ``ms-excel-wopi-support``

    ..  important::

        These URL schemes are only used to check if the currently installed Office supports opening files from a WOPI
        host and not for invoking the Office apps. Invoking the apps is done using the URL schemes described at
        https://msdn.microsoft.com/en-us/library/office/dn906146.aspx#sectionSection3.
