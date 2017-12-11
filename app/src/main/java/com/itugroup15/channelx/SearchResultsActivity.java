package com.itugroup15.channelx;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itugroup15.channelxAPI.APIClient;
import com.itugroup15.channelxAPI.APIController;
import com.itugroup15.channelxAPI.model.Channel;
import com.itugroup15.channelxAPI.model.GetChannelsResponse;
import com.itugroup15.channelxAPI.model.SearchQuery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchResultsActivity.ChannelAdapter adapter;
    public static final String PREFS_NAME = "appSettings";
    public static final String API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSS'Z'";
    SharedPreferences settings;
    APIController apiController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setTitle("Search results");

        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        adapter = new SearchResultsActivity.ChannelAdapter(null, this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String query = intent.getStringExtra(SearchManager.QUERY);

        String authToken = settings.getString("authToken", "");

        SearchQuery searchQuery = new SearchQuery();
        searchQuery.setChannelName(query);

        apiController = APIClient.getClient().create(APIController.class);
        Call<GetChannelsResponse> call = apiController.searchChannels(authToken, searchQuery);

        call.enqueue(new Callback<GetChannelsResponse>() {
            @Override
            public void onResponse(Call<GetChannelsResponse> call, Response<GetChannelsResponse> response) {
                if(response.body() != null){
                    adapter.swap(response.body().getContext());
                }
                else {
                    Toast.makeText(getApplicationContext(), "No channel found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetChannelsResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();

            }
        });






    }



    public class ChannelAdapter extends RecyclerView.Adapter<SearchResultsActivity.ChannelAdapter.ViewHolder> {



        class ViewHolder extends RecyclerView.ViewHolder {

            TextView channelName;
            TextView channelInfo;
            TextView channelNotificationBadge;
            ImageView channelStatus;
            RelativeLayout channelCardLayout;



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

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO join channel
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
        public SearchResultsActivity.ChannelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View contactView = inflater.inflate(R.layout.channel_list_row_layout, parent, false);

            return new SearchResultsActivity.ChannelAdapter.ViewHolder(contactView);
        }

        @Override
        public void onBindViewHolder(SearchResultsActivity.ChannelAdapter.ViewHolder holder, int position) {
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
