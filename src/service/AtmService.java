package service;

import model.AccountDetails;

import java.util.HashMap;
import java.util.Map;

public class AtmService {

    //mapa/kolekcja / baza zarejestrowanych  użytkowników
    Map<String, AccountDetails> accounts = new HashMap<>();

    //obecnie zalogowanego użytkownika
    AccountDetails currentlyLoginAccount = null;

    public AccountDetails login(String login, String password) { //dane podane przez uzytkownika podczas logowania
        AccountDetails accountDetails = accounts.get(login); //szuka w mapie konkretnego konta(accountDetails) dla podanego loginu
        if(accountDetails != null && accountDetails.getPassword().equals(password)) {
            currentlyLoginAccount = accountDetails;                                 //jeżeli podany login przez uzytkownika zgadza sie (jhest w mapie kont) i hasło z konta zgadza sie z hasłem podanym przez użytkownika, to przypisz to konto do obecnie zalogowanego uzytkownika
            return new AccountDetails(currentlyLoginAccount);                       //konstruktor kopiujacy, zabezpieczenie by ktos nie majtrowal przy koncie uzytkownika z zenwatrz.
        }
        else
            throw new RuntimeException("Podane dane są nieprawidłowe.");            //jeżeli login nie istnieje w bazie danych lub hasło sie nie zgadza to wyrzuć wyjątek o podanej treści.
    }

    public void logout() {
        currentlyLoginAccount = null; //kasujesz obecnie zalogowanego uzytkownika
    }

    public void register(String login, String password) {
        AccountDetails accountDetails = accounts.get(login); //
        if(accountDetails != null) // różny od null znaczy że istnieje
            throw new RuntimeException("Podany login istnieje w bazie danych.");
        if(login.isEmpty() || password.isEmpty())
            throw new RuntimeException("Login i hasło muszą zostać wypełnione.");
        if(login.equals(password))
            throw new RuntimeException("Login i hasło nie mogą być takie same.");
        AccountDetails newAccountDetails = new AccountDetails(login, password); //tu używasz konstruktora, podajesz login i hasło i tworzy się nowy użytkownik
        accounts.put(login, newAccountDetails); //dodajesz nowo utworzonego użytkownika do tej "bazy danych"

        /*
          if(login.isEmpty() || password.isEmpty() || password.length() <= 2)
            throw new RuntimeException("Login i hasło muszą zostać wypełnione. I musi mieć więcej niż 2 znaki");
         */
    }

    public double deposit(double value) { //depozyt na konto. Jak masz na start 20, zdeponujesz 15 (czyli value = 15), to masz na wyjściu (resultBalance = 35)
        validateLoggedUser();
        double initialBalance = currentlyLoginAccount.getBalance(); //pobierasz ile masz hajsu na koncie
        double resultBalance = initialBalance + value; //dodajesz hajs który chcesz deponować do tego co masz na koncie
        currentlyLoginAccount.setBalance(resultBalance); //uaktualniasz stan konta o dodany hajs
        return resultBalance;   //  zwracasz wartość, uaktualnionego stanu konta
    }

    //wypłacanie hajsu
    public double withdraw(double value) {
        validateLoggedUser();
        double initialBalance = currentlyLoginAccount.getBalance(); //pobierasz ile masz hajsu na koncie
        double resultBalance = initialBalance - value; //odejmujesz od hajsu na koncie, tyle ile chcesz wyplacic
        if(resultBalance < 0) //jezeli chcesz wyplacic wiecej niz masz, to rzuc wyjątkiem (czyli pokaz komunikat) o tym, że nie można wypłacić aż tyle
            throw new RuntimeException("Nie można wypłacić tak dużej ilości środków. Max kwota to: " + initialBalance);
        currentlyLoginAccount.setBalance(resultBalance);
        return resultBalance;
    }
    //to jest bajer, i możesz go wyjebać jak ci zaciemnia kod.
    private void validateLoggedUser() {
        if(currentlyLoginAccount == null){
            throw new RuntimeException("Nieautoryzowana operacja!");
        }
    }


}
