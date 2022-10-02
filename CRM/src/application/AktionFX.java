package application;

import java.util.Date;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;


public class AktionFX {
	
	private Aktion modellAktion;
	private SimpleStringProperty notiz;
	private SimpleObjectProperty<Date> datum;
	private SimpleStringProperty aktionsart;
	private SimpleStringProperty projekt;
	private SimpleIntegerProperty angebot;
	private SimpleStringProperty kunde;
	private SimpleStringProperty ansprechpartner;

	public AktionFX(Aktion modellAktion) {
		
		this.modellAktion = modellAktion;
		this.notiz = new SimpleStringProperty(modellAktion.getNotiz());
		this.datum = new SimpleObjectProperty<Date>(modellAktion.getDatum());
		this.aktionsart = new SimpleStringProperty(modellAktion.getAktionsart());
		if(modellAktion.getProjekt()!= null) {
			this.projekt = new SimpleStringProperty(modellAktion.getProjekt().getProjektName());
			this.ansprechpartner = new SimpleStringProperty(modellAktion.getProjekt().getAnsprechpartner().getVorname()+" "+modellAktion.getProjekt().getAnsprechpartner().getNachname());
			this.kunde = new SimpleStringProperty(modellAktion.getProjekt().getAnsprechpartner().getKunde().getName());
		}
		if(modellAktion.getAngebot()!=null) {
			this.angebot = new SimpleIntegerProperty(modellAktion.getAngebot().getIdAngebot());
			this.ansprechpartner = new SimpleStringProperty(modellAktion.getAngebot().getAnsprechpartner().getVorname()+" "+modellAktion.getAngebot().getAnsprechpartner().getNachname());
			this.kunde = new SimpleStringProperty(modellAktion.getAngebot().getAnsprechpartner().getKunde().getName());
		}

	}
	
	public Aktion getmodellAktion() {
		return modellAktion;
	}
	
	public String getNotiz() {
		return notiz.get();
	}
	public SimpleStringProperty notizProperty() {
		return notiz;
	}
	public Date getDatum() {
		return datum.get();
	}
	public SimpleObjectProperty<Date> datumProperty(){
		return datum;
	}
	public String getAktionsart() {
		return aktionsart.get();
	}
	public SimpleStringProperty aktionsartProperty() {
		return aktionsart;
	}
	public String getProjekt() {
		return projekt.get();
	}
	public SimpleStringProperty projektProperty() {
		return projekt;
	}
	public int getAngebot() {
		return angebot.get();
	}
	public SimpleIntegerProperty angebotProperty() {
		return angebot;
	}
	public String getKunde() {
		return kunde.get();
	}
	public SimpleStringProperty kundeProperty() {
		return kunde;
	}
	public String getAnsprechpartner() {
		return ansprechpartner.get();
	}
	public SimpleStringProperty ansprechpartnerProperty() {
		return ansprechpartner;
	}
}
