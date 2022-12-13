package backend;

import input.UserInput;

import java.util.ArrayList;
import java.util.Objects;

public final class UsersDatabase {

    private static UsersDatabase instance = null;
    private ArrayList<User> users = new ArrayList<User>();

    private UsersDatabase() {

    }
    /**
     *
     * @return lazy singleton implementation
     */
    public static UsersDatabase getInstance() {
        if (instance == null) {
            instance = new UsersDatabase();
        }
        return instance;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     *
     * @param userInputs gets the users from input and adds them into the database
     */
    public void setUsers(final ArrayList<UserInput> userInputs) {
        for (int i = 0; i < userInputs.size(); i++) {
            UserInput user = userInputs.get(i);
            this.users.add(i, new User(user.getCredentials().getName(),
                    user.getCredentials().getPassword(), user.getCredentials().getAccountType(),
                    user.getCredentials().getCountry(), user.getCredentials().getBalance()));
        }
    }

    /**
     *
     * @param user get a user
     * @return check if he is in the database or not
     */
    public boolean checkUser(final User user) {
        for (int i = 0; i < users.size(); i++) {
            if (Objects.equals(user.getName(), users.get(i).getName())
                    && Objects.equals(user.getPassword(), users.get(i).getPassword())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param user gets a user
     * @return returns the credentials from the database
     */
    public User getUser(final User user) {
        for (int i = 0; i < users.size(); i++) {
            if (Objects.equals(user.getName(), users.get(i).getName())
                    && Objects.equals(user.getPassword(), users.get(i).getPassword())) {
                return users.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @param user adds the new user to the database
     */
    public void addUser(final User user) {
        this.users.add(this.users.size(), new User(user.getName(),
                user.getPassword(), user.getAccountType(),
                user.getCountry(), user.getBalance()));
    }

    /**
     * clears the database for the next test
     */
    public void clearDatabase() {
        this.users.clear();
    }
}
