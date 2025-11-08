package cmd;

import java.nio.file.Path;

import core.Session;
import protocol.ResponseDirector;

public class CwdCmd extends AbstractFsCommand {

    public CwdCmd(String args) {
        super(args);
    }

    @Override
    public String name() {
        return "CWD";
    }

    @Override
    protected String doOnPath(Session s, Path target) {
        boolean ok = s.changeDir(target);
        return ok
            ? ResponseDirector.singleOK(250, "Directory successfully changed").toString()
            : ResponseDirector.singleOK(550, "Failed to change directory").toString();
    }
}
}
