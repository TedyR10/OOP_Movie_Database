package visitor;

import backend.Filters;
import backend.MagicNumbers;
import backend.Movie;
import factory.OutputGenerator;
import frontend.ChangePage;
import frontend.ErrorPage;
import frontend.FilterPage;
import frontend.LogoutPage;
import frontend.LoginPage;
import frontend.RegisterPage;
import frontend.SearchPage;
import frontend.OnPage;
import frontend.SeeDetailsPage;
import frontend.UpgradesPage;
import singleton.MoviesDatabase;
import backend.Search;
import backend.User;
import singleton.UsersDatabase;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionsInput;
import session.Session;

import java.util.ArrayList;
import java.util.Objects;

public class PageVisitor implements Visitor {
    /**
     * change page visitor
     */
    @Override
    public void visit(final ChangePage changePage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {
        if (!session.isLogin()) {
            if (Objects.equals(session.getCurrentPage(),
                    "homepage neautentificat")) {
                if (Objects.equals(action.getPage(), "login")) {
                    session.setCurrentPage("login");
                } else if (Objects.equals(action.getPage(), "register")) {
                    session.setCurrentPage("register");
                } else {
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                }
            }
        } else {
            if (Objects.equals(session.getCurrentPage(),
                    "homepage autentificat")) {
                if (Objects.equals(action.getPage(), "logout")) {
                    LogoutPage logoutPage = new LogoutPage();
                    logoutPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                } else if (Objects.equals(action.getPage(), "movies")) {
                    moviesDatabase.getMoviesUser(session.getCurrentUser());
                    session.setCurrentPage("movies");
                    OutputGenerator outputGenerator = new OutputGenerator("Movies",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());

                    output.add(outputGenerator.outputCreator().deepCopy());
                } else if (Objects.equals(action.getPage(), "upgrades")) {
                    session.setCurrentPage("upgrades");
                } else {
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                }
            } else if (Objects.equals(session.getCurrentPage(),
                    "movies")) {
                if (Objects.equals(action.getPage(), "see details")) {
                    boolean found = false;
                    for (Movie movie : session.getCurrentUser().getCurrentMoviesList()) {
                        if (Objects.equals(movie.getName(), action.getMovie())) {
                            session.setCurrentMovie(moviesDatabase.getMovie(action.getMovie()));
                            session.setCurrentPage("see details");
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
                    OutputGenerator outputGenerator = new OutputGenerator("Movies",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());

                    output.add(outputGenerator.outputCreator().deepCopy());
                } else {
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                }
            } else if (Objects.equals(session.getCurrentPage(),
                    "upgrades")) {
                if (Objects.equals(action.getPage(), "logout")) {
                    LogoutPage logoutPage = new LogoutPage();
                    logoutPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                } else if (Objects.equals(action.getPage(), "movies")) {
                    moviesDatabase.getMoviesUser(session.getCurrentUser());
                    session.setCurrentPage("movies");
                    OutputGenerator outputGenerator = new OutputGenerator("Movies",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());
                    output.add(outputGenerator.outputCreator().deepCopy());
                } else {
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                }
            } else if (Objects.equals(session.getCurrentPage(),
                    "see details")) {
                if (Objects.equals(action.getPage(), "movies")) {
                    moviesDatabase.getMoviesUser(session.getCurrentUser());
                    session.setCurrentMovie(null);
                    session.setCurrentPage("movies");
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
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                }
            }
        }
    }
    /**
     * logout page visitor
     */
    @Override
    public void visit(final LogoutPage logoutPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {
        session.setCurrentPage("homepage neautentificat");
        session.setLogin(false);
        session.setCurrentUser(null);
        session.setCurrentMovie(null);
    }
    /**
     * on page visitor
     */
    @Override
    public void visit(final OnPage onPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {
        // login check
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
            OutputGenerator outputGenerator =
                    new OutputGenerator("General", session.getCurrentUser(),
                    session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());
            output.add(outputGenerator.outputCreator().deepCopy());
        }
    }

    /**
     * login page visitor
     */
    @Override
    public void visit(final LoginPage loginPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {
        User checkUser = new User(action.getCredentials().getName(),
                action.getCredentials().getPassword(),
                null, null, 0);
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
            ErrorPage errorPage = new ErrorPage();
            errorPage.accept(new PageVisitor(), action, session,
                    usersDatabase, moviesDatabase, output);
            session.setCurrentPage("homepage neautentificat");
        }
    }

    /**
     * register page visitor
     */
    @Override
    public void visit(final RegisterPage registerPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {
        User checkUser = new User(action.getCredentials().getName(),
                action.getCredentials().getPassword(),
                action.getCredentials().getAccountType(), action.getCredentials().getCountry(),
                action.getCredentials().getBalance());
        if (usersDatabase.checkUser(checkUser)) {
            ErrorPage errorPage = new ErrorPage();
            errorPage.accept(new PageVisitor(), action, session,
                    usersDatabase, moviesDatabase, output);
            session.setCurrentPage("homepage neautentificat");
        } else {
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
     * search page visitor
     */
    @Override
    public void visit(final SearchPage searchPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {
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
     * filter page visitor
     */
    @Override
    public void visit(final FilterPage filterPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {
        moviesDatabase.getMoviesUser(session.getCurrentUser());
        ArrayList<Movie> filterMovies =
                new Filters().filter(session.getCurrentUser().getCurrentMoviesList(), action);
        session.getCurrentUser().setCurrentMoviesList(filterMovies);
        OutputGenerator outputGenerator =
                new OutputGenerator("Movies", session.getCurrentUser(),
                        session.getCurrentUser().getCurrentMoviesList(),
                        session.getCurrentMovie());
        output.add(outputGenerator.outputCreator().deepCopy());
    }
    /**
     * upgrades page visitor
     */
    @Override
    public void visit(final UpgradesPage upgradesPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {
        if (Objects.equals(action.getFeature(), "buy tokens")) {
            if (session.getCurrentUser().getBalance() >= Integer.parseInt(action.getCount())) {
                session.getCurrentUser().setTokens(Integer.parseInt(action.getCount()));
                session.getCurrentUser().setBalance(session.getCurrentUser().getBalance()
                        - Integer.parseInt(action.getCount()));
            } else {
                ErrorPage errorPage = new ErrorPage();
                errorPage.accept(new PageVisitor(), action, session,
                        usersDatabase, moviesDatabase, output);
                session.setCurrentPage("homepage neautentificat");
            }
        } else if (Objects.equals(action.getFeature(), "buy premium account")) {
            if (session.getCurrentUser().getTokens()
                    >= MagicNumbers.MIN_TOKENS_PREMIUM_ACCOUNT) {
                session.getCurrentUser().setTokens(
                        session.getCurrentUser().getTokens()
                                - MagicNumbers.MIN_TOKENS_PREMIUM_ACCOUNT);
                session.getCurrentUser().setAccountType("premium");
            } else {
                ErrorPage errorPage = new ErrorPage();
                errorPage.accept(new PageVisitor(), action, session,
                        usersDatabase, moviesDatabase, output);
                session.setCurrentPage("homepage neautentificat");
            }
        }
    }
    /**
     * seeDetails page visitor
     */
    @Override
    public void visit(final SeeDetailsPage seeDetailsPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {
        if (Objects.equals(action.getFeature(), "purchase")) {
            if (Objects.equals(session.getCurrentUser().getAccountType(), "premium")) {
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
                    if (session.getCurrentUser().getTokens() >= 2) {
                        session.getCurrentUser().setTokens(
                                session.getCurrentUser().getTokens() - 2);
                        session.getCurrentUser().getPurchasedMovies()
                                .add(session.getCurrentMovie());
                        OutputGenerator outputGenerator =
                                new OutputGenerator("Details", session.getCurrentUser(),
                                        session.getCurrentUser().getCurrentMoviesList(),
                                        session.getCurrentMovie());
                        output.add(outputGenerator.outputCreator().deepCopy());
                    } else {
                        ErrorPage errorPage = new ErrorPage();
                        errorPage.accept(new PageVisitor(), action, session,
                                usersDatabase, moviesDatabase, output);
                    }
                }
            } else {
                if (session.getCurrentUser().getTokens() >= 2) {
                    session.getCurrentUser().setTokens(
                            session.getCurrentUser().getTokens() - 2);
                    session.getCurrentUser().getPurchasedMovies()
                            .add(session.getCurrentMovie());
                    OutputGenerator outputGenerator =
                            new OutputGenerator("Details", session.getCurrentUser(),
                                    session.getCurrentUser().getCurrentMoviesList(),
                                    session.getCurrentMovie());
                    output.add(outputGenerator.outputCreator().deepCopy());
                } else {
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.accept(new PageVisitor(), action, session,
                            usersDatabase, moviesDatabase, output);
                }
            }
        } else if (Objects.equals(action.getFeature(), "watch")) {
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
                ErrorPage errorPage = new ErrorPage();
                errorPage.accept(new PageVisitor(), action, session,
                        usersDatabase, moviesDatabase, output);
            }
        } else if (Objects.equals(action.getFeature(), "like")) {
            if (session.getCurrentUser().checkWatch(
                    session.getCurrentMovie().getName())) {
                session.getCurrentMovie().setNumLikes(
                        session.getCurrentMovie().getNumLikes() + 1);
                session.getCurrentUser().getLikedMovies()
                        .add(session.getCurrentMovie());
                OutputGenerator outputGenerator =
                        new OutputGenerator("Details", session.getCurrentUser(),
                                session.getCurrentUser().getCurrentMoviesList(),
                                session.getCurrentMovie());
                output.add(outputGenerator.outputCreator().deepCopy());
            } else {
                ErrorPage errorPage = new ErrorPage();
                errorPage.accept(new PageVisitor(), action, session,
                        usersDatabase, moviesDatabase, output);
            }
        } else if (Objects.equals(action.getFeature(), "rate")) {
            if (session.getCurrentUser().checkWatch(
                    session.getCurrentMovie().getName())
                    && Integer.parseInt(action.getRate()) <= MagicNumbers.MAX_RATING) {
                session.getCurrentMovie().setNumRatings(
                        session.getCurrentMovie().getNumRatings() + 1);
                session.getCurrentMovie().getRatings().add(
                        session.getCurrentMovie().getRatings().size(),
                        Integer.parseInt(action.getRate()));
                session.getCurrentMovie().setNewRating();
                session.getCurrentUser().getRatedMovies()
                        .add(session.getCurrentMovie());
                OutputGenerator outputGenerator =
                        new OutputGenerator("Details", session.getCurrentUser(),
                                session.getCurrentUser().getCurrentMoviesList(),
                                session.getCurrentMovie());
                output.add(outputGenerator.outputCreator().deepCopy());
            } else {
                ErrorPage errorPage = new ErrorPage();
                errorPage.accept(new PageVisitor(), action, session,
                        usersDatabase, moviesDatabase, output);
            }
        }
    }
    /**
     * seeDetails page visitor
     */
    @Override
    public void visit(final ErrorPage errorPage, final ActionsInput action,
                      final Session session, final UsersDatabase usersDatabase,
                      final MoviesDatabase moviesDatabase, final ArrayNode output) {
        OutputGenerator outputGenerator;
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
}
