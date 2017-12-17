package com.itugroup15.channelx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itugroup15.channelxAPI.APIClient;
import com.itugroup15.channelxAPI.APIController;
import com.itugroup15.channelxAPI.model.ChannelInfoDeleteRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.itugroup15.channelx.LoginActivity.PREFS_NAME;

public class ChannelInformationActivity extends AppCompatActivity {

    public static final String  CHANNEL_ID_INTENT = "channel_id";

    private SharedPreferences settings;
    private APIController apiController;
    private ChannelInfoAdapter adapter;
    private RecyclerView rv;

    private int channelID;

    private TextView channelNameTV;
    private TextView channelOwnerNickNameTV;
    private TextView channelAvailableDaysTV;
    private TextView channelAvailableHoursTV;
    private TextView channelParticipantsLabelTV;

    public static Intent getIntent(Context context, int channelID){
        Intent intent = new Intent(context, ChannelInformationActivity.class);
        intent.putExtra(CHANNEL_ID_INTENT, channelID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_information);
        apiController = APIClient.getClient().create(APIController.class);
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        channelID = getIntent().getExtras().getInt(CHANNEL_ID_INTENT, -1);
        channelNameTV = (TextView)findViewById(R.id.channelInfoChannelName);
        channelOwnerNickNameTV = (TextView)findViewById(R.id.channelInfoChannelOwnerName);
        channelAvailableDaysTV = (TextView)findViewById(R.id.channelInfoAvailableDays);
        channelAvailableHoursTV = (TextView)findViewById(R.id.channelInfoAvailableHours);
        channelParticipantsLabelTV= (TextView)findViewById(R.id.channelInfoParticipantsLabel);

        rv = findViewById(R.id.channelInfoRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        adapter = new ChannelInfoAdapter(null, this);
        rv.setAdapter(adapter);

        getChannelInformation();
    }

    private void getChannelInformation(){
        Call<ChannelInfoDeleteRequest> call = apiController.getChannelInfo
                (settings.getString("authToken", ""),channelID);

        call.enqueue(new Callback<ChannelInfoDeleteRequest>() {
            @Override
            public void onResponse(Call<ChannelInfoDeleteRequest> call, Response<ChannelInfoDeleteRequest> response) {
                if (response.body() != null
                        && response.body().getContext() != null
                        && response.body().getContext().getNickname() != null) {
                    String avdays = response.body().getContext().getAvailableDays().toString();
                    avdays = avdays.substring(1, avdays.length()-1);
                    adapter.addItemsToEnd(response.body().getContext().getNickname());
                    channelNameTV.setText("Channel name: " + response.body().getContext().getChannelName());
                    channelOwnerNickNameTV.setText("Channel owner nickname: " + response.body().getContext().getOwner());
                    channelAvailableDaysTV.setText("Available days: " + avdays);
                    channelAvailableHoursTV.setText("Available hours: "
                            + toLocalHourMin(response.body().getContext().getStartTime(),3)
                            + " - "
                            + toLocalHourMin(response.body().getContext().getEndTime(),3));
                    channelParticipantsLabelTV.setText("Participants :");
                }
                else {
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ChannelInfoDeleteRequest> call, Throwable t) {
                Toast.makeText(adapter.getContext(), "Connection Error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class ChannelInfoAdapter extends RecyclerView.Adapter<ChannelInformationActivity.ChannelInfoAdapter.ViewHolder> {

        public  class ViewHolder extends RecyclerView.ViewHolder {
            TextView nickname;
            public ViewHolder(View itemView) {
                super(itemView);
                nickname = itemView.findViewById(R.id.channelUserNickname);
            }
        }

        private List<String> participants = new ArrayList<>();
        private Context context;

        public ChannelInfoAdapter(List<String> participants, Context context) {
            if(participants!= null) // always null
                this.participants.addAll(participants);
            this.context = context;
        }

        @Override
        public ChannelInformationActivity.ChannelInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View contactView = inflater.inflate(R.layout.channel_info_row_layout, parent, false);
            return new ChannelInformationActivity.ChannelInfoAdapter.ViewHolder(contactView);
        }

        @Override
        public void onBindViewHolder(ChannelInformationActivity.ChannelInfoAdapter.ViewHolder holder, int position) {
            holder.nickname.setText(participants.get(position));
        }

        @Override
        public int getItemCount() {
            if (participants != null)
                return participants.size();
            else
                return 0;
        }

        public void addItem(String participantNickname){
            participants.add(participantNickname);
            notifyItemInserted(participants.size() - 1);
        }

        public boolean addItemsToEnd(List<String> newParticipants){
            Boolean flag = false;
            int prevItemCount = getItemCount();
            for(String message:newParticipants.subList(prevItemCount, newParticipants.size())){
                addItem(message);
                flag = true;
            }
            return flag;
        }

        private Context getContext() {
            return context;
        }
    }

    public static String toLocalHourMin(String timeStamp, int gmtDif){ //3
        String humenReadableTime = "";
        int i = 0;
        int mCount = 0;
        for(i = 1; timeStamp.charAt(i-1) != 'T'; i++);
        mCount = (timeStamp.charAt(i) - '0')*600 + (timeStamp.charAt(i+1) - '0')*60
                + (timeStamp.charAt(i+3) - '0')*10 + (timeStamp.charAt(i+4) - '0');
        mCount += 60*gmtDif;
        mCount = mCount % (60*24);
        humenReadableTime += String.valueOf(mCount / 600);
        mCount -= (mCount / 600)*600;
        humenReadableTime += String.valueOf(mCount / 60);
        mCount -= (mCount / 60)*60;
        humenReadableTime += ":";
        humenReadableTime += String.valueOf(mCount / 10);
        mCount -= (mCount / 10)*10;
        humenReadableTime += String.valueOf(mCount);
        return humenReadableTime;
    }


}
