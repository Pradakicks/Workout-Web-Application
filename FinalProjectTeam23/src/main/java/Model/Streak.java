import java.sql.Timestamp;
import java.util.UUID;

public class Streak {
    private UUID streakID;
    private UUID clientID;
    private int currentStreak; 
    private int longestStreak;
    private Timestamp lastCheckin;

    public Streak(UUID streakID, UUID clientID, int currentStreak, int longestStreak, Timestamp lastCheckin) {
        this.streakID = streakID;
        this.clientID = clientID;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.lastCheckin = lastCheckin;
    }

    public UUID getStreakID() {
        return streakID;
    }

    public void setStreakID(UUID streakID) {
        this.streakID = streakID;
    }

    public UUID getClientID() {
        return clientID;
    }

    public void setClientID(UUID clientID) {
        this.clientID = clientID;
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
