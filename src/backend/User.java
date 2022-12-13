package backend;

import java.util.ArrayList;

public class User {

    private final String name;
    private final String password;
    private String accountType;
    private final String country;
    private int balance;
    private int tokens = 0;
    private ArrayList<String> currentMoviesList = new ArrayList<String>();
    private ArrayList<String> purchasedMovies = new ArrayList<String>();
    private ArrayList<String> watchedMovies = new ArrayList<String>();
    private ArrayList<String> likedMovies = new ArrayList<String>();
    private ArrayList<String> ratedMovies = new ArrayList<String>();
    private int numFreePremiumMovies = 15;

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
    public ArrayList<String> getPurchasedMovies() {
        return purchasedMovies;
    }
    /**
     * for coding style
     */
    public void setPurchasedMovies(final ArrayList<String> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }
    /**
     * for coding style
     */
    public ArrayList<String> getWatchedMovies() {
        return watchedMovies;
    }
    /**
     * for coding style
     */
    public void setWatchedMovies(final ArrayList<String> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }
    /**
     * for coding style
     */
    public ArrayList<String> getLikedMovies() {
        return likedMovies;
    }
    /**
     * for coding style
     */
    public void setLikedMovies(final ArrayList<String> likedMovies) {
        this.likedMovies = likedMovies;
    }
    /**
     * for coding style
     */
    public ArrayList<String> getRatedMovies() {
        return ratedMovies;
    }
    /**
     * for coding style
     */
    public void setRatedMovies(final ArrayList<String> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    /**
     * for coding style
     */
    public ArrayList<String> getCurrentMoviesList() {
        return currentMoviesList;
    }
    /**
     * for coding style
     */
    public void setCurrentMoviesList(final ArrayList<String> currentMoviesList) {
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
