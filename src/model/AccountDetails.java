package model;

public class AccountDetails {
    private String login;
    private String password;
    private Double balance;

    public AccountDetails(String login, String password) {
        this.login = login;
        this.password = password;
        this.balance = 0d;
    }

    public AccountDetails(AccountDetails accountDetails) {
        this.login = accountDetails.login;
        this.password = accountDetails.password;
        this.balance = accountDetails.balance;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
