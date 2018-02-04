package main;

import accesBDD.BDaccess;
import classes.*;
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
		
		bd.requestAll();
		
		System.out.println("nombre photographe: "+bd.getPhotographes().size());
		for(Photographe p : bd.getPhotographes()) {
			System.out.println(p);
		}
		
		bd.closeConnection();
		
	}
}
