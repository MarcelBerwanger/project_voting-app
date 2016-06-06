package datenbank;

/**
 * "u" vor den Variablen steht für Übergeben, also Variablen
 * deren Werte übergeben werden.
 * 
 * @author Marcel
 */

public class tab_signIn {
	public tab_signIn(String uben, String uemail, String uklass, String upass){
		benutzername = uben;
		email = uemail;
		klasse = uklass;
		passwort = upass;
	}
	
	private String benutzername;
	private String email;
	private String klasse;
	private String passwort;
	
	/**
	 * Diese Methode dient dazu einen neu erstellten Benutzer in
	 * der Datenbank zu speichern
	 * 
	 * Arbeitsweise: Prepared Statements -> fertiger String wird an die API geschickt
	 */
	public void save_mem(){
		//Aufruf der API-Methode mit dem SQL-String
		//Fehler werfen wenn Benutzer/ email schon vorhanden!!! (return-Wert der Methode!)
	}
}
