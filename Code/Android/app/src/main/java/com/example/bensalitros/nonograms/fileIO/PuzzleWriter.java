package com.example.bensalitros.nonograms.fileIO;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ben Salitros on 4/12/2018.
 */

public class PuzzleWriter {
    private long bestTime;
    private int difficulty;
    private String fileName;
    private Context context;
    private ArrayList<ArrayList<Integer>> puzzleArray;
    private int m, n;

    public PuzzleWriter(long bestTime, int difficulty, String fileName, Context context)  {

        this.bestTime = bestTime;
        this.difficulty = difficulty;
        this.context = context;
        this. fileName = fileName;

        writePuzzleString();
    }


    private void writePuzzleString()  {

        this.puzzleArray  = new ArrayList<ArrayList<Integer>>();
        StringBuilder sb = new StringBuilder();

        //Format: highscore (<--this could be a date/time type...just a number for now); m; n;
        //		  row_1, (comma separated values)
        //		  ...,
        //		  row_n

        sb.append(this.difficulty).append(";")
                .append(this.bestTime).append(";");

        Scanner sc = null;
        try {
            sc = new Scanner(context.openFileInput(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line = sc.nextLine();
        String data[] = line.split(";");

        String dimensions[] = data[2].split(",");

        this.m = Integer.parseInt(dimensions[0]);
        this.n = Integer.parseInt(dimensions[1]);

        int k = 0;

        while(sc.hasNext())  {
            line = sc.nextLine();
            data = line.split(",");

            this.puzzleArray.add(new ArrayList<Integer>());

            for(int j = 0; j < n; j++)  {
                this.puzzleArray.get(k).add(Integer.parseInt(data[j]));
            }

            k++;
        }

        sc.close();

        sb.append(m).append(",").append(n).append(";").append(System.getProperty("line.separator"));

        for(int i = 0; i < m; i++)  {
            for(int j = 0; j < n; j++)  {
                if(j != 0)  {
                    sb.append(",");
                }

                if(this.puzzleArray.get(i).get(j) == 1)  {
                    sb.append("1");
                }
                else  {
                    sb.append("0");
                }

            }
            sb.append(System.getProperty("line.separator"));
        }

        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            osw.write(sb.toString());
            osw.close();

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
