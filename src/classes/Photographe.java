package classes;

import java.util.ArrayList;

public class Photographe {
	String nom;
	String prenom;
	private int age;
	String photo;
	String lienPortfolio;
	int prixPrestation;
	Pays pays;
	
	public Photographe() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return (prenom + " " + nom + ", " + age + " ans" + ", " + pays + ", " + prixPrestation + "euros.");
	}

	public Continent getContinent() {
		return Continent;
	}
	
}
