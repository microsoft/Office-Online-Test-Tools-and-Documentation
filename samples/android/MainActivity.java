package com.example.shasinh.chinadeeplink;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.PromptBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    private Set<String> staticListAvailablePlayStores = new HashSet<String>();
    public static final String FALL_BACK_PAGE_URI = "https://www.microsoft.com/china/o365consumer/products/officeandroid.html";
    public static final String WEB_FALLBACK_CHOOSER_INTENT_TITLE = "Open By";
    public static final String ADJUST_CHINA_STORE_LINK ="https://app.adjust.com/yaej92"; // TO BE MODIFIED AS NEEDED
    public static final String APP_PACKAGE_MAKETING_FOR = "com.microsoft.office.excel"; // TO BE MODIFIED AS NEEDED

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        staticListAvailablePlayStores.add("com.android.vending");
        staticListAvailablePlayStores.add("com.sec.android.app.samsungapps");
        staticListAvailablePlayStores.add("com.tencent.android.qqdownloader");
        staticListAvailablePlayStores.add("com.xiaomi.market");
        staticListAvailablePlayStores.add("com.baidu.appsearch");
        staticListAvailablePlayStores.add("com.wandoujia.phoenix2");
        staticListAvailablePlayStores.add("com.qihoo.appstore");
        staticListAvailablePlayStores.add("com.hiapk.marketpho");
        staticListAvailablePlayStores.add("com.amazon.mShop.android");
        staticListAvailablePlayStores.add("com.huawei.appmarket");
        staticListAvailablePlayStores.add("com.lenovo.leos.appstore");



        Uri fallBackWebPage = Uri.parse(FALL_BACK_PAGE_URI);
        final Intent webIntentFallBackPageChooserIntent  = Intent.createChooser(new Intent(Intent.ACTION_VIEW, fallBackWebPage),WEB_FALLBACK_CHOOSER_INTENT_TITLE);

        Button staticListButton  = (Button) findViewById(R.id.static_list_button);
        staticListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void[] params) {
                        //1.Create adjust Store Url
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
                    }
                }.execute();
            }
        });










    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
