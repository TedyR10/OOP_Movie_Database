package factory;

import backend.Movie;
import backend.Notifications;
import backend.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

/**
 * This class acts as an extended Factory implementation for output
 */
abstract class Output {

    /**
     * This method generates the output
     * @param objectMapper objectMapper
     * @param node node
     * @param user user
     * @param movies movies
     * @param movie movie
     * @return output
     */
    public abstract ObjectNode generateOutput(ObjectMapper objectMapper,
                                              ObjectNode node, User user, ArrayList<Movie> movies,
                                              Movie movie);

    /**
     * This method makes a deep copy of a movie and returns the ObjectNode out
     * @param objectMapper objectMapper
     * @param node node
     * @param user user
     * @param movies movies
     * @param movieIn movie to be converted
     * @return converted movie into ObjectNode out
     */
    public ObjectNode convertMovie(final ObjectMapper objectMapper, final ObjectNode node,
                                   final User user,
                                   final ArrayList<Movie> movies, final Movie movieIn) {
        Movie movieOut = new Movie(movieIn.getName(), movieIn.getYear(), movieIn.getDuration(),
                movieIn.getGenres(), movieIn.getActors(), movieIn.getCountriesBanned());
        movieOut.setNumRatings(movieIn.getNumRatings());
        movieOut.setRating(movieIn.getRating());
        movieOut.setNumLikes(movieIn.getNumLikes());
        ObjectNode out = objectMapper.createObjectNode();
        out.put("name", movieOut.getName());
        out.put("year", movieOut.getYear());
        out.put("duration", movieOut.getDuration());
        out.putPOJO("genres", movieOut.getGenres());
        out.putPOJO("actors", movieOut.getActors());
        out.putPOJO("countriesBanned", movieOut.getCountriesBanned());
        out.put("numLikes", movieOut.getNumLikes());
        out.put("rating", movieOut.getRating());
        out.put("numRatings", movieOut.getNumRatings());
        return out;
    }

    /**
     * This method makes a deep copy of an entire arraylist
     * @param objectMapper objectMapper
     * @param node node
     * @param user user
     * @param movies movies
     * @param movie movie
     * @return converted movies into ArrayNode moviesOut
     */
    public ArrayNode convertMovies(final ObjectMapper objectMapper, final ObjectNode node,
                                    final User user,
                                    final ArrayList<Movie> movies, final Movie movie) {
        ArrayNode moviesOut = objectMapper.createArrayNode();
        for (Movie movieIn : movies) {
            Movie movieOut = new Movie(movieIn.getName(), movieIn.getYear(), movieIn.getDuration(),
                    movieIn.getGenres(), movieIn.getActors(), movieIn.getCountriesBanned());
            movieOut.setNumRatings(movieIn.getNumRatings());
            movieOut.setRating(movieIn.getRating());
            movieOut.setNumLikes(movieIn.getNumLikes());
            ObjectNode out = objectMapper.createObjectNode();
            out.put("name", movieOut.getName());
            out.put("year", movieOut.getYear());
            out.put("duration", movieOut.getDuration());
            out.putPOJO("genres", movieOut.getGenres());
            out.putPOJO("actors", movieOut.getActors());
            out.putPOJO("countriesBanned", movieOut.getCountriesBanned());
            out.put("numLikes", movieOut.getNumLikes());
            out.put("rating", movieOut.getRating());
            out.put("numRatings", movieOut.getNumRatings());
            moviesOut.add(out.deepCopy());
        }
        return moviesOut;

    }

    /**
     * This method makes a deep copy of a user
     * @param objectMapper objectMapper
     * @param node node
     * @param user user
     * @param movies movies
     * @param movie movie
     * @return converted user into ObjectNode userOut
     */
    public ObjectNode userCreator(final ObjectMapper objectMapper, final ObjectNode node,
                                  final User user,
                                  final ArrayList<Movie> movies, final Movie movie) {
        ObjectNode userOut = objectMapper.createObjectNode();
        ObjectNode credentials = objectMapper.createObjectNode();
        credentials.put("name", user.getName());
        credentials.put("password", user.getPassword());
        credentials.put("accountType", user.getAccountType());
        credentials.put("country", user.getCountry());
        String balance = Integer.toString(user.getBalance());
        credentials.put("balance", balance);
        userOut.set("credentials", credentials);
        userOut.put("tokensCount", user.getTokens());
        userOut.put("numFreePremiumMovies", user.getNumFreePremiumMovies());
        ArrayNode purchasedMovies = convertMovies(
                objectMapper, node, user, user.getPurchasedMovies(), movie);
        userOut.set("purchasedMovies", purchasedMovies);
        ArrayNode watchedMovies = convertMovies(
                objectMapper, node, user, user.getWatchedMovies(), movie);
        userOut.set("watchedMovies", watchedMovies);
        ArrayNode likedMovies = convertMovies(
                objectMapper, node, user, user.getLikedMovies(), movie);
        userOut.set("likedMovies", likedMovies);
        ArrayNode ratedMovies = convertMovies(
                objectMapper, node, user, user.getRatedMovies(), movie);
        userOut.set("ratedMovies", ratedMovies);
        ArrayNode notifications = objectMapper.createArrayNode();
        for (Notifications notification : user.getNotifications()) {
            ObjectNode notificationNode = objectMapper.createObjectNode();
            notificationNode.put("movieName", notification.getMovieName());
            notificationNode.put("message", notification.getMessage());
            notifications.add(notificationNode.deepCopy());
        }
        userOut.set("notifications", notifications);
        return userOut.deepCopy();
    }
}

/**
 * Factory class for general output
 */
class GeneralOutput extends Output {
    @Override
    public ObjectNode generateOutput(final ObjectMapper objectMapper,
                                     final ObjectNode node, final User user,
                                     final ArrayList<Movie> movies, final Movie movie) {
        node.put("error", "Error");
        ArrayList<String> moviesOut = new ArrayList<String>();
        node.putPOJO("currentMoviesList", moviesOut);
        node.putNull("currentUser");
        return node.deepCopy();
    }
}

/**
 * Factory class for user output
 */
class UserOutput extends Output {

    @Override
    public ObjectNode generateOutput(final ObjectMapper objectMapper,
                                     final ObjectNode node, final User user,
                                     final ArrayList<Movie> movies, final Movie movie) {
        node.putNull("error");
        ArrayList<String> moviesOut = new ArrayList<String>();
        node.putPOJO("currentMoviesList", moviesOut);
        ObjectNode userOut = userCreator(objectMapper, node, user, movies, movie);
        node.set("currentUser", userOut);
        return node.deepCopy();
    }
}

/**
 * Factory class for movies output
 */
class Movies extends Output {

    @Override
    public ObjectNode generateOutput(final ObjectMapper objectMapper,
                                     final ObjectNode node, final User user,
                                     final ArrayList<Movie> movies, final Movie movie) {
        node.putNull("error");
        ArrayNode moviesOut = convertMovies(objectMapper, node, user, movies, movie);
        node.set("currentMoviesList", moviesOut);
        ObjectNode userOut = userCreator(objectMapper, node, user, movies, movie);
        node.set("currentUser", userOut);
        return node.deepCopy();
    }
}

/**
 * Factory class for SeeDetails output
 */
class Details extends Output {

    @Override
    public ObjectNode generateOutput(final ObjectMapper objectMapper,
                                     final ObjectNode node, final User user,
                                     final ArrayList<Movie> movies, final Movie movie) {
        node.putNull("error");
        ArrayNode moviesOut = objectMapper.createArrayNode();
        moviesOut.add(convertMovie(objectMapper, node, user, movies, movie));
        node.set("currentMoviesList", moviesOut);
        ObjectNode userOut = userCreator(objectMapper, node, user, movies, movie);
        node.set("currentUser", userOut);
        return node.deepCopy();
    }
}

class Recommend extends Output {
    @Override
    public ObjectNode generateOutput(final ObjectMapper objectMapper,
                                     final ObjectNode node, final User user,
                                     final ArrayList<Movie> movies, final Movie movie) {
        node.putNull("error");
        node.set("currentMoviesList", null);
        ObjectNode userOut = userCreator(objectMapper, node, user, movies, movie);
        node.set("currentUser", userOut);
        return node.deepCopy();
    }
}

/**
 * This class is the main factory for the outputs
 */
final class OutputFactory {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ObjectNode node = objectMapper.createObjectNode();

    private OutputFactory() {
    }

    public enum OutputType {
        General, User, Movies, Details, Recommend
    }

    /**
     * This method creates the output based on the outputType
     * @param outputType outputType
     * @param user user
     * @param movies movies
     * @param movie movie
     * @return output
     */
    public static ObjectNode createOutput(final OutputType outputType, final User user,
                                          final ArrayList<Movie> movies, final Movie movie) {
        switch (outputType) {
            case General:
                return new GeneralOutput().generateOutput(objectMapper, node, user, movies, movie);
            case User:
                return new UserOutput().generateOutput(objectMapper, node, user, movies, movie);
            case Movies:
                return new Movies().generateOutput(objectMapper, node, user, movies, movie);
            case Details:
                return new Details().generateOutput(objectMapper, node, user, movies, movie);
            case Recommend:
                return new Recommend().generateOutput(objectMapper, node, user, movies, movie);
            default:
                return null;
        }
    }
}

/**
 * This class gets the necessary output type from the visitors and starts the factory
 */
public class OutputGenerator {

    private String outputType;
    private User user;
    private ArrayList<Movie> movies;
    private Movie movie;

    /**
     * Output constructor, initializes the fields
     * @param type type of output needed
     * @param outUser user
     * @param outMovies movies
     * @param outMovie movie
     */
    public OutputGenerator(final String type, final User outUser,
                           final ArrayList<Movie> outMovies, final Movie outMovie) {
        this.outputType = type;
        this.user = outUser;
        this.movies = outMovies;
        this.movie = outMovie;
    }

    /**
     * This method calls the factory and returns the correct output
     * @return node output
     */
    public ObjectNode outputCreator() {
        switch (this.outputType) {
            case "General":
                return OutputFactory.createOutput(
                        OutputFactory.OutputType.General, user, movies, movie);
            case "User":
                return OutputFactory.createOutput(
                        OutputFactory.OutputType.User, user, movies, movie);
            case "Movies":
                return OutputFactory.createOutput(
                        OutputFactory.OutputType.Movies, user, movies, movie);
            case "Details":
                return OutputFactory.createOutput(
                        OutputFactory.OutputType.Details, user, movies, movie);
            case "Recommend":
                return OutputFactory.createOutput(
                        OutputFactory.OutputType.Recommend, user, movies, movie);
            default:
                return null;
        }
    }
}
