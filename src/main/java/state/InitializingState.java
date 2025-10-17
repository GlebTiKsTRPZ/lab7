
package state;

import core.ServerContext;
import logging.Logging;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class InitializingState implements ServerState {
    @Override public String name(){ return "INITIALIZING"; }
    @Override public void onEnter(ServerContext ctx){
        Logging.info("State -> INITIALIZING");
    }
    @Override public void handleAccepted(ServerContext ctx, Socket client) throws Exception {
        // Reject new connections politely (421)
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8))) {
            out.write("421 Service not ready, initializing\r\n"); out.flush();
        } finally { client.close(); }
    }
    @Override public String control(ServerContext ctx, String[] t){
        if (t.length == 2 && t[0].equals("STATE") && t[1].equals("OPEN")) {
            ctx.setState(new OpenState());
            return "200 OK";
        }
        return null;
    }
}
