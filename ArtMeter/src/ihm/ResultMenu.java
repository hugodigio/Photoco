package ihm;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

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
	
	Fenetre fenetre;
	
	public ResultMenu(Fenetre fenetre) {
		this.fenetre = fenetre;
		fenetre.add(new PanneauResult());
	}
	

	private class PanneauResult extends JPanel {

		
			public PanneauResult(){
			

				BDaccess bd = new BDaccess();
					
				//Je cree un ideal.
				Specialite mode = new Specialite ("mode",2);
				Specialite portrait = new Specialite ("portrait",2);
				Continent europe = new Continent("Europe",1000);
				Pays france = new Pays("France", europe); 
				PhotographeIdeal ideal = new PhotographeIdeal ("Ideal", "Mon", france, 100, 30, mode, 3);
				System.out.println(ideal);
				
				//Je cree une liste des photographes de la BD.
				bd.requestAll();		
				ArrayList<Photographe> listeBD = bd.getPhotographes();
				Iterator<Photographe> it = listeBD.iterator();
				
				//Je trie la liste grace au comparateur.
				Comparateur c = new Comparateur (ideal,bd.getAUneSpecialite());
				Collections.sort(listeBD, c);
						
				bd.closeConnection();
				
				//Je cree une liste contenant les 10 premiers
				ArrayList<Photographe> palmares = new ArrayList<>();
				
				int i = 0;
				for(i=0; i<10 ; i++){
						palmares.add(listeBD.get(i));
				} 
					
				
				/**
				 * Creation d'une JList a partir de l'ArrayList de la BD deja triée
				 * Ajout de la liste dans le panneau
				 */
				JList liste = new JList (palmares.toArray());
				this.add(liste);
				liste.setCellRenderer(new Renderer());
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
		
	}

}