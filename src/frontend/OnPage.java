package frontend;

import singleton.MoviesDatabase;
import singleton.UsersDatabase;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionsInput;
import session.Session;
import visitor.PageVisitor;
import visitor.Visitable;

/**
 * This class is used for onPage actions
 */
public class OnPage implements Visitable {

    public OnPage() {
    }
    /**
     * Visitor accept method
     */
    public void accept(final PageVisitor v, final ActionsInput action,
                       final Session session, final UsersDatabase usersDatabase,
                       final MoviesDatabase moviesDatabase, final ArrayNode output) {
        v.visit(this, action, session, usersDatabase, moviesDatabase, output);
    }
}
