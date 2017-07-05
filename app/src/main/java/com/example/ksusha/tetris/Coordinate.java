package com.example.ksusha.tetris;

import android.graphics.Paint;

/**
 * Created by ksusha on 05.07.17.
 */

public class Coordinate {
    int x;
    int y;
    Paint paint;

    public Coordinate(int x, int y, Paint paint){
        this.x = x;
        this.y = y;
        this.paint = new Paint();
        this.paint.setColor(paint.getColor());
    }
}
