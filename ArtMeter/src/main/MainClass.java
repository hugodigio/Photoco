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
		
		/* création de la fenetre principale */
		
		Fenetre fenetre = new Fenetre();
		
		/* chargement de la fenetre pour login */
		fenetre.change(Fenetre.MENU_LOGIN);
		
	}
}
