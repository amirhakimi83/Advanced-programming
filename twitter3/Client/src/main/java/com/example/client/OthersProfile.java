package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

public class OthersProfile implements Initializable {
	private User user;
	private User userMain;
	@FXML
	ImageView myHeader;
	@FXML
	ImageView myAvatar;
	@FXML
	Label username;
	@FXML
	Label joinedLabel;
	@FXML
	Label bio;
	@FXML
	Label birthdateLabel;
	@FXML
	Label location;
	@FXML
	Label follower;
	@FXML
	Label following;
	@FXML
	Button follow;
	@FXML
	Button block;
	static Stage stage;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			userMain = (User) IO.in.readObject();
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
			IO.out.writeObject("check follow");
			IO.out.writeObject(userMain);
			IO.out.writeObject(user);
			if ((Boolean)IO.in.readObject()){
				follow.setText("following");
			}else {
				follow.setText("follow");
			}
			IO.out.writeObject("check block");
			IO.out.writeObject(userMain);
			IO.out.writeObject(user);
			if ((Boolean)IO.in.readObject()){
				block.setText("unblock");
			}else {
				block.setText("block");
			}
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
	public void follow() throws IOException, ClassNotFoundException {
		IO.out.writeObject("check follow");
		IO.out.writeObject(userMain);
		IO.out.writeObject(user);
		if ((Boolean) IO.in.readObject()){
			IO.out.writeObject("unfollow");
			IO.out.writeObject(userMain);
			IO.out.writeObject(user);
			IO.out.writeObject("send number following");
			IO.out.writeObject(user);
			follower.setText("follower "+String.valueOf((Integer) IO.in.readObject()));
			follow.setText("follow");
		}else {
			IO.out.writeObject("follow");
			IO.out.writeObject(userMain);
			IO.out.writeObject(user);
			IO.out.writeObject("send number following");
			IO.out.writeObject(user);
			follower.setText("follower "+String.valueOf((Integer) IO.in.readObject()));
			follow.setText("following");
			System.out.println("followed");
		}
	}
	public void block() throws IOException, ClassNotFoundException {
		IO.out.writeObject("check block");
		IO.out.writeObject(userMain);
		IO.out.writeObject(user);
		if ((Boolean)IO.in.readObject()){
			IO.out.writeObject("unBlock");
			IO.out.writeObject(userMain);
			IO.out.writeObject(user);
			block.setText("block");
		}else {
			IO.out.writeObject("block");
			IO.out.writeObject(userMain);
			IO.out.writeObject(user);
			block.setText("unblock");
		}
	}
	public void back() throws IOException {
		IO.out.writeObject("newTweet");
		IO.out.writeObject(userMain);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("search.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		stage.setTitle("twitter2.0");
		Image icon = new Image("D:/apps/twitter2.0/Client/src/main/resources/com/example/client/download.png");
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.show();
	}

}
