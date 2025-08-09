package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Search implements Initializable {
	User user;
	@FXML
	TextField myTextField;
	AbstractList<User> users;
	@FXML
	ScrollPane myScrollPane;
	VBox vBox;
	static Stage stage;
	public void search() throws IOException, ClassNotFoundException {
		String searchText = myTextField.getText();
		IO.out.writeObject("search");
		IO.out.writeObject(user);
		IO.out.writeObject(searchText);
		System.out.println(user.getUsername());
		try {
		users = (ArrayList<User>)IO.in.readObject();
		for (User user1:users){
			System.out.println(user.getUsername());
			System.out.println(user1.getUsername());
			viewUser viewUser = new viewUser(user,user1);
			System.out.println(user.getUsername());
			vBox.getChildren().add(viewUser);
		}
		}catch (IOException e){
			e.printStackTrace();
		}
	}


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			user = (User) IO.in.readObject();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		vBox = new VBox();
		myScrollPane.setContent(vBox);
		vBox.setSpacing(10);
		vBox.setStyle("-fx-background-color:gray;" +
				"-fx-padding: 8;");
		System.out.println("vbox added");
	}
	public void back() throws IOException {
		IO.out.writeObject("newTweet");
		IO.out.writeObject(user);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("timeline.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("twitter2.0");
		Image icon = new Image("D:/apps/twitter2.0/Client/src/main/resources/com/example/client/download.png");
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.show();
	}
}
