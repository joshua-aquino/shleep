package com.example.shleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean isShleeping = false;
    private String darkColor = "#000000";
    private String lightColor = "#FFCAB0";
    private double shleepMinutes = 0.5;
    private ConstraintLayout shleepApp;
    private Button shleepBtn;
    private TextView shleepTxt;
    private ValueAnimator colorAnimation;
    private CountDownTimer shleepTimer;

    private void shleepNow() {
        if (isShleeping == false) {
            isShleeping = true;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            shleepTxt.setText("What's up?");

            setAnimation(darkColor, lightColor, 8000);
            colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
            colorAnimation.setInterpolator(new AccelerateInterpolator());
            colorAnimation.setRepeatCount(Animation.INFINITE);
            shleepTimer = new CountDownTimer((long) (shleepMinutes * 60000), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }
                @Override
                public void onFinish() {
                    shleepNow();
                }
            }.start();
        } else {
            isShleeping = false;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            shleepTxt.setText("Shleep.");

            colorAnimation.cancel();
            setAnimation(lightColor, darkColor, 1000);
            shleepTimer.cancel();
        }
        colorAnimation.start();
    }

    private void setAnimation(String startColor, String endColor, long speed) {
        colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                Color.parseColor(startColor), Color.parseColor(endColor));
        colorAnimation.setDuration(speed);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                shleepApp.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shleepApp = (ConstraintLayout) findViewById(R.id.shleepApp);
        shleepBtn = (Button) findViewById(R.id.shleepBtn);
        shleepTxt = (TextView) findViewById(R.id.shleepTxt);

        shleepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shleepNow();
            }
        });
    }
}