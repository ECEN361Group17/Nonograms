package fileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import grid.Grid;

public class PuzzleWriter {
	
	private Grid grid;
	private String filePath;
	private ArrayList<ArrayList<Integer>> puzzleArray;
	private int m, n;
	
	public PuzzleWriter(Grid grid, String filePath)  {
		this.grid = grid;
		this.filePath = filePath;
		
		writePuzzleString();
	}
	
	private void writePuzzleString()  {
		
		this.puzzleArray  = new ArrayList<ArrayList<Integer>>();
		StringBuilder sb = new StringBuilder();
		
		//Format: highscore (<--this could be a date/time type...just a number for now); m; n;
		//		  row_1, (comma separated values)
		//		  ...,
		//		  row_n
		
		sb.append(this.grid.getBestTime()).append(";");		
		
		try {
			Scanner sc = new Scanner(new File(this.filePath));
				
			String line = sc.nextLine();
			String data[] = line.split(";");
			
			String dimensions[] = data[1].split(",");
			
			this.m = Integer.parseInt(dimensions[0]);
			this.n = Integer.parseInt(dimensions[1]);
			
			int i = 0;
			
			while(sc.hasNext())  {
				line = sc.nextLine();
				data = line.split(",");
				
				this.puzzleArray.add(new ArrayList<Integer>());
				
				for(int j = 0; j < n; j++)  {
					this.puzzleArray.get(i).add(Integer.parseInt(data[j]));
				}
				
				i++;
			}
			
			sc.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sb.append(m).append(",").append(n).append(";").append(System.lineSeparator());
		
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
			sb.append(System.lineSeparator());
		}
		
		//System.out.println(sb.toString());
		
		File file = new File(filePath);
		
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(sb.toString());
			fw.flush();
			fw.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
