package application;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProjektDetailDialog extends Dialog <Projekt>{

	
	private ObservableList<String> olPartner = FXCollections.observableArrayList();
	private ObservableList<AngebotFX> olAngebote = FXCollections.observableArrayList();
	private ObservableList<AktionFX> olMeetings = FXCollections.observableArrayList();
	private ObservableList<AktionFX> olAnrufe = FXCollections.observableArrayList();
	
	//Konstruktor bei Start von "neues Projekt anlegen"
	ProjektDetailDialog(Kunde kunde){

		for(Ansprechpartner a : kunde.getAnsprechpartner()) {
			olPartner.add(a.getVorname()+" "+a.getNachname());
		}
		
		VBox vbTop = new VBox(10);
		Label lbHead = new Label("Projekt von " + kunde.getName());
		GridPane gpProjektInfo = new GridPane();
		Label lbProjektName = new Label("Projektname:");
		Label lbProjektStart = new Label("Projektstart:");
		Label lbStatus = new Label("Status:");
		Label lbAnsprechpartner = new Label("Ansprechpartner:");
		Label lbNotiz = new Label("Notiz:");
		TextField txtProjektName = new TextField();
		DatePicker dpProjektStart = new DatePicker();
		ComboBox<String> cobStatus = new ComboBox<>();
		ComboBox<String> cobPartner = new ComboBox<>();
		TextArea txtNotiz = new TextArea();
		
		cobStatus.setItems(FXCollections.observableArrayList("laufend", "abgeschlossen"));
		cobStatus.getSelectionModel().select("laufend");
		cobPartner.setItems(olPartner);
		cobPartner.setValue(olPartner.get(0));
				
		txtNotiz.setPrefWidth(300);
		txtNotiz.setPrefHeight(400);
		txtNotiz.setWrapText(true);
		txtProjektName.setPrefWidth(300);
		dpProjektStart.setPrefWidth(300);
		cobStatus.setPrefWidth(300);
		cobPartner.setPrefWidth(300);

		gpProjektInfo.setPadding(new Insets(18,5,5,5));
		gpProjektInfo.setHgap(2);
		gpProjektInfo.setVgap(5);
		
		gpProjektInfo.add(lbProjektName,0,0);
		gpProjektInfo.add(lbProjektStart,0,1);
		gpProjektInfo.add(lbStatus,0,2);
		gpProjektInfo.add(lbAnsprechpartner,0,3);
		gpProjektInfo.add(lbNotiz,0,4);
		gpProjektInfo.add(txtProjektName, 1, 0);
		gpProjektInfo.add(dpProjektStart, 1, 1);
		gpProjektInfo.add(cobStatus, 1, 2);
		gpProjektInfo.add(cobPartner, 1, 3);
		gpProjektInfo.add(txtNotiz, 1, 4);

		vbTop.getChildren().addAll(lbHead, gpProjektInfo);
		
		this.setTitle("Projekt anlegen");
		this.getDialogPane().setContent(vbTop);
		this.getDialogPane().getButtonTypes().add(new ButtonType("speichern",ButtonData.OK_DONE));
		this.getDialogPane().getButtonTypes().add(new ButtonType("Abbrechen",ButtonData.CANCEL_CLOSE));

		this.setResultConverter( button -> {
			    if (button.getButtonData()==ButtonData.OK_DONE) {
					if(dpProjektStart.getValue() == null || txtProjektName.getText().equals("")) {
						Alertmsgs.error("kein Projektname oder Datum vergeben");
						return null;
					}
					else {
						String pjName = txtProjektName.getText();
						Date getDatePickerDate = Date.valueOf(dpProjektStart.getValue()); //Wird von Localdate in Date umgewandelt
						String pjStatus = cobStatus.getSelectionModel().getSelectedItem();
						Ansprechpartner pjAnsprechpartner = null;
						String pjNotiz = txtNotiz.getText();
						for(Ansprechpartner a : kunde.getAnsprechpartner()) {
							if((a.getVorname()+" "+a.getNachname()).equals(cobPartner.getSelectionModel().getSelectedItem())){
								pjAnsprechpartner = a;
							}
						}
						return new Projekt(pjName, pjNotiz, getDatePickerDate, pjStatus, pjAnsprechpartner);					
					}
			    }
			    else{
			    	return null;
			    }
		});
	}
			
	
	//Konstruktor bei Start von "Projekt ändern"
	ProjektDetailDialog(Projekt projekt, DBConn dbconn){

		
		for(Ansprechpartner a : projekt.getAnsprechpartner().getKunde().getAnsprechpartner()) {
				olPartner.add(a.getVorname()+" "+a.getNachname());
		}
		for(Angebot a : projekt.getAngebote()) {
			olAngebote.add(new AngebotFX(a));
		}
		refreshAktionen(dbconn, projekt);
		
		
		VBox vbTop = new VBox(10);
		
		Label lbHead = new Label("Projekt von " + projekt.getAnsprechpartner().getKunde().getName());
		Label lbTableAngebote = new Label("Angebote");
		Label lbTableMeetings = new Label("Meetings");
		Label lbTableAnrufe = new Label("Telefonate");
		
		HBox hbMain = new HBox(10);
				
		VBox vbTables = new VBox();
		
		HBox hbAngebote = new HBox();
		TableView<AngebotFX> tableAngebote = new TableView<>(olAngebote);
			TableColumn<AngebotFX,Integer> idAngebotColAngebot = new TableColumn<AngebotFX,Integer>("Angebot");
			TableColumn<AngebotFX,String> anfrageColAngebot = new TableColumn<AngebotFX,String>("Anfrage");	
			TableColumn<AngebotFX,Date> belegDatumColAngebot = new TableColumn<AngebotFX,Date>("Belegdatum");
			TableColumn<AngebotFX,Date> ablaufDatumColAngebot = new TableColumn<AngebotFX,Date>("Gültigkeit");
			TableColumn<AngebotFX,Double> angebotsWertColAngebot = new TableColumn<AngebotFX,Double>("Angebotswert");
			TableColumn<AngebotFX,String> statusColAngebot = new TableColumn<AngebotFX,String>("Status");
			idAngebotColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, Integer>("idAngebot"));
			anfrageColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, String>("anfrage"));
			belegDatumColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, Date>("belegDatum"));
			ablaufDatumColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, Date>("ablaufDatum"));
			angebotsWertColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, Double>("angebotsWert"));
			statusColAngebot.setCellValueFactory(new PropertyValueFactory<AngebotFX, String>("status"));
			tableAngebote.getColumns().addAll(Arrays.asList(idAngebotColAngebot, anfrageColAngebot, belegDatumColAngebot, ablaufDatumColAngebot, angebotsWertColAngebot,statusColAngebot));
			tableAngebote.setItems(olAngebote);
			
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

			
			VBox vbAngebote = new VBox();
				Button btnAngebotShow = new Button("anzeigen");
				Button btnAngebotAdd = new Button("hinzufügen");
				Button btnAngebotRemove = new Button("entfernen");
				
		HBox hbMeetings = new HBox();
			TableView<AktionFX> tableMeetings = new TableView<AktionFX>();
				TableColumn<AktionFX, Date> datumColMeetings  = new TableColumn<AktionFX,Date>("Datum");
				TableColumn<AktionFX,String> artColMeetings  = new TableColumn<AktionFX,String>("Aktion");
				TableColumn<AktionFX,String> notizColMeetings  = new TableColumn<AktionFX,String>("Notiz");
				notizColMeetings.setPrefWidth(380);
				datumColMeetings.setCellValueFactory(new PropertyValueFactory<AktionFX, Date>("datum"));
				artColMeetings.setCellValueFactory(new PropertyValueFactory<AktionFX, String>("aktionsart"));
				notizColMeetings.setCellValueFactory(new PropertyValueFactory<AktionFX, String>("notiz"));
				tableMeetings.getColumns().addAll(Arrays.asList(datumColMeetings, artColMeetings, notizColMeetings));
				tableMeetings.setItems(olMeetings);	
				tableMeetings.setPlaceholder(new Label("keine Meetings angelegt."));
				
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
				
				//öffne MeetingDetails mit Doppelklick auf Zeile
				tableMeetings.setRowFactory( e -> {
				    TableRow<AktionFX> row = new TableRow<>();
				    row.setOnMouseClicked(f -> {
				        if (f.getClickCount() == 2 && (! row.isEmpty()) ) {
				            Aktion aktion = row.getItem().getmodellAktion();
				            AktionDetailDialog add = new AktionDetailDialog(aktion);
				            add.showAndWait();
				            if(add.getResult() != null) {
					            dbconn.updateAktion(add.getResult());
								refreshAktionen(dbconn,projekt);
								tableMeetings.refresh();
				            }
				        }
				    });
				    return row ;
				});
				
			VBox vbMeetings = new VBox();
				Button btnMeetingShow = new Button("anzeigen");
				Button btnMeetingAdd = new Button("hinzufügen");
				Button btnMeetingRemove = new Button("löschen");
				
		HBox hbAnrufe = new HBox();
		TableView<AktionFX> tableAnrufe = new TableView<AktionFX>();
			TableColumn<AktionFX, Date> datumColAnrufe  = new TableColumn<AktionFX,Date>("Datum");
			TableColumn<AktionFX,String> artColAnrufe  = new TableColumn<AktionFX,String>("Aktion");
			TableColumn<AktionFX,String> notizColAnrufe  = new TableColumn<AktionFX,String>("Notiz");
			notizColAnrufe.setPrefWidth(380);
			datumColAnrufe.setCellValueFactory(new PropertyValueFactory<AktionFX, Date>("datum"));
			artColAnrufe.setCellValueFactory(new PropertyValueFactory<AktionFX, String>("aktionsart"));
			notizColAnrufe.setCellValueFactory(new PropertyValueFactory<AktionFX, String>("notiz"));
			tableAnrufe.getColumns().addAll(Arrays.asList(datumColAnrufe, artColAnrufe, notizColAnrufe));
			tableAnrufe.setItems(olAnrufe);	
			tableAnrufe.setPlaceholder(new Label("keine Anrufe angelegt."));
			
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
			
			//öffne AnrufDetails mit Doppelklick auf Zeile
			tableAnrufe.setRowFactory( e -> {
			    TableRow<AktionFX> row = new TableRow<>();
			    row.setOnMouseClicked(f -> {
			        if (f.getClickCount() == 2 && (! row.isEmpty()) ) {
			            Aktion aktion = row.getItem().getmodellAktion();
			            AktionDetailDialog add = new AktionDetailDialog(aktion);
			            add.showAndWait();
			            if(add.getResult() != null) {
				            dbconn.updateAktion(add.getResult());
							refreshAktionen(dbconn,projekt);
							tableAnrufe.refresh();
			            }
			        }
			    });
			    return row ;
			});
			
			VBox vbAnrufe = new VBox();
				Button btnAnrufShow = new Button("anzeigen");
				Button btnAnrufAdd = new Button("hinzufügen");
				Button btnAnrufRemove = new Button("löschen");
		
		GridPane gpProjektInfo = new GridPane();
		
		Label lbProjektName = new Label("Projektname:");
		Label lbProjektStart = new Label("Projektstart:");
		Label lbStatus = new Label("Status:");
		Label lbAnsprechpartner = new Label("Ansprechpartner:");
		Label lbNotiz = new Label("Notiz:");
		
		TextField txtProjektName = new TextField(projekt.getProjektName());
		DatePicker dpProjektStart = new DatePicker(projekt.getProjektStart().toLocalDate());
		ComboBox<String> cobStatus = new ComboBox<>();
		ComboBox<String> cobPartner = new ComboBox<>();
		TextArea txtNotiz = new TextArea();
		

		cobStatus.setItems(FXCollections.observableArrayList("laufend", "abgeschlossen"));
		cobStatus.getSelectionModel().select(projekt.getStatus());
		cobPartner.setItems(olPartner);
		cobPartner.setValue(projekt.getAnsprechpartner().getVorname()+" "+projekt.getAnsprechpartner().getNachname());
		txtNotiz.setText(projekt.getNotiz());
				
		btnAngebotShow.setPrefWidth(80);
		btnAngebotAdd.setPrefWidth(80);
		btnAngebotRemove.setPrefWidth(80);
		btnMeetingShow.setPrefWidth(80);
		btnMeetingAdd.setPrefWidth(80);
		btnMeetingRemove.setPrefWidth(80);
		btnAnrufShow.setPrefWidth(80);
		btnAnrufAdd.setPrefWidth(80);
		btnAnrufRemove.setPrefWidth(80);
		
		lbTableMeetings.setPadding(new Insets(20,0,0,0));
		lbTableAnrufe.setPadding(new Insets(20,0,0,0));
		lbTableMeetings.setStyle("-fx-font-weight: bold");
		lbTableAnrufe.setStyle("-fx-font-weight: bold");
		lbTableAngebote.setStyle("-fx-font-weight: bold");
				
		txtNotiz.setPrefHeight(403);
		txtNotiz.setPrefWidth(300);
		txtNotiz.setWrapText(true);
		txtProjektName.setPrefWidth(300);
		dpProjektStart.setPrefWidth(300);
		cobStatus.setPrefWidth(300);
		cobPartner.setPrefWidth(300);

		tableAngebote.setPrefWidth(550);
		tableMeetings.setPrefWidth(550);
		tableAnrufe.setPrefWidth(550);
		tableAngebote.setMaxHeight(150);
		tableMeetings.setMaxHeight(150);
		tableAnrufe.setMaxHeight(150);
		
		gpProjektInfo.setPadding(new Insets(18,5,5,5));
		gpProjektInfo.setHgap(2);
		gpProjektInfo.setVgap(5);
		
		gpProjektInfo.add(lbProjektName,0,0);
		gpProjektInfo.add(lbProjektStart,0,1);
		gpProjektInfo.add(lbStatus,0,2);
		gpProjektInfo.add(lbAnsprechpartner,0,3);
		gpProjektInfo.add(lbNotiz,0,4);
		gpProjektInfo.add(txtProjektName, 1, 0);
		gpProjektInfo.add(dpProjektStart, 1, 1);
		gpProjektInfo.add(cobStatus, 1, 2);
		gpProjektInfo.add(cobPartner, 1, 3);
		gpProjektInfo.add(txtNotiz, 1, 4);
		
		vbAngebote.setPadding(new Insets(5));
		vbMeetings.setPadding(new Insets(5));
		vbAnrufe.setPadding(new Insets(5));
		
		vbAngebote.getChildren().addAll(btnAngebotShow,btnAngebotAdd,btnAngebotRemove);
		vbMeetings.getChildren().addAll(btnMeetingShow,btnMeetingAdd,btnMeetingRemove);
		vbAnrufe.getChildren().addAll(btnAnrufShow,btnAnrufAdd,btnAnrufRemove);
		hbAngebote.getChildren().addAll(tableAngebote,vbAngebote);
		hbMeetings.getChildren().addAll(tableMeetings,vbMeetings);
		hbAnrufe.getChildren().addAll(tableAnrufe,vbAnrufe);
		vbTables.getChildren().addAll(lbTableAngebote,hbAngebote,lbTableMeetings,hbMeetings,lbTableAnrufe,hbAnrufe);
		hbMain.getChildren().addAll(gpProjektInfo,vbTables);
		vbTop.getChildren().addAll(lbHead,hbMain);
			
		
		this.setTitle("Projekt ändern");
		this.getDialogPane().setContent(vbTop);
		this.getDialogPane().getButtonTypes().add(new ButtonType("speichern",ButtonData.OK_DONE));
		this.getDialogPane().getButtonTypes().add(new ButtonType("Abbrechen",ButtonData.CANCEL_CLOSE));

		this.setResultConverter( button -> {
			    if (button.getButtonData()==ButtonData.OK_DONE) {
					if(dpProjektStart.getValue() == null || txtProjektName.getText().equals("")) {
						Alertmsgs.error("kein Projektname oder Datum vergeben");
						return null;
					}
					else {
						projekt.setProjektName(txtProjektName.getText());
						projekt.setProjektStart(Date.valueOf(dpProjektStart.getValue()));				
						projekt.setStatus(cobStatus.getSelectionModel().getSelectedItem());
						for(Ansprechpartner a : projekt.getAnsprechpartner().getKunde().getAnsprechpartner()) {
							if((a.getVorname()+" "+a.getNachname()).equals(cobPartner.getSelectionModel().getSelectedItem())){
								projekt.setAnsprechpartner(a);
							}
						}	
						projekt.setNotiz(txtNotiz.getText());
						ArrayList<Angebot> angeboteArr = new ArrayList<Angebot>();
						for(AngebotFX afx : olAngebote) {
							angeboteArr.add(afx.getModellAngebot());
						}
						ArrayList<Aktion> aktionenArr = new ArrayList<Aktion>();
						for(AktionFX akt : olMeetings)
							aktionenArr.add(akt.getmodellAktion());
						
						for(AktionFX akt : olAnrufe)
							aktionenArr.add(akt.getmodellAktion());
						projekt.setAktionen(aktionenArr);
						projekt.setAngebote(angeboteArr);
						return projekt;					
					}
			    }
			    else{
			    	return null;
			    }
		});
		
		//dem Projekt ein Angebot hinzufügen
		btnAngebotAdd.setOnAction(e->{
			if(angeboteinKunde(projekt.getAnsprechpartner().getKunde())) {
				ProjektDetailDialogAngebot pdda = new ProjektDetailDialogAngebot(projekt);
				pdda.showAndWait();	
				if(!(pdda.getResult() == null)) {
					olAngebote.add(new AngebotFX(pdda.getResult()))	;
					projekt.getAngebote().add(pdda.getResult());
					tableAngebote.refresh();
				}
			}
		});
		//Angebotsdetails anzeigen
		btnAngebotShow.setOnAction(e->{
			if(tableAngebote.getSelectionModel().getSelectedItem() != null) {
	            AngebotDetailDialog add = new AngebotDetailDialog(tableAngebote.getSelectionModel().getSelectedItem().getModellAngebot(),dbconn);
	            add.showAndWait();
	            if(add.getResult() != null) {
	                dbconn.updateAngebote(add.getResult());
	                olAngebote.clear();
	        		for(Angebot a : projekt.getAngebote()) {
	        			olAngebote.add(new AngebotFX(a));
	        		}
	        		tableAngebote.refresh();
	            }
			}
			else {
				Alertmsgs.error("kein Angebot ausgewählt");
			}
            
		});
		
		//entfernt ein Angebot aus dem Projekt
		btnAngebotRemove.setOnAction(e->{
			tableAngebote.getSelectionModel().getSelectedItem().getModellAngebot().setProjekt(null);
			projekt.getAngebote().remove(tableAngebote.getSelectionModel().getSelectedItem().getModellAngebot());
			dbconn.updateAngebote(tableAngebote.getSelectionModel().getSelectedItem().getModellAngebot());
			olAngebote.remove(tableAngebote.getSelectionModel().getSelectedItem());
			tableAngebote.refresh();
			
			
		});
		
		tableAngebote.setRowFactory( e -> {
		    TableRow<AngebotFX> row = new TableRow<>();
		    row.setOnMouseClicked(f -> {
		        if (f.getClickCount() == 2 && (! row.isEmpty()) ) {
		            AngebotDetailDialog add = new AngebotDetailDialog(tableAngebote.getSelectionModel().getSelectedItem().getModellAngebot(),dbconn);
		            add.showAndWait();
		            if(add.getResult() != null) {
		                dbconn.updateAngebote(add.getResult());
		                olAngebote.clear();
		        		for(Angebot a : projekt.getAngebote()) {
		        			olAngebote.add(new AngebotFX(a));
		        		}
		        		tableAngebote.refresh();
		            }
		        }
		    });
		    return row ;
		});
		
		//Anzeigen eines ausgewählten Meeting
		btnMeetingShow.setOnAction(e ->{
            AktionDetailDialog add = new AktionDetailDialog(tableMeetings.getSelectionModel().getSelectedItem().getmodellAktion());
            add.showAndWait();
            if(add.getResult() != null) {
	            dbconn.updateAktion(add.getResult());
				refreshAktionen(dbconn,projekt);
				tableMeetings.refresh();
            }
		});
		//Anlegen eines Meetings zu dem Projekt
		btnMeetingAdd.setOnAction(e ->{
			AktionDetailDialog add = new AktionDetailDialog(projekt, "Meeting");
            add.showAndWait();
            if(add.getResult() != null) {
            	dbconn.insertAktion(add.getResult());
	            projekt.getAktionen().add(add.getResult());
            	olMeetings.add(new AktionFX(add.getResult()));
            }
		});
		
		//ein Meeting löschen
		btnMeetingRemove.setOnAction(e ->{
			if(tableMeetings.getSelectionModel().getSelectedItem() != null) {
				dbconn.deleteAktion(tableMeetings.getSelectionModel().getSelectedItem().getmodellAktion());
				projekt.getAktionen().remove(tableMeetings.getSelectionModel().getSelectedItem().getmodellAktion());
				olMeetings.remove(tableMeetings.getSelectionModel().getSelectedItem());
				tableMeetings.refresh();
			}
		});
		
		//Anzeigen eines ausgewählten Telefonats
		btnAnrufShow.setOnAction(e ->{
            AktionDetailDialog add = new AktionDetailDialog(tableAnrufe.getSelectionModel().getSelectedItem().getmodellAktion());
            add.showAndWait();
            if(add.getResult() != null) {
	            dbconn.updateAktion(add.getResult());
				refreshAktionen(dbconn,projekt);
				tableAnrufe.refresh();
            }
		});
		
		//Anlegen eines Telefonats zu dem Projekt
		btnAnrufAdd.setOnAction(e ->{
			AktionDetailDialog add = new AktionDetailDialog(projekt, "Telefonat");
            add.showAndWait();
            if(add.getResult() != null) {
            	dbconn.insertAktion(add.getResult());
	            projekt.getAktionen().add(add.getResult());
            	olAnrufe.add(new AktionFX(add.getResult()));
            }
		});
		
		//ein Telefonat löschen
		btnAnrufRemove.setOnAction(e ->{
			if(tableAnrufe.getSelectionModel().getSelectedItem() != null) {
				dbconn.deleteAktion(tableAnrufe.getSelectionModel().getSelectedItem().getmodellAktion());
				projekt.getAktionen().remove(tableAnrufe.getSelectionModel().getSelectedItem().getmodellAktion());
				olAnrufe.remove(tableAnrufe.getSelectionModel().getSelectedItem());
				tableAnrufe.refresh();
			}
		});

	}
	
	
	
	//Prüft ob zu dem Kunden Angebote vorhanden sind
	public boolean angeboteinKunde(Kunde kunde) {
		int countAngebot=0;
		for(Ansprechpartner a : kunde.getAnsprechpartner()) {
			countAngebot = countAngebot + a.getAngebote().size();

		}
		if(countAngebot ==0) {
			Alertmsgs.error("keine Angebote zu "+kunde.getName()+" vorhanden.");
			return false;
		}
		else {
			return true;
		}
	}
	
	// aktualisiert OlList Meetings oder olList Anrufe
	private void refreshAktionen(DBConn dbconn, Projekt projekt) {
		olMeetings.clear();
		olAnrufe.clear();
		for(Aktion akt : projekt.getAktionen()) {
			if(akt.getAktionsart().equals("Meeting")) {
				olMeetings.add(new AktionFX(akt));
			}
			else {
				olAnrufe.add(new AktionFX(akt));
			}
		}
		
	}
}
