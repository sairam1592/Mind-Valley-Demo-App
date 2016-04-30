package com.mindvalley.app.mindvalley_arun_android_test.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mindvalley.app.mindvalley_arun_android_test.R;
import com.mindvalley.app.mindvalley_arun_android_test.misc.Constants;

/**
 * Created by arun on 4/19/2016.
 */
public class WebViewActivity extends Activity {

    String link_url;
    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        setFinishOnTouchOutside(false);
        Intent i = getIntent();
        link_url = i.getStringExtra(Constants.INTENT_WEBVIEW_URL);
        webView = (WebView) findViewById(R.id.webView_url);
        startWebView(link_url);
    }

    private void startWebView(String url) {

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
