package main;

import java.util.ArrayList;
import java.util.Collections;

import accesBDD.BDaccess;
import classes.AUneSpecialite;
import classes.Pays;
import classes.Photographe;
import classes.PhotographeIdeal;
import classes.Specialite;
import comparator.Comparateur;

public class MainTest {
	public static void main(String[] args) {
		
		//J'ecris crée un ideal
		Specialite mode = new Specialite ("mode");
		Specialite portrait = new Specialite ("portrait");
		Pays France = new Pays("France"); 
		PhotographeIdeal ideal = new PhotographeIdeal ("Ideal", "Mon", France, 100, 30, mode, 3);
		System.out.println(ideal);
		
		BDaccess bd = new BDaccess();
		bd.requestAll();
		
		System.out.println("Nombre de photographe dans la BD: "+bd.getPhotographes().size());
		for(Photographe p : bd.getPhotographes()) {
			System.out.println(p);
		}
		
		ArrayList<Photographe> listeBD = bd.getPhotographes();
		
		//NE MARCHE PAS java.lang.NullPointerException ?
		Comparateur c = new Comparateur (ideal,bd.getAUneSpecialite());
		Collections.sort(listeBD, c);
		
		bd.closeConnection();
	}

}
