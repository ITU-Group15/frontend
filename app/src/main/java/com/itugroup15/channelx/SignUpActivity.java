package com.itugroup15.channelx;

import android.content.Context;
import android.content.Intent;
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

        APIController apiController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    public void onSignUpButtonClicked(View view) {

        /* Hides software keyboard */
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null)
            inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null :
                    getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        /* Hides software keyboard */

        final EditText email = findViewById(R.id.emailInput);
        final EditText password = findViewById(R.id.passwordInput);
        final EditText name = findViewById(R.id.nameInput);
        final EditText surname = findViewById(R.id.surnameInput);
        final EditText phone = findViewById(R.id.phoneNumberInput);

        final User user = new User(
                email.getText().toString(),
                password.getText().toString(),
                name.getText().toString(),
                surname.getText().toString(),
                phone.getText().toString());

        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        apiController = APIClient.getClient().create(APIController.class);
        Call<LoginResponse> call = apiController.register(user);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            @SuppressWarnings("ConstantConditions")
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);

                    intent.putExtra("signedUp", true);
                    intent.putExtra("email", email.getText().toString());
                    intent.putExtra("password", password.getText().toString());

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

    @Override
    public void onBackPressed() {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
        super.onBackPressed();
    }

    public void onCancelButtonClicked(View view) { onBackPressed(); }
}
