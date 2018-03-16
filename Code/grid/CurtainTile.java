package grid;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CurtainTile extends Tile {
	private Integer number;
	private Component tile;
	private Boolean gameTile;

	public CurtainTile (Integer number)  {
		super();
		this.number = number;
		
		createTile();
	}
	
	private void createTile()  {
		this.tile = new JLabel("", SwingConstants.CENTER);
		this.tile.setBackground(Color.DARK_GRAY);
		this.tile.setForeground(Color.ORANGE);
		this.tile.isOpaque();
		this.tile.setFont(new Font("Impact", Font.PLAIN, 12));
		if(this.number != 0)  {
			((JLabel) this.tile).setText(this.number.toString());
			((JLabel) this.tile).setToolTipText(this.number.toString());
		}
		
		this.tile.setPreferredSize(new Dimension(25,25));
		//this.tile.setBorder(new LineBorder(Color.BLACK));
		
		this.gameTile = false;
	}
	
	private void changeDimension(Dimension dimension)  {
		this.tile.setPreferredSize(dimension);
		this.tile.repaint();
	}
	
	public void setText(String text)  {
		((JLabel) this.tile).setText(text.toString());
		((JLabel) this.tile).setToolTipText(text.toString());
	}
	
	public void setTextVertical(String text)  {
		StringBuilder sb = new StringBuilder();
		
		if(getText() != "")  {
			StringBuilder temp = new StringBuilder();
			if(getText().endsWith("</html>"))  {
				temp.append(getText());
				temp.delete(temp.length()-7, temp.length());
				temp.delete(0, 6);
			}
			sb.append("<html>").append(text).append("<br/>").append(temp.toString()).append("</html>");
			
		}
		else  {
			sb.append("<html>");
			sb.append(text).append("</html>");
		}
		
		((JLabel) this.tile).setText(sb.toString());
		((JLabel) this.tile).setToolTipText(sb.toString());
	}
	
	public String getText()  {
		return ((JLabel) this.tile).getText();
	}
	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Component getTile() {
		return tile;
	}

	public void setTile(JLabel tile) {
		this.tile = tile;
	}
	
	public Boolean isGameTile()  {
		return this.gameTile;
	}
}
