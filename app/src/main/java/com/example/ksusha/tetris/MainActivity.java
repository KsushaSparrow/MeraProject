package com.example.ksusha.tetris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickStart();
            }

        });

        Button buttonScores = (Button) findViewById(R.id.buttonScores);
        buttonScores.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickScores();
            }

        });
    }

    public void onClickStart(){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void onClickScores(){
        Intent intent = new Intent(this, ScoresActivity.class);
        startActivity(intent);
    }
}
