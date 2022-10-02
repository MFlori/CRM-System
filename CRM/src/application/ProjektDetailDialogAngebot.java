package application;


import java.sql.Date;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProjektDetailDialogAngebot extends Dialog<Angebot>{
	
	//Konstruktor ProjektDetailDialogAngebot - Dialog zeigt Liste mit Angeboten des Kunden, die dem Projekt hinzugefügt werden können
	public ProjektDetailDialogAngebot(Projekt projekt) {

		VBox vb = new VBox(10);
		Label lbHead = new Label("Angebot auswählen:");
		ObservableList<AngebotFX> olAngebote = FXCollections.observableArrayList();
		
		for(Ansprechpartner a : projekt.getAnsprechpartner().getKunde().getAnsprechpartner()) {
			for(Angebot ang: a.getAngebote()) {
				olAngebote.add(new AngebotFX(ang));
			}
		}
		
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
		
		tableAngebote.setPrefWidth(560);
		vb.getChildren().addAll(lbHead,tableAngebote);
		this.setTitle("Angebot zu Projekt hinzufügen");
		this.getDialogPane().setContent(vb);
		this.getDialogPane().getButtonTypes().add(new ButtonType("speichern",ButtonData.OK_DONE));
		this.getDialogPane().getButtonTypes().add(new ButtonType("Abbrechen",ButtonData.CANCEL_CLOSE));
		
		this.setResultConverter( button -> {
		    if (button.getButtonData()==ButtonData.OK_DONE && tableAngebote.getSelectionModel().getSelectedItem()!=null) {
		    	return tableAngebote.getSelectionModel().getSelectedItem().getModellAngebot();
		    }
		    return null;
		});
	}
}
