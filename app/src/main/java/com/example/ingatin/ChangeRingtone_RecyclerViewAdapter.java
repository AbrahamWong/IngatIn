package com.example.ingatin;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

// Credits to: CodingWithMitch -> https://www.youtube.com/watch?v=Vyqz_-sJGFk
public class ChangeRingtone_RecyclerViewAdapter
        extends RecyclerView.Adapter<ChangeRingtone_RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "ChangeRingtone_Recycler";

    private ArrayList<String> arrayRingtoneName = new ArrayList<>();
    private ArrayList<String> arrayRingtoneURI = new ArrayList<>();
    private Context context;
    private MediaPlayer mp;
    private Uri rURI; private String rName;

    public ChangeRingtone_RecyclerViewAdapter(Context context, ArrayList<String> arrayRingtoneName,
                                              ArrayList<String> arrayRingtoneURI)
    {
        this.arrayRingtoneName = arrayRingtoneName;
        this.context = context;
        this.arrayRingtoneURI = arrayRingtoneURI;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ringtone_layout, parent,
                false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.RingtoneName.setText(arrayRingtoneName.get(position));
        holder.RingtoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Play the ringtone
                if (mp != null) {
                    mp.release();
                    mp = MediaPlayer.create(context, Uri.parse(arrayRingtoneURI.get(position)));
                    mp.start();
                }
                else{
                    mp = MediaPlayer.create(context, Uri.parse(arrayRingtoneURI.get(position)));
                    mp.start();
                }

                Log.d(TAG, "Playing: " + arrayRingtoneName.get(position));
                Toast.makeText(context, "Pressed " + arrayRingtoneName.get(position), Toast.LENGTH_SHORT).show();
                setrURI(Uri.parse(arrayRingtoneURI.get(position)));
                setrName(arrayRingtoneName.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayRingtoneName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView RingtoneName;
        View aLine;
        RelativeLayout RingtoneLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RingtoneName = itemView.findViewById(R.id.ringtone_text);
            aLine = itemView.findViewById(R.id.rLine);
            RingtoneLayout = itemView.findViewById(R.id.ringtone_list);
        }
    };

    public MediaPlayer getMp() {
        return mp;
    }

    public void setMp(MediaPlayer mp) {
        this.mp = mp;
    }

    public Uri getrURI() {
        return rURI;
    }

    public void setrURI(Uri rURI) {
        this.rURI = rURI;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }
}
