package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Header {
	static Stage stage;
	private User user;
	@FXML
	TextArea textArea;
	public void confirm() throws IOException, ClassNotFoundException {
		user = (User) IO.in.readObject();
		IO.out.writeObject("bio");
		IO.out.writeObject(user);
		IO.out.writeObject(textArea.getText());
		IO.out.writeObject("newTweet");
		IO.out.writeObject(user);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("twitter2.0");
		Image icon = new Image("D:/apps/twitter2.0/Client/src/main/resources/com/example/client/download.png");
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.show();
	}
	public void back() throws IOException {
		IO.out.writeObject("newTweet");
		IO.out.writeObject(user);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("profile.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("twitter2.0");
		Image icon = new Image("D:/apps/twitter2.0/Client/src/main/resources/com/example/client/download.png");
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.show();
	}
}
