package startup;

import java.util.ArrayList;

import javax.swing.JPanel;

public class MenuGrid {
	
	ArrayList<JPanel> panelList = null;
	int gridHeight = 0;
	int gridWidth = 0;
	
	public MenuGrid(ArrayList<JPanel> panelList, int gridHeight, int gridWidth) {
		this.panelList = panelList;
		this.gridHeight = gridHeight;
		this.gridWidth = gridWidth;
	}

	public ArrayList<JPanel> getPanelList() {
		return panelList;
	}

	public void setPanelList(ArrayList<JPanel> panelList) {
		this.panelList = panelList;
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
	}
	
	
}
