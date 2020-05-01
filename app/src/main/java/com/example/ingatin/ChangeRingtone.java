package com.example.ingatin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ChangeRingtone extends AppCompatActivity {
    private ArrayList<String> rNames = new ArrayList<>();
    private ArrayList<String> rURI = new ArrayList<>();
    public ChangeRingtone_RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_ringtone);

        getNotifications(rNames, rURI);
        initRecyclerView();
    }

    public void getNotifications(ArrayList<String> a, ArrayList<String> b) {
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_ALARM);
        Cursor cursor = manager.getCursor();

        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/" + cursor.getString(RingtoneManager.ID_COLUMN_INDEX);

            a.add(notificationTitle);
            b.add(notificationUri);
        }
    }

    private void initRecyclerView() {
        RecyclerView rv = findViewById(R.id.recycler_ringtone);
        adapter = new ChangeRingtone_RecyclerViewAdapter(
                this, rNames, rURI);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    public void alarmRingtone(View view) {
        if (adapter.getMp() != null) adapter.getMp().release();
        Intent returnIntent = new Intent();
        if (adapter.getrName() == null || adapter.getrURI() == null) {
            setResult(Activity.RESULT_CANCELED, returnIntent);
        }
        else {
            returnIntent.putExtra("RINGTONE_NAME", adapter.getrName());
            returnIntent.putExtra("RINGTONE_URI", adapter.getrURI());
            setResult(Activity.RESULT_OK, returnIntent);
        }
        finish();
    }
}
