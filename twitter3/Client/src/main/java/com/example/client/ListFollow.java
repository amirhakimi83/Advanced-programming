package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListFollow implements Initializable {
	static Stage stage;
	@FXML
	ScrollPane myScrollpane;
	@FXML
	AnchorPane myAnchorpane;
	User user;
	VBox vBox;
	ArrayList<User> followerList;
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			user = (User)IO.in.readObject();
			System.out.println(user.getUsername());
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		vBox = new VBox();
		myScrollpane.setContent(vBox);
		vBox.setSpacing(10);
		vBox.setStyle("-fx-background-color:gray;" +
				"-fx-padding: 8;");
		System.out.println("vbox added");
		try {
			IO.out.writeObject("send followers");
			IO.out.writeObject(user);
			followerList = (ArrayList<User>) IO.in.readObject();
			for (User user1:followerList){
				System.out.println(user.getUsername());
				System.out.println(user1.getUsername());
				viewUser viewUser = new viewUser(user,user1);
				System.out.println(user.getUsername());
				vBox.getChildren().add(viewUser);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
