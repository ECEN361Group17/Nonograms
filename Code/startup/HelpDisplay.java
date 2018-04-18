package startup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import static javax.swing.ScrollPaneConstants.*;

public class HelpDisplay {
	
	public HelpDisplay()  {
		createHelp();
	}
	
	private void createHelp(){
		JFrame frame = new JFrame("Nonograms - Help");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(600, 800));
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JTextPane textArea = new JTextPane();
		JScrollPane jsp = new JScrollPane(textArea);
		jsp.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		
		textArea.setEditable(false);
		textArea.setContentType("text/html");
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		textArea.setText(helpString());
		textArea.addHyperlinkListener(new HyperlinkListener()  {

			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				// TODO Auto-generated method stub
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		            String url = e.getURL().toString();
		            try {
						Desktop.getDesktop().browse(URI.create(url));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
			}
			
		});
		
		textArea.setPreferredSize(new Dimension(600, 800));
		textArea.setCaretPosition(0);
		
		mainPanel.add(jsp);
		
		try {
			Image frameIcon = ImageIO.read(new File("images/frameIcon.png"));
			frame.setIconImage((frameIcon.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		frame.setContentPane(mainPanel);
		frame.pack();
		frame.setVisible(true);
		
		
	}
	
	private String helpString()  {
		String help = null;
		
		try {
			help = "<html> <body style=\"font-family:calibri;\" > <br> <br>"
					+ "<h1>Nonograms - Help</h1> <br>"
					+ "<h2>What is a nonogram?</h2> <br>"
					+ "A nonogram is tile-based puzzle solved by using the provided numeric clues for the rows and columns of the game grid.  "
					+ "Tiles are selected so that they conform to the clues for their particular column and row.  "
					+ "Clues may represent a single tile or groups of adjacent tiles in that row or column.  "
					+ "Multiple clues in a row or column represent a division of groups by at least one empty tile.  <br><br>"
					+ " <div align=\"center\"><img src=\"file:/" 
					+ new File("images/help/clues.png").getCanonicalPath().replace("  ","%20").replace("\\","/") +"\"width=362 height=260></img><br>"
					+ "<h5><i>An example of puzzle clues.  In the second column there is a group of three tiles, and two groups of two tiles.  Each of these "
					+ "groups must be separated by at least one empty cell.</i></h5></div><br>"
					+ " <div align=\"center\"><img src=\"file:/" 
					+ new File("images/help/cluesol.png").getCanonicalPath().replace("  ","%20").replace("\\","/") +"\"width=138 height=350></img><br>"
					+ "<h5><i>A possible solution to the column presented above.</i></h5></div><br>"
					+ "<h2>How to play?</h2> <br>"
					+ "In the main menu, a new game is started by choosing the difficulty level from the drop down and pressing play.  <br><br>"
					+ " <div align=\"center\"><img src=\"file:/" 
					+ new File("images/help/level.png").getCanonicalPath().replace("  ","%20").replace("\\","/") +"\"width=350 height=176></img><br>"
					+ "<h5><i>Select difficulty and press play.</i></h5></div><br>"
					+ "A new puzzle will open.  At the start of a new puzzle, all tiles are empty (white) by default.  <br><br>"
					+ " <div align=\"center\"><img src=\"file:/" 
					+ new File("images/help/emptygrid.png").getCanonicalPath().replace("  ","%20").replace("\\","/") +"\"width=611 height=494></img><br>"
					+ "<h5><i>Empty game grid.</i></h5></div><br><br>"
					+ "Empty tiles are filled (black) by simply clicking on the tile.  "
					+ "A previously filled tile can be cleared by simply clicking on it again.  "
					+ "A puzzle is solved when the game grid matches all of the numeric clues provided above "
					+ "each row and to the side of each column.  <br><br>"
					+ " <div align=\"center\"><img src=\"file:/" 
					+ new File("images/help/solvedgrid.png").getCanonicalPath().replace("  ","%20").replace("\\","/") +"\"width=611 height=494></img><br>"
					+ "<h5><i>A solved game example.</i></h5></div><br><br>"
					+ "For more information on nonograms and puzzle-solving strategies, visit:  <br>"
					+ "<i><a href = https://en.wikipedia.org/wiki/Nonogram>https://en.wikipedia.org/wiki/Nonogram</a></i><br><br><br>"
					+ "</html>";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return help;
	}
	
}
