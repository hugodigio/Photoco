package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import accesBDD.BDaccess;
import classes.Photographe;
import classes.PhotographeIdeal;
import comparator.Comparateur;

public class ModificationMenu {

	Fenetre fenetre;
	JButton ajouter;
	JButton modifier;
	JButton supprimer;
	Photographe selection;
	
	final int AJOUTER = 0;
	final int MODIFIER = 1;
	final int SUPPRIMER = 2;
	
	/**
	 * Constructeur du menu
	 * @param fenetre fenetre dans laquelle le menu est affiché
	 */
	public ModificationMenu(Fenetre fenetre) {
		this.fenetre = fenetre;
		fenetre.add(new PanneauResult());
		fenetre.setTitle("Ajouter modifier ou supprimer un photographe");
	}
	

	private class PanneauResult extends JPanel {
			
			BDaccess bd;
		
			public PanneauResult(){
			
				GridBagLayout gbl = new GridBagLayout();
				setLayout(gbl);
				GridBagConstraints gbc = new GridBagConstraints();

				bd = new BDaccess();
					
				
				//Je cree une liste des photographes de la BD.
				bd.requestAll();		
				ArrayList<Photographe> listeBD = bd.getPhotographes();
				Iterator<Photographe> it = listeBD.iterator();
				
				/* boutons */
				ajouter = new JButton("Ajouter un Photographe");
				modifier = new JButton("Modifier la sélection");
				modifier.setEnabled(false);
				supprimer = new JButton("Supprimer la sélection");
				supprimer.setEnabled(false);
				
				/**
				 * Creation d'une JList a partir de l'ArrayList de la BD deja triée
				 * Ajout de la liste dans le panneau
				 */
				JList liste = new JList (bd.getPhotographes().toArray());
				
//				gbc.gridx = 0;
//				gbc.gridy = 1;
//				add(ajouter,gbc);
//				gbc.gridx = 1;
//				add(modifier,gbc);
//				gbc.gridx = 2;
//				add(supprimer,gbc);
//				gbc.gridx = 0;
//				gbc.gridy = 0;
//				gbc.gridwidth = 3;
//				add(new JScrollPane(liste),gbc);
				add(new View(liste));
				
				liste.setCellRenderer(new Renderer());
				ajouter.addActionListener(new Boutons(AJOUTER));
				modifier.addActionListener(new Boutons(MODIFIER));
				supprimer.addActionListener(new Boutons(SUPPRIMER));
		}
		
		private class View extends JPanel{
			public View(JList liste) {
				setLayout(new BorderLayout());
				add(new JScrollPane(liste),BorderLayout.CENTER);
				add(new BoutonsLayout(),BorderLayout.SOUTH);
			}
		}
		private class BoutonsLayout extends JPanel{
			public BoutonsLayout() {
				setLayout(new FlowLayout(FlowLayout.RIGHT));
				add(ajouter);
				add(modifier);
				add(supprimer);
			}
		}
		

		private class Renderer extends JPanel implements ListCellRenderer<Object>{

			private int rang;
			private String nom_rang;
			private int espace = 5;
			private JList liste10 ;
			
			/**
			 * Affiche le component
			 */
			@Override
			public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
					boolean isSelected, boolean cellHasFocus) {
				
				
				this.rang = index;
				this.nom_rang = value.toString();
				
				
				//recuperer les couleurs habituelles de l'affichage
				if(isSelected){
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
					selection = (Photographe)value;
					if(!modifier.isEnabled() && !supprimer.isEnabled()) {
						modifier.setEnabled(true);
						supprimer.setEnabled(true);
					}
				}else{
					setBackground(list.getBackground());
					setForeground(list.getForeground());
				}
				
				return this;
			}
			
			/**
			 * Affichage
			 */
			public void paintComponent(Graphics g){
				
				super.paintComponent(g);
				Graphics2D g2D = (Graphics2D) g;
				
				FontMetrics fm = g.getFontMetrics();
				int longueur = fm.stringWidth(nom_rang) + espace;
					
				g2D.drawString(nom_rang, 0, 10);
			}
			
			/**
			 * Permet d'avoir une taille optimisée de la liste
			 */
			public Dimension getPreferredSize(){
				FontMetrics fm = this.getGraphics().getFontMetrics();
				int largeur = 7 + fm.stringWidth(nom_rang) + espace + 100;
				int hauteur = Math.max(fm.getAscent(), 10)+3;

				return new Dimension(largeur, hauteur);
			}
			
		}
		
		private class Boutons implements ActionListener {
			int action;
			
			public Boutons(int action) {
				this.action = action;
			}
		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(action) {
				case AJOUTER:
					fenetre.change(Fenetre.MENU_AJOUTER_MODIFIER,bd);
					break;
				case MODIFIER:
					ArrayList<Photographe> p = new ArrayList<>();
					p.add(selection);
					fenetre.change(Fenetre.MENU_AJOUTER_MODIFIER,p,bd);
					break;
				case SUPPRIMER:
					/*Jdialog sert a faire des fenetre de dialog
					avec la possibilité de la rendre prioritaire 
					(obliger de la fermer pour agir sur les autres fenetres)*/
					JDialog confirmation = new JDialog ();
					
					confirmation.setLayout(new BorderLayout());
					
					confirmation.setTitle("Confirmation");
					confirmation.setModal (true);
					confirmation.setBounds(0,0,300,100);
					confirmation.setLocationRelativeTo(null);
					confirmation.setAlwaysOnTop (true);
					confirmation.setModalityType (ModalityType.APPLICATION_MODAL);
					JLabel confirmationtxt = new JLabel("   Voulez vous vraiment supprimer "+selection.getPrenom()+" "+selection.getNom()+" ?");
					JButton oui = new JButton("oui");
					JButton non = new JButton("non");
					non.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							confirmation.dispose();							
						}
					});
					oui.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							bd.supprimerPhotographe(selection);
							fenetre.change(Fenetre.MENU_MODIFICATION);
							confirmation.dispose();
						}
					});
					confirmation.add(confirmationtxt,BorderLayout.CENTER);
					confirmation.add(new OuiNon(oui,non),BorderLayout.SOUTH);
					confirmation.setVisible(true);
					break;
				}
			}
			
			private class OuiNon extends JPanel{
				public OuiNon(JButton ...buttons) {
					setLayout(new FlowLayout(FlowLayout.RIGHT));
					for(JButton b : buttons) {
						add(b);
					}
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
			
			Rectangle2D rect = new Rectangle2D.Double(milieuX-300,milieuY-250,600,400);
			g2d.setColor(new Color(255, 255, 255, 200));
			g2d.fill(rect);
			g2d.draw(rect);
		}
	}
	
}
