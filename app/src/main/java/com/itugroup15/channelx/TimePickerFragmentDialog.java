package com.itugroup15.channelx;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import android.widget.TextView;
import java.util.Calendar;
import android.widget.TimePicker;

public class TimePickerFragmentDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    TimeInData timeInData;
    String time = null;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        timeInData = (TimeInData)getActivity();
        return new TimePickerDialog(getActivity(), this, hour, minute,
                true);
    }

    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        TextView tv = (TextView) getActivity().findViewById(R.id.startTimeTV);
        time = properTime(hourOfDay, minute);
        timeInData.TimeInString(time);
    }

    public static String properTime(int hour, int min){
        String res = "";
        if(hour < 10)
            res = "0" + String.valueOf(hour);
        else
            res = String.valueOf(hour);
        res += ":";
        if(min < 10)
            res += "0" + String.valueOf(min);
        else
            res += String.valueOf(min);
        return res;
    }

    public interface TimeInData {
        public void TimeInString(String date);
    }
}
