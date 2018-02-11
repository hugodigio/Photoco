package ihm;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class LoginMenu{
	Fenetre fenetre;
	public LoginMenu(Fenetre fenetre) {
		this.fenetre = fenetre;
		fenetre.setTitle("Bienvenue sur PhotoCo !");
		
		fenetre.getContentPane().add(new Panneau());
	}
	
	private class PanneauImage extends JPanel{
		
		
		
		public PanneauImage(){
			
		}	
		
	}
	
	private class Panneau extends JPanel{
		
		JTextField identifiant;
		JPasswordField motdepasse;
		JLabel erreur;
		
		public Panneau() {
					
			GridBagLayout gbl = new GridBagLayout();
			setLayout(gbl);
			GridBagConstraints gbc = new GridBagConstraints();
			
			/* champs de saisie */
			identifiant = new JTextField(20);
			identifiant.setBackground(Color.BLACK);
			identifiant.setForeground(Color.WHITE);
			motdepasse = new JPasswordField(20);
			motdepasse.setBackground(Color.BLACK);
			motdepasse.setForeground(Color.WHITE);
			
			/* labels, police et bouton*/
			JLabel id = new JLabel("Identifiant:");
			JLabel mdp = new JLabel("Mot de Passe");
			erreur = new JLabel("");
			erreur.setForeground(Color.RED);
			JButton valider = new JButton("Valider");
			JLabel titre = new JLabel("PHOTOCO");
			Font font = new Font("Verdana", Font.BOLD, 12);
			id.setForeground(Color.WHITE);
			id.setFont(font);
			mdp.setForeground(Color.WHITE);
			mdp.setFont(font);
			titre.setForeground(Color.WHITE);
			titre.setFont(new Font("Verdana", Font.PLAIN, 38));
			
			//image de fond
			File [] listefichiers; 
			File dossier = new File("./src/ihm/images"); 
			listefichiers=dossier.listFiles(); 
			for(File f:listefichiers) {
				System.out.println(f);
			}
			Random r = new Random();
			int i = r.nextInt(listefichiers.length);
			try {
				Fenetre.backgroundImage = ImageIO.read(listefichiers[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			/* placement des elements dans le gridbaglayout */
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			add(id,gbc);
			gbc.gridx = 1;
			gbc.gridy = 2;
			add(identifiant,gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.insets = new Insets(25, 0, 0, 0);
			add(mdp,gbc);
			gbc.gridx = 1;
			gbc.gridy = 3;
			add(motdepasse,gbc);
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.gridwidth=2;
			add(valider,gbc);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 2;
			gbc.insets = new Insets(0, 0, 50, 0);
			add(titre,gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(0, 0, 10, 0);
			add(erreur,gbc);
						
			valider.addActionListener(new ValidationButton());
			motdepasse.addKeyListener(new ValidationKey());
		}
		
		public void validation() {
			String id = identifiant.getText();
			@SuppressWarnings("deprecation")
			String mdp = motdepasse.getText();
			
			if(id.equals("admin") && mdp.equals("admin")) {
				//Si l'utilisateur est un ADMIN
				System.out.println("Admin !");
				fenetre.admin=true;
				fenetre.change(Fenetre.MENU_ADMIN);
			}else if (id.isEmpty()||mdp.isEmpty()) {
				erreur.setText("Tout les champs doivent être remplis !");
			}else if(id.equals("admin") && !(mdp.equals("admin"))){
				erreur.setText("Mot de passe incorrect !");
				motdepasse.setText("");
			} else{
				//si l'utilisateur est un utilisateur lambda
				System.out.println("Utilisateur !");
				fenetre.admin = false;
				fenetre.change(Fenetre.MENU_RECHERCHE);
				
			}
		}
		
		private class ValidationButton implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				validation();				
			}
			
		}
		
		private class ValidationKey extends KeyAdapter {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					validation();
				}
			}
			
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			float ratio = 16.0f/9.0f;
			if((float)fenetre.getWidth()/(float)fenetre.getHeight() >= ratio) {
				g.drawImage(Fenetre.backgroundImage, 0, 0, fenetre.getWidth(), (int)(fenetre.getWidth()*(0.5625)),this);	
			}else {
				g.drawImage(Fenetre.backgroundImage, 0, 0, (int)(fenetre.getHeight()*(1.7777777778)), fenetre.getHeight(),this);	
			}
			
			int milieuX = fenetre.getWidth()/2;
			int milieuY = fenetre.getHeight()/2;
			
			Rectangle2D rect = new Rectangle2D.Double(milieuX-200,milieuY-200,400,400);
			g2d.setColor(new Color(0, 0, 0, 200));
			g2d.fill(rect);
			g2d.draw(rect);
		}
	
	}
}
