package com.example.client;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginSignUp {

	static Stage stage;
	public void goToSignUp(ActionEvent actionEvent){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signUP.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setTitle("twitter2.0");
			Image icon = new Image("D:/apps/twitter2.0/Client/src/main/resources/com/example/client/download.png");
			stage.getIcons().add(icon);
			stage.setScene(scene);
			stage.show();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	@FXML
	protected void onMouseOver(Event event){
		Button button = (Button) event.getSource();
		button.setStyle("-fx-background-color: #010101");
	}
	@FXML
	protected void onMouseExit(Event event){
		Button button = (Button) event.getSource();
		button.setStyle("-fx-background-color: #42e7fc");
	}
	@FXML
	public void goToLogin() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("twitter2.0");
		Image icon = new Image("D:/apps/twitter2.0/Client/src/main/resources/com/example/client/download.png");
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.show();
	}

}
