package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import accesBDD.BDaccess;
import classes.Pays;
import classes.Photographe;
import classes.PhotographeIdeal;
import classes.Specialite;

public class AjouterModifierMenu {
	Fenetre fenetre;
	Photographe photographe;
	BDaccess bd;
	
	final int AJOUTER = 0;
	final int MODIFIER = 1;
	int mode = AJOUTER;
	
	public AjouterModifierMenu(Fenetre fenetre,	Photographe photographe, BDaccess bd) {
		this.fenetre = fenetre;
		if (photographe != null) {
			mode = MODIFIER;
		} else {
			photographe = new Photographe();
		}
		this.photographe = photographe;
		this.bd = bd;
		fenetre.add(new PanneauFormulaire());
		fenetre.setTitle("Modification de l'offre des photographes");
	}
	
	public AjouterModifierMenu(Fenetre fenetre, BDaccess bd) {
		this.fenetre = fenetre;
		this.photographe = new Photographe();
		this.bd = bd;
		
		fenetre.add(new PanneauFormulaire());
		fenetre.setTitle("Modification de l'offre des photographes");
	}
	
	private class PanneauFormulaire extends JPanel{

		private JTextField  JTprenom, JTnom, JTFpays, JTFprix, JTFage;
		private JLabel  JLerreur, JLprenom, JLnom, JLpays, JLprix, JLage;
		private JList<String> listePays;
		private JComboBox<Pays> paysBox;
		
		public PanneauFormulaire() {
			
			GridBagLayout gbl = new GridBagLayout();
			setLayout(gbl);
			GridBagConstraints gbc = new GridBagConstraints();
		    
			JLerreur = new JLabel("");
			JLerreur.setForeground(Color.RED);
			
			JLprenom = new JLabel("Prenom: ");
			JTprenom = new JTextField(20);
			JLnom = new JLabel("Nom: ");
			JTnom = new JTextField(20);
		    JLpays = new JLabel("Pays: ");
		    this.add(JLpays);
		    ArrayList<Pays> listePays = bd.requestPays();
			paysBox = new JComboBox<>();
		    for(Pays p : listePays){
		    	paysBox.addItem(p);
		    }
		    paysBox.setSelectedIndex(0);
		    
		    JLprix = new JLabel("Prix Prestation");
		    this.add(JLprix);
		    JTFprix = new JTextField(20);
		    this.add(JTFprix);
		    
		    JLage = new JLabel("Age");
		    this.add(JLage);
		    JTFage = new JTextField(20);
		    this.add(JTFage);
		    
			JButton valider = new JButton("Valider");
			valider.addActionListener(new ValidationButton());
			JTFage.addKeyListener(new ValidationKey());
			System.out.println("mode = "+mode);
			if(mode == MODIFIER) {
				JTprenom.setText(photographe.getPrenom());
				JTnom.setText(photographe.getNom());
				JTFage.setText(((Integer)photographe.getAge()).toString());
				JTFprix.setText(((Integer)photographe.getPrixPrestation()).toString());
				System.out.println(photographe.getPays());
				for(int i = 0; i < paysBox.getItemCount(); i++) {
					System.out.println(paysBox.getItemAt(i));
					if (paysBox.getItemAt(i).equals(photographe.getPays())) {
						paysBox.setSelectedIndex(i);
					}
				}
			}
		    
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(25, 0, 0, 0);
			add(JLprenom,gbc);
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(JTprenom,gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.insets = new Insets(25, 0, 0, 0);
			add(JLnom, gbc);
			gbc.gridx = 1;
			gbc.gridy = 2;
			add(JTnom,gbc);
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.insets = new Insets(25, 0, 0, 0);
			add(JLpays,gbc);
			gbc.gridx = 1;
			gbc.gridy = 3;
			add(paysBox,gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.insets = new Insets(25, 0, 0, 0);
			add(JLprix,gbc);
			gbc.gridx = 1;
			gbc.gridy = 4;
			add(JTFprix,gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 5;
			gbc.insets = new Insets(25, 0, 0, 0);
			add(JLage,gbc);
			gbc.gridx = 1;
			gbc.gridy = 5;
			add(JTFage,gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 6;
			gbc.gridwidth=2;
			add(valider,gbc);
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(JLerreur,gbc);
		}
		
		/*----------------------VALIDATION-----------------------------------------*/
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
		
		public void validation() {
			
			
			/* on vérifie et récupère les données du formulaire */
			if (!JTprenom.getText().equals("")) {
				photographe.setPrenom(JTprenom.getText());
				if(!JTnom.getText().equals("")) {
					photographe.setNom(JTnom.getText());
					photographe.setPays((Pays)paysBox.getSelectedItem());
					if(!JTFprix.getText().equals("")) {
						if(isNumber(JTFprix.getText())) {
							int prix = Integer.parseInt(JTFprix.getText());
							photographe.setPrixPrestation(prix);
							if(!JTFage.getText().equals("")) {
								if(isNumber(JTFage.getText())) {
									int age = Integer.parseInt(JTFage.getText());
									photographe.setAge(age);
									
									//tout les tests sont réussis
									System.out.println(photographe);
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
									String texte = "Voulez vous vraiment ";
									if (mode == AJOUTER) texte += "ajouter";
									else if (mode == MODIFIER) texte += "modifier";
									texte += " ce photographe ?";
									confirmationtxt.setText(texte);
									JLabel photographetxt = new JLabel(photographe.toString());
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
											if(mode == AJOUTER) {
												bd.ajouterPhotographe(photographe, photographe.getPays());
											}else if(mode == MODIFIER){
												bd.modifierPhotographe(photographe);
											}
											fenetre.change(Fenetre.MENU_MODIFICATION);
											confirmation.dispose();
										}
									});
									confirmation.add(new DialogComponent(confirmationtxt,photographetxt),BorderLayout.CENTER);
									confirmation.add(new DialogComponent(oui,non),BorderLayout.SOUTH);
									confirmation.setVisible(true);
								} else {
									JLerreur.setText("Veuillez saisir une valeur numérique pour l'age");
								}
							} else {
								JLerreur.setText("Veuillez saisir un age");
							}
							
						} else {
							JLerreur.setText("Veuillez saisir une valeur numérique pour le prix Prestation");
						}
					}else {
						JLerreur.setText("Veuillez saisir un prix");
					}
					
				} else {
					JLerreur.setText("Veuillez saisir un nom");
				}
			} else {
				JLerreur.setText("Veuillez saisir un prenom");
			}
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
			
			Rectangle2D rect = new Rectangle2D.Double(milieuX-200,milieuY-175,400,350);
			g2d.setColor(new Color(255, 255, 255, 200));
			g2d.fill(rect);
			g2d.draw(rect);
		}
		
	}
	
	public boolean isNumber(String str) {
		return str.matches("[0-9]*");
	}
	
}
