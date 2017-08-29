
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

   .. figure:: ../images/open_packagename_android.png  
      :alt: A table that shows Office on Android package names.

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
  
      .. figure:: ../images/open_storelinks_android.png  
      :alt: A table that shows google store links for Office on Android.

  Step 2c - When office apps not installed - upsell via China stores 
 
    In China, office apps are uploaded on following app stores. Since Google Play is not supported in China, app installs would happen from one of the following app stores. 
 
      .. figure:: ../images/open_china_storelinks_android.png  
      :alt: A table that shows china store links for Office on Android.

    Note: Vivo Store - Coming Soon 

    Following guidance demos Tencent integration. These guidelines can be modified as needed for any other China specific WOPI integration. 

    Tencent will need to launch the market intent by showing only those app stores where Office apps are present. 
    In order to also track the number of launches in upsell flow, we will make a call to tracking URL (i.e. adjust URL). Following guidance goes over special handling to make a call to tracking URL (i.e. adjust URL) first, and then show the valid list of app stores for app installations.
 
 
    Guidance: 
    1. Working prototype for this is present here – MainActivity.java, and AppCompatActivity.java
    2. Please use following values for each of the variables for Word, Excel and PowerPoint 
    
      .. figure:: ../images/open_tencent_adjusturls_android.png  
      :alt: A table that shows Office on Android package names.
 
Here are guidelines with step by step instructions: 
 
1- Declare these class variables: - 
 
   class MainActivity : 
 
   private Set<String> staticListAvailablePlayStores = new HashSet<String>();
    public static final String FALL_BACK_PAGE_URI = "https://www.microsoft.com/china/o365consumer/products/officeandroid.html";
    public static final String WEB_FALLBACK_CHOOSER_INTENT_TITLE = "Open By";
    public static final String ADJUST_CHINA_STORE_LINK ="https://app.adjust.com/yaej92";
    public static final String APP_PACKAGE_MAKETING_FOR = "com.microsoft.office.excel"; //currently excel change as per need
 
 class AppStoreIntentProvider : 
    
        public static final String PLAYSTORE = "com.android.vending";
        public static final String SAMSUNGSTORE = "com.sec.android.app.samsungapps";
        public static final String BAIDUSTORE = "com.baidu.appsearch";
        public static final String XIAOMISTORE = "com.xiaomi.market";
        public static final String HIAPKSTORE = "com.hiapk.marketpho";
        public static final String TENCENTSTORE = "com.tencent.android.qqdownloader";
        public static final String THREESIXTYSTORE = "com.qihoo.appstore";
        public static final String AMAZONSTORE = "com.amazon.mShop.android";
        public static final String WANDOUJIASTORE = "com.wandoujia.phoenix2";
        public static final String MISTORE = "com.xiaomi.market";
        public static final String HUAWEISTORE = "com.huawei.appmarket";
        public static final String LENOVOSTORE = "com.lenovo.leos.appstore";
        public static final String REFERRERSTRING = "&referrer=adjust_reftag%3DcYI9OGwSzkSJD%26utm_source%3DChinaStore%2B%2528test%2529";
        public static final String PlayStoreWebUrl = "https://play.google.com/store/apps/details?id=";
 
2- Inside main function, add supported playstores  in variable : - 
 
    staticListAvailablePlayStores.add("com.android.vending");
    staticListAvailablePlayStores.add("com.sec.android.app.samsungapps");
    staticListAvailablePlayStores.add("com.tencent.android.qqdownloader");
    staticListAvailablePlayStores.add("com.xiaomi.market");
    staticListAvailablePlayStores.add("com.baidu.appsearch");
    staticListAvailablePlayStores.add("com.wandoujia.phoenix2");
    staticListAvailablePlayStores.add("com.qihoo.appstore");
 
3- In same main function create fallback Intent ( opening MS China page in browser ) in case no appstore in device , check it is chooser 
 
    Uri fallBackWebPage = Uri.parse(FALL_BACK_PAGE_URI);
    final Intent webIntentFallBackPageChooserIntent  = Intent.createChooser(new Intent(Intent.ACTION_VIEW, fallBackWebPage),WEB_FALLBACK_CHOOSER_INTENT_TITLE);
 
4- Hitting adjust server in async task , before hitting server check for network connectivity and internet permissions, creating intent for playstore, displaying chooser for playstore :- 
 
    URL adjustChinaStoreUrl;
     try {
         adjustChinaStoreUrl = new URL(ADJUST_CHINA_STORE_LINK);
     }
     catch(MalformedURLException malFormedException) {
         malFormedException.printStackTrace();
         return null;
     }
 
     //2.Make Http Connection to Adjust, before making check for network and take necessary permissions
     try {
         HttpsURLConnection connection = (HttpsURLConnection) adjustChinaStoreUrl.openConnection();
         connection.setConnectTimeout(30000);
         connection.connect();
         connection.getResponseCode();
     }
     catch (IOException IoException) {
         IoException.printStackTrace();
     }
 
     //3.If static list of supported playstores is empty
     if(staticListAvailablePlayStores.isEmpty()) {
         PackageManager packageManager = getPackageManager();
         List activities = packageManager.queryIntentActivities(webIntentFallBackPageChooserIntent,PackageManager.MATCH_DEFAULT_ONLY);
         boolean isIntentSafe = activities.size() > 0;
         if(isIntentSafe) {
             startActivity(webIntentFallBackPageChooserIntent);
         }
         return null;
     }
 
     //4.If static list of supported playstores is not empty create intents for playstores and launch them
     List<Intent> intents = new ArrayList<Intent>();
     for (String appStore : staticListAvailablePlayStores) {
         intents.add(AppStoreIntentHelper.AppStoreIntentProvider.getIntentForStore(appStore,APP_PACKAGE_MAKETING_FOR));
     }
     Intent intentFilteredToRunChooserStores = AppStoreIntentHelper.GenerateCustomChooserIntent(MainActivity.this, intents, staticListAvailablePlayStores, webIntentFallBackPageChooserIntent);
     if(intentFilteredToRunChooserStores == webIntentFallBackPageChooserIntent) {
         PackageManager packageManager = getPackageManager();
         List activities = packageManager.queryIntentActivities(webIntentFallBackPageChooserIntent,PackageManager.MATCH_DEFAULT_ONLY);
         boolean isIntentSafe = activities.size() > 0;
         if(isIntentSafe) {
             startActivity(webIntentFallBackPageChooserIntent);
         }
         return null;
     }
     intentFilteredToRunChooserStores.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
     startActivity(intentFilteredToRunChooserStores);
     return null;

  


