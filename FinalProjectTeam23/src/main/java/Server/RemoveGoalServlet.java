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

@WebServlet("/RemoveGoal")
public class RemoveGoalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("application/json");

        String clientIdParam = request.getParameter("clientId");
        String goalParam = request.getParameter("goal");


        // Check if Id is provided
        if (clientIdParam == null || clientIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            //System.out.println("Error: userId is missing.");
            pw.write("{\"error\": \"userId is required\"}");
            return;
        } else if (goalParam == null || goalParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            //System.out.println("Error: goal is missing.");
            pw.write("{\"error\": \"goal is required\"}");
            return;
        }

        int clientId;
        try {
            // Try to parse userId to integer
            clientId = Integer.parseInt(clientIdParam);
            //System.out.println("Parsed userId: " + clientId);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            //System.out.println("Error: Invalid userId format - " + clientIdParam);
            pw.write("{\"error\": \"Invalid userId format\"}");
            return;
        }

        // Database connection setup
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            connection = DBConnection.getConnection();

            if (connection == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                pw.write("{\"error\": \"Failed to establish database connection\"}");
                return;
            }

            // First, select if the goal already exists for this user
            String selectQuery = "SELECT goal FROM Goals WHERE goal = '" + goalParam + "' AND client_id = " + clientId;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectQuery);

            if (rs.next()) {
                // Goal exists, proceed to delete it
                String deleteQuery = "DELETE FROM Goals WHERE goal = '" + goalParam + "' AND client_id = " + clientId;
                int rowsAffected = stmt.executeUpdate(deleteQuery);

                if (rowsAffected > 0) {
                    pw.write("{\"success\": \"Goal removed successfully\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    //pw.write("{\"error\": \"Failed to remove goal\"}");
                }
                System.out.println("Goal removed successfully");
            } else {
                // Goal does not exist for the user
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                //pw.write("{\"error\": \"Goal does not exist for the user\"}");
                System.out.println("Goal does not exist for the user");
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
            //pw.write("{\"error\": \"Failed to add goal\"}");
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