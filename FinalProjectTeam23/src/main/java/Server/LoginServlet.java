package Server;

import Model.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        try {
            String url = "jdbc:mysql://localhost:3306/workout_app";
            String username = "your_username";
            String password = "your_password";
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        BufferedReader reader = request.getReader();
        LoginRequest loginRequest = gson.fromJson(reader, LoginRequest.class);
        PrintWriter out = response.getWriter();

        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\": \"Missing username or password.\"}");
            return;
        }

        User user = authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            response.setStatus(HttpServletResponse.SC_OK);
            out.println(gson.toJson(user));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.println("{\"error\": \"Invalid username or password.\"}");
        }
    }