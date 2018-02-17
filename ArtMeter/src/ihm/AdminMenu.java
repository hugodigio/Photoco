package ihm;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminMenu {
	Fenetre fenetre;
	JButton recherche;
	JButton ajouter;
	JButton deconnexion;
	
	public AdminMenu(Fenetre fenetre) {
		this.fenetre = fenetre;
		fenetre.add(new Panneau());
		fenetre.setTitle("Menu administrateur");
		
	}
	
	private class Panneau extends JPanel{
		public Panneau() {
			
			GridBagLayout gbl = new GridBagLayout();
			setLayout(gbl);
			GridBagConstraints gbc = new GridBagConstraints();
			
			recherche = new JButton("Rechercher un photographe");
			ajouter   = new JButton("Ajouter/Modifier/Supprimer un photographe");
			deconnexion = new JButton("Se deconnecter");
			JLabel admin = new JLabel("Connecté en temps qu'administrateur");
			gbc.gridx=0;
			gbc.gridy=0;
			gbc.insets = new Insets(0, 0, 25, 0);
			add(admin,gbc);
			gbc.gridy=1;
			add(recherche,gbc);
			gbc.gridy=2;
			add(ajouter,gbc);
			
			recherche.addActionListener(new changementFenetre(Fenetre.MENU_RECHERCHE));
			ajouter.addActionListener(new changementFenetre(Fenetre.MENU_MODIFICATION));
			
			deconnexion.addActionListener(new changementFenetre(Fenetre.MENU_LOGIN));
		}
		
		private class changementFenetre implements ActionListener{
			int menu;
			public changementFenetre(int menu) {
				this.menu=menu;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				fenetre.change(menu);
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
			
			Rectangle2D rect = new Rectangle2D.Double(milieuX-200,milieuY-150,400,200);
			g2d.setColor(new Color(255, 255, 255, 200));
			g2d.fill(rect);
			g2d.draw(rect);
		}
	}
}
