package com.example.ksusha.tetris;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    boolean playMusic = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        mediaPlayer = MediaPlayer.create(this, R.raw.tetris1);
        try{
            mediaPlayer.prepare();
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
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

        ImageButton buttonMusicOn = (ImageButton) findViewById(R.id.musicOn);
        buttonMusicOn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                switchMusic();
            }
        });

        ImageButton buttonMusicOff = (ImageButton) findViewById(R.id.musicOff);
        buttonMusicOff.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                switchMusic();
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

    public void switchMusic(){
        if (playMusic){
            playMusic = false;
            mediaPlayer.pause();
            findViewById(R.id.musicOn).setVisibility(View.INVISIBLE);
        } else if (!playMusic){
            playMusic = true;
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
            findViewById(R.id.musicOn).setVisibility(View.VISIBLE);
        }
    }
}
