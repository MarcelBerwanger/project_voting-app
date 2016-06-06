package datenbank;

public class tab_bewertung {
	
	public tab_bewertung(String uben, int uresId, double ustern, String ubewer){
		
		benutzer=uben;
		restaurantId = uresId;
		sterne = ustern;
		bewertung_text = ubewer;
	}
	
	private String benutzer;
	private int restaurantId;
	private double sterne;
	private String bewertung_text;
	
	/**
	 * Die abgegebene Bewertung wird in die Datenbank gespechert
	 */
	public void saveBewertung(){
		//Prepared Statement
		//Return als bool, ob dieser Benutzer schon
		//eine Bewertung hat (false = Fehler)
	}
}
