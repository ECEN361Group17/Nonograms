package startup;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import boot.Display;


public class StartupWindow {
	
	private int width = 0;
	private int length = 0;
	private String[] diffList = {"EASY", "HARD", "RANDOM"};
	private String difficulty = "EASY";
	
	//Boots up the menu as well as calling methods to initialize the various buttons.
	public StartupWindow(String title, int width, int length, JFrame frame) {
		
		this.width = width;
		this.length = length;
		
		frame.setSize(this.width, this.length);
		
		JPanel panel = InitPanel();
		panel.setLayout(null);
		
		JButton start = InitJButton("Play!", 550, 550, 170, 70);
		start.addActionListener(new ActionListener()  {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				EventQueue.invokeLater(new Runnable()  {
					public void run()  {
						new Display(frame, difficulty, panel);
					}
				});
				
			}
			
		});
		try {
			Image playIcon = ImageIO.read(new File("images/forwardButton.png"));
			start.setIcon(new ImageIcon(playIcon.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH)));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		start.setToolTipText("Click to play");
		
		JButton help = InitJButton("?", 950, 685, 60, 60);
		help.setToolTipText("Help");
		help.addActionListener(new ActionListener()  {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new HelpDisplay();
			}
			
		});
		
		panel.add(InitComboBox(diffList, 370, 560, 190, 50));
		panel.add(help);
		panel.add(start);
		
		MenuGrid grid = makeGrid(panel);
		
		frame.setContentPane(panel);
		frame.setVisible(true);
		frame.validate();
		
		try {
			Image frameIcon = ImageIO.read(new File("images/frameIcon.png"));
			frame.setIconImage((frameIcon.getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		makeLogo(grid);
	}
	
	//This will produce the written logo on the Nonogram board on the menu screen.
	private void makeLogo(MenuGrid grid) {	
		ArrayList<JPanel> panelList = grid.getPanelList();
		
		String logo = "Nonograms";
		int firstRowStart = grid.getGridWidth() + 2;
		int secondRowStart = grid.getGridWidth()*7 + 1;
		
		int cornerStart = firstRowStart;
		
		for (int i = 0; i < logo.length(); i++) {
			if (i == 4) {
				cornerStart = secondRowStart;
			}
			cornerStart = chooseLetter(panelList, logo.charAt(i), cornerStart, grid.getGridWidth());
		}
		
	}

	//Chooses which letter will be written and calls writeLetter.
	private int chooseLetter(ArrayList<JPanel> panelList, char letter, int cornerStart, int gridWidth) {
		int[] N = {gridWidth*4, gridWidth*3, gridWidth*2, gridWidth*1, 0, 1, gridWidth*1+1, gridWidth*2+2, gridWidth*3+3, gridWidth*4+3, gridWidth*4+4, gridWidth*3+4, gridWidth*2+4, gridWidth*1+4, 4};
		int[] O = {1, 0, gridWidth*1, gridWidth*2, gridWidth*3, gridWidth*4, gridWidth*4+1, gridWidth*4+2, gridWidth*4+3, gridWidth*3+3, gridWidth*2+3, gridWidth*1+3, 3, 2};
		int[] G = {3, 2, 1, 0, gridWidth, gridWidth*2, gridWidth*3, gridWidth*4, gridWidth*4+1, gridWidth*4+2, gridWidth*4+3, gridWidth*3+3, gridWidth*2+3, gridWidth*2+2};
		int[] R = {gridWidth*4, gridWidth*3, gridWidth*2, gridWidth, 0, 1, 2, 3, gridWidth+3, gridWidth*2+3, gridWidth*2+2, gridWidth*2+1, gridWidth*3+2, gridWidth*4+3};
		int[] A = {gridWidth*4, gridWidth*3, gridWidth*2, gridWidth, 0, 1, 2, gridWidth+2, gridWidth*2+2, gridWidth*2+1, gridWidth*3+2, gridWidth*4+2};
		int[] M = {gridWidth*4, gridWidth*3, gridWidth*2, gridWidth, 0, 1, 2, gridWidth+2, gridWidth*2+2, 3, 4, gridWidth+4, gridWidth*2+4, gridWidth*3+4, gridWidth*4+4};
		int[] S = {gridWidth*4, gridWidth*4+1, gridWidth*4+2, gridWidth*3+2, gridWidth*2+2, gridWidth*2+1, gridWidth*2, gridWidth, 0, 1, 2};
		
		letter = Character.toUpperCase(letter);
		switch (letter) {
		case ('N'):
			writeLetter(N, gridWidth, cornerStart, panelList);
		break;
		case ('O'):
			writeLetter(O, gridWidth, cornerStart, panelList);
		cornerStart--;
		break;
		case ('G'):
			writeLetter(G, gridWidth, cornerStart, panelList);
		cornerStart--;
		break;
		case ('R'):
			writeLetter(R, gridWidth, cornerStart, panelList);
		cornerStart--;
		break;
		case ('A'):
			writeLetter(A, gridWidth, cornerStart, panelList);
		cornerStart -= 2;
		break;
		case ('M'):
			writeLetter(M, gridWidth, cornerStart, panelList);
		break;
		case ('S'):
			writeLetter(S, gridWidth, cornerStart, panelList);
		break;
		}
		
		cornerStart += 6;
		
		return cornerStart;
	}
	
	//Called by chooseLetter to turn tiles black according to the array sent over.
	private int writeLetter(int[] letter, int gridWidth, int cornerStart, ArrayList<JPanel> panelList) {
		for (int i = 0; i < letter.length; ++i) {
			try {
				TimeUnit.MILLISECONDS.sleep(15);
				panelList.get(letter[i]+cornerStart).setBackground(Color.BLACK);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		return cornerStart;
	}

	//Initializes a JPanel.
	private JPanel InitPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		return panel;
	}
	
	//Initializes a JComboBox
	private JComboBox<String> InitComboBox(String[] diffList, int horizPosition, int vertPosition, int horizLength, int vertLength) {
		JComboBox<String> difficulties = new JComboBox<String>(diffList);
		difficulties.setUI(new BasicComboBoxUI() {
			
			@Override
			   protected JButton createArrowButton() {
			      JButton btn = new JButton();
			      btn.setBackground(Color.DARK_GRAY);
			      btn.setBorderPainted(false);
				  btn.setFocusPainted(false);
				  btn.setToolTipText("Select difficulty");
			      try {
						Image menuIcon = ImageIO.read(new File("images/dropDown2.png"));
						btn.setIcon(new ImageIcon(menuIcon.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
					} catch (Exception ex) {
						System.out.println(ex);
					}
			      return btn;
			   }
			
			@Override
		    protected ComboPopup createPopup() {
		        BasicComboPopup basicComboPopup = new BasicComboPopup(comboBox);
		        basicComboPopup.setBorder(new LineBorder(Color.ORANGE));
		        
		        return basicComboPopup;
		    }
		});
		
		difficulties.setBounds(horizPosition, vertPosition, horizLength, vertLength);
		difficulties.setBackground(Color.DARK_GRAY);
		difficulties.setForeground(Color.ORANGE);
		difficulties.setFont(new Font("Impact", Font.PLAIN, 40));
		difficulties.setVisible(true);
		
		difficulties.addItemListener(new ItemListener()  {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
			          difficulty = e.getItem().toString();
			          System.out.println(difficulty);
			    }
			}
			
		});
		return difficulties;
	}
	
	//Initializes a JButton
	private JButton InitJButton(String label, int horizPosition, int vertPosition, int horizLength, int vertLength) {
		JButton button = new JButton(label);
		button.setBounds(horizPosition, vertPosition, horizLength, vertLength);
		button.setFont(new Font("Impact", Font.PLAIN, 40));
		button.setForeground(Color.ORANGE);
		button.setBackground(Color.DARK_GRAY);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		
		return button;
	}
	
	//Initializes the menu grid.
	private MenuGrid makeGrid(JPanel panel) {
		ArrayList<JPanel> panelList = new ArrayList<JPanel>();
		int squareLength = 35;
		int origPosition = 95;
		int horizPosition = origPosition;
		int vertPosition = 50;
		int firstColumnOffset = 40;
		
		int gridWidth = 25;
		int gridHeight = 13;
		
		for (int i = 0; i < gridWidth*gridHeight; ++i) {
			JPanel p = new JPanel();
			p.setBackground(Color.WHITE);
			p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			if (i%gridWidth == 0) {
				p.setBounds(horizPosition-firstColumnOffset, vertPosition, squareLength+firstColumnOffset, squareLength);
			}
			else {
				p.setBounds(horizPosition, vertPosition, squareLength, squareLength);
			}
			panelList.add(p);
			panel.add(p);
			
			horizPosition += squareLength;
			
			if ((i+1)%gridWidth == 0) {
				horizPosition = origPosition;
				vertPosition += squareLength;
			}
		}
		MenuGrid grid = new MenuGrid(panelList, gridHeight, gridWidth);
		
		return grid;
	}
	
}
