
package state;

import core.ServerContext;
import java.net.Socket;

public interface ServerState {
    String name();
    /** Called when state becomes active. */
    default void onEnter(ServerContext ctx) {}
    /** Handle a newly accepted client socket. */
    void handleAccepted(ServerContext ctx, Socket client) throws Exception;
    /** Optional control command hook; return response string or null for default. */
    default String control(ServerContext ctx, String[] tokens){ return null; }
}
