package frontend;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionsInput;
import session.Session;
import singleton.MoviesDatabase;
import singleton.UsersDatabase;
import visitor.PageVisitor;
import visitor.Visitable;

/**
 * This class is used when adding or removing a movie from the database
 */
public class DatabasePage implements Visitable {
    public DatabasePage() {
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
