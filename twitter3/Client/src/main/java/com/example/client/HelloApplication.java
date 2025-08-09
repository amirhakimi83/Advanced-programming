package com.example.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

public class HelloApplication extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		Search.stage = stage;
		viewUser.stage = stage;
		OthersProfile.stage = stage;
		Header.stage = stage;
		FollowingList.stage = stage;
		ListFollow.stage = stage;
		Profile.stage = stage;
		Timeline.stage = stage;
		Login.stage = stage;
		SignUP.stage = stage;
		HelloController.stage = stage;
		LoginSignUp.stage = stage;
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("twitter2.0");
		Image icon = new Image("D:/apps/twitter2.0/Client/src/main/resources/com/example/client/download.png");
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.show();

	}
//	public static Image profileImage(User user) throws SQLException {
//		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
//		PreparedStatement statement = connection.prepareStatement("select avatar from user where username = ?");
//		statement.setString(1,user.getUsername());
//		ResultSet resultSet = statement.executeQuery();
//		resultSet.next();
//		Image image = new Image(resultSet.getBinaryStream(1));
//		return image;
//	}
//	public static Image getImage(Tweet tweet) throws SQLException {
//		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
//		PreparedStatement statement = connection.prepareStatement("select image from tweet where tweetid = ?");
//		statement.setString(1,tweet.getId());
//		ResultSet resultSet = statement.executeQuery();
//		resultSet.next();
//		Image image = new Image(resultSet.getBinaryStream(1));
//		return image;
//	}
	public static void main(String[] args) {
		launch();
	}
}