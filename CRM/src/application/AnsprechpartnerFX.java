package application;


import javafx.beans.property.SimpleStringProperty;

public class AnsprechpartnerFX {

	private Ansprechpartner modellAnsprechpartner;
	private SimpleStringProperty vorname;
	private SimpleStringProperty nachname;
	private SimpleStringProperty telefon;
	private SimpleStringProperty email;
	private SimpleStringProperty funktion;
	private SimpleStringProperty kundename;
	
	public AnsprechpartnerFX(Ansprechpartner modellAnsprechpartner) {
		this.modellAnsprechpartner = modellAnsprechpartner;
		vorname = new SimpleStringProperty(modellAnsprechpartner.getVorname());
		nachname = new SimpleStringProperty(modellAnsprechpartner.getNachname());
		telefon = new SimpleStringProperty(modellAnsprechpartner.getTelefon());
		email = new SimpleStringProperty(modellAnsprechpartner.getEmail());
		funktion = new SimpleStringProperty(modellAnsprechpartner.getFunktion());
		kundename = new SimpleStringProperty(modellAnsprechpartner.getKunde().getName());
	}
	
	public Ansprechpartner getmodellAnsprechpartner() {
		return modellAnsprechpartner;
	}
	
	public String getVorname() {
		return vorname.get();
	}
	public SimpleStringProperty vornameProperty() {
		return vorname;
	}
	
	public String getNachname() {
		return nachname.get();
	}
	public SimpleStringProperty nachnameProperty() {
		return nachname;
	}
	
	public String getTelefon() {
		return telefon.get();
	}
	public SimpleStringProperty telefonProperty() {
		return telefon;
	}
	
	public String getEmail() {
		return email.get();
	}
	public SimpleStringProperty emailProperty() {
		return email;
	}
	
	public String getFunktion() {
		return funktion.get();
	}
	public SimpleStringProperty funktionProperty() {
		return funktion;
	}
	
	public String getKundename() {
		return kundename.get();
	}
	public SimpleStringProperty kundenameProperty() {
		return kundename;
	}
	

}
