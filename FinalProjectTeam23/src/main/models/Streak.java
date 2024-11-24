import java.sql.Timestamp;

public class Streak {
    private String streakId;
    private String clientId;
    private int currentStreak;
    private int longestStreak;
    private Timestamp lastCheckin;

    public Streak(String streakId, String clientId, int currentStreak, int longestStreak, Timestamp lastCheckin) {
        this.streakId = streakId;
        this.clientId = clientId;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.lastCheckin = lastCheckin;
    }

    public String getStreakId() {
        return streakId;
    }

    public void setStreakId(String streakId) {
        this.streakId = streakId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public Timestamp getLastCheckin() {
        return lastCheckin;
    }

    public void setLastCheckin(Timestamp lastCheckin) {
        this.lastCheckin = lastCheckin;
    }
}
