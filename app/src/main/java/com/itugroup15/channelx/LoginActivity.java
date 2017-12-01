package com.itugroup15.channelx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itugroup15.channelxAPI.APIClient;
import com.itugroup15.channelxAPI.APIController;
import com.itugroup15.channelxAPI.model.Login;
import com.itugroup15.channelxAPI.model.LoginResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "appSettings";
    APIController apiController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.progressBar).setVisibility(View.GONE);

        boolean forgotPass = getIntent().getBooleanExtra("forgotPass", false);
        if (forgotPass) {

            Snackbar.make(findViewById(R.id.loginActivityLayout),
                    "New password is sent to your mail", Snackbar.LENGTH_LONG).show();
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void onLoginButtonClicked(View view) {
        /* Hides software keyboard */
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null)
            inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null :
                    getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        /* Hides software keyboard */

        EditText email = findViewById(R.id.emailInput);
        EditText password = findViewById(R.id.passwordInput);

        final Login login = new Login(
                email.getText().toString(),
                password.getText().toString());

        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        apiController = APIClient.getClient().create(APIController.class);
        Call<LoginResponse> call = apiController.login(login);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("loggedIn", true);
                    editor.putString("authToken", response.body().getContext().getJwtToken());
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), ChatListActivity.class);
                    intent.putExtra("USER_NAME", login.getUsername());
                    overridePendingTransition(android.R.anim.overshoot_interpolator, android.R.anim.slide_out_right);
                    startActivity(intent);
                    finish();
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getApplicationContext(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onCancelButtonClicked(View view) {
        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
        overridePendingTransition(android.R.anim.overshoot_interpolator, android.R.anim.slide_out_right);
        finish();
        startActivity(intent);
    }

    public void onForgotPasswordButtonClicked(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        overridePendingTransition(android.R.anim.overshoot_interpolator, android.R.anim.slide_out_right);
        startActivity(intent);
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == 1 && resultCode == Activity.RESULT_OK ) {
            EditText username = findViewById(R.id.inputUsername);
            EditText password = findViewById(R.id.inputPassword);

            username.setText(data.getStringExtra("username"));
            password.setText(data.getStringExtra("password"));

            onClickLoginButton(findViewById(android.R.id.content));
        }
    }*/
}
