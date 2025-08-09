package org.example;
import com.example.client.Tweet;
import com.example.client.User;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import static javax.swing.UIManager.getString;

public class profile {
	private User user;
	private User userClient;
	private ArrayList<Tweet> tweetArrayList = new ArrayList<>();
	private ArrayList<String> blockList = new ArrayList<>();
	private String imageInString;

	public profile(User user, User userClient) {
		this.user = user;
		this.userClient = userClient;
	}
	public void unBlock() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("DELETE FROM block WHERE blocker = ? and blocked = ?");
		statement.setString(1, userClient.getUsername());
		statement.setString(2, user.getUsername());
		statement.executeUpdate();
		blockList.remove(getUser(user.getUsername()));
	}
	public void block() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("INSERT INTO block VALUES (?,?)");
		statement.setString(1, userClient.getUsername());
		statement.setString(2, user.getUsername());
		statement.executeUpdate();
		blockList.add(userClient.getUsername());
		PreparedStatement statement1 = connection.prepareStatement("delete from follow where following = ? and follower = ?");
		statement1.setString(1,user.getUsername());
		statement1.setString(2,userClient.getUsername());
		statement1.executeUpdate();
		PreparedStatement statement2 = connection.prepareStatement("delete from follow where following = ? and follower = ?");
		statement2.setString(1,userClient.getUsername());
		statement2.setString(2,user.getUsername());
		statement2.executeUpdate();
	}


	public ArrayList<String> getBlockList() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select *from block where blocker =? and  blocked = ?");
		statement.setString(1,userClient.getUsername());
		statement.setString(2,user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()){
			blockList.add(resultSet.getString(2));
		}
		return blockList;
	}
	public void setBlockList(User user11){
		blockList.add(user11.getUsername());
	}


	public String setBio(String bio)  {
		try {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("update user set bio = ? where username = ?");
		statement.setString(1,bio);
		statement.setString(2,userClient.getUsername());
		statement.executeUpdate();
		return"";
		}catch (SQLException e){
			return "The entered phrase is greater than the limit";
		}
	}
	public String bioShow() throws SQLException {
		String bio;
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select bio from user where username = ?");
		statement.setString(1,user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		bio = resultSet.getString(1);
		return bio;
	}
//	public void setBio(String bio) throws SQLException {
//		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
//		PreparedStatement statement = connection.prepareStatement("update user set avatar = ? where username = ?");
//		statement.setString(1, bio);
//		statement.setString(2, user.getUsername());
//		statement.executeUpdate();
//		System.out.println("photo added");
//	}
	public boolean checkBlock() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select *from block where blocker =? and  blocked = ?");
		statement.setString(1,userClient.getUsername());
		statement.setString(2,user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		return resultSet.next();
	}

	public boolean checkFollow() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("Select * from follow where following = ? and follower = ?");
		statement.setString(1,user.getUsername());
		statement.setString(2,userClient.getUsername());
		ResultSet resultSet = statement.executeQuery();
		return resultSet.next();
	}

	public void unfollow() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("DELETE FROM follow WHERE following = ? and follower = ?");
		statement.setString(2, userClient.getUsername());
		statement.setString(1, user.getUsername());
		statement.executeUpdate();
	}

	public void follow() throws SQLException {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
			PreparedStatement statement = connection.prepareStatement("INSERT INTO follow VALUES (?,?)");
			statement.setString(1, user.getUsername());
			statement.setString(2, userClient.getUsername());
			statement.executeUpdate();
	}

	public int numberOfFollower() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select * from follow where follower = \"" + user.getUsername() + "\"");
		ResultSet resultSet = statement.executeQuery();
		int count = 0;
		while (resultSet.next()) {
			count++;
		}
		System.out.println(count);
		return count;
	}

	public int numberOfFollowing() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select * from follow where following = \"" + user.getUsername() + "\"");
		ResultSet resultSet = statement.executeQuery();
		int count = 0;
		while (resultSet.next()) {
			count++;
		}
		return count;
	}

	public ArrayList<User> followers() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select * from follow where follower = \"" + user.getUsername() + "\"");
		ResultSet resultSet = statement.executeQuery();
		ArrayList<User> userArrayList = new ArrayList<>();
		while (resultSet.next()) {
			User newUser = getUser(resultSet.getString(1));
			userArrayList.add(newUser);
		}
		return userArrayList;
	}

	public ArrayList<User> followings() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select * from follow where following = \"" + user.getUsername() + "\"");
		ResultSet resultSet = statement.executeQuery();
		ArrayList<User> userArrayList = new ArrayList<>();
		while (resultSet.next()) {
			User newUser = getUser(resultSet.getString(2));
			userArrayList.add(newUser);
		}
		return userArrayList;
	}

	public ArrayList<Tweet> listOfTweet() throws SQLException, IOException {
		ArrayList<Tweet> tweets = new ArrayList<>();

		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select * from tweet where username = \"" + user.getUsername() + "\"");
		ResultSet resultSet = statement.executeQuery();
		Tweet tweet;
		Date date;
		while (resultSet.next()) {
			if (resultSet.getBlob(4) == null){
				tweet = new Tweet(getUser(resultSet.getString(2)),resultSet.getString(3),resultSet.getInt(5),resultSet.getInt(6),resultSet.getInt(7),new Date(resultSet.getTimestamp(8).getTime()),null,resultSet.getBoolean(9),Integer.toString(resultSet.getInt(1)),resultSet.getString(11));
				tweets.add(tweet);
			}
			else {
				tweet = new Tweet(getUser(resultSet.getString(2)), resultSet.getString(3), resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7), new Date(resultSet.getTimestamp(8).getTime()), resultSet.getBlob(4).toString(), resultSet.getBoolean(9), Integer.toString(resultSet.getInt(1)), resultSet.getString(11));
				tweets.add(tweet);
			}
		}
		PreparedStatement statement2 = connection.prepareStatement("select tweet__id from retweet where username = \"" + user.getUsername() + "\"");
		ResultSet resultSet2 = statement2.executeQuery();
		PreparedStatement statement3 = connection.prepareStatement("select * from tweet where tweetid = ?");;
		while (resultSet2.next()) {
			statement3.setInt(1,resultSet2.getInt(1));
			ResultSet resultSet3 = statement3.executeQuery();
			resultSet3.next();
			if (resultSet3.getBlob(4) == null){
				Tweet tweet2 = new Tweet(getUser(resultSet3.getString(2)),resultSet3.getString(3),resultSet3.getInt(5),resultSet3.getInt(6),resultSet3.getInt(7),new Date(resultSet3.getTimestamp(8).getTime()),null,resultSet3.getBoolean(9),Integer.toString(resultSet3.getInt(1)),resultSet3.getString(11));
				tweets.add(tweet2);
			}else {
				Tweet tweet2 = new Tweet(getUser(resultSet3.getString(2)), resultSet3.getString(3), resultSet3.getInt(5), resultSet3.getInt(6), resultSet3.getInt(7), new Date(resultSet3.getTimestamp(8).getTime()), resultSet3.getBlob(4).toString(), resultSet3.getBoolean(9), Integer.toString(resultSet3.getInt(1)), resultSet3.getString(11));
				tweets.add(tweet2);
			}
		}

		return tweets;
	}

	public User getUser(String user) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select * from user where username = \"" + user + "\"");
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		User user1 = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
		return user1;
	}

	public void chooseAvatar(String imageAsString) throws SQLException {
		String receivedImageAsString = imageAsString;
		byte[] receivedImageInByte = Base64.getDecoder().decode(receivedImageAsString);
		Blob imageAsBlob = new SerialBlob(receivedImageInByte);
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("update user set avatar = ? where username = ?");
		statement.setBlob(1, imageAsBlob);
		statement.setString(2, user.getUsername());
		statement.executeUpdate();
		System.out.println("photo added");
	}

	public void chooseHeader(String imageAsString) throws SQLException {
		String receivedImageAsString = imageAsString;
		byte[] receivedImageInByte = Base64.getDecoder().decode(receivedImageAsString);
		Blob imageAsBlob = new SerialBlob(receivedImageInByte);
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("update user set header = ? where username = ?");
		statement.setBlob(1, imageAsBlob);
		statement.setString(2, user.getUsername());
		statement.executeUpdate();

	}
	public String getHeader() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select header from user where username = ?");
		statement.setString(1,user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		Blob blob = resultSet.getBlob(1);
		byte[] imageData = blob.getBytes(1, (int) blob.length());
		imageInString = Base64.getEncoder().encodeToString(imageData);
		return imageInString;
	}
	public String getBio() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select bio from user where username = ?");
		statement.setString(1,user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		return resultSet.getString(1);
	}
	public String getAvatar() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select avatar from user where username = ?");
		statement.setString(1,user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		Blob blob = resultSet.getBlob(1);
		byte[] imageData = blob.getBytes(1, (int) blob.length());
		imageInString = Base64.getEncoder().encodeToString(imageData);
		return imageInString;
	}
	public String getJoinedDate() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select register_date from user where username = ?");
		statement.setString(1,user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		System.out.println(resultSet.getString(1));
		return resultSet.getString(1);
	}
	public String getBirthdate() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select birthday from user where username = ?");
		statement.setString(1,user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		return resultSet.getString(1);
	}
	public String getLocation() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select country from user where username = ?");
		statement.setString(1,user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		return resultSet.getString(1);
	}
}