package application;


import java.util.ArrayList;
import java.util.Date;



public class Angebot {
	
	private int idAngebot;
	private int idKunde;
	private String anfrage;
	private Date belegDatum;
	private Date ablaufDatum;
	private String notiz;
	private double angebotsWert;
	private String status;
	private Ansprechpartner ansprechpartner;
	private Projekt projekt;
	private ArrayList<Aktion> aktionen;
	
	//Konstruktor für Laden aus DB
	public Angebot(int idAngebot, int idKunde, String anfrage, Date belegDatum, Date ablaufDatum, double angebotsWert, String status, String notiz, Ansprechpartner ansprechpartner, Projekt projekt, ArrayList<Aktion> aktionen) {
		super();
		this.idAngebot = idAngebot;
		this.idKunde = idKunde;
		this.anfrage = anfrage;
		this.belegDatum = belegDatum;
		this.ablaufDatum = ablaufDatum;
		this.angebotsWert = angebotsWert;	
		this.status = status;
		this.notiz = notiz;
		this.ansprechpartner = ansprechpartner;
		this.projekt = projekt;
		this.aktionen = aktionen;
	}
	
	//Konstruktor für Laden aus Textdatei
	public Angebot(int idAngebot, int idKunde, String anfrage, Date belegDatum, Date ablaufDatum, double angebotsWert) {
		super();
		this.idAngebot = idAngebot;
		this.idKunde = idKunde;
		this.anfrage = anfrage;
		this.belegDatum = belegDatum;
		this.ablaufDatum = ablaufDatum;
		this.angebotsWert = angebotsWert;	
	}
	



	public int getIdAngebot() {
		return idAngebot;
	}

	public int getIdKunde() {
		return idKunde;
	}

	public String getAnfrage() {
		return anfrage;
	}


	public Date getBelegDatum() {
		return belegDatum;
	}

	public Date getAblaufDatum() {
		return ablaufDatum;
	}

	public String getNotiz() {
		return notiz;
	}

	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}

	public double getAngebotsWert() {
		return angebotsWert;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public ArrayList<Aktion> getAktionen() {
		return aktionen;
	}

	public void setAktionen(ArrayList<Aktion> aktionen) {
		this.aktionen = aktionen;
	}

	public Ansprechpartner getAnsprechpartner() {
		return ansprechpartner;
	}

	public void setAnsprechpartner(Ansprechpartner ansprechpartner) {
		this.ansprechpartner = ansprechpartner;
	}

	public Projekt getProjekt() {
		return projekt;
	}

	public void setProjekt(Projekt projekt) {
		this.projekt = projekt;
	}
}
