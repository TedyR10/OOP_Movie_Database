package input;

/**
 * This class is used for getting the inputs from files
 */
public final class CredentialsInput {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private int balance;

    public CredentialsInput() {
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getCountry() {
        return country;
    }

    public int getBalance() {
        return balance;
    }
}
