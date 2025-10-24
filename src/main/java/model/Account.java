package model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Account {

    private static final Map<String, String> USERS = new ConcurrentHashMap<>();

    static {
        USERS.put("admin", "admin");
        USERS.put("test", "test");
    }

    public static boolean verify(String user, String pass) {
        return pass != null && pass.equals(USERS.get(user));
    }
}
