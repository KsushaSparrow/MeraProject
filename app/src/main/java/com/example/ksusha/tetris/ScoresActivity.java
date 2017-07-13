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

public class ScoresActivity extends AppCompatActivity {
    private final static String FILE_NAME = "scores.txt";
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
                String str = "0";
                scoresFile.createNewFile();
                try {
                    FileOutputStream fOut = new FileOutputStream(new File(scoresFile.getAbsolutePath().toString()),true);
                    fOut.write(str.getBytes());
                    fOut.close();
                } catch (Throwable t) {
                    Toast.makeText(getApplicationContext(), "Exception1: " + t.toString(), Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
                text.setText(str);
            } catch (Exception e1) {
                Toast.makeText(getApplicationContext(), "Exception2: " + e1.toString(), Toast.LENGTH_LONG).show();
            }
        }
        else {
                try {
                    FileInputStream fIn = new FileInputStream(new File(scoresFile.getAbsolutePath().toString()));
                    InputStreamReader isr = new InputStreamReader(fIn);
                    char[] inputBuffer = new char[16];
                    isr.read(inputBuffer);
                    String read = new String(inputBuffer);
            /*        FileReader fr = new FileReader(scoresFile);
                    BufferedReader reader = new BufferedReader(fr);
                    String str;
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null){
                        buffer.append(str + "\n");
                    }*/
                    isr.close();
                //    Toast.makeText(getApplicationContext(), read, Toast.LENGTH_LONG).show();

                    text.setText(read);
                } catch (Throwable t){
                    Toast.makeText(getApplicationContext(), "Exception3: " + t.toString(), Toast.LENGTH_LONG).show();
                }
            }
    }
}
