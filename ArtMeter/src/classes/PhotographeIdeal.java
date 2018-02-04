package classes;

public class PhotographeIdeal extends Photographe{
	
	Specialite spe;
	int exp;
	
	public PhotographeIdeal(String nom, String prenom, Pays pays, int prixPrestation, int age, Specialite spe, int exp){
		super(nom, prenom, age, prixPrestation, pays);
		this.spe = spe;
		this.exp = exp;
	}

	public String getSpecialiteAUS() {
		return spe.getNom();
	}
	
	public int getExperienceAUS() {
		return exp;
	}
}
