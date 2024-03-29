package input;

/**
 * This class is used for getting the inputs from files
 */
public final class ActionsInput {
    private String type;
    private String page;
    private String feature;
    private CredentialsInput credentials;
    private String startsWith;
    private String count;
    private String movie;
    private String rate;
    private FiltersInput filters;
    private String subscribedGenre;
    private MovieInput addedMovie;
    private String deletedMovie;
    public ActionsInput() {
    }

    public String getDeletedMovie() {
        return deletedMovie;
    }

    public MovieInput getAddedMovie() {
        return addedMovie;
    }

    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    public String getType() {
        return type;
    }

    public String getPage() {
        return page;
    }

    public String getFeature() {
        return feature;
    }

    public CredentialsInput getCredentials() {
        return credentials;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public String getCount() {
        return count;
    }

    public String getMovie() {
        return movie;
    }

    public String getRate() {
        return rate;
    }

    public FiltersInput getFilters() {
        return filters;
    }
}
