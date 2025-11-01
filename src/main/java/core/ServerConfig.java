package core;

public record ServerConfig(int ftpPort, int controlPort, String banner) {

    public static ServerConfig fromEnv() {
        int p = parse(System.getenv("LAB4_FTP_PORT"), 2123);
        int c = parse(System.getenv("LAB4_CTRL_PORT"), 2124);
        String b = System.getenv("LAB4_BANNER");
        if (b == null || b.trim().isEmpty()) {
            b = "Lab4 FTP with State";
        }
        return new ServerConfig(p, c, b);
    }

    private static int parse(String s, int d) {
        try {
            return s == null ? d : Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return d;
        }
    }
}
