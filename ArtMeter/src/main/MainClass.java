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
		Specialite mode = new Specialite ("mode",2);
		Specialite portrait = new Specialite ("portrait",2);
		Continent europe = new Continent("Europe",1000);
		Pays france = new Pays("France", europe); 
		PhotographeIdeal ideal = new PhotographeIdeal ("Ideal", "Mon", france, 100, 30, mode, 3);
		System.out.println(ideal);

		bd.requestAll();
				
		System.out.println("Nombre de photographe dans la BD: "+bd.getPhotographes().size());
	
				
		ArrayList<Photographe> listeBD = bd.getPhotographes();
				
		//NE MARCHE PAS
		Comparateur c = new Comparateur (ideal,bd.getAUneSpecialite());
		Collections.sort(listeBD, c);
		
		System.out.println("\n\n\nAPRES LE TRI:");
		for(Photographe p : listeBD) {
			System.out.println(p);
		}
				
		bd.closeConnection();
		
	}
}
