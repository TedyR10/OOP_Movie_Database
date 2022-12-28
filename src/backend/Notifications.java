package backend;

/**
 * This class represents a notification for the user
 */
public class Notifications {

    private String movieName;
    private String message;

    /**
     * Constructor for notifications, initializes mandatory fields
     * @param movie movie
     * @param newMessage newMessage
     */
    public Notifications(final String movie, final String newMessage) {
        this.movieName = movie;
        this.message = newMessage;
    }

    /**
     * for coding style
     */
    public String getMovieName() {
        return movieName;
    }
    /**
     * for coding style
     */
    public void setMovieName(final String movieName) {
        this.movieName = movieName;
    }
    /**
     * for coding style
     */
    public String getMessage() {
        return message;
    }
    /**
     * for coding style
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
