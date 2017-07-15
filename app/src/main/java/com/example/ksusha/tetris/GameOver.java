package com.example.ksusha.tetris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Button buttonStartAgain = (Button) findViewById(R.id.buttonStartAgain);
        buttonStartAgain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickStartAgain();
            }

        });

        Button buttonExit = (Button) findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickExit();
            }

        });
    }

    public void onClickStartAgain(){
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(i, 0);
        this.finish();


    }

    public void onClickExit(){
        android.os.Process.killProcess(android.os.Process.myPid());
   //     this.finish();
    }
}
