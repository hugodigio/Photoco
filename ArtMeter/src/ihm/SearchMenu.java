package ihm;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import accesBDD.BDaccess;
import classes.Pays;
import classes.Photographe;
import classes.PhotographeIdeal;
import classes.Specialite;


public class SearchMenu {

	Fenetre fenetre;
	Specialite speIdeal;
	int expIdeal;
	Pays paysIdeal;
	int prixIdeal;
	int ageIdeal;
	
	public SearchMenu(Fenetre fenetre) {
		this.fenetre = fenetre;
		fenetre.add(new PanneauSearch());
		fenetre.setTitle("Recherche");
	}
	
	
	/*----------PANNEAU DE RECHERCHE--------------*/
	
	private class PanneauSearch extends JPanel {
		
		private JTextField JTFexperience, JTFpays, JTFprix, JTFage;
		private JLabel JLerreur, JLspecialite, JLexperience, JLpays, JLprix, JLage;
		private JList<String> listePays, listeSpe;
		private JComboBox<Pays> paysBox;
		 JComboBox<Specialite> speBox;
		
		
		private PanneauSearch(){
			
			BDaccess bd = new BDaccess();
			
			GridBagLayout gbl = new GridBagLayout();
			setLayout(gbl);
			GridBagConstraints gbc = new GridBagConstraints();
			
			JLerreur = new JLabel("");
			JLerreur.setForeground(Color.RED);
			
			JLspecialite = new JLabel("Specialite");
		    this.add(JLspecialite);
		    
		    ArrayList<Specialite> listeSpe = bd.requestSpecialites();
			speBox = new JComboBox();
		    for(Specialite s : listeSpe){
		    	speBox.addItem(s);
		    	speIdeal = s;
		    }
		    speBox.setSelectedIndex(3);
			
		    JLexperience = new JLabel("Experience");
		    this.add(JLexperience);
		    JTFexperience = new JTextField(20);
		    this.add(JTFexperience);
		    
		    JLpays = new JLabel("Pays");
		    this.add(JLpays);
		    ArrayList<Pays> listePays = bd.requestPays();
		    paysBox = new JComboBox();
		    for(Pays p : listePays){
		    	paysBox.addItem(p);
		    	paysIdeal = p;
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
		    
			gbc.gridx = 0;
			gbc.gridy = 1;
			add(JLspecialite,gbc);
			gbc.gridx = 1;
			gbc.gridy = 1;
			add(speBox,gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.insets = new Insets(25, 0, 0, 0);
			add(JLexperience,gbc);
			gbc.gridx = 1;
			gbc.gridy = 2;
			add(JTFexperience,gbc);
			
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
			gbc.insets = new Insets(0, 0, 20, 0);
			add(JLerreur,gbc);
			
			bd.closeConnection();
		    
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
			
			Rectangle2D rect = new Rectangle2D.Double(milieuX-200,milieuY-200,400,350);
			g2d.setColor(new Color(255, 255, 255, 200));
			g2d.fill(rect);
			g2d.draw(rect);
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
			
			//J'ecris crée un ideal
			
			//vérification conformité formulaire
			if(!JTFage.getText().equals("") && !isNumber(JTFage.getText())) {
				JLerreur.setText("veuillez entrer une valeur numerique pour l'age");
			} else if(!JTFprix.getText().equals("") && !isNumber(JTFprix.getText())) {
				JLerreur.setText("veuillez entrer une valeur numerique le prix");
			} else if(!JTFexperience.getText().equals("") && !isNumber(JTFexperience.getText())) {
				JLerreur.setText("veuillez entrer une valeur numerique l'experience");
			} else {
				Specialite spe = (Specialite)speBox.getSelectedItem();
				int exp = 0;
				if(!JTFexperience.getText().equals("")) exp = Integer.parseInt(JTFexperience.getText());
				Pays pays = (Pays)paysBox.getSelectedItem();
				int prix = 0;
				if(!JTFprix.getText().equals("")) prix = Integer.parseInt(JTFprix.getText());
				int age = 20;
				if(!JTFage.getText().equals("")) age = Integer.parseInt(JTFage.getText());
				PhotographeIdeal ideal = new PhotographeIdeal ("Ideal", "Mon", pays, prix, age, spe, exp);
				System.out.println(ideal);
				ArrayList<Photographe> liste = new ArrayList <>();
				liste.add(ideal);
				fenetre.change(Fenetre.MENU_RESULTAT_RECHERCHE,liste);
			}
		}
	}
	
	
	public boolean isNumber(String str) {
		return str.matches("[0-9]*");
	}	
	
}
