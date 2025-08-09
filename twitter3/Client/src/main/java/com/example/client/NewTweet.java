package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewTweet implements Initializable {
	private User user;
	@FXML
	TextArea textArea;
	@FXML
	ImageView imageView;
	@FXML
	Label imageString;
	public void chooseImage() throws IOException {
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(new Stage());
		FileInputStream fileInputStream = new FileInputStream(file);
		byte[] imageInByte = fileInputStream.readAllBytes();
		String imageAsString = Base64.getEncoder().encodeToString(imageInByte);
		fileInputStream.close();
		imageString.setText(imageAsString);
	}
	public void tweet() throws IOException, ClassNotFoundException {
		user = (User) IO.in.readObject();
		String text = textArea.getText();
		String hashtag = null;
		Pattern pattern = Pattern.compile("#(\\w+)");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			hashtag = matcher.group(1);
			int startIndex = matcher.end();
			int endIndex = text.indexOf(" ", startIndex);
			if (endIndex == -1) {
				endIndex = text.length();
			}
		}
//		String nextPhrase = text.substring(startIndex, endIndex);
//		String hash = null;
//		for (int i = 0; i < text.length(); i++) {
//			if (text.charAt(i) == '#'){
//				for (int j = i; (text.charAt(j) == '#') ; j++) {
//					hash.charAt(j) == text.charAt(j);
//				}
//			}
//		}
		System.out.println(hashtag);
		Tweet tweet = new Tweet(user,textArea.getText(),0,0,0,new Date(),imageString.getText(),false,"0",null);
		IO.out.writeObject("tweet");
		IO.out.writeObject(tweet);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		imageString.setText(null);
	}
}
