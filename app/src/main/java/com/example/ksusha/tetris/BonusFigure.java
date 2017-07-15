package com.example.ksusha.tetris;

import android.content.Context;

public class BonusFigure extends Figure {
    boolean upsidedownBonus;

    public BonusFigure(Context context){
        super(context);
    }
    public void setBonusFigure(int middle, int cellSize){
        this.middle = middle;
        this.cellSize = cellSize;
        upsidedownBonus = false;
        positions = new int[2][3];
    //    positions = new int[2][4];
        positions[0][0] = middle+cellSize+cellSize/2;
        positions[1][0] = cellSize/2;
        positions[0][1] = middle+cellSize/2;
        positions[1][1] = cellSize + cellSize/2;
        positions[0][2] = middle-cellSize/2;
        positions[1][2] = 2*cellSize + cellSize/2;
    //    positions[0][3] = middle-cellSize-cellSize/2;
   //     positions[1][3] = 3*cellSize + cellSize/2;
    }

    @Override
    public void turn(){
        if (!upsidedownBonus){
            positions[1][0] = positions[1][0] + 2*cellSize;
        //    positions[1][1] += cellSize;
            positions[1][2] = positions[1][2] - 2*cellSize;
        //    positions[1][3] = positions[1][3] - 3*cellSize;
            upsidedownBonus = true;
        } else if (upsidedownBonus){
            positions[1][0] = positions[1][0] - 3*cellSize;
            positions[1][1] -= cellSize;
            positions[1][2] += cellSize;
        //    positions[1][3] = positions[1][3] + 3*cellSize;
            upsidedownBonus = false;
        }
    }
}
