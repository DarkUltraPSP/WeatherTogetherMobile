package app.hesias.weathertogether.Model;

public class Utilisateur {
    private int id;
    private String username;
    private int phone;

    public Utilisateur(int id, String username, int phone) {
        this.id = id;
        this.username = username;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
