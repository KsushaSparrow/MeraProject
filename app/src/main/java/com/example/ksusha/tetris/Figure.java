package com.example.ksusha.tetris;

/**
 * Created by ksusha on 05.07.17.
 */

public class Figure {
    int[][] positions;
    int xVel = 0;
    int yVel = 5;
    int middle;
    int cellSize;

    public void speedUpByUser(int delta){
        yVel += delta;
    }

    public void speedUp(int delta){
        yVel += delta;
    }

    public void turn(){

    }
}
