package cmd;

import core.Session;
import protocol.ResponseDirector;

public class PwdCmd extends AbstractCommand {

    public PwdCmd(String args) {
        super(args);
    }

    @Override
    public String name() {
        return "PWD";
    }

    @Override
    protected String doExec(Session s) {
        return ResponseDirector.singleOK(257, "\"" + s.cwd() + "\"").toString();
    }
}
