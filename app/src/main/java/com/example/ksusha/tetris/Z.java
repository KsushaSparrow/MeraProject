package com.example.ksusha.tetris;

import android.content.Context;

/**
 * Created by ksusha on 05.07.17.
 */

public class Z extends Figure {
    boolean upsidedown = false;
    public Z(Context context){
        super(context);
    }
    public void setZ(int middle, int cellSize){
        this.middle = middle;
        this.cellSize = cellSize;
        positions = new int[2][4];
        positions[0][0] = middle-cellSize/2;
        positions[1][0] = cellSize/2;
        positions[0][1] = middle+cellSize/2;
        positions[1][1] = cellSize/2;
        positions[0][2] = middle+cellSize/2;
        positions[1][2] = cellSize+cellSize/2;
        positions[0][3] = middle+cellSize+cellSize/2;
        positions[1][3] = cellSize + cellSize/2;
    }

    @Override
    public void turn(){
        if (!upsidedown){
            positions[0][0] += cellSize;
            positions[1][0] += cellSize;
            positions[0][2] -= cellSize;
            positions[0][3] = positions[0][3] - 2*cellSize;
            positions[1][3] += cellSize;
            upsidedown = true;
        } else if (upsidedown){
            positions[0][0] -= cellSize;
            positions[1][0] -= cellSize;
            positions[0][2] += cellSize;
            positions[0][3] = positions[0][3] + 2*cellSize;
            positions[1][3] -= cellSize;
            upsidedown = false;
        }
    }
}
