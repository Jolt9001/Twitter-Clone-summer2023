
package twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Owner
 */
public class UserModel {
    public static boolean login (User user) {
        try {
            Connection connection = DBConnection.getConnection();
            
            String query = "select id, username, password from user where username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, user.getUsername());
            
            ResultSet results = statement.executeQuery();
            String password = "";
            if (results.next()) {
                password = results.getString("password");
            }
            
            results.close();
            statement.close();
            connection.close();
            
            return !(password.isEmpty() || !user.getPassword().equals(password));
        } 
        catch (Exception ex){
            System.out.println(ex);
            return false;
        }
    }
    
    public static User getUser(String username) {
        User user = new User(0, username, "", "");
        try {
            String query = "select id, username, password, filename from user where username = ?";
            
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, username);
            
            ResultSet results = statement.executeQuery();
            System.out.println("");
            
            if (results.next()) {
                int id = results.getInt("id");
                String password = results.getString("password");
                String filename = results.getString("filename");

                user.setId(id);
                user.setPassword(password);
                user.setFilename(filename);
            }
            
            results.close();
            statement.close();
            connection.close();
            return user;
        } 
        catch (Exception ex){
            System.out.println(ex);
            return user;
        }
    }
    
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            
            Statement statement = connection.createStatement();
            
            ResultSet results = statement.executeQuery("select id, username, password, filename from user");
            
            while (results.next()) {
                int id = results.getInt("id");
                String username = results.getString("username");
                String password = results.getString("password");
                String filename = results.getString("filename");
                
                User user = new User(id, username, password, filename);
                
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
    
    public static ArrayList<User> getUsersSansCurrent(String currentUsername) {
        ArrayList<User> userList = new ArrayList<>();
        boolean isFollowing = false;
        try {
            Connection connection = DBConnection.getConnection();
            String sQuery = "select id, username, filename from user where username != ?";
            
            PreparedStatement sStatement = connection.prepareStatement(sQuery);
            sStatement.setString(1, currentUsername);
            
            ResultSet sResult = sStatement.executeQuery();
            
            while (sResult.next()) {
                int id = sResult.getInt("id");
                String username = sResult.getString("username");
                String filename = sResult.getString("filename");

                User user = new User(id, username, "", filename);

                userList.add(user);
            }
            
            sResult.close();
            sStatement.close();
            
            
            
            connection.close();
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return userList;
    }
    
    public static void addUser(User user){
        try {
            Connection connection = DBConnection.getConnection();
            
            String query = "insert into user (username, password) values (?, ?)";
            
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPassword());
                statement.setInt(3, user.getId());
                statement.execute();
                statement.close();
            }
            
            connection.close();
        } 
        catch (Exception ex){
            System.out.println(ex);
        }
    }
    
    public static void updateUser(User user) {
        try {
            Connection connection = DBConnection.getConnection();
            
            String query = "update user set username = ?, password  = ? where id = ?";
            
            PreparedStatement statement = connection.prepareStatement(query);
            
            // indexing starts with 1
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getId());
            
            statement.execute();
            statement.close();
            connection.close();
            
        } 
        catch (Exception ex){
            System.out.println(ex);
        }
    }
    
    public static void deleteUser(User user) {
        try {
            Connection connection = DBConnection.getConnection();
            String query = "delete from user where id = ?";
            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());
            
            statement.execute();
            statement.close();
            connection.close();
        } 
        catch (Exception ex){
            System.out.println(ex);
        }
    }
    
    public static void ensureLoginRedirect(HttpServletRequest request) {
            Login.ensureLoginRedirect(request);
    }
}
