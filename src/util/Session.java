package util;

public class Session {
    private static int userId = -1;
    private static String username = null;
    private static String role = null;

    public static void createSession(int id, String user, String userRole) {
        userId = id;
        username = user;
        role = userRole;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static String getRole() {
        return role;
    }

    public static boolean isLoggedIn() {
        return userId != -1;
    }

    public static void destroySession() {
        userId = -1;
        username = null;
        role = null;
    }
}
