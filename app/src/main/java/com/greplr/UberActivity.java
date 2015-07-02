package com.greplr;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class UberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uber);
        final WebView wv = (WebView) findViewById(R.id.webView);
        if (Build.VERSION.SDK_INT >= 19) {
            wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String authenticationCode = url.split("=")[1];
                wv.setVisibility(View.GONE);
                Log.d("auth", authenticationCode);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        wv.loadUrl("https://login.uber.com/oauth/authorize?client_id=o7W68lOQoiO6SlEnBF91CbqsGuAOYlLx&response_type=code");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_uber, menu);
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
