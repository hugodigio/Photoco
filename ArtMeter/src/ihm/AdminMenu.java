package ihm;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
	}
	
	private class Panneau extends JPanel{
		public Panneau() {
			
			GridBagLayout gbl = new GridBagLayout();
			setLayout(gbl);
			GridBagConstraints gbc = new GridBagConstraints();
			
			recherche = new JButton("Rechercher un photographe");
			ajouter   = new JButton("Ajouter/Modifier/Supprimer un photographe");
			deconnexion = new JButton("Se deconnecter");
			deconnexion.setBackground(Color.PINK);
			JLabel admin = new JLabel("Connecté en temps qu'administrateur");
			gbc.gridx=0;
			gbc.gridy=0;
			gbc.insets = new Insets(0, 0, 25, 0);
			add(admin,gbc);
			gbc.gridy=1;
			add(recherche,gbc);
			gbc.gridy=2;
			add(ajouter,gbc);
			gbc.gridy=3;
			gbc.insets = new Insets(50, 0, 0, 0);
			add(deconnexion,gbc);
			
			recherche.addActionListener(new changementFenetre(Fenetre.MENU_RECHERCHE));
			ajouter.addActionListener(new changementFenetre(Fenetre.MENU_AJOUTER_MODIFIER_SUPPRIMER));
			
			deconnexion.addActionListener(new changementFenetre(Fenetre.MENU_LOGIN));
		}
		
		private class changementFenetre implements ActionListener{
			int menu;
			public changementFenetre(int menu) {
				this.menu=menu;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				fenetre.change(menu, null);
			}
			
		}
	}
}
