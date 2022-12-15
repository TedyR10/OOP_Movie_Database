package backend;

import java.util.ArrayList;

/**
 * This class is used for searching actions
 */
public class Search {

    public Search() {
    }

    /**
     * This method searches for movies that start with a particular sequence
     * @param movies gets the movies to search through
     * @param start gets what the title must start with
     * @return filtered movies list
     */
    public ArrayList<Movie> searchMovies(final ArrayList<Movie> movies, final String start) {
        ArrayList<Movie> returnMovies = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getName().startsWith(start)) {
                returnMovies.add(movie);
            }
        }
        return returnMovies;
    }
}
