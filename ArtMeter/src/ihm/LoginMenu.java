package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;


public class LoginMenu{
	Fenetre fenetre;
	public LoginMenu(Fenetre fenetre) {
		this.fenetre = fenetre;
		
		/* barre de menu */
		JMenuBar barreMenu = new JMenuBar();
		JMenu typeConnexion = new JMenu("Type de connexion");
		JRadioButtonMenuItem bd = new JRadioButtonMenuItem("Base de donnée");
		JRadioButtonMenuItem local = new JRadioButtonMenuItem("Local");
		
		ButtonGroup typeConnexionGroup = new ButtonGroup();
		bd.setSelected(true);
		typeConnexionGroup.add(bd);
		typeConnexionGroup.add(local);
		
		typeConnexion.add(bd);
		typeConnexion.add(local);
		
		barreMenu.add(typeConnexion);
		
		fenetre.setJMenuBar(barreMenu);
		
		/* image de fond */
		fenetre.add(new PanneauLogin());
	}
	
	private class PanneauLogin extends JPanel{
		
		JTextField identifiant;
		JTextField motdepasse;
		
		public PanneauLogin(){
			//pour les placements: grouplayout
			
			identifiant = new JTextField(20);
			motdepasse = new JTextField(20);
			identifiant.setLocation(this.getWidth()/2, this.getHeight()/2);
			JLabel id = new JLabel("Identifiant:");
			JLabel mdp = new JLabel("Mot de Passe:");
			id.setForeground(Color.WHITE);
			mdp.setForeground(Color.WHITE);
			add(id);
			add(identifiant);
			add(mdp);
			add(motdepasse);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			String fichier = "./src/ihm/Photo1-Login-TDG_photography_Tapei.jpg";
			BufferedImage im = null;
			try {
				im = ImageIO.read(new File(fichier));
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawImage(im, 0,0,this.getWidth(),this.getHeight(),null);
			
			
		}
	}
}
