package backend;

import observer.Observer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import factory.OutputGenerator;
import session.Session;
import singleton.MoviesDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.Comparator;

/**
 * This class represents a user in the database
 */
public class User implements Observer {

    private final String name;
    private final String password;
    private String accountType;
    private final String country;
    private int balance;
    private int tokens = 0;
    private int numFreePremiumMovies = Constants.FREE_PREMIUM_MOVIES;
    private ArrayList<Movie> currentMoviesList = new ArrayList<Movie>();
    private ArrayList<Movie> purchasedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> watchedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> likedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> ratedMovies = new ArrayList<Movie>();
    private ArrayList<Notifications> notifications = new ArrayList<>();
    private ArrayList<String> subscribedGenres = new ArrayList<>();
    private HashMap<String, Integer> likedGenres = new HashMap<>();

    /**
     * Constructor for user, initializing mandatory fields
     * @param name name
     * @param password password
     * @param accountType accountType
     * @param country country
     * @param balance balance
     */
    public User(final String name, final String password, final String accountType,
                final String country, final int balance) {
        this.name = name;
        this.password = password;
        this.accountType = accountType;
        this.country = country;
        this.balance = balance;
    }

    /**
     * This method checks if a used is subscribed to a certain genre
     * @param subscription genre to check
     * @return true if the user is subscribed
     */
    public boolean checkSubscription(final String subscription) {
        for (String genre : subscribedGenres) {
            if (Objects.equals(genre, subscription)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if the user has purchsed a particular movie
     * @param nameOut gets the name to check if the movie was purchased
     * @return true if the movie was purchased
     */
    public boolean checkPurchase(final String nameOut) {
        for (Movie movie : this.purchasedMovies) {
            if (Objects.equals(movie.getName(), nameOut)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if the user has watched a particular movie
     * @param nameOut gets the name to check if the movie was watched
     * @return true if the movie was watched
     */
    public boolean checkWatch(final String nameOut) {
        for (Movie movie : this.watchedMovies) {
            if (Objects.equals(movie.getName(), nameOut)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method updates the user liked genres
     * @param genres genres to be updated
     */
    public void updateLikedGenres(final ArrayList<String> genres) {
        for (String genre : genres) {
            boolean found = false;
            for (HashMap.Entry<String, Integer> entry : likedGenres.entrySet()) {
                if (entry.getKey().equals(genre) && !found) {
                    entry.setValue(entry.getValue() + 1);
                    found = true;
                }
            }

            if (!found) {
                likedGenres.put(genre, 1);
            }
        }
    }

    /**
     * This method generates a recommendation for a premium user
     */
    public void generateRecommendation(final MoviesDatabase moviesDatabase, final Session session,
                                       final ArrayNode output) {
        HashMap<String, Integer> copy = new HashMap<>();
        for (HashMap.Entry<String, Integer> entry : likedGenres.entrySet()) {
            copy.put(entry.getKey(), entry.getValue());
        }

        TreeMap<String, Integer> treeMap
                = new TreeMap<String, Integer>(copy);

        moviesDatabase.getMoviesUser(session.getCurrentUser());
        session.getCurrentUser().getCurrentMoviesList().sort(new Comparator<Movie>() {
            @Override
            public int compare(final Movie o1, final Movie o2) {
                if (o1.getNumLikes() == 0 && o2.getNumLikes() == 0) {
                    return 0;
                } else if (o1.getNumLikes() == 0 && o2.getNumLikes() > 0) {
                    return 1;
                } else if (o2.getNumLikes() == 0 && o1.getNumLikes() > 0) {
                    return -1;
                } else if (o1.getNumLikes() == o2.getDuration()) {
                    return 0;
                } else if (o1.getRating() < o2.getRating()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        boolean found = false;
        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            if (!found) {
                for (Movie movies : session.getCurrentUser().getCurrentMoviesList()) {
                    if (!found) {
                        if (!(session.getCurrentUser().checkWatch(movies.getName()))) {
                            for (String genre : movies.getGenres()) {
                                if (Objects.equals(genre, entry.getKey())) {
                                    Notifications notification = new Notifications(movies.getName(),
                                            "Recommendation");
                                    session.getCurrentUser().getNotifications().add(notification);
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                break;
            }
        }

        if (!found) {
            Notifications notification = new Notifications("No recommendation",
                    "Recommendation");
            session.getCurrentUser().getNotifications().add(notification);
        }
        OutputGenerator outputGenerator =
                new OutputGenerator("Recommend", session.getCurrentUser(),
                        session.getCurrentUser().getCurrentMoviesList(),
                        session.getCurrentMovie());
        output.add(outputGenerator.outputCreator().deepCopy());
    }

    /**
     * for coding style
     */
    public HashMap<String, Integer> getLikedGenres() {
        return likedGenres;
    }
    /**
     * for coding style
     */
    public void setLikedGenres(final HashMap<String, Integer> likedGenres) {
        this.likedGenres = likedGenres;
    }
    /**
     * for coding style
     */
    public ArrayList<String> getSubscribedGenres() {
        return subscribedGenres;
    }
    /**
     * for coding style
     */
    public void setSubscribedGenres(final ArrayList<String> subscribedGenres) {
        this.subscribedGenres = subscribedGenres;
    }
    /**
     * for coding style
     */
    public ArrayList<Notifications> getNotifications() {
        return notifications;
    }
    /**
     * for coding style
     */
    public void setNotifications(final ArrayList<Notifications> notifications) {
        this.notifications = notifications;
    }
    /**
     * for coding style
     */
    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }
    /**
     * for coding style
     */
    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    /**
     * for coding style
     */
    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }
    /**
     * for coding style
     */
    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }
    /**
     * for coding style
     */
    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }
    /**
     * for coding style
     */
    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }
    /**
     * for coding style
     */
    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }
    /**
     * for coding style
     */
    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }
    /**
     * for coding style
     */
    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }
    /**
     * for coding style
     */
    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    /**
     * for coding style
     */
    public ArrayList<Movie> getCurrentMoviesList() {
        return currentMoviesList;
    }
    /**
     * for coding style
     */
    public void setCurrentMoviesList(final ArrayList<Movie> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    /**
     * for coding style
     */
    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }
    /**
     * for coding style
     */
    public void setBalance(final int balance) {
        this.balance = balance;
    }
    /**
     * for coding style
     */
    public String getName() {
        return name;
    }
    /**
     * for coding style
     */
    public String getPassword() {
        return password;
    }
    /**
     * for coding style
     */
    public String getAccountType() {
        return accountType;
    }
    /**
     * for coding style
     */
    public String getCountry() {
        return country;
    }
    /**
     * for coding style
     */
    public int getBalance() {
        return balance;
    }
    /**
     * for coding style
     */
    public int getTokens() {
        return tokens;
    }
    /**
     * for coding style
     */
    public void setTokens(final int tokens) {
        this.tokens = tokens;
    }

    /**
     * This method updates the User when a movie has been added or deleted
     * @param o movie
     * @param type type of action
     */
    @Override
    public void update(final Object o, final String type) {
        Movie updateMovie = (Movie) o;
        boolean notified = false;
        for (String genre : subscribedGenres) {
            if (!notified) {
                for (String movieGenres : updateMovie.getGenres()) {
                    if (Objects.equals(genre, movieGenres)) {
                        Notifications newNotification;
                        if (Objects.equals(type, "add")) {
                            newNotification = new Notifications(
                                    updateMovie.getName(), "ADD");
                        } else {
                            newNotification = new Notifications(
                                    updateMovie.getName(), "DELETE");
                        }
                        this.notifications.add(newNotification);
                        notified = true;
                        break;
                    }
                }
            }
        }
    }
}
