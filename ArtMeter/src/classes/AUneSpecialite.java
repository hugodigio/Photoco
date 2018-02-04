package classes;

public class AUneSpecialite {
	Photographe photographe;
	Specialite specialite;
	int experience;

	public AUneSpecialite(){}
	
	public AUneSpecialite(Photographe ph, Specialite spe, int exp) {
		this.photographe = ph;
		this.specialite = spe;
		this.experience = exp;
		}
	
	//Getter et Setter
	public Photographe getPhotographe() {
		return photographe;
	}
	public void setPhotographe(Photographe photographe) {
		this.photographe = photographe;
	}
	public Specialite getSpecialite() {
		return specialite;
	}
	public void setSpecialite(Specialite specialite) {
		this.specialite = specialite;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	
	
	
}
