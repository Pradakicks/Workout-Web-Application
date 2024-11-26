import java.util.UUID;

public class Trainer extends User {
    private UUID trainerId;
    private String trainerContact;
    private String services;
    private String workoutPlan;
    private String about;

    public Trainer(UUID userId, String username, String passwordHash, String email, String profilePicture, String role, UUID trainerId, String trainerContact, String services, String workoutPlan, String about) {
        super(userId, username, passwordHash, email, profilePicture, role);
        this.trainerId = UUID.randomUUID();
        this.trainerContact = trainerContact;
        this.services = services;
        this.workoutPlan = workoutPlan;
        this.about = about;
    }

    public UUID getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(UUID trainerId) {
        this.trainerId = trainerId;
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
