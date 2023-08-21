
package twitter;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Profile", urlPatterns = {"/Profile"})
public class Twitter extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("text/html;charset=UTF-8");
        
        if (!Login.ensureLoginRedirect(request)) {
            request.setAttribute("message", "Please log in to continue.");
            response.sendRedirect("Login");
            return;
        }
        if (action == null) {
            action = "listUsers";
            request.setAttribute("action", action);
        }
        
        // Users section
        if (action.equalsIgnoreCase("listUsers")) {
            HttpSession session = request.getSession();
            String username = (String)session.getAttribute("username");
            
            ArrayList<User> users = UserModel.getUsersSansCurrent(username);
            request.setAttribute("users", users);
            action = "listTweets";
            request.setAttribute("action", action);
        } else if (action.equalsIgnoreCase("followUser")) {
            HttpSession session = request.getSession();
            int u1ID = 0;
            String u1name = (String)session.getAttribute("username");
            String u2ID = request.getParameter("followeduid");
            User user1 = UserModel.getUser(u1name);
            u1ID = user1.getId();
            
            if (u1ID == 0 || u2ID == null){
                String error = "one or both id(s) are missing";
                request.setAttribute("error", error);
                String url = "/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }

            try {
                Follow follow = new Follow(0, u1ID, Integer.parseInt(u2ID));
                FollowModel.addFollow(follow);
            } catch (Exception ex) {
                exceptionPage(ex, request, response);
            } 
        } else if (action.equalsIgnoreCase("unfollowUser")) {
            HttpSession session = request.getSession();
            String sessionUser = (String)session.getAttribute("username");
            String u2ID = request.getParameter("followeduid");
            
            User user = UserModel.getUser(sessionUser);
            int u1ID = user.getId();
            
            request.setAttribute("u1ID", u1ID);
            
            if (u1ID == 0 || u2ID == null){
                String error = "one or both id(s) are missing";
                request.setAttribute("error", error);
                String url = "/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }
            
            try {
                int followID = FollowModel.getFollow(u1ID, Integer.parseInt(u2ID));
                
                Follow f = new Follow(followID, u1ID, Integer.parseInt(u2ID));
                
                FollowModel.unfollow(f);
            } catch (Exception ex) {
                exceptionPage(ex, request, response);
            } 
        } 
            
        if (action == "listUsers"){
            action = "listTweets";
        }
        
        if (action.equalsIgnoreCase("listTweets")) {
            ArrayList<Tweet> tweets = TweetModel.getAllTweets();
            request.setAttribute("tweets", tweets);
            action = "listUsers";
            request.setAttribute("action", action);
        } else if (action.equalsIgnoreCase("createTweet")) {
            String text = request.getParameter("text");
            String filename = request.getParameter("filename");
            
            if (text == "") {
                String error = "Tweet content missing.";
                request.setAttribute("error", error);
                String url = "/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }
            
            try {
                HttpSession session = request.getSession();
                String username = (String)session.getAttribute("username");
                User user = UserModel.getUser(username);
                
                boolean attchTest = request.getParameter("attached") != null && request.getParameter("attached").equals("true");
                boolean flag = (Boolean)session.getAttribute("success");
                if (attchTest && !flag) {
                    request.getRequestDispatcher("/UploadAttch").forward(request, response);
                }
                if (attchTest && flag) {
                    Blob attachment = (Blob)session.getAttribute("blob");
                    filename = (String)session.getAttribute("filename");
                    Tweet tweet = new Tweet(0, text, null, user.getId(), attachment, filename, 0);
                    TweetModel.createTweet(tweet, true);
                } else {
                    Tweet tweet = new Tweet(0, text, null, user.getId(), 0);
                    TweetModel.createTweet(tweet, false);
                }
                action = "listUsers";
                request.setAttribute("action", action);
            } catch (Exception ex) {
                exceptionPage(ex, request, response);
            }
        } else if (action.equalsIgnoreCase("likeTweet")) {
            String idSt = (String)request.getParameter("tweet_id");
            String like = (String)request.getParameter("likes");
            if (idSt == "" || like == "" || idSt == null || like == null) {
                String error = "null pointer or empty string exception.";
                request.setAttribute("error", error);
                String url = "/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            } else {
                int id = Integer.parseInt(idSt);
                Tweet tweet = TweetModel.getTweet(id);

                int likes = Integer.parseInt(like);
                if (tweet != null) {
                    likes = TweetModel.likeTweet(tweet);
                    request.setAttribute("likes", likes);
                }
                request.setAttribute("action", null);
                String url = "/home.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }
        }
        String url = "/home.jsp";
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    public void exceptionPage(Exception ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String error = ex.toString();
        request.setAttribute("error", error);
        String url = "/error.jsp";
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    
    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
