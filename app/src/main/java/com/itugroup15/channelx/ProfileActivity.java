package com.itugroup15.channelx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itugroup15.channelxAPI.APIClient;
import com.itugroup15.channelxAPI.APIController;
import com.itugroup15.channelxAPI.model.ProfileRequests;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.itugroup15.channelx.LoginActivity.PREFS_NAME;

public class ProfileActivity extends AppCompatActivity {

    private TextView emailAdTV;
    private TextView nameTV;
    private TextView surnameTV;

    private EditText nicknameET;
    private EditText telET;


    private String authToken;
    private SharedPreferences settings;
    private APIController apiController;

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        emailAdTV = findViewById(R.id.profileEmail);
        nameTV = findViewById(R.id.profileName);
        surnameTV = findViewById(R.id.profileSurname);
        telET = findViewById(R.id.profileTel);
        nicknameET = findViewById(R.id.profileNickname);

        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        authToken = settings.getString("authToken", "");
        apiController = APIClient.getClient().create(APIController.class);
        getProfileInfo();
    }

    public void onProfileCancel(View view){
        finish();
    }

    public void onProfileSave(View view){
        ProfileRequests pr = new ProfileRequests();
        pr.setNickname(nicknameET.getText().toString());
        pr.setPhone(telET.getText().toString());
        pr.setRealname(nameTV.getText().toString());
        pr.setRealsurname(surnameTV.getText().toString());
        pr.setUsername(emailAdTV.getText().toString());

        Call<ProfileRequests> call = apiController.updateProfile(authToken, pr);
        call.enqueue(new Callback<ProfileRequests>() {
            @Override
            public void onResponse(Call<ProfileRequests> call, Response<ProfileRequests> response) {
                if(response.body()!=null && response.body().getCode() == 0){
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ProfileRequests> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection Error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getProfileInfo(){
        Call<ProfileRequests> call = apiController.getProfile(authToken);
        call.enqueue(new Callback<ProfileRequests>() {
            @Override
            public void onResponse(Call<ProfileRequests> call, Response<ProfileRequests> response) {
                if(response.body() != null && response.body().getContext() != null){
                    nameTV.setText(response.body().getContext().getUser().getRealname());
                    surnameTV.setText(response.body().getContext().getUser().getRealsurname());
                    emailAdTV.setText(response.body().getContext().getUser().getUsername());
                    telET.setText(response.body().getContext().getUser().getPhone());
                    nicknameET.setText(response.body().getContext().getUser().getNickname());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Connection Error",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ProfileRequests> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Connection Error",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
