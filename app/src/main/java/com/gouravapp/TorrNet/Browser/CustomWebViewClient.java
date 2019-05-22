package com.gouravapp.TorrNet.Browser;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.gouravapp.TorrNet.MainActivity;
import com.gouravapp.TorrNet.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.gouravapp.TorrNet.Browser.BrowserActivity.mMenu;
import static com.gouravapp.TorrNet.Browser.BrowserActivity.mProgressBar;
import static com.gouravapp.TorrNet.Browser.BrowserActivity.mSwipeRefreshLayout;
import static com.gouravapp.TorrNet.Browser.BrowserActivity.shareMode;
import static com.gouravapp.TorrNet.Browser.BrowserActivity.website;

public class CustomWebViewClient extends WebViewClient {
    private WebView webView;
    private Context mContext;
    List<String> blockedUrl;

    public CustomWebViewClient(Context context, WebView webView) {
        this.mContext = context;
        this.webView = webView;
        List<String> blockList = Arrays.asList("https://look.ufinkln.com","https://titan.infra.systems",
                "https://www.get-express-vpn.com","https://maskip.co","https://vexacion.com","https://inronbabunling", "https://jsc.adskeeper.co.uk","http://traffic.trafficposse.com");
        blockedUrl = new ArrayList<>(blockList);

    }
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                /*if(error.getErrorCode() == -6){
                    webView.stopLoading();
                }*/
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
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }catch (ActivityNotFoundException e){
                e.printStackTrace();
                Toast.makeText(mContext, "Download a Torrent Client", Toast.LENGTH_SHORT).show();

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
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent1);
                return true;
            }
            String new_url = url.replace("http", "https");
            Log.i(TAG, "Download Torrent: " + new_url);
            Intent intent_new = new Intent(Intent.ACTION_VIEW);
            intent_new.setData(Uri.parse(new_url));
                    /*if(isPackageInstalled("com.android.chrome")){
                        intent_new.setPackage("com.android.chrome");
                    }*/
            Intent intentChooser = Intent.createChooser(intent_new, "Download using Browser");
            intent_new.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentChooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intentChooser);
        }
        for(int i = 0; i < blockedUrl.size(); i++) {
            if (webView.getUrl().startsWith(blockedUrl.get(i))){
                webView.stopLoading();
                Log.i(TAG, "URL blocked");
            }
        }

        return super.shouldOverrideUrlLoading(view, url);
    }
};



