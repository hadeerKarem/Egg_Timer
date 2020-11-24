package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar timerSeekBar;
    Button goButton;
    CountDownTimer myTimer;
    Boolean timerIsRunning=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        goButton = (Button) findViewById(R.id.goButton);

        //max timer value is 10 mins = 600 sec
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    public void buttonClicked(View view) {
        Log.i("Info", "Button Clicked!");

        if (timerIsRunning) {
            resetTimer();
        }
        else {
            timerIsRunning = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("STOP!");

            myTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {
                    timerIsRunning = true;
                    updateTimer((int) l / 1000);
                    Log.i("Info", "Tick Tock!");
                }

                @Override
                public void onFinish() {
                    timerIsRunning = false;
                    Log.i("Info", "Timer is Done!");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int secondsLeft) {
        Log.i("Info", "updateTimer called!");
        int minutes = secondsLeft/60;
        int seconds = secondsLeft-(minutes*60);
        String secondsString = Integer.toString(seconds);
        String minutesString = Integer.toString(minutes);

        if (seconds<10) {
            secondsString = "0" + secondsString;
        }

        if (minutes<10){
            minutesString = "0" + minutesString;
        }

        timerTextView.setText(minutesString + ":" + secondsString);
    }

    public void resetTimer(){
        timerTextView.setText("00:30");
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(30);
        goButton.setText("GO!");
        myTimer.cancel();
        timerIsRunning = false;
    }
}