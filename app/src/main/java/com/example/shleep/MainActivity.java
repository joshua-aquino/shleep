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
    private double shleepMinutes = 0.1;
    private ConstraintLayout shleepApp;
    private Button shleepBtn;
    private TextView shleepTxt;
    private ValueAnimator colorAnimation;
    private CountDownTimer shleepTimer;

    private void showSystemUI(boolean showIt) {
        View decorView = getWindow().getDecorView();
        if (showIt) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        } else {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
    }

    private void changeScreen(boolean changeIt) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        if (changeIt) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            layoutParams.screenBrightness = 1;
            getWindow().setAttributes(layoutParams);
        } else {
            layoutParams.screenBrightness = -1;
            getWindow().setAttributes(layoutParams);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private void setInfiniteAnimation(boolean isInfinite) {
        if (isInfinite) {
            colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                    Color.parseColor(darkColor), Color.parseColor(lightColor));
            colorAnimation.setDuration(9000);
            colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
            colorAnimation.setInterpolator(new AccelerateInterpolator());
            colorAnimation.setRepeatCount(Animation.INFINITE);
        } else {
            colorAnimation.cancel();
            colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                    Color.parseColor(lightColor), Color.parseColor(darkColor));
            colorAnimation.setDuration(1000);
        }
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                shleepApp.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
    }

    private void makeTimer(boolean makeIt) {
        if (makeIt) {
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
            shleepTimer.cancel();
        }

    }

    private void shleepNow() {
        if (isShleeping == false) {
            isShleeping = true;
            shleepTxt.setText("shleep.");
            showSystemUI(false);
            changeScreen(true);
            setInfiniteAnimation(true);
            makeTimer(true);
        } else {
            isShleeping = false;
            shleepTxt.setText("What's up?");
            changeScreen(false);
            showSystemUI(true);
            setInfiniteAnimation(false);
            makeTimer(false);
        }
        colorAnimation.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shleepApp = (ConstraintLayout) findViewById(R.id.shleepApp);
        shleepBtn = (Button) findViewById(R.id.shleepBtn);
        shleepTxt = (TextView) findViewById(R.id.shleepTxt);

        showSystemUI(true);
        shleepTxt.setText("What's up?");

        shleepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shleepNow();
            }
        });
    }
}