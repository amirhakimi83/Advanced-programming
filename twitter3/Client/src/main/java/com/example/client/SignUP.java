package com.example.client;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;


public class SignUP implements Initializable {

	static Stage stage;
	@FXML
	TextField myName;
	@FXML
	TextField myLastName;
	@FXML
	TextField myUserName;
	@FXML
	TextField myEmail;
	@FXML
	PasswordField myPassword;
	@FXML
	DatePicker datePicker;
	@FXML
	Label error;
	@FXML
	PasswordField repeatPassword;
	@FXML
	ChoiceBox<String > choiceBox;
	ObservableList<String> temp = FXCollections.observableArrayList("Afghanistan","Albania","Algeria","Andorra","Angola","Antigua & Deps","Argentina","Armenia","Australia","Austria","Azerbaijan","Bahamas","Bahrain","Bangladesh","Barbados","Belarus","Belgium","Belize","Benin","Bhutan","Bolivia","Bosnia Herzegovina","Botswana","Brazil","Brunei","Bulgaria","Burkina","Burundi","Cambodia","Cameroon","Canada","Cape Verde","Central African Rep","Chad","Chile","China","Colombia","Comoros","Congo","Congo {Democratic Rep}","Costa Rica","Croatia","Cuba","Cyprus","Czech Republic","Denmark","Djibouti","Dominica","Dominican Republic","East Timor","Ecuador","Egypt","El Salvador","Equatorial Guinea","Eritrea","Estonia","Ethiopia","Fiji","Finland","France","Gabon","Gambia","Georgia","Germany","Ghana","Greece","Grenada","Guatemala","Guinea","Guinea-Bissau","Guyana","Haiti","Honduras","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland {Republic}","Israel","Italy","Ivory Coast","Jamaica","Japan","Jordan","Kazakhstan","Kenya","Kiribati","Korea North","Korea South","Kosovo","Kuwait","Kyrgyzstan","Laos","Latvia","Lebanon","Lesotho","Liberia","Libya","Liechtenstein","Lithuania","Luxembourg","Macedonia","Madagascar","Malakhestan","Malawi","Malaysia","Maldives","Mali","Malta","Marshall Islands","Mauritania","Mauritius","Mexico","Micronesia","Moldova","Monaco","Mongolia","Montenegro","Morocco","Mozambique","Myanmar, {Burma}","Namibia","Nauru","Nepal","Netherlands","New Zealand","Nicaragua","Niger","Nigeria","Norway","Oman","Pakistan","Palau","Panama","Papua New Guinea","Paraguay","Peru","Philippines","Poland","Portugal","Qatar","Qom","Romania","Russian Federation","Rwanda","St Kitts & Nevis","St Lucia","Saint Vincent & the Grenadines","Samoa","San Marino","Sao Tome & Principe","Senegal","Serbia","Seychelles","Sierra Leone","Singapore","Slovakia","Slovenia","Solomon Islands","Somalia","South Africa","South Sudan","Spain","Sri Lanka","Sudan","Suriname","Swaziland","Sweden","Switzerland","Syria","Taiwan","Tajikistan","Tanzania","Thailand","Togo","Tonga","Trinidad & Tobago","Tunisia","Turkey","Turkmenistan","Tuvalu","Uganda","Ukraine","United Arab Emirates","United Kingdom","United States","Uruguay","Uzbekistan","Vanuatu","Vatican City","Venezuela","Vietnam","Yemen","Zambia","Zimbabwe");


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		choiceBox.setItems(temp);
	}

	@FXML
	public void confirm(){
		try {
			FileInputStream imageFile = new FileInputStream("D:/apps/twitter3/Client/src/main/resources/com/example/client/avatar.png");
			byte[] imageInByte = imageFile.readAllBytes();
			String imageAsString = Base64.getEncoder().encodeToString(imageInByte);
			FileInputStream imageFile1 = new FileInputStream("D:/apps/twitter3/Client/src/main/resources/com/example/client/defaultheader.jpg");
			byte[] imageInByte1 = imageFile1.readAllBytes();
			String imageAsString1 = Base64.getEncoder().encodeToString(imageInByte1);
			User user = new User(myUserName.getText(),myName.getText(),myLastName.getText(),myEmail.getText(),myPassword.getText(),choiceBox.getValue(),String.valueOf(datePicker.getValue()));
			if (!repeatPassword.getText().equals(myPassword.getText())){
				error.setText("Password doesn't match!");
			}
			else {
				IO.out.writeObject("register");
				IO.out.writeObject(user);
				switch ((String) IO.in.readObject()){
					case "this username already exist":
						System.out.println("klfn");
						error.setText("this username already exist");
						break;
					case "please enter all of information":
						error.setText("please enter all of information");
						break;
					case "please enter valid email":
						error.setText("please enter valid email");
						break;
					case "this email already exist":
						error.setText("this email already exist");
						break;
					case "please enter valid password":
						error.setText("please enter valid password");
						break;
					case "success":
						IO.out.writeObject(imageAsString);
						IO.out.writeObject(imageAsString1);
						System.out.println("success");
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
			}

		}
		catch (IOException e){

		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
