import backend.ActionHandler;
import backend.MoviesDatabase;
import backend.UsersDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.ActionsInput;
import input.InputData;
import session.Session;

import java.io.File;
import java.io.IOException;

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
        InputData input = objectMapper.readValue(sourceFile,
                InputData.class);
        Session session = new Session();
        UsersDatabase usersDatabase = UsersDatabase.getInstance();
        MoviesDatabase moviesDatabase = MoviesDatabase.getInstance();
        moviesDatabase.setMovies(input.getMovies());
        usersDatabase.setUsers(input.getUsers(), moviesDatabase);
        ArrayNode output = objectMapper.createArrayNode();

        for (ActionsInput action : input.getActions()) {

            System.out.println();
            System.out.println("Pagina curenta: " + session.getCurrentPage());
            System.out.println("Comanda: " + action.getType() + " " + action.getPage());
            System.out.println();

            ActionHandler actionHandler = new ActionHandler();
            actionHandler.handle(action, session, usersDatabase, moviesDatabase, output);
        }

        usersDatabase.clearDatabase();
        moviesDatabase.clearDatabase();

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(resultFile, output);
    }
}
