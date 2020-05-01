package com.example.ingatin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Objects;

public class AddAlarm extends AppCompatActivity implements AlertMode_BottomSheetDialog.Listener{
    // Data yang akan di-passing menggunakan format bahasa Indonesia
    // atau sesuai CDM yang terlampir
    private String labelAlarm;
    private boolean alarmBerdering, alarmBergetar;
    private Uri alarmRingtone;  private String namaRingtone;

    private TimePicker chooseTime;
    public TextView alertMode;
    public TextView ringtoneName;
    public TextView alarmLabel;

    ToggleButton tSunday;       ToggleButton tMonday;
    ToggleButton tTuesday;      ToggleButton tWednesday;
    ToggleButton tThursday;     ToggleButton tFriday;
    ToggleButton tSaturday;     String markedButtons = "";

    // AlertMode Implementation
    @Override
    public void onClick(String s) {
        alertMode.setText(s);
        if (s.equals("Ring")) {
            setAlarmBerdering(true);
            setAlarmBergetar(false);
        }
        else if (s.equals("Vibrate")) {
            setAlarmBerdering(false);
            setAlarmBergetar(true);
        }
        else if (s.equals("Ring & Vibrate")) {
            setAlarmBerdering(true);
            setAlarmBergetar(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);
        chooseTime = findViewById(R.id.chooseTime);
        alertMode = findViewById(R.id.aModeName);
        ringtoneName = findViewById(R.id.ringtoneName);
        alarmLabel = findViewById(R.id.labelName);

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
        Dialog popupAddLabel = new Dialog(this);
        popupAddLabel.setContentView(R.layout.popup_add_label);
        popupAddLabel.setCancelable(false);
        Objects.requireNonNull(popupAddLabel.getWindow()).setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT));
        popupAddLabel.show();

        Button pBack, pOk;
        EditText pLabel;
        pBack = popupAddLabel.findViewById(R.id.back);
        pOk = popupAddLabel.findViewById(R.id.OK);
        pLabel = popupAddLabel.findViewById(R.id.label_name);

        pBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupAddLabel.dismiss();
            }
        });

        pOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLabelAlarm(pLabel.getText().toString());
                alarmLabel.setText(getLabelAlarm());
                popupAddLabel.dismiss();
            }
        });
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
        startActivityForResult(ringtoneIntent, 1);
    }

    public void changeLocation(View view) {
//        Intent locationIntent = new Intent(this, ChangeLocation.class);
        Intent locationIntent = new Intent(this, ChangeLocation.class);
        startActivity(locationIntent);
    }


    // startActivityForResult indent handler here
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                setAlarmRingtone(data.getParcelableExtra("RINGTONE_URI"));
                setNamaRingtone(data.getStringExtra("RINGTONE_NAME"));
                ringtoneName.setText(getNamaRingtone());
            }
            if (resultCode == Activity.RESULT_CANCELED) {}
        }
    }

    // Setter and Getter
    public String getLabelAlarm() {
        return labelAlarm;
    }

    public void setLabelAlarm(String labelAlarm) {
        this.labelAlarm = labelAlarm;
    }

    public boolean isAlarmBerdering() {
        return alarmBerdering;
    }

    public void setAlarmBerdering(boolean alarmBerdering) {
        this.alarmBerdering = alarmBerdering;
    }

    public boolean isAlarmBergetar() {
        return alarmBergetar;
    }

    public void setAlarmBergetar(boolean alarmBergetar) {
        this.alarmBergetar = alarmBergetar;
    }

    public Uri getAlarmRingtone() {
        return alarmRingtone;
    }

    public void setAlarmRingtone(Uri alarmRingtone) {
        this.alarmRingtone = alarmRingtone;
    }

    public String getNamaRingtone() {
        return namaRingtone;
    }

    public void setNamaRingtone(String namaRingtone) {
        this.namaRingtone = namaRingtone;
    }

}
