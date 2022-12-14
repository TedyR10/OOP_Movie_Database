package backend;

import input.MovieInput;

import java.util.ArrayList;
import java.util.Objects;

public final class MoviesDatabase {

    private static MoviesDatabase instance = null;

    private ArrayList<Movie> movies = new ArrayList<Movie>();

    private MoviesDatabase() {

    }
    /**
     *
     * @return lazy singleton implementation
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
     *
     * @param moviesInput gets the movies from input and adds them into the database
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
     * clears the database for the next test
     */
    public void clearDatabase() {
        this.movies.clear();
    }

    /**
     *
     * @param user reassigns the correct movies to a user
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
     *
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
     *
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
