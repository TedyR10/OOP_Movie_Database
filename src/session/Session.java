package session;

import backend.Movie;
import backend.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the current session
 */
public class Session {
    private boolean login;
    private String currentPage;
    private User currentUser;
    private Movie currentMovie;
    private List<String> previousPages = new ArrayList<>();

    /**
     * Initializes the current session fields
     */
    public Session() {
        this.login = false;
        this.currentPage = "homepage neautentificat";
        this.currentUser = null;
        this.currentMovie = null;
    }

    /**
     * for coding style
     */
    public List<String> getPreviousPages() {
        return previousPages;
    }
    /**
     * for coding style
     */
    public void setPreviousPages(final List<String> previousPages) {
        this.previousPages = previousPages;
    }
    /**
     * for coding style
     */
    public Movie getCurrentMovie() {
        return currentMovie;
    }
    /**
     * for coding style
     */
    public void setCurrentMovie(final Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    /**
     * for coding style
     */
    public User getCurrentUser() {
        return currentUser;
    }
    /**
     * for coding style
     */
    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }
    /**
     * for coding style
     */
    public boolean isLogin() {
        return login;
    }
    /**
     * for coding style
     */
    public void setLogin(final boolean login) {
        this.login = login;
    }
    /**
     * for coding style
     */
    public String getCurrentPage() {
        return currentPage;
    }
    /**
     * for coding style
     */
    public void setCurrentPage(final String currentPage) {
        this.currentPage = currentPage;
    }
}
