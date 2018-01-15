package classes;

import java.util.ArrayList;
import java.util.HashMap;

public class Photographe {
	String nom;
	String prenom;
	int age;
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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getLienPortfolio() {
		return lienPortfolio;
	}

	public void setLienPortfolio(String lienPortfolio) {
		this.lienPortfolio = lienPortfolio;
	}

	public int getPrixPrestation() {
		return prixPrestation;
	}

	public void setPrixPrestation(int prixPrestation) {
		this.prixPrestation = prixPrestation;
	}

	public Pays getPays() {
		return pays;
	}

	public void setPays(Pays pays) {
		this.pays = pays;
	}
	
	public Continent getContinent() {
		return pays.getContinent();
	}
	
	
	public int getExperience(Specialite specialite) {
		//TODO a remplir
		return 0;
	}
	
	public HashMap<Specialite, Integer> getAllExperiences(){
		//TODO a remplir
		return null;
	}
	
	public ArrayList<Specialite> getSpecialites(){
		ArrayList sp = new ArrayList<>();
		//TODO a remplir
		return sp;
	}
	
	
}