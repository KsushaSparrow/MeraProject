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
import android.view.View;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class MovementView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private Matrix translate;
    private GestureDetector gestures;

    Context context;
    Figure figure;
    enum Figures {L,O,T,Z,S,J,I};
    enum Colors {RED, BLUE, GREEN, YELLOW};
    ArrayList<Coordinate> filledCells = new ArrayList<Coordinate>(0);
    HashMap<Integer, Integer> values = new HashMap<Integer, Integer>(0);


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
    public boolean onTouch(View v, MotionEvent event){
        return gestures.onTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return gestures.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private final static int SWIPE_THRESHOLD = 100;
        private final static int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else{
                            onSwipeLeft();
                        }
                        result = true;
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }

    //add check
    public  void onSwipeRight() {
        for (int i = 0; i < figure.positions[0].length; i++)
            figure.positions[0][i] += cellSize;
    }
    //add check
    public void onSwipeLeft() {
        for (int i = 0; i < figure.positions[0].length; i++)
            figure.positions[0][i] -= cellSize;
    }
    public void onSwipeBottom() {
        figure.yVel += 20;
    }
    public void onSwipeTop() {
    }

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
        canvas.drawColor(Color.BLACK);
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
            int[] newCoordinates = new int[2];
            int[] oldCoordinates = new int[2];
            if (figure.positions[1][i] + cellSize/2 < height && !reachedFilledCell1(figure.positions[1][i]+cellSize, figure.positions[0][i], newCoordinates, oldCoordinates)) {
                figure.positions[1][i] += figure.yVel;
            } else {
                int[] yPositionsOfFigure = new int[figure.positions[0].length];
           //     filledCells.add(new Coordinate(newCoordinates[0], newCoordinates[1], rectanglePaint));
                for (int j = 0; j < figure.positions[0].length; j++) {
                    int deltaX = figure.positions[0][j] - oldCoordinates[0];
                    int deltaY = figure.positions[1][j] - oldCoordinates[1];
                    filledCells.add(new Coordinate(newCoordinates[0]+deltaX, newCoordinates[1]+deltaY, rectanglePaint));
                    yPositionsOfFigure[j] = newCoordinates[1]+deltaY;
                //    filledCells.add(new Coordinate(figure.positions[0][j], figure.positions[1][j], rectanglePaint));
                    ;
                }
                reduce(yPositionsOfFigure);
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
                if(reachedFilledCell1(figure.positions[1][i]+cellSize, figure.positions[0][i], newCoordinates, oldCoordinates) && figure.positions[1][i] - cellSize/2 == 0) {
                    Intent intent = new Intent(context, GameOver.class);
                    context.startActivity(intent);
                    break;
                }
                rectanglePaint.setColor(pickRandomColor());
                break;
            }
        }
    }

    //reduces filled lines
    public void reduce(int[] yPositionsOfNewFigure) {
        for (int i = 0; i < yPositionsOfNewFigure.length; i++){
            int yPos = yPositionsOfNewFigure[i];
            if(!values.containsKey(yPos))
                values.put(yPos, 1);
            else {
                int temp = values.get(yPos);
                int count = temp + 1;
                values.remove(yPos);
                values.put(yPos, count);
            //    values.replace(yPos, temp, count);
            }
        }
  //      Integer[] valuesArr = new Integer[values.size()];
  //      values.values().toArray(valuesArr);
        Integer[] keysArr = new Integer[values.size()];
        values.keySet().toArray(keysArr);
    //    for (int i = 0; i < valuesArr.length; i++){
    //    if (valuesArr[i] == width/cellSize){
        for (int i = 0; i < keysArr.length; i++){
            if (values.get(keysArr[i]) == width/cellSize){
                for (int j = 0; j < filledCells.size(); j++){
                    if (filledCells.get(j).y == keysArr[i])
                        filledCells.remove(j);
                }
            }
        }
    }

    public int updateScore(){
        return 0;
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
            return Color.rgb(229, 43, 80);
        else if (x == 1)
            return Color.rgb(0, 127, 255);
        else if (x == 2)
            return Color.rgb(127, 255, 0);
        else if (x == 3)
            return Color.rgb(253, 233, 16);
        else
            return Color.BLACK;
    }

    public Coordinate reachedFilledCell(int positionY, int positionX){
        Coordinate coord = null;
        for (int i = 0; i < filledCells.toArray().length; i++)
            if (positionY >= filledCells.get(i).y && positionX == filledCells.get(i).x)
                coord = new Coordinate(filledCells.get(i).x, filledCells.get(i).y, null);
        return coord;
    }

    public boolean reachedFilledCell1(int positionY, int positionX, int[] newCoordinates, int[] oldCoordinates){
        int size = filledCells.toArray().length;
        for (int i = 0; i < size; i++) {
            if (positionY >= filledCells.get(i).y && positionX == filledCells.get(i).x) {
                newCoordinates[0] = filledCells.get(i).x;
                newCoordinates[1] = filledCells.get(i).y;
                oldCoordinates[0] = positionX;
                oldCoordinates[1] = positionY;
                return true;
            }
        }
        if (size == 0){
            newCoordinates[0] = positionX;
            newCoordinates[1] = height - cellSize/2;
            oldCoordinates[0] = positionX;
            oldCoordinates[1] = positionY;
        }
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

 /*   public boolean reachedHighest(int positionY, int positionX){
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
    }*/

 /*   public void addHighest(Figure figure){
        int min = 10000;
        for (int j = 0; j < figure.positions[0].length; j++){
            if(figure.positions[1][j] < min)
                min = figure.positions[1][j];
        }
        for (int j = 0; j < figure.positions[0].length; j++){
            if (figure.positions[1][j] == min)
                highestCells.add(new Coordinate(figure.positions[0][j],figure.positions[1][j],rectanglePaint));
        }
    }*/

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
