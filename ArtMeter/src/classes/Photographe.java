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
	
	public Photographe(){}
	
	public Photographe(String nom, String prenom, int age, int prixPrestation, Pays pays) {
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.prixPrestation = prixPrestation;
		this.pays = pays;
	}
	
	@Override
	public String toString() {
		return (prenom + " " + nom + ", " + age + " ans" + ", " + pays + ", " + prixPrestation + " euros.");
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Photographe) {
			Photographe p = (Photographe)obj;
			if(p.getPrenom().equals(getPrenom()) && p.getNom().equals(getNom()) && p.getAge() == getAge() && p.getPays().equals(getPays()) && p.getPrixPrestation() == getPrixPrestation()) {
				return true;
			}
		}
		return false;
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
	
	/**
	 * permet de recup�rer le nombre d'ann�es d'experiences d'une sp�cialit� pour un photographe
	 * @param specialite sp�cialit� concern�e
	 * @return nombre d'ann�e d'exp�riences
	 */
	public int getExperience(Specialite specialite, ArrayList<AUneSpecialite> aus) {
		int exp = 0;
		for(AUneSpecialite a : aus) {
			if(a.getPhotographe().equals(this) && a.getSpecialite().equals(specialite)) {
				exp = a.getExperience();
			}
		}
		return exp;
	}
	
	/**
	 * Permet de récupérer toutes les années d'experiences d'un photographes dans ses diff�rentes specialites
	 * @param aus arraylist d'objet AUneSpecialite, qui contient toutes les relations entre les photographes et les specialite avec leurs ann�es d'experiences
	 * @return retourne une hasmap qui contient la relation Specialité : nb d'annee d'experience
	 */
	public HashMap<Specialite, Integer> getAllExperiences(ArrayList<AUneSpecialite> aus){
		HashMap<Specialite, Integer> si = new HashMap<>();
		for(AUneSpecialite a : aus) {
			if(a.getPhotographe().equals(this)) {
				si.put(a.getSpecialite(), a.getExperience());
			}
		}
		return si;
	}
	
	/**
	 * Fonction qui permet de récupérer toutes les sp�cialites d'un photographe
	 * @param aus arraylist d'objet AUneSpecialite, qui contient toutes les relations entre les photographes et les specialite avec leurs ann�es d'experiences
	 * @return retourne une arraylist qui contient toute les specialite du photographe
	 */
	public ArrayList<Specialite> getSpecialites(ArrayList<AUneSpecialite> aus){
		ArrayList<Specialite> sp = new ArrayList<>();
		for(AUneSpecialite a : aus) {
			if(a.getPhotographe().equals(this)) {
				sp.add(a.getSpecialite());
			}
		}
		return sp;
	}
	
	
}