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
import com.itugroup15.channelxAPI.model.ChannelInfoDeleteRequest;
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

import static com.itugroup15.channelx.TimePickerFragmentDialog.properTime;

public class ChannelListActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "appSettings";
    public static final String API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSS'Z'";

    private String authToken;
    private SharedPreferences settings;
    private APIController apiController;

    private RecyclerView recyclerView;
    private ChannelAdapter adapter;

    private SearchView searchView;

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
                startActivity(CreateChannel.getIntent(recyclerView.getContext()));
            }
        });

        authToken = settings.getString("authToken", "");
        apiController = APIClient.getClient().create(APIController.class);
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
            case R.id.action_logout:
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("loggedIn", false);
                editor.apply();
                final Intent intent = new Intent(ChannelListActivity.this, LoginActivity.class);
                overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_search:
                searchView.setFocusable(true);
                searchView.setIconified(false);
                searchView.requestFocusFromTouch();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        //nackbar.make(findViewById(R.id.chat_list_layout), "yara", Snackbar.LENGTH_LONG).show();
                        //intent.putExtra("q", searchView.getQuery());
                        Intent intent1 = new Intent(ChannelListActivity.this, SearchResultsActivity.class);
                        intent1.putExtra("query", searchView.getQuery().toString());
                        startActivity(intent1);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {

                        return false;
                    }
                });
            case R.id.action_account:
                startActivityForResult(ProfileActivity.getIntent(this),1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateChannels();
    }

    private void updateChannels(){
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

    public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

        private int selected = -1;
        private ActionMode actionMode = null;
        private boolean onProcess = false;

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
                        case R.id.action_delete:
                            if(selected == -1){
                                mode.finish();
                                return true;
                            }
                            Call<ChannelInfoDeleteRequest> call = apiController
                                    .deleteChannel(authToken,channels.get(selected).getChannelID());
                            onProcess = true;
                            call.enqueue(new Callback<ChannelInfoDeleteRequest>() {
                                @Override
                                public void onResponse(@NonNull Call<ChannelInfoDeleteRequest> call
                                        , @NonNull Response<ChannelInfoDeleteRequest> response) {
                                    if(response.body() != null && response.body().getCode() == 0){
                                        adapter.remove(selected);
                                        Snackbar.make(findViewById(R.id.chat_list_layout)
                                                , "Channel Deleted", Snackbar.LENGTH_LONG).show();
                                        onProcess = false;
                                        selected = -1;
                                    }
                                    else {
                                        Snackbar.make(findViewById(R.id.chat_list_layout)
                                                , "Something went wrong", Snackbar.LENGTH_LONG)
                                                .show();
                                        onProcess = false;
                                        selected = -1;
                                    }
                                }
                                @Override
                                public void onFailure(@NonNull Call<ChannelInfoDeleteRequest> call
                                        , @NonNull Throwable t) {
                                    Snackbar.make(findViewById(R.id.chat_list_layout)
                                            , "Connection Error", Snackbar.LENGTH_LONG).show();
                                    onProcess = false;
                                    selected = -1;
                                }
                            });
                            mode.finish();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    if(!onProcess)
                        selected = -1;
                    actionMode = null;
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
                    int lastUpdatedhour =(updateDate.getHours() + 3 )%24;
                    int lastUpdatedMin =(updateDate.getMinutes())%60;

                    channelInfo.setText(properTime(lastUpdatedhour, lastUpdatedMin));

                    Date availableTimeStart = inputAvailableDateFormat.parse(channel.getStartTime());
                    Date availableTimeEnd = inputAvailableDateFormat.parse(channel.getEndTime());
                    String weekDay = weekDayFormat.format(currentTime);

                    int currentMinutes = currentTime.getHours() * 60 + currentTime.getMinutes();
                    int endMinutes = (availableTimeEnd.getHours()) * 60 + availableTimeEnd.getMinutes();
                    int startMinutes = (availableTimeStart.getHours()) * 60 + availableTimeStart.getMinutes();
                    endMinutes = (endMinutes + 60*3) % (60*24);
                    startMinutes = (startMinutes + 60*3) % (60*24);

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
                                int endMinutes = (availableTimeEnd.getHours()) * 60 + availableTimeEnd.getMinutes();
                                int startMinutes = (availableTimeStart.getHours()) * 60 + availableTimeStart.getMinutes();
                                endMinutes = (endMinutes + 60*3) % (60*24);
                                startMinutes = (startMinutes + 60*3) % (60*24);
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

        private void remove(int position){
            channels.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, channels.size());
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
