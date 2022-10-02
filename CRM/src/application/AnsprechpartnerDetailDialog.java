package application;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AnsprechpartnerDetailDialog extends Dialog <Ansprechpartner>{
	
	//Konstruktor bei Start von "neuen Ansprechpartner anlegen"
	AnsprechpartnerDetailDialog(Kunde kunde){
		
		VBox vb = new VBox(10);
		Label lbhead = new Label("neuer Ansprechpartner für " + kunde.getName());
		GridPane gp = new GridPane();
		
		Label lbVorname = new Label("Vorname: ");
		Label lbNachname = new Label("Nachname: ");
		Label lbTelefon = new Label("Telefon: ");
		Label lbEmail = new Label("Email: ");
		Label lbFunktion = new Label("Funktion: ");
		
		TextField txtVorname = new TextField();
		TextField txtNachname = new TextField();
		TextField txtTelefon = new TextField();
		TextField txtEmail = new TextField();
		TextField txtFunktion = new TextField();
		
		txtVorname.setPrefWidth(300);
		
		gp.setPadding(new Insets(15));
		gp.setHgap(5);
		gp.setVgap(5);
		
		gp.add(lbVorname,0,0);
		gp.add(lbNachname,0,1);
		gp.add(lbTelefon,0,2);
		gp.add(lbEmail,0,3);
		gp.add(lbFunktion,0,4);
		
		gp.add(txtVorname, 1, 0);
		gp.add(txtNachname, 1, 1);
		gp.add(txtTelefon, 1, 2);
		gp.add(txtEmail, 1, 3);
		gp.add(txtFunktion, 1, 4);
		
		
		vb.getChildren().addAll(lbhead,gp);
		
		this.setTitle("Ansprechpartner anlegen");
		this.getDialogPane().setContent(vb);
		this.getDialogPane().getButtonTypes().add(new ButtonType("speichern",ButtonData.OK_DONE));
		this.getDialogPane().getButtonTypes().add(new ButtonType("Abbrechen",ButtonData.CANCEL_CLOSE));

		this.setResultConverter( button -> {
			    if (button.getButtonData()==ButtonData.OK_DONE) {
					if(txtVorname.getText().equals("") || txtNachname.getText().equals("")) {
						Alertmsgs.error("kein Vor- oder Nachname angegeben.\nAnsprechpartner wurde nicht angelegt.");
						return null;
					}
					else {
						Ansprechpartner a = new Ansprechpartner(txtVorname.getText(), txtNachname.getText(), txtTelefon.getText(), txtEmail.getText(), txtFunktion.getText(), kunde);
						return a;
					}
			    }
			    else {
			    	return null;
			    }
			});
	}
	
	//Konstruktor für "Ansprechpartner ändern"
	AnsprechpartnerDetailDialog(Ansprechpartner ansp){
		
		VBox vb = new VBox(10);
		Label lbhead = new Label(" Ansprechpartner " + ansp.getVorname() + " " + ansp.getNachname() + " von " + ansp.getKunde().getName() + " ändern:");
		GridPane gp = new GridPane();
		
		Label lbVorname = new Label("Vorname: ");
		Label lbNachname = new Label("Nachname: ");
		Label lbTelefon = new Label("Telefon: ");
		Label lbEmail = new Label("Email: ");
		Label lbFunktion = new Label("Funktion: ");
		
		TextField txtVorname = new TextField();
		TextField txtNachname = new TextField();
		TextField txtTelefon = new TextField();
		TextField txtEmail = new TextField();
		TextField txtFunktion = new TextField();
		
		txtVorname.setText(ansp.getVorname());
		txtNachname.setText(ansp.getNachname());
		txtTelefon.setText(ansp.getTelefon());
		txtEmail.setText(ansp.getEmail());
		txtFunktion.setText(ansp.getFunktion());
		
		txtVorname.setPrefWidth(300);
		
		gp.setPadding(new Insets(15));
		gp.setHgap(5);
		gp.setVgap(5);
		
		gp.add(lbVorname,0,0);
		gp.add(lbNachname,0,1);
		gp.add(lbTelefon,0,2);
		gp.add(lbEmail,0,3);
		gp.add(lbFunktion,0,4);
		
		gp.add(txtVorname, 1, 0);
		gp.add(txtNachname, 1, 1);
		gp.add(txtTelefon, 1, 2);
		gp.add(txtEmail, 1, 3);
		gp.add(txtFunktion, 1, 4);
		
		
		vb.getChildren().addAll(lbhead,gp);
		
		this.setTitle("Ansprechpartner anlegen");
		this.getDialogPane().setContent(vb);
		this.getDialogPane().getButtonTypes().add(new ButtonType("speichern",ButtonData.OK_DONE));
		this.getDialogPane().getButtonTypes().add(new ButtonType("Abbrechen",ButtonData.CANCEL_CLOSE));

		
		
		  this.setResultConverter( button -> { 
			    if (button.getButtonData()==ButtonData.OK_DONE) {
					if(txtVorname.getText().equals("") || txtNachname.getText().equals("")) {
						Alertmsgs.error("kein Vor- oder Nachname angegeben");
					}
					else {
						ansp.setVorname(txtVorname.getText());
						ansp.setNachname(txtNachname.getText());
						ansp.setTelefon(txtTelefon.getText());
						ansp.setEmail(txtEmail.getText());
						ansp.setFunktion(txtFunktion.getText());
					}
					return ansp;
			    }		      
			    else {
			    	return null;
			    }
		});
	}
}