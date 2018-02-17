package classes;

public class Pays {
	String nom;
	Continent continent;
	
	public Pays() {
	}
	
	public Pays(String nom) {
		this.nom = nom;
	}
	
	public Pays(String nom, Continent continent) {
		this.nom = nom;
		this.continent = continent;
	}

	@Override
	public String toString() {
		return ("Pays: " + nom + "(" + continent+")");
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Pays) {
			Pays p = (Pays)obj;
			if (p.getNom().equals(nom) && p.getContinent().getNom().equals(getContinent().getNom())) {
				return true;
			}
		} else {
			System.out.println("erreur ! equals avec un objet qui n'est pas un pays");
		}
		return false;
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
