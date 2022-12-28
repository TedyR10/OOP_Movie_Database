package input;

import java.util.ArrayList;

/**
 * This class is used for getting the inputs from files
 */
public final class MovieInput {

    private String name;
    private String year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> countriesBanned;
    private ArrayList<String> actors;

    public MovieInput() {
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public ArrayList<String> getActors() {
        return actors;
    }
}
