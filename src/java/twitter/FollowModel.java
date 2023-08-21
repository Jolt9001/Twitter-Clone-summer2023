/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Owner
 */
public class FollowModel {
    public static void addFollow(Follow f) {
        try {
            Connection c = DBConnection.getConnection();
            
            /*
            * @var followedbyuid: the user who's following (uid1)
            * @var followeduid: the user who's being followed (uid2)
            */
            String query = "insert into following (followedbyuid, followeduid) values (?, ?)";
            
            PreparedStatement s = c.prepareStatement(query);
            
            // indexing starts with 1
            s.setInt(1, f.getFollowedbyuid());
            s.setInt(2, f.getFolloweduid());
            
            s.execute();
            
            s.close();
            c.close();
        } catch (Exception ex){
            System.out.println(ex);
        }
    }
    
    public static void unfollow(Follow f) {
        try {
            Connection c = DBConnection.getConnection();
            String query = "delete from following where id = ?";
            
            PreparedStatement s = c.prepareStatement(query);
            s.setInt(1, f.getId());
            
            s.execute();
            s.close();
            c.close();
        } catch (Exception ex){
            System.out.println(ex);
        }
    }
    public static int getFollow(int uid1, int uid2) {
        int id = 0;
        try {
            Connection c = DBConnection.getConnection();
            String query = "select id from following where followedbyuid = ? and followeduid = ?";
            
            PreparedStatement s = c.prepareStatement(query);
            s.setInt(1, uid1);
            s.setInt(2, uid2);
            ResultSet r = s.executeQuery();
            
            if (r.next()){
                id = r.getInt("id");
            }
            
            r.close();
            s.close();
            c.close();
        } catch (Exception ex){
            System.out.println(ex);
        }
        return id;
    }
    
    public static void ensureLoginRedirect(HttpServletRequest request, boolean isLoggedIn) {
        Login.ensureLoginRedirect(request, isLoggedIn);
    }
}
