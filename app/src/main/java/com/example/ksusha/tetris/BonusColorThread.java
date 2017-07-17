package com.example.ksusha.tetris;

import android.graphics.Color;

public class BonusColorThread extends Thread{
    final int time = 30;
    private int i = 0;
    int[] colors;
    private MovementView movementView;

    public BonusColorThread (MovementView rMovementView){
        movementView = rMovementView;
        colors = new int[30];
        colors[0] = Color.rgb(225, 30, 125);
        colors[1] = Color.rgb(219, 26, 120);
        colors[2] = Color.rgb(212, 23, 115);
        colors[3] = Color.rgb(202, 22, 110);
        colors[4] = Color.rgb(195, 20, 105);
        colors[5] = Color.rgb(188, 19, 101);
        colors[6] = Color.rgb(181, 19, 98);
        colors[7] = Color.rgb(176, 21, 96);
        colors[8] = Color.rgb(169, 19, 92);
        colors[9] = Color.rgb(162, 18, 87);
        colors[10] = Color.rgb(155, 18, 84);
        colors[11] = Color.rgb(147, 18, 81);
        colors[12] = Color.rgb(140, 19, 78);
        colors[13] = Color.rgb(133, 19, 74);
        colors[14] = Color.rgb(124, 18, 69);
        colors[15] = Color.rgb(116, 17, 65);
        colors[16] = Color.rgb(109, 15, 61);
        colors[17] = Color.rgb(102, 18, 59);
        colors[18] = Color.rgb(93, 13, 51);
        colors[19] = Color.rgb(83, 12, 46);
        colors[20] = Color.rgb(76, 12, 43);
        colors[21] = Color.rgb(69, 13, 40);
        colors[22] = Color.rgb(62, 9, 35);
        colors[23] = Color.rgb(55, 8, 31);
        colors[24] = Color.rgb(47, 11, 29);
        colors[25] = Color.rgb(38, 6, 22);
        colors[26] = Color.rgb(33, 4, 19);
        colors[27] = Color.rgb(26, 4, 15);
        colors[28] = Color.rgb(19, 5, 12);
        colors[29] = Color.rgb(0, 0, 0);
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
