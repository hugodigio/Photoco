package classes;

public class Pays {
	String nom;
	Continent continent;
	
	public Pays() {
	}
	
	public Pays(String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return ("Pays: " + nom + "(" + continent+")");
	}

	//Getter et Setter
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Continent getContinent() {
		return continent;
	}

	public void setContinent(Continent continent) {
		this.continent = continent;
	}
	
}
