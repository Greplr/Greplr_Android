/*
 * Greplr : A super-aggregator. One app to rule them all.
 *     Copyright (C) 2015  Greplr Team
 *     Where Greplr Team consists of :
 *       1. Arnav Gupta
 *       2. Abhinav Sinha
 *       3. Prempal Singh
 *       4. Raghav Apoorv
 *       5. Shubham Dokania
 *       6. Yogesh Balan
 *
 *     The source code of this program is confidential and proprietary. If you are not part of the
 *     Greplr Team (one of the above 6 named individuals) you should not be viewing this code.
 *
 *     You should immediately close your copy of code, and destory the file. You are not authorised to
 *     be in possession of this code or view or modify it or use it in any capacity.
 */

package com.greplr;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.greplr.common.ui.CircularRevealView;
import com.greplr.common.ui.Helper;
import com.greplr.common.utils.Utils;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.twitter.Twitter;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private boolean destroyed = false;
    private Map<String, String> analyticsParams;
    private ImageView backgroundImage;
    private FrameLayout mLoginFrameLayout;
    private Button googleLoginButton, facebookLoginButton, twitterLoginButton;
    private CircularRevealView revealView;
    private int googleColor, twitterColor, facebookColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        facebookLoginButton = (Button) findViewById(R.id.facebook_login);
        twitterLoginButton = (Button) findViewById(R.id.twitter_login);
        googleLoginButton = (Button) findViewById(R.id.google_login);
        mLoginFrameLayout = (FrameLayout) findViewById(R.id.login_framelayout);
        revealView = (CircularRevealView) findViewById(R.id.reveal);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        analyticsParams = new HashMap<>();

        googleColor = getResources().getColor(R.color.google_main);
        twitterColor = getResources().getColor(R.color.twitter_main);
        facebookColor = getResources().getColor(R.color.facebook_main);

        backgroundImage = (ImageView) findViewById(R.id.login_background_image);
        Picasso.with(this).load(R.drawable.main_background).fit().centerCrop().into(backgroundImage);

        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircularRevealStart(revealView, facebookLoginButton, facebookColor);
                analyticsParams.put("account", "facebook");
                List<String> permissions = Arrays.asList(
                        "public_profile", "email"/*, "user_birthday", "user_location"*/); //Use when we get reviewed
                ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginActivity.this, permissions, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (isDestroyed()) {
                            return;
                        }

                        if (parseUser == null) {
                            if (e != null) {
                                analyticsParams.put("login", "failed");
                                ParseAnalytics.trackEventInBackground("login", analyticsParams);
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
                                                        analyticsParams.put("newUser", "true");
                                                        loginSuccess();
                                                    }
                                                });
                                            }
                                            analyticsParams.put("newUser", "true");
                                            loginSuccess();
                                        }
                                    }
                            ).executeAsync();
                        } else {
                            analyticsParams.put("newUser", "false");
                            loginSuccess();
                        }
                    }
                });
            }
        });


        twitterLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CircularRevealStart(revealView, twitterLoginButton, twitterColor);
                analyticsParams.put("account", "twitter");
                ParseTwitterUtils.logIn(LoginActivity.this, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (isDestroyed()) {
                            return;
                        }
                        if (user == null) {
                            if (e != null) {
                                analyticsParams.put("login", "failed");
                                ParseAnalytics.trackEventInBackground("login", analyticsParams);
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
                                        analyticsParams.put("newUser", "true");
                                        loginSuccess();
                                    }
                                });
                            }
                        } else {
                            analyticsParams.put("newUser", "false");
                            loginSuccess();
                        }
                    }
                });
            }
        });

        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircularRevealStart(revealView, googleLoginButton, googleColor);
            }
        });

//        ChangeColorOnTouch(facebookLoginButton, Color.parseColor("#3e62ba"), Color.parseColor("#aa263238"));
//        ChangeColorOnTouch(twitterLoginButton, Color.parseColor("#55acee"), Color.parseColor("#aa263238"));
//        ChangeColorOnTouch(googleLoginButton, Color.parseColor("#db4437"), Color.parseColor("#aa263238"));

    }

    //Use to Circular Reveal Show and collapse on touch
    //Not being used right now
    public void ChangeColorOnTouch(final Button button, final int primary, final int secondary){
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        CircularRevealStart(revealView, button, primary);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            button.setElevation(15f);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        CircularRevealEnd(revealView, button);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            button.setElevation(0f);
                        }
                        break;

                }
                return false;
            }
        });
    }

    public void CircularRevealStart(CircularRevealView revealView, Button button, int color){
        final Point p = Helper.getLocationInView(revealView, button);

       revealView.reveal(p.x, p.y, color, button.getHeight() / 10, 440, null);
    }

    public void CircularRevealEnd(CircularRevealView revealView, Button button){
        final Point p = Helper.getLocationInView(revealView, button);
        revealView.hide(p.x, p.y, Color.TRANSPARENT, button.getHeight() / 10, 440, null);
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
        analyticsParams.put("success", "true");
        ParseAnalytics.trackEventInBackground("login", analyticsParams);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
