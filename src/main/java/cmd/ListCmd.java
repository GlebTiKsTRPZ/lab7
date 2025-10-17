
package cmd;

import core.Session;
import java.nio.file.*;
import java.io.IOException;
import java.util.stream.Collectors;

public class ListCmd extends AbstractCommand {
    public ListCmd(String args){ super(args); }
    @Override public String name(){ return "LIST"; }
    @Override protected String validate(Session s){ return s.isAuthed() ? null : "530 Please login"; }
    @Override protected String doExec(Session s) {
        try {
            String listing = Files.list(s.cwd())
                    .map(p -> {
                        try { return (Files.isDirectory(p)? "d":"-") + " " + p.getFileName() + " " + Files.size(p); }
                        catch (IOException e){ return "? " + p.getFileName(); }
                    })
                    .collect(Collectors.joining("\r\n"));
            return "226-Listing follows\r\n" + listing + "\r\n226 End";
        } catch (IOException e){
            return "451 Local error";
        }
    }
}
