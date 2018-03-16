package boot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import fileIO.PuzzleReader;
import fileIO.PuzzleWriter;
import fileIO.FileListReader;
import grid.Grid;
import grid.Tile;

public class Display {
	
	private JFrame frame;
	private JPanel mainWindow;
	private JPanel menuBar;
	private JPanel gridPanel;
	private Grid grid;
	private Grid gameGrid;
	private String filePath;
	private JPopupMenu menuPopup;
	private GameTimer gameTimer;
	private long startTime;
	private JLabel bestTimeLabel;
	private JButton menuButton;
	
	public Display()  {
		init();
	}
	
	private void init()  {
		frame = new JFrame("Nonograms");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.DARK_GRAY);
		frame.setPreferredSize(new Dimension(1040, 800));
		
		mainWindow = new JPanel(new BorderLayout());
		mainWindow.setBackground(Color.DARK_GRAY);
		mainWindow.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		gridPanel = new JPanel(new BorderLayout());
		gridPanel.setBackground(Color.DARK_GRAY);
		
		menuBar = new JPanel(new BorderLayout());
		menuBar.setBackground(Color.BLACK);
		
		menuButton = new JButton();
		menuButton.setPreferredSize(new Dimension(24, 25));
		menuButton.setBackground(Color.BLACK);
		menuButton.setBorderPainted(false);
		menuButton.setFocusPainted(false);
		menuButton.addMouseListener(new menuButtonListener());
		
		try {
			Image menuIcon = ImageIO.read(new File("images/menuIcon.png"));
			menuButton.setIcon(new ImageIcon(menuIcon.getScaledInstance(24, 25, java.awt.Image.SCALE_SMOOTH)));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		startTime = System.currentTimeMillis();
		gameTimer = new GameTimer(startTime);
		menuBar.add(menuButton, BorderLayout.WEST);
		menuBar.add(gameTimer, BorderLayout.EAST);
		
		FileListReader flr = new FileListReader("Puzzles/");
		filePath = flr.getRandomFile();
		
		PuzzleReader pr = new PuzzleReader(filePath);
		PuzzleReader prGameFile = new PuzzleReader(filePath);
		
		grid = pr.getGrid();
		gameGrid = prGameFile.getGrid();
		
		bestTimeLabel = new JLabel();
		bestTimeLabel.setBackground(Color.BLACK);
		bestTimeLabel.setForeground(Color.WHITE);
		bestTimeLabel.setFont(new Font("Impact", Font.PLAIN, 14));
		
		if(gameGrid.getBestTime().toString().equals("0"))  {
			bestTimeLabel.setText("                           best time     ");
		}
		else  {
			bestTimeLabel.setText("                           best time     " + createTime(gameGrid.getBestTime()));
		}
		
		menuBar.add(bestTimeLabel, BorderLayout.CENTER);
		
		gridPanel.removeAll();
		
		for(int i = 0; i < grid.getGridArray().size(); i++)  {
			for(int j = 0; j < grid.getGridArray().get(i).size(); j++)  {
				if(grid.getGridArray().get(i).get(j).isGameTile())  {
					grid.getGridArray().get(i).get(j).setTileActive(false);
					grid.getGridArray().get(i).get(j).getTile().addMouseListener((new TileActionListener(grid.getGridArray().get(i).get(j))));
				}
			}
		}
		
		gridPanel.add(grid.getGridPanel(), BorderLayout.CENTER);
		
		mainWindow.add(menuBar, BorderLayout.NORTH);
		mainWindow.add(gridPanel, BorderLayout.CENTER);
		
		frame.setContentPane(mainWindow);
		frame.pack();
		frame.setVisible(true);
		
		try {
			Image frameIcon = ImageIO.read(new File("images/frameIcon.png"));
			frame.setIconImage((frameIcon.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		globalKeyboardListener();
	}
	
	private class menuButtonListener implements MouseListener  {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			menuPopup = new JPopupMenu();
			menuPopup.setBorderPainted(false);
			menuPopup.setFocusable(true);
			menuPopup.setBackground(Color.DARK_GRAY);
			
			menuPopup.add(createMenu());
			
			menuPopup.show(e.getComponent(), 0, 28);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class NewGameListener implements ActionListener  {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			newGame();
			if(menuPopup.isVisible())  {
				menuPopup.setVisible(false);
			}
		}
		
	}
	
	private class RestartListener implements ActionListener  {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			restart();
			if(menuPopup.isVisible())  {
				menuPopup.setVisible(false);
			}
		}
		
	}
	
	private class ExitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(1);
		}
		
	}

	public class TileActionListener implements MouseListener {

		private Tile tile;
		
		public TileActionListener(Tile tile)  {
			this.tile = tile;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if(tile.isTileActive())  {
				this.tile.getTile().setBackground(Color.WHITE);
				this.tile.setTileActive(false);
			}
			else  {
				this.tile.getTile().setBackground(Color.BLACK);
				this.tile.setTileActive(true);
			}
			
			if(gridCompare())  {
				gameTimer.getTimer().stop();
				long gameTime = System.currentTimeMillis() - startTime;
				
				if(gameGrid.getBestTime() != 0)  {
					if(gameGrid.getBestTime() > gameTime)  {
						gameGrid.setBestTime(gameTime);
						new PuzzleWriter(gameGrid, filePath);
					}
				}
				else  {
					gameGrid.setBestTime(gameTime);
					new PuzzleWriter(gameGrid, filePath);
				}
				
				Object[] options = {"New Game", "Play Again", "Exit"};
				
				int n = JOptionPane.showOptionDialog(frame, "You Win!", "Winner!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
				
				if(n == 0)  {
					newGame();
				}
				else if(n == 1)  {
					restart();
				}
				else  {
					System.exit(1);
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		private Boolean gridCompare()  {
			Boolean winner = true;
			
			for(int i = 0; i < grid.getGridArray().size(); i++)  {
				for(int j = 0; j < grid.getGridArray().get(i).size(); j++)  {
					
					if(grid.getGridArray().get(i).get(j).isTileActive() != gameGrid.getGridArray().get(i).get(j).isTileActive())  {
						return false;
					}					
				}
			}
			
			return winner;
		}

	}
	
	private void globalKeyboardListener()  {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		    	  if(KeyEvent.KEY_PRESSED == e.getID())  {
		    		  if(e.getKeyCode() == 113)  {
							newGame();
						}
						else if(e.getKeyCode() == 525)   {
							menuPopup = new JPopupMenu();
							menuPopup.setBorderPainted(false);
							menuPopup.setFocusable(false);
							menuPopup.setBackground(Color.DARK_GRAY);
							menuPopup.add(createMenu());
							
							menuPopup.show(menuButton, 0, 28);
						}
						//System.out.println(e.getKeyCode());
		    	  }
		    	  
		        return false;
		      }
		});
	}
	
	private void newGame()  {
		FileListReader flr = new FileListReader("Puzzles/");
		String temp = flr.getRandomFile();
		
		while(temp.equals(filePath))  {
			temp = flr.getRandomFile();
		}
		filePath = temp;
		
		PuzzleReader pr = new PuzzleReader(filePath);
		PuzzleReader prGameFile = new PuzzleReader(filePath);
		
		grid = pr.getGrid();
		gameGrid = prGameFile.getGrid();
		
		gridPanel.removeAll();
		
		for(int i = 0; i < grid.getGridArray().size(); i++)  {
			for(int j = 0; j < grid.getGridArray().get(i).size(); j++)  {
				if(grid.getGridArray().get(i).get(j).isGameTile())  {
					grid.getGridArray().get(i).get(j).setTileActive(false);
					grid.getGridArray().get(i).get(j).getTile().addMouseListener((new TileActionListener(grid.getGridArray().get(i).get(j))));
				}
			}
		}
		
		if(gameGrid.getBestTime().toString().equals("0"))  {
			bestTimeLabel.setText("                           best time     ");
		}
		else  {
			bestTimeLabel.setText("                           best time     " + createTime(gameGrid.getBestTime()));
		}
		bestTimeLabel.revalidate();
		bestTimeLabel.repaint();
		
		startTime = System.currentTimeMillis();
		gameTimer.setStartTime(startTime);
		gameTimer.getTimer().start();
			
		gridPanel.add(grid.getGridPanel(), BorderLayout.CENTER);
		gridPanel.revalidate();
		gridPanel.repaint();
		
		mainWindow.revalidate();
		mainWindow.repaint();
	
		frame.revalidate();
		frame.repaint();
	}
	
	private void restart()  {
		
		PuzzleReader pr = new PuzzleReader(filePath);
		PuzzleReader prGameFile = new PuzzleReader(filePath);
		
		grid = pr.getGrid();
		gameGrid = prGameFile.getGrid();
		
		gridPanel.removeAll();
		
		for(int i = 0; i < grid.getGridArray().size(); i++)  {
			for(int j = 0; j < grid.getGridArray().get(i).size(); j++)  {
				if(grid.getGridArray().get(i).get(j).isGameTile())  {
					grid.getGridArray().get(i).get(j).setTileActive(false);
					grid.getGridArray().get(i).get(j).getTile().addMouseListener((new TileActionListener(grid.getGridArray().get(i).get(j))));
				}
			}
		}
		
		if(gameGrid.getBestTime().toString().equals("0"))  {
			bestTimeLabel.setText("                           best time     ");
		}
		else  {
			bestTimeLabel.setText("                           best time     " + createTime(gameGrid.getBestTime()));
		}
		bestTimeLabel.revalidate();
		bestTimeLabel.repaint();
		
		startTime = System.currentTimeMillis();
		gameTimer.setStartTime(startTime);
		gameTimer.getTimer().start();
			
		gridPanel.add(grid.getGridPanel(), BorderLayout.CENTER);
		gridPanel.revalidate();
		gridPanel.repaint();
		
		mainWindow.revalidate();
		mainWindow.repaint();		
		
		frame.revalidate();
		frame.repaint();
	}
	
	private JPanel createMenu()  {
		JButton newGame = new JButton("   New Game         ");
		newGame.setBackground(Color.BLACK);
		newGame.setForeground(Color.ORANGE);
		newGame.setFont(new Font("Impact", Font.PLAIN, 20));
		//newGame.setBorderPainted(false);
		newGame.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
		newGame.setFocusPainted(false);		
		newGame.setFocusable(false);
		newGame.addActionListener(new NewGameListener());
		newGame.setHorizontalAlignment(SwingConstants.LEFT);
		
		JButton restart = new JButton("   Restart");
		restart.setBackground(Color.BLACK);
		restart.setForeground(Color.ORANGE);
		restart.setFont(new Font("Impact", Font.PLAIN, 20));
		//restart.setBorderPainted(false);
		restart.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
		restart.setFocusPainted(false);		
		restart.setFocusable(false);
		restart.addActionListener(new RestartListener());
		restart.setHorizontalAlignment(SwingConstants.LEFT);
		
		JButton exit = new JButton("   Exit");
		exit.setBackground(Color.BLACK);
		exit.setForeground(Color.ORANGE);
		exit.setFont(new Font("Impact", Font.PLAIN, 20));
		//exit.setBorderPainted(false);
		exit.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
		exit.setFocusPainted(false);		
		exit.setFocusable(false);
		exit.addActionListener(new ExitListener());
		exit.setHorizontalAlignment(SwingConstants.LEFT);
	
		JPanel menuPopupPanel = new JPanel(new GridLayout(3,1));
		menuPopupPanel.setBackground(Color.BLACK);
		
		menuPopupPanel.add(newGame);
		menuPopupPanel.add(restart);
		menuPopupPanel.add(exit);
		
		return(menuPopupPanel);
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
