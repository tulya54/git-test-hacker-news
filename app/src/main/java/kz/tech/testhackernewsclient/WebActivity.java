package kz.tech.testhackernewsclient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity  extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initViews();
        initObjects();
    }

    private void initViews() {
        webView = (WebView) findViewById(R.id.wbView);
    }

    private void initObjects() {
        // String url = getIntent().getExtras().getString("url");
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        Log.e("dfd", "df");
    }
}
