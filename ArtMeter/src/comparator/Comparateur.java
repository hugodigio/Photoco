package comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import classes.AUneSpecialite;

/**
 * Cette classe défini un photographe et contient tous les attributs qui lui sont propre.
 * Elle permet également d'évaluer un photographe en fonction d'un autre ou d'un string pour avoir une note de proximité.
 * @author Aliyya Adira
 * @version 1.0
 */

import classes.Pays;
import classes.Photographe;
import classes.PhotographeIdeal;
import classes.Specialite;

public class Comparateur implements Comparator<Photographe> {

	PhotographeIdeal ideal;
	ArrayList<AUneSpecialite> liste;
	
	public Comparateur (PhotographeIdeal ideal, ArrayList<AUneSpecialite> liste) {
		this.ideal = ideal;
		this.liste = liste;
	}
	
	
	
	public int score (Photographe photographe){
		
		int score = 0;
		
		//SPECIALITE ET EXPERIENCES
		ArrayList<Specialite> listespe;
		listespe =	photographe.getSpecialites(liste);
		
		for (Specialite spe : listespe){
		
			int scoreInterne = 0;
			String speIdeal = ideal.getSpecialiteAUS();
			int expIdeal = ideal.getExperienceAUS();
			int exp = photographe.getExperience(spe, liste);
			
			if (spe.getNom().equals(speIdeal)){
				scoreInterne +=100;
			
				if (exp >= expIdeal) scoreInterne += 80;
				else if ( Math.abs(exp-expIdeal) > 0 && Math.abs(exp-expIdeal) < 6 ) scoreInterne += 40;
				else if ( Math.abs(exp-expIdeal) > 5 && Math.abs(exp-expIdeal) < 11 ) scoreInterne += 0;
				else scoreInterne -= 10;
				
			}
			else if (spe.getGroupe() == speIdeal.getGroupe()){
				scoreInterne +=60;
				
				if (exp >= expIdeal) scoreInterne += 80;
				else if ( Math.abs(exp-expIdeal) > 0 && Math.abs(exp-expIdeal) < 6 ) scoreInterne += 40;
				else if ( Math.abs(exp-expIdeal) > 5 && Math.abs(exp-expIdeal) < 11 ) scoreInterne += 0;
				else scoreInterne -= 10;
				
			}
			else
				scoreInterne -= 100;
			
			score = Math.max(score, scoreInterne);
		}
		
		
		
		//PAYS
		if( photographe.getPays().equals(ideal.getPays()) )
			score += 90;
		
		else if ( photographe.getContinent().equals(ideal.getContinent()) )
			score +=30;
		
		
		else if (  Math.abs( (photographe.getContinent().getDistance()) - (ideal.getContinent().getDistance()) ) < 5010 )
			score +=0;
		else
			score += (-30);
		
		
		
		//PRIX
		if(photographe.getPrixPrestation() <= ideal.getPrixPrestation())
			score += 50;
		
		else if ( photographe.getPrixPrestation()-ideal.getPrixPrestation() > 0 && photographe.getPrixPrestation()-ideal.getPrixPrestation() < 101)
			score +=30;
		
		else if ( photographe.getPrixPrestation()-ideal.getPrixPrestation() > 100 && photographe.getPrixPrestation()-ideal.getPrixPrestation() < 251)
			score +=15;
		else
			score +=0;
		
		
		//AGE
		if(photographe.getAge() == ideal.getAge())
			score +=10;
		
		else if ( Math.abs(photographe.getAge()-ideal.getAge()) > 0 && Math.abs(photographe.getAge()-ideal.getAge()) < 5)
			score +=5;
		
		else if ( Math.abs(photographe.getAge()-ideal.getAge()) > 4 && Math.abs(photographe.getAge()-ideal.getAge()) < 10)
			score +=2;
			
		else
			score +=0;
		
		return -score;
	}
	
	
	public int compare(Photographe o1, Photographe o2) {
		int score_o1 = score(o1);
		int score_o2 = score(o2);
		return (score_o1 - score_o2);
	}


}

