
package twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Owner
 */
public class UserModel {
    
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            
            Statement statement = connection.createStatement();
            
            ResultSet results = statement.executeQuery("select * from user");
            
            while (results.next()) {
                int id = results.getInt("id");
                String username = results.getString("string");
                String password = results.getString("password");
                
                User user = new User(id, username, password);
                
                users.add(user);
            }
            
            results.close();
            statement.close();
            connection.close();
            
        } 
        catch (Exception ex){
            System.out.println(ex);
        }
        return users;
    }
    
    public static void addUser(User user){
        try {
            Connection connection = DBConnection.getConnection();
            
            String query = "insert into user ( username, password ) "
                    + " values ( ?, ? )";
            
            PreparedStatement statement = connection.prepareStatement(query);
            
            // indexing starts with 1
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            
            statement.execute();
            
            statement.close();
            connection.close();
            
        } 
        catch (Exception ex){
            System.out.println(ex);
        }
    }
}
