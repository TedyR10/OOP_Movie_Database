import backend.ActionHandler;
import singleton.MoviesDatabase;
import singleton.UsersDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionsInput;
import input.InputData;
import session.Session;

import java.io.File;
import java.io.IOException;

/**
 * This class is the brain of the operation
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * @param args from test
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File resultFile = new File(args[1]);
        File sourceFile = new File(args[0]);

        ObjectMapper objectMapper = new ObjectMapper();
        // Reads from file
        InputData input = objectMapper.readValue(sourceFile,
                InputData.class);
        // Initialize session
        Session session = new Session();
        // Initialize databases
        UsersDatabase usersDatabase = UsersDatabase.getInstance();
        MoviesDatabase moviesDatabase = MoviesDatabase.getInstance();
        moviesDatabase.setMovies(input.getMovies());
        usersDatabase.setUsers(input.getUsers(), moviesDatabase);
        ArrayNode output = objectMapper.createArrayNode();

        // Iterate through the required actions
        for (ActionsInput action : input.getActions()) {
            ActionHandler actionHandler = new ActionHandler();
            actionHandler.handle(action, session, usersDatabase, moviesDatabase, output);
        }

        // Clear the databases for the next tests
        usersDatabase.clearDatabase();
        moviesDatabase.clearDatabase();

        // Write to resultFile
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(resultFile, output);
    }
}
