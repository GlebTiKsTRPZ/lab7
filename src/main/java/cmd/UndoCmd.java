package cmd;

import core.Session;
import protocol.ResponseDirector;

public final class UndoCmd extends AbstractCommand {

    public UndoCmd(String args) {
        super(args);
    }

    @Override
    public String name() {
        return "UNDO";
    }

    @Override
    protected String doExec(Session s) {
        boolean ok = s.undo();
        return ok
                ? ResponseDirector.singleOK(200, "Session state restored").toString()
                : ResponseDirector.singleOK(550, "Nothing to undo").toString();
    }
}
