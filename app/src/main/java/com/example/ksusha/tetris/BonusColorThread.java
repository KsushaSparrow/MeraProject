package com.example.ksusha.tetris;

import android.graphics.Color;

public class BonusColorThread extends Thread{
    final int time = 20;
    private int i = 0;
    int[] colors;
    private MovementView movementView;

    public BonusColorThread (MovementView rMovementView){
        movementView = rMovementView;
        colors = new int[20];
        colors[0] = Color.rgb(225, 30, 125);
        colors[1] = Color.rgb(219, 36, 125);
        colors[2] = Color.rgb(221, 39, 123);
        colors[3] = Color.rgb(207, 43, 122);
        colors[4] = Color.rgb(197, 44, 118);
        colors[5] = Color.rgb(185, 44, 113);
        colors[6] = Color.rgb(183, 49, 114);
        colors[7] = Color.rgb(171, 47, 107);
        colors[8] = Color.rgb(159, 45, 100);
        colors[9] = Color.rgb(147, 45, 94);
        colors[10] = Color.rgb(137, 42, 88);
        colors[11] = Color.rgb(126, 40, 82);
        colors[12] = Color.rgb(114, 38, 75);
        colors[13] = Color.rgb(99, 35, 66);
        colors[14] = Color.rgb(83, 30, 56);
        colors[15] = Color.rgb(71, 26, 48);
        colors[16] = Color.rgb(59, 24, 41);
        colors[17] = Color.rgb(47, 20, 33);
        colors[18] = Color.rgb(35, 14, 25);
        colors[19] = Color.rgb(0, 0, 0);
    }

    @Override
    public void run(){
        try{
            while(i < time){
                movementView.color = colors[i];
                sleep(500);
                ++i;
            }
        } catch (InterruptedException e){

        }
    }
}
