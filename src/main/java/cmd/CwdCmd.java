
package cmd;

import core.Session;
import java.nio.file.Path;

public class CwdCmd extends AbstractCommand {
    public CwdCmd(String args){ super(args); }
    @Override public String name(){ return "CWD"; }
    @Override protected String doExec(Session s){
        String p = args.isBlank()? "." : args.trim();
        return s.changeDir(Path.of(p)) ? "250 Directory changed" : "550 Failed to change directory";
    }
}
