package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

public class DBConn {

	private Connection myConn;
	private ArrayList<Kunde> kundenDB;
	
	//Konstruktor DBConn, lädt auch alle Kundenobjekte in das Programm
	public DBConn(Connection myConn) {
		this.myConn = myConn;
		ladeKundenDB();
	}

	public Connection getMyConn() {
		return myConn;
	}

	public void setMyConn(Connection myConn) {
		this.myConn = myConn;
	}
	
	/// ****************************************** START: ArrayListen über alle Objekte einer Klasse ******************************************
	
	//erzeugt eine ArrayList von ALLEN Kunden
	public ArrayList<Kunde> getKundenDB() {
		return kundenDB;
	}
	
	//erzeugt eine ArrayList von ALLEN Ansprechpartnern
	public ArrayList<Ansprechpartner> getAnsprechpartnerDB(){
		ArrayList<Ansprechpartner> ansprechpartnerArr = new ArrayList<Ansprechpartner>();
		for(Kunde k : getKundenDB()) {
			ansprechpartnerArr.addAll(k.getAnsprechpartner());
		}
		return ansprechpartnerArr;
	}
	
	//erzeugt eine ArrayList von ALLEN Projekten
	public ArrayList<Projekt> getProjekteDB(){
		ArrayList<Projekt> projekteArr = new ArrayList<Projekt>();
		for(Ansprechpartner a : getAnsprechpartnerDB()) {
			if(!(a.getProjekte() == null)) {
				projekteArr.addAll(a.getProjekte());
			}
			
		}
		return projekteArr;
	}
	
	//erzeugt eine ArrayList von ALLEN Angeboten
	public ArrayList<Angebot> getAngeboteDB(){
		ArrayList<Angebot> angeboteArr = new ArrayList<Angebot>();
		for(Ansprechpartner a : getAnsprechpartnerDB()) {
			if(!(a.getAngebote() == null))
			angeboteArr.addAll(a.getAngebote());
		}
		return angeboteArr;
	}
	
	//erzeugt eine ArrayList von ALLEN Aktionen
	public ArrayList<Aktion> getAktionenDB(){
		ArrayList<Aktion> aktionenArr = new ArrayList<Aktion>();
		for(Angebot a : getAngeboteDB()) {
			if(a.getAktionen() != null) {
				aktionenArr.addAll(a.getAktionen());
			}
		}
		for(Projekt p : getProjekteDB()) {
			if(p.getAktionen() != null) {
				aktionenArr.addAll(p.getAktionen());
			}
		}
		return aktionenArr;
	}
	/// ****************************************** Ende: ArrayListen über alle Objekte einer Klasse ******************************************

	/// ****************************************** START: Aufbau der Kundenobjekte durch Auslesen der DB ******************************************
	
	//Methode lädt alle Kundenobjekte in das Programm
	public void ladeKundenDB() {
		ArrayList<Kunde> kunden = new ArrayList<Kunde>();
		try {
			// 1. statement erstellen
			Statement myStmt = myConn.createStatement();
			
			// 2. sql query ausführen
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM kunde");
			
			// 3. ArrayList Kunden wird befüllt
			while(myRs.next()) {
				kunden.add(new Kunde(myRs.getInt("idkunde"),
									myRs.getString("name"),
									myRs.getString("plz"),
									myRs.getString("land"),
									myRs.getString("ort"),
									myRs.getString("strasse"),
									myRs.getInt("vkgr"),
									myRs.getString("suchbegriff"),
									null //wird mit ladeAnsprechpartnerDB() befüllt
									));
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		kundenDB = kunden;
		ladeAnsprechpartnerDB();
	}
	
	//Methode lädt Ansprechpartner-Objekte in die ArrayList Ansprechpartner der Kunden-Objekte
	public void ladeAnsprechpartnerDB(){
		for (Kunde k : getKundenDB()){
			ArrayList<Ansprechpartner> ansprechpartnerArr = new ArrayList<Ansprechpartner>();
			try {
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery("SELECT * FROM crmsys.ansprechpartner WHERE idKunde = "+k.getIdKunde());
				while(myRs.next()) {
					ansprechpartnerArr.add(new Ansprechpartner(myRs.getInt("idAnsprechpartner"),
																	myRs.getString("vorname"),
																	myRs.getString("nachname"),
																	myRs.getString("telefon"),
																	myRs.getString("email"),
																	myRs.getString("funktion"),
																	null, //wird mit ladeProjekteDB() befüllt
																	null, //wird mit ladeAngeboteFuerAnsprechpartnerDB() befüllt
																	k
							));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			k.setAnsprechpartner(ansprechpartnerArr);
		}
		ladeProjekteDB();
		ladeAngeboteFuerAnsprechpartnerDB();
	}
	
	//Methode lädt Projekte-Objekte in die ArrayList Projekte der Ansprechpartner-Objekte
	public void ladeProjekteDB() {	
		for(Ansprechpartner a : getAnsprechpartnerDB()) {
			ArrayList<Projekt> projektArr = new ArrayList<Projekt>();	
			try {
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery("SELECT * FROM crmsys.projekt WHERE idAnsprechpartner = "+a.getIdAnsprechpartner());
				while(myRs.next()) {		
					projektArr.add(new Projekt(myRs.getInt("idProjekt"),
													myRs.getString("projektName"),
													myRs.getString("notiz"),
													myRs.getDate("projektStart"),
													myRs.getString("status"),
													myRs.getBoolean("deleted"),
													null, // ladeAngeboteFuerProjekte
													null, // ladeAktionenFuerProjekte
													a					
							));
				}
			}
			catch(Exception exc) {
				exc.printStackTrace();
			}
			a.setProjekte(projektArr);
		}
		ladeAngeboteFuerProjekteDB();
		ladeAktionenFuerProjekteDB();
	}
	
	//Methode lädt Angebot-Objekte in die ArrayList Angebote der Ansprechpartner-Objekte
	public void ladeAngeboteFuerAnsprechpartnerDB() {
		
		for(Ansprechpartner a : getAnsprechpartnerDB()) {
			ArrayList<Angebot> angeboteArr = new ArrayList<Angebot>();
			try {
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery("SELECT * FROM angebot WHERE idAnsprechpartner="+a.getIdAnsprechpartner());
				while(myRs.next()) {		
					angeboteArr.add(new Angebot(myRs.getInt("idAngebot"),
							myRs.getInt("idKunde"),
							myRs.getString("anfrageNummer"),
							myRs.getDate("belegdatum"),
							myRs.getDate("gueltigkeit"),
							myRs.getDouble("nettoWert"),
							myRs.getString("status"),
							myRs.getString("notiz"),
							a,
							getProjektForAngebot(myRs.getInt("idAngebot")),
							null //wird mit ladeAktionenFuerAngebote() befüllt
					));
				}
			}
			catch(Exception exc) {
				exc.printStackTrace();
			}
			a.setAngebote(angeboteArr);
		}
		ladeAktionenFuerAngebote();
	}
	
	private Projekt getProjektForAngebot(int idAngebot) {
		for(Projekt p : getProjekteDB()) {
			for(Angebot a : p.getAngebote()) {
				if(a.getIdAngebot()==idAngebot) {
					return p;
				}
			}
		}
		return null;
	}
	
	//Methode lädt Angebot-Objekte in die ArrayList Angebote der Projekte-Objekte
	public void ladeAngeboteFuerProjekteDB() {
		for(Projekt p : getProjekteDB()) {
			ArrayList<Angebot> angeboteArr = new ArrayList<Angebot>();
			try {
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery("SELECT * FROM angebot WHERE idProjekt="+p.getIdProjekt());
				while(myRs.next()) {		
					angeboteArr.add(new Angebot(myRs.getInt("idAngebot"),
							myRs.getInt("idKunde"),
							myRs.getString("anfrageNummer"),
							myRs.getDate("belegdatum"),
							myRs.getDate("gueltigkeit"),
							myRs.getDouble("nettoWert"),
							myRs.getString("status"),
							myRs.getString("notiz"),
							p.getAnsprechpartner(),
							p,
							null //wird mit ladeAktionenFuerAngebote() befüllt
					));
				}
			}
			catch(Exception exc) {
				exc.printStackTrace();
			}
			p.setAngebote(angeboteArr);
		}
		ladeAktionenFuerAngebote();
	}

	//Methode lädt Aktion-Objekte in die ArrayList Aktionen der Angebot-Objekte
	public void ladeAktionenFuerAngebote(){
		for(Angebot a : getAngeboteDB()) {
			ArrayList<Aktion> aktionenArr = new ArrayList<Aktion>();
			try {
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery("SELECT * FROM crmsys.aktion WHERE idAngebot="+a.getIdAngebot());
				while(myRs.next()) {
					aktionenArr.add(new Aktion(myRs.getInt("idAktion"),
								myRs.getString("notiz"),
								myRs.getDate("datum"),
								myRs.getString("aktion"),
								a,
								null //Projekt bleibt leer
							));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			a.setAktionen(aktionenArr);
		}

		
	}
	
	//Methode lädt Aktion-Objekte in die ArrayList Aktionen der Projekt-Objekte
	public void ladeAktionenFuerProjekteDB(){
		for(Projekt p : getProjekteDB()) {
			ArrayList<Aktion> aktionenArr = new ArrayList<Aktion>();
			try {
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery("SELECT * FROM crmsys.aktion WHERE idProjekt="+p.getIdProjekt());
				while(myRs.next()) {
					aktionenArr.add(new Aktion(myRs.getInt("idAktion"),
								myRs.getString("notiz"),
								myRs.getDate("datum"),
								myRs.getString("aktion"),
								null, //Angebot bleibt leer
								p
							));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			p.setAktionen(aktionenArr);
		}	
	}
	
	/// ****************************************** ENDE: Aufbau der Kundenobjekte durch Auslesen der DB******************************************

	
	/// ****************************************** START: INSERT-, UPDATE-, DELETE-Methoden  ******************************************
	
	//Methode spielt neue Ansprechpartner in die DB ein 
	public void insertAnsprechpartner(Ansprechpartner ap) {
		try {
			PreparedStatement pstmt = null;
			String insertAnsprechpartnerQuery = "INSERT INTO ansprechpartner(vorname, nachname, telefon, email, funktion, idKunde) VALUES(?,?,?,?,?,?)";
			pstmt = myConn.prepareStatement(insertAnsprechpartnerQuery);
			pstmt.setString(1, ap.getVorname());
			pstmt.setString(2, ap.getNachname());
			pstmt.setString(3, ap.getTelefon());
			pstmt.setString(4, ap.getEmail());
			pstmt.setString(5, ap.getFunktion());
			pstmt.setInt(6, ap.getKunde().getIdKunde());
			pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ladeKundenDB();
	}
	
	//Methode speichert Änderungen bei Ansprechpartnern in DB
	public void updateAnsprechpartner(Ansprechpartner ansp) {
		
		try {
			PreparedStatement pstmt = null;
			String updateAnsprechpartnerQuery = "UPDATE `crmsys`.`ansprechpartner` SET `vorname` = '" + ansp.getVorname() +"', `nachname` = '" + ansp.getNachname() + "', `email` = '" + ansp.getEmail() + "', `funktion` = '" + ansp.getFunktion() + "', `telefon` = '" + ansp.getTelefon() + "' WHERE (`idAnsprechpartner` = '" + ansp.getIdAnsprechpartner() + "')";
			pstmt = myConn.prepareStatement(updateAnsprechpartnerQuery);
			pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	//löscht den Ansprechpartner aus der DB und setzt Projekte und Angebote auf den Dummy-Ansprechpartner
	public void deleteAnsprechpartner(Ansprechpartner ansp) {
		try {
			PreparedStatement pstmt = null;
			if(!(ansp.getAngebote() == null)) {
				String deleteAnsprechpartnerQuery = "UPDATE crmsys.angebot SET idAnsprechpartner="+ansp.getKunde().getAnsprechpartner().get(0).getIdAnsprechpartner()+" WHERE idAnsprechpartner="+ansp.getIdAnsprechpartner()+";";
				pstmt = myConn.prepareStatement(deleteAnsprechpartnerQuery);
				pstmt.executeUpdate();
			}
			if(!(ansp.getProjekte() == null)) {
				String deleteAnsprechpartnerQuery = "UPDATE crmsys.projekt SET idAnsprechpartner="+ansp.getKunde().getAnsprechpartner().get(0).getIdAnsprechpartner()+" WHERE idAnsprechpartner="+ansp.getIdAnsprechpartner()+";";
				pstmt = myConn.prepareStatement(deleteAnsprechpartnerQuery);
				pstmt.executeUpdate();
			}
			String deleteAnsprechpartnerQuery = "DELETE FROM crmsys.ansprechpartner WHERE idAnsprechpartner="+ansp.getIdAnsprechpartner()+";";
			
			pstmt = myConn.prepareStatement(deleteAnsprechpartnerQuery);
			pstmt.executeUpdate();

		} catch (SQLException exc) {
			exc.printStackTrace();
		}
		ansp.getKunde().getAnsprechpartner().remove(ansp);
		
		ladeKundenDB();
	}
	
	//fügt ein neues Projekt in die DB
	public void insertProjekt(Projekt p) {
		try {
			PreparedStatement pstmt = null;
			String insertProjektQuery = "INSERT INTO `crmsys`.`projekt` (`projektName`, `notiz`, `projektStart`, `status`, `idAnsprechpartner`) VALUES ('"+p.getProjektName()+"', '"+p.getNotiz()+"', '"+new java.sql.Date(p.getProjektStart().getTime())+"', '"+p.getStatus()+"', '"+p.getAnsprechpartner().getIdAnsprechpartner()+"')";
			pstmt = myConn.prepareStatement(insertProjektQuery);
			pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ladeKundenDB();
	}
	
	//Aktualisiert die Projektdaten in der DB
	public void updateProjekt(Projekt p) {
		try {
			PreparedStatement pstmt = null;
			String updateProjektQuery = "UPDATE `crmsys`.`projekt` SET `projektName` = '"+p.getProjektName()+"', `notiz` = '"+p.getNotiz()+"', `projektStart` = '"+p.getProjektStart()+"', `status` = '"+p.getStatus()+"', `idAnsprechpartner` = '"+p.getAnsprechpartner().getIdAnsprechpartner()+"' WHERE (`idProjekt` = '"+p.getIdProjekt()+"');";
			pstmt = myConn.prepareStatement(updateProjektQuery);
			pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		for(Angebot a : p.getAngebote()) {
			try {
				PreparedStatement pstmt = null;
				String updateAngebotQuery = "UPDATE `crmsys`.`angebot` SET `idProjekt` = '"+p.getIdProjekt()+"' WHERE (`idAngebot` = '"+a.getIdAngebot()+"');";
				pstmt = myConn.prepareStatement(updateAngebotQuery);
				pstmt.executeUpdate();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		ladeKundenDB();
	}

	//setzt Projekt auf "deleted" und entfernt den Projektbezug bei Angeboten
	public void deleteProjekt(Projekt p) {
		try {
			PreparedStatement pstmt = null;
			String deleteProjektQuery = "UPDATE `crmsys`.`projekt` SET `deleted` = '1' WHERE (`idProjekt` = '"+p.getIdProjekt()+"');";
			pstmt = myConn.prepareStatement(deleteProjektQuery);
			pstmt.executeUpdate();	
			String removeProjektAngebotQuery = "UPDATE `crmsys`.`angebot` SET `idProjekt` = null WHERE (`idProjekt` = '"+p.getIdProjekt()+"');";
			pstmt = myConn.prepareStatement(removeProjektAngebotQuery);
			pstmt.executeUpdate();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ladeKundenDB();
	}
	
	
	//speichert die Angebotsänderungen in die DB
	public void updateAngebote(Angebot angebot) {
		try {
			PreparedStatement pstmt = null;
			String updateAngebotQuery = "UPDATE `crmsys`.`angebot` SET `status` = '" + angebot.getStatus() + "', `notiz` = ?, `idProjekt` =  ?, `idAnsprechpartner` = '" + angebot.getAnsprechpartner().getIdAnsprechpartner() +"' WHERE (`idAngebot` = '" + angebot.getIdAngebot() + "');";
			pstmt = myConn.prepareStatement(updateAngebotQuery);
			if(angebot.getNotiz() == null) {
				pstmt.setString(1,"");
			}
			else {
				pstmt.setString(1,angebot.getNotiz());
			}
			if(angebot.getProjekt() == null) {
				pstmt.setNull(2, Types.INTEGER);
			}
			else {
				pstmt.setInt(2, angebot.getProjekt().getIdProjekt());
			}

			pstmt.executeUpdate();
									    
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	

	//fügt eine neue Aktion in die DB
	public void insertAktion(Aktion a) {
		try {
			PreparedStatement pstmt = null;
			String insertAktionQuery;
			if( a.getAngebot() != null) {
				insertAktionQuery = "INSERT INTO `crmsys`.`aktion` (`notiz`, `datum`, `aktion`, `idAngebot`) VALUES ('"+a.getNotiz()+"', '"+a.getDatum()+"', '"+a.getAktionsart()+"', '"+a.getAngebot().getIdAngebot()+"');";
			}
			else {
				insertAktionQuery = "INSERT INTO `crmsys`.`aktion` (`notiz`, `datum`, `aktion`, `idProjekt`) VALUES ('"+a.getNotiz()+"', '"+a.getDatum()+"', '"+a.getAktionsart()+"', '"+a.getProjekt().getIdProjekt()+"');";
			}
			pstmt = myConn.prepareStatement(insertAktionQuery);
			pstmt.executeUpdate();	

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ladeKundenDB();
	}

	//aktualisiert Aktionsdaten in der DB
	public void updateAktion(Aktion a) {
		try {
			PreparedStatement pstmt = null;
			String updateAktionQuery = "UPDATE `crmsys`.`aktion` SET `notiz` = '"+a.getNotiz()+"', `datum` = '"+a.getDatum()+"' WHERE (`idAktion` = '"+a.getIdAktion()+"');";
			pstmt = myConn.prepareStatement(updateAktionQuery);
			pstmt.executeUpdate();	

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ladeKundenDB();
		
	}
	
	//löscht eine Aktion aus der DB
	public void deleteAktion(Aktion a) {
		try {
			PreparedStatement pstmt = null;
			String deleteAktionQuery = "DELETE FROM `crmsys`.`aktion` WHERE (`idAktion` = '"+a.getIdAktion()+"');";
			pstmt = myConn.prepareStatement(deleteAktionQuery);
			pstmt.executeUpdate();	

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ladeKundenDB();
	}
	/// ****************************************** ENDE: INSERT-, UPDATE-, DELETE-Methoden  ******************************************
	
	/// ****************************************** START: IMPORT Kunden und Angebote aus SAP(Textdatei) ******************************************
	
	//importiert Kunden in die DB durch Auslesen der Textdatei
	public void importKundenInDB(String dateipfad) {
		int entryNew = 0;
		int entryChanged = 0;
		if(dateipfad == "") {
			Alertmsgs.error("kein Dateipfad angegeben");
			return;
		}
		ArrayList <Kunde> kundenImport = SapImport.getKunden(dateipfad);
		for(Kunde k : kundenImport) { //1.for-Schleife für importierte Kunden
			boolean inDB = false;
			for(Kunde m : kundenDB) { //2.for-Schleife, jeder Import-Kunde durchläuft alle DB-Kunden
				if(k.getIdKunde() == m.getIdKunde()) { //prüft ob die KundenID`s ident sind
					inDB = true;
					if(checkUpdateCustomer(k, m)) { //Vergleicht Import- und DB-Kunden hinsichtlich Änderungen
						entryChanged++;
						try {
							PreparedStatement pstmt = null;
							String updateQuery = "UPDATE `crmsys`.`kunde` SET `name` = '"+k.getName()+"', `plz` = '"+k.getPlz()+"', `land` = '"+k.getLand()+"', `ort` = '"+k.getOrt()+"', `strasse` = '"+k.getStrasse()+"', `vkgr` = '"+k.getVkgr()+"', `suchbegriff` = '"+k.getSuchbegriff()+"' WHERE (`idkunde` = '"+m.getIdKunde()+"')";
							pstmt = myConn.prepareStatement(updateQuery);
							pstmt.executeUpdate();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			if(inDB == false) { // Kunde in die DB laden, wenn noch nicht vorhanden
				entryNew++;
				try {
					PreparedStatement pstmt = null;
					String insertKundeQuery = "INSERT INTO kunde VALUES(?,?,?,?,?,?,?,?)";
					pstmt = myConn.prepareStatement(insertKundeQuery);
					pstmt.setInt(1, k.getIdKunde());
					pstmt.setString(2, k.getName());
					pstmt.setString(3, k.getPlz());
					pstmt.setString(4, k.getLand());
					pstmt.setString(5, k.getOrt());
					pstmt.setString(6, k.getStrasse());
					pstmt.setInt(7, k.getVkgr());
					pstmt.setString(8, k.getSuchbegriff());
					//INSERT-Query ausführen
					pstmt.executeUpdate();
					
					//Query für Dummy-Kunden
					String insertDummyQuery = "INSERT INTO ansprechpartner(vorname, nachname,idKunde) VALUES('nicht', 'zugewiesen','"+k.getIdKunde()+"')"; 
					pstmt = myConn.prepareStatement(insertDummyQuery);
					pstmt.executeUpdate();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		if(entryNew == 0 && entryChanged == 0) {
			Alertmsgs.info("es wurden keine Daten importiert.\nDie Datenbank ist am aktuellsten Stand");
		}
		else {
			Alertmsgs.info("Kundendatensätze importiert: "+entryNew+"\nKundendatensätze aktualisiert: " + entryChanged);
		}
		ladeKundenDB();
	}
	
	//prüft bei Methode 'importKundenInDB', ob bestehender Kunde geändert wurde
	public boolean checkUpdateCustomer(Kunde k1, Kunde k2) {
		if(k1.getName().equals(k2.getName()) && k1.getPlz().equals(k2.getPlz()) && 
				k1.getLand().equals(k2.getLand()) && k1.getOrt().equals(k2.getOrt()) && k1.getStrasse().equals(k2.getStrasse()) && 
				k1.getVkgr() == k2.getVkgr() && k1.getSuchbegriff().equals(k2.getSuchbegriff())){
			return false;
		}
		return true;
	}

	//importiert Angebote in die DB durch Auslesen der Textdatei
	public void insertAngebote(String dateipfad) {
		if(dateipfad == "") {
			Alertmsgs.error("kein Dateipfad angegeben");
			return;
		}
		ArrayList <Angebot> angebotImport = SapImport.getAngebote(dateipfad);
		int entryNew = 0;
		int errors = 0;
		
		for(Angebot a : angebotImport) {
			boolean inDB = false;
			for(Angebot b : getAngeboteDB()) {
				if(a.getIdAngebot() == b.getIdAngebot()) {
					inDB = true;
					break;
				}
			}
			if(inDB == false) {
				entryNew++;
				try {
					PreparedStatement pstmt = null;
					String insertAngebotQuery = "INSERT INTO angebot(idAngebot,idKunde,anfrageNummer,belegdatum,gueltigkeit,nettoWert,idAnsprechpartner,status)  VALUES(?,?,?,?,?,?,?,?)";
					pstmt = myConn.prepareStatement(insertAngebotQuery);
					pstmt.setInt(1, a.getIdAngebot());
					pstmt.setInt(2, a.getIdKunde());
					pstmt.setString(3, a.getAnfrage());
					pstmt.setDate(4, new java.sql.Date(a.getBelegDatum().getTime()));
					pstmt.setDate(5, new java.sql.Date(a.getAblaufDatum().getTime()));
					pstmt.setDouble(6, a.getAngebotsWert());
					for(Kunde k : kundenDB) {
						if(k.getIdKunde() == a.getIdKunde()) {
							pstmt.setInt(7, k.getAnsprechpartner().get(0).getIdAnsprechpartner());
							break;
						}
					}
					pstmt.setString(8, "offen");
					
					//INSERT-Query ausführen
					pstmt.executeUpdate();
											    
				} catch (SQLException e1) {
					e1.printStackTrace();
					errors++;
					
				}
			}
		}

		entryNew = entryNew - errors;
		if(entryNew == 0 && errors == 0) {
			Alertmsgs.info("es wurden keine Daten importiert.\nDie Datenbank ist am aktuellsten Stand");
		}
		else {
			Alertmsgs.info("Angebotsdatensätze importiert: "+entryNew+"\nDatensätze fehlerhaft: "+errors);
		}
			ladeKundenDB();
	}

	/// ****************************************** ENDE: IMPORT Kunden und Angebote aus SAP(Textdatei) ******************************************

	

	//Methode liefert ArrayList von Ansprechpartnern zu einem Kunden 
	public ArrayList<Ansprechpartner> showAnsprechpartner(String kdnr) {
		for(Kunde k: kundenDB) {
			if(Integer.parseInt(kdnr) == k.getIdKunde()) {
				return k.getAnsprechpartner();
			}	
		}
		return null;
	}
	
	//Methode prüft mittels Kundennummer, ob ein Kunde bereits in der DB vorhanden ist
	public Kunde getCustomerFromKdnr(String kdnr) {
		if(kdnr.equals("")) {
			return null;
		}
		for(Kunde k : kundenDB) {
			if(Integer.parseInt(kdnr) == k.getIdKunde()) {
				return k;
			}
		}
		return null;
	}

}
