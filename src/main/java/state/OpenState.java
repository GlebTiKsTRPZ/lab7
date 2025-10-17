
package state;

import cmd.Command;
import cmd.CommandManager;
import core.ServerContext;
import core.Session;
import logging.Logging;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class OpenState implements ServerState {
    @Override public String name(){ return "OPEN"; }
    @Override public void onEnter(ServerContext ctx){ Logging.info("State -> OPEN"); }
    @Override public void handleAccepted(ServerContext ctx, Socket client) throws Exception {
        ctx.pool().submit(() -> serve(ctx, client));
    }

    private void serve(ServerContext ctx, Socket socket) {
        long in=0, out=0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            Session s = new Session(bw, ctx.root());
            s.writeLine("220 " + ctx.config().banner());
            String line;
            while ((line = br.readLine()) != null) {
                in += line.length()+2;
                String[] p = line.trim().split("\s+",2);
                Command cmd = CommandManager.of(p[0], p.length>1? p[1]:"");
                String resp;
                try { resp = cmd.execute(s); } catch (Exception e){ resp = "451 Local error"; }
                bw.write(resp); bw.write("\r\n"); bw.flush();
                out += resp.length()+2;
                if (s.shouldQuit()) break;
            }
        } catch (Exception e) {
            // ignore for demo
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }

    @Override public String control(ServerContext ctx, String[] t){
        if (t.length == 2 && t[0].equals("STATE") && t[1].equals("CLOSING")) {
            ctx.setState(new ClosingState());
            return "200 OK";
        }
        return null;
    }
}
