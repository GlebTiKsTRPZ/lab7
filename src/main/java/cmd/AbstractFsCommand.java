package cmd;

import java.nio.file.Path;

import core.Session;
import protocol.ResponseDirector;

public abstract class AbstractFsCommand extends AuthenticatedCommand {

    protected AbstractFsCommand(String args) { super(args); }

    @Override
    protected final String doExec(Session s) throws Exception {
        Path target = resolveTarget(s, args);
        if (target == null) {
            return ResponseDirector.singleOK(550, "Invalid path").toString();
        }
        return doOnPath(s, target);
    }

    protected abstract String doOnPath(Session s, Path target) throws Exception;

    protected Path resolveTarget(Session s, String raw) {
        String p = (raw == null || raw.isBlank()) ? "." : raw.trim();
        try {
            Path np = s.cwd().resolve(Path.of(p)).normalize();
            return np;
        } catch (Exception e) {
            return null;
        }
    }
}
