package com.itugroup15.channelx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.itugroup15.channelxAPI.APIClient;
import com.itugroup15.channelxAPI.APIController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatListActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "appSettings";
    SharedPreferences settings;
    APIController apiController;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        listView = findViewById(R.id.listView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This button will ad new channel", Snackbar.LENGTH_LONG).show();
                //        .setAction("Action", null).show();
            }
        });

        String authToken = settings.getString("authToken", "");

        apiController = APIClient.getClient().create(APIController.class);
        Call<GetUserResponse> call = apiController.getUsers(authToken);
        call.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                Toast.makeText(getApplicationContext(), response.body().getContext().get(0).getRealsurname(), Toast.LENGTH_SHORT).show();

                ArrayAdapter<GetUserResponse.Context> arrayAdapter = new ArrayAdapter<GetUserResponse.Context>(
                        getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        response.body().getContext() );

                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_chat_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_logout:
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("loggedIn", false);
                editor.apply();
                Intent intent = new Intent(ChatListActivity.this, LoginActivity.class);
                overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
