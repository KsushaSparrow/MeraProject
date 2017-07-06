package com.example.ksusha.tetris;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class MovementView extends SurfaceView implements SurfaceHolder.Callback {

    private Matrix translate;
    private GestureDetector gestures;

    Context context;
    Figure figure;
    enum Figures {L,O,T,Z,S,J,I};
    enum Colors {RED, BLUE, GREEN, YELLOW};
    ArrayList<Coordinate> filledCells = new ArrayList<Coordinate>(0);
    ArrayList<Coordinate> highestCells = new ArrayList<Coordinate>(0);

    private int width;
    private int height;
    private int cellSize;

    private Paint rectanglePaint;

    UpdateThread updateThread;

 //   private int chooseColor;

    public MovementView(Context context){
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        translate = new Matrix();
        gestures = new GestureDetector(context, new MyGestureListener());

        rectanglePaint = new Paint();
        rectanglePaint.setColor(pickRandomColor());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return gestures.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapConfirmed(MotionEvent event){
            boolean reachedFilledCell = reachedFilledCell(figure, 0);
            if (touchInsideFigure(event.getX(), event.getY()) && !reachedFilledCell){
                figure.turn();
            } else if (event.getX() >= width/2 && !figureIsOverSide() && !reachedFilledCell(figure, cellSize)) {
                for (int i = 0; i < figure.positions[0].length; i++)
                    figure.positions[0][i] += cellSize;
            } else if (event.getX() < width/2 && !figureIsOverSide() && !reachedFilledCell(figure, cellSize)){
                for (int i = 0; i < figure.positions[0].length; i++)
                    figure.positions[0][i] -= cellSize;
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent event){
            onSingleTapConfirmed(event);
            return true;
        }

        @Override
        public boolean onDown(MotionEvent event){
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY){
            return true;
        }
    }

    public int[][] getMinAndMaxCoordFigure(){
        int[][] arr = new int[2][2];
        int minX = 10000;
        int minY = 10000;
        int maxX = 0;
        int maxY = 0;
        for (int i = 0; i < figure.positions[0].length; i++){
            if (figure.positions[0][i] < minX)
                minX = figure.positions[0][i];
            else if (figure.positions[0][i] > maxX)
                maxX = figure.positions[0][i];
            if (figure.positions[1][i] < minY)
                minY = figure.positions[1][i];
            else if (figure.positions[1][i] > maxY)
                maxY = figure.positions[1][i];
        }
        arr[0][0] = minX;
        arr[1][0] = minY;
        arr[0][1] = maxX;
        arr[1][1] = maxY;
        return arr;
    }

    public boolean touchInsideFigure(float xPositionTouch, float yPositionTouch){
        int[][] minMaxCoord = getMinAndMaxCoordFigure();
        if (minMaxCoord[0][0]-2*cellSize <= xPositionTouch & xPositionTouch <= minMaxCoord[0][1]+2*cellSize
                & minMaxCoord[1][0]-2*cellSize <= yPositionTouch & yPositionTouch <= minMaxCoord[1][1]+2*cellSize)
            return true;
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        for (int i = 0; i < figure.positions[0].length; i++) {
            canvas.drawRect(figure.positions[0][i]-cellSize/2, figure.positions[1][i]-cellSize/2, figure.positions[0][i]+cellSize/2, figure.positions[1][i]+cellSize/2, rectanglePaint);
        }
        for (int i = 0; i < filledCells.toArray().length; i++) {
            canvas.drawRect(filledCells.get(i).x-cellSize/2, filledCells.get(i).y-cellSize/2, filledCells.get(i).x+cellSize/2, filledCells.get(i).y+cellSize/2, filledCells.get(i).paint);
        }
    }

    public boolean figureIsOverSide(){
        for (int i = 0; i < figure.positions[0].length; i++){
            if (figure.positions[0][i] + cellSize >= width || figure.positions[0][i] - cellSize <=0)
                return true;
        }
        return false;
    }

    public void updatePhysics(){
        for (int i = 0; i < figure.positions[0].length; i++) {
            if (figure.positions[1][i] + cellSize/2 <= height && !reachedFilledCell(figure.positions[1][i]+cellSize, figure.positions[0][i])) {
                figure.positions[1][i] += figure.yVel;
            } else{
                for (int j = 0; j < figure.positions[0].length; j++){
                    filledCells.add(new Coordinate(figure.positions[0][j], figure.positions[1][j], rectanglePaint));
                }
                addHighest(figure);
                Figures random = pickRandomFigure();
                if (random.equals(Figures.J))
                    figure = new J(width/2, cellSize);
                else if (random.equals(Figures.I))
                    figure = new I(width/2, cellSize);
                else if (random.equals(Figures.Z))
                    figure = new Z(width/2, cellSize);
                else if (random.equals(Figures.O))
                    figure = new O(width/2, cellSize);
                else if (random.equals(Figures.S))
                    figure = new S(width/2, cellSize);
                else if (random.equals(Figures.T))
                    figure = new T(width/2, cellSize);
                else if (random.equals(Figures.L))
                    figure = new L(width/2, cellSize);
                if(reachedFilledCell(figure.positions[1][i] + cellSize, figure.positions[0][i]) && figure.positions[1][i] - cellSize/2 == 0) {
                    Intent intent = new Intent(context, GameOver.class);
                    context.startActivity(intent);
                    break;
                }
                rectanglePaint.setColor(pickRandomColor());
                break;
            }
        }
    }

    public Figures pickRandomFigure(){
        Random rand = new Random();
        int x = rand.nextInt(7-0);
        return Figures.values()[x];
    }

    public int pickRandomColor(){
        Random rand = new Random();
        int x = rand.nextInt(4-0);
        if (x == 0)
            return Color.RED;
        else if (x == 1)
            return Color.BLUE;
        else if (x == 2)
            return Color.GREEN;
        else if (x == 3)
            return Color.YELLOW;
        else
            return Color.BLACK;
    }

    public boolean reachedFilledCell(int positionY, int positionX){
        for (int i = 0; i < filledCells.toArray().length; i++)
            if (positionY >= filledCells.get(i).y && positionX == filledCells.get(i).x)
                return true;
        return false;
    }

    public boolean reachedFilledCell(Figure figure, int deltaX){
        for (int i = 0; i < figure.positions[0].length; i++){
            for (int j = 0; j < filledCells.toArray().length; j++){
                if (figure.positions[1][i] >= filledCells.get(j).y && figure.positions[0][i] == (filledCells.get(j).x + deltaX))
                    return true;
            }
        }
        return false;
    }

    public boolean reachedHighest(int positionY, int positionX){
        for (int i = 0; i < highestCells.toArray().length; i++)
            if (positionY >= highestCells.get(i).y && positionX == highestCells.get(i).x)
                return true;
        return false;
    }

    public boolean crossesHighest(int position){
        for (int i = 0; i < highestCells.toArray().length; i++)
            if (position >= highestCells.get(i).y)
                return true;
        return false;
    }

    public void addHighest(Figure figure){
        int min = 10000;
        for (int j = 0; j < figure.positions[0].length; j++){
            if(figure.positions[1][j] < min)
                min = figure.positions[1][j];
        }
        for (int j = 0; j < figure.positions[0].length; j++){
            if (figure.positions[1][j] == min)
                highestCells.add(new Coordinate(figure.positions[0][j],figure.positions[1][j],rectanglePaint));
        }
    }

    public void surfaceCreated(SurfaceHolder holder){
        Rect surfaceFrame = holder.getSurfaceFrame();
        width = surfaceFrame.width();
        height = surfaceFrame.height();
        cellSize = Math.round(width/16);
        int temp = cellSize;
        int temp1 = cellSize/2;
        if (temp != 2*temp1)
            cellSize += 1;
        while(!this.isShown()){

        }
        figure = new I(width/2, cellSize);

        updateThread = new UpdateThread(this);
        updateThread.setRunning(true);
        updateThread.start();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    public void surfaceDestroyed(SurfaceHolder holder){

        boolean retry = true;

        updateThread.setRunning(false);
        while(retry){
            try{
                updateThread.join();
                retry = false;
            } catch (InterruptedException e){

            }
        }
    }
}
