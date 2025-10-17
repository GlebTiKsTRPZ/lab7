
package core;

import logging.Logging;
import state.ServerState;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class FtpServer {
    private final ServerContext ctx;

    public FtpServer(ServerConfig cfg) {
        this.ctx = new ServerContext(cfg, Paths.get(System.getProperty("user.dir")));
    }

    public void start() throws Exception {
        // start control loop
        new Thread(this::runControl, "control").start();
        // start FTP listen loop
        try (ServerSocket ss = new ServerSocket(ctx.config().ftpPort())) {
            Logging.info("FTP on :" + ctx.config().ftpPort());
            while (true) {
                Socket c = ss.accept();
                ctx.state().handleAccepted(ctx, c);
            }
        }
    }

    private void runControl() {
        try (ServerSocket ss = new ServerSocket(ctx.config().controlPort())) {
            Logging.info("CTRL on :" + ctx.config().controlPort());
            while (true) {
                Socket s = ss.accept();
                new Thread(() -> handleCtrl(s)).start();
            }
        } catch (IOException e) {
            Logging.error("CTRL failed: " + e.getMessage());
        }
    }

    private void handleCtrl(Socket s) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8))) {
            bw.write("200 Control Ready\r\n"); bw.flush();
            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.trim().toUpperCase().split("\\s+");
                if (t.length == 1 && t[0].equals("STATUS")) {
                    bw.write("200 " + ctx.status() + "\r\n"); bw.flush();
                    continue;
                }
                String resp = ctx.state().control(ctx, t);
                if (resp == null) { bw.write("500 Unknown\r\n"); bw.flush(); }
                else { bw.write(resp + "\r\n"); bw.flush(); }
            }
        } catch (IOException ignored) {
        } finally {
            try { s.close(); } catch (IOException ignored) {}
        }
    }

    public static void main(String[] args) throws Exception {
        new FtpServer(ServerConfig.fromEnv()).start();
    }
}
