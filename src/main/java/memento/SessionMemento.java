package memento;

import java.nio.file.Path;

public final class SessionMemento {
    private final Path cwd;
    private final boolean authed;
    private final boolean quit;
    private final String pendingUser;

    public SessionMemento(Path cwd, boolean authed, boolean quit, String pendingUser) {
        this.cwd = cwd;
        this.authed = authed;
        this.quit = quit;
        this.pendingUser = pendingUser;
    }

    public Path cwd() {
        return cwd;
    }

    public boolean authed() {
        return authed;
    }

    public boolean quit() {
        return quit;
    }

    public String pendingUser() {
        return pendingUser;
    }
}