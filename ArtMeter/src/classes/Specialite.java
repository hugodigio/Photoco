package classes;

public class Specialite {

	String nom;
	int groupe;
	
	public Specialite(String nom, int groupe) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return (nom);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Specialite) {
			Specialite s = (Specialite)obj;
			if (s.getNom().equals(getNom())) {
				return true;
			}
		}
		return false;
	}
	
	public int getGroupe() {
		return groupe;
	}

	public void setGroupe(int groupe) {
		this.groupe = groupe;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
}
