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

        // Retrieve clientId from request parameters
        String clientIdParam = request.getParameter("clientId");

        // Check if clientId is missing or empty
        if (clientIdParam == null || clientIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.write("{\"error\": \"clientId is required\"}");
            return;
        }

        int clientId;
        try {
            // Parse clientId to integer
            clientId = Integer.parseInt(clientIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            pw.write("{\"error\": \"Invalid clientId format\"}");
            return;
        }

        // Try to establish a connection and fetch goals
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT goal FROM Goals WHERE client_id = ?")) {

            // Set the clientId parameter for the query
            stmt.setInt(1, clientId);

            // Execute the query and fetch the results
            try (ResultSet rs = stmt.executeQuery()) {
                List<String> goals = new ArrayList<>();

                // Add goals to the list
                while (rs.next()) {
                    goals.add(rs.getString("goal"));
                }

                // If no goals found, return a 404 status
                if (goals.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    pw.write("{\"error\": \"No goals found for the specified clientId\"}");
                } else {
                    // Convert goals list to JSON and send it in the response
                    Gson gson = new Gson();
                    String jsonResponse = gson.toJson(goals);
                    pw.write(jsonResponse);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            pw.write("{\"error\": \"Database error occurred: " + e.getMessage() + "\"}");
        }
    }
}
