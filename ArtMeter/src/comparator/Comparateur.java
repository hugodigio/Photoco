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
		
		else if ( photographe.getAge()-ideal.getAge() > 0 && photographe.getAge()-ideal.getAge() < 101)
			score +=30;
		
		else if ( photographe.getAge()-ideal.getAge() > 100 && photographe.getAge()-ideal.getAge() < 251)
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
		
		return score;
	}
	
	
	public int compare(Photographe o1, Photographe o2) {
		int score_o1 = score(o1);
		int score_o2 = score(o2);
		return (score_o1 - score_o2);
	}


}

