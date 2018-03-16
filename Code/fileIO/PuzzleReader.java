package fileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import grid.CurtainTile;
import grid.Grid;
import grid.Tile;

public class PuzzleReader {
	
	private String fileLocation;
	private ArrayList<ArrayList<Tile>> gridArray = new ArrayList<ArrayList<Tile>>();
	private Grid grid;
	private ArrayList<ArrayList<Integer>> puzzleArray = new ArrayList<ArrayList<Integer>>();
	private int m;
	private int n;
	private int columnCurtainSize;
	private int rowCurtainSize;
	private long bestTime;
	
	public PuzzleReader(String fileLocation)  {
		this.fileLocation = fileLocation;
		System.out.println(fileLocation);
		processPuzzleArray();
	}
	
	private void processPuzzleArray()  {
		try {
			Scanner sc = new Scanner(new File(this.fileLocation));
				
			String line = sc.nextLine();
			String data[] = line.split(";");
			
			bestTime = Long.parseLong(data[0]);
			
			String dimensions[] = data[1].split(",");
			
			m = Integer.parseInt(dimensions[0]);
			n = Integer.parseInt(dimensions[1]);
			
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
		
		ArrayList<ArrayList<Integer>> rows = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> columns = new ArrayList<ArrayList<Integer>>();
		
		for(int i = 0; i < m; i++)  {
			boolean previous = false;
			int count = 0;
			
			ArrayList<Integer> currentRow = new ArrayList<Integer>();
			
			for(int j = 0; j < puzzleArray.get(i).size(); j++)  {
				
				boolean current = false;
				
				if(this.puzzleArray.get(i).get(j) == 1)  {
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
				
				if(this.puzzleArray.get(j).get(i) == 1)  {
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
		
		/*
		 * 
		 * Create Grid Curtains
		 * 
		 */
		
		this.grid = new Grid(m + 1, n + 1, bestTime);
		
		this.gridArray.add(new ArrayList<Tile>());
		
		for(int i = 0; i < n + 1; i++)  {
			this.gridArray.get(0).add(new CurtainTile(0));
		}
			
		for(int i = 0; i < this.columnCurtainSize; i++)  {
			
			for(int j = 1; j < n + 1; j++)  {
				
				if(i < columns.get(j-1).size())  {
					String string = "";
					if(columns.get(j-1).get(i) != 0)  {
						string = (columns.get(j-1).get(i)).toString();
					}
					
					this.gridArray.get(0).get(j).setTextVertical(string);
				}
		
			}
		}
		
		for(int i = 1; i < m + 1; i++)  {
			this.gridArray.add(new ArrayList<Tile>());
			this.gridArray.get(i).add(new CurtainTile(0));
		}
		
		for(int i = 1; i < m + 1; i++)  {
			for(int j = 0; j < this.rowCurtainSize; j++)  {
				String string = this.gridArray.get(i).get(0).getText();
				
				if(j < rows.get(i-1).size())  {
					if(rows.get(i-1).get(j) != 0)  {
						string = rows.get(i-1).get(j).toString() + "  " + string;
					}
				}
				
				this.gridArray.get(i).get(0).setText(string);
			}
		}
		
		
		for(int i = 1; i < (m + 1); i++)  {
			for(int j = 1; j < puzzleArray.get(i - 1).size()+1; j++)  {
				if(this.puzzleArray.get(i - 1).get(j - 1) == 1)  {
					this.gridArray.get(i).add(new Tile(true));
				}
				else  {
					this.gridArray.get(i).add(new Tile(false));
				}
			}
		}
		
		this.grid.setGridArray(this.gridArray);
	}
	

	public ArrayList<ArrayList<Tile>> getGridArray() {
		return this.gridArray;
	}  
	
	public Grid getGrid() {
		return this.grid;
	}
	
}
