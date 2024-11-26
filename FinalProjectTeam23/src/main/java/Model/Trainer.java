import java.util.UUID;

public class Trainer extends User {
    private UUID trainerID;
    private String trainerContact;
    private String services;
    private String workoutPlan;
    private String about;

    public Trainer(UUID userID, String username, String passwordHash, String email, String profilePicture, String role, UUID trainerID, String trainerContact, String services, String workoutPlan, String about) {
        super(userID, username, passwordHash, email, profilePicture, role);
        this.trainerID = trainerID;
        this.trainerContact = trainerContact;
        this.services = services;
        this.workoutPlan = workoutPlan;
        this.about = about;
    }

    public UUID getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(UUID trainerID) {
        this.trainerID = trainerID;
    }

    public String getTrainerContact() {
        return trainerContact;
    }

    public void setTrainerContact(String trainerContact) {
        this.trainerContact = trainerContact;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getWorkoutPlan() {
        return workoutPlan;
    }

    public void setWorkoutPlan(String workoutPlan) {
        this.workoutPlan = workoutPlan;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
