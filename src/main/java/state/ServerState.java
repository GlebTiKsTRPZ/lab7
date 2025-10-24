package state;

import java.net.Socket;

import core.ServerContext;

public interface ServerState {

    String name();

    default void onEnter(ServerContext ctx) {
    }

    void handleAccepted(ServerContext ctx, Socket client) throws Exception;

    default String control(ServerContext ctx, String[] tokens) {
        return null;
    }
}
