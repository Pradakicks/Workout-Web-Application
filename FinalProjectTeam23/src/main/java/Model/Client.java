package Model;

public class Client extends User {
    private int clientId;
    private String goals; 
    private String workoutPlan; 
    private Streak streak;

    public Client(int userId, String username, String passwordHash, String email, String profilePicture, String role, UUID clientId, String goals, String workoutPlan, Streak streak) {
        super(userId, username, passwordHash, email, profilePicture, role);
        this.clientId = clientId;
        this.goals = goals;
        this.workoutPlan = workoutPlan;
        this.streak = streak;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
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

