package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alertmsgs {
	
	//liefert einen Alert vom Type ERROR - verkürzt Code für Alerts in anderen Klassen auf eine Zeile
	public static void error (String s) {
		Alert alert1;
		alert1 = new Alert(AlertType.ERROR);
		alert1.setHeaderText(s);
		alert1.show();
	}
	//liefert einen Alert vom Type Information - verkürzt Code für Alerts in anderen Klassen auf eine Zeile
	public static void info(String s) {
		Alert alert1;
		alert1 = new Alert(AlertType.INFORMATION);
		alert1.setHeaderText(s);
		alert1.show();
	}

}
