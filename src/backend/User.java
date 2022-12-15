package backend;

import java.util.ArrayList;
import java.util.Objects;

public class User {

    private final String name;
    private final String password;
    private String accountType;
    private final String country;
    private int balance;
    private int tokens = 0;
    private int numFreePremiumMovies = MagicNumbers.FREE_PREMIUM_MOVIES;

    private ArrayList<Movie> currentMoviesList = new ArrayList<Movie>();
    private ArrayList<Movie> purchasedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> watchedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> likedMovies = new ArrayList<Movie>();
    private ArrayList<Movie> ratedMovies = new ArrayList<Movie>();

    /**
     * for coding style
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
     *
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
     *
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
}
