package Server;

import Model.*;
import SQL.*;
import com.google.gson.Gson;
import SQL.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.UUID;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final Gson gson = new Gson();
    private DBConnection dbConnection;

    @Override
    public void init() throws ServletException {
        // Initialize DBConnection
        dbConnection = new DBConnection();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);
        PrintWriter out = response.getWriter();

        // Validate required fields
        if (user.getUsername() == null || user.getPasswordHash() == null || user.getRole() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Missing required fields: username, password, or role.\"}");
            return;
        }

        // Register user using DBConnection
        if (dbConnection.registerUser(user)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.println("{\"message\": \"User registered successfully.\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            out.println("{\"error\": \"Username or email already exists.\"}");
        }
    }

    @Override
    public void destroy() {
        // Close the database connection when the servlet is destroyed
        if (dbConnection != null) {
            dbConnection.closeConnection();
        }
    }
}