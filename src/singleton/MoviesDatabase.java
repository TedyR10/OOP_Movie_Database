package singleton;

import observer.Observer;
import backend.Movie;
import backend.User;
import input.MovieInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the movies database
 */
public final class MoviesDatabase {

    private static MoviesDatabase instance = null;

    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private List<Observer> observerList = new ArrayList<>();

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

    /**
     * This method returns the movies inside the database
     * @return movies
     */
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
     * This method returns the available movies for a specific user
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

    /**
     * This method adds a movie into the database and notifies all observers about a new movie
     * @param movie movie to add into the database
     */
    public void addMovie(final MovieInput movie) {
        this.movies.add(new Movie(movie.getName(), movie.getYear(), movie.getDuration(),
                movie.getGenres(), movie.getActors(), movie.getCountriesBanned()));

        for (Observer observer : observerList) {
            observer.update(this.movies.get(this.movies.size() - 1), "add");
        }
    }

    /**
     * This method removes a movie from the database and notifies all observers
     * @param name
     */
    public void deleteMovie(final String name) {
        for (Movie movie : this.movies) {
            if (Objects.equals(movie.getName(), name)) {
                for (Observer observer : observerList) {
                    observer.update(movie, "delete");
                }
                this.movies.remove(movie);
                break;
            }
        }
    }

    /**
     * This method adds a user as an Observer
     * @param observer Observer
     */
    public void addObserver(final Observer observer) {
        this.observerList.add(observer);
    }

    /**
     * This method eliminates an Observer
     * @param observer Observer
     */
    public void removeObserver(final Observer observer) {
        this.observerList.remove(observer);
    }
}
