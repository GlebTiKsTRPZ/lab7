package cmd;

import core.Session;
import protocol.ResponseDirector;

public class UnknownCmd extends AbstractCommand {

    private final String n;

    public UnknownCmd(String n) {
        super("");
        this.n = n;
    }

    @Override
    public String name() {
        return n;
    }

    @Override
    protected String doExec(Session s) {
        return ResponseDirector.singleOK(502, "Command not implemented").toString();
    }
}
