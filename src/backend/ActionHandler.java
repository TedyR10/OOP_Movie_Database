package backend;

import com.fasterxml.jackson.databind.node.ArrayNode;
import frontend.ChangePage;
import frontend.OnPage;
import frontend.PageVisitor;
import input.ActionsInput;
import session.Session;

import java.util.Objects;

public final class ActionHandler {
    /**
     *
     * @param action action type
     */
    public void handle(final ActionsInput action, final Session session,
                       final UsersDatabase usersDatabase, final MoviesDatabase moviesDatabase,
                       final ArrayNode output) {
        PageVisitor visitor = new PageVisitor();
        if (Objects.equals(action.getType(), "change page")) {
            ChangePage changePage = new ChangePage();
            changePage.accept(visitor, action, session, usersDatabase, moviesDatabase, output);
        } else if (Objects.equals(action.getType(), "on page")) {
            OnPage onPage = new OnPage();
            onPage.accept(visitor, action, session, usersDatabase, moviesDatabase, output);
        }
    }
}
