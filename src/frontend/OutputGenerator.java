package frontend;

import backend.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

abstract class Output {
    public abstract ObjectNode generateOutput(ObjectMapper objectMapper,
                                              ObjectNode node, User user);
}
class GeneralOutput extends Output {
    @Override
    public ObjectNode generateOutput(final ObjectMapper objectMapper,
                                     final ObjectNode node, final User user) {
        node.put("error", "Error");
        ArrayList<String> movies = new ArrayList<String>();
        node.putPOJO("currentMoviesList", movies);
        node.putNull("currentUser");
        return node;
    }
}
class UserOutput extends Output {

    @Override
    public ObjectNode generateOutput(final ObjectMapper objectMapper,
                                     final ObjectNode node, final User user) {
        node.putNull("error");
        node.putPOJO("currentMoviesList", user.getCurrentMoviesList());
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
        userOut.putPOJO("purchasedMovies", user.getPurchasedMovies());
        userOut.putPOJO("watchedMovies", user.getWatchedMovies());
        userOut.putPOJO("likedMovies", user.getLikedMovies());
        userOut.putPOJO("ratedMovies", user.getRatedMovies());
        node.set("currentUser", userOut);
        return node;
    }
}
class HawaiianPizza extends Output {

    @Override
    public ObjectNode generateOutput(final ObjectMapper objectMapper,
                                     final ObjectNode node, final User user) {
        return null;
    }
}

final class OutputFactory {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static ObjectNode node = objectMapper.createObjectNode();

    private OutputFactory() {
    }

    public enum OutputType {
        General, User, Hawaiian
    }
    public static ObjectNode createOutput(final OutputType outputType,
                                          final User user) {
        switch (outputType) {
            case General:   return new GeneralOutput().generateOutput(objectMapper, node, user);
            case User:    return new UserOutput().generateOutput(objectMapper, node, user);
            case Hawaiian:  return new HawaiianPizza().generateOutput(objectMapper, node, user);
            default:    return null;
        }
    }
}

public class OutputGenerator {

    private String outputType;
    private User user;

    public OutputGenerator(final String type, final User outUser) {
        this.outputType = type;
        this.user = outUser;
    }

    /**
     *
     * @return node output
     */
    public ObjectNode outputCreator() {
        switch (this.outputType) {
            case "General":
                return OutputFactory.createOutput(OutputFactory.OutputType.General, user);
            case "User":
                return OutputFactory.createOutput(OutputFactory.OutputType.User, user);
            default:
                return null;
        }
    }
}
