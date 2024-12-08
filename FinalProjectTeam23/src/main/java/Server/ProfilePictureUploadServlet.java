package Server;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/UploadProfilePicture")
@MultipartConfig(maxFileSize = 16177215) // Upload limit ~16MB
public class ProfilePictureUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdParam = request.getParameter("userId");

        if (userIdParam == null || userIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"UserId is required\"}");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid UserId format\"}");
            return;
        }

        Part filePart = request.getPart("profilePicture"); // Retrieves <input type="file" name="profilePicture">
        if (filePart == null || filePart.getSize() == 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"No file uploaded\"}");
            return;
        }

        // Handle file upload
        try (InputStream inputStream = filePart.getInputStream();
             Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("UPDATE Users SET profile_picture = ? WHERE user_id = ?")) {

            pstmt.setBlob(1, inputStream);
            pstmt.setInt(2, userId);

            int row = pstmt.executeUpdate();
            if (row > 0) {
                response.getWriter().write("{\"message\": \"Profile picture uploaded successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"User not found\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error occurred\"}");
        }
    }

    private String saveProfilePictureToDatabase(int userId, byte[] image) throws SQLException {
        String imageUrl = null;

        if (image == null || image.length == 0) {
            throw new SQLException("Image data is empty or null.");
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Users SET profile_picture = ? WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setBytes(1, image);
                stmt.setInt(2, userId);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    imageUrl = "http://localhost/assets/profile_pictures/" + userId + ".png";
                } else {
                    throw new SQLException("User not found or profile picture update failed.");
                }
            }
        }
        return imageUrl;
    }
}
