package classes;

public class Pays {
	String nom;
	Continent continent;
	
	@Override
	public String toString() {
		return ("Pays: " + nom + "Continent: ");
	}
	
	
	public Pays(String nom) {
		super();
		this.nom = nom;
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
