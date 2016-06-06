package datenbank;

public class tab_logIn {
	
	public tab_logIn(String uben,String upass){
		benutzer = uben;
		passwort = upass;
	}
	
	private String benutzer;
	private String passwort;
	
	/**
	 * Man schaut ob der Benutzer und das Passwort in der
	 * Tabelle vorhanden sind
	 */
	public void check_logIn(){
		//Prepared Statement
		//Fehler throwen, wenn nicht vorhandne (Rückgabebool)
	}
}
