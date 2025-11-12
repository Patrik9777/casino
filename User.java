package saves.save1;

public class User {
    private String username;
    private String password;
    private String email;
    private int balance;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.balance = 0;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }
}
