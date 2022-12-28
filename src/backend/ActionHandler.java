package backend;

import com.fasterxml.jackson.databind.node.ArrayNode;
import frontend.BackPage;
import frontend.ChangePage;
import frontend.DatabasePage;
import frontend.OnPage;
import frontend.SubscribePage;
import visitor.PageVisitor;
import input.ActionsInput;
import session.Session;
import singleton.MoviesDatabase;
import singleton.UsersDatabase;

import java.util.Objects;

/**
 * This class handles actions
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
        } else if (Objects.equals(action.getType(), "back")) {
            BackPage backPage = new BackPage();
            backPage.accept(new PageVisitor(),
                    action, session, usersDatabase, moviesDatabase, output);
        } else if (Objects.equals(action.getType(), "subscribe")) {
            SubscribePage subscribePage = new SubscribePage();
            subscribePage.accept(new PageVisitor(),
                    action, session, usersDatabase, moviesDatabase, output);
        } else if (Objects.equals(action.getType(), "database")) {
            DatabasePage databasePage = new DatabasePage();
            databasePage.accept(new PageVisitor(),
                    action, session, usersDatabase, moviesDatabase, output);
        }
    }
}
