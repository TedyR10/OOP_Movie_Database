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
     * @param userInput gets the users from input and adds them into the database
     * @param moviesDatabase adds the available movies for each user
     */
    public void setUsers(final ArrayList<UserInput> userInput,
                         final MoviesDatabase moviesDatabase) {
        for (int i = 0; i < userInput.size(); i++) {
            UserInput user = userInput.get(i);
            this.users.add(i, new User(user.getCredentials().getName(),
                    user.getCredentials().getPassword(), user.getCredentials().getAccountType(),
                    user.getCredentials().getCountry(), user.getCredentials().getBalance()));
            ArrayList<Movie> availableMovies = moviesDatabase.getMovies();
            for (Movie movie : availableMovies) {
                boolean banned = false;
                for (String country : movie.getCountriesBanned()) {
                    if (Objects.equals(this.users.get(i).getCountry(), country)) {
                        banned = true;
                        break;
                    }
                }
                if (!banned) {
                    this.users.get(i).getCurrentMoviesList().add(movie);
                }
            }
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
    public void addUser(final User user, final MoviesDatabase moviesDatabase) {
        this.users.add(this.users.size(), new User(user.getName(),
                user.getPassword(), user.getAccountType(),
                user.getCountry(), user.getBalance()));
        ArrayList<Movie> availableMovies = moviesDatabase.getMovies();
        for (Movie movie : availableMovies) {
            boolean banned = false;
            for (String country : movie.getCountriesBanned()) {
                if (Objects.equals(this.users.get(this.users.size() - 1).getCountry(), country)) {
                    banned = true;
                    break;
                }
            }
            if (!banned) {
                this.users.get(this.users.size() - 1).getCurrentMoviesList().add(movie);
            }
        }
    }

    /**
     * clears the database for the next test
     */
    public void clearDatabase() {
        this.users.clear();
    }
}
