/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package twitter;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static twitter.Twitter.getSHA;
import static twitter.Twitter.toHexString;

/**
 *
 * @author Owner
 */
public class UserManager extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        response.setContentType("text/html;charset=UTF-8");
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
            
            if (username == "" || password == "") {
                String error = "username or password missing.";
                request.setAttribute("error", error);
                String url = "/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }
            
            try {
                String hashedPassword = toHexString(getSHA(password));
                User user = new User(0, username, hashedPassword);
                UserModel.addUser(user);
            
                HttpSession session = request.getSession();
                session.setAttribute("username", username); 
            
                response.sendRedirect("UserManager");
            } catch (Exception ex) {
                exceptionPage(ex, request, response);
            }
        } else if (action.equalsIgnoreCase("updateUser")) {
            String id = request.getParameter("id");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            if (id == "" || username == "" || password == "") {
                String error = "username or password missing.";
                request.setAttribute("error", error);
                String url = "/error.jsp";
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }
            
            try {
                String hashedPassword = toHexString(getSHA(password));
                User user = new User(Integer.parseInt(id), username, hashedPassword);
                UserModel.updateUser(user);

                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("password", hashedPassword);
            
                response.sendRedirect("UserManager");
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
            
            response.sendRedirect("UserManager");
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
        } else {
            response.sendRedirect("UserManager");
        }
    }
    
    public void exceptionPage(Exception ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String error = ex.toString();
        request.setAttribute("error", error);
        String url = "/error.jsp";
        getServletContext().getRequestDispatcher(url).forward(request, response);
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
