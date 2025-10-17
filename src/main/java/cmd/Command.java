
package cmd;

import core.Session;

public interface Command {
    String name();
    String execute(Session s) throws Exception;
}
