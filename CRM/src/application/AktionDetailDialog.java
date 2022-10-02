package application;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.sql.Date;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AktionDetailDialog extends Dialog <Aktion>{ 

	//neue Aktion für Angebot anlegen
	public AktionDetailDialog(Angebot angebot, String aktionsArt) {
		
		VBox vbTop = new VBox(10);
		GridPane gp = new GridPane();
		
		Label lbHead = new Label(angebot.getAnsprechpartner().getKunde().getName()+" - Angebot "+angebot.getIdAngebot()+": "+aktionsArt+" erfassen");
		Label lbDatum = new Label("Datum");
		Label lbNotiz = new Label("Notiz");
		DatePicker dpAktionDatum = new DatePicker();
		TextArea txtNotiz = new TextArea();
		
		gp.add(lbDatum, 0, 0);
		gp.add(lbNotiz, 0, 1);
		gp.add(dpAktionDatum, 1, 0);
		gp.add(txtNotiz, 1, 1);
		gp.setHgap(5);
		gp.setVgap(2);
		
		
		txtNotiz.setPromptText("Gesprächsinhalt hier anführen...");
		txtNotiz.setWrapText(true);
		vbTop.getChildren().addAll(lbHead, gp);
		
		this.setTitle(aktionsArt+" erfassen");
		this.getDialogPane().setContent(vbTop);
		
		ButtonType save = new ButtonType("speichern",ButtonData.OK_DONE);
		ButtonType quit = new ButtonType("abbrechen",ButtonData.CANCEL_CLOSE);
		
		this.getDialogPane().getButtonTypes().addAll(save,quit);
		
		this.setResultConverter( button -> {
		    if (button.getButtonData()==ButtonData.OK_DONE) {
		    	return new Aktion(txtNotiz.getText(),Date.valueOf(dpAktionDatum.getValue()),aktionsArt, angebot,null);
		    }
		    return null;
		});
	}
	
	//neue Aktion für Projekt anlegen
		public AktionDetailDialog(Projekt projekt, String aktionsArt) {
			
			VBox vbTop = new VBox(10);
			GridPane gp = new GridPane();
			
			Label lbHead = new Label(projekt.getAnsprechpartner().getKunde().getName()+" - Projekt "+projekt.getProjektName()+": "+aktionsArt+" erfassen");
			Label lbDatum = new Label("Datum");
			Label lbNotiz = new Label("Notiz");
			DatePicker dpAktionDatum = new DatePicker();
			TextArea txtNotiz = new TextArea();
			
			gp.add(lbDatum, 0, 0);
			gp.add(lbNotiz, 0, 1);
			gp.add(dpAktionDatum, 1, 0);
			gp.add(txtNotiz, 1, 1);
			gp.setHgap(5);
			gp.setVgap(2);
			
			
			txtNotiz.setPromptText("Gesprächsinhalt hier anführen...");
			txtNotiz.setWrapText(true);
			vbTop.getChildren().addAll(lbHead, gp);
			
			this.setTitle(aktionsArt+" erfassen");
			this.getDialogPane().setContent(vbTop);
			
			ButtonType save = new ButtonType("speichern",ButtonData.OK_DONE);
			ButtonType quit = new ButtonType("abbrechen",ButtonData.CANCEL_CLOSE);
			
			this.getDialogPane().getButtonTypes().addAll(save,quit);
			
			this.setResultConverter( button -> {
			    if (button.getButtonData()==ButtonData.OK_DONE) {
			    	return new Aktion(txtNotiz.getText(),Date.valueOf(dpAktionDatum.getValue()),aktionsArt, null,projekt);
			    }
			    return null;
			});
		}

	//Aktion ändern
	public AktionDetailDialog(Aktion a) {
		VBox vbTop = new VBox(10);
		GridPane gp = new GridPane();
		
		Label lbHead = new Label(a.getAktionsart()+" ändern");
		Label lbDatum = new Label("Datum");
		Label lbNotiz = new Label("Notiz");
		DatePicker dpAktionDatum = new DatePicker(a.getDatum().toLocalDate());
		TextArea txtNotiz = new TextArea(a.getNotiz());
		
		gp.add(lbDatum, 0, 0);
		gp.add(lbNotiz, 0, 1);
		gp.add(dpAktionDatum, 1, 0);
		gp.add(txtNotiz, 1, 1);
		gp.setHgap(5);
		gp.setVgap(2);
		
		txtNotiz.setWrapText(true);
		vbTop.getChildren().addAll(lbHead, gp);
		
		this.setTitle(a.getAktionsart()+" ändern");
		this.getDialogPane().setContent(vbTop);
		
		ButtonType save = new ButtonType("speichern",ButtonData.OK_DONE);
		ButtonType quit = new ButtonType("abbrechen",ButtonData.CANCEL_CLOSE);
		
		this.getDialogPane().getButtonTypes().addAll(save,quit);
		
		this.setResultConverter( button -> {
		    if (button.getButtonData()==ButtonData.OK_DONE) {
		    	a.setDatum(Date.valueOf(dpAktionDatum.getValue()));
		    	a.setNotiz(txtNotiz.getText());
		    	return a;
		    	
		    }
		    return null;
		});
	}
	
}
