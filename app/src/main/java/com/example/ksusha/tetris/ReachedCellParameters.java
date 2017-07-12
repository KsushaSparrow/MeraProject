package com.example.ksusha.tetris;

public class ReachedCellParameters {
    boolean reached = false;
    int[] oldValues;
    int[] newValues;

    public ReachedCellParameters(boolean reached, int oldX, int oldY, int newX, int newY){
        this.reached = reached;
        oldValues = new int[2];
        oldValues[0] = oldX;
        oldValues[1] = oldY;
        newValues = new int[2];
        newValues[0] = newX;
        newValues[1] = newY;
    }
}
