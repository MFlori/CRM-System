package application;

import java.util.Date;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProjektFX {

	private Projekt modellProjekt;
	private SimpleIntegerProperty idProjekt;
	private SimpleStringProperty kundeName;
	private SimpleStringProperty projektName;
	private SimpleObjectProperty<Date> projektStart; //auf simpleStringproperty ändern und formatieren
	private SimpleStringProperty status;
	private SimpleIntegerProperty angebote;
	private SimpleDoubleProperty angebotsWert;
	private SimpleStringProperty notiz;
	private SimpleStringProperty partnerName;

	
	public ProjektFX(Projekt modellProjekt) {
		super();
		this.modellProjekt = modellProjekt;
		this.idProjekt = new SimpleIntegerProperty(modellProjekt.getIdProjekt());
		this.kundeName = new SimpleStringProperty(modellProjekt.getAnsprechpartner().getKunde().getName());
		this.projektName = new SimpleStringProperty(modellProjekt.getProjektName());
		this.projektStart = new SimpleObjectProperty<Date>(modellProjekt.getProjektStart());
		this.status = new SimpleStringProperty(modellProjekt.getStatus());
		int angebotcount = 0;
		double angebotswertCount = 0.0;
		for(Angebot a : modellProjekt.getAngebote()) {
			angebotcount++;
			angebotswertCount = angebotswertCount + a.getAngebotsWert();
		}
		this.angebote = new SimpleIntegerProperty(angebotcount);
		this.angebotsWert = new SimpleDoubleProperty(angebotswertCount);
		this.partnerName = new SimpleStringProperty(modellProjekt.getAnsprechpartner().getVorname()+" "+modellProjekt.getAnsprechpartner().getNachname());
		this.notiz = new SimpleStringProperty(modellProjekt.getNotiz());
		
	}
	public Projekt getmodellProjekt() {
		return modellProjekt;
	}
	
	public int getidProjekt() {
		return idProjekt.get();
	}
	public SimpleIntegerProperty idProjektProperty() {
		return idProjekt;
	}
	
	public String getkundeName() {
		return kundeName.get();
	}
	public SimpleStringProperty kundeNameProperty() {
		return kundeName;
	}
	
	public String getprojektName() {
		return projektName.get();
	}
	public SimpleStringProperty projektNameProperty() {
		return projektName;
	}
	
	public Date getprojektStart() {
		return projektStart.get();
	}
	public SimpleObjectProperty<Date> projektStartProperty(){
		return projektStart;
	}
	
	public String getStatus() {
		return status.get();
	}
	public SimpleStringProperty statusProperty() {
		return status;
	}
	
	public int getAngebote() {
		return angebote.get();
	}
	public SimpleIntegerProperty angeboteProperty() {
		return angebote;
	}
	
	public double getAngebotsWert() {
		return angebotsWert.get();
	}
	public SimpleDoubleProperty angebotsWertProperty() {
		return angebotsWert;
	}
	
	public String getNotiz() {
		return status.get();
	}
	public SimpleStringProperty notizProperty() {
		return notiz;
	}
	
	public String getPartnerName() {
		return partnerName.get();
	}
	public SimpleStringProperty partnerNameProperty() {
		return partnerName;
	}

}
