package input;

import java.util.ArrayList;

/**
 * This class is used for getting the inputs from files
 */
public final class InputData {
    private ArrayList<UserInput> users;
    private ArrayList<MovieInput> movies;
    private ArrayList<ActionsInput> actions;

    public InputData() {
    }

    public ArrayList<UserInput> getUsers() {
        return users;
    }

    public ArrayList<MovieInput> getMovies() {
        return movies;
    }

    public ArrayList<ActionsInput> getActions() {
        return actions;
    }
}
