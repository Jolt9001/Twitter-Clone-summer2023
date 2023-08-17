/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Owner
 */
public class FollowModel {
    public static void addFollow(Follow f) {
        try {
            Connection c = DBConnection.getConnection();
            
            String query = "insert into following (followedbyuid, followinguid) values (?, ?)";
            
            PreparedStatement s = c.prepareStatement(query);
            
            // indexing starts with 1
            s.setInt(1, f.getFollowedbyuid());
            s.setInt(2, f.getFollowinguid());
            
            s.execute();
            
            s.close();
            c.close();
        } catch (Exception ex){
            System.out.println(ex);
        }
    }
    
    public static void Unfollow(Follow f) {
        try {
            Connection c = DBConnection.getConnection();
            
            String query = "delete from follow where id = ?";
            
            PreparedStatement s = c.prepareStatement(query);
            s.setInt(1, f.getId());
            
            s.execute();
            s.close();
            c.close();
        } catch (Exception ex){
            System.out.println(ex);
        }
    }
    
    public static void ensureLoginRedirect(HttpServletRequest request) {
        Login.ensureLoginRedirect(request);
    }
}
