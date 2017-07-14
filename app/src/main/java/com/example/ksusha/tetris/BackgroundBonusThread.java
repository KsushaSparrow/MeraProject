package com.example.ksusha.tetris;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

/**
 * Created by ksusha on 14.07.17.
 */

public class BackgroundBonusThread extends Thread{
    private long time;
    private final int fps = 100;
    private boolean toRun = false;
    private MovementView movementView;
    private SurfaceHolder surfaceHolder;
    private long times = 0;


    public BackgroundBonusThread(MovementView rMovementView){
        movementView = rMovementView;
        surfaceHolder = movementView.getHolder();
    }
    public void setRunning(boolean run){
        toRun = run;
    }

    @Override
    public void run() {

        Canvas c;
        while (toRun) {
            boolean changed = false;

            c = null;
        //    try {
         //       while (times < 5000) {
                /*    if (!changed) {
                        movementView.setTemporaryBackground(Color.MAGENTA);
                        changed = true;
                    }
                    else
                        movementView.setTemporaryBackground(Color.BLACK);*/
           //     changeColor();
          //          sleep(300);
           //         ++times;
                }
         //   } catch (InterruptedException e) {

         //   }
        //}
    }

    private void changeColor (){
        final int initialColor = Color.MAGENTA;
        final int finalColor = Color.BLACK;
        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float position = valueAnimator.getAnimatedFraction();
                int blended = blendColors(initialColor, finalColor, position);
         //       movementView.setTemporaryBackground(blended);
            }
        });

        anim.setDuration(5000).start();
    }

    private  int blendColors (int from, int to, float ratio){
        final float inverseRatio = 1f - ratio;
        final float r = Color.red(to)*ratio + Color.red(from)*inverseRatio;
        final float g = Color.green(to)*ratio + Color.green(from)*inverseRatio;
        final float b = Color.blue(to)*ratio + Color.blue(from)*inverseRatio;

        return Color.rgb((int)r, (int)g, (int)b);
    }
}

