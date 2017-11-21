package com.itugroup15.channelx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    public void onClickSignUpButton(View view) {
        if (findViewById(R.id.progressBar).getVisibility() == View.GONE)
            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    public void onClickCancelButton(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
        super.onBackPressed();
    }
}
