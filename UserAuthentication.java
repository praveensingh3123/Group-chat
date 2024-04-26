package group.chatting.application;

import java.util.HashMap;
import java.util.Map;

public class UserAuthentication {
    private static Map<String, String> userCredentials = new HashMap<>();

    static {
        // Add some sample user credentials
        userCredentials.put("user1", "password1");
        userCredentials.put("user2", "password2");
        userCredentials.put("user3", "password3");
    }

    public static boolean authenticate(String username, String password) {
        String storedPassword = userCredentials.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}