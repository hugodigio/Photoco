package ihm;

import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Fenetre extends JFrame{
	int current_layout;
	public Fenetre() {
		current_layout = -1;
		setTitle("Bienvenue sur Photoco !");
		setBounds(0,0,800,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setFocusable(true);
		
		/* Pour que le theme corresponde au theme de Windows */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		setVisible(true);
	}
	
	public void change(int layout) {
		if(current_layout != -1 && layout != current_layout) {
			System.out.println("efface tout !");
			removeAll();
		}
		//layout Login
		if (layout == 1) {
			new LoginMenu(this);
		} 
		//layout admin
		if (layout == 2) {
			new AdminMenu(this);
		}
		current_layout = layout;
		setVisible(true);
	}
}
