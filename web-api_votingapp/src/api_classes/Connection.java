//Anleitung/ Quelle: http://openbook.rheinwerk-verlag.de/java7/1507_13_003.html#top
package api_classes;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jws.*;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Endpoint;

import database_classes.Connection_var;
import database_classes.Tab_bewertung;
import database_classes.Tab_freund;
import database_classes.Tab_kategorie;
import database_classes.Tab_person;
import database_classes.Tab_restaurant;
import database_classes.Tab_voting;


/*
 * Jede Klasse die einen WebService darstellt muss die folgenden 2
 * Parameter besitzen.
 * @WebService legt den namen des Webservices fest
 * @SOAPBinding legt die Art der Nachrichten fest, es gibt (Dokument oder RPC)
 * 
 * weiters muss jede Methode mit @WebMethod gekennzeichnet sein, in @WebMethod
 * legt das Feld operationName fest wie der Name der Methode in der API lautet.
 * Wenn die Methode einen Returntyp aufweist muss man sie neben @WebMethod mit
 * @WebResult kennzeichnen, weider legt das Feld name (in Klammern) den Namen des
 * Ergenisses in der Api fest.
 */
@WebService(name="ConnectionService", endpointInterface="api_classes.Connection")
@SOAPBinding(style= SOAPBinding.Style.RPC)
public class Connection extends Connection_var implements ConnectionInt{
	
	/*Konstruktoren der Klasse Connection, wird keine Url
	 * übergeben, wird sie auf "http://localhost:8080/services"
	 * festgelegt.
	 */
	public Connection(){
		this("http://localhost:8080/services?wsdl");
		databaseConnection();
	}
	public Connection(String gweburl){
		weburl=gweburl;
	}
	
	private String weburl;
	private Endpoint endpoint;
	private ResultSet sqlResult;
	
	//Datenbank-Verbindungsvariablen
	private String hostname = "localhost";
	private String port = "3306";
	private String dbname = "votingapp"; //Name der Datenbank!
	private java.sql.Connection con; //Wird in databaseConnection initialisiert
	
	public void databaseConnection(){
		//Nur wenn noch keine Connection existiert, soll eine neue erstellt werden, da es sonst zu fehlern kommt
		if(con==null){
			String url = "jdbc:mysql://"+hostname+":"+port+"/"+dbname ;
			try {
				String user = "root";
				String password = "";
				try 
				{
					Class.forName( "com.mysql.jdbc.Driver" );
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				con = DriverManager.getConnection(url, user, password);
				System.out.println("Connected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Der WebService muss veröffentlich werden, dies Geschieht in dieser Methode
	//die URL des Webservices steht in dem feld weburl
	//Hinter der URL muss noch "?wsdl" stehen
	public void runService(){
		endpoint = Endpoint.publish(weburl, this);
	}
	
	//Stopt den Service in dem Try/Catch Block
	public void stopService(){
		endpoint.stop();
	}
	
	//Verbindung zur Datenbank
	public void requestData(String ustmt){
		try{
			Statement stmt = con.createStatement();
			sqlResult = stmt.executeQuery(ustmt);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//Schreiben von Daten in die Datenbank
	public boolean writeData(String ustmt)
	{
		try{
			Statement stmt = con.createStatement();
			stmt.execute(ustmt);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/*********************************************************************************************
	 * *******************************************************************************************
	 * *******************************************************************************************
	 * *** Ab Hier stehen die Methoden der einzelnen Dataklassen zum abrufen der Daten von der ***
	 * *** Datenbank																		   ***
	 * *******************************************************************************************
	 * *******************************************************************************************
	 * *******************************************************************************************
	 * **/
	

	/***************************************************************
	 * **** Klasse: Tab_person									****
	 * **** Methoden: savePerson, deletePerson, getAllPersons,	****
	 * **** 		  check_logIn								****
	 * *************************************************************/
	public boolean savePerson(String uname, String uemail, String uklasse, String upasswort, String urolle){
		//Return als bool, ob dieser Benutzer schon
		//existiert (false = existiert)
		String stmt = "SELECT pid FROM person WHERE pid='"+uname+"'";
		requestData(stmt);
		
		//Probieren, ob das Abfrage-Ergebnis leer ist; return false wenn leer, sonst true
		try{
			sqlResult.next();
			sqlResult.getString("pid");
			//Wenn etwas gefunden wird dann abbruch, weil es schon gibt!!
			return false;
		}catch(Exception e){}
		
		//Person in die Datenbank einfügen
		stmt = "INSERT INTO person (pid, email, klasse, passwort, rolle) ";
		stmt += "VALUES('"+uname+"','"+uemail+"','"+uklasse+"','"+upasswort+"','"+urolle+"')";
		return writeData(stmt);
	}
	
	public boolean deletePerson(String upid){
		//Return als bool, ob diese Person schon
		//existiert, damit es gelöscht werden kann (false = existiert nicht)
		String stmt = "SELECT * FROM person WHERE pid='"+upid+"'";
		requestData(stmt);
		
		//Wenn die Person nicht vorhanden ist, dann Abbruch der Methode
		try{
			sqlResult.next();
			sqlResult.getString("pid");
		}catch(Exception e){return false;}
		
		//Eigentliches Löschen, nach der obigen "Überprüfung".
		//Return true, wenn erfolgrich.
		stmt = "DELETE FROM person WHERE pid='"+upid+"'";
		return writeData(stmt);
	}
	
	public Tab_person[] getAllPersons(){
		//Zuerst ArrayList clearen, damit keine Doppelten vorkommen
		Tab_person.allPersonen.clear();
		
		//Statement erzeugen & loschchicken
		String stmt = "SELECT * FROM person";
		requestData(stmt);
		
		//Alle abgefragten Personen in die ArrayList eintragen
		try{
			while(sqlResult.next()){
				String pid =sqlResult.getString("PID");
				String mail = sqlResult.getString("Email");
				String klass = sqlResult.getString("Klasse");
				String pass = sqlResult.getString("Passwort");
				String role = sqlResult.getString("Rolle");
				Tab_person.allPersonen.add(new Tab_person(pid, mail, klass, pass, role));
			}
		}catch(SQLException e){}
		
		return Tab_person.allPersonen.toArray(new Tab_person[Tab_person.allPersonen.size()]);
	}
	
	public boolean check_logIn(String ubenutzer, String upasswort){
		String stmt = "SELECT pid FROM Person WHERE pid='"+ubenutzer+"'AND passwort="+upasswort;
		requestData(stmt);
		
		//Probieren, ob das Abfrage-Ergebnis leer ist; return false wenn leer, sonst true
		try{
			sqlResult.next();
			sqlResult.getString("pid");
		}catch(Exception e){return false;}
		return true;
	}
	
	
	/***************************************************************
	 * **** Klasse: Tab_restaurant								****
	 * **** Methoden: save_restaurant, delete_restaurant,		****
	 * **** 		  getAllRestaurants							****
	 * *************************************************************/
	
	public boolean save_restaurant(String urid, String uadresse, String ukategorie){
		//Return als bool, ob dieses Restaurant schon
		//existiert (false = existiert)
		String stmt = "SELECT * FROM restaurant WHERE rid='"+urid+"'";
		requestData(stmt);
		
		//Wenn das Restaurant vorhanden ist, dann abbruch der Methode
		//Probieren, ob das Abfrage-Ergebnis leer ist; return false = vorhanden
		try{
			sqlResult.next();
			sqlResult.getString("rid");
			//Wenn etwas gefunden wird dann abbruch, weil es schon gibt!!
			return false;
		}catch(Exception e){}
		
		//Restaurant in die Datenbank einfügen
		stmt = "INSERT INTO restaurant (rid, kategorie, adresse) ";
		stmt += "VALUES('"+urid+"','"+ukategorie+"','"+uadresse+"')";
		return writeData(stmt);
	}
	
	public boolean deleteRestaurant(String uname, String ukategorie){
		//Return als bool, ob dieses Restaurant schon
		//existiert, damit es gelöscht werden kann (false = existiert nicht)
		String stmt = "SELECT * FROM restaurant WHERE rid='"+uname+"' AND kategorie='"+ukategorie+"'";
		requestData(stmt);
		
		//Wenn das Restaurant nicht vorhanden ist, dann Abbruch der Methode
		try{
			sqlResult.next();
			sqlResult.getString("rid");
		}catch(Exception e){return false;}
		
		//Eigentliches löschen, nach der obigen "Überprüfung".
		//Return true, wenn erfolgrich
		stmt = "DELETE FROM restaurant WHERE rid='"+uname+"' AND kategorie="+ukategorie+"'";
		return writeData(stmt);
	}
	
	public Tab_restaurant[] getAllRestaurants(){
		//Zuerst ArrayList clearen, damit keine Doppelten vorkommen
		Tab_restaurant.allRestaurants.clear();
		
		//Statement erzeugen & loschchicken
		String stmt = "SELECT * FROM restaurant";
		requestData(stmt);
		
		//Alle abgefragten Restaurants in die ArrayList eintragen
		try{
			while(sqlResult.next()){
				String rid =sqlResult.getString("RID");
				String kat = sqlResult.getString("Kategorie");
				String add = sqlResult.getString("Adresse");
				Tab_restaurant.allRestaurants.add(new Tab_restaurant(rid, kat, add));
			}
		}catch(SQLException e){}
		return Tab_restaurant.allRestaurants.toArray(new Tab_restaurant[Tab_restaurant.allRestaurants.size()]);
	}
	
	
	/***************************************************************
	 * **** Klasse: Tab_bewertung								****
	 * **** Methoden: saveBewertung, deleteBewertung,			****
	 * **** 		  getBewertungPerson, getBewertungRestaurant****
	 * *************************************************************/
	
	public boolean saveBewertung(String upid, String urid, double usterne, String utext){
		//Return als bool, ob dieser Benutzer schon
		//eine Bewertung abgegeben hat (false = Fehler bzw. Bewertung vorhanden)
		String stmt = "SELECT pid, rid FROM bewertung WHERE pid='"+upid+"' AND rid='"+urid+"'";
		requestData(stmt);
		//Wenn die Bewertung schon vorhanden ist Abbruch der Methode mit return false
		try{
			sqlResult.next();
			sqlResult.getString("rid");
			//Wenn etwas gefunden wird dann abbruch, weil es schon gibt!!
			return false;
		}catch(Exception e){}
		
		//Bewertung in die Datenbank einfügen
		stmt = "INSERT INTO bewertung (pid, rid, sterne, beschreibung) ";
		stmt += "VALUES('"+upid+"','"+urid+"',"+usterne+",'"+utext+"')";
		return writeData(stmt);
	}
	
	public boolean deleteBewertung(String upid, String urid){
		//Return als bool, ob diese Bewertung schon
		//existiert damit es gelöscht werden kann (false = existiert nicht)
		String stmt = "SELECT pid, rid FROM bewertung WHERE rid='"+urid+"' AND pid='"+upid+"'";
		requestData(stmt);
		
		//Wenn die Bewertung nicht vorhanden ist, dann Abbruch der Methode mit return false
		try{
			sqlResult.next();
			sqlResult.getString("rid");
			return false;
		}catch(Exception e){}
		
		//Eigentliches löschen, nach der obigen "Überprüfung". Returnt "true", wenn erfolgrich
		stmt = "DELETE FROM bewertung WHERE rid='"+urid+"' AND pid="+upid+"'";
		return writeData(stmt);
	}
	
	public Tab_bewertung[] getBewertungRestaurant(String urid){
		//Zurücksetzten der ArrayList, damit keine Doppelten vorkommen
		Tab_bewertung.bewertungRestaurant.clear();
		
		//Statement zum Abfragen aller Bewertungen eines Restaurants
		String stmt = "SELECT * FROM bewertung WHERE rid='"+urid+"'";
		requestData(stmt);
		
		try {
			while(sqlResult.next()){
				String tpid = sqlResult.getString("pid");
				String trid = sqlResult.getString("rid");
				int tstern = sqlResult.getInt("sterne");
				String tbeschr = sqlResult.getString("beschreibung");
				Tab_bewertung.bewertungRestaurant.add(new Tab_bewertung(tpid, trid, tstern, tbeschr));
			}
		} catch (SQLException e) {}
		
		//Ausgeben der ArrayList
		return Tab_bewertung.bewertungRestaurant.toArray(new Tab_bewertung[Tab_bewertung.bewertungRestaurant.size()]);
	}
	
	public Tab_bewertung[] getBewertungPerson(String upid){
		//Zurücksetzten der ArrayList, damit keine Doppelten vorkommen
		Tab_bewertung.bewertungPerson.clear();
		
		//Statement, welches alle Bewertungen einer Person abfragt
		String stmt = "SELECT * FROM Bewertung WHERE pid='"+upid+"'";
		requestData(stmt);
		
		//Auslesen des Resultsetes
		try {
			while(sqlResult.next()){
				String tpid = sqlResult.getString("pid");
				String trid = sqlResult.getString("rid");
				int tstern = sqlResult.getInt("sterne");
				String tbeschr = sqlResult.getString("beschreibung");
				Tab_bewertung.bewertungPerson.add(new Tab_bewertung(tpid, trid, tstern, tbeschr));
			}
		} catch (SQLException e) {}
		
		//Ausgabe des Arrays
		return Tab_bewertung.bewertungPerson.toArray(new Tab_bewertung[Tab_bewertung.bewertungPerson.size()]);
	}
	
	
	/***************************************************************
	 * **** Klasse: Tab_freund									****
	 * **** Methoden: saveFreund, deleteFreund, getAllFreunde	****
	 * *************************************************************/
	
	public boolean saveFreund(String upid, String ufid){
		//Return als bool, ob diese Freundschaft schon
		//existiert (false = existiert)
		String stmt = "SELECT pid, fid FROM freund WHERE pid='"+upid+"' AND fid='"+ufid+"'";
		requestData(stmt);
		
		//Probieren, ob das Abfrage-Ergebnis leer ist; return false vorhanden
		try{
			sqlResult.next();
			sqlResult.getString("pid");
			//Wenn etwas gefunden wird dann abbruch, weil es schon gibt!!
			return false;
		}catch(Exception e){}
		
		//Freundschaft in die Datenbank einfügen
		stmt = "INSERT INTO freund (pid, fid) ";
		stmt += "VALUES('"+upid+"','"+ufid+"')";
		return writeData(stmt);
	}
	
	public boolean deleteFreund(String upid, String ufid){
		//Return als bool, ob diese Freundschaft schon
		//existiert, damit es gelöscht werden kann (false = existiert nicht)
		String stmt = "SELECT * FROM freund WHERE pid='"+upid+"' AND fid='"+ufid+"'";
		requestData(stmt);
		
		//Wenn die Freundschaft nicht vorhanden ist, dann abbruch der Methode
		try{
			sqlResult.next();
			sqlResult.getString("pid");
		}catch(Exception e){return false;}
		
		//Eigentliches löschen, nach der obigen "Überprüfung".Returnt "true", wenn erfolgrich
		stmt = "DELETE FROM freund WHERE pid='"+upid+"' AND fid="+ufid+"'";
		return writeData(stmt);
	}
	
	public Tab_freund[] getAllFreunde(String upid){		
		//ArrayList clearen, damit Daten stimmen
		Tab_freund.allFriends.clear();
		
		//Statement erzeugen & loschchicken
		String stmt = "SELECT * FROM freunde WHERE pid='"+upid+"'";
		requestData(stmt);
		
		//Alle abgefragten Freundschaften in die ArrayList eintragen
		try{
			while(sqlResult.next()){
				String pid = sqlResult.getString("pid");
				String fid = sqlResult.getString("fid");
				Tab_freund.allFriends.add(new Tab_freund(pid, fid));
			}
		}catch(SQLException e){}
		return Tab_freund.allFriends.toArray(new Tab_freund[Tab_freund.allFriends.size()]);
	}
	
	
	/***************************************************************
	 * **** Klasse: Tab_kategorie								****
	 * **** Methoden: saveKategorie, deleteKategorie, 			****
	 * ****			  getAllKategories							****
	 * *************************************************************/
	
	public boolean saveKategorie(String ukate){
		//Return als bool, ob diese Kategorie schon
		//existiert (false = Fehler)
		String stmt = "SELECT * FROM kategorie WHERE bezeichnung='"+ukate+"'";
		requestData(stmt);
		
		//Schauen, ob die Kategorie schon existiert, wenn ja beendet die Methode mit return flase
		try{
			sqlResult.next();
			sqlResult.getString("bezeichnung");
			return false;
		}catch(Exception e){}
		
		//Kategorie in die Datenbank einfügen
		stmt = "INSERT INTO kategorie (bezeichnung) ";
		stmt += "VALUES('"+ukate+"')";
		return writeData(stmt);
	}
	
	public boolean deleteKategorie(String ukate){
		//Return als bool, ob es diese Kategorie schon
		//existiert, damit es gelöscht werden kann (false = existiert nicht)
		String stmt = "SELECT * FROM kategorie WHERE bezeichnung='"+ukate+"'";
		requestData(stmt);
		
		//Wenn die Kategorie nicht vorhanden ist, dann Abbruch der Methode
		try{
			sqlResult.next();
			sqlResult.getString("bezeichnung");
		}catch(Exception e){return false;}
		
		//Eigentliches löschen, nach der obigen "Überprüfung".
		//Return true, wenn erfolgrich
		stmt = "DELETE FROM kategorie WHERE bezeichnung='"+ukate+"'";
		return writeData(stmt);
	}
	
	public Tab_kategorie[] getAllKategories(){
		//Zuerst ArrayList clearen, damit keine Doppelten vorkommen
		Tab_kategorie.allKategories.clear();
		
		//Statement erzeugen & loschchicken
		String stmt = "SELECT * FROM kategorie";
		requestData(stmt);
		
		//Alle abgefragten Kategorien in die ArrayList eintragen
		try{
			while(sqlResult.next()){
				String kategorie =sqlResult.getString("bezeichnung");
				Tab_kategorie.allKategories.add(new Tab_kategorie(kategorie));
			}
		}catch(SQLException e){}
		
		return Tab_kategorie.allKategories.toArray(new Tab_kategorie[Tab_kategorie.allKategories.size()]);
	}
	
	/***************************************************************
	 * **** Klasse: Tab_voting									****
	 * **** Methoden: saveVote, deleteAllVotes, getAllVotes,	****
	 * ****			  getVotesRestaurant, checkVotes			****
	 * *************************************************************/
	
	public boolean saveVote(String upid, String urid){
		//Return als bool, ob dieser Benutzer schon
		//ein Vote gegeben hat (false = Fehler)
		if(!checkVote(upid))return false;
		
		//Voting in die Datenbank einfügen
		String stmt = "INSERT INTO voting (pid, rid) ";
		stmt += "VALUES('"+upid+"','"+urid+"')";
		return writeData(stmt);
	}
	
	public boolean deleteAllVotes(){
		//Statement zum Löschen der Votes
		String stmt = "DELETE * FROM voting";
		return writeData(stmt);
	}
	
	public Tab_voting[] getVotesRestaurant(String urid){
		//Clearen, damit keine Doppelten Vorkommen
		Tab_voting.allVotes.clear();
		
		//Statement, welches alle Votings in der Datenbank abrafgt
		String stmt = "SELECT * FROM voting";
		requestData(stmt);
		
		try{
			while(sqlResult.next()){
					String restaurant = sqlResult.getString("rid");
					String person = sqlResult.getString("pid");
					Tab_voting.allVotes.add(new Tab_voting(restaurant, person));
				}
		}catch(SQLException e){}
		
		return Tab_voting.allVotes.toArray(new Tab_voting[Tab_voting.allVotes.size()]);
	}
	
	public boolean checkVote(String upid){
		//Statement, welches das Vote eines Benutzers abfragt
		String stmt = "SELECT * FROM voting WHERE pid='"+upid+"'";
		requestData(stmt);
		
		//Wenn die Person schon gevotet hat, dann return true, sonst false
		try{
			sqlResult.next();
			sqlResult.getString("pid");
		}catch(Exception e){return true;}
		return false;
	}
}