package visitor;

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
import input.MovieInput;
import strategy.Filters;
import backend.Constants;
import backend.Movie;
import factory.OutputGenerator;
import singleton.MoviesDatabase;
import backend.Search;
import backend.User;
import singleton.UsersDatabase;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionsInput;
import session.Session;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class is used for visiting purposes. Does most of the things
 */
public class PageVisitor implements Visitor {

    /**
     * This method is used for changing pages
     */
    @Override
    public void visit(final ChangePage changePage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {

        // Checks if the current session has a user logged in
        if (!session.isLogin()) {

            // Checks which page we are currently on
            if (Objects.equals(session.getCurrentPage(),
                    "homepage neautentificat")) {

                // Changes page accordingly from "homepage neautentificat"
                if (Objects.equals(action.getPage(), "login")) {
                    session.setCurrentPage("login");
                } else if (Objects.equals(action.getPage(), "register")) {
                    session.setCurrentPage("register");
                } else {
                    // Display error if the page is not available
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                }
            }
        } else {

            // Checks which page we are currently on
            if (Objects.equals(session.getCurrentPage(),
                    "homepage autentificat")) {

                // Changes page accordingly from "homepage autentificat"
                if (Objects.equals(action.getPage(), "logout")) {
                    LogoutPage logoutPage = new LogoutPage();
                    logoutPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                } else if (Objects.equals(action.getPage(), "movies")) {
                    moviesDatabase.getMoviesUser(session.getCurrentUser());
                    session.setCurrentPage("movies");
                    session.getPreviousPages().add("homepage autentificat");
                    OutputGenerator outputGenerator = new OutputGenerator("Movies",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());
                    output.add(outputGenerator.outputCreator().deepCopy());
                } else if (Objects.equals(action.getPage(), "upgrades")) {
                    session.setCurrentPage("upgrades");
                    session.getPreviousPages().add("homepage autentificat");
                } else {
                    // Display error if the page is not available
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                }
            } else if (Objects.equals(session.getCurrentPage(),
                    "movies")) {

                // Changes page accordingly from "movies"
                if (Objects.equals(action.getPage(), "see details")) {

                    // Checks if the movie can be accessed
                    boolean found = false;
                    for (Movie movie : session.getCurrentUser().getCurrentMoviesList()) {
                        if (Objects.equals(movie.getName(), action.getMovie())) {
                            session.setCurrentMovie(moviesDatabase.getMovie(action.getMovie()));
                            session.setCurrentPage("see details");
                            session.getPreviousPages().add("movies");
                            OutputGenerator outputGenerator = new OutputGenerator("Details",
                                    session.getCurrentUser(),
                                    session.getCurrentUser().getCurrentMoviesList(),
                                    session.getCurrentMovie());
                            output.add(outputGenerator.outputCreator().deepCopy());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        // Display error if the movie cannot be accessed
                        ErrorPage errorPage = new ErrorPage();
                        errorPage.accept(new PageVisitor(), action, session,
                                usersDatabase, moviesDatabase, output);
                    }
                } else if (Objects.equals(action.getPage(), "logout")) {
                    LogoutPage logoutPage = new LogoutPage();
                    logoutPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                } else if (Objects.equals(action.getPage(), "movies")) {
                    moviesDatabase.getMoviesUser(session.getCurrentUser());
                    session.setCurrentPage("movies");
                    session.getPreviousPages().add("movies");
                    OutputGenerator outputGenerator = new OutputGenerator("Movies",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());
                    output.add(outputGenerator.outputCreator().deepCopy());
                } else {
                    // Display error if the page is not available
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                }
            } else if (Objects.equals(session.getCurrentPage(),
                    "upgrades")) {

                // Changes page accordingly from "upgrades"
                if (Objects.equals(action.getPage(), "logout")) {
                    LogoutPage logoutPage = new LogoutPage();
                    logoutPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                } else if (Objects.equals(action.getPage(), "movies")) {
                    moviesDatabase.getMoviesUser(session.getCurrentUser());
                    session.setCurrentPage("movies");
                    session.getPreviousPages().add("upgrades");
                    OutputGenerator outputGenerator = new OutputGenerator("Movies",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());
                    output.add(outputGenerator.outputCreator().deepCopy());
                } else {
                    // Display error if the page is not available
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                }
            } else if (Objects.equals(session.getCurrentPage(),
                    "see details")) {

                // Changes page accordingly from "see details"
                if (Objects.equals(action.getPage(), "movies")) {
                    moviesDatabase.getMoviesUser(session.getCurrentUser());
                    session.setCurrentMovie(null);
                    session.setCurrentPage("movies");
                    session.getPreviousPages().add("see details");
                    OutputGenerator outputGenerator = new OutputGenerator("Movies",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());
                    output.add(outputGenerator.outputCreator().deepCopy());
                } else if (Objects.equals(action.getPage(), "logout")) {
                    LogoutPage logoutPage = new LogoutPage();
                    logoutPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                } else {
                    // Display error if the page is not available
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                }
            }
        }
    }

    /**
     * This method is used for logging out
     */
    @Override
    public void visit(final LogoutPage logoutPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {
        session.setCurrentPage("homepage neautentificat");
        session.setLogin(false);
        session.setCurrentUser(null);
        session.setCurrentMovie(null);
        session.getPreviousPages().clear();
    }

    /**
     * This method is used for on page actions
     */
    @Override
    public void visit(final OnPage onPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {

        // Checks the current page & on page action and does whatever action needed
        if (Objects.equals(action.getFeature(), "login")
                && Objects.equals(session.getCurrentPage(), "login")) {
            LoginPage loginPage = new LoginPage();
            loginPage.accept(new PageVisitor(), action, session,
                    usersDatabase, moviesDatabase, output);
        } else if (Objects.equals(action.getFeature(), "register")
                && Objects.equals(session.getCurrentPage(), "register")) {
            RegisterPage registerPage = new RegisterPage();
            registerPage.accept(new PageVisitor(), action, session,
                    usersDatabase, moviesDatabase, output);
        } else if (Objects.equals(action.getFeature(), "search")
                && Objects.equals(session.getCurrentPage(), "movies")) {
            SearchPage searchPage = new SearchPage();
            searchPage.accept(new PageVisitor(), action, session,
                    usersDatabase, moviesDatabase, output);
        } else if (Objects.equals(action.getFeature(), "filter")
                && Objects.equals(session.getCurrentPage(), "movies")) {
            FilterPage filterPage = new FilterPage();
            filterPage.accept(new PageVisitor(), action, session,
                    usersDatabase, moviesDatabase, output);
        } else if (Objects.equals(session.getCurrentPage(), "upgrades")) {
            UpgradesPage upgradesPage = new UpgradesPage();
            upgradesPage.accept(new PageVisitor(), action, session,
                    usersDatabase, moviesDatabase, output);
        } else if (Objects.equals(session.getCurrentPage(), "see details")) {
            SeeDetailsPage seeDetailsPage = new SeeDetailsPage();
            seeDetailsPage.accept(new PageVisitor(), action, session,
                    usersDatabase, moviesDatabase, output);
        } else {
            // Display error if the action is unavailable
            ErrorPage errorPage = new ErrorPage();
            errorPage.accept(new PageVisitor(), action, session,
                    usersDatabase, moviesDatabase, output);
        }
    }

    /**
     * This method is used for logging in
     */
    @Override
    public void visit(final LoginPage loginPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {

        // Create a new user with the given credentials
        User checkUser = new User(action.getCredentials().getName(),
                action.getCredentials().getPassword(),
                null, null, 0);

        // Checks if the given user exists and proceeds to log in
        if (usersDatabase.checkUser(checkUser)) {
            session.setCurrentUser(usersDatabase.getUser(checkUser));
            session.setLogin(true);
            session.setCurrentPage("homepage autentificat");
            User outUser = usersDatabase.getUser(checkUser);
            OutputGenerator outputGenerator = new OutputGenerator("User", outUser,
                    outUser.getCurrentMoviesList(),
                    session.getCurrentMovie());
            output.add(outputGenerator.outputCreator().deepCopy());
        } else {
            // Display error if the credentials are invalid
            ErrorPage errorPage = new ErrorPage();
            errorPage.accept(new PageVisitor(), action, session,
                    usersDatabase, moviesDatabase, output);
            session.setCurrentPage("homepage neautentificat");
        }
    }

    /**
     * This method is used for registering
     */
    @Override
    public void visit(final RegisterPage registerPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {

        // Create a new user with the given credentials
        User checkUser = new User(action.getCredentials().getName(),
                action.getCredentials().getPassword(),
                action.getCredentials().getAccountType(), action.getCredentials().getCountry(),
                action.getCredentials().getBalance());

        // Checks if the user already exists
        if (usersDatabase.checkUser(checkUser)) {
            // Display error if the user already exists
            ErrorPage errorPage = new ErrorPage();
            errorPage.accept(new PageVisitor(), action, session,
                    usersDatabase, moviesDatabase, output);
            session.setCurrentPage("homepage neautentificat");
        } else {
            // Adds the user into the database and logs him in
            usersDatabase.addUser(checkUser, moviesDatabase);
            session.setCurrentPage("homepage autentificat");
            session.setLogin(true);
            User newUser = usersDatabase.getUser(checkUser);
            session.setCurrentUser(newUser);
            OutputGenerator outputGenerator = new OutputGenerator("User", checkUser,
                    checkUser.getCurrentMoviesList(),
                    session.getCurrentMovie());
            output.add(outputGenerator.outputCreator().deepCopy());
        }
    }

    /**
     * This method is used for searching
     */
    @Override
    public void visit(final SearchPage searchPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {

        // Gets the available movies for the current user and performs the search action
        moviesDatabase.getMoviesUser(session.getCurrentUser());
        ArrayList<Movie> searchMovies =
                new Search().searchMovies(session.getCurrentUser().getCurrentMoviesList(),
                        action.getStartsWith());
        session.getCurrentUser().setCurrentMoviesList(searchMovies);
        OutputGenerator outputGenerator =
                new OutputGenerator("Movies", session.getCurrentUser(),
                        session.getCurrentUser().getCurrentMoviesList(), session.getCurrentMovie());
        output.add(outputGenerator.outputCreator().deepCopy());
    }

    /**
     * This method is used for filtering
     */
    @Override
    public void visit(final FilterPage filterPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {

        // Gets the available movies for the current user and performs the filter action
        moviesDatabase.getMoviesUser(session.getCurrentUser());
        ArrayList<Movie> filterMovies =
                new Filters().filterMovies(session.getCurrentUser().getCurrentMoviesList(), action);
        session.getCurrentUser().setCurrentMoviesList(filterMovies);
        OutputGenerator outputGenerator =
                new OutputGenerator("Movies", session.getCurrentUser(),
                        session.getCurrentUser().getCurrentMoviesList(),
                        session.getCurrentMovie());
        output.add(outputGenerator.outputCreator().deepCopy());
    }

    /**
     * This method is used for upgrades
     */
    @Override
    public void visit(final UpgradesPage upgradesPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {

        // Check for the required action
        if (Objects.equals(action.getFeature(), "buy tokens")) {

            // Check if the current user has enough balance to buy tokens
            if (session.getCurrentUser().getBalance() >= Integer.parseInt(action.getCount())) {
                session.getCurrentUser().setTokens(session.getCurrentUser().getTokens()
                        + Integer.parseInt(action.getCount()));
                session.getCurrentUser().setBalance(session.getCurrentUser().getBalance()
                        - Integer.parseInt(action.getCount()));
            } else {
                // Display error if the user doesn't have enough balance
                ErrorPage errorPage = new ErrorPage();
                errorPage.accept(new PageVisitor(), action, session,
                        usersDatabase, moviesDatabase, output);
                session.setCurrentPage("homepage neautentificat");
            }
        } else if (Objects.equals(action.getFeature(), "buy premium account")) {

            // Check if the current user has enough tokens to buy a premium account
            if (session.getCurrentUser().getTokens()
                    >= Constants.MIN_TOKENS_PREMIUM_ACCOUNT) {
                session.getCurrentUser().setTokens(
                        session.getCurrentUser().getTokens()
                                - Constants.MIN_TOKENS_PREMIUM_ACCOUNT);
                session.getCurrentUser().setAccountType("premium");
            } else {
                // Display error if the user doesn't have enough tokens
                ErrorPage errorPage = new ErrorPage();
                errorPage.accept(new PageVisitor(), action, session,
                        usersDatabase, moviesDatabase, output);
                session.setCurrentPage("homepage neautentificat");
            }
        }
    }

    /**
     * This method is used for seeDetails actions
     */
    @Override
    public void visit(final SeeDetailsPage seeDetailsPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {

        // Check for the required action
        if (Objects.equals(action.getFeature(), "purchase")) {

            if (session.getCurrentUser().checkPurchase(
                    session.getCurrentMovie().getName())) {
                // Display error if the user has already purchased the movie
                ErrorPage errorPage = new ErrorPage();
                errorPage.accept(new PageVisitor(), action, session,
                        usersDatabase, moviesDatabase, output);
            } else {
                // Check which account type the current user has
                if (Objects.equals(session.getCurrentUser().getAccountType(), "premium")) {

                    // Check if he has any free premium movies left
                    if (session.getCurrentUser().getNumFreePremiumMovies() > 0) {
                        session.getCurrentUser().setNumFreePremiumMovies(
                                session.getCurrentUser().getNumFreePremiumMovies() - 1);
                        session.getCurrentUser().getPurchasedMovies()
                                .add(session.getCurrentMovie());
                        OutputGenerator outputGenerator =
                                new OutputGenerator("Details", session.getCurrentUser(),
                                        session.getCurrentUser().getCurrentMoviesList(),
                                        session.getCurrentMovie());
                        output.add(outputGenerator.outputCreator().deepCopy());
                    } else {
                        // Check if he has enough tokens to purchase the movie
                        if (session.getCurrentUser().getTokens()
                                >= Constants.MIN_TOKENS_MOVIE) {
                            session.getCurrentUser().setTokens(
                                    session.getCurrentUser().getTokens()
                                            - Constants.MIN_TOKENS_MOVIE);
                            session.getCurrentUser().getPurchasedMovies()
                                    .add(session.getCurrentMovie());
                            OutputGenerator outputGenerator =
                                    new OutputGenerator("Details", session.getCurrentUser(),
                                            session.getCurrentUser().getCurrentMoviesList(),
                                            session.getCurrentMovie());
                            output.add(outputGenerator.outputCreator().deepCopy());
                        } else {
                            // Display error if the user doesn't have enough tokens
                            ErrorPage errorPage = new ErrorPage();
                            errorPage.accept(new PageVisitor(), action, session,
                                    usersDatabase, moviesDatabase, output);
                        }
                    }
                } else {
                    // Check if he has enough tokens to purchase the movie
                    if (session.getCurrentUser().getTokens()
                            >= Constants.MIN_TOKENS_MOVIE) {
                        session.getCurrentUser().setTokens(
                                session.getCurrentUser().getTokens()
                                        - Constants.MIN_TOKENS_MOVIE);
                        session.getCurrentUser().getPurchasedMovies()
                                .add(session.getCurrentMovie());
                        OutputGenerator outputGenerator =
                                new OutputGenerator("Details", session.getCurrentUser(),
                                        session.getCurrentUser().getCurrentMoviesList(),
                                        session.getCurrentMovie());
                        output.add(outputGenerator.outputCreator().deepCopy());
                    } else {
                        // Display error if the user doesn't have enough tokens
                        ErrorPage errorPage = new ErrorPage();
                        errorPage.accept(new PageVisitor(), action, session,
                                usersDatabase, moviesDatabase, output);
                    }
                }
            }
        } else if (Objects.equals(action.getFeature(), "watch")) {
            if (!(session.getCurrentUser().checkWatch(
                    session.getCurrentMovie().getName()))) {
                // Check if the user has purchased the movie he wants to watch
                if (session.getCurrentUser().checkPurchase(
                        session.getCurrentMovie().getName())) {
                    session.getCurrentUser().getWatchedMovies()
                            .add(session.getCurrentMovie());
                    OutputGenerator outputGenerator =
                            new OutputGenerator("Details", session.getCurrentUser(),
                                    session.getCurrentUser().getCurrentMoviesList(),
                                    session.getCurrentMovie());
                    output.add(outputGenerator.outputCreator().deepCopy());
                } else {
                    // Display error if the user hasn't purchased the movie yet
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                }
            }
        } else if (Objects.equals(action.getFeature(), "like")) {
            // Check if the user has watched the movie he wants to like
            if (session.getCurrentUser().checkWatch(
                    session.getCurrentMovie().getName())) {
                session.getCurrentMovie().setNumLikes(
                        session.getCurrentMovie().getNumLikes() + 1);
                session.getCurrentUser().getLikedMovies()
                        .add(session.getCurrentMovie());
                session.getCurrentUser().updateLikedGenres(session.getCurrentMovie().getGenres());
                OutputGenerator outputGenerator =
                        new OutputGenerator("Details", session.getCurrentUser(),
                                session.getCurrentUser().getCurrentMoviesList(),
                                session.getCurrentMovie());
                output.add(outputGenerator.outputCreator().deepCopy());
            } else {
                // Display error if the user hasn't watched the movie yet
                ErrorPage errorPage = new ErrorPage();
                errorPage.accept(new PageVisitor(), action, session,
                        usersDatabase, moviesDatabase, output);
            }
        } else if (Objects.equals(action.getFeature(), "rate")) {
            // Check if the user has watched the movie he wants to rate
            if (session.getCurrentUser().checkWatch(
                    session.getCurrentMovie().getName())
                    && Integer.parseInt(action.getRate()) <= Constants.MAX_RATING) {
                if (session.getCurrentMovie().hasRated(session.getCurrentUser())) {
                    session.getCurrentMovie().getRatings().put(
                            session.getCurrentUser(),
                            Integer.parseInt(action.getRate()));
                    session.getCurrentMovie().setNewRating();
                } else {
                    session.getCurrentMovie().setNumRatings(
                            session.getCurrentMovie().getNumRatings() + 1);
                    session.getCurrentMovie().getRatings().put(
                            session.getCurrentUser(),
                            Integer.parseInt(action.getRate()));
                    session.getCurrentMovie().setNewRating();
                    session.getCurrentUser().getRatedMovies()
                            .add(session.getCurrentMovie());
                }
                OutputGenerator outputGenerator =
                        new OutputGenerator("Details", session.getCurrentUser(),
                                session.getCurrentUser().getCurrentMoviesList(),
                                session.getCurrentMovie());
                output.add(outputGenerator.outputCreator().deepCopy());
            } else {
                // Display error if the user hasn't watched the movie yet
                ErrorPage errorPage = new ErrorPage();
                errorPage.accept(new PageVisitor(), action, session,
                        usersDatabase, moviesDatabase, output);
            }
        }
    }

    /**
     * This method is used for handling errors
     */
    @Override
    public void visit(final ErrorPage errorPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {
        OutputGenerator outputGenerator;

        // Check if a user is logged in
        if (session.isLogin()) {
            outputGenerator = new OutputGenerator("General", session.getCurrentUser(),
                    session.getCurrentUser().getCurrentMoviesList(),
                    session.getCurrentMovie());
        } else {
            outputGenerator = new OutputGenerator("General",
                    null, null, null);
        }
        output.add(outputGenerator.outputCreator().deepCopy());
    }

    /**
     * This method is used for changing back to the previous page
     * @param backPage backPage
     * @param action action
     * @param session session
     * @param usersDatabase database
     * @param moviesDatabase database
     * @param output output
     */
    @Override
    public void visit(final BackPage backPage, final ActionsInput action, final Session session,
                      final UsersDatabase usersDatabase, final MoviesDatabase moviesDatabase,
                      final ArrayNode output) {
        if (session.isLogin()) {
            if (session.getPreviousPages().size() == 0) {
                // Display error if the command is not available
                ErrorPage errorPage = new ErrorPage();
                errorPage.accept(new PageVisitor(), action, session,
                        usersDatabase, moviesDatabase, output);
            } else {
                String previousPage = session.getPreviousPages()
                        .get(session.getPreviousPages().size() - 1);
                switch (previousPage) {
                    case "homepage autentificat", "upgrades":
                        session.setCurrentPage(previousPage);
                        session.getPreviousPages().remove(
                                session.getPreviousPages().size() - 1);
                        break;
                    case "movies":
                        moviesDatabase.getMoviesUser(session.getCurrentUser());
                        session.setCurrentPage(previousPage);
                        session.getPreviousPages().remove(
                                session.getPreviousPages().size() - 1);
                        OutputGenerator outputGenerator = new OutputGenerator("Movies",
                                session.getCurrentUser(),
                                session.getCurrentUser().getCurrentMoviesList(),
                                session.getCurrentMovie());
                        output.add(outputGenerator.outputCreator().deepCopy());
                        break;
                    case "see details":
                        session.setCurrentMovie(moviesDatabase.getMovie(action.getMovie()));
                        session.setCurrentPage(previousPage);
                        session.getPreviousPages().remove(
                                session.getPreviousPages().size() - 1);
                        outputGenerator = new OutputGenerator("Details",
                                session.getCurrentUser(),
                                session.getCurrentUser().getCurrentMoviesList(),
                                session.getCurrentMovie());
                        output.add(outputGenerator.outputCreator().deepCopy());
                        break;
                    default:
                        // Display error if the command is not available
                        ErrorPage errorPage = new ErrorPage();
                        errorPage.accept(new PageVisitor(), action, session,
                                usersDatabase, moviesDatabase, output);
                        break;
                }

            }
        } else {
            // Display error if the command is not available
            ErrorPage errorPage = new ErrorPage();
            errorPage.accept(new PageVisitor(), action, session,
                    usersDatabase, moviesDatabase, output);
        }
    }

    /**
     * This method is used for subscriptions
     * @param subscribePage subscribePage
     * @param action action
     * @param session session
     * @param usersDatabase database
     * @param moviesDatabase database
     * @param output output
     */
    @Override
    public void visit(final SubscribePage subscribePage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {
        if (Objects.equals(session.getCurrentPage(), "see details")) {
            if (session.getCurrentMovie().hasGenre(action.getSubscribedGenre())
                    && !(session.getCurrentUser().checkSubscription(action.getSubscribedGenre()))) {
                session.getCurrentUser().getSubscribedGenres().add(action.getSubscribedGenre());
            } else {
                // Display error if the command is not available
                ErrorPage errorPage = new ErrorPage();
                errorPage.accept(new PageVisitor(), action, session,
                        usersDatabase, moviesDatabase, output);
            }
        } else {
            // Display error if the command is not available
            ErrorPage errorPage = new ErrorPage();
            errorPage.accept(new PageVisitor(), action, session,
                    usersDatabase, moviesDatabase, output);
        }
    }

    /**
     * This method is used for database operations
     * @param databasePage
     * @param action
     * @param session
     * @param usersDatabase
     * @param moviesDatabase
     * @param output
     */
    @Override
    public void visit(final DatabasePage databasePage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {
        if (Objects.equals(action.getFeature(), "add")) {
            if (moviesDatabase.checkMovie(action.getAddedMovie().getName())) {
                // Display error if the movie is already in the database
                ErrorPage errorPage = new ErrorPage();
                errorPage.accept(new PageVisitor(), action, session,
                        usersDatabase, moviesDatabase, output);
            } else {
                MovieInput addMovie = action.getAddedMovie();
                moviesDatabase.addMovie(addMovie);
            }
        } else if (Objects.equals(action.getFeature(), "delete")) {
            if (!(moviesDatabase.checkMovie(action.getDeletedMovie()))) {
                // Display error if the movie is already in the database
                ErrorPage errorPage = new ErrorPage();
                errorPage.accept(new PageVisitor(), action, session,
                        usersDatabase, moviesDatabase, output);
            } else {
                String deletedMovie = action.getDeletedMovie();
                moviesDatabase.deleteMovie(deletedMovie);
            }
        }
    }
}
