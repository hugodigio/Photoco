package main;

import accesBDD.BDaccess;
import classes.*;

public class MainClass {
	public static void main(String[] args) {
		
		System.out.println("test base de données:\n");
		BDaccess bd = new BDaccess();
		
		bd.requestAll();
		
		bd.TestsSQL("SELECT * FROM AUneSpecialite WHERE id_photographe=1;");
		
		bd.closeConnection();
		
	}
}
