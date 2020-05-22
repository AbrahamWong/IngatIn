package com.example.ingatin;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.ingatin.MainActivity.TAG;

public class Alarm implements Serializable {
    private String labelAlarm;
    private Calendar waktuAlarm;
    private boolean alarmBerdering, alarmBergetar, alarmAdaLokasi = false;
    private String alarmRingtone;               // REMEMBER THAT THIS IS SUPPOSED TO BE URI. IF USING THIS, PLEASE CONVERT TO URI FIRST
    private ArrayList<Integer> alarmRutinitas;

    private String namaRingtone;
    private int jamAlarm, menitAlarm;
    public boolean everydayFlag, onceFlag;

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

        checkRoutines();
    }

    public String convertRoutinesToString () {
        String days = "";
        everydayFlag = true;
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

    public void checkRoutines () {
        everydayFlag = onceFlag = true;
        for (int a : alarmRutinitas) {
            if (a == 1) {
                onceFlag = false;
            }
            else {
                everydayFlag = false;
            }
        }
    }

    // Setters and getters
    public Calendar getWaktuAlarm() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Calendar.Builder build = new Calendar.Builder().setTimeOfDay(jamAlarm, menitAlarm, 0);
            Calendar c = Calendar.getInstance(); int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            // Loop twice, once for the day before Sunday, once for the day after Sunday and before the day.
            if (!onceFlag) {
                for (int i = dayOfWeek; i < 7; i++) {
                    if (alarmRutinitas.get(i) == 1) {
                        build.setWeekDate(c.get(Calendar.YEAR), c.get(Calendar.WEEK_OF_YEAR), i);
                    }
                }
                for (int i = 0; i < dayOfWeek; i++) {
                    if (alarmRutinitas.get(i) == 1) {
                        build.setWeekDate(c.get(Calendar.YEAR), c.get(Calendar.WEEK_OF_YEAR), i);
                    }
                }
            }
            else {
                build.setWeekDate(c.get(Calendar.YEAR), c.get(Calendar.WEEK_OF_YEAR), c.get(Calendar.DAY_OF_WEEK));
            }
            waktuAlarm = build.build();
        }
        else {
            Calendar alarmTimeWillBe = new Calendar() {
                @Override
                protected void computeTime() {

                }

                @Override
                protected void computeFields() {

                }

                @Override
                public void add(int field, int amount) {

                }

                @Override
                public void roll(int field, boolean up) {

                }

                @Override
                public int getMinimum(int field) {
                    return 0;
                }

                @Override
                public int getMaximum(int field) {
                    return 0;
                }

                @Override
                public int getGreatestMinimum(int field) {
                    return 0;
                }

                @Override
                public int getLeastMaximum(int field) {
                    return 0;
                }
            };
            Calendar c = Calendar.getInstance(); int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            int date = 0;
            if (!onceFlag) {
                for (int i = dayOfWeek; i < 7; i++) {
                    if (alarmRutinitas.get(i) == 1) {
                        date = c.get(Calendar.DATE) + i;
                        if (date > 31 && (c.get(Calendar.MONTH) == Calendar.JANUARY ||
                                c.get(Calendar.MONTH) == Calendar.MARCH ||
                                c.get(Calendar.MONTH) == Calendar.MAY ||
                                c.get(Calendar.MONTH) == Calendar.JULY ||
                                c.get(Calendar.MONTH) == Calendar.AUGUST ||
                                c.get(Calendar.MONTH) == Calendar.OCTOBER ||
                                c.get(Calendar.MONTH) == Calendar.DECEMBER)) {
                            date -= 31;
                        }
                        else if (date > 30 && (c.get(Calendar.MONTH) == Calendar.APRIL ||
                                c.get(Calendar.MONTH) == Calendar.JUNE ||
                                c.get(Calendar.MONTH) == Calendar.SEPTEMBER ||
                                c.get(Calendar.MONTH) == Calendar.NOVEMBER)) {
                            date -= 30;
                        }
                        else if (date > 28 && (c.get(Calendar.MONTH) == Calendar.FEBRUARY && c.get(Calendar.YEAR) % 4 != 0)) {
                            date -= 28;
                        }
                    }
                }
                for (int i = 0; i < dayOfWeek; i++) {
                    if (alarmRutinitas.get(i) == 1) {
                        date = c.get(Calendar.DATE) + i;
                        if (date > 31 && (c.get(Calendar.MONTH) == Calendar.JANUARY ||
                                c.get(Calendar.MONTH) == Calendar.MARCH ||
                                c.get(Calendar.MONTH) == Calendar.MAY ||
                                c.get(Calendar.MONTH) == Calendar.JULY ||
                                c.get(Calendar.MONTH) == Calendar.AUGUST ||
                                c.get(Calendar.MONTH) == Calendar.OCTOBER ||
                                c.get(Calendar.MONTH) == Calendar.DECEMBER)) {
                            date -= 31;
                        }
                        else if (date > 30 && (c.get(Calendar.MONTH) == Calendar.APRIL ||
                                c.get(Calendar.MONTH) == Calendar.JUNE ||
                                c.get(Calendar.MONTH) == Calendar.SEPTEMBER ||
                                c.get(Calendar.MONTH) == Calendar.NOVEMBER)) {
                            date -= 30;
                        }
                        else if (date > 28 && (c.get(Calendar.MONTH) == Calendar.FEBRUARY && c.get(Calendar.YEAR) % 4 != 0)) {
                            date -= 28;
                        }
                    }
                }
            }
            else {
                date = c.get(Calendar.DATE);
            }

            alarmTimeWillBe.set(c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    date,
                    getJamAlarm(), menitAlarm, 0);
            waktuAlarm = alarmTimeWillBe;
            Log.d(TAG, "getWaktuAlarm: " + getJamAlarm());
        }
        return waktuAlarm;
    }

    public void setWaktuAlarm(Calendar waktuAlarm) {
        this.waktuAlarm = waktuAlarm;
    }

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

    public String getMenitAlarm() {
        return menitAlarm < 10 ? "0" + menitAlarm : String.valueOf(menitAlarm);
    }

    public void setMenitAlarm(int menitAlarm) {
        this.menitAlarm = menitAlarm;
    }
}
