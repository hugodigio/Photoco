package main;

import accesBDD.BDaccess;
import classes.*;

public class MainClass {
	public static void main(String[] args) {
		
		System.out.println("test base de données:\n");
		BDaccess bd = new BDaccess();
		
		bd.requestAll();
		
		System.out.println("nombre photographe: "+bd.getPhotographes().size());
		for(Photographe p : bd.getPhotographes()) {
			System.out.println(p);
		}
		
		bd.closeConnection();
		
	}
}
