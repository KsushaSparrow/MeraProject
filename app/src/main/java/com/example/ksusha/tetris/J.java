package com.example.ksusha.tetris;

import android.content.Context;

/**
 * Created by ksusha on 05.07.17.
 */

public class J extends Figure {
    int turnedState;
    public J(Context context){
        super(context);
    }
    public void setJ(int middle, int cellSize){
        this.middle = middle;
        this.cellSize = cellSize;
        positions = new int[2][4];
        positions[0][0] = middle+cellSize/2;
        positions[1][0] = cellSize/2;
        positions[0][1] = middle+cellSize/2;
        positions[1][1] = cellSize + cellSize/2;
        positions[0][2] = middle+cellSize/2;
        positions[1][2] = 2*cellSize + cellSize/2;
        positions[0][3] = middle-cellSize/2;
        positions[1][3] = 2*cellSize + cellSize/2;
    }

    @Override
    public void turn(){
        if (turnedState == 0) {
            positions[0][0] += cellSize;
            positions[1][0] = positions[1][3];
            positions[1][1] = positions[1][3];
            positions[0][2] -= cellSize;
            positions[1][3] -= cellSize;
            turnedState = 1;
        } else if (turnedState == 1) {
            positions[0][0] = positions[0][0] - 2*cellSize;
            positions[0][1] -= cellSize;
            positions[1][1] -= cellSize;
            positions[1][2] = positions[1][2] - 2*cellSize;
            positions[0][3] += cellSize;
            positions[1][3] -= cellSize;
            turnedState = 2;
        } else if (turnedState == 2) {
            positions[0][1] += cellSize;
            positions[1][1] += cellSize;
            positions[0][2] = positions[0][2] + 2*cellSize;
            positions[1][2] = positions[1][2] + 2*cellSize;
            positions[0][3] += cellSize;
            positions[1][3] = positions[1][3] + 3*cellSize;
            turnedState = 3;
        } else if (turnedState == 3) {
            positions[0][0] = positions[0][0] + 2*cellSize;
            positions[1][0] -= cellSize;
            positions[0][1] += cellSize;
            positions[1][2] += cellSize;
            positions[0][3] -= cellSize;
            turnedState = 0;
        }
    }
}
