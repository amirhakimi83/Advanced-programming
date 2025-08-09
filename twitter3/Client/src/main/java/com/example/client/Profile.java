package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

public class Profile implements Initializable {
	@FXML
	ImageView myHeader;
	@FXML
	ImageView myAvatar;
	private User user;
	@FXML
	Circle profileCircle;
	@FXML
	Label joinedLabel;
	@FXML
	Label birthdateLabel;
	@FXML
	Label bio;
	@FXML
	Label username;
	@FXML
	Label location;
	@FXML
	Label follower;
	@FXML
	Label following;
	static Stage stage;

	public void setBio() throws IOException {
		IO.out.writeObject("newTweet");
		IO.out.writeObject(user);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("header.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("twitter2.0");
		Image icon = new Image("D:/apps/twitter2.0/Client/src/main/resources/com/example/client/download.png");
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.show();
//		IO.out.writeObject("newTweet");
//		IO.out.writeObject(user);
//		FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("profile.fxml"));
//		Scene scene1 = new Scene(fxmlLoader.load());
//		stage.setTitle("twitter2.0");
//		Image icon1 = new Image("D:/apps/twitter2.0/Client/src/main/resources/com/example/client/download.png");
//		stage.getIcons().add(icon);
//		stage.setScene(scene);
//		stage.show();
	}
	public void chooseMyAvatar() throws IOException, ClassNotFoundException {
		IO.out.writeObject("setAvatar");
		IO.out.writeObject(user);
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(new Stage());
		FileInputStream fileInputStream = new FileInputStream(file);
		byte[] imageInByte = fileInputStream.readAllBytes();
		String imageAsString = Base64.getEncoder().encodeToString(imageInByte);
		IO.out.writeObject(imageAsString);
		fileInputStream.close();
		IO.out.writeObject("send Avatar");
		IO.out.writeObject(user);
		String receivedImage = (String) IO.in.readObject();
		byte[] imageData = Base64.getDecoder().decode(receivedImage);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
		Image image = new Image(inputStream);
		myAvatar.setImage(image);
	}
	public void chooseHeader() throws IOException, ClassNotFoundException {
		IO.out.writeObject("setHeader");
		IO.out.writeObject(user);
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(new Stage());
		FileInputStream fileInputStream = new FileInputStream(file);
		byte[] imageInByte = fileInputStream.readAllBytes();
		String imageAsString = Base64.getEncoder().encodeToString(imageInByte);
		IO.out.writeObject(imageAsString);
		fileInputStream.close();
		IO.out.writeObject("send header");
		IO.out.writeObject(user);
		String receivedImage = (String) IO.in.readObject();
		byte[] imageData = Base64.getDecoder().decode(receivedImage);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
		Image image = new Image(inputStream);
		myHeader.setImage(image);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			user = (User)IO.in.readObject();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		try {
			System.out.println("getAvatar");
			IO.out.writeObject("sendAvatar");
			IO.out.writeObject(user);
			String receivedImage = (String) IO.in.readObject();
			byte[] imageData = Base64.getDecoder().decode(receivedImage);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
			Image image = new Image(inputStream);
			myAvatar.setImage(image);
			System.out.println("avatar added");
			joinedLabel.setText("Joined "+(String) IO.in.readObject());
			birthdateLabel.setText("Born "+(String) IO.in.readObject());
			bio.setText((String) IO.in.readObject());
			username.setText((String) IO.in.readObject());
			location.setText((String) IO.in.readObject());
			follower.setText("follower "+String.valueOf((Integer) IO.in.readObject()));
			following.setText("following "+String.valueOf(IO.in.readObject()));
			IO.out.writeObject("send header");
			IO.out.writeObject(user);
			String receivedImage1 = (String) IO.in.readObject();
			byte[] imageData1= Base64.getDecoder().decode(receivedImage1);
			ByteArrayInputStream inputStream1 = new ByteArrayInputStream(imageData1);
			Image image1 = new Image(inputStream1);
			myHeader.setImage(image1);
//			System.out.println("s");
		}catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	public void goToFollowerList() throws IOException {
		IO.out.writeObject("newTweet");
		IO.out.writeObject(user);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listFollow'.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("twitter2.0");
		Image icon = new Image("D:/apps/twitter2.0/Client/src/main/resources/com/example/client/download.png");
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.show();
	}
	public void goToFollowingList() throws IOException, ClassNotFoundException {
		IO.out.writeObject("newTweet");
		IO.out.writeObject(user);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FollowingList.fxml"));
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
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("timeline.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("twitter2.0");
		Image icon = new Image("D:/apps/twitter2.0/Client/src/main/resources/com/example/client/download.png");
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.show();
	}
}
