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
		private JLabel JLspecialite, JLexperience, JLpays, JLprix, JLage;
		private JList<String> listePays, listeSpe;
		
		
		private PanneauSearch(){
			
			BDaccess bd = new BDaccess();
			
			GridBagLayout gbl = new GridBagLayout();
			setLayout(gbl);
			GridBagConstraints gbc = new GridBagConstraints();
			
			JLspecialite = new JLabel("Specialite");
		    this.add(JLspecialite);
		    
		    ArrayList<Specialite> listeSpe = bd.requestSpecialites();
			JComboBox speBox = new JComboBox();
		    for(Specialite s : listeSpe){
		    	speBox.addItem(s);
		    	speIdeal = s;
		    }
		    speBox.setSelectedIndex(3);
		    speBox.addActionListener(new ComboSpe());
			
		    JLexperience = new JLabel("Experience");
		    this.add(JLexperience);
		    JTFexperience = new JTextField(20);
		    DocumentListener dlExperience = new ExperienceListener();
		    JTFexperience.getDocument().addDocumentListener(dlExperience);
		    this.add(JTFexperience);
		    
		    JLpays = new JLabel("Pays");
		    this.add(JLpays);
		    ArrayList<Pays> listePays = bd.requestPays();
			JComboBox paysBox = new JComboBox();
		    for(Pays p : listePays){
		    	paysBox.addItem(p);
		    	paysIdeal = p;
		    }
		    paysBox.setSelectedIndex(11);
		   // paysBox.setRenderer(new paysRenderer());
		    paysBox.addActionListener(new ComboPays());
		    
		    
		    
		    JLprix = new JLabel("Prix Prestation");
		    this.add(JLprix);
		    JTFprix = new JTextField(20);
		    this.add(JTFprix);
		    DocumentListener dlPrix = new PrixListener();
		    JTFprix.getDocument().addDocumentListener(dlPrix);
		    
		    JLage = new JLabel("Age");
		    this.add(JLage);
		    JTFage = new JTextField(20);
		    this.add(JTFage);
		    DocumentListener dlAge = new AgeListener();
		    JTFage.getDocument().addDocumentListener(dlAge);
		    
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
			
			bd.closeConnection();
		    
		}
		
		/*--------------LES LISTENERS----------------*/
		
		
		private class ExperienceListener implements DocumentListener{

			@Override
			public void insertUpdate(DocumentEvent e) {
				expIdeal = setExp(Integer.parseInt(JTFexperience.getText()));
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(!(JTFexperience.getText().equals(""))){
					expIdeal = setExp(Integer.parseInt(JTFexperience.getText()));
				}
				else{
					expIdeal = setExp(0);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				expIdeal = setExp(Integer.parseInt(JTFexperience.getText()));		
			}

		}
		
		private class PrixListener implements DocumentListener{

			@Override
			public void insertUpdate(DocumentEvent e) {
				prixIdeal = setPrix(Integer.parseInt(JTFprix.getText()));
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(!(JTFprix.getText().equals(""))){
					prixIdeal = setPrix(Integer.parseInt(JTFprix.getText()));
				}
				else{
					prixIdeal = setPrix(0);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				prixIdeal = setPrix(Integer.parseInt(JTFprix.getText()));
				
			}
		}
		
		private class AgeListener implements DocumentListener{

			@Override
			public void insertUpdate(DocumentEvent e) {
				ageIdeal = setAge(Integer.parseInt(JTFage.getText()));
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(!(JTFprix.getText().equals(""))){
					ageIdeal = setAge(Integer.parseInt(JTFage.getText()));
				}
				else{
					ageIdeal = setAge(20);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				ageIdeal = setAge(Integer.parseInt(JTFage.getText()));				
			}
		}
		
	}
	
	/*-----------------COMBOBOX SPE---------------------------------------*/
	
	public class ComboSpe implements ActionListener {
	   
	    public void actionPerformed(ActionEvent e) {
	        JComboBox cb = (JComboBox)e.getSource();
	        speIdeal = (Specialite)cb.getSelectedItem();
	    }
	    
	}
	
	/*------------------SPEBOX RENDERER-----------------------------------*/
	
	/*private class paysRenderer implements ListCellRenderer<Object>{
		
			public Component getListCellRendererComponent(JList<?> list,
										                    Object value,
										                    int index,
										                    boolean isSelected,
										                    boolean cellHasFocus) {
				
			        setText(((Pays) value).getNom());
			        return this;
			}
		}*/

	
	/*-------------------- COMBOBOX PAYS-----------------------------*/
	
	private class ComboPays implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
		        JComboBox cb = (JComboBox)e.getSource();
		        paysIdeal = (Pays)cb.getSelectedItem();
		}
		
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
	
	/*----------------------CREATION DE L'IDEAL---------------------------------*/
	
	private int setExp(int parseInt) {
		return parseInt;
	}
	
	private int setPrix(int parseInt) {
		return parseInt;
	}
	
	private int setAge(int parseInt) {
		return parseInt;
	}
	
	private Specialite setSpe(String nom, int groupe){
		Specialite spe = new Specialite (nom, groupe);
		return spe;
		
	}
	
	public void validation() {
		
		//J'ecris crée un ideal
		
		PhotographeIdeal ideal = new PhotographeIdeal ("Ideal", "Mon", paysIdeal, prixIdeal, ageIdeal, speIdeal, expIdeal);
		System.out.println(ideal);
		ArrayList<Photographe> liste = new ArrayList <>();
		liste.add(ideal);
		fenetre.change(Fenetre.MENU_RESULTAT_RECHERCHE,liste);
		
	}
	
	
}
