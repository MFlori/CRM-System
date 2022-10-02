package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ControllerLoginOptions implements Initializable{

	@FXML
	private TextField conntxt;
	
	@FXML
	private Button connOK;
	
	//beim start des Fensters wird die Verbindung im Textfeld angezeigt
	public void initialize(URL arg0, ResourceBundle arg1) {
		conntxt.setText(setConn());
	}
	
	public String getConnText() {
		return conntxt.getText();
	}

	//holt die Verbindung aus connection.txt
	public String setConn() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("./data/connection.txt"));
			String s = br.readLine();
			br.close();
			return s;
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//speichert die Verbindung nach connection.txt und schlieﬂt den Dialog
	public void saveConnection(ActionEvent event) {
		String s = conntxt.getText();
		try {
			FileOutputStream fos = new FileOutputStream(new File("./data/connection.txt"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write(s);
			bw.close();
			
			Stage stage1 = (Stage) ((Node)event.getSource()).getScene().getWindow();
			stage1.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
