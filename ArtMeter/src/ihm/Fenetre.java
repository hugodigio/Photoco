package ihm;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Fenetre extends JFrame{
	
	public Fenetre() {
		setTitle("Bienvenue sur Photoco !");
		setBounds(0,0,800,600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
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
		if (layout == 1) {
			new LoginMenu(this);
			setVisible(true);
		}
	}
}
