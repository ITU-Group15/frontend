package com.itugroup15.channelx;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.itugroup15.channelxAPI.APIClient;
import com.itugroup15.channelxAPI.APIController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatListActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "appSettings";
    APIController apiController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                SharedPreferences settings = getSharedPreferences("appSettings", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("loggedIn", false);
                editor.apply();
            }
        });


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String authToken = settings.getString("authToken", "");


        apiController = APIClient.getClient().create(APIController.class);
        Call<GetUserResponse> call = apiController.getUsers(authToken);
        call.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                Toast.makeText(getApplicationContext(), response.body().getContext().get(0).getRealsurname(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {

            }
        });


    }



}
