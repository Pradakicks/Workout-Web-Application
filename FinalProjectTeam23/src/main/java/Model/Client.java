import java.util.UUID;

public class Client extends User {
    private UUID clientID;
    private String goals; 
    private String workoutPlan; 
    private Streak streak;

    public Client(UUID userID, String username, String passwordHash, String email, String profilePicture, String role, UUID clientID, String goals, String workoutPlan, Streak streak) {
        super(userID, username, passwordHash, email, profilePicture, role);
        this.clientID = clientID;
        this.goals = goals;
        this.workoutPlan = workoutPlan;
        this.streak = streak;
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
    
    public Streak getStreak() {
        return streak;
    }

    public void setStreak(Streak streak) {
        this.streak = streak;
    }
}

