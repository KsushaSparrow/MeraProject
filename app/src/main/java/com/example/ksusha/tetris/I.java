package com.example.ksusha.tetris;

/**
 * Created by ksusha on 05.07.17.
 */

public class I extends Figure {
    boolean upsidedown;
    public I(int middle, int cellSize){
        this.middle = middle;
        this.cellSize = cellSize;
        upsidedown = false;
        positions = new int[2][4];
        positions[0][0] = middle+cellSize/2;
        positions[1][0] = cellSize/2;
        positions[0][1] = middle+cellSize/2;
        positions[1][1] = cellSize + cellSize/2;
        positions[0][2] = middle+cellSize/2;
        positions[1][2] = 2*cellSize + cellSize/2;
        positions[0][3] = middle+cellSize/2;
        positions[1][3] = 3*cellSize + cellSize/2;
    }

    @Override
    public void turn(){
        if (!upsidedown) {
            positions[0][0] = positions[0][0] - 2 * cellSize;
            positions[1][0] = positions[1][3];
            positions[0][1] = positions[0][1] - cellSize;
            positions[1][1] = positions[1][3];
            positions[1][2] = positions[1][3];
            positions[0][3] = positions[0][3] + cellSize;
            upsidedown = true;
        } else if (upsidedown){
            positions[0][0] = positions[0][0] + 2 * cellSize;
            positions[1][0] -= 3*cellSize;
            positions[0][1] = positions[0][1] + cellSize;
            positions[1][1] -= 2*cellSize;
            positions[1][2] -= cellSize;
            positions[0][3] = positions[0][3] - cellSize;
            upsidedown = false;
        }
    }
}
