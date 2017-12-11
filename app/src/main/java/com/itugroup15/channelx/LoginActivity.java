package com.itugroup15.channelx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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


        Intent intent = getIntent();

        /* If activity started from forgot password activity */
        if (intent.getBooleanExtra("forgotPass", false)) {
            Snackbar.make(findViewById(R.id.loginActivityLayout),
                    "New password is sent to your mail", Snackbar.LENGTH_LONG).show();
        }

        /* If activity started from sign up activity */
        else if (intent.getBooleanExtra("signedUp", false)) {
            EditText username = findViewById(R.id.emailInput);
            EditText password = findViewById(R.id.passwordInput);

            username.setText(intent.getStringExtra("email"));
            password.setText(intent.getStringExtra("password"));

            /* Call on click function to directly login user */
            onLoginButtonClicked(findViewById(android.R.id.content));
        }
    }

    public void onLoginButtonClicked(View view) {
        /* Hides software keyboard */
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null)
            inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null :
                    getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        /* Hides software keyboard */

        final EditText email = findViewById(R.id.emailInput);
        EditText password = findViewById(R.id.passwordInput);

        final Login login = new Login(
                email.getText().toString(),
                password.getText().toString());

        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        /* API call*/
        apiController = APIClient.getClient().create(APIController.class);
        Call<LoginResponse> call = apiController.login(login);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            @SuppressWarnings("ConstantConditions")
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("loggedIn", true);
                    editor.putString("authToken", response.body().getContext().getJwtToken());
                    editor.putInt(getString(R.string.sharedpref_userid),response.body().getContext().getUserID());
                    editor.putString(getString(R.string.sharedpref_email), email.getText().toString());
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), ChannelListActivity.class);
                    intent.putExtra(getString(R.string.sharedpref_email), login.getUsername());
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
        startActivity(intent);
        finish();
    }

    public void onForgotPasswordButtonClicked(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        overridePendingTransition(android.R.anim.overshoot_interpolator, android.R.anim.slide_out_right);
        startActivity(intent);
    }
}
