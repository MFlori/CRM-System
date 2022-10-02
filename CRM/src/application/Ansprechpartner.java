package application;

import java.util.ArrayList;

public class Ansprechpartner {
	
	private int idAnsprechpartner;
	private String vorname;
	private String nachname;
	private String telefon;
	private String email;
	private String funktion;
	private ArrayList<Projekt> projekte;
	private ArrayList<Angebot> angebote;
	private Kunde kunde;
	
	//Konstruktor für "Ansprechpartner aus DB laden"
	public Ansprechpartner(int idAnsprechpartner, String vorname, String nachname, String telefon, String email,
			String funktion, ArrayList<Projekt> projekte, ArrayList<Angebot>angebote, Kunde kunde) {
		super();
		this.idAnsprechpartner = idAnsprechpartner;
		this.vorname = vorname;
		this.nachname = nachname;
		this.telefon = telefon;
		this.email = email;
		this.funktion = funktion;
		this.projekte = projekte;
		this.angebote = angebote;
		this.kunde = kunde;
	}
	
	//Konstruktor für "Ansprechpartner anlegen"
	public Ansprechpartner(String vorname, String nachname, String telefon, String email,
			String funktion, Kunde kunde) {
		super();
		this.vorname = vorname;
		this.nachname = nachname;
		this.telefon = telefon;
		this.email = email;
		this.funktion = funktion;
		this.kunde = kunde;
	}
	
	public int getIdAnsprechpartner() {
		return idAnsprechpartner;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFunktion() {
		return funktion;
	}

	public void setFunktion(String funktion) {
		this.funktion = funktion;
	}

	public ArrayList<Projekt> getProjekte() {
		return projekte;
	}

	public void setProjekte(ArrayList<Projekt> projekte) {
		this.projekte = projekte;
	}

	public ArrayList<Angebot> getAngebote() {
		return angebote;
	}

	public void setAngebote(ArrayList<Angebot> angebote) {
		this.angebote = angebote;
	}

	public Kunde getKunde() {
		return kunde;
	}
}
