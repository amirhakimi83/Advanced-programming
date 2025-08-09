package com.example.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
	static Socket socket;
	static Stage stage;
	static ObjectOutputStream out;
	static ObjectInputStream in;
	Scene scene;
	Parent root;

	@FXML
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		System.out.println("dad");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
					IO.setValues();
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {
								FXMLLoader loader = new FXMLLoader(getClass().getResource("loginSignUp.fxml"));
								scene = new Scene(loader.load());
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
						});
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});
		thread.start();
	}
}