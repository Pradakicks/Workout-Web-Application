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

@WebServlet("/UpdateUserProfile")
public class UpdateUserProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("application/json");

        String userIdParam = request.getParameter("userId");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (userIdParam == null || userIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println("Error: userId is missing.");
            pw.write("{\"error\": \"userId is required\"}");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println("Error: Invalid userId format - " + userIdParam);
            //pw.write("{\"error\": \"Invalid userId format\"}");
            return;
        }

        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getConnection();

            if (connection == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                //pw.write("{\"error\": \"Failed to establish database connection\"}");
                return;
            }

            String selectUserQuery = "SELECT username, email, password_hash FROM Users WHERE user_id = " + userId;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectUserQuery);

            if (rs.next()) {
                //String currentName = rs.getString("username");
                //String currentEmail = rs.getString("email");
                //String currentPassword = rs.getString("password_hash");

                //System.out.println("Current user data: Username=" + currentName + ", Email=" + currentEmail + ", Password=" + currentPassword);

                StringBuilder updateQuery = new StringBuilder("UPDATE Users SET ");
                boolean isFirst = true;

                if (username != null && !username.isEmpty()) {
                    if (!isFirst) {
                        updateQuery.append(", ");
                    }
                    updateQuery.append("username = '" + username.replace("'", "''") + "'");
                    isFirst = false;
                }
                
                if (email != null && !email.isEmpty()) {
                    if (!isFirst) {
                        updateQuery.append(", ");
                    }
                    updateQuery.append("email = '" + email.replace("'", "''") + "'");
                    isFirst = false;
                }

                if (password != null && !password.isEmpty()) {
                    if (!isFirst) {
                        updateQuery.append(", ");
                    }
                    updateQuery.append("password_hash = '" + password.replace("'", "''") + "'");
                    isFirst = false;
                }

                updateQuery.append(" WHERE user_id = " + userId);
                int rowsAffected = stmt.executeUpdate(updateQuery.toString());

                if (rowsAffected > 0) {
                    pw.println("{\"success\": \"User profile updated successfully\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    //pw.println("{\"error\": \"Failed to update user profile\"}");
                }
            } else {
                // User not found in database
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                //pw.write("{\"error\": \"User not found\"}");
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            //pw.println("{\"error\": \"Database error occurred\"}");
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            //pw.println("{\"error\": \"Failed to update user profile\"}");
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}


