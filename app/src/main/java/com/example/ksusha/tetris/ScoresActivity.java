package com.example.ksusha.tetris;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class ScoresActivity extends AppCompatActivity {
    private final static String FILE_NAME = "tetris_scores.txt";
    private String fileFullName = "";
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        text = (TextView) findViewById(R.id.allScores);
        setScores();
    }

    public void setScores(){
        File scoresFile = new File(getFilesDir(), FILE_NAME);
        if(!scoresFile.exists()) {
            try {
                scoresFile.createNewFile();
            } catch (Exception e1) {
                Toast.makeText(getApplicationContext(), "Exception2: " + e1.toString(), Toast.LENGTH_LONG).show();
            }
        }
        try {
            FileInputStream fIn = new FileInputStream(new File(scoresFile.getAbsolutePath().toString()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fIn));
            String line = reader.readLine();
            int counter = 0;
            while(line != null) {
                counter++;
                text.setText(text.getText() + "\n" + counter + ". " + line);
                line = reader.readLine();
            }
        } catch (Throwable t){
            Toast.makeText(getApplicationContext(), "Exception3: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
