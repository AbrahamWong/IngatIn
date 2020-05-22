package com.example.ingatin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private ArrayList<Alarm> alarmArrayList = new ArrayList<>();

    private int mCount = 0;
    public TextClock hour_minute;
    public TextView today_date; public TextView test;
    public String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(
            new Date());
    public SharedPreferences alarmList;

    @Override
    protected void onStart() {
        super.onStart();
        getAllAlarm();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hour_minute = findViewById(R.id.hour_minute);
        today_date = findViewById(R.id.todayDate);

        Typeface typ = ResourcesCompat.getFont(this, R.font.lato);
        hour_minute.setTypeface(typ);
        today_date.setText(date_n);

        SharedPreferences alarmList = this.getApplicationContext().getSharedPreferences(TAG, MODE_PRIVATE);

        // Show all alarm available
        test = findViewById(R.id.test);
//        test.setText(alarmList.getAll().toString());
        getAllAlarm();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showAllAvailableAlarm();
    }

//    public void showToast(View view) {
//        // Create and show toast
//        /* Toast.makeText(  context - the current Activity
//         *                  message - fill with message to show in the toast
//         *                  length  - duration of the toast, either Toast.LENGTH_SHORT <2s>
//         *                            or Toast.LENGTH_LONG <3.5s>
//         *                )
//        */
//        Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
//        toast.show();
//    }

    public void addAlarm(View view) {
        Intent addAlarmIntent = new Intent(this, AddAlarm.class);
        startActivityForResult(addAlarmIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                // Get alarm data
                assert data != null;
                Alarm newAlarm = (Alarm) data.getSerializableExtra(AddAlarm.NEW_ALARM);

                // save to SharedPreferences.
                saveToSharedPreferences(newAlarm);
                getAllAlarm();
                showAllAvailableAlarm();
            }
            else if (resultCode == Activity.RESULT_CANCELED) {}
        }
    }

    private void saveToSharedPreferences(Alarm newAlarm) {
        SharedPreferences alarmList = this.getApplicationContext().getSharedPreferences(TAG, MODE_PRIVATE);
        SharedPreferences.Editor alarmListEditor = alarmList.edit();
        Gson gson = new Gson();
        String json = gson.toJson(newAlarm);
        int size = alarmList.getAll().size();
        String keyName = "alarm_" + (size + 1);
        alarmListEditor.putString(keyName, json);
        alarmListEditor.apply();

        // For the sake of debugging, uncomment here
//        test.setText(alarmList.getAll().toString());
    }

    private void getAllAlarm() {
        SharedPreferences alarmList = this.getApplicationContext().getSharedPreferences(TAG, MODE_PRIVATE);
        alarmArrayList.clear();
        for (int i = 0; i < alarmList.getAll().size(); i++) {
            Gson gson = new Gson();
            String json = alarmList.getString("alarm_" + (i + 1), "");
            Alarm alarm = gson.fromJson(json, Alarm.class);
            alarmArrayList.add(alarm);
        }

//        test.setText(alarmArrayList.get(0).getLabelAlarm());
    }

    private void showAllAvailableAlarm() {
        if (!alarmArrayList.isEmpty())
        {
            Alarm_RecyclerViewAdapter adapter = new Alarm_RecyclerViewAdapter(this, alarmArrayList);
            RecyclerView recyclerView = findViewById(R.id.recycler_alarm);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
