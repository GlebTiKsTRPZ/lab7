package state;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import core.ServerContext;
import logging.Logging;

public class ClosingState implements ServerState {

    @Override
    public String name() {
        return "CLOSING";
    }

    @Override
    public void onEnter(ServerContext ctx) {
        Logging.info("State -> CLOSING");
    }

    @Override
    public void handleAccepted(ServerContext ctx, Socket client) throws Exception {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8))) {
            out.write("421 Service not available, closing\r\n");
            out.flush();
        } finally {
            client.close();
        }
    }

    @Override
    public String control(ServerContext ctx, String[] t) {
        if (t.length == 2 && t[0].equals("STATE") && t[1].equals("OPEN")) {
            ctx.setState(new OpenState());
            return "200 OK";
        }
        return null;
    }
}
