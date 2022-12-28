package visitor;

import com.fasterxml.jackson.databind.node.ArrayNode;
import frontend.BackPage;
import frontend.ChangePage;
import frontend.DatabasePage;
import frontend.ErrorPage;
import frontend.FilterPage;
import frontend.LoginPage;
import frontend.LogoutPage;
import frontend.OnPage;
import frontend.RegisterPage;
import frontend.SearchPage;
import frontend.SeeDetailsPage;
import frontend.SubscribePage;
import frontend.UpgradesPage;
import input.ActionsInput;
import session.Session;
import singleton.MoviesDatabase;
import singleton.UsersDatabase;

public interface Visitor {
    /**
     * visit change page and apply changes
     * @param changePage
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    void visit(ChangePage changePage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);

    /**
     * visit logout page and apply changes
     * @param logoutPage
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    void visit(LogoutPage logoutPage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);

    /**
     * visit onpage page and apply changes
     * @param onPage
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    void visit(OnPage onPage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);

    /**
     * visit login page and apply changes
     * @param loginPage
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    void visit(LoginPage loginPage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);

    /**
     * visit register page and apply changes
     * @param registerPage
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    void visit(RegisterPage registerPage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);

    /**
     * visit search page and apply changes
     * @param searchPage
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    void visit(SearchPage searchPage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);

    /**
     * visit search page and apply changes
     * @param filterPage
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    void visit(FilterPage filterPage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);

    /**
     * visit search page and apply changes
     * @param upgradesPage
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    void visit(UpgradesPage upgradesPage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);

    /**
     * visit search page and apply changes
     * @param seeDetailsPage
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    void visit(SeeDetailsPage seeDetailsPage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);

    /**
     * visit search page and apply changes
     * @param errorPage
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    void visit(ErrorPage errorPage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);

    /**
     * visit back page and apply changes
     * @param backPage
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    void visit(BackPage backPage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);

    /**
     * visit subscribe page and apply changes
     * @param subscribePage
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    void visit(SubscribePage subscribePage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);

    /**
     * visit database page and apply changes
     * @param databasePage
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    void visit(DatabasePage databasePage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);
}
