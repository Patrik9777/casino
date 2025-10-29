import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users;
    private User currentUser;

    public UserManager() {
        users = new HashMap<>();
        // Példa felhasználó hozzáadása
        users.put("user1", new User("user1", "jelszo123", "user1@example.com"));
    }

    public boolean registerUser(String username, String password, String email) {
        if (users.containsKey(username)) {
            return false; // Már létezik ilyen felhasználónév
        }
        users.put(username, new User(username, password, email));
        return true;
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
