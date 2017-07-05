package com.example.ksusha.tetris;

/**
 * Created by ksusha on 05.07.17.
 */

public class O extends Figure{

    public O(int middle) {
        positions = new int[2][4];
        positions[0][0] = middle;
        positions[1][0] = 25;
        positions[0][1] = middle+50;
        positions[1][1] = 25;
        positions[0][2] = middle;
        positions[1][2] = 75;
        positions[0][3] = middle + 50;
        positions[1][3] = 75;
    }
}
