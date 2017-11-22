package com.itugroup15.channelx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.itugroup15.channelxAPI.APIClient;
import com.itugroup15.channelxAPI.APIController;
import com.itugroup15.channelxAPI.model.LoginResponse;
import com.itugroup15.channelxAPI.model.User;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "appSettings";
    APIController apiController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    public void onClickSignUpButton(View view) {

        /* Hides software keyboard */
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null)
            inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null :
                    getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        /* Hides software keyboard */

        final EditText username = findViewById(R.id.inputUsername);
        final EditText password = findViewById(R.id.inputPassword);
        EditText name = findViewById(R.id.inputName);
        EditText surname = findViewById(R.id.inputSurname);
        EditText phone = findViewById(R.id.inputPhoneNumber);

        final User user = new User(
                username.getText().toString(),
                password.getText().toString(),
                name.getText().toString(),
                surname.getText().toString(),
                phone.getText().toString());

        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        apiController = APIClient.getClient().create(APIController.class);
        Call<LoginResponse> call = apiController.register(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    /*
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putBoolean("loggedIn", true);
                    editor.putString("authToken", response.body().getContext().getJwtToken());
                    editor.apply();
                    */

                    /*
                    Intent intent = new Intent(getApplicationContext(), ChatListActivity.class);
                    intent.putExtra("USER_NAME", user.getUsername());
                    overridePendingTransition(android.R.anim.overshoot_interpolator, android.R.anim.slide_out_right);
                    startActivity(intent);
                    */
                    Intent intent = new Intent();
                    intent.putExtra("username", username.getText().toString());
                    intent.putExtra("password", password.getText().toString());
                    setResult(Activity.RESULT_OK, intent);
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

            }
        });
    }

    public void onClickCancelButton(View view) { onBackPressed(); }

    @Override
    public void onBackPressed() {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
        super.onBackPressed();
    }
}
