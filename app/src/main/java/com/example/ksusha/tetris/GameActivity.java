package com.example.ksusha.tetris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    boolean musicOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MovementView(this));
    }

 /*   @Override
    protected void onRestart(){
        super.onRestart();
    }*/

    @Override
    protected void onPause(){
        super.onPause();
        this.finish();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();
        this.finish();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Intent intent = new Intent(this, GameOver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.finish();
        startActivity(intent);
    }
/*
    public void startAgain(){
        Intent i = new Intent(this, this.getClass());
        this.startActivity(i);
    }*/
}
