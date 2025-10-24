package logging;

public final class Logging {

    public static void info(String m) {
        System.out.println("[INFO] " + m);
    }

    public static void warn(String m) {
        System.out.println("[WARN] " + m);
    }

    public static void error(String m) {
        System.err.println("[ERROR] " + m);
    }
}
