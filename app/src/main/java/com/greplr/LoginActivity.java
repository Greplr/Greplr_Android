package com.greplr;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.twitter.Twitter;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private boolean destroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button facebookLoginButton = (Button) findViewById(R.id.facebook_login);
        Button twitterLoginButton = (Button) findViewById(R.id.twitter_login);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);

        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> permissions = Arrays.asList(
                        "public_profile", "email", "user_birthday", "user_location");
                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (isDestroyed()) {
                            return;
                        }

                        if (parseUser == null) {
                            if (e != null) {
                                Toast.makeText(getApplicationContext(), getString(R.string.facebook_login_failed_toast), Toast.LENGTH_SHORT).show();
                            }
                        } else if (parseUser.isNew()) {
                            GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject fbUser,
                                                                GraphResponse response) {
                                            ParseUser parseUser = ParseUser.getCurrentUser();
                                            if (fbUser != null && parseUser != null
                                                    && fbUser.optString("name").length() > 0) {
                                                parseUser.put("name", fbUser.optString("name"));
                                                parseUser.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {
                                                        loginSuccess();
                                                    }
                                                });
                                            }
                                            loginSuccess();
                                        }
                                    }
                            ).executeAsync();
                        } else {
                            loginSuccess();
                        }
                    }
                });
            }
        });


        twitterLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseTwitterUtils.logIn(LoginActivity.this, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (isDestroyed()) {
                            return;
                        }
                        if (user == null) {
                            if (e != null) {
                                Toast.makeText(getApplicationContext(), getString(R.string.twitter_login_failed_toast), Toast.LENGTH_SHORT).show();
                            }
                        } else if (user.isNew()) {
                            Twitter twitterUser = ParseTwitterUtils.getTwitter();
                            if (twitterUser != null
                                    && twitterUser.getScreenName().length() > 0) {
                                user.put("name", twitterUser.getScreenName());
                                user.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        loginSuccess();
                                    }
                                });
                            }
                        } else {
                            loginSuccess();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Required for making Facebook login work
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        destroyed = true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean isDestroyed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return super.isDestroyed();
        }
        return destroyed;
    }

    private void loginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
