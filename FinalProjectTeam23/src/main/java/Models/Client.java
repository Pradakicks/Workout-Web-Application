import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.sql.Connection;

public class Client extends User {
    private UUID clientID;
    private String goals;
    private String workoutPlan;

    public Client(UUID userID, String username, String passwordHash, String email, String role, UUID clientID, String goals, String workoutPlan) {
        super(userID, username, passwordHash, email, role);
        this.clientID = clientID;
        this.goals = goals;
        this.workoutPlan = workoutPlan;
    }

    public UUID getClientID() {
        return clientID;
    }

    public void setClientID(UUID clientID) {
        this.clientID = clientID;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getWorkoutPlan() {
        return workoutPlan;
    }

    public void setWorkoutPlan(String workoutPlan) {
        this.workoutPlan = workoutPlan;
    }
}

