
package twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Owner
 */
public class TweetModel {
    public static Tweet getTweet(int id) {
        Tweet tweet = new Tweet(id, "", null, 0, 0);
        try {
            String query = "select * from tweet where id = ?";
            
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery(query);
            
            while (results.next()) {
                
            }
        } catch (Exception ex){
            System.out.println(ex);
        }
        return tweet;
    }
}