package backend;

import input.ActionsInput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Filters {
    /**
     *
     * @param movies gets the movies to filter
     * @param actionsInput gets the type of filters
     * @return filtered movie array
     */
    public ArrayList<Movie> filter(final ArrayList<Movie> movies, final ActionsInput actionsInput) {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
        if (actionsInput.getFilters().getContains() != null) {
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
                int actorsFilter = actionsInput.getFilters().getContains().getActors().size();
                filteredMovies = filterContainsActors(movies,
                        actionsInput.getFilters().getContains().getActors(), actorsFilter);
            } else if (actionsInput.getFilters().getContains().getGenre() != null
                    && actionsInput.getFilters().getContains().getActors() == null) {
                int genresFilter = actionsInput.getFilters().getContains().getGenre().size();
                filteredMovies = filterContainsGenre(movies,
                        actionsInput.getFilters().getContains().getGenre(), genresFilter);
            }
        }
        if (actionsInput.getFilters().getSort() != null) {
            System.out.println("Sorting");
            filteredMovies = sortMovies(movies, actionsInput.getFilters().getSort().getRating(),
                    actionsInput.getFilters().getSort().getDuration());
        }
        return filteredMovies;
    }

    /**
     *
     * @param movies gets the movies to be filtered
     * @param ratingSort gets the sorting type
     * @param durationSort gets the sorting type
     * @return sorted movies list
     */
    public ArrayList<Movie> sortMovies(final ArrayList<Movie> movies,
                                        final String ratingSort, final String durationSort) {

        if (ratingSort != null && durationSort == null) {
            System.out.println("by rating");
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
            System.out.println("by duration");
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
     *
     * @param movie gets movie to filter
     * @param actor filters by actor
     * @return true if the movies has said actor
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
     *
     * @param movie gets movie to filter
     * @param genre filters by genre
     * @return true if the movies has said genre
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
     *
     * @param movies gets movies to filter
     * @param actors gets actors to filter by
     * @param actorsRequired gets number actors to filter by
     * @return filtered movie array
     */
    public ArrayList<Movie> filterContainsActors(
            final ArrayList<Movie> movies, final ArrayList<String> actors,
                                                 final int actorsRequired) {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
        for (Movie movie : movies) {
            int actorsFound = 0;
            for (String name : actors) {
                if (filterActor(movie, name)) {
                    actorsFound++;
                    boolean added = false;
                    for (Movie mov : filteredMovies) {
                        if (Objects.equals(mov.getName(), movie.getName())) {
                            added = true;
                            break;
                        }
                    }
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
     *
     * @param movies gets movies to filter
     * @param genres gets genres to filter by
     * @param genresRequired gets number genres to filter by
     * @return filtered movie array
     */
    public ArrayList<Movie> filterContainsGenre(
            final ArrayList<Movie> movies, final ArrayList<String> genres,
                                                final int genresRequired) {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
        for (Movie movie : movies) {
            int genresFound = 0;
            for (String name : genres) {
                if (filterGenre(movie, name)) {
                    genresFound++;
                    boolean added = false;
                    for (Movie mov : filteredMovies) {
                        if (Objects.equals(mov.getName(), movie.getName())) {
                            added = true;
                            break;
                        }
                    }
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
