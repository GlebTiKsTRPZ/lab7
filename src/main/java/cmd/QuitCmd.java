package cmd;

import core.Session;
import protocol.ResponseDirector;

public class QuitCmd extends AbstractCommand {

    public QuitCmd(String args) {
        super(args);
    }

    @Override
    public String name() {
        return "QUIT";
    }

    @Override
    protected String doExec(Session s) {
        s.setQuit(true);
        return ResponseDirector.singleOK(221, "Goodbye").toString();
    }
}
