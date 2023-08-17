
package twitter;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Twitter extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (!Login.ensureLoginRedirect(request)) {
            request.setAttribute("message", "Please log in to continue.");
            response.sendRedirect("Login");
            return;
        }
        
        if (action == null) {
            action = "listUsers";
        }
        
        if (action.equalsIgnoreCase("listUsers")) {
            ArrayList<User> users = UserModel.getUsers();
            request.setAttribute("users", users);
        
            String url = "/users.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        } else if (action.equalsIgnoreCase("createUser")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            if (username == null || password == null) {
                String error = "username or password missing.";
                request.setAttribute("error", error);
                String url = "/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }
            
            try {
            String hashedPassword = toHexString(getSHA(password));
            User user = new User(0, username, hashedPassword);
            UserModel.addUser(user);
            
            response.sendRedirect("Twitter");
            } catch (Exception ex) {
                exceptionPage(ex, request, response);
            }
        } else if (action.equalsIgnoreCase("updateUser")) {
            String id = request.getParameter("id");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            if (id == null || username == null || password == null) {
                String error = "username or password missing.";
                request.setAttribute("error", error);
                String url = "/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }
            
            try {
            String hashedPassword = toHexString(getSHA(password));
            User user = new User(Integer.parseInt(id), username, hashedPassword);
            UserModel.updateUser(user);
            
            response.sendRedirect("Twitter");
            } catch (Exception ex) {
                exceptionPage(ex, request, response);
            } 
        } else if (action.equalsIgnoreCase("deleteUser")) {
            String id = request.getParameter("id");
            if (id == null){
                String error = "id is missing";
                request.setAttribute("error", error);
                String url = "/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }
            
            try {
            User user = new User(Integer.parseInt(id), "", "");
            UserModel.deleteUser(user);
            
            response.sendRedirect("Twitter");
            } catch (Exception ex) {
                exceptionPage(ex, request, response);
            } 
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
        } else if (action.equalsIgnoreCase("login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            if (username == null || password == null){
                if (username == null) {
                    String e = "Username is invalid.";
                    request.setAttribute("error", e);
                    String url = "/error.jsp";
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                } else {
                    String e = "Password is invalid.";
                    request.setAttribute("error", e);
                    String url = "/error.jsp";
                    getServletContext().getRequestDispatcher(url).forward(request, response);
                }
            }
            
            try {
                String hashedPassword = toHexString(getSHA(password));
                User user = new User(0, username, hashedPassword);
                
                if (UserModel.login(user)) {
                    response.sendRedirect("Twitter");
                }
            } catch (Exception ex) {
                exceptionPage(ex, request, response);
            } 
        } else {
            response.sendRedirect("Twitter");
        }
    }

    private void exceptionPage(Exception ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
