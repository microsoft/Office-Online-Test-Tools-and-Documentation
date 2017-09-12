package com.example.shasinh.chinadeeplink;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shasinh on 8/18/2016.
 */
public class AppStoreIntentHelper {
    public static Intent GenerateCustomChooserIntent(final Context context, final List<Intent> prototypes, final Set<String> allowList, final Intent fallback)
    {
        String[] blacklist = new String[]{ };
        if (allowList == null || allowList.isEmpty())
        {
            return prototypes.get(0);
        }

        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        List<HashMap<String, String>> intentMetaInfo = new ArrayList<HashMap<String, String>>();
        Intent chooserIntent;
        List<String> disallowed = Arrays.asList(blacklist);
        Set<String> used = new HashSet<String>();

        if (prototypes.size() > 0)
        {
            for (Intent prototype : prototypes)
            {
                intentMetaInfo.clear();
                List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(prototype, prototype.getFlags());
                for (ResolveInfo resolveInfo : resInfo)
                {
                    if ((resolveInfo.activityInfo != null
                            || !disallowed.contains(resolveInfo.activityInfo.packageName))
                            && !used.contains(resolveInfo.activityInfo.packageName))
                    {
                        //Get all the possible app stores
                        HashMap<String, String> info = new HashMap<String, String>();
                        used.add(resolveInfo.activityInfo.packageName);
                        info.put("packageName", resolveInfo.activityInfo.packageName);
                        info.put("className", resolveInfo.activityInfo.name);
                        String appName = String.valueOf(resolveInfo.activityInfo
                                .loadLabel(context.getPackageManager()));
                        info.put("simpleName", appName);
                        //Add only ones that we have shipped current app to
                        for (String allowed : allowList)
                        {
                            if (resolveInfo.activityInfo.packageName.contains(allowed) ||
                                    resolveInfo.activityInfo.name.contains(allowed))
                            {
                                intentMetaInfo.add(info);
                            }
                        }
                    }
                }

                if (!intentMetaInfo.isEmpty())
                {
                    // create the custom intent list
                    for (HashMap<String, String> metaInfo : intentMetaInfo)
                    {
                        Intent targetedShareIntent = (Intent) prototype.clone();
                        targetedShareIntent.setPackage(metaInfo.get("packageName"));
                        targetedShareIntent.setClassName(
                                metaInfo.get("packageName"),
                                metaInfo.get("className"));
                        targetedShareIntents.add(targetedShareIntent);
                    }
                }
            }
            String shareTitle = "Available Stores";
            if (!targetedShareIntents.isEmpty())
            {
                chooserIntent = Intent.createChooser(targetedShareIntents
                        .remove(targetedShareIntents.size() - 1), shareTitle);
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                        targetedShareIntents.toArray(new Parcelable[]{}));
                return chooserIntent;
            }
        }
        return fallback;
    }


    public static class AppStoreIntentProvider
    {
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
        public static final String REFERRERSTRING = "&referrer=adjust_reftag%3DcYI9OGwSzkSJD%26utm_source%3DChinaStore%2B%2528test%2529"; // MODIFY THIS
        public static final String PlayStoreWebUrl = "https://play.google.com/store/apps/details?id=";

        public static List<String> GetAvailableAppStores()
        {
            List<String> appStores = new ArrayList<String>();
            appStores.add(PLAYSTORE);
            appStores.add(SAMSUNGSTORE);
            appStores.add(BAIDUSTORE);
            appStores.add(XIAOMISTORE);
            appStores.add(HIAPKSTORE);
            appStores.add(TENCENTSTORE);
            appStores.add(THREESIXTYSTORE);
            appStores.add(AMAZONSTORE);
            appStores.add(WANDOUJIASTORE);
            appStores.add(MISTORE);
            appStores.add(LENOVOSTORE);
            appStores.add(HUAWEISTORE);
            return appStores;
        }

        public static Intent getIntentForStore(final String storePackageName, final String targetAppPackage)
        {
            Uri targetUri = null;
            if (storePackageName.equals(PLAYSTORE))
            {
                targetUri = getPlayUri(targetAppPackage);
            }
            else if (storePackageName.equals(SAMSUNGSTORE))
            {
                targetUri = getSamsungUri(targetAppPackage);
            }
            else if (storePackageName.equals(AMAZONSTORE))
            {
                targetUri = getAmazonUri(targetAppPackage);
            }
            else
            {
                targetUri = getMarketUri(targetAppPackage);
            }

            Intent targetIntent = new Intent(Intent.ACTION_VIEW, targetUri);
            targetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return targetIntent;
        }

        public static Uri getMarketUri(final String targetAppPackage)
        {
            return Uri.parse("market://details?id=" + targetAppPackage+REFERRERSTRING);
        }

        public static Uri getPlayUri(final String targetAppPackage)
        {
            return Uri.parse(PlayStoreWebUrl + targetAppPackage+REFERRERSTRING);
        }

        public static Uri getAmazonUri(final String appPackageName)
        {
            return Uri.parse("amzn://apps/android?p=" + appPackageName+REFERRERSTRING);
        }

        public static Uri getSamsungUri(final String appPackageName)
        {
            return Uri.parse("samsungapps://ProductDetail/" + appPackageName+REFERRERSTRING);
        }
    }
}
