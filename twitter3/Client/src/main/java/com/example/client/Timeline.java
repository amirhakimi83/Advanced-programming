package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Timeline implements Initializable {
	static Stage stage;
	@FXML
	ScrollPane myScrollpane;
	@FXML
	AnchorPane myAnchorpane;
	@FXML
	Button newTweet;
	@FXML
	ImageView imageView;
	VBox vBox;
	User user;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		vBox = new VBox();
		myScrollpane.setContent(vBox);
		vBox.setSpacing(10);
		vBox.setStyle("-fx-background-color:gray;" +
				"-fx-padding: 8;");
		System.out.println("vbox added");
		try {
			user = (User) IO.in.readObject();
			IO.out.writeObject("sendTweets");
			IO.out.writeObject(user);
			ArrayList<Tweet> tweets = (ArrayList<Tweet>) IO.in.readObject();
			for (Tweet tweet: tweets){
				ViewTweet viewTweet = new ViewTweet(tweet,user);
				vBox.getChildren().add(viewTweet);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	@FXML
	public void openNewTweet() throws IOException {
		IO.out.writeObject("newTweet");
		IO.out.writeObject(user);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newTweet.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("twitter2.0");
		Image icon = new Image("D:/apps/twitter2.0/Client/src/main/resources/com/example/client/download.png");
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	public void goToProfile() throws IOException {
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
	public void goToSearch() throws IOException {
		IO.out.writeObject("newTweet");
		IO.out.writeObject(user);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("twitter2.0");
		Image icon = new Image("D:/apps/twitter2.0/Client/src/main/resources/com/example/client/download.png");
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.show();
	}
}
