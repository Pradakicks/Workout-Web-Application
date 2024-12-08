package Model;
import java.sql.Timestamp;

public class Streak {
    private int streakId;
    private int clientId;
    private int currentStreak; 
    private int longestStreak;
    private Timestamp lastCheckin;

    public Streak(int streakId, int clientId, int currentStreak, int longestStreak, Timestamp lastCheckin) {
        this.streakId = streakId;
        this.clientId = clientId;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.lastCheckin = lastCheckin;
    }

    public int getStreakId() {
        return streakId;
    }

    public void setStreakId(int streakId) {
        this.streakId = streakId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
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