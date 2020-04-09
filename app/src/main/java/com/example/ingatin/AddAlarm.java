package com.example.ingatin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddAlarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm);
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
}
