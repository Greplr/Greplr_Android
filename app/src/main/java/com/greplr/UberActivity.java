package com.greplr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.greplr.api.UberApi;
import com.greplr.models.travel.UberAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UberActivity extends AppCompatActivity {

    private Pattern AUTH_CODE_PATTERN = Pattern.compile("(code)" + "(=)" + "(.*)");
    public static final String LOG_TAG = "Greplr/Uber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uber);
        final WebView wv = (WebView) findViewById(R.id.webView);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("url", url);
                Matcher m = AUTH_CODE_PATTERN.matcher(url);
                if (m.find()) {
                    String authenticationCode = url.split("=")[1];
                    wv.setVisibility(View.GONE);
                    Log.d("auth", authenticationCode);
                    UberApi apiHandler = ((App) getApplication()).getUberApiHandler();
                    apiHandler.getUberAuth("JNg33SN8pXKQJIL0lLRMEEDkVp92j0de3GzkrSpj",
                            "o7W68lOQoiO6SlEnBF91CbqsGuAOYlLx",
                            "authorization_code",
                            "https://www.google.com",
                            authenticationCode,
                            new Callback<UberAuth>() {
                                @Override
                                public void success(UberAuth uberAuth, Response response) {
                                    Log.d("Access token", uberAuth.getAccess_token());
                                    Log.d("Refresh token", uberAuth.getRefresh_token());
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Log.d(LOG_TAG, "failure" + error.getUrl() + error.getMessage());
                                }
                            });

                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        wv.loadUrl("https://login.uber.com/oauth/authorize?response_type=code&client_id=o7W68lOQoiO6SlEnBF91CbqsGuAOYlLx");
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
