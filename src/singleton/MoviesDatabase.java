package singleton;

import backend.Movie;
import backend.User;
import input.MovieInput;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents the movies database and will probably be of use in part 2
 */
public final class MoviesDatabase {

    private static MoviesDatabase instance = null;

    private ArrayList<Movie> movies = new ArrayList<Movie>();

    private MoviesDatabase() {

    }
    /**
     * This method is used for the singleton implementation
     * @return instance
     */
    public static MoviesDatabase getInstance() {
        if (instance == null) {
            instance = new MoviesDatabase();
        }
        return instance;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    /**
     * This method gets the movies from input and puts them inside our database
     * @param moviesInput moviesInput
     */
    public void setMovies(final ArrayList<MovieInput> moviesInput) {
        for (int i = 0; i < moviesInput.size(); i++) {
            MovieInput movie = moviesInput.get(i);
            this.movies.add(i, new Movie(movie.getName(), movie.getYear(),
                    movie.getDuration(), movie.getGenres(), movie.getActors(),
                    movie.getCountriesBanned()));
        }
    }

    /**
     * This method clears the database for the next test
     */
    public void clearDatabase() {
        this.movies.clear();
    }

    /**
     * This method return the available movies for a specific user
     * @param user user
     */
    public void getMoviesUser(final User user) {
        user.getCurrentMoviesList().clear();
        for (Movie movie : this.movies) {
            boolean banned = false;
            for (String country : movie.getCountriesBanned()) {
                if (Objects.equals(user.getCountry(), country)) {
                    banned = true;
                    break;
                }
            }
            if (!banned) {
                user.getCurrentMoviesList().add(movie);
            }
        }
    }

    /**
     * This method returns a movie from the database
     * @param name name of the movie
     * @return movie
     */
    public Movie getMovie(final String name) {
        for (Movie movie : this.movies) {
            if (Objects.equals(movie.getName(), name)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * This method checks if a movie is in the database
     * @param name name of the movie
     * @return true if the movie exists
     */
    public boolean checkMovie(final String name) {
        for (Movie movie : this.movies) {
            if (Objects.equals(movie.getName(), name)) {
                return true;
            }
        }
        return false;
    }
}
