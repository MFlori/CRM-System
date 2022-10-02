/*
 * Die Methoden dieser Klasse importieren die txt Dateien und liefern Arraylisten zu Angeboten bzw Kunden
 * 
 * */

package application;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SapImport {

	public static ArrayList<Kunde> getKunden(String pfad) {
		
		int n = 0;
		ArrayList<Kunde> kundenImport = new ArrayList<Kunde>();
			
		try {
			FileReader fr = new FileReader(pfad);
			BufferedReader br = new BufferedReader(fr);
			String line;
			//while-Schleife, um die Kopfzeilen zu ignorieren
			while(n < 8) {
				br.readLine();
				n++;
			}
			//Hier startet der erste Datensatz
			while((line = br.readLine()) != null) {
				line = line.substring(1); // entfernt ersten Tabulator in der Zeile
				String[] lineSegment =  line.split("\t");
				
				// erster Split zu ArrayList kundennummer hinzufügen
				// zweiter Split zu ArrayList name hinzufügen
				// dritter Split zu ArrayList plz hinzufügen
				// vierter Split zu ArrayList land hinzufügen
				// fünfter Split zu ArrayList ort hinzufügen
				// sechster Split zu ArrayList strasse hinzufügen
				// siebenter Split zu ArrayList vkgr hinzufügen
				// achter Split zu ArrayList suchbegr hinzufügen
				
				kundenImport.add(new Kunde(Integer.parseInt(lineSegment[0]), lineSegment[1], lineSegment[3], lineSegment[4], 
										   lineSegment[5], lineSegment[6], Integer.parseInt(lineSegment[7]), lineSegment[8], null));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return kundenImport;
	}

	public static ArrayList<Angebot> getAngebote(String pfad) {
		
		int n = 0;
		ArrayList<Angebot> angebotImport = new ArrayList<Angebot>();
				
		try {
			FileReader fr = new FileReader(pfad);
			BufferedReader br = new BufferedReader(fr);
			String line;
			//while-Schleife, um die Kopfzeilen zu ignorieren
			while(n < 8) {
				br.readLine();
				n++;
			}
			//Hier startet der erste Datensatz
			while((line = br.readLine()) != null) {
				line = line.substring(1); // entfernt ersten Tabulator in der Zeile
				String[] lineSegment =  line.split("\t");
				
				//Zeichenformat Komma und Tausenderzeichen ändern
				lineSegment[6] = lineSegment[6].replace(".", ""); // entfernt Tausender-Trennzeichen
				lineSegment[6] = lineSegment[6].replace(",", "."); // tauscht Kommazeichen von Strich auf Punkt

				if(checkAngebotDuplicate(lineSegment[0],angebotImport)) {
					angebotImport.add(new Angebot(Integer.parseInt(lineSegment[0]), 
												Integer.parseInt(lineSegment[1]), 
												lineSegment[3], 
												new SimpleDateFormat("dd.MM.yyyy").parse(lineSegment[4]), 
												new SimpleDateFormat("dd.MM.yyyy").parse(lineSegment[5]),
												Double.valueOf(lineSegment[6]) 
												));
				}
			}
			br.close();
		} catch (IOException | NumberFormatException | ParseException e) {
			e.printStackTrace();
		}
		return angebotImport;
	}
	
	// Methode prüft, ob die Angebotsnummer bereits in der ArrayList vorhanden ist
	public static boolean checkAngebotDuplicate(String s, ArrayList<Angebot> angebotImport) {
		for(Angebot a : angebotImport) {
			if(a.getIdAngebot() == Integer.parseInt(s)) {
				return false;
			}
		}
		return true;
	}
}
