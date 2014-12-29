package com.mi.FoodChoice;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.mi.FoodChoice.data.Constants;

public class WebViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        WebView mWebView = (WebView) findViewById(R.id.content);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(getIntent().getStringExtra(Constants.KEY_URI));
    }
}
