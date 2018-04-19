package com.example.bensalitros.nonograms;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bensalitros.nonograms.fileIO.FileListReader;
import com.example.bensalitros.nonograms.fileIO.PuzzleReader;
import com.example.bensalitros.nonograms.fileIO.PuzzleWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> gridList = new ArrayList<String>();
    List<String> topCurtainList = new ArrayList<String>();
    List<String> sideCurtainList = new ArrayList<String>();
    ArrayList<Cell> cellList = new ArrayList<Cell>();
    ArrayList<Integer> puzzleArray = new ArrayList<Integer>();

    int numColumns = 0;
    int numElements = 0;

    Context context;
    GridView gv = null;
    TextView bestTimeLabel = null;
    Chronometer timerLabel = null;
    ImageButton menuButton = null;
    ImageButton backButton = null;
    GridView topCurtain = null;
    GridView sideCurtain = null;

    String fileName;

    long startTime;
    long bestTime;

    String difficulty = "EASY";
    int puzzleDifficulty = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        loadStartupScreen();



    }

    public void loadStartupScreen()  {
        setContentView(R.layout.start_screen);

        //Load Startup Screen

        //Startup Resource variables
        final Spinner difficultySpinner = (Spinner) findViewById(R.id.difficultySpinner);
        ImageButton startBtn = (ImageButton) findViewById(R.id.startBtn);
        Button helpBtn = (Button) findViewById(R.id.help);

        //Intialize difficulty spinner
        List<String> diffList = new ArrayList<String>();
        diffList.add("EASY");
        diffList.add("HARD");
        diffList.add("RANDOM");

        ArrayAdapter<String> diffAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, diffList);
        diffAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(diffAdapter);
        difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                difficulty = difficultySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGameView();
            }

        });

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpView();
            }

        });
    }

    public void helpView()  {
        setContentView(R.layout.help);

        ImageButton backBtn = (ImageButton) findViewById(R.id.backButton);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadStartupScreen();
            }

        });

        WebView wv = (WebView) findViewById(R.id.helpText);

        String help;

        InputStream inputStream = context.getResources().openRawResource(R.raw.helpfile);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {

        }

        help = text.toString();

        wv.loadDataWithBaseURL("", help, "text/html", "utf-8", "");


    }

    public void loadGameView()  {
        //Main Resource variables
        setContentView(R.layout.activity_main);

        gv = (GridView) findViewById(R.id.grid);
        bestTimeLabel = (TextView) findViewById(R.id.bestTimeLabel);
        timerLabel = (Chronometer) findViewById(R.id.timerLabel);
        menuButton = (ImageButton) findViewById(R.id.menuButton);
        backButton = (ImageButton) findViewById(R.id.backButton);
        topCurtain = (GridView) findViewById(R.id.topCurtain);
        sideCurtain = (GridView) findViewById(R.id.sideCurtain);

        timerLabel.setTextColor(getResources().getColor(android.R.color.white));
        bestTimeLabel.setTextColor(getResources().getColor(android.R.color.white));

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(MainActivity.this, menuButton);

                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getTitle().equals("New Game")) {
                            newGame();
                        }
                        if (item.getTitle().equals("Restart")) {
                            restart();
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadStartupScreen();
            }

        });

        //Load game
        newGame();

        //Adapters

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Cell cell = cellList.get((int) id);
                cell.setClicked(!cell.isClicked());

                if (cell.isClicked()) {
                    v.setBackgroundColor(Color.BLACK);
                } else {
                    v.setBackgroundColor(Color.WHITE);
                }

                //If winner, display toast, delay 1.5s and load new game
                if (gridCompare()) {
                    timerLabel.stop();

                    long elapsedTime = System.currentTimeMillis() - startTime;

                    if(elapsedTime < bestTime)  {

                        bestTimeLabel.setText("best time: " + createTime(elapsedTime) +
                                "        time: ");
                        PuzzleWriter pw = new PuzzleWriter(elapsedTime, puzzleDifficulty, fileName, context);

                    }

                    Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();


                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 2s = 2000ms
                            newGame();
                        }
                    }, 2000);

                }

            }

        });
    }

    private class GridAdapter extends BaseAdapter {

        Context context;

        public GridAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return gridList.size();
        }

        @Override
        public Object getItem(int i) {
            return gridList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView text = new TextView(this.context);

            text.setText(gridList.get(i));

            text.setGravity(Gravity.CENTER);

            text.setBackgroundColor(Color.WHITE);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            text.setWidth(width/numColumns);
            text.setHeight(width/numColumns);
            text.setPadding(2,2,2,2);

            return text;
        }
    }

    private class TopCurtainAdapter extends BaseAdapter {

        Context context;

        public TopCurtainAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return topCurtainList.size();
        }

        @Override
        public Object getItem(int i) {
            return topCurtainList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView text = new TextView(this.context);

            text.setText(topCurtainList.get(i));

            text.setGravity(Gravity.CENTER);

            text.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
            Typeface tf = Typeface.createFromAsset(getAssets(), "font/impact.ttf");
            text.setTypeface(tf);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            text.setWidth(width / numColumns);

            return text;
        }
    }

    private class SideCurtainAdapter extends BaseAdapter {

        Context context;

        public SideCurtainAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return sideCurtainList.size();
        }

        @Override
        public Object getItem(int i) {
            return sideCurtainList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView text = new TextView(this.context);

            text.setText(sideCurtainList.get(i));

            text.setGravity(Gravity.CENTER);

            text.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
            Typeface tf = Typeface.createFromAsset(getAssets(), "font/impact.ttf");
            text.setTypeface(tf);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            text.setHeight(width / numColumns);
            text.setPadding(2,2,2,2);

            return text;
        }
    }

    public boolean gridCompare() {
        boolean winner = true;

        for (int i = 0; i < numElements; i++) {
            if (cellList.get(i).getValue() != puzzleArray.get(i)) {
                return false;
            }
        }

        return winner;
    }

    public void newGame() {
        String[] fileList = new String[0];
        try {
            fileList = getAssets().list("Puzzles");
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileListReader flr = new FileListReader(context);
        String temp[];
        String s = flr.getRandomFile();

        if(difficulty.equals("EASY"))  {
            temp = flr.getEasyFileList();
            s = flr.getRandomFile(temp);
        }
        else if(difficulty.equals("HARD"))  {
            temp = flr.getHardFileList();
            s = flr.getRandomFile(temp);
        }
        else  {
            temp = fileList;
            s = flr.getRandomFile(temp);
        }

        while(s.equals(fileName))  {
            s = flr.getRandomFile(temp);
        }

        fileName = s;


        launchGame();
    }

    public void restart()  {
        launchGame();
    }

    public void launchGame() {

        PuzzleReader pr = new PuzzleReader(this, fileName);
        puzzleDifficulty = pr.getDifficulty();
        puzzleArray = pr.getPuzzleArray();

        ArrayList<String> rowClues = pr.getRowClues();
        ArrayList<String> columnClues = pr.getColumnClues();

        numColumns = pr.getN();
        numElements = puzzleArray.size();
        bestTime = pr.getBestTime();

        gridList.clear();
        cellList.clear();
        topCurtainList.clear();
        sideCurtainList.clear();

        if(bestTime == 0)  {
            bestTime = 900000000;
            bestTimeLabel.setText("best time: " + "     "+
                    "         time: ");
        }
        else  {
            bestTimeLabel.setText("best time: " + createTime(bestTime)+
                    "       time: ");
        }


        //Create top curtain
        for (int i = 0; i < numColumns; i++) {
            topCurtainList.add(columnClues.get(i));
        }

        //Create side curtain
        for (int i = 0; i < pr.getM(); i++) {
            sideCurtainList.add(rowClues.get(i));
        }

        //Create game grid
        for (int i = 0; i < numElements; i++) {
            gridList.add("");
            cellList.add(new Cell((long) i));
        }

        topCurtain.invalidate();
        sideCurtain.invalidate();
        gv.invalidate();

        topCurtain.setNumColumns(numColumns);
        sideCurtain.setNumColumns(1);
        gv.setNumColumns(numColumns);

        topCurtain.setAdapter(new TopCurtainAdapter(this));
        sideCurtain.setAdapter(new SideCurtainAdapter(this));
        gv.setAdapter(new GridAdapter(this));

        startTime = System.currentTimeMillis();
        timerLabel.setBase(SystemClock.elapsedRealtime());
        timerLabel.stop();
        timerLabel.start();
    }

    private String createTime(Long time)  {
        StringBuilder sb = new StringBuilder();

        int timeElapsed = (int) (time/1000);

        Integer hours = timeElapsed / 3600;
        Integer remainder = timeElapsed % 3600;
        Integer minutes = remainder / 60;
        Integer seconds = remainder % 60;

        if(hours > 0)  {
            sb.append(hours.toString()).append(":");
        }
        sb.append(minutes.toString());
        sb.append(":");
        if(seconds < 10)  {
            sb.append("0");
        }
        sb.append(seconds.toString());

        return sb.toString();
    }

}
