package com.gouravapp.TorrNet.Browser;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gouravapp.TorrNet.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BrowserActivity extends AppCompatActivity {
    private WebView webView;
    static final String TAG = "BrowserActivity";
    public static String website;
    static SwipeRefreshLayout mSwipeRefreshLayout;
    static boolean shareMode = false;
    static ProgressBar mProgressBar;
    static Menu mMenu;
    public static String urlIntent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_browser);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        webView = findViewById(R.id.webView);
        final Intent intent = getIntent();
        if(null != intent.getStringExtra("website")) {
            website = intent.getStringExtra("website");
            urlIntent = "";
        }
        if (website.startsWith("ThePirateBay")) {
            toolbar.setBackgroundResource(R.color.tpb);
        } else if (website.startsWith("Limetorrents")) {
            toolbar.setBackgroundResource(R.color.limeT);
        } else {
            toolbar.setBackgroundResource(R.color.TorZ);
        }
        mSwipeRefreshLayout = findViewById(R.id.swipeLay);
        //setupWindowAnimations();
        mProgressBar = findViewById(R.id.progressBar);
        Drawable draw = getResources().getDrawable(R.drawable.customprogressbar);
        mProgressBar.setProgressDrawable(draw);
        final List<String> blockList = Arrays.asList("https://look.ufinkln.com", "https://titan.infra.systems",
                "https://www.get-express-vpn.com", "https://maskip.co", "https://vexacion.com", "https://inronbabunling", "https://jsc.adskeeper.co.uk");
        final List<String> blockedUrl = new ArrayList<>(blockList);

        String searchTerm = intent.getStringExtra("search");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(website);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
            }

        });

        CustomWebViewClient customWebViewClient = new CustomWebViewClient(getApplicationContext(), webView);

        /*final WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                *//*if(error.getErrorCode() == -6){
                    webView.stopLoading();
                }*//*
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mProgressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressBar.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                if (webView.getSettings().getJavaScriptEnabled()) {
                    if (website.equalsIgnoreCase("Torrentz(Proxy)") || website.equalsIgnoreCase("Torrentz")) {
                        view.getSettings().setJavaScriptEnabled(false);
                        mMenu.findItem(R.id.enable_javaScript).setChecked(false);
                    }
                }
                super.onPageFinished(view, url);


            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "shouldOverrideUrlLoading: " + url);

                view.loadUrl(url);
                if(webView.getUrl().startsWith("magnet")){
                    Log.i(TAG, "shouldOverrideUrlLoading: " + view.getUrl());
                    view.stopLoading();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //intent.setPackage("com.utorrent.client");
                    intent.setData(Uri.parse(url));
                    try {
                        startActivity(intent);
                    }catch (ActivityNotFoundException e){
                        e.printStackTrace();
                        Toast.makeText(BrowserActivity.this, "Download a Torrent Client", Toast.LENGTH_SHORT).show();

                    }
                    return true;
                }
                if(webView.getUrl().startsWith("http://itorrents.org")){
                    view.stopLoading();
                    String[] array = webView.getUrl().split("/");
                    String torrent_name =  array[array.length - 1];
                    Log.i(TAG, "torrentName " + torrent_name);
                    if(shareMode){
                        Intent intent1 = new Intent(Intent.ACTION_SEND);
                        intent1.setType("text/plain");
                        intent1.putExtra(Intent.EXTRA_TEXT, torrent_name +"\n\n\n\n\n"+ "URL: " + url );
                        startActivity(intent1);
                        return true;
                    }
                    String new_url = url.replace("http", "https");
                    Log.i(TAG, "Download Torrent: " + new_url);
                    Intent intent_new = new Intent(Intent.ACTION_VIEW);
                    intent_new.setData(Uri.parse(new_url));
                    *//*if(isPackageInstalled("com.android.chrome")){
                        intent_new.setPackage("com.android.chrome");
                    }*//*
                    Intent intentChooser = Intent.createChooser(intent_new, "Download using Browser");
                    startActivity(intentChooser);
                }
                for(int i = 0; i < blockedUrl.size(); i++) {
                    if (webView.getUrl().startsWith(blockedUrl.get(i))){
                        webView.stopLoading();
                        Log.i(TAG, "URL blocked");
                    }
                }

                return super.shouldOverrideUrlLoading(view, url);
            }
        };*/

        webView.setWebViewClient(customWebViewClient);
        //webView.setWebViewClient(webViewClient);
        final WebSettings webSettings = webView.getSettings();
        Log.i(TAG, "onCreate: " + website);
        if (website != null) {
            if (website.equalsIgnoreCase("Torrentz(Proxy)") || website.equalsIgnoreCase("Torrentz")) {
                webSettings.setJavaScriptEnabled(true);
                webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
                Log.i(TAG, "JavaScript Enabled");
            } else {
                Log.i(TAG, "JavaScript Disabled ");
                webSettings.setJavaScriptEnabled(false);
            }
        }

        String url;
        if (!urlIntent.isEmpty()) {
            url = urlIntent;
        } else {
            url = new UrlGenerator(website, searchTerm).createUrl();
        }
        webView.loadUrl(url);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Log.i(TAG, "onDownloadStart ");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                //intent.setPackage("com.utorrent.client");
                startActivity(intent);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });/*
        registerForContextMenu(webView);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i(TAG, "onLongClick: ");
                openContextMenu(v);
                return true;
            }
        });*/

    }

    private boolean isPackageInstalled(String packageName) {

        boolean found = true;
        PackageManager packageManager = getApplicationContext().getPackageManager();

        try {

            packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {

            found = false;
        }

        return found;
    }
    /*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        Log.i(TAG, "onCreateContextMenu: ");
        getMenuInflater().inflate(R.menu.webview_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_menu, menu);
        this.mMenu = menu;
        if (webView.getSettings().getJavaScriptEnabled()) {
            menu.findItem(R.id.enable_javaScript).setChecked(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.open_browser:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(webView.getUrl()));
                startActivity(intent);
                break;
            case R.id.enable_javaScript:
                if (item.isChecked()) {
                    webView.getSettings().setJavaScriptEnabled(false);
                    webView.reload();
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
                    webView.reload();
                }
                break;
            case R.id.sharemode:
                if (!shareMode) {
                    Toast.makeText(this, "Share Mode Enabled", Toast.LENGTH_SHORT).show();
                    shareMode = true;
                    item.setIcon(getResources().getDrawable(R.drawable.ic_action_share));
                } else {
                    Toast.makeText(this, "Share Mode Disabled", Toast.LENGTH_SHORT).show();
                    shareMode = false;
                    item.setIcon(getResources().getDrawable(R.drawable.ic_action_sharedis));

                }

                break;

            case R.id.sharelink:
                String sharelink = webView.getUrl();
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.setType("text/plain");
                intent1.putExtra(Intent.EXTRA_TEXT, sharelink);
                startActivity(intent1);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }

    }


}
