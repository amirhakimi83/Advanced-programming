package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {
	static Stage stage;
	@FXML
	TextField myUserName;
	@FXML
	TextField myPassword;
	@FXML
	Label error;
	@FXML
	public void confirm(){
		try {
			IO.out.writeObject("login");
			User user = new User(myUserName.getText(), myPassword.getText());
			IO.out.writeObject(user);
			String respond = (String) IO.in.readObject();
			switch (respond) {
				case "this username doesnt exist":
					error.setText("this username doesnt exist");
					break;
				case "invalid-pass":
					error.setText("invalid-pass");
					break;
				case "success":
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("timeline.fxml"));
					Scene scene = new Scene(fxmlLoader.load());
					//System.out.println("dmasop");
					stage.setTitle("twitter2.0");
					Image icon = new Image("D:/apps/twitter2.0/Client/src/main/resources/com/example/client/download.png");
					stage.getIcons().add(icon);
					stage.setScene(scene);
					stage.show();
					break;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
