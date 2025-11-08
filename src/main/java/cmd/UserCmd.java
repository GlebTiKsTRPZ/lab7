package cmd;

import core.Session;
import protocol.ResponseDirector;

public class UserCmd extends PublicCommand {

    public UserCmd(String args) {
        super(args);
    }

    @Override
    public String name() {
        return "USER";
    }

    @Override
    protected String doExec(Session s) {
        s.setPendingUser(args.trim());
        return ResponseDirector.singleOK(331, "Username ok, need password").toString();
    }
}
