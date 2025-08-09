package com.example.client;

import com.example.client.User;

import javax.sql.rowset.serial.SerialBlob;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Tweet implements Serializable {
	private User user;
	private String message;
	private int likes;
	private int retweets;
	private int commentsOrReplies; // comments?
	private Date date;
	private String imageInByte;
	private boolean favstar;
	private String id;
	private Tweet quoteTweet;
	private String hashtag;

	public Tweet(User user, String message, String imageInByte, Tweet quoteTweet) throws SQLException, IOException {
		this.user = user;
		this.message = message;
		this.likes = 0;
		this.retweets = 0;
		this.commentsOrReplies = 0;
		this.date = Calendar.getInstance().getTime();
		this.imageInByte = imageInByte;
		this.favstar = false;
		this.hashtag = getHashtag();
		System.out.println(getHashtag());
		SimpleDateFormat dateToString = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String stringedDate = dateToString.format(date);
		this.id = user.getUsername().concat(stringedDate);
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("INSERT INTO tweet VALUES (default,?,?,?,default,default,default,?,default,?,?)");
		statement.setString(1, user.getUsername());
		statement.setString(2, message);
		if (imageInByte != null) {
			String receivedImageAsString = imageInByte;
			byte[] imageInBytes = Base64.getDecoder().decode(receivedImageAsString);
			Blob blobImage = new SerialBlob(imageInBytes);
			statement.setBlob(3, blobImage);
		}else {
			Blob blob = null;
			statement.setBlob(3, blob);
		}
		Timestamp timestamp = new Timestamp(date.getTime());
		statement.setTimestamp(4,timestamp);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(quoteTweet);
		byte[] tweetInByte = bos.toByteArray();
		Blob blobTweet = new SerialBlob(tweetInByte);
		statement.setBlob(5,blobTweet);
		statement.setString(6,getHashtag());
		statement.executeUpdate();
		System.out.println("tweet added");
	}

	public Tweet(User user, String message, int likes, int retweets, int commentsOrReplies, Date date, String imageInByte, boolean favstar, String id, String hashtag) {
		this.user = user;
		this.message = message;
		this.likes = likes;
		this.retweets = retweets;
		this.commentsOrReplies = commentsOrReplies;
		this.date = date;
		this.imageInByte = imageInByte;
		this.favstar = favstar;
		this.id = id;
		this.hashtag = hashtag;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getRetweets() {
		return retweets;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setRetweets(int retweets) {
		this.retweets = retweets;
	}

	public int getCommentsOrReplies() {
		return commentsOrReplies;
	}

	public void setCommentsOrReplies(int commentsOrReplies) {
		this.commentsOrReplies = commentsOrReplies;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getImageInByte() {
		return imageInByte;
	}

	public void setImageInByte(String imageInByte) {
		this.imageInByte = imageInByte;
	}

	public boolean isFavstar() {
		return favstar;
	}

	public void setFavstar(boolean favstar) {
		this.favstar = favstar;
	}

	public String getHashtag() {
		Pattern pattern = Pattern.compile("#[a-zA-Z0-9_]+");
		Matcher matcher = pattern.matcher(message);
		String hashtag = null;
		while (matcher.find()) {
			hashtag = matcher.group(0);
		}
		return hashtag;
	}

	// methods
	public void Like(String usernameClient) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		String query  = "UPDATE tweet SET likes = likes + 1 WHERE tweetid = " + getId();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.executeUpdate();
		PreparedStatement statement3 = connection.prepareStatement("UPDATE tweet set favstar = ? where likes > 1000");
		statement3.setBoolean(1,true);
		PreparedStatement statement2 = connection.prepareStatement("insert into likes_list values (?,?)");
		statement2.setString(1,getId());
		statement2.setString(2,usernameClient);
		statement2.executeUpdate();
	}
	public void unLike(String usernameClient) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		String query  = "UPDATE tweet SET likes = likes - 1 WHERE tweetid = " + getId();
		PreparedStatement statement = connection.prepareStatement("update tweet set likes = likes - 1 where tweetid = ?");
		statement.setString(1,getId());
		statement.executeUpdate();
		PreparedStatement statement3 = connection.prepareStatement("UPDATE tweet set favstar = ? where likes < 1000");
		statement3.setBoolean(1,false);
		statement3.executeUpdate();
		PreparedStatement statement2 = connection.prepareStatement("delete from likes_list where tweet_id = ? and username = ?");
		statement2.setString(1,getId());
		statement2.setString(2,usernameClient);
		statement2.executeUpdate();
	}
	public int numberOfLike() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select likes from tweet where tweetid = ?");
		statement.setString(1,getId());
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		int count = 0;
		while (resultSet.next()){
			count++;
		}
		return count;
	}
	public boolean checkLike(String username) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select * from likes_list where tweet_id = ? and username = ?");
		statement.setString(1,getId());
		statement.setString(2,username);
		ResultSet resultSet = statement.executeQuery();
		return resultSet.next();
	}
	public void Retweet(String usernameClient) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		String query  = "UPDATE tweet SET retweets = retweets + 1 WHERE tweetid = " + getId();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.executeUpdate();
		PreparedStatement statement2 = connection.prepareStatement("insert into retweet values (?,?)");
		statement2.setString(1,usernameClient);
		statement2.setString(2,getId());
		statement2.executeUpdate();
	}
	public void unRetweet(String usernameClient) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		String query  = "UPDATE tweet SET retweets = retweets - 1 WHERE tweetid = " + getId();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.executeUpdate();
		PreparedStatement statement2 = connection.prepareStatement("delete from retweet where username = ? and tweet__id = ?");
		statement2.setString(1,usernameClient);
		statement2.setString(2,getId());
		statement2.executeUpdate();
	}

	public int numberOfRetweet() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("select retweets from tweet where tweetid = ?");
		statement.setString(1,getId());
		ResultSet resultSet = statement.executeQuery();
		int count = 0;
		while (resultSet.next()){
			count++;
		}
		return count;
	}
	public boolean checkRetweet(String username) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		String query = "select * from likes_list where tweet_id = " + getId() + " and username = " + username;
		PreparedStatement statement = connection.prepareStatement("select * from  retweet where username = ? and tweet__id = ?");
		statement.setString(1,username);
		statement.setString(2,getId());
		ResultSet resultSet = statement.executeQuery();
		return resultSet.next();
	}
	public Tweet Quote(Tweet quote,User user,String message,String imageInByte) throws SQLException, IOException {
		Tweet newTweet = new Tweet(user,message,imageInByte,quoteTweet);
		return newTweet;
	}
	public void Reply(Tweet reply,String Comment) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "Amir1383@");
		PreparedStatement statement = connection.prepareStatement("update tweet set replies = ? where tweetid = ?");
		PreparedStatement statement2 = connection.prepareStatement("select replies from tweet where tweetid = ?");
		ResultSet resultSet = statement2.executeQuery();
		resultSet.next();
		User newUser = reply.getUser();
//		Gson gson = new Gson();
//		String jsonData = gson.toJson(newUser);
		statement.setString(1,resultSet.getString(1)+"#"+ Comment);
		statement.executeUpdate();
	}
	public String toString(Tweet tweet){
		return tweet.user.getUsername() + "\t" + tweet.getMessage() + tweet.getLikes()+"|"+tweet.getRetweets()+"|"+tweet.getCommentsOrReplies()+ "\t" +tweet.getDate();
	}

}