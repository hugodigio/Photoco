package comparator;

import java.util.Scanner;

/**
 * Cette classe défini un photographe et contient tous les attributs qui lui sont propre.
 * Elle permet également d'évaluer un photographe en fonction d'un autre ou d'un string pour avoir une note de proximité.
 * @author Aliyya Adira
 * @version 1.0
 */

import classes.Pays;
import classes.Photographe;
import classes.Specialite;

public class Comparator {

	private Specialite specialite;
	private Pays pays;
	private int experience;
	private int prixPrestation;
	private int age;

	/**
	 * Permet de créer une base à qui comparer.
	 * @param specialite
	 * @param pays
	 * @param experiences
	 * @param prixPrestation
	 * @param age
	 */

	//Constructeur
	public Comparator (Specialite specialite, Pays pays, int experience, int prixPrestation, int age) {
		this.specialite=specialite;
		this.pays=pays;
		this.experience=experience;
		this.prixPrestation=prixPrestation;
		this.age=age;
	}


	//Fonction qui prend en compte les criteres demandees par le client

	public Comparator getCriteria() {

		Comparator base;

		//get critere specialite
		System.out.println("Specialite?");
		Scanner sc_specialite = new Scanner(System.in);
		String string_specialite =  sc_specialite.nextLine();
		Specialite base_specialite = new Specialite(string_specialite);

		//get critere pays
		System.out.println("Pays?");
		Scanner sc_pays = new Scanner(System.in);
		String string_pays =  sc_pays.nextLine();
		Pays base_pays = new Pays(string_pays);

		//get critere experiences
		int base_experiences;
		System.out.println("Experiences?");
		Scanner sc_experiences = new Scanner(System.in);
		String string_experiences =  sc_experiences.nextLine();
		base_experiences = Integer.parseInt(string_experiences);

		//get critere prix prestations
		int base_prixPrestation;
		System.out.println("Prix Prestation?");
		Scanner sc_prixPrestation = new Scanner(System.in);
		String string_prixPrestation =  sc_prixPrestation.nextLine();
		base_prixPrestation = Integer.parseInt(string_prixPrestation);

		//get critere age
		int base_age;
		System.out.println("Age?");
		Scanner sc_age = new Scanner(System.in);
		String string_age =  sc_age.nextLine();
		base_age = Integer.parseInt(string_age);

		base = new Comparator(base_specialite, base_pays, base_experiences, base_prixPrestation, base_age);

		return base;

	}


	public void CompareSpecialite() {
	}

	public void ComparePays() {
	}

	public void CompareExperiences() {
	}

	public void ComparePrixPrestations() {
	}

	public void CompareAge() {
	}

}

