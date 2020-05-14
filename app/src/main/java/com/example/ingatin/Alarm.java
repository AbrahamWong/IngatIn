package com.example.ingatin;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Alarm implements Serializable {
    private String labelAlarm;
    private Calendar waktuAlarm;
    private boolean alarmBerdering, alarmBergetar, alarmAdaLokasi = false;
    private String alarmRingtone;               // REMEMBER THAT THIS IS SUPPOSED TO BE URI. IF USING THIS, PLEASE CONVERT TO URI FIRST
    private ArrayList<Integer> alarmRutinitas;

    private String namaRingtone;
    private int jamAlarm, menitAlarm;

    /*
     * Create a new alarm class, to save the data given by user.
     * @param:
     *  - String                labelAlarm
     *  - boolean               alarmBerdering
     *  - boolean               alarmBergetar
     *  - boolean               alarmAdaLokasi
     *  - Uri                   alarmRingtone
     *  - ArrayList<Integer>    alarmRutinitas
     *  - String                namaRingtone
     *  - int                   jamAlarm
     *  - int                   menitAlarm
    */
    public Alarm(String labelAlarm, boolean alarmBerdering, boolean alarmBergetar,
                 boolean alarmAdaLokasi, String alarmRingtone, ArrayList<Integer> alarmRutinitas,
                 String namaRingtone, int jamAlarm, int menitAlarm) {
        this.labelAlarm = labelAlarm;
        this.alarmBerdering = alarmBerdering;
        this.alarmBergetar = alarmBergetar;
        this.alarmAdaLokasi = alarmAdaLokasi;
        this.alarmRingtone = alarmRingtone;
        this.alarmRutinitas = alarmRutinitas;
        this.namaRingtone = namaRingtone;
        this.jamAlarm = jamAlarm;
        this.menitAlarm = menitAlarm;
    }

    public String convertRoutinesToString () {
        String days = ""; boolean everydayFlag = true;
        for (int a : alarmRutinitas) {
            if (everydayFlag){
                if (a == 1) {
                }
                else {
                    everydayFlag = false;
                }
            }
            else break;
        }

        if (everydayFlag) return "Everyday";
        else {
            if (alarmRutinitas.get(0) == 1) {
                days += "Sun, ";
            }
            if (alarmRutinitas.get(1) == 1) {
                days += "Mon, ";
            }
            if (alarmRutinitas.get(2) == 1) {
                days += "Tue, ";
            }
            if (alarmRutinitas.get(3) == 1) {
                days += "Wed, ";
            }
            if (alarmRutinitas.get(4) == 1) {
                days += "Thu, ";
            }
            if (alarmRutinitas.get(5) == 1) {
                days += "Fri, ";
            }
            if (alarmRutinitas.get(6) == 1) {
                days += "Sat";
            }
        }

        return days;
    }

    // Setters and getters

    public String getLabelAlarm() {
        return labelAlarm;
    }

    public void setLabelAlarm(String labelAlarm) {
        this.labelAlarm = labelAlarm;
    }

    public boolean isAlarmAdaLokasi() {
        return alarmAdaLokasi;
    }

    public void setAlarmAdaLokasi(boolean alarmAdaLokasi) {
        this.alarmAdaLokasi = alarmAdaLokasi;
    }

    public ArrayList<Integer> getAlarmRutinitas() {
        return alarmRutinitas;
    }

    public void setAlarmRutinitas(ArrayList<Integer> alarmRutinitas) {
        this.alarmRutinitas = alarmRutinitas;
    }

    public int getJamAlarm() {
        return jamAlarm;
    }

    public void setJamAlarm(int jamAlarm) {
        this.jamAlarm = jamAlarm;
    }

    public int getMenitAlarm() {
        return menitAlarm;
    }

    public void setMenitAlarm(int menitAlarm) {
        this.menitAlarm = menitAlarm;
    }
}
