
package twitter;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Owner
 */
public class TweetModel {
    public static List<Tweet> getAllTweets() {
        List<Tweet> tweets = new ArrayList<>();
        try {
            String query = "select * from tweet";
            
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet results = statement.executeQuery(query);
            
            while (results.next()) {
                int tweetId = results.getInt("id");
                String text = results.getString("text");
                Blob attachment = results.getBlob("attachment"); // Assuming this is the attachment URL
                int userId = results.getInt("user_id");
                int likes = results.getInt("likes");
                Timestamp timestamp = results.getTimestamp("timestamp");

                // Create a new Tweet object and set its attributes
                Tweet tweet = new Tweet(tweetId, text, timestamp, userId, attachment, likes);
                tweets.add(tweet);
            }
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
            ResultSet results = statement.executeQuery(query);
            
            while (results.next()) {
                int tweetId = results.getInt("id");
                String text = results.getString("text");
                Blob attachment = results.getBlob("attachment"); // Assuming this is the attachment URL
                int userId = results.getInt("user_id");
                int likes = results.getInt("likes");
                Timestamp timestamp = results.getTimestamp("timestamp");

                // Create a new Tweet object and set its attributes
                tweet = new Tweet(tweetId, text, timestamp, userId, attachment, likes);
            }
        } catch (Exception ex){
            System.out.println(ex);
        }
        return tweet;
    }
}