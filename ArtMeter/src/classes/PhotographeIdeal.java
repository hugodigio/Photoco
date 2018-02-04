package classes;

public class PhotographeIdeal extends Photographe{
	
	Specialite spe;
	int exp;
	
	public PhotographeIdeal(String nom, String prenom, Pays pays, int prixPrestation, int age, Specialite spe, int exp){
		super(nom, prenom, pays, prixPrestation, age);
		this.spe = spe;
		this.exp = exp;
	}

	public void ajouter_AUS (Photographe ph, Specialite spe, int exp){
		AUneSpecialite ajout = new AUneSpecialite(ph, spe, exp);
		liste.add(ajout);
	}

	public String getSpecialiteAUS() {
		return spe.getNom();
	}
	
	public int getExperienceAUS() {
		return exp;
	}
}
