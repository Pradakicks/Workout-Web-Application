package com.fitnessapp.servlets;

import javax.servlet.http.HttpServlet;

import com.fitnessapp.utils.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        // Retrieve username and password from request
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");

	        // Validate user (implement this method)
	        boolean isValidUser = validateUser(username, password);

	        if (isValidUser) {
	            // Successful login
	            response.setStatus(HttpServletResponse.SC_OK);
	            response.getWriter().write("Login successful");
	        } 
	        else {
	            // Failed login
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            response.getWriter().write("Invalid credentials");
	        }
	    }

	 private boolean validateUser(String username, String password) {
		    boolean isValid = false;

		    try (Connection conn = DBConnection.getConnection()) {
		        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		        PreparedStatement stmt = conn.prepareStatement(sql);
		        stmt.setString(1, username);
		        stmt.setString(2, password);

		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            isValid = true;
		        }

		        rs.close();
		        stmt.close();
		    } 
		    catch (SQLException e) {
		        e.printStackTrace();
		    }

		    return isValid;
		}
	}