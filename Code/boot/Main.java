package boot;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import startup.StartupWindow;

public class Main {
	public static void main(String args[])  {
		UIManager.put("ComboBox.background", new ColorUIResource(Color.DARK_GRAY));
        UIManager.put("JTextField.background", new ColorUIResource(Color.DARK_GRAY));
        UIManager.put("ComboBox.selectionBackground", new ColorUIResource(Color.DARK_GRAY));
        UIManager.put("ComboBox.selectionForeground", new ColorUIResource(Color.ORANGE));
        UIManager.put("ComboBox.border", BorderFactory.createEmptyBorder());
        UIManager.put("ComboBox.buttonDarkShadow", new ColorUIResource(Color.ORANGE));
        UIManager.put("ComboBox.buttonShadow", new ColorUIResource(Color.BLACK));
        UIManager.put("ComboBox.buttonHightlight", new ColorUIResource(Color.BLACK));
        
        JFrame frame = new JFrame("Nonograms");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		new StartupWindow("Nonograms", 1040, 800, frame);
		
	}
}
