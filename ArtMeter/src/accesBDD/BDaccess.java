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


/** classe <b> BDaccess </b> qui gère l'interaction avec la base de données */

public class BDaccess {
	
	
	/** variable qui gère la connexion à la base de données */
	private Statement state;
	private ArrayList<Photographe> photographes;
	private ArrayList<Pays> pays;
	private ArrayList<Continent> continents;
	private ArrayList<AUneSpecialite> aUneSpecialtes;
	private ArrayList<Specialite> specialites;
	
	
	/**Constructeur de la classe BDacces. Le constucteur établie une connexion à la base de données */
	public BDaccess() {
	    try {
	      Class.forName("com.mysql.jdbc.Driver");
	         
	      String url = "jdbc:mysql://154.49.211.33/wgaizq_adigio";  // protocole,driver a utiliser://adresse de la base / nom de base
	      String user = "wgaizq_adigio";
	      String passwd = "20sur20";
	         
	      Connection conn = DriverManager.getConnection(url, user, passwd);
	         
	      //Création d'un objet Statement
	      state = conn.createStatement();
	      
	      photographes = new ArrayList<>();
	      pays 		   = new ArrayList<>();
	      aUneSpecialtes=new ArrayList<>();
	      specialites  = new ArrayList<>();
	      continents   = new ArrayList<>();
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }      
	  }
	
	public void closeConnection() {
		try {
			state.close();
		} catch (Exception e) {
			System.out.println("erreur lors de la fermeture de la base de données:\n"+e.getMessage());
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
		
		/* nous commençons par récupérer la classe centrale de la base de données: photographe.
		 * a chaque fois que nous récupérons une clé étrangère, on récupère toutes les données
		 * qui sont associé à cette clé étrangère */

		try {
			ResultSet result; 		// contiendra le résultat de la requête effectué
			
			//les hashmaps servent a retenir la correspondance ID/Variable, pour éviter les doublons
			HashMap<Integer, Photographe> ids_photographes = new HashMap<>();
			HashMap<Integer, Pays> ids_pays = new HashMap<>();
			HashMap<Integer, Continent> ids_continents = new HashMap<>();
			HashMap<Integer, Specialite> ids_specialites = new HashMap<>();
			
			//variable qui stocke les requêtes a executer (pour plus de clarté);
			String requete; 
			
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
			
			
			//importation des données de table photographe
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
				//nous stockons la clé étrangère id_pays dans une Arraylist
				//afin de pouvoir la traiter après que la table pays soit importé
				fk_pays.add(result.getInt(ID_PAYS));
				photographes.add(p);
			}
			
			//importation des données de Specialite
			result = state.executeQuery("SELECT * FROM Specialite");
			while(result.next()) {
				Specialite s = new Specialite(result.getObject(1).toString());
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
				if(ids_specialites.containsKey(result.getInt(2))) {
					aus.setSpecialite(ids_specialites.get(result.getInt(2)));
				}
				aus.setExperience(result.getInt(3));
				aUneSpecialtes.add(aus);
				
			}
			
			//importation de Continent
			requete = "SELECT * FROM Continent;";
			result = state.executeQuery(requete);
			while(result.next()) {
				Continent ct = new Continent();
				ct.setNom(result.getString(NOM));
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
			
			
			//on lie les clé étrangères de Photographe avec les Objets Pays correspondant
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
			System.out.println("erreur lors de la récupération des données: \n"+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/** renvoi true si la requête est vide, false sinon
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

//TODO améliorer les requetes l'accès a la base SQL pour que les identifiants ne soit pas lisible.
