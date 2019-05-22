package com.gouravapp.TorrNet.Browser;

import android.util.Log;

import static android.content.ContentValues.TAG;

class UrlGenerator {
    private String mWebsite;
    private String mSearchTerm;

    public UrlGenerator(String website, String searchTerm) {
        mWebsite = website;
        mSearchTerm = searchTerm;
    }

    public String createUrl(){
        String url;
        switch (mWebsite){
            case "ThePirateBay":
                url = "https://m.thepiratebay.org/search/" + mSearchTerm+"/0/0/1";
                break;
            case "Limetorrents":
                url = "https://limetorrents.info/search/all/"+ mSearchTerm+"/seeds/1/";
                break;
            case "Torrentz":
                url= "https://torrentz2.eu/search?f="+ mSearchTerm;
                break;
            case "ThePirateBay(Proxy)":
                url = "https://pirateproxy.lat/mobileproxy/search/" + mSearchTerm+ "/0/0/1";
                break;
            case "Limetorrents(Proxy)":
                url = "https://limetorrents.asia/search/all/"+ mSearchTerm+"/seeds/1/";
                break;
            case "Torrentz(Proxy)":
                url = "https://torrentz2.is/search?f="+ mSearchTerm;
                break;

            default:
                url = null;
        }
        Log.i(TAG, "createUrl: " + url);

        return url;
    }
}
