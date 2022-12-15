package backend;

import input.ActionsInput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * This class filteres a given movie list based on several factors
 */
public class Filters {
    /**
     * Main filter method. From here, we check which filter has been applied
     * @param movies movies to filter
     * @param actionsInput type of filtering needed
     * @return filtered movie array
     */
    public ArrayList<Movie> filter(final ArrayList<Movie> movies, final ActionsInput actionsInput) {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();

        // Checking which type of filtering we need
        if (actionsInput.getFilters().getContains() != null) {
            // Filtering by both actors & genres
            if (actionsInput.getFilters().getContains().getActors() != null
                    && actionsInput.getFilters().getContains().getGenre() != null) {
                int actorsFilter = actionsInput.getFilters().getContains().getActors().size();
                ArrayList<Movie> actorsFiltered = new ArrayList<Movie>();
                actorsFiltered = filterContainsActors(movies,
                        actionsInput.getFilters().getContains().getActors(), actorsFilter);
                int genresFilter = actionsInput.getFilters().getContains().getGenre().size();
                filteredMovies = filterContainsGenre(actorsFiltered,
                        actionsInput.getFilters().getContains().getGenre(), genresFilter);
            } else if (actionsInput.getFilters().getContains().getActors() != null
                    && actionsInput.getFilters().getContains().getGenre() == null) {
                // Filtering only by actors
                int actorsFilter = actionsInput.getFilters().getContains().getActors().size();
                filteredMovies = filterContainsActors(movies,
                        actionsInput.getFilters().getContains().getActors(), actorsFilter);
            } else if (actionsInput.getFilters().getContains().getGenre() != null
                    && actionsInput.getFilters().getContains().getActors() == null) {
                // Filtering only by genres
                int genresFilter = actionsInput.getFilters().getContains().getGenre().size();
                filteredMovies = filterContainsGenre(movies,
                        actionsInput.getFilters().getContains().getGenre(), genresFilter);
            }
        }
        // Check if we need to sort the movies
        if (actionsInput.getFilters().getSort() != null) {
            filteredMovies = sortMovies(movies, actionsInput.getFilters().getSort().getRating(),
                    actionsInput.getFilters().getSort().getDuration());
        }
        return filteredMovies;
    }

    /**
     * This method sorts movies either by rating or duration
     * @param movies movies to sort
     * @param ratingSort type of sort (increasing/decreasing)
     * @param durationSort type of sort (increasing/decreasing)
     * @return return sorted movies array
     */
    public ArrayList<Movie> sortMovies(final ArrayList<Movie> movies,
                                        final String ratingSort, final String durationSort) {

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
     * This method is used to filter movies that contain certain actors
     * @param movies movies to filter
     * @param actors actors to sort by
     * @param actorsRequired actors needed
     * @return filtered movies array
     */
    public ArrayList<Movie> filterContainsActors(
            final ArrayList<Movie> movies, final ArrayList<String> actors,
                                                 final int actorsRequired) {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
        // Search every movie for the given actor(s)
        for (Movie movie : movies) {
            // This counter will help us when adding a movie
            int actorsFound = 0;
            for (String name : actors) {
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

    /**
     * This method is used to filter movies that contain certain genres
     * @param movies movies to filter
     * @param genres actors to sort by
     * @param genresRequired actors needed
     * @return filtered movies array
     */
    public ArrayList<Movie> filterContainsGenre(
            final ArrayList<Movie> movies, final ArrayList<String> genres,
                                                final int genresRequired) {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
        // Search every movie for the given genre(s)
        for (Movie movie : movies) {
            // This counter will help us when adding a movie
            int genresFound = 0;
            for (String name : genres) {
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
