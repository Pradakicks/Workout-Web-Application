package Model;

public class Client extends User {
    private int clientId;
    private int userId; 

    public Client(int clientId, int userId, String username, String name, String passwordHash, String email, String profilePicture, String role) {
        super(userId, username, name, passwordHash, email, profilePicture, role);
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }   
}

