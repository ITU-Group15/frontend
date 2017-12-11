package com.itugroup15.channelx;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
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
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.itugroup15.channelxAPI.APIClient;
import com.itugroup15.channelxAPI.APIController;
import com.itugroup15.channelxAPI.model.Channel;
import com.itugroup15.channelxAPI.model.GetChannelsResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelListActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "appSettings";
    public static final String API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSS'Z'";
    SharedPreferences settings;
    APIController apiController;

    RecyclerView recyclerView;
    ChannelAdapter adapter;

    SearchView searchView;

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
        Call<GetChannelsResponse> call = apiController.getChannels(authToken);
        call.enqueue(new Callback<GetChannelsResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetChannelsResponse> call, @NonNull Response<GetChannelsResponse> response) {
                if(response.body() != null){
                    adapter.swap(response.body().getContext());
                }
                else{
                    Intent intent = new Intent(ChannelListActivity.this, LoginActivity.class);
                    overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetChannelsResponse> call, @NonNull Throwable t) {

            }
        });
    }

    /* Show options on toolbar */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_chat_list_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    /* Handle the selections in options menu */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_account:
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("loggedIn", false);
                editor.apply();
                final Intent intent = new Intent(ChannelListActivity.this, LoginActivity.class);
                overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_search:
                Snackbar.make(findViewById(R.id.chat_list_layout), "This button will search for a channel", Snackbar.LENGTH_LONG).show();

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        Snackbar.make(findViewById(R.id.chat_list_layout), "yara", Snackbar.LENGTH_LONG).show();
                        //intent.putExtra("q", searchView.getQuery());
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        return false;
                    }
                });


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Channel adapter to recycler view */
    public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

        private int selected = -1;
        private ActionMode actionMode = null;

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView channelName;
            TextView channelInfo;
            TextView channelNotificationBadge;
            ImageView channelStatus;
            RelativeLayout channelCardLayout;

            private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    actionMode = mode;
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.activity_chat_list_action_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    if (selected < 0)
                        mode.finish();
                    return true;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_info:
                            Snackbar.make(findViewById(R.id.chat_list_layout), "This button will show info", Snackbar.LENGTH_LONG).show();
                            mode.finish(); // Action picked, so close the CAB
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    actionMode = null;
                    selected = -1;
                    notifyDataSetChanged();
                }
            };

            private ViewHolder(View itemView) {
                super(itemView);
                channelName = itemView.findViewById(R.id.channelName);
                channelInfo = itemView.findViewById(R.id.channelInfo);
                channelNotificationBadge = itemView.findViewById(R.id.channelNotificationBadge);
                channelStatus = itemView.findViewById(R.id.channelStatus);
                channelCardLayout = itemView.findViewById(R.id.channelCardLayout);
            }

            void update(final int position) {
                Channel channel = channels.get(position);
                if (channel.getChannelName() != null)
                    channelName.setText(channel.getChannelName());
                else
                    channelName.setText("");

                SimpleDateFormat inputDateFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.US);
                SimpleDateFormat inputAvailableDateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                SimpleDateFormat outputDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
                SimpleDateFormat weekDayFormat = new SimpleDateFormat("EEE", Locale.US);

                Date currentTime = Calendar.getInstance().getTime();


                try {
                    Date updateDate = inputDateFormat.parse(channel.getUpdatedAt());
                    channelInfo.setText(outputDateFormat.format(updateDate));

                    Date availableTimeStart = inputAvailableDateFormat.parse(channel.getStartTime());
                    Date availableTimeEnd = inputAvailableDateFormat.parse(channel.getEndTime());
                    String weekDay = weekDayFormat.format(currentTime);

                    int currentMinutes = currentTime.getHours() * 60 + currentTime.getMinutes();
                    int endMinutes = (availableTimeEnd.getHours() + 3 ) * 60 + availableTimeEnd.getMinutes();
                    int startMinutes = (availableTimeStart.getHours() + 3 ) * 60 + availableTimeStart.getMinutes();

                    if (channel.getAvailableDays() == null || channel.getAvailableDays().contains(weekDay)) {
                        if (currentMinutes < endMinutes && currentMinutes > startMinutes) {
                            channelCardLayout.setEnabled(true);
                            channelStatus.setImageDrawable(getResources().getDrawable(R.drawable.channel_icon_online));
                        }
                        else {
                            channelCardLayout.setEnabled(false);
                            channelStatus.setImageDrawable(getResources().getDrawable(R.drawable.channel_icon_offline));
                        }
                    }
                    else {
                        channelCardLayout.setEnabled(false);
                        channelStatus.setImageDrawable(getResources().getDrawable(R.drawable.channel_icon_offline));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
/*
                if (channel.getPhone() != null && channel.getPhone().contains("5"))
                    channelStatus.setImageDrawable(getResources().getDrawable(R.drawable.channel_icon_offline));

                if (channel.getPhone() != null && channel.getPhone().contains("8")) {
                    channelNotificationBadge.setText("2");
                    channelNotificationBadge.setVisibility(View.VISIBLE);
                }

                if (channel.getPhone() != null && channel.getPhone().contains("9")) {
                    channelNotificationBadge.setText("1");
                    channelNotificationBadge.setVisibility(View.VISIBLE);
                }
*/
                if (selected == position) {
                    channelCardLayout.setBackgroundColor(Color.LTGRAY);
                } else {
                    channelCardLayout.setBackgroundColor(Color.WHITE);
                }

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (position == selected && actionMode != null) {
                            selected = -1;
                            actionMode.invalidate();
                        }
                        else if (position > -1  && actionMode != null) {
                            channelCardLayout.setBackgroundColor(Color.LTGRAY);
                            selected = -1;
                            actionMode.invalidate();
                            selected = position;
                            ((AppCompatActivity)view.getContext()).startSupportActionMode(actionModeCallbacks);
                        } else {
                            channelCardLayout.setBackgroundColor(Color.LTGRAY);
                            selected = position;
                            ((AppCompatActivity)view.getContext()).startSupportActionMode(actionModeCallbacks);

                        }

                        return true;
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (position == selected && actionMode != null) {
                            selected = -1;
                            actionMode.invalidate();
                        }
                        else if (position > -1  && actionMode != null) {
                            channelCardLayout.setBackgroundColor(Color.LTGRAY);
                            selected = -1;
                            actionMode.invalidate();
                            selected = position;
                            ((AppCompatActivity)view.getContext()).startSupportActionMode(actionModeCallbacks);
                        }
                        else {
                            Channel selectedChannel = channels.get(position);

                            SimpleDateFormat inputAvailableDateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                            SimpleDateFormat weekDayFormat = new SimpleDateFormat("EEE", Locale.US);

                            Date currentTime = Calendar.getInstance().getTime();


                            try {
                                Date availableTimeStart = inputAvailableDateFormat.parse(selectedChannel.getStartTime());
                                Date availableTimeEnd = inputAvailableDateFormat.parse(selectedChannel.getEndTime());
                                String weekDay = weekDayFormat.format(currentTime);

                                int currentMinutes = currentTime.getHours() * 60 + currentTime.getMinutes();
                                int endMinutes = (availableTimeEnd.getHours() + 3 ) * 60 + availableTimeEnd.getMinutes();
                                int startMinutes = (availableTimeStart.getHours() + 3 ) * 60 + availableTimeStart.getMinutes();

                                if (selectedChannel.getAvailableDays() == null || selectedChannel.getAvailableDays().contains(weekDay)) {
                                    if (currentMinutes < endMinutes && currentMinutes > startMinutes) {
                                        startActivityForResult(ChatActivity.getIntent(context, selectedChannel.getChannelID()
                                                , 0, selectedChannel.getOwnerID()),1);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Channel offline", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Channel offline", Toast.LENGTH_SHORT).show();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }

        private List<Channel> channels;
        private Context context;

        private ChannelAdapter(List<Channel> channels, Context context) {
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
            holder.update(position);
        }

        @Override
        public int getItemCount() {
            if (channels != null)
                return channels.size();
            else
                return 0;
        }

        private void swap(List<Channel> list){
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
