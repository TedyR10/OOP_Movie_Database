package observer;

/**
 * This class represents the observer main interface
 */
public interface Observer {
    /**
     * This method updates the observers
     * @param o movie
     * @param type type of action
     */
    void update(Object o, String type);
}
