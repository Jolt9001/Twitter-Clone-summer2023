
package twitter;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Owner
 */
public class TweetModel {
    public static ArrayList<Tweet> getAllTweets() {
        ArrayList<Tweet> tweets = new ArrayList<>();
        try {
            String query = "select * from tweet";
            
            Connection connection = DBConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query);
            
            while (results.next()) {
                int tweetId = results.getInt("id");
                String txt = results.getString("text");
                Blob att = results.getBlob("attachment"); // Assuming this is the attachment URL
                String fName = results.getString("filename");
                int userId = results.getInt("user_id");
                int likes = results.getInt("likes");
                Timestamp ts = results.getTimestamp("timestamp");

                // Create a new Tweet object and set its attributes
                Tweet tweet = new Tweet(tweetId, txt, ts, userId, att, fName, likes);
                tweets.add(tweet);
            }
            connection.close();
            statement.close();
        } catch (Exception ex){
            System.out.println(ex);
        }
        return tweets;
    }
    public static Tweet getTweet(int id) {
        Tweet tweet = null;
        try {
            String query = "select * from tweet where id = ?";
            
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            
            if (results.next()) {
                int tweetId = results.getInt("id");
                String text = results.getString("text");
                Blob attachment = results.getBlob("attachment"); // Assuming this is the attachment URL
                String filename = results.getString("filename");
                int userId = results.getInt("user_id");
                int likes = results.getInt("likes");
                Timestamp timestamp = results.getTimestamp("timestamp");

                // Create a new Tweet object and set its attributes
                tweet = new Tweet(tweetId, text, timestamp, userId, attachment, filename, likes);
            }
            connection.close();
            statement.close();
            results.close();
        } catch (Exception ex){
            System.out.println(ex);
        }
        return tweet;
    }
    public static String getUsername(int user_id) {
        String username = "";
        try {
            String query = "select username from user where id = ?";
            
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setInt(1, user_id);
            ResultSet results = statement.executeQuery();
            
            if (results.next()) {
                username = results.getString("username");
            }
            
            connection.close();
            statement.close();
            results.close();
        } catch (Exception ex){
            System.out.println(ex);
        }
        return username;
    }
    
    public static String getPFP_URL(String username) {
        String profileImageURL = "";
        try {
            String query = "select filename from user where username = ?";

            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, username);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                profileImageURL = results.getString("filename");
            }

            connection.close();
            statement.close();
            results.close();
        } catch (Exception ex){
            System.out.println(ex);
        }
        return profileImageURL;
    }
}