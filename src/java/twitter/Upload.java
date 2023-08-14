/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package twitter;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Owner
 */
@MultipartConfig(maxFileSize = 1000000)
public class Upload extends HttpServlet {

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
        InputStream inputStream = null;
        String fileName = "";
        
        Part filePart = request.getPart("file");
        if (filePart != null) {
            fileName = extractFileName(filePart);
            inputStream = filePart.getInputStream();
        }
        
        try {
            HttpSession session = request.getSession();
            String username = session.getAttribute("username").toString();
            
            Connection connection = DBConnection.getConnection();
            String preparedSQL = "update user set image = ? filename = ?"
                    + " where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(preparedSQL.toString());
            
            // index starts at 17
            preparedStatement.setBlob(1, inputStream);
            preparedStatement.setString(2, fileName);
            preparedStatement.setString(3, username);
            
            preparedStatement.executeUpdate();
            
            preparedStatement.close();
            
            String url = "/Profile";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", ex.toString());
            String url = "/error.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        }
    }
    
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
