package com.example.jimmymunoz.testappspanel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TabInfo extends Fragment {
    private String currentURL = "http://lagenda.apnl.ws/?key=N25QS3JnOWg=&deviceuid=MTIzNDU2&action=Z2V0aW5mb3M=&data1=bWVudGlvbg==&format=anNvbg==&noencode=1";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("SwA", "WVF onCreateView");
        View v = inflater.inflate(R.layout.tab_fragment_2, container, false);
        if (currentURL != null) {
            Log.d("SwA", "Current URL 1[" + currentURL + "]");

            WebView wv = (WebView) v.findViewById(R.id.webPage);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.setWebViewClient(new SwAWebClient());
            wv.loadUrl(currentURL);
        }
        return v;
        //return inflater.inflate(R.layout.tab_fragment_2, container, false);
    }

    public void updateUrl(String url) {
        Log.d("SwA", "Update URL ["+url+"] - View ["+getView()+"]");
        currentURL = url;

        WebView wv = (WebView) getView().findViewById(R.id.webPage);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(url);

    }

    private class SwAWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }
}
