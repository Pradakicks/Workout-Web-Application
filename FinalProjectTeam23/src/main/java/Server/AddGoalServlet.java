package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/AddGoal")
public class AddGoalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("application/json");

        String clientIdParam = request.getParameter("clientId");
        String goalParam = request.getParameter("goal");

        if (clientIdParam == null || clientIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.write("{\"error\": \"clientId is required\"}");
            return;
        } else if (goalParam == null || goalParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.write("{\"error\": \"goal is required\"}");
            return;
        }

        int clientId;
        try {
            clientId = Integer.parseInt(clientIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.write("{\"error\": \"Invalid clientId format\"}");
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

            String selectQuery = "SELECT goal FROM Goals WHERE goal = ? AND client_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(selectQuery);
            pstmt.setString(1, goalParam);
            pstmt.setInt(2, clientId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                pw.write("{\"message\": \"Goal already exists for the user\"}");
            } else {
                String insertQuery = "INSERT INTO Goals (client_id, goal) VALUES (?, ?)";
                pstmt = connection.prepareStatement(insertQuery);
                pstmt.setInt(1, clientId);
                pstmt.setString(2, goalParam);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    pw.write("{\"success\": \"Goal added successfully\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.write("{\"error\": \"Failed to add goal\"}");
                }
            }

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            pw.write("{\"error\": \"Database error occurred\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            pw.write("{\"error\": \"Unexpected error\"}");
        } finally {
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
