package com.example.ksusha.tetris;

import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectStreamException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

public class MovementView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private boolean gotFocusOnce = false;
    int divideBy = 10;
    int bonusCounter = 0;
    boolean bonusLaunched = false;

    private Matrix translate;
    private GestureDetector gestures;

    Context context;
    Figure figure;
    enum Figures {L,O,T,Z,S,J,I, BONUS};
    enum Colors {RED, BLUE, GREEN, YELLOW};


    ArrayList<Coordinate> filledCells = new ArrayList<Coordinate>(0);
    TreeMap<Integer, Integer> values = new TreeMap<Integer, Integer>();


    private int width;
    private int height;
    private int cellSize;
    private String currentScore = "0";
    private final static String FILE_NAME = "tetris_scores.txt";
    private String fileFullName = "";
    File fileScores;

    private Paint scorePaint;
    int scoreTextSize = 100;
    float[] widths;
    float widthScoreText;
    private Paint rectanglePaint;

    UpdateThread updateThread;

    public int numberOfFigures = 0;
    int color = Color.BLACK;
    boolean changingColorStarted = false;

 //   private int chooseColor;

    public MovementView(Context context){
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        translate = new Matrix();
        gestures = new GestureDetector(context, new MyGestureListener());

        rectanglePaint = new Paint();
        rectanglePaint.setColor(pickRandomColor());
        scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(scoreTextSize);
        scorePaint.setStyle(Paint.Style.STROKE);
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
                    if (diffX > 0 && !figureIsOverSideRight() && !reachedFilledCell(figure, cellSize)) {
                        onSwipeRight();
                    } else if (diffX <= 0 && !figureIsOverSideLeft() && !reachedFilledCell(figure, -cellSize)){
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
        figure.yVel += 30;
    }
    public void onSwipeTop() {
    }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event){
            boolean reachedFilledCell = reachedFilledCell(figure, 0);
            if (touchInsideFigure(event.getX(), event.getY()) && !reachedFilledCell){
                Figure fig = getTurnedFigure();
                if (!crossesFilledCells(fig) && !figureIsOverSideRight(fig) && !figureIsOverSideLeft(fig))
                    figure.turn();
            } else if (event.getX() >= width/2 && !figureIsOverSideRight() && !reachedFilledCell(figure, cellSize)) {
                for (int i = 0; i < figure.positions[0].length; i++)
                    figure.positions[0][i] += cellSize;
            } else if (event.getX() < width/2 && !figureIsOverSideLeft() && !reachedFilledCell(figure, -cellSize)){
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

    public Figure getTurnedFigure(){
        Figure temp = null;
        if (figure instanceof I) {
            temp = new I(context);
            ((I) temp).setI(width/2, cellSize);
        } else if (figure instanceof J) {
            temp = new J(context);
            ((J) temp).setJ(width/2, cellSize);
        } else if (figure instanceof L) {
            temp = new L(context);
            ((L) temp).setL(width/2, cellSize);
        } else if (figure instanceof T) {
            temp = new T(context);
            ((T) temp).setT(width/2, cellSize);
        } else if (figure instanceof O) {
            temp = new O(context);
            ((O) temp).setO(width/2, cellSize);
        } else if (figure instanceof S) {
            temp = new S(context);
            ((S) temp).setS(width/2, cellSize);
        } else if (figure instanceof Z) {
            temp = new Z(context);
            ((Z) temp).setZ(width/2, cellSize);
        }
        for (int i = 0; i < temp.positions[0].length; i++){
            temp.positions[0][i] = figure.positions[0][i];
            temp.positions[1][i] = figure.positions[1][i];
        }
        temp.turn();
        return temp;
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
        canvas.drawColor(color);
        widthScoreText = scorePaint.measureText(currentScore);
        widths = new float[currentScore.length()];
        scorePaint.getTextWidths(currentScore, widths);
        canvas.drawText(currentScore, 40, 100, scorePaint);
        Paint edge = new Paint();
        edge.setColor(Color.WHITE);
        int offsetColor = 5;
        for (int i = 0; i < figure.positions[0].length; i++) {
            canvas.drawRect(figure.positions[0][i]-cellSize/2, figure.positions[1][i]-cellSize/2, figure.positions[0][i]+cellSize/2, figure.positions[1][i]+cellSize/2, edge);
            canvas.drawRect(figure.positions[0][i]-cellSize/2+offsetColor, figure.positions[1][i]-cellSize/2+offsetColor, figure.positions[0][i]+cellSize/2-offsetColor, figure.positions[1][i]+cellSize/2-offsetColor, rectanglePaint);
        }
        for (int i = 0; i < filledCells.toArray().length; i++) {

            canvas.drawRect(filledCells.get(i).x-cellSize/2, filledCells.get(i).y-cellSize/2, filledCells.get(i).x+cellSize/2, filledCells.get(i).y+cellSize/2, edge);
            canvas.drawRect(filledCells.get(i).x-cellSize/2+offsetColor, filledCells.get(i).y-cellSize/2+offsetColor, filledCells.get(i).x+cellSize/2-offsetColor, filledCells.get(i).y+cellSize/2-offsetColor, filledCells.get(i).paint);
        }
    }

    public boolean figureIsOverSideRight(){
        for (int i = 0; i < figure.positions[0].length; i++){
            if (figure.positions[0][i] + cellSize >= width)
                return true;
        }
        return false;
    }

    public boolean figureIsOverSideLeft(){
        for (int i = 0; i < figure.positions[0].length; i++){
            if (figure.positions[0][i] - cellSize <=0)
                return true;
        }
        return false;
    }

    public boolean figureIsOverSideRight(Figure fig){
        for (int i = 0; i < fig.positions[0].length; i++){
            if (fig.positions[0][i] + cellSize >= width)
                return true;
        }
        return false;
    }

    public boolean figureIsOverSideLeft(Figure fig){
        for (int i = 0; i < fig.positions[0].length; i++){
            if (fig.positions[0][i] - cellSize <=0)
                return true;
        }
        return false;
    }

    public boolean reachedBottom(){
        for (int i = 0; i < figure.positions[0].length; i++){
            if (figure.positions[1][i] + cellSize/2 >= height)
                return true;
        }
        return false;
    }
    public void updateYofFigure(){
        for (int i = 0; i < figure.positions[0].length; i++){
            figure.positions[1][i] += figure.yVel;
        }
    }
    public boolean onTheTop(){
            if (figure.positions[1][findHighestCell()] - cellSize/2 <= 0) {
                return true;
        }
        return false;
    }

    public void updatePhysics() {
        ReachedCellParameters reachedCellParameters = reachedFilledCell();
        if (!reachedBottom() && !reachedCellParameters.reached) {
            updateYofFigure();
        } else {
            if (reachedCellParameters.reached && onTheTop()) {
                saveScore();
                boolean retry = true;
                updateThread.setRunning(false);
                Intent intent = new Intent(context, GameOver.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("score", currentScore);
                context.startActivity(intent);
            }
            //    currentScore = String.valueOf(Integer.valueOf(currentScore)+1);
            int[] yPositionsOfFigure = new int[figure.positions[0].length];
            int deltaX = 0;
            int deltaY = 0;
            for (int j = 0; j < figure.positions[0].length; j++) {
                deltaX = figure.positions[0][j] - reachedCellParameters.oldValues[0];
                deltaY = figure.positions[1][j] - reachedCellParameters.oldValues[1];
                filledCells.add(new Coordinate(reachedCellParameters.newValues[0] + deltaX, reachedCellParameters.newValues[1] + deltaY, rectanglePaint));
                yPositionsOfFigure[j] = reachedCellParameters.newValues[1] + deltaY;
            }
            reduce(yPositionsOfFigure);
            setRandomFigure();
        }

    }

    public void setRandomFigure(){
        rectanglePaint.setColor(pickRandomColor());
        Figures random = pickRandomFigure();
        if (random.equals(Figures.J)) {
            figure = new J(context);
            ((J) figure).setJ(width / 2, cellSize);
        } else if (random.equals(Figures.I)) {
            figure = new I(context);
            ((I) figure).setI(width / 2, cellSize);
        } else if (random.equals(Figures.Z)) {
            figure = new Z(context);
            ((Z) figure).setZ(width / 2, cellSize);
        } else if (random.equals(Figures.O)) {
            figure = new O(context);
            ((O) figure).setO(width / 2, cellSize);
        } else if (random.equals(Figures.S)) {
            figure = new S(context);
            ((S) figure).setS(width / 2, cellSize);
        } else if (random.equals(Figures.T)) {
            figure = new T(context);
            ((T) figure).setT(width / 2, cellSize);
        } else if (random.equals(Figures.L)) {
            figure = new L(context);
            ((L) figure).setL(width / 2, cellSize);
        } else if (random.equals(Figures.BONUS)) {
            bonusCounter++;
            if (bonusCounter > 15){
                bonusLaunched = false;
                bonusCounter = 0;
            }
            if (!bonusLaunched) {
                bonusLaunched = true;
                figure = new BonusFigure(context);
                ((BonusFigure) figure).setBonusFigure(width / 2, cellSize);
                rectanglePaint.setColor(Color.MAGENTA);
                BonusColorThread bonusThread = new BonusColorThread(this);
                bonusThread.setDaemon(true);
                bonusThread.start();
            }
            else setRandomFigure();
        }
    }
    //reduces filled lines
    public void reduce(int[] yPositionsOfNewFigure) {
        int bonus = 1;
        for (int i = 0; i < yPositionsOfNewFigure.length; i++){
            int yPos = yPositionsOfNewFigure[i];
            if(!values.containsKey(yPos))
                values.put(yPos, 1);
            else {
                int temp = values.get(yPos);
                int count = temp + 1;
                values.remove(yPos);
                values.put(yPos, count);
            }
        }
        Integer[] keysArr = new Integer[values.size()];
        values.keySet().toArray(keysArr);
        for (int i = 0; i < keysArr.length; i++){
            int temp = values.get(keysArr[i]);
            if (temp*cellSize >= width && (temp-1)*cellSize < width){
                for (int j = 0; j < filledCells.size(); j++) {
                    if (filledCells.get(j).y == keysArr[i]) {
                        filledCells.remove(j);
                        j--;
                    } else if(filledCells.get(j).y < keysArr[i]) {
                        filledCells.get(j).y += cellSize;
                    }
                }
                int compare = keysArr[i];
                for (int j = values.size()-1; j > 0; j--){
                    if (keysArr[j] <= compare) {
                        values.put(compare, values.get(keysArr[j-1]));
                        compare = keysArr[j-1];
                    }
                }
                values.put(compare, 0);
                if (color != Color.BLACK)
                    bonus++;
                currentScore = String.valueOf(Integer.valueOf(currentScore)+divideBy*bonus);
            }
        }
    }

    public Figures pickRandomFigure(){
        Random rand = new Random();
        int x = rand.nextInt(8-0);
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

    public int findLowestCell(){
        int maxY = 0;
        int cellNumber = 0;
        for (int i = 0; i < figure.positions[0].length; i++){
            if (figure.positions[1][i] > maxY) {
                maxY = figure.positions[1][i];
                cellNumber = i;
            }
        }
        return cellNumber;
    }

    public int findHighestCell(){
        int maxY = 10000;
        int cellNumber = 0;
        for (int i = 0; i < figure.positions[0].length; i++){
            if (figure.positions[1][i] < maxY) {
                maxY = figure.positions[1][i];
                cellNumber = i;
            }
        }
        return cellNumber;
    }

    public ReachedCellParameters reachedFilledCell(){
        int sizeOfFilledCells = filledCells.toArray().length;
        for (int j = 0; j < figure.positions[0].length; j++) {
            for (int i = 0; i < sizeOfFilledCells; i++) {
                if (figure.positions[1][j] + cellSize >= filledCells.get(i).y && figure.positions[1][j] + cellSize < filledCells.get(i).y+cellSize &&
                        figure.positions[0][j] == filledCells.get(i).x) {
                    return new ReachedCellParameters(true, figure.positions[0][j], figure.positions[1][j], figure.positions[0][j], filledCells.get(i).y - cellSize);
                }
            }
        }
        int temp = height - cellSize/2;
        int lowestCell = findLowestCell();
        return new ReachedCellParameters(false, figure.positions[0][lowestCell], figure.positions[1][lowestCell], figure.positions[0][lowestCell], temp);
    }

    public boolean reachedFilledCell(Figure figure, int deltaX){
        int counter = 0;
        for (int i = 0; i < figure.positions[0].length; i++){
            for (int j = 0; j < filledCells.toArray().length; j++){
                if (figure.positions[1][i] >= filledCells.get(j).y && figure.positions[0][i] + deltaX == (filledCells.get(j).x)
                        && figure.positions[1][i] < filledCells.get(j).y+cellSize)
                    counter++;
            }
        }
        if (counter!=0)
            return true;
        else return false;
    }

    public boolean crossesFilledCells(Figure fig){
        for (int j = 0; j < fig.positions[0].length; j++){
            for (int i = 0; i < filledCells.size(); i++){
                if (fig.positions[1][j] + cellSize >= filledCells.get(i).y && fig.positions[1][j] + cellSize < filledCells.get(i).y+cellSize &&
                        fig.positions[0][j] == filledCells.get(i).x)
                    return true;
            }
        }
        return false;
    }

    public void saveScore(){
        boolean addedScore = false;
        ArrayList<Integer> scores = getPreviousSortedScores();
        if (scores.size() < 5) {
            scores.add(Integer.valueOf(currentScore));
            addedScore = true;
        }
        for (int i = 0; i < scores.size() && i < 5 && !addedScore; i++){
            if (Integer.valueOf(currentScore) > scores.get(i)) {
                scores.set(i, Integer.valueOf(currentScore));
                addedScore = true;
                break;
            }
        }

        if (addedScore) {
            File scoresFile = new File(context.getFilesDir(), FILE_NAME);
            if (!scoresFile.exists()) {
                try {
                    scoresFile.createNewFile();
                } catch (Exception e1) {
                    Toast.makeText(context.getApplicationContext(), "Exception1: " + e1.toString(), Toast.LENGTH_LONG).show();
                }
            }
            Collections.sort(scores, new ScoresComparator());
            try {
                PrintWriter writer = new PrintWriter(scoresFile);
                writer.print("");
                for (int i = 0; i < scores.size(); i++) {
                    if (i < scores.size() - 1)
                        writer.println(scores.get(i));
                    else
                        writer.print(scores.get(scores.size() - 1));
                }
                writer.close();
            } catch (Exception e) {
                Toast.makeText(context.getApplicationContext(), "Exception2: " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public ArrayList<Integer> getPreviousSortedScores(){
        ArrayList<Integer> scores = new ArrayList<Integer>(0);
        File scoresFile = new File(context.getFilesDir(), FILE_NAME);
        if(!scoresFile.exists()) {
            try {
                scoresFile.createNewFile();
            } catch (Exception e1) {
                Toast.makeText(context.getApplicationContext(), "Exception2: " + e1.toString(), Toast.LENGTH_LONG).show();
            }
        }
        try {
            FileInputStream fIn = new FileInputStream(new File(scoresFile.getAbsolutePath().toString()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fIn));
            String line = reader.readLine();
            while(line != null){
                scores.add(Integer.valueOf(line));
                line = reader.readLine();
            }
        } catch (Throwable t){
            Toast.makeText(context, "Exception3: " + t.toString(), Toast.LENGTH_LONG).show();
        }
        Collections.sort(scores, new ScoresComparator());
        return scores;
    }

    public class ScoresComparator implements Comparator<Integer>{
        public int compare(Integer first, Integer second){
            return first.compareTo(second);
        }
    }

    public void surfaceCreated(SurfaceHolder holder){
        Rect surfaceFrame = holder.getSurfaceFrame();
        width = surfaceFrame.width();
        height = surfaceFrame.height();
        cellSize = Math.round(width/divideBy);
        int temp = cellSize;
        int temp1 = cellSize/2;
        if (temp != 2*temp1)
            cellSize += 1;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus){
        if (!gotFocusOnce) {
            gotFocusOnce = true;
            if (hasWindowFocus) {
                figure = new I(context);
                ((I) figure).setI(width / 2, cellSize);

                updateThread = new UpdateThread(this);
                updateThread.setRunning(true);
                updateThread.start();
            }
        }
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
