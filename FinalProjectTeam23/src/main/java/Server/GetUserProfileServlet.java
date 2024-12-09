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

        // Log incoming request
        System.out.println("Received request to get user profile for userId: " + userIdParam);

        // Check if userId is provided
        if (userIdParam == null || userIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println("Error: userId is missing.");
            pw.write("{\"error\": \"userId is required\"}");
            return;
        }

        int userId;
        try {
            // Try to parse userId to integer
            userId = Integer.parseInt(userIdParam);
            System.out.println("Parsed userId: " + userId);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println("Error: Invalid userId format - " + userIdParam);
            pw.write("{\"error\": \"Invalid userId format\"}");
            return;
        }

        // Database connection setup
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            System.out.println("Attempting to connect to the database...");
            connection = DBConnection.getConnection();

            if (connection == null) {
                System.out.println("Error: Failed to establish database connection.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                pw.write("{\"error\": \"Failed to establish database connection\"}");
                return;
            }

            System.out.println("Database connection established.");

            // Updated SQL query to include profile_picture
            String query = "SELECT name, username, email, password FROM Users WHERE user_ID = " + userId;
            System.out.println("Executing query: " + query);

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            // Check if the user exists
            if (rs.next()) {
                String name = rs.getString("name") != null ? rs.getString("name") : "N/A";
                String username = rs.getString("username") != null ? rs.getString("username") : "N/A";
                String email = rs.getString("email") != null ? rs.getString("email") : "N/A";
                String password = rs.getString("password") != null ? rs.getString("password") : "N/A";

                // Constructing the response JSON
                String jsonResponse = String.format(
                    "{\"name\": \"%s\", \"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\"}",
                    name, username, email, password
                );

                System.out.println("User found, sending profile data: " + jsonResponse);
                pw.write(jsonResponse);
            } else {
                // Log user not found
                System.out.println("No user found with userId: " + userId);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                pw.write("{\"error\": \"User not found\"}");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            pw.write("{\"error\": \"Database error occurred\"}");
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            //pw.write("{\"error\": \"Failed to load user's profile\"}");
        } finally {
            try {
                // Close database resources
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
