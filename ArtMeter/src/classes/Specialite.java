package classes;

public class Specialite {
	String nom;
	Metier metier;	//le metier associé a la specialite
	
	public Specialite(String nom, Metier metier) {
		this.nom = nom;
		this.metier = metier;
	}
	
	@Override
	public String toString() {
		return nom;
	}
}
