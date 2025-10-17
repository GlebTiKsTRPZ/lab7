
package cmd;

import core.Session;
import model.Account;

public class PassCmd extends AbstractCommand {
    public PassCmd(String args){ super(args); }
    @Override public String name(){ return "PASS"; }
    @Override protected String doExec(Session s){
        String u = s.getPendingUser();
        if (u == null) return "503 Bad sequence of commands";
        boolean ok = Account.verify(u, args.trim());
        if (ok) { s.setAuthed(true); return "230 User logged in"; }
        return "530 Login incorrect";
    }
}
