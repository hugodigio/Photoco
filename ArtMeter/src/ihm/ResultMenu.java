package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import accesBDD.BDaccess;
import classes.Continent;
import classes.Pays;
import classes.Photographe;
import classes.PhotographeIdeal;
import classes.Specialite;
import comparator.Comparateur;

public class ResultMenu {
	
	PhotographeIdeal ideal;
	Fenetre fenetre;
	Photographe selection;
	JButton reserver;
	String[] specialites;
	BDaccess bd;
	
	public ResultMenu(Fenetre fenetre, Photographe ideal) {
		this.fenetre = fenetre;
		this.ideal = (PhotographeIdeal)ideal;
		selection = new Photographe();
		fenetre.add(new PanneauResult());
		fenetre.setTitle("Resultats");
	}
	

	private class PanneauResult extends JPanel {

		
			public PanneauResult(){
			

				bd = new BDaccess();
				
				GridBagLayout gbl = new GridBagLayout();
				setLayout(gbl);
				GridBagConstraints gbc = new GridBagConstraints();
				
				//Je cree une liste des photographes de la BD.
				bd.requestAll();
				setLayout(new GridBagLayout());
				ArrayList<Photographe> listeBD = bd.getPhotographes();
				//Iterator<Photographe> it = listeBD.iterator();
				
				//Je trie la liste grace au comparateur.
				Comparateur c = new Comparateur (ideal,bd.getAUneSpecialite());
				Collections.sort(listeBD, c);
						
				//Je cree une liste contenant les 10 premiers
				ArrayList<Photographe> palmares = new ArrayList<>();
				
				int i = 0;
				for(i=0; i<10 ; i++){
						palmares.add(listeBD.get(i));
				} 
				specialites = new String[10];
				i = 0;
				for(i=0; i<10 ; i++){
						specialites[i]=" ";
				} 
				/**
				 * Creation d'une JList a partir de l'ArrayList de la BD deja triée
				 * Ajout de la liste dans le panneau
				 */
				JList liste = new JList (palmares.toArray());
				JList listespe = new JList(specialites);
				
				JLabel specialitestxt = new JLabel("Specialite(s) du photographe: ");
				
				reserver = new JButton("réserver");
				reserver.setEnabled(false);
				reserver.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						//fenetre de confirmation
						JDialog confirmation = new JDialog ();
						confirmation.setLayout(new BorderLayout());
						confirmation.setTitle("Confirmation");
						confirmation.setModal (true);
						confirmation.setBounds(0,0,500,200);
						confirmation.setLocationRelativeTo(null);
						confirmation.setAlwaysOnTop (true);
						confirmation.setModalityType (ModalityType.APPLICATION_MODAL);
						JLabel confirmationtxt = new JLabel();
						String texte = "Une demande de réservation a ete envoyé à "+selection.getPrenom()+" "+selection.getNom()+".";
						confirmationtxt.setText(texte);
						JLabel photographetxt = new JLabel("Le photographe répondra à votre demande, dès que possible");
						JButton ok = new JButton("Ok");
						ok.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								confirmation.dispose();							
							}
						});
						confirmation.add(new DialogComponent(confirmationtxt,photographetxt),BorderLayout.CENTER);
						confirmation.add(new DialogComponent(ok),BorderLayout.SOUTH);
						confirmation.setVisible(true);
					}
				});
				gbc.gridy = 0;
				add(liste,gbc);
				gbc.gridy = 1;
				gbc.insets = new Insets(20, 0, 0, 0);
				add(specialitestxt,gbc);
				gbc.gridy = 2;
				gbc.insets = new Insets(0, 0, 0, 0);
				add(listespe,gbc);
				gbc.gridy = 3;
				add(reserver,gbc);
				liste.setCellRenderer(new Renderer(listespe));
				listespe.setCellRenderer(new RendererSpecialite());
		}

			private class DialogComponent extends JPanel{
				public DialogComponent(Component ...components) {
					if(components[0] instanceof JButton) {
						setLayout(new FlowLayout(FlowLayout.RIGHT));
					}
					for(Component c : components) {
						add(c);
					}
				}
			}

		private class Renderer extends JPanel implements ListCellRenderer<Object>{

			private int rang;
			private String nom_rang;
			private int espace = 5;
			private JList liste10 ;
			JList rs;
			
			public Renderer(JList rs) {
				this.rs = rs;
			}
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
					Photographe p = (Photographe)value;
					selection = p;
					reserver.setEnabled(true);
					HashMap<Specialite,Integer> spes = p.getAllExperiences(bd.getAUneSpecialite());
					int i = 0;
					if(!spes.isEmpty()) {
						for(Specialite s : spes.keySet()) {
							String spe = s.getNom()+", "+spes.get(s).toString()+" annee(s) d'expererience";
							specialites[i] = spe;
							i++;
						}
					}
					for(String s:specialites) {
						System.out.println(s);
					}
					rs.updateUI();
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
				int largeur = 7 + fm.stringWidth(nom_rang) + espace + 10;
				int hauteur = Math.max(fm.getAscent(), 10)+3;

				return new Dimension(largeur, hauteur);
			}
			
		}
		
		private class RendererSpecialite extends JPanel implements ListCellRenderer<Object>{

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
				
				
				setBackground(list.getBackground());
				setForeground(list.getForeground());
				
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
				int largeur = 7 + fm.stringWidth(nom_rang) + espace + 10;
				int hauteur = Math.max(fm.getAscent(), 10)+3;

				return new Dimension(300, hauteur);
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
			
			Rectangle2D rect = new Rectangle2D.Double(milieuX-300,milieuY-250,600,425);
			g2d.setColor(new Color(255, 255, 255, 200));
			g2d.fill(rect);
			g2d.draw(rect);
		}
		
	}

}