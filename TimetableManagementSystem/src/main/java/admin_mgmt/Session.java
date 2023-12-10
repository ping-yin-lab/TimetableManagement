package admin_mgmt;

public class Session {
	public static String currentUser;

    public static String getCurrentUser() {
        return currentUser;
    }

    static void setCurrentUser(String username) {
        currentUser = username;
    }

    static void resetSession() {
        currentUser = null;
    }
}
