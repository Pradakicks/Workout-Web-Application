package Server;

import Model.User;
import com.google.gson.Gson;
import SQL.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Gson gson = new Gson();
    private DBConnection dbConnection; // Instance of DBConnection

    @Override
    public void init() throws ServletException {
        // Initialize the DBConnection instance
        dbConnection = new DBConnection();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class); // Parse JSON into User object
        PrintWriter out = response.getWriter();

        // Validate required fields
        if (user.getUsername() == null || user.getPasswordHash() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Missing username or password.\"}");
            return;
        }

        // Authenticate user using DBConnection
        User authenticatedUser = dbConnection.authenticateUser(user.getUsername(), user.getPasswordHash());
        if (authenticatedUser != null) {
            response.setStatus(HttpServletResponse.SC_OK);
            out.println(gson.toJson(authenticatedUser));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.println("{\"error\": \"Invalid username or password.\"}");
        }
    }

    @Override
    public void destroy() {
        // Close the DB connection when the servlet is destroyed
        if (dbConnection != null) {
            dbConnection.closeConnection();
        }
    }
}