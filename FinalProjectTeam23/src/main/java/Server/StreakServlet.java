package Server;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;

@WebServlet("/update-streak")
public class StreakServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String clientIdParam = req.getParameter("clientId");
        if (clientIdParam == null || clientIdParam.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Error: clientId is required.");
            return;
        }

        try {
            int clientId = Integer.parseInt(clientIdParam);
            boolean isUpdated = updateStreak(clientId);

            if (isUpdated) {
            	int updatedStreak = getCurrentStreak(clientId);

                // Send success response with updated streak value
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Streak updated successfully! Current streak: " + updatedStreak);
            } 
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Error: Invalid clientId format.");
            e.printStackTrace();
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error thrown herefjrgfnmjrglnr: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean updateStreak(int clientId) {
        int currentStreak = 0;
        int longestStreak = 0;
        Timestamp lastCheckin = null;

        try {
            System.out.println("Fetching streak for client ID: " + clientId);
            String query = "SELECT current_streak, longest_streak, last_checkin FROM Streaks WHERE client_ID = " + clientId;
            try (Connection conn = Database.getConnection()) {
                if (conn == null) {
                    throw new Exception("Failed to establish database connection.");
                }
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if (rs.next()) {
                    currentStreak = rs.getInt("current_streak");
                    longestStreak = rs.getInt("longest_streak");
                    lastCheckin = rs.getTimestamp("last_checkin");
                } else {
                    // No streak found, so create a new streak for the client
                    System.out.println("No streak found for client ID: " + clientId + ". Creating new streak.");
                    return createNewStreak(clientId);
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching streak: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        // Get the current date 
        java.util.Date nowDate = new java.util.Date();
        java.sql.Date currentDate = new java.sql.Date(nowDate.getTime()); 

        java.sql.Date lastCheckinDate = new java.sql.Date(lastCheckin.getTime());

        // Check if the streak should be reset
        if (!currentDate.equals(lastCheckinDate)) {
            long diffInMillis = currentDate.getTime() - lastCheckinDate.getTime();
            long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);

            if (diffInDays > 1) {
                currentStreak = 1;  // Reset the streak to 1 if more than one day has passed
                System.out.println("More than a day has passed. Streak is reset to 1.");
            } else {
                // If it's a new day, increment the streak
                currentStreak++;
                System.out.println("New day detected. Streak incremented.");
            }

            // Update the longest streak if the current streak is greater
            if (currentStreak > longestStreak) {
                longestStreak = currentStreak;
            }

            // Update last check-in date to today
            lastCheckin = Timestamp.valueOf(currentDate.toString() + " 00:00:00");
        } 

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Failed to connect to the database.");
                throw new Exception("Failed to connect to the database.");
            }

            String updateStreakQuery = "UPDATE Streaks SET current_streak = " + currentStreak +
                    ", longest_streak = " + longestStreak + ", last_checkin = '" + lastCheckin + "' WHERE client_ID = " + clientId;

            Statement stmt = conn.createStatement();
            int rowsUpdatedStreak = stmt.executeUpdate(updateStreakQuery);

            if (rowsUpdatedStreak > 0) {
                System.out.println("Streak updated successfully in Streaks table!");
                return true;
            } else {
                System.out.println("Failed to update streak in Streaks table: No rows updated.");
                throw new Exception("Failed to update streak in Streaks table.");
            }

            // String updateClientQuery = "UPDATE Clients SET current_streak = " + currentStreak + " WHERE client_ID = " + clientId;
            // int rowsUpdatedClient = stmt.executeUpdate(updateClientQuery);

            // if (rowsUpdatedClient > 0) {
            //     System.out.println("Current streak updated successfully in Clients table!");
            //     return true;
            // } else {
            //     System.out.println("Failed to update current streak in Clients table: No rows updated.");
            //     throw new Exception("Failed to update current streak in Clients table.");
            // }
        } catch (Exception e) {
            System.out.println("Error during update operation: " + e.getMessage());
            e.printStackTrace();  
            return false; 
        }
    }



    private int getCurrentStreak(int clientId) {
        int currentStreak = 0;
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                throw new Exception("Failed to establish database connection.");
            }
            String query = "SELECT current_streak FROM Clients WHERE client_ID = " + clientId;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                currentStreak = rs.getInt("current_streak");
            }
        } catch (Exception e) {
            System.out.println("Error fetching current streak: " + e.getMessage());
            e.printStackTrace();
        }
        return currentStreak;
    }


    private boolean createNewStreak(int clientId) throws Exception {
        Timestamp now = Timestamp.from(Instant.now());
        String formattedTimestamp = now.toString(); 

        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            
            String insertQuery = "INSERT INTO Streaks (client_ID, current_streak, longest_streak, last_checkin) " +
                                 "VALUES (" + clientId + ", 1, 1, '" + formattedTimestamp + "')";
            int rowsInserted = stmt.executeUpdate(insertQuery);
            
            if (rowsInserted > 0) {
            	String updateQuery = "UPDATE Clients SET current_streak = 1 WHERE client_ID = " + clientId;
                int rowsUpdated = stmt.executeUpdate(updateQuery);
                
                if (rowsUpdated > 0) {
                    return true;
                } else {
                    throw new Exception("Failed to update current_streak in Clients table.");
                }
            } else {
                throw new Exception("Failed to insert new streak into the database.");
            }
        } catch (Exception e) {
            System.out.println("Error during insert or update operation: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}