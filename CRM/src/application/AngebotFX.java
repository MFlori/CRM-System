package application;

import java.util.Date;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class AngebotFX {
	
	private Angebot modellAngebot;
	private SimpleIntegerProperty idAngebot;
	private SimpleStringProperty kundeName;
	private SimpleStringProperty anfrage;
	private SimpleObjectProperty<Date> belegDatum;
	private SimpleObjectProperty<Date> ablaufDatum;
	private SimpleStringProperty notiz;
	private SimpleDoubleProperty angebotsWert;
	private SimpleStringProperty status;
	private SimpleStringProperty projekt;
	private SimpleStringProperty ansprechpartner;
	
	public AngebotFX(Angebot modellAngebot) {
		super();
		this.modellAngebot = modellAngebot;
		idAngebot = new SimpleIntegerProperty(modellAngebot.getIdAngebot());
		kundeName = new SimpleStringProperty(modellAngebot.getAnsprechpartner().getKunde().getName());
		anfrage = new SimpleStringProperty(modellAngebot.getAnfrage());
		belegDatum = new SimpleObjectProperty<Date>(modellAngebot.getBelegDatum());
		ablaufDatum = new SimpleObjectProperty<Date>(modellAngebot.getAblaufDatum());
		notiz = new SimpleStringProperty(modellAngebot.getNotiz());
		angebotsWert = new SimpleDoubleProperty(modellAngebot.getAngebotsWert());
		status = new SimpleStringProperty(modellAngebot.getStatus());	
		ansprechpartner = new SimpleStringProperty(modellAngebot.getAnsprechpartner().getVorname()+" "+modellAngebot.getAnsprechpartner().getNachname());
		if(modellAngebot.getProjekt() == null) {
			projekt = new SimpleStringProperty("");
		}
		else {
			projekt = new SimpleStringProperty(modellAngebot.getProjekt().getProjektName());
		}
		
		
		if(modellAngebot.getNotiz() == null) {
			notiz = new SimpleStringProperty("");
		}
		
		if(modellAngebot.getProjekt() == null) {
			projekt = new SimpleStringProperty("");
		}
		else {
			projekt = new SimpleStringProperty(modellAngebot.getProjekt().getProjektName());
		}
	}

	public Angebot getModellAngebot() {
		return modellAngebot;
	}
	
	public int getIdAngebot() {
		return idAngebot.get();
	}
	public SimpleIntegerProperty idAngebotProperty() {
		return idAngebot;
	}
	
	public String getkundeName() {
		return kundeName.get();
	}
	public SimpleStringProperty kundeNameProperty() {
		return kundeName;
	}
	
	public String getAnfrage() {
		return anfrage.get();
	}
	public SimpleStringProperty anfrageProperty() {
		return anfrage;
	}
	
	public Date getBelegDatum() {
		return belegDatum.get();
	}
	public SimpleObjectProperty<Date> belegDatumProperty(){
		return belegDatum;
	}
	
	public Date getAblaufDatum() {
		return ablaufDatum.get();
	}
	public SimpleObjectProperty<Date> ablaufDatumProperty(){
		return ablaufDatum;
	}
	
	public String getNotiz() {
		return notiz.get();
	}
	public SimpleStringProperty notizProperty() {
		return notiz;
	}
	
	public Double getAngebotsWert() {
		return angebotsWert.get();
	}
	public SimpleDoubleProperty angebotsWertProperty() {
		return angebotsWert;
	}
	
	public String getStatus() {
		return status.get();
	}
	public SimpleStringProperty statusProperty() {
		return status;
	}
	
	public String getProjekt() {
		return projekt.get();
	}
	public SimpleStringProperty projektProperty() {
		return projekt;
	}
	
	public String getAnsprechpartner() {
		return ansprechpartner.get();
	}
	public SimpleStringProperty ansprechpartnerProperty() {
		return ansprechpartner;
	}
}
