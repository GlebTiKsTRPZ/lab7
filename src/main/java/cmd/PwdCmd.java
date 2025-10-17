
package cmd;

import core.Session;

public class PwdCmd extends AbstractCommand {
    public PwdCmd(String args){ super(args); }
    @Override public String name(){ return "PWD"; }
    @Override protected String doExec(Session s){ return "257 \"" + s.cwd() + "\""; }
}
