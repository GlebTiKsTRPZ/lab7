package state;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import cmd.Command;
import cmd.CommandManager;
import core.ServerContext;
import core.Session;
import logging.Logging;

public class OpenState implements ServerState {

    @Override
    public String name() {
        return "OPEN";
    }

    @Override
    public void onEnter(ServerContext ctx) {
        Logging.info("State -> OPEN");
    }

    @Override
    public void handleAccepted(ServerContext ctx, Socket client) throws Exception {
        ctx.pool().submit(() -> serve(ctx, client));
    }

    private void serve(ServerContext ctx, Socket socket) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            Session s = new Session(bw, ctx.root());
            s.writeLine("220 " + ctx.config().banner());
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.trim().split("\s+", 2);
                Command cmd = CommandManager.of(p[0], p.length > 1 ? p[1] : "");
                String resp;
                try {
                    resp = cmd.execute(s);
                } catch (Exception e) {
                    resp = "451 Local error";
                }
                bw.write(resp);
                bw.write("\r\n");
                bw.flush();
                if (s.shouldQuit()) {
                    break;
                }
            }
        } catch (Exception e) {

        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public String control(ServerContext ctx, String[] t) {
        if (t.length == 2 && t[0].equals("STATE") && t[1].equals("CLOSING")) {
            ctx.setState(new ClosingState());
            return "200 OK";
        }
        return null;
    }
}
