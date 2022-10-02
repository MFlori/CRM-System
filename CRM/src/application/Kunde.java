package application;

import java.util.ArrayList;

public class Kunde {
	
	private int idKunde;
	private String name;
	private String plz;
	private String land;
	private String ort;
	private String strasse;
	private int vkgr; 
	private String suchbegriff;
	private ArrayList<Ansprechpartner> ansprechpartner;
	
	public Kunde(int idKunde, String name, String plz, String land, String ort,
			String strasse, int vkgr, String suchbegriff, ArrayList<Ansprechpartner> ansprechpartner) {
		super();
		this.idKunde = idKunde;
		this.name = name;
		this.plz = plz;
		this.land = land;
		this.ort = ort;
		this.strasse = strasse;
		this.vkgr = vkgr;
		this.suchbegriff = suchbegriff;
		this.ansprechpartner = ansprechpartner;
	}
	
	public int getIdKunde() {
		return idKunde;
	}

	public String getName() {
		return name;
	}

	public String getPlz() {
		return plz;
	}

	public String getLand() {
		return land;
	}

	public String getOrt() {
		return ort;
	}

	public String getStrasse() {
		return strasse;
	}

	public int getVkgr() {
		return vkgr;
	}

	public String getSuchbegriff() {
		return suchbegriff;
	}
	
	public ArrayList<Ansprechpartner> getAnsprechpartner() {
		return ansprechpartner;
	}

	public void setAnsprechpartner(ArrayList<Ansprechpartner> ansprechpartner) {
		this.ansprechpartner = ansprechpartner;
	}
	
}
