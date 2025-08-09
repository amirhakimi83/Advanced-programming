package org.example;

import com.example.client.Tweet;
import com.example.client.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;

public class timeline {
	private User user;
    private ArrayList<Tweet> arrayList = new ArrayList<>();
	public timeline(User user) {
		this.user = user;
	}
	public ArrayList<Tweet> tweetArrayList() throws SQLException {
		ArrayList<Tweet> tweets = new ArrayList<>();
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select follower from follow where following = ?");
		statement.setString(1,user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()){
			PreparedStatement statement2 = connection.prepareStatement("select * from tweet where username = ?");
			statement2.setString(1,resultSet.getString(1));
			ResultSet resultSet2 = statement2.executeQuery();
			while (resultSet2.next()) {
				if (resultSet2.getBlob(4) != null) {
					byte[] blobImage = resultSet2.getBlob(4).getBytes(1, (int) resultSet2.getBlob(4).length());
					String imageAsString = Base64.getEncoder().encodeToString(blobImage);
					Tweet tweet = new Tweet(getUser(resultSet2.getString(2)), resultSet2.getString(3), resultSet2.getInt(5), resultSet2.getInt(6), resultSet2.getInt(7), resultSet2.getTimestamp(8), imageAsString, resultSet2.getBoolean(9), Integer.toString(resultSet2.getInt(1)), resultSet2.getString(11));
					tweets.add(tweet);
				} else {
					Tweet tweet = new Tweet(getUser(resultSet2.getString(2)), resultSet2.getString(3), resultSet2.getInt(5), resultSet2.getInt(6), resultSet2.getInt(7), resultSet2.getTimestamp(8), null, resultSet2.getBoolean(9), Integer.toString(resultSet2.getInt(1)), resultSet2.getString(11));
					tweets.add(tweet);
				}
			}
		}
		return tweets;
	}
	public ArrayList<Tweet> favTweet() throws SQLException {
		ArrayList<Tweet> tweets = new ArrayList<>();
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select * from tweet where favstar = ?");
		statement.setBoolean(1,true);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()){
			if (resultSet.getBlob(4) != null) {
				byte[] blobImage = resultSet.getBlob(4).getBytes(1, (int) resultSet.getBlob(4).length());
				String imageAsString = Base64.getEncoder().encodeToString(blobImage);
				Tweet tweet = new Tweet(getUser(resultSet.getString(2)), resultSet.getString(3), resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getTimestamp(8), imageAsString, resultSet.getBoolean(9), Integer.toString(resultSet.getInt(1)), resultSet.getString(11));
				tweets.add(tweet);
			}else {
				Tweet tweet = new Tweet(getUser(resultSet.getString(2)), resultSet.getString(3), resultSet.getInt(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getTimestamp(8), null, resultSet.getBoolean(9), Integer.toString(resultSet.getInt(1)), resultSet.getString(11));
				tweets.add(tweet);
			}
		}
		return tweets;
	}
	public ArrayList<Tweet> retweet() throws SQLException {
		ArrayList<Tweet> tweets = new ArrayList<>();
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select follower from follow where following = ?");
		statement.setString(1,user.getUsername());
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()){
			PreparedStatement statement1 = connection.prepareStatement("select tweet__id from retweet where username = ?");
			statement1.setString(1,resultSet.getString(1));
			ResultSet resultSet1 = statement1.executeQuery();
			while (resultSet1.next()) {
				PreparedStatement statement2 = connection.prepareStatement("select * from tweet where tweetid = ?");
				statement2.setInt(1,resultSet1.getInt(1));
				ResultSet resultSet2 = statement2.executeQuery();
				while (resultSet2.next()) {
					if (resultSet2.getBlob(4) != null) {
						byte[] blobImage = resultSet2.getBlob(4).getBytes(1, (int) resultSet2.getBlob(4).length());
						String imageAsString = Base64.getEncoder().encodeToString(blobImage);
						Tweet tweet = new Tweet(getUser(resultSet2.getString(2)), resultSet2.getString(3), resultSet2.getInt(5), resultSet2.getInt(6), resultSet2.getInt(7), resultSet2.getTimestamp(8), imageAsString, resultSet2.getBoolean(9), Integer.toString(resultSet2.getInt(1)), resultSet2.getString(11));
						tweets.add(tweet);
					} else {
						Tweet tweet = new Tweet(getUser(resultSet2.getString(2)), resultSet2.getString(3), resultSet2.getInt(5), resultSet2.getInt(6), resultSet2.getInt(7), resultSet2.getTimestamp(8), null, resultSet2.getBoolean(9), Integer.toString(resultSet2.getInt(1)), resultSet2.getString(11));
						tweets.add(tweet);
					}
				}
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
	public String toString(){
		try {
			return tweetArrayList().toString() + "\n" + favTweet().toString();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
