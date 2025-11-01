package cmd;

import core.Session;
import protocol.ResponseDirector;

public final class SnapCmd extends AbstractCommand {

    public SnapCmd(String args) {
        super(args);
    }

    @Override
    public String name() {
        return "SNAP";
    }

    @Override
    protected String doExec(Session s) {
        s.snapshot();
        return ResponseDirector.singleOK(200, "Snapshot saved").toString();
    }
}
