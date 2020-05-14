package com.example.ingatin;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Alarm_RecyclerViewAdapter extends RecyclerView.Adapter<Alarm_RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "Alarm_RecyclerViewAdapt";

    private ArrayList<Alarm> alarmList;
    private Context context;

    public Alarm_RecyclerViewAdapter(Context context, ArrayList<Alarm> alarmList) {
        this.alarmList = alarmList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_layout,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.alarmLabel.setText(alarmList.get(position).getLabelAlarm());
        holder.alarmHour.setText(String.valueOf(alarmList.get(position).getJamAlarm()));
        holder.alarmMinute.setText(String.valueOf(alarmList.get(position).getMenitAlarm()));
        holder.alarmRoutines.setText(alarmList.get(position).convertRoutinesToString());

        if (!alarmList.get(position).isAlarmAdaLokasi()){
            holder.isUsingLocation.setVisibility(View.INVISIBLE);
        } else {
            holder.isUsingLocation.setVisibility(View.VISIBLE);
        }

        holder.alarmLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Fungsi mengubah alarm belum tersedia", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView alarmHour, alarmMinute, alarmRoutines, alarmLabel;
        ImageView isUsingLocation;
        Switch alarmState;
        RelativeLayout alarmLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            alarmHour       = itemView.findViewById(R.id.alarm_hour);
            alarmMinute     = itemView.findViewById(R.id.alarm_minute);
            alarmRoutines   = itemView.findViewById(R.id.alarm_routines);
            alarmLabel      = itemView.findViewById(R.id.alarm_label);
            isUsingLocation = itemView.findViewById(R.id.is_using_location);
            alarmState      = itemView.findViewById(R.id.alarm_state);

            alarmLayout     = itemView.findViewById(R.id.alarm_layout);
        }
    }
}
