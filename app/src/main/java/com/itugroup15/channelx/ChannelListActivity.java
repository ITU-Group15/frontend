package com.itugroup15.channelx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itugroup15.channelxAPI.APIClient;
import com.itugroup15.channelxAPI.APIController;
import com.itugroup15.channelxAPI.model.GetUserResponse;
import com.itugroup15.channelxAPI.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelListActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "appSettings";
    SharedPreferences settings;
    APIController apiController;

    RecyclerView recyclerView;
    ChannelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        adapter = new ChannelAdapter(null, this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This button will add new channel", Snackbar.LENGTH_LONG).show();
            }
        });

        String authToken = settings.getString("authToken", "");
        apiController = APIClient.getClient().create(APIController.class);
        Call<GetUserResponse> call = apiController.getUsers(authToken);
        call.enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetUserResponse> call, @NonNull Response<GetUserResponse> response) {
                adapter.swap(response.body().getContext());
            }

            @Override
            public void onFailure(@NonNull Call<GetUserResponse> call, @NonNull Throwable t) {

            }
        });
    }

    /* Show options on toolbar */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_chat_list_menu, menu);
        return true;
    }

    /* Handle the selections in options menu */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_logout:
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("loggedIn", false);
                editor.apply();
                Intent intent = new Intent(ChannelListActivity.this, LoginActivity.class);
                overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Channel adapter to recycler view */
    public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

        public  class ViewHolder extends RecyclerView.ViewHolder {

            TextView channelName;
            TextView channelInfo;
            TextView channelNotificationBadge;
            ImageView channelStatus;

            public ViewHolder(View itemView) {
                super(itemView);
                channelName = itemView.findViewById(R.id.channelName);
                channelInfo = itemView.findViewById(R.id.channelInfo);
                channelNotificationBadge = itemView.findViewById(R.id.channelNotificationBadge);
                channelStatus = itemView.findViewById(R.id.channelStatus);
            }
        }

        private List<User> channels;
        private Context context;

        public ChannelAdapter(List<User> channels, Context context) {
            this.channels = channels;
            this.context = context;
        }

        @Override
        public ChannelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View contactView = inflater.inflate(R.layout.channel_list_row_layout, parent, false);

            return new ViewHolder(contactView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            User channel = channels.get(position);
            if (channel.getUsername() != null)
                holder.channelName.setText(channel.getUsername());
            else
                holder.channelName.setText("");
            holder.channelInfo.setText(String.valueOf(channel.getUserID()));

            if (channel.getPhone() != null && channel.getPhone().contains("5"))
                holder.channelStatus.setImageDrawable(getResources().getDrawable(R.drawable.channel_icon_offline));

            if (channel.getPhone() != null && channel.getPhone().contains("8")) {
                holder.channelNotificationBadge.setText("2");
                holder.channelNotificationBadge.setVisibility(View.VISIBLE);
            }

            if (channel.getPhone() != null && channel.getPhone().contains("9")) {
                holder.channelNotificationBadge.setText("1");
                holder.channelNotificationBadge.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            if (channels != null)
                return channels.size();
            else
                return 0;
        }

        private Context getContext() {
            return context;
        }

        public void swap(List list){
            if (channels != null) {
                channels.clear();
                channels.addAll(list);
            }
            else {
                channels = list;
            }
            notifyDataSetChanged();
        }
    }
}
