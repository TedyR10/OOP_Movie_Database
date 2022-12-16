package singleton;

import backend.Movie;
import backend.User;
import input.UserInput;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents the users database
 */
public final class UsersDatabase {

    private static UsersDatabase instance = null;
    private ArrayList<User> users = new ArrayList<User>();

    private UsersDatabase() {

    }
    /**
     * This method is used for the singleton implementation
     * @return lazy singleton implementation
     */
    public static UsersDatabase getInstance() {
        if (instance == null) {
            instance = new UsersDatabase();
        }
        return instance;
    }

    /**
     * This method returns the users inside the database
     * @return users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * This method gets the users from input and puts them inside our database
     * along with their available movies
     * @param userInput users
     * @param moviesDatabase moviesDatabase
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

                // Checks if the movie is banned in user's country
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
     * This method checks if a user is in the database
     * @param user user
     * @return true if the credentials given match a user in the database
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
     * This method returns a user from the database
     * @param user user
     * @return user
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
     * This method adds a new user in the database
     * @param user user
     */
    public void addUser(final User user, final MoviesDatabase moviesDatabase) {
        this.users.add(this.users.size(), new User(user.getName(),
                user.getPassword(), user.getAccountType(),
                user.getCountry(), user.getBalance()));
        ArrayList<Movie> availableMovies = moviesDatabase.getMovies();
        for (Movie movie : availableMovies) {
            boolean banned = false;

            // Checks if the movie is banned in user's country
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
     * This method clears the database for the next test
     */
    public void clearDatabase() {
        this.users.clear();
    }
}
