package cmd;

import core.Session;

public abstract class AbstractCommand implements Command {

    protected final String args;

    protected AbstractCommand(String args) {
        this.args = args == null ? "" : args;
    }

    protected String validate(Session s) throws Exception {
        return null;
    }

    protected abstract String doExec(Session s) throws Exception;

    @Override
    public final String execute(Session s) throws Exception {
        String v = validate(s);
        if (v != null) {
            return v;
        }
        return doExec(s);
    }
}
