package frontend;

import backend.Filters;
import backend.MagicNumbers;
import backend.Movie;
import backend.MoviesDatabase;
import backend.Search;
import backend.User;
import backend.UsersDatabase;


import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionsInput;
import session.Session;

import java.util.ArrayList;
import java.util.Objects;

interface Visitor {
    void visit(ChangePage changePage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);
    void visit(LogoutPage logoutPage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);
    void visit(OnPage onPage, ActionsInput action, Session session,
               UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);

}

interface Visitable {
    void accept(PageVisitor v, ActionsInput action, Session session,
                UsersDatabase usersDatabase, MoviesDatabase moviesDatabase, ArrayNode output);
}

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
                    System.out.println("changed to login");
                    session.setCurrentPage("login");
                } else if (Objects.equals(action.getPage(), "register")) {
                    System.out.println("changed to regsiter");
                    session.setCurrentPage("register");
                } else {
                    System.out.println("Eroare pe homepage neautentificat. Pagina inexistenta");
                    OutputGenerator outputGenerator = new OutputGenerator("General",
                            null, null, null);

                    output.add(outputGenerator.outputCreator().deepCopy());
                }
            } else if (Objects.equals(session.getCurrentPage(), "login")) {
                System.out.println("Numai on page command login");
            } else if (Objects.equals(session.getCurrentPage(), "register")) {
                System.out.println("Numai on page command register");
            } else {
                System.out.println("blocat in home neauth");
            }
        } else {
            if (Objects.equals(session.getCurrentPage(),
                    "homepage autentificat")) {
                if (Objects.equals(action.getPage(), "logout")) {

                    PageVisitor visitor = new PageVisitor();
                    LogoutPage logoutPage = new LogoutPage();
                    logoutPage.accept(visitor, action, session,
                            usersDatabase, moviesDatabase, output);
                } else if (Objects.equals(action.getPage(), "movies")) {
                    moviesDatabase.getMoviesUser(session.getCurrentUser());
                    System.out.println("changed to movies");
                    session.setCurrentPage("movies");
                    OutputGenerator outputGenerator = new OutputGenerator("Movies",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());

                    output.add(outputGenerator.outputCreator().deepCopy());
                } else if (Objects.equals(action.getPage(), "upgrades")) {
                    System.out.println("changed to upgrades");
                    session.setCurrentPage("upgrades");
                } else {
                    System.out.println("Erroare autentificat changePage");
                    OutputGenerator outputGenerator = new OutputGenerator("General",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());

                    output.add(outputGenerator.outputCreator().deepCopy());
                }
            } else if (Objects.equals(session.getCurrentPage(),
                    "movies")) {
                if (Objects.equals(action.getPage(), "see details")) {
                    System.out.println("See details");
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
                        System.out.println("Erroare see details");
                        OutputGenerator outputGenerator = new OutputGenerator("General",
                                session.getCurrentUser(),
                                session.getCurrentUser().getCurrentMoviesList(),
                                session.getCurrentMovie());

                        output.add(outputGenerator.outputCreator().deepCopy());
                    }
                } else if (Objects.equals(action.getPage(), "logout")) {

                    PageVisitor visitor = new PageVisitor();
                    LogoutPage logoutPage = new LogoutPage();
                    logoutPage.accept(visitor, action, session,
                            usersDatabase, moviesDatabase, output);
                } else if (Objects.equals(action.getPage(), "movies")) {
                    moviesDatabase.getMoviesUser(session.getCurrentUser());
                    System.out.println("changed to movies");
                    session.setCurrentPage("movies");
                    OutputGenerator outputGenerator = new OutputGenerator("Movies",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());

                    output.add(outputGenerator.outputCreator().deepCopy());
                } else {
                    System.out.println("Eroare");
                    OutputGenerator outputGenerator = new OutputGenerator("General",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());

                    output.add(outputGenerator.outputCreator().deepCopy());
                }
            } else if (Objects.equals(session.getCurrentPage(),
                    "upgrades")) {
                if (Objects.equals(action.getPage(), "logout")) {

                    PageVisitor visitor = new PageVisitor();
                    LogoutPage logoutPage = new LogoutPage();
                    logoutPage.accept(visitor, action, session,
                            usersDatabase, moviesDatabase, output);
                } else if (Objects.equals(action.getPage(), "movies")) {
                    System.out.println("changed to movies");
                    moviesDatabase.getMoviesUser(session.getCurrentUser());
                    session.setCurrentPage("movies");
                    OutputGenerator outputGenerator = new OutputGenerator("Movies",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());
                    output.add(outputGenerator.outputCreator().deepCopy());
                } else {
                    System.out.println("Eroare");
                    OutputGenerator outputGenerator = new OutputGenerator("General",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());

                    output.add(outputGenerator.outputCreator().deepCopy());
                }
            } else if (Objects.equals(session.getCurrentPage(),
                    "see details")) {
                if (Objects.equals(action.getPage(), "movies")) {
                    System.out.println("changed to movies");
                    moviesDatabase.getMoviesUser(session.getCurrentUser());
                    session.setCurrentMovie(null);
                    session.setCurrentPage("movies");
                    OutputGenerator outputGenerator = new OutputGenerator("Movies",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());
                    output.add(outputGenerator.outputCreator().deepCopy());
                } else if (Objects.equals(action.getPage(), "logout")) {

                    PageVisitor visitor = new PageVisitor();
                    LogoutPage logoutPage = new LogoutPage();
                    logoutPage.accept(visitor, action, session,
                            usersDatabase, moviesDatabase, output);
                } else {
                    System.out.println("Eroare");
                    OutputGenerator outputGenerator = new OutputGenerator("General",
                            session.getCurrentUser(),
                            session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());

                    output.add(outputGenerator.outputCreator().deepCopy());
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
        System.out.println("Logout");
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
            User checkUser = new User(action.getCredentials().getName(),
                    action.getCredentials().getPassword(),
                    null, null, 0);
            if (usersDatabase.checkUser(checkUser)) {
                session.setCurrentUser(usersDatabase.getUser(checkUser));
                session.setLogin(true);
                session.setCurrentPage("homepage autentificat");
                System.out.println("User " + checkUser.getName() + " logged in");
                User outUser = usersDatabase.getUser(checkUser);
                OutputGenerator outputGenerator = new OutputGenerator("User", outUser,
                        outUser.getCurrentMoviesList(),
                        session.getCurrentMovie());
                output.add(outputGenerator.outputCreator().deepCopy());
            } else {
                OutputGenerator outputGenerator = new OutputGenerator("General", checkUser,
                        checkUser.getCurrentMoviesList(),
                        session.getCurrentMovie());
                output.add(outputGenerator.outputCreator().deepCopy());
                System.out.println("Eroare: nu e in baza de date " + checkUser.getName() + " "
                        + checkUser.getPassword());
                session.setCurrentPage("homepage neautentificat");
            }
        } else if (Objects.equals(action.getFeature(), "register")
                && Objects.equals(session.getCurrentPage(), "register")) {
            User checkUser = new User(action.getCredentials().getName(),
                    action.getCredentials().getPassword(),
                    action.getCredentials().getAccountType(), action.getCredentials().getCountry(),
                    action.getCredentials().getBalance());
            if (usersDatabase.checkUser(checkUser)) {
                System.out.println("error: User "
                        + action.getCredentials().getName() + " deja exista");
            } else {
                usersDatabase.addUser(checkUser, moviesDatabase);
                session.setCurrentPage("homepage autentificat");
                session.setLogin(true);
                User newUser = usersDatabase.getUser(checkUser);
                session.setCurrentUser(newUser);
                System.out.println("User " + session.getCurrentUser().getName() + " added");
                OutputGenerator outputGenerator = new OutputGenerator("User", checkUser,
                        checkUser.getCurrentMoviesList(),
                        session.getCurrentMovie());
                output.add(outputGenerator.outputCreator().deepCopy());
            }
        } else if (Objects.equals(action.getFeature(), "search")
                && Objects.equals(session.getCurrentPage(), "movies")) {
            moviesDatabase.getMoviesUser(session.getCurrentUser());
            System.out.println("Search");
            ArrayList<Movie> searchMovies =
                    new Search().searchMovies(session.getCurrentUser().getCurrentMoviesList(),
                    action.getStartsWith());
            session.getCurrentUser().setCurrentMoviesList(searchMovies);
            OutputGenerator outputGenerator =
                    new OutputGenerator("Movies", session.getCurrentUser(),
                    session.getCurrentUser().getCurrentMoviesList(), session.getCurrentMovie());
            output.add(outputGenerator.outputCreator().deepCopy());
        } else if (Objects.equals(action.getFeature(), "filter")
                && Objects.equals(session.getCurrentPage(), "movies")) {
            moviesDatabase.getMoviesUser(session.getCurrentUser());
            System.out.println("Filter");
            ArrayList<Movie> filterMovies =
                    new Filters().filter(session.getCurrentUser().getCurrentMoviesList(), action);
            session.getCurrentUser().setCurrentMoviesList(filterMovies);
            OutputGenerator outputGenerator =
                    new OutputGenerator("Movies", session.getCurrentUser(),
                    session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());
            output.add(outputGenerator.outputCreator().deepCopy());
        } else if (Objects.equals(session.getCurrentPage(), "upgrades")) {
            if (Objects.equals(action.getFeature(), "buy tokens")) {
                if (session.getCurrentUser().getBalance() >= Integer.parseInt(action.getCount())) {
                    System.out.println("Buying tokens");
                    session.getCurrentUser().setTokens(Integer.parseInt(action.getCount()));
                    session.getCurrentUser().setBalance(session.getCurrentUser().getBalance()
                            - Integer.parseInt(action.getCount()));
                } else {
                    System.out.println("Foame mare, n-avem bani de tokeni");
                }
            } else if (Objects.equals(action.getFeature(), "buy premium account")) {
                if (session.getCurrentUser().getTokens()
                        >= MagicNumbers.MIN_TOKENS_PREMIUM_ACCOUNT) {
                    System.out.println("Buying premium acc");
                    session.getCurrentUser().setTokens(
                            session.getCurrentUser().getTokens()
                                    - MagicNumbers.MIN_TOKENS_PREMIUM_ACCOUNT);
                    session.getCurrentUser().setAccountType("premium");
                } else {
                    System.out.println("Foame mare, n-avem bani de cont premium");
                }
            }
        } else if (Objects.equals(session.getCurrentPage(), "see details")) {
            if (Objects.equals(action.getFeature(), "purchase")) {
                System.out.println("Purchase");
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
                            System.out.println("Not enough tokens to purchase movie");
                            OutputGenerator outputGenerator =
                                    new OutputGenerator("General", session.getCurrentUser(),
                                            session.getCurrentUser().getCurrentMoviesList(),
                                            session.getCurrentMovie());
                            output.add(outputGenerator.outputCreator().deepCopy());
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
                        System.out.println("Not enough tokens to purchase movie");
                        OutputGenerator outputGenerator =
                                new OutputGenerator("General", session.getCurrentUser(),
                                        session.getCurrentUser().getCurrentMoviesList(),
                                        session.getCurrentMovie());
                        output.add(outputGenerator.outputCreator().deepCopy());
                    }
                }
            } else if (Objects.equals(action.getFeature(), "watch")) {
                if (session.getCurrentUser().checkPurchase(
                        session.getCurrentMovie().getName())) {
                    System.out.println("Watch");
                    session.getCurrentUser().getWatchedMovies()
                            .add(session.getCurrentMovie());
                    OutputGenerator outputGenerator =
                            new OutputGenerator("Details", session.getCurrentUser(),
                                    session.getCurrentUser().getCurrentMoviesList(),
                                    session.getCurrentMovie());
                    output.add(outputGenerator.outputCreator().deepCopy());
                } else {
                    System.out.println("Cannot Watch. Haven't purchased the movie yet.");
                    OutputGenerator outputGenerator =
                            new OutputGenerator("General", session.getCurrentUser(),
                                    session.getCurrentUser().getCurrentMoviesList(),
                                    session.getCurrentMovie());
                    output.add(outputGenerator.outputCreator().deepCopy());
                }
            } else if (Objects.equals(action.getFeature(), "like")) {
                if (session.getCurrentUser().checkWatch(
                        session.getCurrentMovie().getName())) {
                    System.out.println("Like");
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
                    System.out.println("Cannot Like. Haven't watched the movie yet.");
                    OutputGenerator outputGenerator =
                            new OutputGenerator("General", session.getCurrentUser(),
                                    session.getCurrentUser().getCurrentMoviesList(),
                                    session.getCurrentMovie());
                    output.add(outputGenerator.outputCreator().deepCopy());
                }
            } else if (Objects.equals(action.getFeature(), "rate")) {
                if (session.getCurrentUser().checkWatch(
                        session.getCurrentMovie().getName())
                        && Integer.parseInt(action.getRate()) <= MagicNumbers.MAX_RATING) {
                    System.out.println("Rate");
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
                    System.out.println("Cannot Rate. Haven't watched the movie yet.");
                    OutputGenerator outputGenerator =
                            new OutputGenerator("General", session.getCurrentUser(),
                                    session.getCurrentUser().getCurrentMoviesList(),
                                    session.getCurrentMovie());
                    output.add(outputGenerator.outputCreator().deepCopy());
                }
            }
        } else {
            System.out.println("Error");
            OutputGenerator outputGenerator =
                    new OutputGenerator("General", session.getCurrentUser(),
                    session.getCurrentUser().getCurrentMoviesList(),
                            session.getCurrentMovie());
            output.add(outputGenerator.outputCreator().deepCopy());
        }
    }
}
