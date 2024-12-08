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
@MultipartConfig(maxFileSize = 16177215) // Upload limit ~16MB
public class ProfilePictureUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdParam = request.getParameter("userId");

        if (userIdParam == null || userIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"userId is required\"}");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid userId format\"}");
            return;
        }

        Part filePart = request.getPart("profilePicture"); // Retrieves <input type="file" name="profilePicture">
        if (filePart == null || filePart.getSize() == 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"No file uploaded\"}");
            return;
        }

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
}
