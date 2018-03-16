package grid;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Grid {

	private int m,n;
	private ArrayList<ArrayList<Tile>> gridArray = new ArrayList<ArrayList<Tile>>();
	private int tileID = 0;
	private JPanel gridPanel;
	private Long bestTime;

	public Long getBestTime() {
		return bestTime;
	}

	public void setBestTime(Long bestTime) {
		this.bestTime = bestTime;
	}

	public Grid(int m, int n, long bestTime)  {
		this.m = m;
		this.n = n;
		this.bestTime = bestTime;
		
		createGrid();
	}
	
	private void createGrid()  {
		for(int i = 0; i < this.m; i++)  {
			
			this.gridArray.add(new ArrayList<Tile>());
			
			for(int j = 0; j < this.n; j++)  {
				gridArray.get(i).add(new Tile(i, j, tileID++));
			}
		
		}
		
		createGridPanel();
	}
	
	private void createGridPanel()  {
		
		this.gridPanel = new JPanel(new GridLayout(this.m, this.n));
		this.gridPanel.setBackground(Color.DARK_GRAY);
		
		for(int i = 0; i < this.gridArray.size(); i++)  {
			for(int j = 0; j < this.gridArray.get(i).size(); j++)  {
				this.gridPanel.add(this.gridArray.get(i).get(j).getTile());
			}
		}
		
		gridPanel.revalidate();
		gridPanel.repaint();
	}
	
	public void setDimension(int m, int n)  {
		//This doesn't work yet...may not implement anyway
		
		/*System.out.println(m);
		if(m < this.m)  {
			//for(int i = this.m-1; i > m; i--)  {
				this.gridArray.get(0).remove(m-1);
			//}
		}
		else if(m > this.m)  {
			
			for(int i = this.m; i < m; i++)  {
				this.gridArray.get(i).add(new Tile(i, m, tileID++));
			}
		}
		
		this.m = m;
		
		if(n < this.n)  {
			for(int i = this.n-1; i > n; i--)  {
				this.gridArray.remove(i);
				tileID--;
			}
		}
		else if(n > this.n)  {
			
			for(int i = this.n; i < n; i++)  {
				this.gridArray.get(i).add(new Tile(i, n, tileID++));
			}
		}
		
		this.n = n;
		
		createGridPanel();*/
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

	public ArrayList<ArrayList<Tile>> getGridArray() {
		return this.gridArray;
	}

	public void setGridArray(ArrayList<ArrayList<Tile>> gridArray) {
		this.gridArray = gridArray;
		createGridPanel();
	}
	
	public JPanel getGridPanel() {
		return this.gridPanel;
	}

	public void setGridPanel(JPanel gridPanel) {
		this.gridPanel = gridPanel;
	}
}
