package org.example;
import java.net.URL;
import java.sql.*;

import com.example.client.Tweet;
import com.example.client.User;
import com.google.gson.Gson;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;


public class Server implements Runnable {
	private ServerSocket server;
	private ExecutorService service;
	private boolean done;

	public Server() {
		this.done = false;
	}
	@Override
	public void run() {
		try {
			server= new ServerSocket(8052);
			service = Executors.newCachedThreadPool();
			while (!done){
				Socket client = server.accept();
				System.out.println("connect");
				ConnectionHandler connectionHandler = new ConnectionHandler(client);
				service.execute(connectionHandler);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Server main = new Server();
		main.run();
//		try {
//			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}


	}
	class ConnectionHandler implements Runnable{
		private Socket  client;
//		private BufferedReader in;
//		private PrintWriter out;
		private ObjectInputStream in;
		private ObjectOutputStream out;

		public ConnectionHandler(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			try {
				in = new ObjectInputStream(client.getInputStream());
				out = new ObjectOutputStream(client.getOutputStream());
				System.out.println("here");
//				Tweet tweet = (Tweet) in.readObject();
//				System.out.println(tweet.getUser().getUsername());
//				profile profile = new profile();
				String request;
				while ((request = (String)in.readObject()) != null){
					System.out.println(request);
					if (request.equals("register")){
						User user = (User) in.readObject();
						System.out.println(user.getUsername());
						signup(user);
					} else if (request.equals("login")) {
						User user = (User) in.readObject();
						login(user);
					} else if (request.equals("sendTweets")) {
						User user = (User) in.readObject();
						timeline timeline = new timeline(user);
						ArrayList<Tweet> tweets = new ArrayList<>();
						for (Tweet tweet:timeline.favTweet()){
							tweets.add(tweet);
						}
						for (Tweet tweet:timeline.tweetArrayList()){
							tweets.add(tweet);
						}
//						for (Tweet tweet: timeline.retweet()){
//							tweets.add(tweet);
//						}
						out.writeObject(tweets);
					} else if (request.equals("like")) {
						Tweet tweet = (Tweet) in.readObject();
						User user = (User) in.readObject();
						if (tweet.checkLike(user.getUsername())) {
							System.out.println("unlike");
							tweet.unLike(user.getUsername());
						}else {
							System.out.println("Like");
							tweet.Like(user.getUsername());
						}
					} else if (request.equals("checkLike")) {
						Tweet tweet = (Tweet) in.readObject();
						User user = (User) in.readObject();
						out.writeObject(String.valueOf(tweet.checkLike(user.getUsername())));
					} else if (request.equals("checkRetweet")) {
						Tweet tweet = (Tweet) in.readObject();
						User user = (User) in.readObject();
						out.writeObject(String.valueOf(tweet.checkRetweet(user.getUsername())));
					} else if (request.equals("retweet")) {
						Tweet tweet = (Tweet) in.readObject();
						User user = (User) in.readObject();
						if (tweet.checkRetweet(user.getUsername())) {
							System.out.println("unRetweet");
							tweet.unRetweet(user.getUsername());
						}else {
							System.out.println("Retweet");
							tweet.Retweet(user.getUsername());
						}
					} else if (request.equals("sendProfile")) {
						Tweet tweet = (Tweet) in.readObject();
						profile profile1 = new profile(tweet.getUser(),tweet.getUser());
						out.writeObject(profile1.getAvatar());
					} else if (request.equals("newTweet")) {
						User user = (User) in.readObject();
						out.writeObject(user);
					} else if (request.equals("tweet")) {
						Tweet tweet = (Tweet) in.readObject();
						Tweet tweet1 = new Tweet(tweet.getUser(),tweet.getMessage(),tweet.getImageInByte(),null);
					} else if (request.equals("setAvatar")) {
						User user = (User) in.readObject();
						profile profile2 = new profile(user,user);
						profile2.chooseAvatar((String) in.readObject());
						System.out.println("Avatar added");
					} else if (request.equals("sendAvatar")) {
						User user =(User) in.readObject();
						user.getUsername();
						profile profile = new profile(user,user);
						out.writeObject(profile.getAvatar());
						out.writeObject(profile.getJoinedDate());
						out.writeObject(profile.getBirthdate());
						if (profile.getBio() != null){
							out.writeObject(profile.getBio());
						}else {
							out.writeObject("bio...");
						}
						out.writeObject(user.getUsername());
						out.writeObject(profile.getLocation());
						out.writeObject(profile.numberOfFollower());
						out.writeObject(profile.numberOfFollowing());
					} else if (request.equals("bio")) {
						User user = (User) in.readObject();
						profile profile = new profile(user,user);
						profile.setBio((String) in.readObject());
						System.out.println("header added");
					} else if (request.equals("send followers")) {
						User user = (User) in.readObject();
						profile profile = new profile(user,user);
						out.writeObject(profile.followers());
					} else if (request.equals("send following")) {
						User user = (User) in.readObject();
						profile profile = new profile(user,user);
						out.writeObject(profile.followings());
					} else if (request.equals("getBio")) {
						User user = (User) in.readObject();
						profile profile = new profile(user,user);
						out.writeObject(profile.bioShow());
					} else if (request.equals("check follow")) {
						User user = (User) in.readObject();
						User userClient = (User) in.readObject();
						profile profile = new profile(user,userClient);
						out.writeObject(profile.checkFollow());
					} else if (request.equals("send Profile")) {
						User user = (User) in.readObject();
						profile profile = new profile(user,user);
						out.writeObject(profile.getAvatar());
					} else if (request.equals("follow")) {
						User user = (User) in.readObject();
						User userClient = (User) in.readObject();
						profile profile = new profile(user,userClient);
						profile.follow();
						System.out.println("followed");
					} else if (request.equals("unfollow")) {
						User user = (User) in.readObject();
						User userClient = (User) in.readObject();
						profile profile = new profile(user,userClient);
						profile.unfollow();
						System.out.println("unfollowed");
					} else if (request.equals("send Avatar")) {
						User user =(User) in.readObject();
						user.getUsername();
						profile profile = new profile(user,user);
						out.writeObject(profile.getAvatar());
					} else if (request.equals("send header")) {
						User user = (User) in.readObject();
						profile profile = new profile(user,user);
						out.writeObject(profile.getHeader());
					} else if (request.equals("setHeader")) {
						User user = (User) in.readObject();
						profile profile = new profile(user,user);
						profile.chooseHeader((String) in.readObject());
					} else if (request.equals("search")) {
						User user = (User)in.readObject();
						String search = (String) in.readObject();
						Search(user,search);
						System.out.println("sent");
					} else if (request.equals("sendUsers")) {
						User userMain = (User) in.readObject();
						User user = (User) in.readObject();
						out.writeObject(userMain);
						out.writeObject(user);
					} else if (request.equals("block")) {
						User userMain = (User) in.readObject();
						User user = (User) in.readObject();
						profile profile = new profile(user,userMain);
						profile.block();
						System.out.println("blocked");
					} else if (request.equals("check block")) {
						System.out.println("ss");
						User userMain = (User) in.readObject();
						User user = (User) in.readObject();
						profile profile = new profile(user,userMain);
						profile.checkBlock();
						out.writeObject(profile.checkBlock());
					} else if (request.equals("unBlock")) {
						User userMain = (User) in.readObject();
						User user = (User) in.readObject();
						profile profile = new profile(user,userMain);
						profile.unBlock();
						System.out.println("unblocked");
					} else if (request.equals("send number following")) {
						User user = (User) in.readObject();
						profile profile = new profile(user,user);
						out.writeObject(profile.numberOfFollower());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();

			}
//			catch (SQLException e) {
//				throw new RuntimeException(e);
//			}
			catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		public void signup(User user) throws IOException {
			System.out.println("sign up");
			if (CheckData("SELECT username FROM user WHERE username = \"" + user.getUsername() + "\"")){
				if (!(user.getUsername() == null|| user.getName() == null|| user.getSurname() == null||user.getPassword() == null||user.getBirthday() == null || user.getCountry() == null || user.getEmailOrPhoneNumber() == null)){
					if(patternMatches(user.getEmailOrPhoneNumber(),"^(.+)@(.+)$")){
						if (CheckData("SELECT username FROM user WHERE email_phonenumber = \"" + user.getEmailOrPhoneNumber() + "\"")){
							if (!(user.getPassword().length()<8)&&(checkPasswordFormat(user.getPassword()))){
								try {
									out.writeObject("success");
									out.writeObject(user);
									addData(user.getUsername(),user.getName(),user.getSurname(),user.getEmailOrPhoneNumber(),user.getPassword()
											,user.getCountry(),user.getBirthday(),user.getRegisterDay(),(String) in.readObject(),(String) in.readObject());
//									timeline timeline = new timeline(user);
//									out.writeObject(timeline.toString());
									//timeline
								} catch (SQLException e) {
									throw new RuntimeException(e);
								} catch (ClassNotFoundException e) {
									throw new RuntimeException(e);
								}
							}
							else {
								out.writeObject("please enter valid password");
							}
						}
						else {
							out.writeObject("this email already exist");
						}
					}else {
						out.writeObject("please enter valid email");
					}

				}
				else {
					out.writeObject("please enter all of information");
				}
			}else {
				out.writeObject("this username already exist");
			}

		}
		public void login(User user) throws SQLException, IOException {
			if (CheckData("SELECT username FROM user WHERE username = \"" + user.getUsername() + "\"")){
				out.writeObject("this username doesnt exist");
			} else if (checkPassword("select pass_word from user where username = \"" + user.getUsername()+"\"",user.getPassword())) {
				out.writeObject("invalid-pass");
			}else {
				System.out.println(user.getUsername() + " login successfully");
				out.writeObject("success");
				out.writeObject(user);
			}
		}
		public User getUser(String user) throws SQLException {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
			PreparedStatement statement = connection.prepareStatement("select * from user where username = \"" + user + "\"");
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			User user1 = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
			return user1;
		}
		public void Search(User clientUser,String search) throws SQLException, IOException {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
			PreparedStatement statement = connection.prepareStatement("select * from user where username = ? or first_name = ? or family_name = ?");
			statement.setString(1,search);
			statement.setString(2,search);
			statement.setString(3,search);
			ResultSet resultSet = statement.executeQuery();
			//resultSet.next();
			AbstractList<User> users = new ArrayList<>();
			while (resultSet.next()){
				User user = new User(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7));
				profile profile = new profile(clientUser,user);
				if (!profile.checkBlock()) {
					users.add(user);
				}
			}
			out.writeObject(users);
		}
		public boolean checkPassword(String query,String password){
			try {
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery();
				resultSet.next();
				System.out.println(resultSet.getString(1));
				return !resultSet.getString(1).equals(password);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		public static boolean checkPasswordFormat(String password) {
			return password.matches(".*[A-Z].*") && password.matches(".*[a-z].*");
		}


		public static boolean patternMatches(String emailAddress, String regexPattern) {
			return Pattern.compile(regexPattern)
					.matcher(emailAddress)
					.matches();
		}
		public boolean CheckData(String query){
			try {
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
				System.out.println("fsof");
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery();
				return !resultSet.next();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		public void addData(String getUsername,String getName,String getSuerName,String getEmailOrPhoneNumber,String getPassword
				,String getCountry,String getBirthday,String getRegisterDay,String imagAsString,String headerAsString) throws SQLException {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
			PreparedStatement statement = connection.prepareStatement("INSERT INTO user VALUES (?,?,?,?,?,?,?,?,?,?,default)");
			statement.setString(1, getUsername);
			statement.setString(2, getName);
			statement.setString(3, getSuerName);
			statement.setString(4, getEmailOrPhoneNumber);
			statement.setString(5, getPassword);
			statement.setString(6, getCountry);
			statement.setString(7, getBirthday);
			statement.setString(8, getRegisterDay);
			byte[] imageInByte = Base64.getDecoder().decode(imagAsString);
			Blob blob = new SerialBlob(imageInByte);
			statement.setBlob(9,blob);
			byte[] headerInByte = Base64.getDecoder().decode(headerAsString);
			Blob blob1 = new SerialBlob(headerInByte);
			statement.setBlob(10,blob1);
			statement.executeUpdate();
		}
	}
}