package Server;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/update-streak")
public class StreakServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userIdParam = request.getParameter("userId");

        if (userIdParam == null || userIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"userId is required\"}");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid userId format\"}");
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT profile_picture FROM Users WHERE user_id = ?")) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    byte[] profilePicture = rs.getBytes("profile_picture");
                    if (profilePicture != null) {
                        response.setContentType("image/png");
                        response.setContentLength(profilePicture.length);
                        response.getOutputStream().write(profilePicture);
                    } else {
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        response.getWriter().write("{\"error\": \"Profile picture not found\"}");
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"User not found\"}");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Database error occurred\"}");
        }
    }

    private boolean updateStreak(int clientId) {
        int currentStreak = 0;
        int longestStreak = 0;
        Timestamp lastCheckin = null;

        try {
            String query = "SELECT current_streak, longest_streak, last_checkin FROM Streaks WHERE client_ID = " + clientId;
            try (Connection conn = DBConnection.getConnection()) {
                if (conn == null) throw new Exception("Failed to establish database connection.");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if (rs.next()) {
                	System.out.println("144 ");
                    currentStreak = rs.getInt("current_streak");
                    System.out.println("55 ");
                    longestStreak = rs.getInt("longest_streak");
                    System.out.println("66 ");
                    lastCheckin = rs.getTimestamp("last_checkin");
                    System.out.println("177");
                } else {
                    return createNewStreak(clientId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Timestamp now = Timestamp.from(Instant.now());
        if (lastCheckin == null || now.toLocalDateTime().toLocalDate().isAfter(lastCheckin.toLocalDateTime().toLocalDate())) {
            currentStreak = (lastCheckin == null) ? 1 : currentStreak + 1;
            longestStreak = Math.max(longestStreak, currentStreak);
        }

        try (Connection conn = DBConnection.getConnection()) {
            String updateQuery = "UPDATE Streaks SET current_streak = ?, longest_streak = ?, last_checkin = ? WHERE client_ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                stmt.setInt(1, currentStreak);
                stmt.setInt(2, longestStreak);
                stmt.setTimestamp(3, now);
                stmt.setInt(4, clientId);
                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int getCurrentStreak(int clientId) {
        String query = "SELECT current_streak FROM Streaks WHERE client_ID = " + clientId;
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("current_streak");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean createNewStreak(int clientId) throws Exception {
        Timestamp now = Timestamp.from(Instant.now());
        String query = "INSERT INTO Streaks (client_ID, current_streak, longest_streak, last_checkin) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            stmt.setInt(2, 1);
            stmt.setInt(3, 1);
            stmt.setTimestamp(4, now);
            return stmt.executeUpdate() > 0;
        }
    }
}
