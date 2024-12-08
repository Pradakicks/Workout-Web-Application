package Model;

import Model.User;

public class Trainer extends User {
    private int trainerId;
    private String trainerContact;
    private String services;
    private String trainerTitle;

    public Trainer(int trainerId, int userId, String username, String name, String passwordHash, String email, String profilePicture, String role, String trainerContact, String services, String trainerTitle) {
        super(userId, username, name, passwordHash, email, profilePicture, role);
        this.trainerId = trainerId;
        this.trainerContact = trainerContact;
        this.services = services;
        this.trainerTitle = trainerTitle;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
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

    public String getTrainerTitle() {
        return trainerTitle;
    }

    public void setWorkoutPlan(String trainerTitle) {
        this.trainerTitle = trainerTitle;
    }

}
