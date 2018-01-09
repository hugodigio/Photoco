package classes;

public class Specialite {

	String nom;
	
	public Specialite(String nom) {
		super();
		this.nom = nom;
	}

	@Override
	public String toString() {
		return ("Specialite: " + nom);
	}
	
	//Getter et Setter
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
}
