package datenbank;
/**
 * Voraussetzung: Benutzer der angemeldet ist wird gespeichert!!! (PID)
 * @author Marcel
 *
 */
public class tab_votingGeben {
	
	public tab_votingGeben(String uben, String urestaurant){
		benutzername = uben;
		restaurantId = urestaurant;
	}
	
	private String benutzername;
	private String restaurantId;
	
	/**
	 * Das abgegebene Vote in der Datenbank speichern
	 */
	public void save_vote(){
		//Prepared Statement
	}
}
