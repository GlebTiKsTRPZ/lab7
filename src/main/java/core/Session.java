package core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;

public class Session {

    private final BufferedWriter out;
    private final Path root;
    private Path cwd = null;
    private boolean authed = false;
    private boolean quit = false;
    private String pendingUser = null;

    public Session(BufferedWriter out, Path root) {
        this.out = out;
        this.root = root.toAbsolutePath().normalize();
        this.cwd = this.root;
    }

    public void writeLine(String s) throws IOException {
        out.write(s);
        out.write("\r\n");
        out.flush();
    }

    public Path cwd() {
        return cwd;
    }

    public boolean changeDir(Path p) {
        Path np = cwd.resolve(p).normalize();
        if (!np.toAbsolutePath().startsWith(root)) {
            return false;
        }
        this.cwd = np;
        return true;
    }

    public boolean isAuthed() {
        return authed;
    }

    public void setAuthed(boolean a) {
        this.authed = a;
    }

    public boolean shouldQuit() {
        return quit;
    }

    public void setQuit(boolean q) {
        this.quit = q;
    }

    public String getPendingUser() {
        return pendingUser;
    }

    public void setPendingUser(String u) {
        this.pendingUser = u;
    }
}
