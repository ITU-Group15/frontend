package com.itugroup15.channelx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    public void onSendButtonClicked(View view) {
        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        overridePendingTransition(android.R.anim.overshoot_interpolator, android.R.anim.slide_out_right);
        intent.putExtra("forgotPass", true);
        startActivity(intent);
        finish();
    }

    public void onCancelButtonClicked(View view) {
        finish();
    }
}
