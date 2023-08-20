
package twitter;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Twitter extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        boolean loginPersist = false;

        if (!Login.ensureLoginRedirect(request, loginPersist)) {
            request.setAttribute("message", "Please log in to continue.");
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
        } else if (action.equalsIgnoreCase("followUser")) {
            String u1ID = request.getParameter("followedbyuid");
            String u2ID = request.getParameter("followinguid");
            if (u1ID == null || u2ID == null){
                String error = "one or both id(s) are missing";
                request.setAttribute("error", error);
                String url = "/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }

            try {
                Follow follow = new Follow(0, Integer.parseInt(u1ID), Integer.parseInt(u2ID));
            } catch (Exception ex) {
                exceptionPage(ex, request, response);
            } 
            
        } else if (action.equalsIgnoreCase("unfollowUser")) {
            
        }
        
        // Tweets section
        
        // set action to "listTweets"
        action = "listTweets";
        request.setAttribute("action", action);
        if (action.equalsIgnoreCase("listTweets")) {
            ArrayList<Tweet> tweets = TweetModel.getAllTweets();
            request.setAttribute("tweets", tweets);
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
            } catch (Exception ex) {
                exceptionPage(ex, request, response);
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
