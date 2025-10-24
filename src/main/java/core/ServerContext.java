package core;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import state.InitializingState;
import state.ServerState;

public class ServerContext {

    private final ServerConfig config;
    private final Path root;
    private final ExecutorService pool = Executors.newCachedThreadPool();
    private volatile ServerState state;

    public ServerContext(ServerConfig cfg, Path rootDir) {
        this.config = cfg;
        this.root = rootDir;
        this.state = new InitializingState();
        this.state.onEnter(this);
    }

    public ServerConfig config() {
        return config;
    }

    public Path root() {
        return root;
    }

    public ExecutorService pool() {
        return pool;
    }

    public ServerState state() {
        return state;
    }

    public void setState(ServerState next) {
        this.state = next;
        next.onEnter(this);
    }

    public String status() {
        return "STATE " + state.name();
    }
}
