package application;


import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class ControllerMain implements Initializable{

	private DBConn dbconn;
	private ObservableList<KundeFX> olKunden = FXCollections.observableArrayList();
	private ObservableList<AngebotFX> olAngebote = FXCollections.observableArrayList();
	private ObservableList<AnsprechpartnerFX> olAnsprechpartner = FXCollections.observableArrayList();
	private ObservableList<ProjektFX> olProjekte = FXCollections.observableArrayList();
	private ObservableList<AktionFX> olMeetings = FXCollections.observableArrayList();
	private ObservableList<AktionFX> olAnrufe = FXCollections.observableArrayList();
	
	
	@FXML private Button btnMinimize;
	@FXML private BorderPane bp;
	@FXML	private TabPane mainTabPane;
	@FXML	private Tab tabKunden;
		@FXML private TextField txtKundenNummerSearch;
		@FXML private Button btnKundenNummerSearch;
		@FXML private TextField txtKundenNameSearch;
		@FXML private Button btnKundenNameSearch;
		@FXML 	private TableView<KundeFX> tableKunden;
			@FXML TableColumn<KundeFX,Integer> idKundeCol;
			@FXML TableColumn<KundeFX,String> nameCol;
			@FXML TableColumn<KundeFX,String> plzCol;
			@FXML TableColumn<KundeFX,String> landCol;
			@FXML TableColumn<KundeFX,String> ortCol;
			@FXML TableColumn<KundeFX,String> strasseCol;
			@FXML TableColumn<KundeFX,Integer> vkgrCol;
			@FXML TableColumn<KundeFX,String> suchbegriffCol;

	@FXML	private Tab tabKontakte;
		@FXML private TextField txtKontakteSearch;
		@FXML private Label lbAnsprechpartnerSort;
		@FXML private Button btnKontakteSearch;
		@FXML private Button partnerNeu;
		@FXML private Button partnerEdit;
		@FXML private Button partnerDelete;
		@FXML private TableView<AnsprechpartnerFX> tableAnsprechpartner;
			@FXML private TableColumn<AnsprechpartnerFX,String> vornameColPartner;
			@FXML private TableColumn<AnsprechpartnerFX,String> nachnameColPartner;
			@FXML private TableColumn<AnsprechpartnerFX,String> kundeNameColPartner;	
			@FXML private TableColumn<AnsprechpartnerFX,String> telefonColPartner;
			@FXML private TableColumn<AnsprechpartnerFX,String> emailColPartner;
			@FXML private TableColumn<AnsprechpartnerFX,String> funktionColPartner;
			
	@FXML	private Tab tabAngebote;
		@FXML private RadioButton rbtnAngebotAlle;
		@FXML private RadioButton rbtnAngebotBestellt;
		@FXML private RadioButton rbtnAngebotVerloren;
		@FXML private RadioButton rbtnAngebotOffen;
		@FXML private ToggleGroup tgAngebote;
		@FXML private TextField txtKundenSearchAngebote;
		@FXML private Button btnKundenSearchAngebote;
		@FXML private TableView<AngebotFX> tableAngebote;
			@FXML private TableColumn<AngebotFX,Integer> idAngebotColAngebot;
			@FXML private TableColumn<AngebotFX,String> kundeNameColAngebot;	
			@FXML private TableColumn<AngebotFX,String> anfrageColAngebot;	
			@FXML private TableColumn<AngebotFX,Date> belegDatumColAngebot;
			@FXML private TableColumn<AngebotFX,Date> ablaufDatumColAngebot;
			@FXML private TableColumn<AngebotFX,Double> angebotsWertColAngebot;
			@FXML private TableColumn<AngebotFX,String> statusColAngebot;
			@FXML private TableColumn<AngebotFX,String> projektColAngebot;
			@FXML private TableColumn<AngebotFX,String> notizColAngebot;
			@FXML private TableColumn<AngebotFX,String> partnerColAngebot;
		
	@FXML	private Tab tabProjekte;
		@FXML private RadioButton rbtnProjektLaufend;
		@FXML private RadioButton rbtnProjektAbgeschlossen;
		@FXML private ToggleGroup tgProjekte;
		@FXML private TextField txtProjekteSearch;
		@FXML private Button btnProjekteSearch;
		@FXML private Button projektNeu;
		@FXML private Button projektEdit;
		@FXML private Button projektDelete;
		@FXML private TableView<ProjektFX> tableProjekte;
			@FXML private TableColumn<ProjektFX,Integer> idProjektColProjekt;
			@FXML private TableColumn<ProjektFX,String> kundeNameColProjekt;
			@FXML private TableColumn<ProjektFX,String> projektNameColProjekt;
			@FXML private TableColumn<ProjektFX,Date> projektStartColProjekt;
			@FXML private TableColumn<ProjektFX,String> statusColProjekt;
			@FXML private TableColumn<ProjektFX,Integer> angeboteColProjekt;
			@FXML private TableColumn<ProjektFX,Double> wertColProjekt;
			@FXML private TableColumn<ProjektFX,String> notizColProjekt;
			@FXML private TableColumn<ProjektFX,String> partnerNameColProjekt;

	@FXML	private Tab tabMeetings;
		@FXML private Button btnChangeMeeting;
		@FXML private Button btnDeleteMeeting;
		@FXML private TableView<AktionFX> tableMeetings;
			@FXML private TableColumn<AktionFX, Date> datumColMeetings;
			@FXML private TableColumn<AktionFX,String> kundeColMeetings;
			@FXML private TableColumn<AktionFX,String> ansprechpartnerColMeetings;
			@FXML private TableColumn<AktionFX,Integer> angebotColMeetings;
			@FXML private TableColumn<AktionFX,String> projektColMeetings;
			@FXML private TableColumn<AktionFX,String> notizColMeetings;
		
		
	@FXML	private Tab tabAnrufe;
	@FXML private Button btnChangeAnruf;
	@FXML private Button btnDeleteAnruf;
		@FXML private TableView<AktionFX> tableAnrufe;
			@FXML private TableColumn<AktionFX, Date> datumColAnrufe;
			@FXML private TableColumn<AktionFX,String> kundeColAnrufe;
			@FXML private TableColumn<AktionFX,String> ansprechpartnerColAnrufe;
			@FXML private TableColumn<AktionFX,Integer> angebotColAnrufe;
			@FXML private TableColumn<AktionFX,String> projektColAnrufe;
			@FXML private TableColumn<AktionFX,String> notizColAnrufe;
	
	
	@FXML	private Tab tabDatenimport;
		@FXML 	private TextField kundenImportPath;
		@FXML 	private TextField angebotImportPath;
		@FXML 	private Button angebotImportStart;
		@FXML 	private Button kundenImportStart;
		@FXML 	private Button kundenChooser;
		@FXML 	private Button angebotChooser;
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Platform.runLater(() -> { //sonst ist myConn null
			
			createTables();
			kundenImportPath.setEditable(false);
			angebotImportPath.setEditable(false);
			refreshCustomers();
			refreshAngebote();
			refreshProjekte();
			refreshAnsprechpartner();
			refreshMeetings();
			refreshAnrufe();
			
			//Dateipfad für Kundenimport wählen
			kundenChooser.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Textdatei", "*.txt"));
				File selectedFile = fileChooser.showOpenDialog(getStage());
				kundenImportPath.setText(selectedFile.getAbsolutePath());
			});
			//Dateipfad für Angebotimport wählen
			angebotChooser.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Textdatei", "*.txt"));
				File selectedFile = fileChooser.showOpenDialog(getStage());
				angebotImportPath.setText(selectedFile.getAbsolutePath());
			});
			
			//Startet den Kundenimport
			kundenImportStart.setOnAction(e -> {
				dbconn.importKundenInDB(kundenImportPath.getText());
				refreshCustomers();
			});
			
			//Startet den Angebots-Import
			angebotImportStart.setOnAction(e -> {
				dbconn.insertAngebote(angebotImportPath.getText());
				refreshAngebote();
			});
			
			//Refresh Ansprechpartner in Kontakte-Tab
			btnKontakteSearch.setOnAction(e -> {
				refreshAnsprechpartner();
			});
			
			btnProjekteSearch.setOnAction(e -> {
				refreshProjekte();
			});
			
			//neuen Ansprechpartner hinzufügen
			partnerNeu.setOnAction(e -> {
				addNewAnsprechpartner();
			
			});
			//editieren eines bestehenden Ansprechpartners
			partnerEdit.setOnAction(e ->{
				changeAnsprechpartner();
			});
			
			//löschen eines Ansprechpartners
			partnerDelete.setOnAction(e ->{
				deleteAnsprechpartner();
			});
			
			//Angebote nach Kunde filtern
			btnKundenSearchAngebote.setOnAction(e ->{
				refreshAngebote();
			});
			
			//neues Projekt anlegen
			projektNeu.setOnAction(e ->{
				addNewProject();
			});
			
			//Meeting-Aktion ändern
			btnChangeMeeting.setOnAction(e->{
				changeAktion(tableMeetings.getSelectionModel().getSelectedItem().getmodellAktion());
			});
			
			//Meeting-Aktion löschen
			btnDeleteMeeting.setOnAction(e->{
				deleteAktion(tableMeetings.getSelectionModel().getSelectedItem().getmodellAktion());
			});
			
			//Anruf-Aktion ändern
			btnChangeAnruf.setOnAction(e->{
				changeAktion(tableAnrufe.getSelectionModel().getSelectedItem().getmodellAktion());
			});
			
			//Meeting-Aktion löschen
			btnDeleteAnruf.setOnAction(e->{
				deleteAktion(tableAnrufe.getSelectionModel().getSelectedItem().getmodellAktion());
			});
			
			//Kundentabelle filtern nach KundenNummer
			btnKundenNummerSearch.setOnAction(e->{
				txtKundenNameSearch.setText("");
				refreshCustomers();
			});
			
			//Kundentabelle filtern nach KundenName
			btnKundenNameSearch.setOnAction(e->{
				txtKundenNummerSearch.setText("");
				refreshCustomers();
			});
			
			
			
			//neues Projekt ändern
			projektEdit.setOnAction(e ->{
				if(tableProjekte.getSelectionModel().getSelectedItem() == null) {
					Alertmsgs.error("kein Projekt selektiert.");
				}
				else {
					changeProject(tableProjekte.getSelectionModel().getSelectedItem().getmodellProjekt());
				}
			});
			
			//Projekt löschen
			projektDelete.setOnAction(e->{
				if(tableProjekte.getSelectionModel().getSelectedItem() == null) {
					Alertmsgs.error("kein Projekt selektiert.");
				}
				else {
					deleteProject(tableProjekte.getSelectionModel().getSelectedItem().getmodellProjekt());
				}
			});
			
			//Listener prüft welcher Radiobutton ausgewählt wurde und filtert Projektliste nach Status und/oder Kundennummer
			tgProjekte.selectedToggleProperty().addListener(new ChangeListener<>() {
				@Override
				public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
					refreshProjekte();
				}
			});
			
			//Listener prüft welcher Radiobutton ausgewählt wurde und filtert Angebotsliste nach Status
			tgAngebote.selectedToggleProperty().addListener(new ChangeListener<>() {
				@Override
				public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
					refreshAngebote();
				}
			});
			
			//öffne AngebotDetails mit Doppelklick auf Zeile in Angebotsliste um AngebotDetails zu ändern
			tableAngebote.setRowFactory( e -> {
			    TableRow<AngebotFX> row = new TableRow<>();
			    row.setOnMouseClicked(f -> {
			        if (f.getClickCount() == 2 && (! row.isEmpty()) ) {
			            Angebot angebot = row.getItem().getModellAngebot();
			            AngebotDetailDialog add = new AngebotDetailDialog(angebot,dbconn);
			            add.showAndWait();
			            if(add.getResult() != null) {
				            dbconn.updateAngebote(add.getResult());
						    refreshAngebote();
			            }
			        }
			    });
			    return row ;
			});
			
			//öffne AnsprechpartnerDetails mit Doppelklick auf Zeile für Anzeige oder Änderungen
			tableAnsprechpartner.setRowFactory( e -> {
			    TableRow<AnsprechpartnerFX> row = new TableRow<>();
			    row.setOnMouseClicked(f -> {
			        if (f.getClickCount() == 2 && (! row.isEmpty()) ) {
			            Ansprechpartner ansprechpartner = row.getItem().getmodellAnsprechpartner();
			            AnsprechpartnerDetailDialog add = new AnsprechpartnerDetailDialog(ansprechpartner);
			            add.showAndWait();
			            if(add.getResult() != null) {
				            dbconn.updateAnsprechpartner(add.getResult());
						    refreshAnsprechpartner();
			            }
			        }
			    });
			    return row ;
			});
			
			//öffne ProjektDetails mit Doppelklick auf Zeile für Anzeige oder Änderungen
			tableProjekte.setRowFactory( e -> {
			    TableRow<ProjektFX> row = new TableRow<>();
			    row.setOnMouseClicked(f -> {
			        if (f.getClickCount() == 2 && (! row.isEmpty()) ) {
			            Projekt projekt = row.getItem().getmodellProjekt();
			            ProjektDetailDialog pdd = new ProjektDetailDialog(projekt,dbconn);
			            pdd.showAndWait();
			            if(pdd.getResult() != null) {
				            dbconn.updateProjekt(pdd.getResult());
							refreshProjekte();
							refreshAngebote();
			            }
			        }
			    });
			    return row ;
			});
			
			//öffne MeetingDetails mit Doppelklick auf Zeile
			tableMeetings.setRowFactory( e -> {
			    TableRow<AktionFX> row = new TableRow<>();
			    row.setOnMouseClicked(f -> {
			        if (f.getClickCount() == 2 && (! row.isEmpty()) ) {
			        	changeAktion(tableMeetings.getSelectionModel().getSelectedItem().getmodellAktion());
			        }
			    });
			    return row ;
			});
			
			//öffne AnrufDetails mit Doppelklick auf Zeile
			tableAnrufe.setRowFactory( e -> {
			    TableRow<AktionFX> row = new TableRow<>();
			    row.setOnMouseClicked(f -> {
			        if (f.getClickCount() == 2 && (! row.isEmpty()) ) {
			        	changeAktion(tableAnrufe.getSelectionModel().getSelectedItem().getmodellAktion());
			        }
			    });
			    return row ;
			});
			
			//Minimiert die Anwendung
			btnMinimize.setOnAction(e->{
				((Stage)((Button)e.getSource()).getScene().getWindow()).setIconified(true);
			});
	    });
	}

	@FXML	private void goToTabKontakte() {
		mainTabPane.getSelectionModel().select(tabKontakte);
	}
	@FXML	private void goToTabKunden() {
		mainTabPane.getSelectionModel().select(tabKunden);
	}
	@FXML	private void goToTabAngebote() {
		mainTabPane.getSelectionModel().select(tabAngebote);
	}
	@FXML	private void goToTabProjekte() {
		mainTabPane.getSelectionModel().select(tabProjekte);
	}
	@FXML	private void goToTabMeetings() {
		mainTabPane.getSelectionModel().select(tabMeetings);
		refreshMeetings();
	}
	@FXML	private void goToTabAnrufe() {
		mainTabPane.getSelectionModel().select(tabAnrufe);
		refreshAnrufe();
	}
	@FXML	private void goToTabDatenimport() {
		mainTabPane.getSelectionModel().select(tabDatenimport);
	}

	//schließt die Anwendung
	@FXML
	public void close() {
		Platform.exit();
	}
	
	//Befüllt die ObservableList mit Kunden-Objekten
	public void refreshCustomers() {
		olKunden.clear();
		
		if(txtKundenNameSearch.getText().equals("") && txtKundenNummerSearch.getText().equals("")) {
			for(Kunde k : dbconn.getKundenDB()) {
				olKunden.add(new KundeFX(k));
			}
		}	
		else if(txtKundenNameSearch.getText().equals("")) { //Wenn KundenName leer, suche nach Kundennummer
			try{
				for(Kunde k : dbconn.getKundenDB()) {
					if(k.getIdKunde() == Integer.parseInt(txtKundenNummerSearch.getText())) {
						olKunden.add(new KundeFX(k));
					}		
				}
			}
			catch(NumberFormatException ex) {
				Alertmsgs.error("ungültige Kundennummer.");
			}
		}
		else if(txtKundenNummerSearch.getText().equals("")) { //Wenn KundenNummer leer, suche nach Kundenname)
			for(Kunde k : dbconn.getKundenDB()) {
				if(k.getName().contains(txtKundenNameSearch.getText()) || k.getName().contains(txtKundenNameSearch.getText())) {
					olKunden.add(new KundeFX(k));
				}
			}
		}
		tableKunden.refresh();
	}
	//Befüllt die ObservableList mit Ansprechpartner-Objekten
	public void refreshAnsprechpartner() {
		olAnsprechpartner.clear();
		if(txtKontakteSearch.getText().equals("")) {
			lbAnsprechpartnerSort.setText("alle Ansprechpartner:");
			for(Ansprechpartner a :dbconn.getAnsprechpartnerDB()) {
				if(!a.getVorname().equals("nicht")) {
					olAnsprechpartner.add(new AnsprechpartnerFX(a));
				}
			}
		}
		else if(dbconn.showAnsprechpartner(txtKontakteSearch.getText()) == null){
			lbAnsprechpartnerSort.setText("Kein Kunde zu dieser Kundennummer vorhanden!");
			Alertmsgs.error("keine gültige Kundennummer eingegeben.");
		}
		else {
			lbAnsprechpartnerSort.setText("Ansprechpartner von "+dbconn.getCustomerFromKdnr(txtKontakteSearch.getText()).getName()+":");
			for(Ansprechpartner a : dbconn.showAnsprechpartner(txtKontakteSearch.getText())){
				if(!a.getVorname().equals("nicht")) {
					olAnsprechpartner.add(new AnsprechpartnerFX(a));
				}
			}	
		}
		tableAnsprechpartner.refresh();
	}
	//Befüllt die Meetings-ObservableList mit Aktions-Objekten, die die AktionsArt "Meeting" haben
	public void refreshMeetings() {
		olMeetings.clear();
		for(Aktion a : dbconn.getAktionenDB()) {
			if(a.getAktionsart().equals("Meeting")) {
				olMeetings.add(new AktionFX(a));
			}
		}
		tableMeetings.refresh();
	}
	//Befüllt die Anrufe-ObservableList mit Aktions-Objekten, die die AktionsArt "Telefonat" haben
	public void refreshAnrufe() {
		olAnrufe.clear();
		for(Aktion a : dbconn.getAktionenDB()) {
			if(a.getAktionsart().equals("Telefonat")) {
				olAnrufe.add(new AktionFX(a));
			}
		}
		tableAnrufe.refresh();
	}
	//Befüllt die ObservableList mit Projekt-Objekten
	public void refreshProjekte() {
		olProjekte.clear();
		if(tgProjekte.getSelectedToggle().equals(rbtnProjektLaufend)) {
			if(txtProjekteSearch.getText().equals("")) {
				for(Projekt p : dbconn.getProjekteDB()) {
					if(!p.isDeleted() && p.getStatus().equals("laufend"))
					olProjekte.add(new ProjektFX(p));
				}	
			}
			else {
				for(Projekt p : dbconn.getProjekteDB()) {
					if(!p.isDeleted() && p.getAnsprechpartner().getKunde().getIdKunde() == Integer.parseInt(txtProjekteSearch.getText()) &&  p.getStatus().equals("laufend")) {
						olProjekte.add(new ProjektFX(p));
					}
				}
			}
		}
		else if(tgProjekte.getSelectedToggle().equals(rbtnProjektAbgeschlossen)){
			if(txtProjekteSearch.getText().equals("")) {
				for(Projekt p : dbconn.getProjekteDB()) {
					if(!p.isDeleted() && p.getStatus().equals("abgeschlossen"))
					olProjekte.add(new ProjektFX(p));
				}	
			}
			else {
				for(Projekt p : dbconn.getProjekteDB()) {
					if(!p.isDeleted() && p.getAnsprechpartner().getKunde().getIdKunde() == Integer.parseInt(txtProjekteSearch.getText()) &&  p.getStatus().equals("abgeschlossen")) {
						olProjekte.add(new ProjektFX(p));
					}
				}
			}
		}
		tableProjekte.refresh();
	}
	//aktualisiert die Angebotsliste, dabei wird geprüft welcher Radiobutton selektiert ist
	public void refreshAngebote() {
		olAngebote.clear();
		
		Kunde k = dbconn.getCustomerFromKdnr(txtKundenSearchAngebote.getText());
		
		if(k == null) {
			if(tgAngebote.getSelectedToggle().equals(rbtnAngebotAlle)) {
				for(Angebot a : dbconn.getAngeboteDB()) {
					olAngebote.add(new AngebotFX(a));
				}
			}
			else if(tgAngebote.getSelectedToggle().equals(rbtnAngebotBestellt)) {
				for(Angebot a : dbconn.getAngeboteDB()) {
					if(a.getStatus().equals("bestellt")) {
						olAngebote.add(new AngebotFX(a));
					}
				}
			}
			else if(tgAngebote.getSelectedToggle().equals(rbtnAngebotVerloren)) {
				for(Angebot a : dbconn.getAngeboteDB()) {
					if(a.getStatus().equals("verloren")) {
						olAngebote.add(new AngebotFX(a));
					}							
				}
			}
			else if(tgAngebote.getSelectedToggle().equals(rbtnAngebotOffen)) {
				for(Angebot a : dbconn.getAngeboteDB()) {
					if(a.getStatus().equals("offen")) {
						olAngebote.add(new AngebotFX(a));
					}							
				}
			}	
		}
		else {
			if(tgAngebote.getSelectedToggle().equals(rbtnAngebotAlle)) {
				lbAnsprechpartnerSort.setText("alle Angebote:  "+k.getName());
				for(Angebot a : dbconn.getAngeboteDB()) {
					if(a.getAnsprechpartner().getKunde().getIdKunde()==k.getIdKunde())
						olAngebote.add(new AngebotFX(a));
				}
			}
			else if(tgAngebote.getSelectedToggle().equals(rbtnAngebotBestellt)) {
				lbAnsprechpartnerSort.setText("Angebote bestellt:  "+k.getName());
				for(Angebot a : dbconn.getAngeboteDB()) {
					if(a.getStatus().equals("bestellt") && a.getAnsprechpartner().getKunde().getIdKunde()==k.getIdKunde()) {
						olAngebote.add(new AngebotFX(a));
					}
				}
			}
			else if(tgAngebote.getSelectedToggle().equals(rbtnAngebotVerloren)) {
				lbAnsprechpartnerSort.setText("Angebote verloren:  "+k.getName());
				for(Angebot a : dbconn.getAngeboteDB()) {
					if(a.getStatus().equals("verloren") && a.getAnsprechpartner().getKunde().getIdKunde()==k.getIdKunde()) {
						olAngebote.add(new AngebotFX(a));
					}							
				}
			}
			else if(tgAngebote.getSelectedToggle().equals(rbtnAngebotOffen)) {
				lbAnsprechpartnerSort.setText("Angebote offen:  "+k.getName());
				for(Angebot a : dbconn.getAngeboteDB()) {
					if(a.getStatus().equals("offen")&& a.getAnsprechpartner().getKunde().getIdKunde()==k.getIdKunde()) {
						olAngebote.add(new AngebotFX(a));
					}							
				}
			}			
		}
		tableAngebote.refresh();
	}
	
	//weist den Tabellen Spalten zu und befüllt sie mit Daten aus der jeweiligen ObservableList
	public void createTables() {
				
		idKundeCol.setCellValueFactory(new PropertyValueFactory<KundeFX, Integer>("idKunde"));
		nameCol.setCellValueFactory(new PropertyValueFactory<KundeFX, String>("name"));
		plzCol.setCellValueFactory(new PropertyValueFactory<KundeFX, String>("plz"));
		landCol.setCellValueFactory(new PropertyValueFactory<KundeFX, String>("land"));
		ortCol.setCellValueFactory(new PropertyValueFactory<KundeFX, String>("ort"));
		strasseCol.setCellValueFactory(new PropertyValueFactory<KundeFX, String>("strasse"));
		vkgrCol.setCellValueFactory(new PropertyValueFactory<KundeFX, Integer>("vkgr"));
		suchbegriffCol.setCellValueFactory(new PropertyValueFactory<KundeFX, String>("suchbegriff"));
		tableKunden.setItems(olKunden);		

		vornameColPartner.setCellValueFactory(new PropertyValueFactory<AnsprechpartnerFX, String>("vorname"));
		nachnameColPartner.setCellValueFactory(new PropertyValueFactory<AnsprechpartnerFX, String>("nachname"));
		kundeNameColPartner.setCellValueFactory(new PropertyValueFactory<AnsprechpartnerFX, String>("kundename"));
		telefonColPartner.setCellValueFactory(new PropertyValueFactory<AnsprechpartnerFX, String>("telefon"));
		emailColPartner.setCellValueFactory(new PropertyValueFactory<AnsprechpartnerFX, String>("email"));
		funktionColPartner.setCellValueFactory(new PropertyValueFactory<AnsprechpartnerFX, String>("funktion"));
		tableAnsprechpartner.setItems(olAnsprechpartner);	

		idProjektColProjekt.setCellValueFactory(new PropertyValueFactory<ProjektFX, Integer>("idProjekt"));
		kundeNameColProjekt.setCellValueFactory(new PropertyValueFactory<ProjektFX, String>("kundeName"));
		projektNameColProjekt.setCellValueFactory(new PropertyValueFactory<ProjektFX, String>("projektName"));
		projektStartColProjekt.setCellValueFactory(new PropertyValueFactory<ProjektFX, Date>("projektStart"));
		statusColProjekt.setCellValueFactory(new PropertyValueFactory<ProjektFX, String>("status"));
		angeboteColProjekt.setCellValueFactory(new PropertyValueFactory<ProjektFX, Integer>("angebote"));
		wertColProjekt.setCellValueFactory(new PropertyValueFactory<ProjektFX, Double>("angebotsWert"));
		notizColProjekt.setCellValueFactory(new PropertyValueFactory<ProjektFX, String>("notiz"));
		partnerNameColProjekt.setCellValueFactory(new PropertyValueFactory<ProjektFX, String>("partnerName"));
				
		projektStartColProjekt.setCellFactory(tc -> new TableCell<ProjektFX, Date>() {
		    @Override
		    protected void updateItem(Date date, boolean leer) {
		    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		        super.updateItem(date, leer);
		        if (leer) {
		            setText(null);
		        } else {
		            setText(dateFormat.format(date));
		        }
		    }
		});
		
		wertColProjekt.setCellFactory(tc -> new TableCell<ProjektFX, Double>() {
		    @Override
		    protected void updateItem(Double preis, boolean leer) {
		    	NumberFormat waehrung = NumberFormat.getCurrencyInstance();
		        super.updateItem(preis, leer);
		        if (leer) {
		            setText(null);
		        } else {
		            setText(waehrung.format(preis));
		        }
		    }
		});
		
		tableProjekte.setItems(olProjekte);

		idAngebotColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, Integer>("idAngebot"));
		kundeNameColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, String>("kundeName"));
		anfrageColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, String>("anfrage"));
		belegDatumColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, Date>("belegDatum"));
		ablaufDatumColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, Date>("ablaufDatum"));
		angebotsWertColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, Double>("angebotsWert"));
		statusColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, String>("status"));
		projektColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, String>("projekt"));
		notizColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, String>("notiz"));
		partnerColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, String>("ansprechpartner"));
	
		//Datumsformat von Ablaufdatum in tableview auf Tag/Monat/Jahr anpassen
		ablaufDatumColAngebot.setCellFactory(tc -> new TableCell<AngebotFX, Date>() {
		    @Override
		    protected void updateItem(Date date, boolean leer) {
		    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		        super.updateItem(date, leer);
		        if (leer) {
		            setText(null);
		        } else {
		            setText(dateFormat.format(date));
		        }
		    }
		});
		//Datumsformat von Belegdatum in tableview auf Tag/Monat/Jahr anpassen
		belegDatumColAngebot.setCellFactory(tc -> new TableCell<AngebotFX, Date>() {
		    @Override
		    protected void updateItem(Date date, boolean leer) {
		    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		        super.updateItem(date, leer);
		        if (leer) {
		            setText(null);
		        } else {
		            setText(dateFormat.format(date));
		        }
		    }
		});
		
		//Darstellung von Angebotswert als EURO in Tableview
		angebotsWertColAngebot.setCellFactory(tc -> new TableCell<AngebotFX, Double>() {
		    @Override
		    protected void updateItem(Double preis, boolean leer) {
		    	NumberFormat waehrung = NumberFormat.getCurrencyInstance();
		        super.updateItem(preis, leer);
		        if (leer) {
		            setText(null);
		        } else {
		            setText(waehrung.format(preis));
		        }
		    }
		});
		tableAngebote.setItems(olAngebote);
		
		datumColMeetings.setCellValueFactory(new PropertyValueFactory<AktionFX, Date>("datum"));
		kundeColMeetings.setCellValueFactory(new PropertyValueFactory<AktionFX, String>("kunde"));
		ansprechpartnerColMeetings.setCellValueFactory(new PropertyValueFactory<AktionFX, String>("ansprechpartner"));
		angebotColMeetings.setCellValueFactory(new PropertyValueFactory<AktionFX, Integer>("angebot"));
		projektColMeetings.setCellValueFactory(new PropertyValueFactory<AktionFX, String>("projekt"));
		notizColMeetings.setCellValueFactory(new PropertyValueFactory<AktionFX, String>("notiz"));
		tableMeetings.setItems(olMeetings);	
		
		datumColMeetings.setCellFactory(tc -> new TableCell<AktionFX, Date>() {
		    @Override
		    protected void updateItem(Date date, boolean leer) {
		    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		        super.updateItem(date, leer);
		        if (leer) {
		            setText(null);
		        } else {
		            setText(dateFormat.format(date));
		        }
		    }
		});
		
		datumColAnrufe.setCellValueFactory(new PropertyValueFactory<AktionFX, Date>("datum"));
		kundeColAnrufe.setCellValueFactory(new PropertyValueFactory<AktionFX, String>("kunde"));
		ansprechpartnerColAnrufe.setCellValueFactory(new PropertyValueFactory<AktionFX, String>("ansprechpartner"));
		angebotColAnrufe.setCellValueFactory(new PropertyValueFactory<AktionFX, Integer>("angebot"));
		projektColAnrufe.setCellValueFactory(new PropertyValueFactory<AktionFX, String>("projekt"));
		notizColAnrufe.setCellValueFactory(new PropertyValueFactory<AktionFX, String>("notiz"));
		tableAnrufe.setItems(olAnrufe);	
		
		datumColAnrufe.setCellFactory(tc -> new TableCell<AktionFX, Date>() {
		    @Override
		    protected void updateItem(Date date, boolean leer) {
		    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		        super.updateItem(date, leer);
		        if (leer) {
		            setText(null);
		        } else {
		            setText(dateFormat.format(date));
		        }
		    }
		});
	}

	//Methode liefert die Stage
	public Stage getStage() {
		return (Stage) bp.getScene().getWindow();
	}
	
	//Methode liefert die Datenbankverbindung
	public DBConn getDbconn() {
		return dbconn;
	}
	
	//Methode setzt die Datenbankverbindung
	public void setDbconn(DBConn dbconn) {
		this.dbconn = dbconn;
	}

	//fügt neuen Ansprechpartner hinzu
	private void addNewAnsprechpartner() {
		Kunde kunde = null;
		try {
			Integer.valueOf(txtKontakteSearch.getText());
		} catch (NumberFormatException ex) {
			Alertmsgs.error("keine gültige Kundennummer eingegeben.");
			return;
		}
		
		for(Kunde k : dbconn.getKundenDB()) {
			if(k.getIdKunde() == Integer.valueOf(txtKontakteSearch.getText())) {
				kunde = k; //Wenn die Kundennummer gefunden wird, Kundenobjekt auswählen
			}
		}
		if(kunde == null) {
			Alertmsgs.error("kein Kunde mit Kundennummer \""+txtKontakteSearch.getText()+"\" gefunden");		
			}
		else {
			AnsprechpartnerDetailDialog add = new AnsprechpartnerDetailDialog(kunde);
			add.showAndWait();
			if(!(add.getResult() == null)) {
				dbconn.insertAnsprechpartner(add.getResult());
				refreshAnsprechpartner();
				refreshAngebote();
				refreshProjekte();
			}			
		}						
	}
	
	//Methode ändert einen bestehenden Ansprechpartner
	private void changeAnsprechpartner() {
		if(tableAnsprechpartner.getSelectionModel().getSelectedItem() == null) {
			Alertmsgs.error("kein Ansprechpartner selektiert");
		}
		else {
			AnsprechpartnerDetailDialog add = new AnsprechpartnerDetailDialog(tableAnsprechpartner.getSelectionModel().getSelectedItem().getmodellAnsprechpartner());
			add.showAndWait();	
			if(!(add.getResult() == null)) {
				dbconn.updateAnsprechpartner(add.getResult());
				refreshAnsprechpartner();
				refreshAngebote();
				refreshProjekte();
			}		
		}
	}
	
	//Methode ändert löscht einen bestehenden Ansprechpartner
	private void deleteAnsprechpartner() {
		if(tableAnsprechpartner.getSelectionModel().getSelectedItem() == null) {
			Alertmsgs.error("kein Ansprechpartner selektiert");
		}
		else {
			String name = tableAnsprechpartner.getSelectionModel().getSelectedItem().getmodellAnsprechpartner().getVorname()+" "+tableAnsprechpartner.getSelectionModel().getSelectedItem().getmodellAnsprechpartner().getNachname();
			dbconn.deleteAnsprechpartner(tableAnsprechpartner.getSelectionModel().getSelectedItem().getmodellAnsprechpartner());
			Alertmsgs.error("Ansprechpartner "+name+" wurde gelöscht.");
		}
		refreshAnsprechpartner();
		refreshAngebote();
	}
	
	//Methode legt ein neues Projekt an
	public void addNewProject() {
		if((dbconn.getCustomerFromKdnr(txtProjekteSearch.getText())) == null){
			Alertmsgs.error("Kundennummer unbekannt");
		}
		else {
			Kunde k = dbconn.getCustomerFromKdnr(txtProjekteSearch.getText());
			ProjektDetailDialog pdd = new ProjektDetailDialog(k);
			pdd.showAndWait();
			if(!(pdd.getResult()==null)) {
				dbconn.insertProjekt(pdd.getResult());
				refreshProjekte();
			}
		}
	}
	//Methode ändert bestehendes Projekt
	public void changeProject(Projekt projekt) {
		ProjektDetailDialog pdd = new ProjektDetailDialog(projekt,dbconn);
		pdd.showAndWait();
		if(!(pdd.getResult()==null)) {
			dbconn.updateProjekt(pdd.getResult());
			refreshProjekte();
			refreshAngebote();
		}
	}
	//löscht ein Projekt
	public void deleteProject(Projekt projekt) {
		dbconn.deleteProjekt(projekt);
		refreshProjekte();
		refreshAngebote();
	}
	
	//ändert eine Aktion
	public void changeAktion(Aktion a){
		
		AktionDetailDialog add = new AktionDetailDialog(a);
		add.showAndWait();
		if(add.getResult() != null) {
			dbconn.updateAktion(add.getResult());
			refreshAnrufe();
			refreshMeetings();
		}
	}
	//löscht eine Aktion
	public void deleteAktion(Aktion a){
		dbconn.deleteAktion(a);
		refreshAnrufe();
		refreshMeetings();
	}
}
