package pp.projects.model;

// Classe per contenere 2 valori (nome utente e password)
public class UserCredentials {
	private String userName;
    private String password;
    private String eventsFilePath;

    public UserCredentials(String password, String userName, String eventsFilePath) {
        this.userName = userName;
        this.password = password;
        this.eventsFilePath = eventsFilePath;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEventsFilePath() {
        return this.eventsFilePath;
    }

    public void setEventsFilePath(String eventsFilePath) {
        this.eventsFilePath = eventsFilePath;
    }
    
    @Override
    public String toString() {
        return "UserCredentials{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", eventsFilePath='" + eventsFilePath + '\'' +
                '}';
    }
    
}
