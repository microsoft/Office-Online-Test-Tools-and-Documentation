
..  _open files:

Opening files from your app in |Office iOS| and |Office Android|
================================================================

..  spelling::

    passback
    Passback

In order to invoke |Office iOS| or |Office Android| when opening an Office file from your app, you must use the URL
schemes that Word, Excel, and PowerPoint for iOS or Android register when they are installed.

URL schemes for invoking |Office iOS| and |Office Android|
----------------------------------------------------------

The following is the list of scheme names used to invoke :

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
|Office iOS| or |Office Android| without passing the actual file. The |Office iOS| or |Office Android| app then
opens the file directly using the WOPI protocol.

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


(Optional) Passback protocol for |Office iOS|
---------------------------------------------
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

|Office Android| Upsell guidance
--------------------------------

**Step 1 - Verify that Office has been installed**

Your referring application will first need to verify that a particular Office application is installed. The following Office
applications can be installed on Android devices for document viewing and editing:

* Excel
* PowerPoint
* Word

Use Android PackageManager to determine whether a particular Office application is installed on the device. The following table
lists the package names for the Office applications that you can use in this process.

+-------------+--------------------------------+
| Application | Package Name                   |
+=============+================================+
| Excel       | com.microsoft.office.excel     |
+-------------+--------------------------------+
| PowerPoint  | com.microsoft.office.powerpoint|
+-------------+--------------------------------+
| Word        | com.microsoft.office.word      |
+-------------+--------------------------------+

If yes Office is installed, go to Step 2a. Else go to Step 2b/2c.

**Step 2 - Integrate upsell logic in your app**

Step 2a - When office apps are installed - Check version of office apps
 
* Make sure office apps are greater than 16.0.XXXX.XX version.

    Note : Exact Version number will be provided by Office on Android team.
 
* Guidance to determine Office application version number

    Use Android `PackageInfo`_ to determine whether a particular version of Office application is installed on the device

        .. _PackageInfo: https://developer.android.com/reference/android/content/pm/PackageInfo.html

Example::

    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
    String version = pInfo.versionName;

Step 2b - When office apps not installed - upsell via Google Play store
 
Use adjust URLs to throw market intent to install office apps. These links will be created by Office Android team for you.
e.g. https://aka.ms/egnyte_word is created for Egnyte.
These links will redirect to following Google Play store page for corresponding office apps.

+-------------+-------------------------------------------------------------------------------+
| Application | Google Play Store                                                             |
+=============+===============================================================================+
| Excel       | \https://play.google.com/store/apps/details?id=com.microsoft.office.excel     |
+-------------+-------------------------------------------------------------------------------+
| PowerPoint  | \https://play.google.com/store/apps/details?id=com.microsoft.office.powerpoint|
+-------------+-------------------------------------------------------------------------------+
| Word        | \https://play.google.com/store/apps/details?id=com.microsoft.office.word      |
+-------------+-------------------------------------------------------------------------------+

Step 2c - When office apps not installed - upsell via China stores
 
In China, office apps are uploaded on following app stores. Since Google Play is not supported in China, app installs
would happen from one of the following app stores.
 
+-----------+------------------------------------------------------------------------------+
| Stores    | Word                                                                         |
+===========+==============================================================================+
| Baidu     | \http://shouji.baidu.com/software/9450548.html                               |
+-----------+------------------------------------------------------------------------------+
| 360       | \http://zhushou.360.cn/detail/index/soft_id/2483089                          |
+-----------+------------------------------------------------------------------------------+
| Tencent   | \http://android.myapp.com/myapp/detail.htm?apkName=com.microsoft.office.word |
+-----------+------------------------------------------------------------------------------+
| Wandoujia | \http://www.wandoujia.com/apps/com.microsoft.office.word                     |
+-----------+------------------------------------------------------------------------------+
| Xiaomi    | \http://app.mi.com/detail/91625                                              |
+-----------+------------------------------------------------------------------------------+
| Huawei    | \http://appstore.huawei.com/app/C10586094                                    |
+-----------+------------------------------------------------------------------------------+
| Lenovo    | \http://www.lenovomm.com/app/20682833.html                                   |
+-----------+------------------------------------------------------------------------------+
| Oppo      | \http://store.oppomobile.com/product/0010/458/460_1.html?from=1152_2         |
+-----------+------------------------------------------------------------------------------+

Note: Vivo Store - Coming Soon

Following guidance demos Tencent integration. These guidelines can be modified as needed for any other China specific WOPI
integration.

Tencent will need to launch the market intent by showing only those app stores where Office apps are present.
In order to also track the number of launches in upsell flow, we will make a call to tracking URL (i.e. adjust URL). Following
guidance goes over special handling to make a call to tracking URL (i.e. adjust URL) first, and then show the valid list of app
stores for app installations.
  
Guidance:

1. Working prototype for this is present here :

* `MainActivity.java <https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/android/MainActivity.java>`_
* `AppCompatActivity.java <https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/android/AppCompatActivity.java>`_


..  _MainActivity.java: https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/android/MainActivity.java

.. _AppCompatActivity.java: https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/android/AppCompatActivity.java

2. Please change following variable values as per guidance from |Office Android| team.

* ADJUST_CHINA_STORE_LINK
* APP_PACKAGE_MAKETING_FOR
* REFERRERSTRING
