package backend;

import java.util.ArrayList;

/**
 * This class represents a movie in the database
 */
public class Movie {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private int numLikes;
    private double rating = 0.00;
    private int numRatings;

    // We'll store all the ratings given by users to calculate the new rating
    private ArrayList<Integer> ratings = new ArrayList<Integer>();

    /**
     * Constructor for movie, initializes mandatory fields
     * @param name name
     * @param year year
     * @param duration duration
     * @param genres genres
     * @param actors actors
     * @param countriesBanned countries banned
     */
    public Movie(final String name, final int year, final int duration,
                 final ArrayList<String> genres, final ArrayList<String> actors,
                 final ArrayList<String> countriesBanned) {
        this.name = name;
        this.year = year;
        this.duration = duration;
        this.genres = genres;
        this.actors = actors;
        this.countriesBanned = countriesBanned;
    }

    /**
     * This method sets the updated rating for a movie after a new rating
     */
    public void setNewRating() {
        Integer sum = 0;
        for (Integer rate : this.ratings) {
            sum += rate;
        }
        this.rating = (double) (sum / this.numRatings);
    }

    /**
     * for coding style
     */
    public ArrayList<Integer> getRatings() {
        return ratings;
    }
    /**
     * for coding style
     */
    public void setRatings(final ArrayList<Integer> ratings) {
        this.ratings = ratings;
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
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * for coding style
     */
    public int getYear() {
        return year;
    }
    /**
     * for coding style
     */
    public void setYear(final int year) {
        this.year = year;
    }
    /**
     * for coding style
     */
    public int getDuration() {
        return duration;
    }
    /**
     * for coding style
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }
    /**
     * for coding style
     */
    public ArrayList<String> getGenres() {
        return genres;
    }
    /**
     * for coding style
     */
    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }
    /**
     * for coding style
     */
    public ArrayList<String> getActors() {
        return actors;
    }
    /**
     * for coding style
     */
    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }
    /**
     * for coding style
     */
    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }
    /**
     * for coding style
     */
    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }
    /**
     * for coding style
     */
    public int getNumLikes() {
        return numLikes;
    }
    /**
     * for coding style
     */
    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }
    /**
     * for coding style
     */
    public double getRating() {
        return rating;
    }
    /**
     * for coding style
     */
    public void setRating(final double rating) {
        this.rating = rating;
    }
    /**
     * for coding style
     */
    public int getNumRatings() {
        return numRatings;
    }
    /**
     * for coding style
     */
    public void setNumRatings(final int numRatings) {
        this.numRatings = numRatings;
    }
}
