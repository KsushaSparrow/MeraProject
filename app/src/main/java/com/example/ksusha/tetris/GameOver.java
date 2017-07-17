package com.example.ksusha.tetris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOver extends AppCompatActivity implements View.OnClickListener{

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

        Button buttonExit = (Button) findViewById(R.id.buttonToScores);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToScores();
            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.buttonStartAgain:
                onClickStartAgain();
                break;
            case R.id.buttonToScores:
                onClickToScores();
                break;
        }
    }

    public void onClickStartAgain(){
        Intent i = new Intent(this, GameActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(i, 0);
        this.finish();
    }

    public void onClickToScores(){
        Intent i = new Intent(this, ScoresActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(i, 0);
        this.finish();
    }

}
