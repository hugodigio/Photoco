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
	      
	      
	      /*
	      //L'objet ResultSet contient le résultat de la requête SQL
	      result = state.executeQuery("SELECT * FROM Artiste");
	      //On récupère les MetaData
	      ResultSetMetaData resultMeta = result.getMetaData();
	         
	      System.out.println("\n**********************************");
	      //On affiche le nom des colonnes
	      for(int i = 1; i <= resultMeta.getColumnCount(); i++)
	        System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
	         
	      System.out.println("\n**********************************");
	         
	      while(result.next()){         
	        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
	          System.out.print("\t" + result.getObject(i).toString() + "\t |");
	            
	        System.out.println("\n---------------------------------");

	      }

	      */
	         
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
	
	/*
	public Photographe getPhotographe(int id) {
		Photographe p = null;
		try {
			result = state.executeQuery("SELECT * FROM Photographe WHERE id_photographe="+id);
			ResultSetMetaData resultMeta = result.getMetaData();
		         
		      result.next();
		      p.setNom(result.getObject(2).toString());
		      p.setPrenom(result.getObject(2).toString());
		      p.setAge(result.getInt(6));
		      p.setPays(result.getObject(2));
		      
		} catch (Exception e) {
			System.out.println("erreur lors de la récupération de l'artiste "+id+" :\n"+e.getMessage());
		}
		return p;
	}
	*/
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
		int i = 0;
		try {
			ResultSet resultP; 		// contiendra le résultat de la requête effectué pour Photographe
			ResultSet resultS; 		// contiendra le résultat de la requête effectué pour Specialite
			ResultSet resultAuS;	// contiendra le résultat de la requête effectué pour AUneSpecialite
			ResultSet resultPays;	// contiendra le résultat de la requête effectué pour Pays
			ResultSet resutltC;		//// contiendra le résultat de la requête effectué pour Continent
			
			//les hashmaps servent a retenir la correspondance ID/Variable, pour éviter les doublons
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
			
			//variables temporaires
			Specialite sp = null;
			AUneSpecialite aus = null;
			Continent ct = null;
			Pays pays = null;
			Photographe p = null;	
			
			
			
			requete = "SELECT * FROM Photographe;";
			resultP = state.executeQuery(requete);
			while(resultP.next()) {
				i++;
				//ajout du photographe
				
				//traitement des liaisons avec la table AUneSpecialite
				requete =  "SELECT * FROM AUneSpecialite ";
				requete += "WHERE id_photographe="+resultP.getInt(ID)+";";
				if(!isEmpty(requete)) {
					/*resultAuS = state.executeQuery(requete);
					while(resultAuS.next()) {
						requete =  "SELECT * FROM Specialite ";
						requete += "WHERE id_specialite="+resultAuS.getInt(ID_SPECIALITE)+";";
						if(!isEmpty(requete)) {
							//ajout de la specialite de la personne en cours de traitement si elle n'existe pas deja
							resultS = state.executeQuery(requete);
							if(ids_specialites.get(resultS.getInt(ID)) != null){
								sp = new Specialite(resultS.getString(NOM));
								ids_specialites.put(resultS.getInt(ID), sp);
							}
						} else {
							System.out.println("la specialité avec id "+resultAuS.getInt(ID_SPECIALITE)+" n'existe pas!");
						}
						
						
					}*/
					
				} else {
					System.out.println("Le photographe "+ resultP.getString(NOM)+" "+resultP.getString(PRENOM)+" n'a pas de specialite");
				}
				
				//suite de l'ajout de photographe
			}
			//Specialites
			ResultSet resultSpecialite;
			resultSpecialite = state.executeQuery("SELECT nom FROM Specialite");
			ResultSetMetaData resultMeta = resultSpecialite.getMetaData();
			while(resultSpecialite.next()) {
				specialites.add(new Specialite(resultSpecialite.getObject(1).toString()));
			}
		} catch (SQLException e) {
			System.out.println("erreur lors de la récupération des données: "+i+"\n"+e.getMessage());
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
