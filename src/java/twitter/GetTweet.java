
package twitter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Owner
 */
public class GetTweet extends HttpServlet {

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
        String tAction = request.getParameter("tAction");
        
        if ("getAttachment".equalsIgnoreCase(tAction)) {
            getTweetAttachment(request, response);
        } else if ("getUserPFP".equalsIgnoreCase(tAction)) {
            getUserPFP(request, response);
        } else {
            request.setAttribute("error", "Invalid action.");
            String url = "/error.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        }
    }
    
    private void getTweetAttachment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tweetId = request.getParameter("id");
        try {
            Connection connection = DBConnection.getConnection();
            String preparedSQL = "select attachment, filename from tweet where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(preparedSQL);
            
            preparedStatement.setString(1, tweetId);
            ResultSet result = preparedStatement.executeQuery();
            
            Blob attachment = null;
            String filename = "";
            if (result.next()) {
                while (result.next()) {
                    attachment = result.getBlob("attachment");
                    filename = result.getString("filename");
                }
                
                byte[] imageBytes = attachment.getBytes(1, (int)attachment.length());
                
                preparedStatement.close();
                connection.close();
                
                String contentType = this.getServletContext().getMimeType(filename);
            
                response.setHeader("Content-Type", contentType);
                
                OutputStream os = response.getOutputStream();
                os.write(imageBytes);
                os.flush();
                os.close();
            }
        } catch (Exception ex) {
            request.setAttribute("error", ex.toString());
            String url = "/error.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        }
    }
    
    private void getUserPFP(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tweetId = request.getParameter("id");

        try {
            Connection connection = DBConnection.getConnection();
            String preparedSQL = "select user_id from tweet where id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(preparedSQL);
            preparedStatement.setString(1, tweetId);

            ResultSet result = preparedStatement.executeQuery();

            int user_id = result.getInt("user_id");

            preparedStatement.close();
            connection.close();

            // Call the getTweetUsername method to fetch the username
            String username = TweetModel.getUsername(user_id);
            
            // Get the image URL using the username and store it in a variable
            String profileImageURL = TweetModel.getPFP_URL(username);
            request.setAttribute("username", username);
            
            // Set the profileImageURL as an attribute and forward to JSP
            request.setAttribute("profileImageURL", profileImageURL);
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", ex.toString());
            String url = "/error.jsp";
            getServletContext().getRequestDispatcher(url).forward(request, response);
        }
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
