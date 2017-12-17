package com.itugroup15.channelx;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.itugroup15.channelxAPI.APIClient;
import com.itugroup15.channelxAPI.APIController;
import com.itugroup15.channelxAPI.model.ChannelCreateRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.itugroup15.channelx.ChannelListActivity.PREFS_NAME;

public class CreateChannel extends AppCompatActivity implements TimePickerFragmentDialog.TimeInData {

    private boolean isStartDate = false;

    private String authToken;
    private SharedPreferences settings;
    private APIController apiController;

    private TextView startTimeTV;
    private TextView endTimeTV;
    private TextInputEditText channelName;

    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;
    private CheckBox sunday;

    private ArrayList<String> availableWeekdays = new ArrayList<>();

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, CreateChannel.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_channel);
        startTimeTV = (TextView)findViewById(R.id.startTimeTV);
        endTimeTV = (TextView)findViewById(R.id.endTimeTV);
        monday = (CheckBox)findViewById(R.id.monCheckBox);
        tuesday = (CheckBox)findViewById(R.id.tueCheckBox);
        wednesday = (CheckBox)findViewById(R.id.wedCheckBox);
        thursday = (CheckBox)findViewById(R.id.thuCheckBox);
        friday = (CheckBox)findViewById(R.id.friCheckBox);
        saturday = (CheckBox)findViewById(R.id.satCheckBox);
        sunday = (CheckBox)findViewById(R.id.sunCheckBox);

        channelName = (TextInputEditText)findViewById(R.id.channelNameInput);

        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        authToken = settings.getString("authToken", "");
        apiController = APIClient.getClient().create(APIController.class);

    }

    public void onStartTimeClicked(View v){
        DialogFragment newFragment = new TimePickerFragmentDialog();
        isStartDate = true;
        newFragment.show(getFragmentManager(),"TimePickerFragmentDialog");
    }

    public void onEndTimeClicked(View v){
        DialogFragment newFragment = new TimePickerFragmentDialog();
        isStartDate = false;
        newFragment.show(getFragmentManager(),"TimePickerFragmentDialog");
    }

    public void onCreateChannelClicked(View v){
        getAvailableWeekdays();
        if(channelName.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a channel name",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if(availableWeekdays.size() == 0){
            Toast.makeText(this, "Please select available day",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if(getIntTime(startTimeTV.getText().toString()) > getIntTime(endTimeTV.getText().toString())) {
            Toast.makeText(this, "Start time cant be greater than end time",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        final ChannelCreateRequest channel = new ChannelCreateRequest(channelName.getText().toString()
                , true, availableWeekdays
                , startTimeTV.getText().toString(), endTimeTV.getText().toString());
        Call<ChannelCreateRequest> call = apiController.createChannel(authToken, channel);
        call.enqueue(new Callback<ChannelCreateRequest>() {
            @Override
            public void onResponse(@NonNull Call<ChannelCreateRequest> call, @NonNull Response<ChannelCreateRequest> response) {
                if(response.body() != null && response.body().getCode() == 0){
                    finish();
                }
                else{
                    Toast.makeText(CreateChannel.this, "Channel name exists",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChannelCreateRequest> call, @NonNull Throwable t) {
                    Toast.makeText(CreateChannel.this, "Connection Error",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void TimeInString(String date) {
        if (isStartDate)
            startTimeTV.setText(date);
        else
            endTimeTV.setText(date);
    }

    private void getAvailableWeekdays(){
        availableWeekdays.clear();
        if(monday.isChecked())
            availableWeekdays.add("Mon");
        if(tuesday.isChecked())
            availableWeekdays.add("Tue");
        if(wednesday.isChecked())
            availableWeekdays.add("Wed");
        if(thursday.isChecked())
            availableWeekdays.add("Thu");
        if(friday.isChecked())
            availableWeekdays.add("Fri");
        if(saturday.isChecked())
            availableWeekdays.add("Sat");
        if(sunday.isChecked())
            availableWeekdays.add("Sun");
    }

    private int getIntTime(String time){
        int hour = (time.charAt(0) - '0') * 10 + (time.charAt(1) - '0');
        int min = (time.charAt(3) - '0') * 10 + (time.charAt(4) - '0');
        return hour*60 + min;
    }



}
