package cmd;

import core.Session;
import model.Account;
import protocol.ResponseDirector;

public class PassCmd extends AbstractCommand {

    public PassCmd(String args) {
        super(args);
    }

    @Override
    public String name() {
        return "PASS";
    }

    @Override
    protected String doExec(Session s) {
        String u = s.getPendingUser();
        if (u == null) {
            return "503 Bad sequence of commands";
        }
        boolean ok = Account.verify(u, args.trim());
        if (ok) {
            s.setAuthed(true);
            return ResponseDirector.singleOK(230, "User logged in").toString();
        }
        return ResponseDirector.singleOK(530, "Login incorrect").toString();
    }
}
