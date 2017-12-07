package com.chx.channelx;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chx.R;

public class WelcomeActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "appSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView imageView = findViewById(R.id.animatedLogoView);
        Button loginButton = findViewById(R.id.loginButton);
        Button signUpButton = findViewById(R.id.signUpButton);

        /*Check if logged in*/
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean loggedIn = settings.getBoolean("loggedIn", false);

        if (loggedIn) {
            finish();
            Intent intent = new Intent(WelcomeActivity.this, ChannelListActivity.class);
            startActivity(intent);
        }
        /*Check if logged in*/

        /*Handle Animations*/
        Animatable animatable = (Animatable) imageView.getDrawable();
        animatable.start();

        ObjectAnimator animLoginButtonAlpha = ObjectAnimator.ofFloat(loginButton, "alpha", 0f, 1f);
        ObjectAnimator animSignUpButtonAlpha = ObjectAnimator.ofFloat(signUpButton, "alpha", 0f, 1f);
        AnimatorSet buttonAnimations = new AnimatorSet();

        buttonAnimations.playSequentially(animLoginButtonAlpha, animSignUpButtonAlpha);
        buttonAnimations.setStartDelay(1000);
        buttonAnimations.setDuration(500);
        buttonAnimations.start();
        /*Handle Animations*/
    }

    public void onLoginButtonClicked(View view) {
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        overridePendingTransition(android.R.anim.overshoot_interpolator, android.R.anim.slide_out_right);
        startActivity(intent);
    }

    public void onSignUpButtonClicked(View view) {
        Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
        overridePendingTransition(android.R.anim.overshoot_interpolator, android.R.anim.slide_out_right);
        startActivity(intent);
    }
}
