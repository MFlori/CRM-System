package application;

import java.sql.Date;

public class Aktion {
	
	private int idAktion;
	private String notiz;
	private Date datum;
	private String aktionsart;
	private Angebot angebot;
	private Projekt projekt;
	
	//Konstruktor für Laden aus DB
	public Aktion(int idAktion, String notiz, Date datum, String aktionsart, Angebot angebot, Projekt projekt) {
		super();
		this.idAktion = idAktion;
		this.notiz = notiz;
		this.datum = datum;
		this.aktionsart = aktionsart;
		this.angebot = angebot;
		this.projekt = projekt;
	}
	
	//Konstruktor Aktion anlegen
	public Aktion(String notiz, Date datum, String aktionsart, Angebot angebot, Projekt projekt) {
		super();
		this.notiz = notiz;
		this.datum = datum;
		this.aktionsart = aktionsart;
		this.angebot = angebot;
		this.projekt = projekt;
	}
	
	public int getIdAktion() {
		return idAktion;
	}

	public String getNotiz() {
		return notiz;
	}
	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}
	public Date getDatum() {
		return datum;
	}
	public void setDatum(Date datum) {
		this.datum = datum;
	}
	public String getAktionsart() {
		return aktionsart;
	}
	public void setAktionsart(String aktionsart) {
		this.aktionsart = aktionsart;
	}


	public Angebot getAngebot() {
		return angebot;
	}

	public void setAngebot(Angebot angebot) {
		this.angebot = angebot;
	}

	public Projekt getProjekt() {
		return projekt;
	}

	public void setProjekt(Projekt projekt) {
		this.projekt = projekt;
	}

	
	
}
