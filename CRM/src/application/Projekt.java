package application;

import java.sql.Date;
import java.util.ArrayList;


public class Projekt {

	private int idProjekt;
	private String projektName;
	private String notiz;
	private Date projektStart;
	private String status;
	private boolean deleted;
	private ArrayList<Angebot> angebote;
	private ArrayList<Aktion> aktionen;
	private Ansprechpartner ansprechpartner;
	
	//Konstruktor für "Projekt aus der DB laden"
	public Projekt(int idProjekt, String projektName, String notiz, Date projektStart, String status, boolean deleted, ArrayList<Angebot> angebote, ArrayList<Aktion>aktionen, Ansprechpartner ansprechpartner) {
	super();
	this.idProjekt = idProjekt;
	this.projektName = projektName;
	this.notiz = notiz;
	this.projektStart = projektStart;
	this.status = status;
	this.deleted = deleted;
	this.angebote = angebote;
	this.aktionen = aktionen;
	this.ansprechpartner = ansprechpartner;
	}
	
	//Konstruktor für "InsertProjekt"
	public Projekt(String projektName, String notiz, Date projektStart, String status, Ansprechpartner ansprechpartner) {
	super();
	this.projektName = projektName;
	this.notiz = notiz;
	this.projektStart = projektStart;
	this.status = status;
	this.deleted = false;
	this.ansprechpartner = ansprechpartner;
	}
		
	public int getIdProjekt() {
		return idProjekt;
	}
	public String getProjektName() {
		return projektName;
	}
	public void setProjektName(String projektName) {
		this.projektName = projektName;
	}
	public String getNotiz() {
		return notiz;
	}
	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}
	public Date getProjektStart() {
		return projektStart;
	}
	public void setProjektStart(Date projektStart) {
		this.projektStart = projektStart;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public ArrayList<Angebot> getAngebote() {
		return angebote;
	}
	public void setAngebote(ArrayList<Angebot> angebote) {
		this.angebote = angebote;
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
}
