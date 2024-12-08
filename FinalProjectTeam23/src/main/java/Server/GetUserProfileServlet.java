package Server;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetUserProfile")
public class GetUserProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("application/json");

        String userIdParam = request.getParameter("userId");

        //System.out.println("Received request to get user profile for userId: " + userIdParam);

        if (userIdParam == null || userIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.write("{\"error\": \"userId is required\"}");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdParam);
            //System.out.println("Parsed userId: " + userId);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.write("{\"error\": \"Invalid userId format\"}");
            return;
        }

        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getConnection();

            if (connection == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                pw.write("{\"error\": \"Failed to establish database connection\"}");
                return;
            }

            String query = "SELECT username, email, password_hash FROM Users WHERE user_id = " + userId;

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                String username = rs.getString("username") != null ? rs.getString("username") : "N/A";
                String email = rs.getString("email") != null ? rs.getString("email") : "N/A";
                String password = rs.getString("password_hash") != null ? rs.getString("password_hash") : "N/A";

                String jsonResponse = String.format(
                    "{\"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\"}",
                    username, email, password
                );

                //System.out.println("User found, sending profile data: " + jsonResponse);
                pw.write(jsonResponse);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                pw.write("{\"error\": \"User not found\"}");
            }
        } catch (SQLException e) {
            //System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            pw.write("{\"error\": \"Database error occurred\"}");
        } catch (Exception e) {
            //System.err.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            pw.write("{\"error\": \"Failed to load user's profile\"}");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
                System.out.println("Database resources closed.");
            } catch (SQLException se) {
                System.err.println("Error closing resources: " + se.getMessage());
                se.printStackTrace();
            }
        }
    }
}

