package model;

public class AccountDetails {
    private String login;
    private String password;
    private Double balance;

    //konstruktor odpala się gdy zakładamy konto
    public AccountDetails(String login, String password) { //konstruktor, przyjmuje login i password, wpisuje to do zmiennych skladowych klasy (pól)
        this.login = login;
        this.password = password;
        this.balance = 0d;  //wartość domyślna stanu konta podczas zakładania konta
    }
    //konstruktor kopiujący, kopiuje wartośći z bazowego obiektu do nowego.
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

    //aktualizuje stan konta
    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
