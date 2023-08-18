
package twitter;

import java.io.IOException;
import java.io.OutputStream;
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
        String tweetId = request.getParameter("id");
        
        try {
            Connection connection = DBConnection.getConnection();
            String preparedSQL = "select user_id, attachment, filename from tweet where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(preparedSQL);
            ResultSet result = preparedStatement.executeQuery();
            
            preparedStatement.setString(1, tweetId);
            int user_id = 0;
            Blob attachment = null;
            String filename = "";
            
            String attachmentCheck = result.getString("attachment");
            if (attachmentCheck != null) {
                while (result.next()) {
                    user_id = result.getInt("user_id");
                    attachment = result.getBlob("attachment");
                    filename = result.getString("filename");
                }
                
                byte[] imageBytes = attachment.getBytes(1, (int)attachment.length());
                
                preparedStatement.close();
                
                String contentType = this.getServletContext().getMimeType(filename);
            
                response.setHeader("Content-Type", contentType);
                
                OutputStream os = response.getOutputStream();
                os.write(imageBytes);
                os.flush();
                os.close();
            } else {
                while (result.next()) {
                    user_id = result.getInt("user_id");
                }
                
                preparedStatement.close();
            }
            
            
            preparedSQL = "select username from user where id =  ?";
            preparedStatement = connection.prepareStatement(preparedSQL);
            
            String userId = Integer.toString(user_id);
            preparedStatement.setString(1, userId);
            result = preparedStatement.executeQuery();
            String username = "";
            while (result.next()) {
                username = result.getString("username");
            }
            
            request.setAttribute("username", username);
            request.getRequestDispatcher("/getImage").forward(request, response);
            
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
