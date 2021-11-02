package com.example.shleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ArgbEvaluator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String darkColor = "#000000";
    private String lightColor = "#FFCAB0";
    private boolean isShleeping;
    private ConstraintLayout shleepApp;
    private Button shleepBtn;
    private TextView shleepTxt;
    private ValueAnimator colorAnimation;

    private void shleepNow() {
        if (isShleeping == false) {
            isShleeping = true;
            shleepTxt.setText("What's up?");
            setAnimation(darkColor, lightColor, 8000);
            colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
            colorAnimation.setInterpolator(new AccelerateInterpolator());
            colorAnimation.setRepeatCount(Animation.INFINITE);
            colorAnimation.start();
        } else {
            isShleeping = false;
            shleepTxt.setText("Shleep.");
            colorAnimation.cancel();
            setAnimation(lightColor, darkColor, 1000);
            colorAnimation.start();
        }
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
        isShleeping = false;

        shleepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shleepNow();
            }
        });
    }
}