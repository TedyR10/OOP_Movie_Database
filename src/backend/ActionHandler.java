package backend;

import com.fasterxml.jackson.databind.node.ArrayNode;
import frontend.ChangePage;
import frontend.OnPage;
import visitor.PageVisitor;
import input.ActionsInput;
import session.Session;
import singleton.MoviesDatabase;
import singleton.UsersDatabase;

import java.util.Objects;

/**
 * This class handles actions and can be easily extended in part 2
 */
public final class ActionHandler {

    /**
     * This method handles actions & calls the pageVisitor
     * @param action action
     * @param session current session information
     * @param usersDatabase users database
     * @param moviesDatabase movies database
     * @param output arraynode output
     */
    public void handle(final ActionsInput action, final Session session,
                       final UsersDatabase usersDatabase, final MoviesDatabase moviesDatabase,
                       final ArrayNode output) {
        if (Objects.equals(action.getType(), "change page")) {
            ChangePage changePage = new ChangePage();
            changePage.accept(new PageVisitor(),
                    action, session, usersDatabase, moviesDatabase, output);
        } else if (Objects.equals(action.getType(), "on page")) {
            OnPage onPage = new OnPage();
            onPage.accept(new PageVisitor(),
                    action, session, usersDatabase, moviesDatabase, output);
        }
    }
}
