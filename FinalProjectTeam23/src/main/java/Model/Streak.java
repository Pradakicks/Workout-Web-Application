import java.sql.Timestamp;
import java.util.UUID;

public class Streak {
    private UUID streakId;
    private UUID clientId;
    private int currentStreak; 
    private int longestStreak;
    private Timestamp lastCheckin;

    public Streak(UUID streakId, UUID clientId, int currentStreak, int longestStreak, Timestamp lastCheckin) {
        this.streakId = streakId;
        this.clientId = clientId;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.lastCheckin = lastCheckin;
    }

    public UUID getStreakId() {
        return streakId;
    }

    public void setStreakId(UUID streakId) {
        this.streakId = streakId;
    }

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
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
