package com.example.bensalitros.nonograms.fileIO;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ben Salitros on 4/18/2018.
 */

public class FileListReader {

    private String[] fileList;
    private Scanner sc;
    private Context context;

    public FileListReader(Context context)  {
        this.context = context;
        try {
            this.fileList = context.getAssets().list("Puzzles");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getEasyFileList()  {
        ArrayList<String> list = new ArrayList<String>();

        sc = null;
        try  {
            sc = new Scanner(context.openFileInput(fileList[0]));
        } catch (FileNotFoundException e) {
            MoveAssets ma = new MoveAssets(context);
        }

        for(String s : this.fileList)  {

            int difficulty = 0;

            try {
                sc = new Scanner((context.openFileInput(s)));

                String line = sc.nextLine();
                String data[] = line.split(";");

                difficulty = Integer.parseInt(data[0]);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(difficulty == 0)  {
                list.add(s);
            }
            sc.close();
        }

        return list.toArray(new String[list.size()]);
    }

    public String[] getHardFileList()  {
        ArrayList<String> list = new ArrayList<String>();

        try  {
            sc = new Scanner(context.openFileInput(fileList[0]));
        } catch (FileNotFoundException e) {
            MoveAssets ma = new MoveAssets(context);
        }

        for(String s : this.fileList)  {
            int difficulty = 0;

            try {
                sc = new Scanner((context.openFileInput(s)));

                String line = sc.nextLine();
                String data[] = line.split(";");

                difficulty = Integer.parseInt(data[0]);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(difficulty == 1)  {
                list.add(s);
            }
            sc.close();
        }

        return list.toArray(new String[list.size()]);
    }

    public String getRandomFile()  {
        int r = (int)(Math.random() * (this.fileList.length ));
        return (this.fileList[r]);
    }

    public String getRandomFile(String[] list)  {
        int r = (int)(Math.random() * (list.length ));
        return (list[r]);
    }
}
