package application;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AngebotDetailDialog extends Dialog <Angebot>{
	
	private ObservableList<String> olPartner = FXCollections.observableArrayList();
	private ObservableList<AktionFX> olAktionen = FXCollections.observableArrayList();
	private TableView <AktionFX> tableAktionen = new TableView<AktionFX>();
	
	//Konstruktor für neuen AngebotDetailDialog
	public AngebotDetailDialog(Angebot angebot, DBConn dbconn) {

		refreshAktionen(dbconn,angebot);
		
		for(Ansprechpartner a : angebot.getAnsprechpartner().getKunde().getAnsprechpartner()) {
				olPartner.add(a.getVorname()+" "+a.getNachname());
		}
		
		VBox vbTop = new VBox(10);
		HBox hb = new HBox(10);
		VBox vbTable = new VBox(10);
		
		
		Label lbhead = new Label("Angebot " + angebot.getIdAngebot() + " - " + angebot.getAnsprechpartner().getKunde().getName());
		GridPane gp = new GridPane();
		GridPane gpButtons1 = new GridPane();
		GridPane gpButtons2 = new GridPane();
		
		Label lbIdAngebot = new Label("Angebotsnummer: ");
		Label lbAnfrage = new Label("Anfrage: ");
		Label lbBelegDatum = new Label("Belegdatum: ");
		Label lbAblaufDatum = new Label("Gültig bis: ");
		Label lbAngebotsWert = new Label("Angebotswert: ");
		Label lbStatus = new Label("Status: ");
		Label lbAnsprechpartner = new Label("Ansprechpartner");
		Label lbNotiz = new Label("Notiz: ");
		Label lbAktionen = new Label("Meetings und Anrufe: ");
		
		
		TableColumn<AktionFX, Date> datumColAktionen  = new TableColumn<AktionFX,Date>("Datum");
		TableColumn<AktionFX,String> artColAktionen  = new TableColumn<AktionFX,String>("Aktion");
		TableColumn<AktionFX,String> notizColAktionen  = new TableColumn<AktionFX,String>("Notiz");
		notizColAktionen.setPrefWidth(200);
		
		datumColAktionen.setCellFactory(tc -> new TableCell<AktionFX, Date>() {
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
		
		
		TextField txtIdAngebot = new TextField();
		TextField txtAnfrage = new TextField();
		TextField txtBelegDatum = new TextField();
		TextField txtAblaufDatum = new TextField();
		TextField txtAngebotsWert = new TextField();
		ComboBox<String> cobStatus = new ComboBox<>();
		ComboBox<String> cobPartner = new ComboBox<>();
		TextArea txtNotiz = new TextArea();
		Button btnMeeting = new Button("Meeting");
		Button btnAnruf = new Button("Telefonat");
		
		txtIdAngebot.setText(Integer.toString(angebot.getIdAngebot()));
		txtAnfrage.setText(angebot.getAnfrage());
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");   
		txtBelegDatum.setText(dateFormat.format(angebot.getBelegDatum()));
		txtAblaufDatum.setText(dateFormat.format(angebot.getAblaufDatum()));
		Locale locale = Locale.GERMANY;
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
		txtAngebotsWert.setText(numberFormat.format(angebot.getAngebotsWert()));
		cobStatus.setItems(FXCollections.observableArrayList("offen", "bestellt", "verloren"));
		cobStatus.getSelectionModel().select(angebot.getStatus());
		cobPartner.setItems(olPartner);
		cobPartner.setValue(angebot.getAnsprechpartner().getVorname()+" "+angebot.getAnsprechpartner().getNachname());
		txtNotiz.setText(angebot.getNotiz());
		txtNotiz.setWrapText(true);
		
		cobStatus.setPrefWidth(150);
		cobPartner.setPrefWidth(150);
		btnMeeting.setPrefWidth(150);
		btnAnruf.setPrefWidth(150);
		HBox.setMargin(gp, new Insets(13,0,0,0));
		
		gpButtons1.setHgap(200);
		gpButtons2.setHgap(200);
		txtIdAngebot.setEditable(false);
		txtAnfrage.setEditable(false);
		txtBelegDatum.setEditable(false);
		txtAblaufDatum.setEditable(false);
		txtAngebotsWert.setEditable(false);
				
		gp.add(lbIdAngebot,0,0);
		gp.add(lbAnfrage,0,1);
		gp.add(lbBelegDatum,0,2);
		gp.add(lbAblaufDatum,0,3);
		gp.add(lbAngebotsWert,0,4);
		gp.add(lbStatus,0,5);
		gp.add(lbAnsprechpartner, 0, 6);
		gp.add(lbNotiz,0,7);	
		gp.add(txtIdAngebot,1,0);
		gp.add(txtAnfrage,1,1);
		gp.add(txtBelegDatum,1,2);
		gp.add(txtAblaufDatum,1,3);
		gp.add(txtAngebotsWert,1,4);
		gp.add(gpButtons1,1,5);
		gpButtons1.add(cobStatus, 0, 0);
		gpButtons1.add(btnMeeting, 1, 0);
		gp.add(gpButtons2,1,6);
		gpButtons2.add(cobPartner, 0, 0);
		gpButtons2.add(btnAnruf, 1, 0);
		gp.add(txtNotiz,1,7);
		
		gp.setPadding(new Insets(15));
		gp.setHgap(5);
		gp.setVgap(5);
		
		datumColAktionen.setCellValueFactory(new PropertyValueFactory<AktionFX, Date>("datum"));
		artColAktionen.setCellValueFactory(new PropertyValueFactory<AktionFX, String>("aktionsart"));
		notizColAktionen.setCellValueFactory(new PropertyValueFactory<AktionFX, String>("notiz"));
		tableAktionen.getColumns().addAll(Arrays.asList(datumColAktionen, artColAktionen, notizColAktionen));
		tableAktionen.setItems(olAktionen);	
		tableAktionen.setPlaceholder(new Label("keine Telefonate oder Meetings angelegt."));
		
		

		vbTable.getChildren().addAll(lbAktionen,tableAktionen);
		hb.getChildren().addAll(gp,vbTable);
		vbTop.getChildren().addAll(lbhead,hb);
		
		//öffne AngebotDetails mit Doppelklick auf Zeile in Angebotsliste um AngebotDetails zu ändern
		tableAktionen.setRowFactory( e -> {
		    TableRow<AktionFX> row = new TableRow<>();
		    row.setOnMouseClicked(f -> {
		        if (f.getClickCount() == 2 && (! row.isEmpty()) ) {
		            Aktion aktion = row.getItem().getmodellAktion();
		            AktionDetailDialog add = new AktionDetailDialog(aktion);
		            add.showAndWait();
		            if(add.getResult() != null) {
			            dbconn.updateAktion(add.getResult());
						refreshAktionen(dbconn,angebot);
						tableAktionen.refresh();
		            }
		        }
		    });
		    return row ;
		});
		
		this.setTitle("Angebot");
		this.getDialogPane().setContent(vbTop);
		this.getDialogPane().getButtonTypes().add(new ButtonType("speichern",ButtonData.OK_DONE));
		this.getDialogPane().getButtonTypes().add(new ButtonType("Abbrechen",ButtonData.CANCEL_CLOSE));
		
		this.setResultConverter( button -> {
		    if (button.getButtonData()==ButtonData.OK_DONE) {
				angebot.setNotiz(txtNotiz.getText());
				angebot.setStatus(cobStatus.getSelectionModel().getSelectedItem());
				for(Ansprechpartner a : angebot.getAnsprechpartner().getKunde().getAnsprechpartner()) {
					if((a.getVorname()+" "+a.getNachname()).equals(cobPartner.getSelectionModel().getSelectedItem())){
						angebot.setAnsprechpartner(a);
					};
				}
				return angebot;
		    }
		    return null;
		});
			
		btnMeeting.setOnAction(e ->{
			newAktion(angebot,"Meeting", dbconn);
		});
		btnAnruf.setOnAction(e ->{
			newAktion(angebot,"Telefonat", dbconn);
		});
	}

	//Methode befüllt OlAktionen mit Objekten vom Typ Aktion, die zu dem ausgewählten Angebot gehören
	private void refreshAktionen(DBConn dbconn, Angebot angebot) {
		olAktionen.clear();
		for(Aktion a : dbconn.getAktionenDB()) {
			if(a.getAngebot() != null) {
				if(a.getAngebot().getIdAngebot() == angebot.getIdAngebot()) {
					olAktionen.add(new AktionFX(a));
				}
			}
		}
		
	}
	
	//Methode erzeugt neues Aktion-Objekt
	private void newAktion(Angebot angebot, String AktionsArt, DBConn dbconn) {
		AktionDetailDialog add = new AktionDetailDialog(angebot, AktionsArt);
		add.showAndWait();	
		if(add.getResult() != null) {
			dbconn.insertAktion(add.getResult());
			refreshAktionen(dbconn,angebot);
			tableAktionen.refresh();
		}
		
	}
	
}
