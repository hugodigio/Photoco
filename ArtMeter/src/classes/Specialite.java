package classes;

public class Specialite {

	String nom;
	
	public Specialite(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return (nom);
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
}
