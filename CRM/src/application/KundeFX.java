package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class KundeFX {

	private Kunde modellKunde;
	private SimpleIntegerProperty idKunde;
	private SimpleStringProperty name;
	private SimpleStringProperty plz;
	private SimpleStringProperty land;
	private SimpleStringProperty ort;
	private SimpleStringProperty strasse;
	private SimpleIntegerProperty vkgr; 
	private SimpleStringProperty suchbegriff;

	public KundeFX(Kunde modellKunde) {
		super();
		this.modellKunde = modellKunde;
		idKunde = new SimpleIntegerProperty(modellKunde.getIdKunde());
		name = new SimpleStringProperty(modellKunde.getName());
		plz = new SimpleStringProperty(modellKunde.getPlz());
		land = new SimpleStringProperty(modellKunde.getLand());
		ort = new SimpleStringProperty(modellKunde.getOrt());
		strasse = new SimpleStringProperty(modellKunde.getStrasse());
		vkgr = new SimpleIntegerProperty(modellKunde.getVkgr());
		suchbegriff = new SimpleStringProperty(modellKunde.getSuchbegriff());
	}
	
	public Kunde getModellKunde() {
		return modellKunde;
	}
	
	public int getIdKunde() {
		return idKunde.get();
	}
	public SimpleIntegerProperty idKundeProperty() {
		return idKunde;
	}
	
	public String getName() {
		return name.get();
	}
	
	public SimpleStringProperty nameProperty() {
		return name;
	}
	
	public String getPlz() {
		return plz.get();
	}
	
	public SimpleStringProperty plzProperty() {
		return plz;
	}
	
	public String getLand() {
		return land.get();
	}
	
	public SimpleStringProperty landProperty() {
		return land;
	}
	
	public String getOrt() {
		return ort.get();
	}
	
	public SimpleStringProperty ortProperty() {
		return ort;
	}
	
	public String getStrasse() {
		return strasse.get();
	}
	
	public SimpleStringProperty strasseProperty() {
		return strasse;
	}
	
	public int getVkgr() {
		return vkgr.get();
	}
	
	public SimpleIntegerProperty vkgrProperty() {
		return vkgr;
	}
	
	public String getSuchbegriff() {
		return suchbegriff.get();
	}
	
	public SimpleStringProperty suchbegriffProperty() {
		return suchbegriff;
	}
}