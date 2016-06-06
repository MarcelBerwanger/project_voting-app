package datenbank;

public class tab_createRestaurant {
	
	public tab_createRestaurant(String uname, String uadd, String ukat){
		name = uname;
		adresse = uadd;
		kategorie = ukat;
	}
	
	private String name;
	private String adresse;
	private String kategorie;
	
	/**
	 * Das angelegte Restaurant wird in der Datenbank gespeichert
	 */
	public void save_restaurant(){
		//Prepared Statement
		//return ob schon gibt (über Name)
	}
}
