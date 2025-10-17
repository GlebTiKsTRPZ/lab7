
package cmd;

import core.Session;

public class UserCmd extends AbstractCommand {
    public UserCmd(String args){ super(args); }
    @Override public String name(){ return "USER"; }
    @Override protected String doExec(Session s){ s.setPendingUser(args.trim()); return "331 Username ok, need password"; }
}
