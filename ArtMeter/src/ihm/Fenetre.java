package ihm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import accesBDD.BDaccess;
import classes.Photographe;

@SuppressWarnings("serial")
public class Fenetre extends JFrame{
	int current_layout;
	boolean admin;
	JButton retourmenu;
	static Image backgroundImage;
	
	public static final int MENU_LOGIN = 1;
	public static final int MENU_ADMIN = 2;
	public static final int MENU_RECHERCHE = 3;
	public static final int MENU_MODIFICATION = 4;
	public static final int MENU_AJOUTER_MODIFIER = 5;
	public static final int MENU_RESULTAT_RECHERCHE = 6;
	
	
	public Fenetre() {
		current_layout = -1;
		admin = false;
		setTitle("Bienvenue sur Photoco !");
		setBounds(0,0,1024,576);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setFocusable(true);
		
		/* Pour que le theme corresponde au theme de Windows */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		//icon fenetre
		ImageIcon img = new ImageIcon("./src/ihm/icon.png");
		setIconImage(img.getImage());
			
		setVisible(true);
	}
	
	public void change(int layout, ArrayList<Photographe> p, BDaccess bd) {
		if(current_layout != -1 && layout != current_layout) {
			System.out.println("efface tout !");
			getContentPane().removeAll();
			getContentPane().repaint();
			//gestion du bouton de retour
			if (layout != MENU_LOGIN) {
				if((layout == Fenetre.MENU_RECHERCHE && admin == false) || (layout == Fenetre.MENU_ADMIN)) {
					retourmenu = new JButton("Deconnexion");
					add(new Bouton("Deconnexion"),BorderLayout.NORTH);
				} else {
					add(new Bouton("Retourner au Menu"),BorderLayout.NORTH);
				}
				System.out.println("ajout bouton retour");
				retourmenu.addActionListener(new Retour(admin,this,layout));
			}
		}
		
		switch(layout) {
		case MENU_LOGIN:
			new LoginMenu(this);
			break;
		
		case MENU_ADMIN:
			new AdminMenu(this);
			break;
			
		case MENU_MODIFICATION:
			new ModificationMenu(this);
			break;
			
		case MENU_AJOUTER_MODIFIER:
			if(bd != null) {
				if(p != null) {
					new AjouterModifierMenu(this, p.get(0), bd);
				}else {
					new AjouterModifierMenu(this, bd);
				}
				
			}
			break;
			
		case MENU_RECHERCHE:
			new SearchMenu(this);
			break;
			
		case MENU_RESULTAT_RECHERCHE:
			new ResultMenu(this, p.get(0));
			break;
		}
		current_layout = layout;
		setVisible(true);
	}
	
	public void change(int layout, BDaccess bd) {
		change(layout,null,bd);
	}
	
	public void change(int layout, ArrayList<Photographe> p) {
		change(layout,p,null);
	}
	
	public void change(int layout) {
		change(layout,null,null);
	}
	
	public void retourmenu(boolean admin, Fenetre fenetre, int layout) {
		System.out.println("layout "+layout + " admin ? "+admin);
		if ((layout == Fenetre.MENU_RECHERCHE && admin == false) || (layout == Fenetre.MENU_ADMIN)){
			fenetre.change(Fenetre.MENU_LOGIN);
		} else if (admin) {
			fenetre.change(Fenetre.MENU_ADMIN);
		} else {
			fenetre.change(Fenetre.MENU_RECHERCHE);
		}
	}
	
	public void retourmenu() {
		retourmenu(admin, this,current_layout);
	}
	
	private class Bouton extends JPanel{
		public Bouton(String str) {
			retourmenu = new JButton(str);
			setLayout(new FlowLayout(FlowLayout.LEFT));
			add(retourmenu);
		}
	}
	
	private class Retour implements ActionListener{
		boolean admin;
		Fenetre fenetre;
		int layout;
		
		public Retour(boolean admin, Fenetre fenetre, int layout) {
			this.admin = admin;
			this.fenetre = fenetre;
			this.layout = layout;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			retourmenu(admin,fenetre,layout);
		}
		
	}
}
