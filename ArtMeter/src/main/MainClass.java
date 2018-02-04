package main;

import java.util.ArrayList;
import java.util.Collections;

import accesBDD.BDaccess;
import classes.*;
import comparator.Comparateur;
import ihm.Fenetre;
import ihm.LoginMenu;

public class MainClass {
	public static void main(String[] args) {
		
		/* cr�ation de la fenetre principale */
		
		Fenetre fenetre = new Fenetre();
		
		/* chargement de la fenetre pour login */
		fenetre.change(1);
		
		System.out.println("test base de donn�es:\n");
		BDaccess bd = new BDaccess();
		
		
		//J'ecris crée un ideal
		Specialite mode = new Specialite ("mode");
		Specialite portrait = new Specialite ("portrait");
		Pays France = new Pays("France"); 
		PhotographeIdeal ideal = new PhotographeIdeal ("Ideal", "Mon", France, 100, 30, mode, 3);
		System.out.println(ideal);

		bd.requestAll();
				
		System.out.println("Nombre de photographe dans la BD: "+bd.getPhotographes().size());
		for(Photographe p : bd.getPhotographes()) {
			System.out.println(p);
		}
				
		ArrayList<Photographe> listeBD = bd.getPhotographes();
				
		//NE MARCHE PAS
		Comparateur c = new Comparateur (ideal,bd.getAUneSpecialite());
		Collections.sort(listeBD, c);
				
		bd.closeConnection();
		
	}
}
