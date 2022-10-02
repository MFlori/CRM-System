package application;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ControllerLogin {

	@FXML private Button loginBtn;
	@FXML private TextField userField;
	@FXML private PasswordField pwField;
	@FXML private ImageView closeImage;
	@FXML private ImageView optionsImage;
	
	@FXML
	private void close() {
		Platform.exit();
	}
	
	//Wenn Maus auf dem close-image --> ändere Bild 
	public void mouseOnEnterCloseImage() {
		try {
			closeImage.setImage(new Image(new FileInputStream("./resource/closeOnMouse.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Wenn Maus close-image verlässt --> ändere Bild 
	public void mouseOnExitCloseImage() {
		try {
			closeImage.setImage(new Image(new FileInputStream("./resource/close.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Wenn Maus auf dem options-image --> ändere Bild
	public void mouseOnEnterOptionsImage() {
		try {
			optionsImage.setImage(new Image(new FileInputStream("./resource/optionsOnMouse.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Wenn Maus options-image verlässt --> ändere Bild 
	public void mouseOnExitOptionsImage() {
		try {
			optionsImage.setImage(new Image(new FileInputStream("./resource/options.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Methode zum Scene-Wechsel nach Login
	public void changetoMainScreen(ActionEvent event) {
		try {
			DBConn dbconn = new DBConn(DriverManager.getConnection(getConnection(), userField.getText(), pwField.getText())); //Datenbankverbindung

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
			Parent root = (Parent)fxmlLoader.load();
			ControllerMain controllerMain = fxmlLoader.<ControllerMain>getController();
			controllerMain.setDbconn(dbconn);
			Scene mainScene = new Scene (root);
			mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			
			stage.setScene(mainScene);
			stage.setX(0);
			stage.setY(0);
			stage.setWidth(Screen.getPrimary().getBounds().getMaxX());
			stage.setHeight(Screen.getPrimary().getBounds().getMaxY()-40);
			stage.show();
			
		} catch (IOException | SQLException e) {
			Alertmsgs.error("Anmeldung fehlgeschlagen.\nBenutzername, Passwort oder Verbindung ungültig\nVerbindung: "+getConnection());
			pwField.setText("");
		}
	}

	//Methode zum Ändern der Datenbankverbindung
	public void onOptionsImageClicked() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginOptions.fxml"));
		try {
			Parent parent = fxmlLoader.load();
	        Scene scene = new Scene(parent);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        scene.setFill(Color.TRANSPARENT);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.initStyle(StageStyle.TRANSPARENT);
	        stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Methode zum Holen der Datenbankverbindung
	public String getConnection() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("./data/connection.txt"));
			String s = br.readLine();
			br.close();
			return s;
		} catch (IOException e) {
			e.printStackTrace();
			return "no connection found";
		}
	}
}
