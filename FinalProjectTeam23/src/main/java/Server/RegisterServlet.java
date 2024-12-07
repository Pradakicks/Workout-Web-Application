package Server;

import Model.*;
import SQL;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.UUID;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);
        PrintWriter out = response.getWriter();

        if (user.getUsername() == null || user.getPasswordHash() == null || user.getRole() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Missing required fields: username, password, or role.\"}");
            return;
        }

        if (registerUser(user)) {
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.println("{\"message\": \"User registered successfully.\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            out.println("{\"error\": \"Username or email already exists.\"}");
        }
    }

    @Override
    public void destroy() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean registerUser(User user) {
        String query = "INSERT INTO Users (user_ID, username, password_hash, email, role) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUserId().toString());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPasswordHash());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getRole());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.err.println("Duplicate entry: " + e.getMessage());
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }
}
