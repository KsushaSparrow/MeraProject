package com.example.ksusha.tetris;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ksusha on 05.07.17.
 */

public class Figure extends AppCompatImageView implements View.OnDragListener{
    int[][] positions;
    int xVel = 0;
    int yVel = 5;
    int middle;
    int cellSize;

    public Figure (Context context){
        super(context);
    }

    public void speedUpByUser(int delta){
        yVel += delta;
    }

    public void speedUp(int delta){
        yVel += delta;
    }

    public void turn(){

    }
    //@Override
    public boolean onTouch(View v, MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_MOVE){
            ClipData clipData = ClipData.newPlainText("","");
            View.DragShadowBuilder dsb = new View.DragShadowBuilder(v);
            v.startDrag(clipData, dsb, v, 0);
            v.setVisibility(v.INVISIBLE);//do i need it?
            return true;
        } else return false;
        //     return gestures.onTouchEvent(event);
    }


    public boolean onDrag(View v, DragEvent dragEvent){
        int dragAction = dragEvent.getAction();
        View dragView = (View) dragEvent.getLocalState();
        boolean containsDragable = true;
        if (dragAction == DragEvent.ACTION_DRAG_EXITED){
            containsDragable = false;
        } else if(dragAction == DragEvent.ACTION_DRAG_ENTERED){
            containsDragable = true;
        } else if(dragAction == DragEvent.ACTION_DRAG_ENDED){
            if (dropEventNotHandled(dragEvent)){
                dragView.setVisibility(View.VISIBLE);
            }
        } else if (dragAction == DragEvent.ACTION_DROP && containsDragable){
            dragView.setVisibility(View.VISIBLE);
        }
        return true;
    }

    private boolean dropEventNotHandled(DragEvent dragEvent){
        return !dragEvent.getResult();
    }

    private void checkForValidMove(View dragView){

    }
}
