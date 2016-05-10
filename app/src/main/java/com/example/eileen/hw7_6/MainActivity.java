package com.example.eileen.hw7_6;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity{
    TextView txtTime;
    Button btnStopAlarm, btnStopTimer;
    int timerCount = 0;
    long timerDelay = 1*1000, timerPeriod = 1*1000;
    long oneShotAlarmTime=10*1000, alarmPeriod = 20*1000;
    Timer timer = new Timer(false);
    final Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTime = (TextView) this.findViewById(R.id.txtTime);

        TimerTask cycleTask = createTimerTask();
        timer.scheduleAtFixedRate(cycleTask, timerDelay, timerPeriod);
        btnStopTimer = (Button) this.findViewById(R.id.btnStopTimer);
        btnStopTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                timer.cancel();
                txtTime.setText("Timer Stopped");
                txtTime.setTextColor(android.graphics.Color.RED);
            }
        });

        setAlarm();
        btnStopAlarm = (Button) this.findViewById(R.id.btnStopAlarm);
        btnStopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                cancelAlarm();
                txtTime.setText("Alarm Cleared");
                txtTime.setTextColor(android.graphics.Color.RED);
            }
        });
    }

    TimerTask createTimerTask(){
        TimerTask cycleTask = new TimerTask() {
            @Override
            public void run() {
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        timerCount++;
                        if(timerCount==1){
                            txtTime.setText("Start another cycle of counting...");
                            txtTime.setTextColor(android.graphics.Color.BLACK);
                        }
                        else if(timerCount==5){
                            txtTime.setText("Another" + timerCount + " second passed...");
                            txtTime.setTextColor(android.graphics.Color.BLUE);
                            timerCount=0;
                        }
                    }
                });
            }
        };
        return cycleTask;
    }

    void setAlarm() {
        long elapsedTime = SystemClock.elapsedRealtime();

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("time1", oneShotAlarmTime);
        intent.putExtra("time2", alarmPeriod);
        PendingIntent pendIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, elapsedTime+oneShotAlarmTime, alarmPeriod, pendIntent);
    }

    void cancelAlarm() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(pendIntent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }





}
