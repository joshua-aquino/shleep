package com.example.shleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
            colorAnimation.start();
        } else {
            isShleeping = false;
            shleepTxt.setText("Shleep.");
            shleepApp.setBackgroundColor(Color.parseColor(darkColor));
        }
    }

    private void changeColors() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shleepApp = (ConstraintLayout) findViewById(R.id.shleepApp);
        shleepBtn = (Button) findViewById(R.id.shleepBtn);
        shleepTxt = (TextView) findViewById(R.id.shleepTxt);
        isShleeping = false;
        colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                Color.parseColor(darkColor), Color.parseColor(lightColor));
        colorAnimation.setDuration(10000);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                shleepApp.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });

        shleepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shleepNow();
            }
        });
    }
}