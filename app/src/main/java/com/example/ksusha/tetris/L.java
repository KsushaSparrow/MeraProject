package com.example.ksusha.tetris;

import android.content.Context;

/**
 * Created by ksusha on 05.07.17.
 */

public class L extends Figure {
    int stateTurned = 0;

    public L(Context context){
        super(context);
    }

    public void setL(int middle, int cellSize){
        this.middle = middle;
        this.cellSize = cellSize;
        positions = new int[2][4];
        positions[0][0] = middle+cellSize/2;
        positions[1][0] = cellSize/2;
        positions[0][1] = middle+cellSize/2;
        positions[1][1] = cellSize + cellSize/2;
        positions[0][2] = middle+cellSize/2;
        positions[1][2] = 2*cellSize + cellSize/2;
        positions[0][3] = middle+cellSize + cellSize/2;
        positions[1][3] = 2*cellSize + cellSize/2;
    }

    @Override
    public void turn(){
        if (stateTurned==0){
            positions[0][0] += cellSize;
            positions[1][0] += cellSize;
            positions[0][2] -= cellSize;
            positions[1][2] -= cellSize;
            positions[0][3] = positions[0][3] - 2*cellSize ;
            stateTurned = 1;
        }
        else if (stateTurned==1) {
            positions[0][0] -= cellSize;
            positions[1][0] += cellSize;
            positions[0][2] += cellSize;
            positions[1][2] -= cellSize;
            positions[1][3] = positions[1][3] -2*cellSize;
            stateTurned = 2;
        } else if (stateTurned==2) {
            positions[0][0] -= cellSize;
            positions[1][0] -= cellSize;
            positions[0][2] += cellSize;
            positions[1][2] += cellSize;
            positions[0][3] = positions[0][3] +2*cellSize;
            stateTurned = 3;
        } else if (stateTurned == 3) {
            positions[0][0] += cellSize;
            positions[1][0] -= cellSize;
            positions[0][2] -= cellSize;
            positions[1][2] += cellSize;
            positions[1][3] = positions[1][3] + 2*cellSize;
            stateTurned = 0;
        }
    }
}
