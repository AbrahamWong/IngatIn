package com.example.ingatin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;
    public TextClock hour_minute;
    public TextView today_date;
    public String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(
            new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hour_minute = findViewById(R.id.hour_minute);
        today_date = findViewById(R.id.todayDate);

        Typeface typ = ResourcesCompat.getFont(this, R.font.lato);
        hour_minute.setTypeface(typ);
        today_date.setText(date_n);

        // Show all alarm available
        // Code here
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
        startActivity(addAlarmIntent);

        // Expecting alarm data to be created
    }
}
