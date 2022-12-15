package visitor;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionsInput;
import session.Session;
import singleton.MoviesDatabase;
import singleton.UsersDatabase;

public interface Visitable {
    /**
     * accept page
     * @param v
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    void accept(PageVisitor v, ActionsInput action, Session session,
                UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);
}
