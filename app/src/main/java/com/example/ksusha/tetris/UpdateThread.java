package com.example.ksusha.tetris;

/**
 * Created by ksusha on 02.07.17.
 */
import android.graphics.Canvas;
import android.graphics.Color;
import android.provider.Settings;
import android.view.SurfaceHolder;
public class UpdateThread extends Thread {
    private long time;
    private final int fps = 20;
    private boolean toRun = false;
    private MovementView movementView;
    private SurfaceHolder surfaceHolder;


    public UpdateThread(MovementView rMovementView){
        movementView = rMovementView;
        surfaceHolder = movementView.getHolder();
    }
    public void setRunning(boolean run){
        toRun = run;
    }

    @Override
    public void run(){

        Canvas c;
        while (toRun){
            long cTime = System.currentTimeMillis();

            if((cTime - time) <= (1000/fps)){

                c = null;
                try{
                    c = surfaceHolder.lockCanvas(null);

                    movementView.updatePhysics();
                    movementView.onDraw(c);
                } finally {
                    if (c!=null){
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
            time = cTime;
        }
    }
}
