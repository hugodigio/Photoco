package classes;

public class Continent {
	String nom;
	int distance;
	
	@Override
	public String toString() {
		return ("Continent: " + nom);
	}

	//Getter et Setter
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public int getDistance(){
		return distance;
	}
	
	
}
