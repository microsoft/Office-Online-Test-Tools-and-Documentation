
..  _open files:

Opening files from your app in |Office Android|
==============================================

In order to invoke |Office Android| when opening an Office file from your app, you must use the URL schemes that Word,
Excel, and PowerPoint for Android register when they are installed.

URL schemes for invoking |Office Android|
-----------------------------------------

The following is the list of scheme names used to invoke |Office Android|:

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
|Office Android| without passing the actual file. The |Office Android| app then opens the file directly using the WOPI
protocol.


CSPP Office on Android Upsell guidance
--------------------------------------
 
Step 1 - Verify that Office has been installed

Your referring application will first need to verify that a particular Office application is installed. The following Office applications can be installed on Android devices for document viewing and editing:

* Excel
* PowerPoint
* Word

Use Android PackageManager to determine whether a particular Office application is installed on the device. The following table lists the package names for the Office applications that you can use in this process.

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

Step 2 - Integrate upsell logic in your app

  Step 2a - When office apps are installed - Check version of office apps
 
    Make sure office apps are greater than 16.0.XXXX.XX version. 
    
    Note : Exact Version number will be provided upon readiness of Office on Android Beta launch with CSPP integration complete. 
 
    How to find version number?
 
      Use Android PackageInfo to determine whether a particular version of Office application is installed on the device.
 
        PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        String version = pInfo.versionName;
 
  Step 2b - When office apps not installed - upsell via Google Play store 
 
    Use adjust URLs to throw market intent to install office apps. These links will be created by Office Android team for you. e.g. https://aka.ms/egnyte_word is created for Egnyte.
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
 
    In China, office apps are uploaded on following app stores. Since Google Play is not supported in China, app installs would happen from one of the following app stores. 
 
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

    Following guidance demos Tencent integration. These guidelines can be modified as needed for any other China specific WOPI integration. 

    Tencent will need to launch the market intent by showing only those app stores where Office apps are present. 
    In order to also track the number of launches in upsell flow, we will make a call to tracking URL (i.e. adjust URL). Following guidance goes over special handling to make a call to tracking URL (i.e. adjust URL) first, and then show the valid list of app stores for app installations.
  
    Guidance: 
    1. Working prototype for this is present here – `MainActivity.java`, and `AppCompatActivity.java`
    2. Please use following values for each of the variables for Word, Excel and PowerPoint 
    
.. _MainActivity.java: https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/android/MainActivity.java

.. _AppCompatActivity.java: https://github.com/Microsoft/Office-Online-Test-Tools-and-Documentation/blob/master/samples/android/AppCompatActivity.java

    +-------------------------+-------------+-------------------------------------------------------------------------------------------+
    |Variable Name            | Application | Example URL                                                                               |
    +=========================+=============+===========================================================================================+
    |ADJUST_CHINA_STORE_LINK  |  Word       | https://aka.ms/tencent_word                                                               |     +-------------------------+-------------+-------------------------------------------------------------------------------------------+
    |ADJUST_CHINA_STORE_LINK  |  Excel      | https://aka.ms/tencent_excel                                                              |     +-------------------------+-------------+-------------------------------------------------------------------------------------------+
    |ADJUST_CHINA_STORE_LINK  |  PowerPoint | https://aka.ms/tencent_ppt                                                                |     +-------------------------+-------------+-------------------------------------------------------------------------------------------+
    |APP_PACKAGE_MAKETING_FOR |  Word       | https://aka.ms/tencent_ppt                                                                |     +-------------------------+-------------+-------------------------------------------------------------------------------------------+
    |APP_PACKAGE_MAKETING_FOR |  Excel      | https://aka.ms/tencent_ppt                                                                |     +-------------------------+-------------+-------------------------------------------------------------------------------------------+
    |APP_PACKAGE_MAKETING_FOR |  PowerPoint | https://aka.ms/tencent_ppt                                                                |     +-------------------------+-------------+-------------------------------------------------------------------------------------------+
    |REFERRERSTRING           |  Word       | referrer=adjust_reftag%3DcnREFKz8RUc6i%26utm_source%3DThirdParty%26utm_campaign%3DTencent |     +-------------------------+-------------+-------------------------------------------------------------------------------------------+
    |REFERRERSTRING           |  Excel      | referrer=adjust_reftag%3DcQVPZG14QfkNQ%26utm_source%3DThirdParty%26utm_campaign%3DTencent |     +-------------------------+-------------+-------------------------------------------------------------------------------------------+
    |REFERRERSTRING           |  PowerPoint | referrer=adjust_reftag%3DcfVvTwlIJmkwH%26utm_source%3DThirdParty%26utm_campaign%3DTencent |     +-------------------------+-------------+-------------------------------------------------------------------------------------------+

    Here are guidelines with step by step instructions: 
 
      


