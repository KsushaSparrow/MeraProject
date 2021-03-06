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
    }
}
