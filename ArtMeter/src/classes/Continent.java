package classes;

public class Continent {
	String nom;
	int distance;
	
	public Continent() {
		
	}
	
	public Continent(String nom, int distance) {
		this.nom = nom;
		this.distance = distance;
	}

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
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getDistance(){
		return distance;
	}
	
	
}
