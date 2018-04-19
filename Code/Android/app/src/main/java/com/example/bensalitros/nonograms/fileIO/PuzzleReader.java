package com.example.bensalitros.nonograms.fileIO;

import android.content.Context;
import android.util.Log;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Ben Salitros on 4/12/2018.
 */

public class PuzzleReader {

    private String fileName;
    private Context context;
    private int m;
    private int n;
    private int columnCurtainSize;
    private int rowCurtainSize;
    private long bestTime;
    private int difficulty;
    private ArrayList<Integer> puzzleArray = new ArrayList<Integer>();
    private ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();


    private ArrayList<String> clueArray = new ArrayList<String>();

    private ArrayList<ArrayList<Integer>> rows = new ArrayList<ArrayList<Integer>>();
    private ArrayList<ArrayList<Integer>> columns = new ArrayList<ArrayList<Integer>>();

    private ArrayList<String> rowClues = new ArrayList<String>();
    private ArrayList<String> columnClues = new ArrayList<String>();

    public PuzzleReader(Context context, String fileName) {
        this.fileName = fileName;
        this.context = context;
        processPuzzleArray();
    }

    private void processPuzzleArray() {
        Scanner sc = null;
        try  {
            sc = new Scanner(context.openFileInput(fileName));
        } catch (FileNotFoundException e) {
            MoveAssets ma = new MoveAssets(context);
        }


        try {
            sc = new Scanner(context.openFileInput(fileName));


        String line = sc.nextLine();
        String data[] = line.split(";");

        difficulty = Integer.parseInt(data[0]);

        bestTime = Long.parseLong(data[1]);

        String dimensions[] = data[2].split(",");

        m = Integer.parseInt(dimensions[0]);
        n = Integer.parseInt(dimensions[1]);

        int i = 0;

        while (sc.hasNext()) {
            line = sc.nextLine();
            data = line.split(",");

            this.matrix.add(new ArrayList<Integer>());

            //Create array for grid
            for(int j = 0; j < n; j++)  {
                this.puzzleArray.add(Integer.parseInt(data[j]));
                this.matrix.get(i).add(Integer.parseInt(data[j]));
            }

            i++;

        }

        sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        createClueArray();
    }



    private void createClueArray()  {

        for(int i = 0; i < m; i++)  {
            boolean previous = false;
            int count = 0;

            ArrayList<Integer> currentRow = new ArrayList<Integer>();

            for(int j = 0; j < matrix.get(i).size(); j++)  {

                boolean current = false;

                if(this.matrix.get(i).get(j) == 1)  {
                    current = true;
                }

                if(current)  {
                    count++;
                }
                else if(previous)  {
                    currentRow.add(count);

                    count = 0;
                }
                previous = current;
            }
            if(count != 0)  {
                currentRow.add(count);
            }
            else if(currentRow.size() < 1)  {
                currentRow.add(count);
            }
            rows.add(currentRow);

        }

        for(int i = 0; i < n; i++)  {
            boolean previous = false;
            int count = 0;

            ArrayList<Integer> currentColumn = new ArrayList<Integer>();

            for(int j = 0; j < m; j++)  {

                boolean current = false;

                if(this.matrix.get(j).get(i) == 1)  {
                    current = true;
                }

                if(current)  {
                    count++;
                }
                else if(previous)  {
                    currentColumn.add(count);

                    count = 0;
                }
                previous = current;
            }
            if(count != 0)  {
                currentColumn.add(count);
            }
            else if(currentColumn.size() < 1)  {
                currentColumn.add(count);
            }
            columns.add(currentColumn);

        }

        this.rowCurtainSize = 0;
        this.columnCurtainSize = 0;

        for(ArrayList<Integer> a : rows)  {
            if(a.size() > this.rowCurtainSize)  {
                this.rowCurtainSize = a.size();
            }
        }

        for(ArrayList<Integer> a : columns)  {
            if(a.size() > this.columnCurtainSize)  {
                this.columnCurtainSize = a.size();
            }
        }

        for(ArrayList<Integer> a : rows)  {
            StringBuilder sb = new StringBuilder(a.toString());
            sb.deleteCharAt(sb.length()-1);
            sb.deleteCharAt(0);

            rowClues.add(sb.toString().replaceAll("0",""));
        }

        for(ArrayList<Integer> a : columns)  {
            StringBuilder sb = new StringBuilder(a.toString());
            sb.deleteCharAt(sb.length()-1);
            sb.deleteCharAt(0);

            columnClues.add(sb.toString());
        }

        for(int i = 0; i < columnClues.size(); i++)  {
            String s = columnClues.get(i);
            s = s.replaceAll(",\\s*", "\n\n").replaceAll("0","");
            columnClues.set(i, s);
            for(int j = 0; j < columnClues.size(); j++)  {
                if(columnClues.get(j).length() < s.length()-1)  {
                    columnClues.set(j, "\n" + columnClues.get(j));
                }
            }
        }


    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getColumnCurtainSize() {
        return columnCurtainSize;
    }

    public void setColumnCurtainSize(int columnCurtainSize) {
        this.columnCurtainSize = columnCurtainSize;
    }

    public int getRowCurtainSize() {
        return rowCurtainSize;
    }

    public void setRowCurtainSize(int rowCurtainSize) {
        this.rowCurtainSize = rowCurtainSize;
    }

    public long getBestTime() {
        return bestTime;
    }

    public void setBestTime(long bestTime) {
        this.bestTime = bestTime;
    }

    public ArrayList<Integer> getPuzzleArray() {
        return puzzleArray;
    }

    public void setPuzzleArray(ArrayList<Integer> puzzleArray) {
        this.puzzleArray = puzzleArray;
    }

    public ArrayList<ArrayList<Integer>> getRows() {
        return rows;
    }

    public ArrayList<ArrayList<Integer>> getColumns() {
        return columns;
    }

    public ArrayList<String> getRowClues() {
        return rowClues;
    }

    public ArrayList<String> getColumnClues() {
        return columnClues;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
