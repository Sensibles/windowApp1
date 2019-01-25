package service;

import model.AccountDetails;

import java.util.HashMap;
import java.util.Map;

public class AtmService {

    Map<String, AccountDetails> accounts = new HashMap<>();
    AccountDetails currentlyLoginAccount = null;

    public AccountDetails login(String login, String password) {
        AccountDetails accountDetails = accounts.get(login);
        if(accountDetails != null && accountDetails.getPassword().equals(password)) {
            currentlyLoginAccount = accountDetails;
            return new AccountDetails(currentlyLoginAccount);           //konstruktor kopiujacy, zabezpieczenie by ktos nie majtrowal przy koncie uzytkownika z zenwatrz.
        }
        else
            throw new RuntimeException("Podane dane są nieprawidłowe.");
    }

    public void logout() {
        currentlyLoginAccount = null;
    }

    public void register(String login, String password) {
        AccountDetails accountDetails = accounts.get(login);
        if(accountDetails != null)
            throw new RuntimeException("Podany login istnieje w bazie danych.");
        if(login.isEmpty() || password.isEmpty())
            throw new RuntimeException("Login i hasło muszą zostać wypełnione.");
        if(login.equals(password))
            throw new RuntimeException("Login i hasło nie mogą być takie same.");
        AccountDetails newAccountDetails = new AccountDetails(login, password);
        accounts.put(login, newAccountDetails);
    }

    public double deposit(double value) {
        validateLoggedUser();
        double initialBalance = currentlyLoginAccount.getBalance();
        double resultBalance = initialBalance + value;
        currentlyLoginAccount.setBalance(resultBalance);
        return resultBalance;
    }

    public double withdraw(double value) {
        validateLoggedUser();
        double initialBalance = currentlyLoginAccount.getBalance();
        double resultBalance = initialBalance - value;
        if(resultBalance < 0)
            throw new RuntimeException("Nie można tak dużej ilości środków. Max kwota to: " + initialBalance);
        currentlyLoginAccount.setBalance(resultBalance);
        return resultBalance;
    }

    private void validateLoggedUser() {
        if(currentlyLoginAccount == null){
            throw new RuntimeException("Nieautoryzowana operacja!");
        }
    }


}
