package cmd;

public final class CommandManager {

    public static Command of(String name, String args) {
        return switch (name.toUpperCase()) {
            case "USER" ->
                new UserCmd(args);
            case "PASS" ->
                new PassCmd(args);
            case "PWD" ->
                new PwdCmd(args);
            case "CWD" ->
                new CwdCmd(args);
            case "LIST" ->
                new ListCmd(args);
            case "QUIT" ->
                new QuitCmd(args);
            case "UNDO" ->
                new UndoCmd(args);
            case "SNAP" ->
                new SnapCmd(args);
            default ->
                new UnknownCmd(name);
        };
    }
}
