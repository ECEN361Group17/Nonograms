package grid;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class Tile {
	
	private int x, y, tileID;
	private boolean tileActive;
	private Component tile;
	private Boolean gameTile;

	public Tile()  {
		
	}
	
	public Tile(boolean tileActive)  {
		this.tileActive = tileActive;
		
		createTile();
	}
	
	public Tile(int x, int y, int tileID) {
		this.x = x;
		this.y = y;
		this.tileID = tileID;
		this.tileActive = false;
		
		createTile();
	}
	
	public boolean isTileActive() {
		return tileActive;
	}

	public void setTileActive(boolean tileActive) {
		this.tileActive = tileActive;
	}

	private void createTile()  {
		this.tile = new JButton();
		this.tile.setBackground(Color.WHITE);
		
		this.tile.setPreferredSize(new Dimension(25,25));
		((JButton) this.tile).setBorder(new LineBorder(Color.BLACK));
		
		this.gameTile = true;
		
	}
	
	private void changeDimension(Dimension dimension)  {
		this.tile.setPreferredSize(dimension);
		this.tile.repaint();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getTileID() {
		return tileID;
	}

	public void setTileID(int tileID) {
		this.tileID = tileID;
	}
	
	public Component getTile() {
		return tile;
	}

	public void setTile(JButton tile) {
		this.tile = tile;
	}
	
	public Boolean isGameTile()  {
		return this.gameTile;
	}
	
	public void setText(String text)  {
		((JLabel) this.tile).setText(text);
	}
	
	public String getText()  {
		return ((JLabel) this.tile).getText();
	}

	public void setTextVertical(String string) {
		// TODO Auto-generated method stub
		
	}
}
