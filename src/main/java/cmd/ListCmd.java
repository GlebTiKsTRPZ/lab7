package cmd;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import core.Session;
import protocol.ResponseDirector;

public class ListCmd extends AbstractCommand {

    public ListCmd(String args) {
        super(args);
    }

    @Override
    public String name() {
        return "LIST";
    }

    @Override
    protected String validate(Session s) {
        return s.isAuthed()
                ? null
                : ResponseDirector.singleOK(530, "Please login").toString();
    }

    @Override
    protected String doExec(Session s) {
        try {
            List<String> lines = Files.list(s.cwd())
                    .map(p -> {
                        try {
                            return (Files.isDirectory(p) ? "d" : "-") + " " + p.getFileName() + " " + Files.size(p);
                        } catch (IOException e) {
                            return "? " + p.getFileName();
                        }
                    }).collect(Collectors.toList());
            return ResponseDirector.listing(226, "Listing follows", lines, "End").toString();
        } catch (IOException e) {
            return ResponseDirector.singleOK(451, "Local error").toString();
        }
    }
}
