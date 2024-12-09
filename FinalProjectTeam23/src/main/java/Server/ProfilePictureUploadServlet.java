package Server;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/UploadProfilePicture")
@MultipartConfig
public class ProfilePictureUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("Received a profile picture upload request.");

        Part filePart = request.getPart("profilePicture");
        String userId = request.getParameter("userId");

        if (userId == null || userId.isEmpty() || userId.equals("undefined")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid user ID provided.\"}");
            System.out.println("Error: Invalid user ID provided.");
            return;
        }

        int parsedUserId;
        try {
            parsedUserId = Integer.parseInt(userId);
            System.out.println("Parsed User ID: " + parsedUserId);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"User ID must be an integer.\"}");
            //System.out.println("Error: User ID must be an integer.");
            return;
        }

        if (filePart == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"No file provided.\"}");
            //System.out.println("Error: No file provided.");
            return;
        }

        try (InputStream fileContent = filePart.getInputStream()) {
            System.out.println("Processing file upload for user ID: " + parsedUserId);

            byte[] image = fileContent.readAllBytes();
            String savedImageUrl = saveProfilePictureToDatabase(parsedUserId, image);

            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true, \"profile_picture_url\": \"" + savedImageUrl + "\"}");
            System.out.println("Profile picture uploaded successfully. URL: " + savedImageUrl);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error: " + e.getMessage() + "\"}");
            System.out.println("Error: Database error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error processing file: " + e.getMessage() + "\"}");
            System.out.println("Error: I/O error while processing file: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Unexpected error: " + e.getMessage() + "\"}");
            System.out.println("Error: Unexpected error: " + e.getMessage());
        }
    }

    private String saveProfilePictureToDatabase(int userId, byte[] image) throws SQLException {
        String imageUrl = null;

        if (image == null || image.length == 0) {
            throw new SQLException("Image data is empty or null.");
        }

        System.out.println("Saving profile picture to database for user ID: " + userId);

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE users SET profile_picture = ? WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setBytes(1, image);  
                stmt.setInt(2, userId);  

                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    imageUrl = "http://localhost:8080/settings/assets/example-trainer.png";
                    System.out.println("Profile picture updated successfully in database.");
                } else {
                    throw new SQLException("User not found or profile picture update failed.");
                }
            }
        }

        return imageUrl;
    }
}
