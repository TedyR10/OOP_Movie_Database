package strategy;

import backend.Movie;
import input.ActionsInput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * Common interface
 */
interface FiltersStrategy {
    ArrayList<Movie> filter(ArrayList<Movie> movies, ActionsInput actionsInput);
}

/**
 * This class executes the strategies
 */
class FiltersContext {
    private final FiltersStrategy filtersStrategy;

    /**
     * Constructor for FiltersContext. Initializes filtersStrategy
     * @param filtersStrategyIn filtersStrategyIn
     */
    FiltersContext(final FiltersStrategy filtersStrategyIn) {
        this.filtersStrategy = filtersStrategyIn;
    }

    /**
     * This method executes a strategy based on filtersStrategy's class
     * @param movies movies
     * @param actionsInput actionsInput
     * @return filtered movies array
     */
    public ArrayList<Movie> executeStrategy(
            final ArrayList<Movie> movies,
            final ActionsInput actionsInput) {
        return filtersStrategy.filter(movies, actionsInput);
    }
}

/**
 * This class filteres a given movie list based on several factors
 */
public class Filters {

    private FiltersStrategy strategy;
    private FiltersContext context;

    /**
     * Main filter method. We let the strategy figure out the filter needed
     * @param movies movies to filter
     * @param actionsInput type of filtering needed
     * @return filtered movie array
     */
    public ArrayList<Movie> filterMovies(
            final ArrayList<Movie> movies,
            final ActionsInput actionsInput) {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();

        // Checking which type of filtering we need
        if (actionsInput.getFilters().getContains() != null) {

            // Filtering by both actors & genres
            if (actionsInput.getFilters().getContains().getActors() != null
                    && actionsInput.getFilters().getContains().getGenre() != null) {
                // Filter by actors
                ArrayList<Movie> actorsFiltered = new ArrayList<Movie>();
                strategy = new FilterByActors();
                context = new FiltersContext(strategy);
                actorsFiltered = context.executeStrategy(movies, actionsInput);

                // Filter by genres
                strategy = new FilterByGenres();
                context = new FiltersContext(strategy);
                filteredMovies = context.executeStrategy(actorsFiltered, actionsInput);
            } else if (actionsInput.getFilters().getContains().getActors() != null
                    && actionsInput.getFilters().getContains().getGenre() == null) {
                // Filtering only by actors
                strategy = new FilterByActors();
                context = new FiltersContext(strategy);
                filteredMovies = context.executeStrategy(movies, actionsInput);
            } else if (actionsInput.getFilters().getContains().getGenre() != null
                    && actionsInput.getFilters().getContains().getActors() == null) {
                // Filtering only by genres
                strategy = new FilterByGenres();
                context = new FiltersContext(strategy);
                filteredMovies = context.executeStrategy(movies, actionsInput);
            }
        }

        // Check if we need to sort the movies
        if (actionsInput.getFilters().getSort() != null) {
            strategy = new SortMovies();
            filteredMovies = strategy.filter(movies, actionsInput);
        }
        return filteredMovies;
    }

    /**
     * for coding style
     */
    public FiltersStrategy getStrategy() {
        return strategy;
    }

    /**
     * for coding style
     */
    public void setStrategy(final FiltersStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * for coding style
     */
    public FiltersContext getContext() {
        return context;
    }

    /**
     * for coding style
     */
    public void setContext(final FiltersContext context) {
        this.context = context;
    }
}

/**
 * Concrete strategy. Implements FiltersStrategy & filters by actors
 */
class FilterByActors implements FiltersStrategy {

    /**
     * This method checks if a given actor acts in a movie
     * @param movie movie to check
     * @param actor actor to check
     * @return true if the actor acts in the movie
     */
    public boolean filterActor(final Movie movie, final String actor) {
        for (String actors : movie.getActors()) {
            if (Objects.equals(actor, actors)) {
                return true;
            }
        }
        return false;
    }
    /**
     * This method is used to filter movies that contain certain actors
     * @param movies movies to filter
     * @param actionsInput actionsInput
     * @return filtered movies array
     */
    @Override
    public ArrayList<Movie> filter(final ArrayList<Movie> movies,
                                   final ActionsInput actionsInput) {
        int actorsRequired =
                actionsInput.getFilters().getContains().getActors().size();
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();

        // Search every movie for the given actor(s)
        for (Movie movie : movies) {

            // This counter will help us when adding a movie
            int actorsFound = 0;
            for (String name : actionsInput.getFilters().getContains().getActors()) {

                // If we have found an actor, increase the counter
                if (filterActor(movie, name)) {
                    actorsFound++;
                    boolean added = false;

                    // Check to see if the movie has already been added by any chance
                    for (Movie mov : filteredMovies) {
                        if (Objects.equals(mov.getName(), movie.getName())) {
                            added = true;
                            break;
                        }
                    }

                    // If the counter matches the number of actors needed, add the current movie
                    if (!added && actorsFound == actorsRequired) {
                        filteredMovies.add(movie);
                        break;
                    }
                }
            }
        }
        return filteredMovies;
    }
}

/**
 * Concrete strategy. Implements FiltersStrategy & filters by genres
 */
class FilterByGenres implements FiltersStrategy {

    /**
     * This method checks if a given movie has a particular genre
     * @param movie movie to check
     * @param genre genre to check
     * @return true if the movie has the genre
     */
    public boolean filterGenre(final Movie movie, final String genre) {
        for (String genres : movie.getGenres()) {
            if (Objects.equals(genres, genre)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to filter movies that contain certain genres
     * @param movies movies to filter
     * @param actionsInput actionsInput
     * @return filtered movies array
     */
    @Override
    public ArrayList<Movie> filter(final ArrayList<Movie> movies,
                                   final ActionsInput actionsInput) {
        int genresRequired =
                actionsInput.getFilters().getContains().getGenre().size();
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();

        // Search every movie for the given genre(s)
        for (Movie movie : movies) {

            // This counter will help us when adding a movie
            int genresFound = 0;
            for (String name : actionsInput.getFilters().getContains().getGenre()) {

                // If we have found a genre, increase the counter
                if (filterGenre(movie, name)) {

                    genresFound++;
                    boolean added = false;

                    // Check to see if the movie has already been added by any chance
                    for (Movie mov : filteredMovies) {
                        if (Objects.equals(mov.getName(), movie.getName())) {
                            added = true;
                            break;
                        }
                    }

                    // If the counter matches the number of genres needed, add the current movie
                    if (!added && genresFound == genresRequired) {
                        filteredMovies.add(movie);
                        break;
                    }
                }
            }
        }
        return filteredMovies;
    }
}

/**
 * Concrete strategy. Implements FiltersStrategy & sorts
 */
class SortMovies implements FiltersStrategy {

    /**
     * This method sorts movies either by rating or duration
     * @param movies movies to sort
     * @param actionsInput actionsInput
     * @return return sorted movies array
     */
    @Override
    public ArrayList<Movie> filter(
            final ArrayList<Movie> movies,
            final ActionsInput actionsInput) {

        String ratingSort = actionsInput.getFilters().getSort().getRating();
        String durationSort = actionsInput.getFilters().getSort().getDuration();

        // Sort by rating
        if (ratingSort != null && durationSort == null) {

            movies.sort(new Comparator<Movie>() {
                @Override
                public int compare(final Movie o1, final Movie o2) {
                    if (o1.getRating() == 0 && o2.getRating() == 0) {
                        return 0;
                    } else if (o1.getRating() == 0) {
                        if (Objects.equals(ratingSort, "decreasing")) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else if (o2.getRating() == 0) {
                        if (Objects.equals(ratingSort, "decreasing")) {
                            return -1;
                        } else {
                            return 1;
                        }
                    } else if (o1.getRating() == o2.getRating()) {
                        return 0;
                    } else if (o1.getRating() < o2.getRating()) {
                        if (Objects.equals(ratingSort, "decreasing")) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else {
                        if (Objects.equals(ratingSort, "decreasing")) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                }
            });
            return movies;
        } else if (durationSort != null) {

            // Sort by duration
            movies.sort(new Comparator<Movie>() {
                @Override
                public int compare(final Movie o1, final Movie o2) {

                    if (o1.getDuration() == o2.getDuration()) {
                        if (o1.getRating() == 0 && o2.getRating() == 0) {
                            return 0;
                        } else if (o1.getRating() == 0) {
                            return 1;
                        } else if (o2.getRating() == 0) {
                            return -1;
                        } else if (o1.getRating() == o2.getRating()) {
                            return 0;
                        } else if (o1.getRating() < o2.getRating()) {
                            if (Objects.equals(ratingSort, "decreasing")) {
                                return 1;
                            } else {
                                return -1;
                            }
                        } else {
                            if (Objects.equals(ratingSort, "decreasing")) {
                                return -1;
                            } else {
                                return 1;
                            }
                        }
                    } else if (o1.getDuration() < o2.getDuration()) {
                        if (Objects.equals(durationSort, "decreasing")) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else {
                        if (Objects.equals(durationSort, "decreasing")) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                }
            });
            return movies;
        }
        return movies;
    }
}
