package ihm;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import classes.Photographe;

public class Fenetre extends JFrame{
	int current_layout;
	
	public static final int MENU_LOGIN = 1;
	public static final int MENU_ADMIN = 2;
	public static final int MENU_RECHERCHE = 3;
	public static final int MENU_AJOUTER_MODIFIER_SUPPRIMER = 4;
	public static final int MENU_RESULTAT_RECHERCHE = 5;
	
	
	public Fenetre() {
		current_layout = -1;
		setTitle("Bienvenue sur Photoco !");
		setBounds(0,0,800,600);
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
	
	public void change(int layout, ArrayList<Photographe> p) {
		if(current_layout != -1 && layout != current_layout) {
			System.out.println("efface tout !");
			getContentPane().removeAll();
			getContentPane().repaint();
		}
		//layout Login
		if (layout == MENU_LOGIN) {
			new LoginMenu(this);
		} 
		//layout admin
		if (layout == MENU_ADMIN) {
			new AdminMenu(this);
		}
		if (layout == MENU_AJOUTER_MODIFIER_SUPPRIMER) {
			//TODO a remplir
		}
		if (layout == MENU_RECHERCHE) {
			//TODO a remplir
		}
		if (layout == MENU_RESULTAT_RECHERCHE) {
			//TODO a remplir
		}
		current_layout = layout;
		setVisible(true);
	}
}
