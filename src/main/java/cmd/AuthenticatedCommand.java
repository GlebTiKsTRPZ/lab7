package cmd;

import core.Session;
import protocol.ResponseDirector;

public abstract class AuthenticatedCommand extends AbstractCommand {

    protected AuthenticatedCommand(String args) {
        super(args);
    }

    @Override
    protected String validate(Session s) throws Exception {
        if (!s.isAuthed()) {
            return ResponseDirector.singleOK(530, "Login required").toString();
        }
        return null;
    }
}
