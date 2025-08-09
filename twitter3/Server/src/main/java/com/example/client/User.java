package com.example.client;

import java.io.Serializable;
import java.time.LocalDate;


public class User implements Serializable {
	private String username;
	private String name;
	private String surname;
	private String emailOrPhoneNumber;
	private String password;
	private String country;
	private String birthday;
	private String registerDay;
	public User(){}

	public User(String username, String name, String surname, String emailOrPhoneNumber, String password, String country, String birthday) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.emailOrPhoneNumber = emailOrPhoneNumber;
		this.password = password;
		this.country = country;
		this.birthday = birthday;
		this.registerDay = String.valueOf(LocalDate.now());
	}

	public User(String username,String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getRegisterDay() {
		return registerDay;
	}

	public void setRegisterDay(String registerDay) {
		this.registerDay = registerDay;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmailOrPhoneNumber() {
		return emailOrPhoneNumber;
	}

	public void setEmailOrPhoneNumber(String emailOrPhoneNumber) {
		this.emailOrPhoneNumber = emailOrPhoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
}
