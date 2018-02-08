package accesBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import classes.*;


/** classe <b> BDaccess </b> qui g�re l'interaction avec la base de donn�es */

public class BDaccess {
	
	
	/** variable qui g�re la connexion � la base de donn�es */
	private Statement state;
	
	/* differentes arraylist qui garde en memoire les tables de la bd sous forme de classes*/
	private ArrayList<Photographe> photographes;
	private ArrayList<Pays> pays;
	private ArrayList<Continent> continents;
	private ArrayList<AUneSpecialite> aUneSpecialtes;
	private ArrayList<Specialite> specialites;
	//les hashmaps servent a retenir la correspondance ID/Variable, pour �viter les doublons
	HashMap<Integer, Photographe> ids_photographes;
	HashMap<Integer, Pays> ids_pays;
	HashMap<Integer, Continent> ids_continents;
	HashMap<Integer, Specialite> ids_specialites;
	
	//CONSTANTES
	
	//pour photographe / Continent / Specialite
	final int ID = 1;
	final int NOM = 2;
	//pour photographe
	final int PRENOM = 3;
	final int AGE = 6;
	final int PRIX = 7;
	final int ID_PAYS = 8;
	//pour AUneSpecialite
	final int ID_SPECIALITE = 2;
	final int ANNEE_EXPERIENCES = 3;
	//pour pays
	final int ID_CONTINENT = 2;
	final int NOM_PAYS = 3;
	//pour continent
	final int DISTANCE = 3;
	//specialite
	final int GROUPE = 3;
	
	
	/**Constructeur de la classe BDacces. Le constucteur �tablie une connexion � la base de donn�es */
	public BDaccess() {
	    try {
	      Class.forName("com.mysql.jdbc.Driver");
	         
	      String url = "jdbc:mysql://154.49.211.33/wgaizq_adigio";  // protocole,driver a utiliser://adresse de la base / nom de base
	      String user = "wgaizq_adigio";
	      String passwd = "20sur20";
	         
	      Connection conn = DriverManager.getConnection(url, user, passwd);
	         
	      //Cr�ation d'un objet Statement
	      state = conn.createStatement();
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }      
	  }
	
	public void closeConnection() {
		try {
			state.close();
		} catch (Exception e) {
			System.out.println("erreur lors de la fermeture de la base de donn�es:\n"+e.getMessage());
		}
		
	}
	
	public ArrayList<Photographe> getPhotographes(){
		return photographes;
	}
	
	public ArrayList<Pays> getPays(){
		return pays;
	}
	
	public ArrayList<Continent> getContinents(){
		return continents;
	}
	
	public ArrayList<Specialite> getSpecialites(){
		return specialites;
	}
	
	public ArrayList<AUneSpecialite> getAUneSpecialite(){
		return aUneSpecialtes;
	}
	
	public void requestAll() {
		
		/* nous commen�ons par r�cup�rer la classe centrale de la base de donn�es: photographe.
		 * a chaque fois que nous r�cup�rons une cl� �trang�re, on r�cup�re toutes les donn�es
		 * qui sont associ� � cette cl� �trang�re */

		try {
			ResultSet result; 		// contiendra le r�sultat de la requ�te effectu�
			
			photographes  = new ArrayList<>();
		    pays          = new ArrayList<>();
		    aUneSpecialtes= new ArrayList<>();
		    specialites   = new ArrayList<>();
		    continents    = new ArrayList<>();
			
			//les hashmaps servent a retenir la correspondance ID/Variable, pour �viter les doublons
			ids_photographes = new HashMap<>();
			ids_pays         = new HashMap<>();
			ids_continents   = new HashMap<>();
			ids_specialites  = new HashMap<>();
			
			//variable qui stocke les requ�tes a executer (pour plus de clart�);
			String requete;
			
			
			//importation des donn�es de table photographe
			requete = "SELECT * FROM Photographe;";
			result = state.executeQuery(requete);
			ArrayList<Integer> fk_pays = new ArrayList<>(); 
			while(result.next()) {
				Photographe p = new Photographe();
				ids_photographes.put(result.getInt(ID), p);
				p.setNom(result.getString(NOM));
				p.setPrenom(result.getString(PRENOM));
				p.setAge(result.getInt(AGE));
				p.setPrixPrestation(result.getInt(PRIX));
				//nous stockons la cl� �trang�re id_pays dans une Arraylist
				//afin de pouvoir la traiter apr�s que la table pays soit import�
				fk_pays.add(result.getInt(ID_PAYS));
				photographes.add(p);
			}
			
			//importation des donn�es de Specialite
			result = state.executeQuery("SELECT * FROM Specialite");
			while(result.next()) {
				Specialite s = new Specialite(result.getObject(NOM).toString(),result.getInt(GROUPE));
				specialites.add(s);
				ids_specialites.put(result.getInt(ID), s);
			}
			
			//importation de AUneSpecialite
				//utilisation des differents Hmap pour faire la correspondande ID/Objet
			requete = "SELECT * FROM AUneSpecialite;";
			result = state.executeQuery(requete);
			while(result.next()) {
				AUneSpecialite aus = new AUneSpecialite();
				if(ids_photographes.containsKey(result.getInt(1))) {
					aus.setPhotographe(ids_photographes.get(result.getInt(1)));
				}
				if(ids_specialites.containsKey(result.getInt(ID_SPECIALITE))) {
					aus.setSpecialite(ids_specialites.get(result.getInt(ID_SPECIALITE)));
				}
				aus.setExperience(result.getInt(ANNEE_EXPERIENCES));
				aUneSpecialtes.add(aus);
				
			}
			
			//importation de Continent
			requete = "SELECT * FROM Continent;";
			result = state.executeQuery(requete);
			while(result.next()) {
				Continent ct = new Continent();
				ct.setNom(result.getString(NOM));
				ct.setDistance(result.getInt(DISTANCE));
				continents.add(ct);
				ids_continents.put(result.getInt(ID), ct);
			}
			
			//importation de Pays
			requete = "SELECT * FROM Pays;";
			result = state.executeQuery(requete);
			while(result.next()) {
				Pays p = new Pays();
				p.setNom(result.getString(NOM_PAYS));
				if(ids_continents.containsKey(result.getInt(ID_CONTINENT))) {
					p.setContinent(ids_continents.get(result.getInt(ID_CONTINENT)));
				}
				pays.add(p);
				ids_pays.put(result.getInt(ID), p);
				
			}
			
			
			//on lie les cl� �trang�res de Photographe avec les Objets Pays correspondant
			int i=0;
			if(fk_pays.size() != photographes.size()) {
				System.out.println("Il n'y a pas autant de photographes que de cle etrangeres en attente d'association!"
						+ "\nnombre photographes: "+photographes.size()+", nombre cles: "+fk_pays.size());
			}else {
				for(Photographe p : photographes) {
					p.setPays(ids_pays.get(fk_pays.get(i)));
					i++;
				}
			}
			
			
			
			
		} catch (SQLException e) {
			System.out.println("erreur lors de la r�cup�ration des donn�es: \n"+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Pays> requestPays(){
		String requete = "SELECT * FROM Continent;";
		ResultSet result;
		continents = new ArrayList<Continent>();
		pays = new ArrayList<Pays>();
		ids_continents = new HashMap<Integer, Continent>();
		ids_pays = new HashMap<Integer, Pays>();
		try {
			result = state.executeQuery(requete);
			while(result.next()) {
				Continent ct = new Continent();
				ct.setNom(result.getString(NOM));
				ct.setDistance(result.getInt(DISTANCE));
				continents.add(ct);
				ids_continents.put(result.getInt(ID), ct);
			}
			
			//importation de Pays
			requete = "SELECT * FROM Pays;";
			result = state.executeQuery(requete);
			while(result.next()) {
				Pays p = new Pays();
				p.setNom(result.getString(NOM_PAYS));
				if(ids_continents.containsKey(result.getInt(ID_CONTINENT))) {
					p.setContinent(ids_continents.get(result.getInt(ID_CONTINENT)));
				}
				pays.add(p);
				ids_pays.put(result.getInt(ID), p);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pays;
	}
	
	public ArrayList<Specialite> requestSpecialites(){
		specialites = new ArrayList<Specialite>();
		ids_specialites = new HashMap<Integer, Specialite>();
		//importation des donn�es de Specialite
		
		try{
			ResultSet result = state.executeQuery("SELECT * FROM Specialite");
			while(result.next()) {
				Specialite s = new Specialite(result.getObject(NOM).toString(),result.getInt(GROUPE));
				specialites.add(s);
				ids_specialites.put(result.getInt(ID), s);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return specialites;
	}
	
	/** supprimer le photographe en parametre � la base de donn�e **/
	//@SuppressWarnings("unlikely-arg-type")
	public void supprimerPhotographe(Photographe photographe) {
		/*chercher si le photographe est dans la liste des photographes et r�cup�rer son ID*/
		Integer id = -1;
		if(ids_photographes.containsValue(photographe)) {
			for (Integer o : ids_photographes.keySet()) {
			      if (ids_photographes.get(o).equals(photographe)) {
			    	  id = o;
			      }
			 }
			/*supprimer la la ligne ou le photographe est present dans la table AUneSpecialite*/
			if(photographes.contains(photographe) && id != -1) {
				if(aUneSpecialtes.contains(photographe)) {
					String requete = "DELETE * FROM AUneSpecialite WHERE id_photographe="+id+";";
					try {
						ResultSet result = state.executeQuery(requete);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(AUneSpecialite aus : aUneSpecialtes) {
						if(aus.getPhotographe() == photographe) {
							aUneSpecialtes.remove(aus);
						}
					}
					
				}
				String requete = "DELETE id_photographe="+id+" FROM Photographe";
				photographes.remove(photographe);
				ids_photographes.remove(id);
				
			}
		}
				
		/*supprimer le photographe*/
		
	}
	
	/**
	 * ajouter le photographe en parametre � la base de donn�e
	 * 
	 * @param photographe photographe que l'on doit ajouter
	 */
	public void ajouterPhotographe(Photographe photographe, Pays pays) {
		String requete = null;
		Integer id_pays = 0;
		requete = "INSERT INTO `Photographe` (`id_photographe`, `Nom`, `Prenom`, `Photo`, `Lienportfolio`, `age`, `prixPrestation`, `id_pays`)";
		for (Integer o : ids_photographes.keySet()) {
		      if (ids_photographes.get(o).equals(photographe)) {
		    	  id_pays = o;
		      }
		 }
		requete += "VALUES ('', '"+photographe.getPrenom()+"', '"+photographe.getNom()+"', '', '', '"+photographe.getAge()+"', '"+photographe.getPrixPrestation()+"', '"+id_pays+"')";
		requestAll();
	}
	
	/** modifier le photographe en parametre � la base de donn�e 
	 * 
	 * @param photographe Photographe sur lequel on va effectuer les modifications
	 * */
	public void modifierPhotographe(Photographe photographe) {
		Integer id = 0, id_pays = 0;
		String requete;
		/*chercher id du photographe dans la hashmap*/
		if(ids_photographes.containsValue(photographe)) {
			for (Integer o : ids_photographes.keySet()) {
			      if (ids_photographes.get(o).equals(photographe)) {
			    	  id = o;
			      }
			 }
		}
		/*il faut r�cup�rer l'id du pays du photographe*/
		if(ids_pays.containsValue(photographe.getPays())) {
			for (Integer o : ids_photographes.keySet()) {
			      if (ids_pays.get(o).equals(photographe.getPays())) {
			    	  id_pays = o;
			      }
			 }
		}
		/*effectuer les modifications sur la base de donn�es*/
		requete = "UPDATE `Photographe`";
		requete += " SET `Nom` = '"+photographe.getNom()+"', `Prenom` = '"+photographe.getPrenom()+"', `age` = '"+photographe.getAge()+"', `prixPrestation` = '"+photographe.getPrixPrestation()+"', `id_pays` = '"+id_pays+"' WHERE `Photographe`.`id_photographe` = "+id+";";
		
		
		
	}
	
	/** renvoi true si la requ�te est vide, false sinon
	 * 
	 * @param sql requete que l'on veux tester.
	 */
	public Boolean isEmpty(String sql) {
		Boolean bool = false;
		try {
			//enleve le caractere ; a la fin de la requete si il s'y trouve
			if(sql.endsWith(";")){
				sql = (new StringBuilder(sql)).deleteCharAt(sql.length()-1).toString();
			}
			System.out.println("requete: "+sql);
			//execute la requete avec 1 seul resultat maximum (afin de limiter la charge pour le serveur SQL)
			ResultSet result = state.executeQuery(sql+" LIMIT 1;");
			if(!result.next()) {
				return true;
			}
			result.close();
		} catch (Exception e) {
			System.out.println("Erreur SQL :\n \t"+e.getMessage());
		}
		
		return bool;
	}
	
	public void TestsSQL(String sql) {
		ResultSet result;
		try {
			result = state.executeQuery(sql);
			ResultSetMetaData resultMeta = result.getMetaData();
		    System.out.println("\n**********************************");
		    for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		        System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
		    System.out.println("\n**********************************");
		    while(result.next()){         
		        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		          System.out.print("\t" + result.getObject(i).toString() + "\t |");   
		    System.out.println("\n---------------------------------");
		    }
			    
		} catch (Exception e) {
			System.out.println("Erreur SQL :\n \t"+e.getMessage());
		}
	}
}

//TODO remplacer les erreurs afficher en println par un systeme d'exception
//TODO remplacer RequestAll() par des requetes plus simple

//TODO am�liorer les requetes l'acc�s a la base SQL pour que les identifiants ne soit pas lisible.
