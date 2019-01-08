package com.school.health;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.os.CountDownTimer;
import android.widget.TextView;

public class Exercise_view extends AppCompatActivity {

    private ImageView imgView;
    private TextView countDwn;
    private Button timerButton;
    private Button resetButton;
    private Boolean isPaused = false;
    private CountDownTimer countDwnTimer;
    private  int Clicked = 0;
    long millisInFuture = 60000*60;
    long coutDownInterval = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_view);
        countDwn = (TextView) findViewById(R.id.countdownTimer);
        Intent intent = getIntent();
        String value = intent.getStringExtra("key");
        resetButton = findViewById(R.id.resetButton);
        timerButton = findViewById(R.id.timerButton);
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Clicked % 2 == 1) isPaused = true;
                else isPaused = false;
                Clicked += 1;
                timerButton.setText("Pause");
                if (isPaused == false)
                    countDwnTimer = new CountDownTimer(millisInFuture, coutDownInterval) {
                        public void onTick(long millisUntilFinished) {
                            countDwn.setText("" + millisUntilFinished / 60000 + ":" + (millisUntilFinished / 1000) % 60);
                             millisInFuture = millisUntilFinished;
                        }

                        public void onFinish() {
                            countDwn.setText("done!");
                        }
                    }.start();
                else {
                    timerButton.setText("Resume");
                    countDwnTimer.cancel();
                }
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                millisInFuture = 60000*60;
            }
        });
        switch (value)
        {
            case "fb_btn1":
                imgView = (ImageView) findViewById(R.id.imageView);
                imgView.setImageResource(R.drawable.fullodlvl1);
                break;
            case"fb_btn2":
                imgView = (ImageView) findViewById(R.id.imageView);
                imgView.setImageResource(R.drawable.fullbodlvl2);
                break;
            case"fb_btn3":
                imgView = (ImageView) findViewById(R.id.imageView);
                imgView.setImageResource(R.drawable.fullbodlvl3);
                break;
            case"lb_btn1":
                imgView = (ImageView) findViewById(R.id.imageView);
                imgView.setImageResource(R.drawable.lebodlvl1);
                break;
            case"lb_btn2":
                imgView = (ImageView) findViewById(R.id.imageView);
                imgView.setImageResource(R.drawable.legbodlvl2);
                break;
            case"lb_btn3":
                imgView = (ImageView) findViewById(R.id.imageView);
                imgView.setImageResource(R.drawable.legbodlvl3);
                break;
            case"ub_btn1":
                imgView = (ImageView) findViewById(R.id.imageView);
                imgView.setImageResource(R.drawable.upperbodlvl1);
                break;
            case"ub_btn2":
                imgView = (ImageView) findViewById(R.id.imageView);
                imgView.setImageResource(R.drawable.upperbodlvl2);
                break;
            case"ub_btn3":
                imgView = (ImageView) findViewById(R.id.imageView);
                imgView.setImageResource(R.drawable.upperbodlvl3);
                break;
        }
    }


}
