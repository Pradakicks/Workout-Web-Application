package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/fetchGoals")
public class GetGoalsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("application/json");

        String userIdParam = request.getParameter("userId");

        if (userIdParam == null || userIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.write("{\"error\": \"clientId is required\"}");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.write("{\"error\": \"Invalid clientId format\"}");
            return;
        }

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getConnection();

            if (connection == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                pw.write("{\"error\": \"Failed to establish database connection\"}");
                return;
            }

            String query = "SELECT goal FROM Goals WHERE user_id = ?";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, userId);

            rs = stmt.executeQuery();
            List<String> goals = new ArrayList<>();

            while (rs.next()) {
                goals.add(rs.getString("goal"));
            }

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(goals);
            pw.write(jsonResponse);

        } catch (SQLException e) {
            e.printStackTrace(); 
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            pw.write("{\"error\": \"Database error occurred: " + e.getMessage() + "\"}");
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); 
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            pw.write("{\"error\": \"JDBC Driver not found: " + e.getMessage() + "\"}");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException ignored) {
            }
        }
    }
}