package com.example.ingatin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AddAlarm extends AppCompatActivity implements AlertMode_BottomSheetDialog.Listener{
    public static final String NEW_ALARM = "new_alarm";

    // Data yang akan di-passing menggunakan format bahasa Indonesia
    // atau sesuai CDM yang terlampir
    private String labelAlarm;
    private boolean alarmBerdering, alarmBergetar, alarmAdaLokasi;
    private Uri alarmRingtone;  private String namaRingtone;
    private ArrayList<Integer> alarmRutinitas = new ArrayList<>();

    // Harusnya di tabel lokasi, tapi disetor di sini dulu.
    private LatLng koordinat1, koordinat2;
    private String namaLokasi;

    private TimePicker chooseTime;
    public TextView repeaterName;
    public TextView alertMode;
    public TextView ringtoneName;
    public TextView alarmLabel;
    public Switch useLocation; public TextView locationName;

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
        repeaterName = findViewById(R.id.repeaterName); repeaterName.setText(R.string.label_initial);
        chooseTime = findViewById(R.id.chooseTime);
        alertMode = findViewById(R.id.aModeName);
        ringtoneName = findViewById(R.id.ringtoneName);
        alarmLabel = findViewById(R.id.labelName);
        useLocation = findViewById(R.id.locationIsSet); useLocation.setChecked(false);
        locationName = findViewById(R.id.locationName);

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

    // Back Button
    public void backToMain(View view) {
        Intent backIntent = new Intent();
        setResult(RESULT_CANCELED, backIntent);
        finish();
    }

    // Add button
    public void addAlarmToSystem(View view) {
         Intent addIntent = new Intent();
         if (Build.VERSION.SDK_INT >= 23) {
             Alarm newAlarm = new Alarm(
                     labelAlarm != null ? getLabelAlarm(): "",
                     alarmBerdering, alarmBergetar, alarmAdaLokasi,
                     alarmRingtone != null ? alarmRingtone.toString() : "content://media/internal/audio/media/5",
                     addAlarmRoutines(alarmRutinitas),
                     namaRingtone,
                     chooseTime.getHour(),
                     chooseTime.getMinute()
             );
             addIntent.putExtra(NEW_ALARM, newAlarm);
             setResult(RESULT_OK, addIntent);
             finish();
         }
         else {
             Alarm newAlarm = new Alarm(
                     labelAlarm != null ? getLabelAlarm(): "",
                     alarmBerdering, alarmBergetar, alarmAdaLokasi,
                     alarmRingtone != null ? alarmRingtone.toString() : "content://media/internal/audio/media/5",
                     addAlarmRoutines(alarmRutinitas),
                     namaRingtone,
                     chooseTime.getCurrentHour(),       // DEPRECATED IN API LEVEL 23, since
                     chooseTime.getCurrentMinute()      // the writer uses API 21, this is necessary
             );
             addIntent.putExtra(NEW_ALARM, newAlarm);
             setResult(RESULT_OK, addIntent);
             finish();
         }
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

    // onClick for the day toggle
    public void test(View view) {
        markedButtons = "";
        if(tSunday.isChecked()){
            markedButtons +="Sun, ";
        }
        if(tMonday.isChecked()){
            markedButtons +="Mon, ";
        }
        if(tTuesday.isChecked()){
            markedButtons +="Tue, ";
        }
        if(tWednesday.isChecked()){
            markedButtons +="Wed, ";
        }
        if(tThursday.isChecked()){
            markedButtons +="Thu, ";
        }
        if(tFriday.isChecked()){
            markedButtons +="Fri, ";
        }
        if(tSaturday.isChecked()){
            markedButtons +="Sat";
        }

        if (tSunday.isChecked() && tMonday.isChecked() && tTuesday.isChecked()
                && tWednesday.isChecked() && tThursday.isChecked() && tFriday.isChecked()
                && tSaturday.isChecked()) {
            markedButtons = "Everyday";
        }

        repeaterName.setText(markedButtons);
    }

    // BottomSheetDialog when changing alarm mode
    public void changeAlertMode(View view) {
        AlertMode_BottomSheetDialog alertMode = new AlertMode_BottomSheetDialog();
        alertMode.show(getSupportFragmentManager(), "alertMode_BottomSheet");
    }

    // New intent to change Ringtone
    public void changeRingtone(View view) {
        Intent ringtoneIntent = new Intent(this, ChangeRingtone.class);
        startActivityForResult(ringtoneIntent, 1);
    }

    // new intent to change Location
    public void changeLocation(View view) {
        Intent locationIntent = new Intent(this, ChangeLocation.class);
        startActivityForResult(locationIntent, 2);
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
        else if(requestCode == 2) {
            if(resultCode == Activity.RESULT_OK) {
                LatLng exactCoordinate = new LatLng(data.getDoubleExtra(ChangeLocation.LOCATION_LATITUDE, 0),
                        data.getDoubleExtra(ChangeLocation.LOCATION_LONGITUDE, 0));
                koordinat1 = new LatLng(exactCoordinate.latitude - 0.001, exactCoordinate.longitude - 0.001);
                koordinat2 = new LatLng(exactCoordinate.latitude + 0.001, exactCoordinate.longitude + 0.001);
                useLocation.setChecked(true);
                alarmAdaLokasi = useLocation.isChecked();
                namaLokasi = data.getStringExtra(ChangeLocation.LOCATION_ADDRESS);
                locationName.setText(namaLokasi);
            }
            if (resultCode == Activity.RESULT_CANCELED) {}
        }
    }

    // Other functions: set alarmRutinitas to track days
    public ArrayList<Integer> addAlarmRoutines(ArrayList<Integer> arrayList) {
        arrayList.add(0, tSunday.isChecked() ? 1 : 0);
        arrayList.add(1, tMonday.isChecked() ? 1 : 0);
        arrayList.add(2, tTuesday.isChecked() ? 1 : 0);
        arrayList.add(3, tWednesday.isChecked() ? 1 : 0);
        arrayList.add(4, tThursday.isChecked() ? 1 : 0);
        arrayList.add(5, tFriday.isChecked() ? 1 : 0);
        arrayList.add(6, tSaturday.isChecked() ? 1 : 0);

        return arrayList;
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
