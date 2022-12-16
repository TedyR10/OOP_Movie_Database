package frontend;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionsInput;
import session.Session;
import singleton.MoviesDatabase;
import singleton.UsersDatabase;
import visitor.PageVisitor;
import visitor.Visitable;

/**
 * This class is used for filtering movies
 */
public class FilterPage implements Visitable {

    public FilterPage() {
    }
    /**
     * Visitor accept method
     */
    @Override
    public void accept(final PageVisitor v, final ActionsInput action,
                       final Session session, final UsersDatabase usersDatabase,
                       final MoviesDatabase moviesDatabase, final ArrayNode output) {
        v.visit(this, action, session, usersDatabase, moviesDatabase, output);
    }
}
