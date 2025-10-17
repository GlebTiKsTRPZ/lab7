
package cmd;

import core.Session;

public class QuitCmd extends AbstractCommand {
    public QuitCmd(String args){ super(args); }
    @Override public String name(){ return "QUIT"; }
    @Override protected String doExec(Session s){ s.setQuit(true); return "221 Goodbye"; }
}
