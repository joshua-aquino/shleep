package com.example.shleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button shleepBtn;
    private TextView shleepTxt;
    private boolean isShleeping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shleepBtn = (Button) findViewById(R.id.shleepBtn);
        shleepTxt = (TextView) findViewById(R.id.shleepTxt);
        isShleeping = false;

        shleepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShleeping == false) {
                    isShleeping = true;
                    shleepTxt.setText("What's up?");
                } else {
                    isShleeping = false;
                    shleepTxt.setText("shleep.");
                }
            }
        });
    }
}