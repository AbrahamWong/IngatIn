package com.example.ingatin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

public class AddAlarm extends AppCompatActivity implements AlertMode_BottomSheetDialog.Listener{
    private TimePicker chooseTime;
    public TextView alertMode;

    public ToggleButton tSunday;
    ToggleButton tMonday;
    ToggleButton tTuesday;
    ToggleButton tWednesday;
    ToggleButton tThursday;
    ToggleButton tFriday;
    ToggleButton tSaturday;
    String markedButtons = "";

    // AlertMode Implementation
    @Override
    public void onClick(String s) {
        alertMode.setText(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);
        chooseTime = findViewById(R.id.chooseTime);
        alertMode = findViewById(R.id.aModeName);

        // sigh, here we go
        tSunday     = findViewById(R.id.tSunday);
        tMonday     = findViewById(R.id.tMonday);
        tTuesday    = findViewById(R.id.tTuesday);
        tWednesday  = findViewById(R.id.tWednesday);
        tThursday   = findViewById(R.id.tThursday);
        tFriday     = findViewById(R.id.tFriday);
        tSaturday   = findViewById(R.id.tSaturday);

        chooseTime.setIs24HourView(true);
        chooseTime.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        chooseTime.setCurrentMinute(Calendar.getInstance().get(Calendar.MINUTE));
    }

    public void backToMain(View view) {
        Intent backIntent = new Intent();
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

    public void addAlarmToSystem(View view) {
        // Intent addIntent = new Intent();
        // setResult(RESULT_OK, addIntent);
        // finish();
    }

    public void changeLabel(View view) {
    }

    public void smhTheToogleButtons() {
        if(tSunday.isChecked()){
            markedButtons +="Sun,";
        }
        if(tMonday.isChecked()){
            markedButtons +="Mon,";
        }
        if(tTuesday.isChecked()){
            markedButtons +="Tue,";
        }
        if(tWednesday.isChecked()){
            markedButtons +="Wed,";
        }
        if(tThursday.isChecked()){
            markedButtons +="Thu,";
        }
        if(tFriday.isChecked()){
            markedButtons +="Fri,";
        }
        if(tSaturday.isChecked()){
            markedButtons +="Sat";
        }
    }

    public void changeAlertMode(View view) {
        AlertMode_BottomSheetDialog alertMode = new AlertMode_BottomSheetDialog();
        alertMode.show(getSupportFragmentManager(), "alertMode_BottomSheet");
    }

    public void changeRingtone(View view) {
        Intent ringtoneIntent = new Intent(this, ChangeRingtone.class);
        startActivity(ringtoneIntent);
    }
}
