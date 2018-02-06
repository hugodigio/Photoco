package ihm;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
		
		
		/* barre de menu 
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
		*/
		
		fenetre.getContentPane().add(new Panneau());
	}
	
	private class PanneauImage extends JPanel{
		
		
		
		public PanneauImage(){
			
		}	
		
	}
	
	private class Panneau extends JPanel{
		
		JTextField identifiant;
		JPasswordField motdepasse;
		private Image backgroundImage;
		
		public Panneau() {
			try {
				backgroundImage = ImageIO.read(new File("./src/ihm/Photo1-Login-TDG_photography_Tapei.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			GridBagLayout gbl = new GridBagLayout();
			setLayout(gbl);
			GridBagConstraints gbc = new GridBagConstraints();
			
			identifiant = new JTextField(20);
			motdepasse = new JPasswordField(20);
			identifiant.setLocation(this.getWidth()/2, this.getHeight()/2);
			JLabel id = new JLabel("Identifiant:");
			JLabel mdp = new JLabel("Mot de Passe:");
			JButton valider = new JButton("Valider");
			JLabel titre = new JLabel("PHOTOCO");
			Font font = new Font("Verdana", Font.BOLD, 12);
			id.setForeground(Color.WHITE);
			id.setFont(font);
			mdp.setForeground(Color.WHITE);
			mdp.setFont(font);
			titre.setForeground(Color.WHITE);
			titre.setFont(new Font("Verdana", Font.PLAIN, 38));
			gbc.gridx = 0;
			gbc.gridy = 1;
			add(id,gbc);
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(identifiant,gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.insets = new Insets(25, 0, 0, 0);
			add(mdp,gbc);
			gbc.gridx = 1;
			gbc.gridy = 2;
			add(motdepasse,gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.gridwidth=2;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			add(valider,gbc);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 2;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(0, 0, 50, 0);
			add(titre,gbc);
			
			for (Component c : getComponents()) {
				c.getParent().setBackground(new Color(255, 255, 255, 100));
			}
							
			
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
				fenetre.change(Fenetre.MENU_ADMIN,null);
			}else if (id.equals("")||id.equals("")) {
				System.out.println("Champ(s) vide");
			}else {
				//si l'utilisateur est un utilisateur lambda
				System.out.println("Utilisateur !");
				fenetre.change(Fenetre.MENU_RECHERCHE, null);
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
			g.drawImage(backgroundImage, 0, 0, fenetre.getWidth(), fenetre.getHeight(),this);
			
			
		}
	
	}
}
