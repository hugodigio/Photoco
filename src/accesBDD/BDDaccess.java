package accesBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class BDDaccess {
	public static void main(String[] args) {
	    try {
	      Class.forName("com.mysql.jdbc.Driver");
	         
	      String url = "jdbc:mysql://154.49.211.33/wgaizq_adigio";  // protocole,driver a utiliser://adresse de la base / nom de base
	      String user = "wgaizq_adigio";
	      String passwd = "20sur20";
	         
	      Connection conn = DriverManager.getConnection(url, user, passwd);
	         
	      //Création d'un objet Statement
	      Statement state = conn.createStatement();
	      //L'objet ResultSet contient le résultat de la requête SQL
	      ResultSet result = state.executeQuery("SELECT * FROM Artiste");
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

	      result.close();
	      state.close();
	         
	    } catch (Exception e) {
	      e.printStackTrace();
	    }      
	  }
}
