import model.AccountDetails;
import service.AtmService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//klasa cześciowo generowana przez kreator okienka
public class MainWindow {
    //--- to nie napisalem ja
    private JPanel panel1; // głowne okno
    private JTextField loginField;
    private JButton login;
    private JPasswordField passwordField;
    private JPanel Login;
    private JButton register;
    private JLabel label;
    private JPanel atm;
    private JTextField balanceReadonlyField;
    private JTextField newValueField;
    private JButton deposit;
    private JButton withdraw;
    private JButton logout;
    private JLabel loggedLabel;
    // -------------------

    private AtmService atmService = new AtmService();

    // metoda wygenerowala sie sama
    private void createUIComponents() {
        // TODO: place custom component creation code here
        //to dopisalem ja, bo bez tego sie nie uruchomi okienko. (wyczytalem to w necie)
        panel1 = new JPanel();
    }

    public static void main(String[] args) {
        // to jest kod konieczny do odpalenia apki (znalazlem w necie)
        JFrame frame = new JFrame("MazeBank ATM");
        frame.setContentPane(new MainWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public MainWindow() {
        //listener to po prostu odsyłacz do konkretnego skryptu
        setLoginView();
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAction(() -> login());
               //wyrażenie lambda, "nie bardzo rozumiem, kolega mi tłumaczył" ewentualnei poczytaj "java lambda, java 8 lambda " () ->
            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAction(() -> logout());
            }
        });

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAction(() -> register());
            }
        });

        deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAction(() -> deposit());
            }
        });

        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAction(() -> withdraw());
            }
        });
    }

    private void performAction(Runnable action){
        try{
            action.run();
        } catch (RuntimeException ex){
            JOptionPane.showInternalMessageDialog(panel1, ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void login(){
        String login = loginField.getText(); //pobierasz z pola tekstowego login
        String password = passwordField.getText(); //pobierasz z pola tekstowego(passwordfield) hasło. To jest przekreśłone, bo ta funkcja jest przestarzała
        AccountDetails accountDetails = atmService.login(login, password); // wykonaj logowanie z klasy AtmService i pobierz zalogowanego użytkownika
        JOptionPane.showMessageDialog(panel1, "Witaj, " + accountDetails.getLogin()); //wyświetl komunikat "Witaj, {user}"
        setAtmView(accountDetails); //przestań pokazywać pole tekstowe login i hasło i przyciski zaloguj, zarejestruj, pokaż widok "bankomatu"
    }

    private void logout() {
        atmService.logout();
        setLoginView(); //wylogowujesz sie, wiec przechodzisz do wiodku 'login' by umożliwić zalogowanie się innemu użytkownikowi
    }

    private void register() {  //String - typ tekstowy
        String login = loginField.getText();
        String password = passwordField.getText();
        atmService.register(login, password); //wywołujesz klase atmService i funkcje(metode) register.  AtmService to ten pierwszy plik
        JOptionPane.showMessageDialog(panel1, "Pomyślnie zarejestrowano użytkownika: " + login); //komunikat jak popraqnie zarejestrowano
    }
//deponowanie pieniedzy
    private void deposit() {
        String balanceValue = newValueField.getText(); //pobierasz wartość tekstową z pola tekstowego
        double parsedValue = parseStringToDouble(balanceValue); //transformujesz wartość tekstową "12.10" na wartość liczbową 12.10. Przy okazji walidujesz czy użytkownik podał cyfry a nie np 12CHUJDUPA10
        double newValue = atmService.deposit(parsedValue); //jeżeli podał prawidłową to ustaw nową wartość konta o powiększoną kwote(balanceValue + parsedValue)
        balanceReadonlyField.setText("Stan konta: " + newValue); //uaktualnij stan konta w okienku o nową kwote
    }
    //DOKŁADNIE TO SAMO, ALE ODEJMUJESZ HAJS Z KONTA, A NIE DODAJESZ
    private void withdraw() {
        String balanceValue = newValueField.getText();
        double parsedValue = parseStringToDouble(balanceValue);
        double newValue = atmService.withdraw(parsedValue);
        balanceReadonlyField.setText("Stan konta: " + newValue);
    }
//rzutujesz wartość tekstową na liczbową. Czyli jak masz String = "12", to robisz z tego double = 12;
    //double wartość zmiennoprzecinkowa, żebyś mógł zdeponować np 12.50zł
    private double parseStringToDouble(String value) {
        try {
            double parsedValue = Double.valueOf(value); //double.valueOf to jest funkcja javy która przerabia string na double.
            if(parsedValue < 0)
                throw new RuntimeException("Podana wartość musi być dodatnia!"); //jeżeli użytkownik podał "-100", to inofrmujemy go że wartość ujemna a takiej nie może podać
            return parsedValue;
        } catch (NumberFormatException ex){ //Jeżeli użytkownik podał zły string "10HUJ20" to nie może być zrzutowany na wartość liczbową, więc go informujemy o tym.
            throw new RuntimeException("Podana wartość powinna składać się tylko z cyfr!");
        }
    }
//pokaż widok "logowania"
    private void setLoginView() {
        Login.setVisible(true);  // login to jPane (pole logowania, podokienko?)
        atm.setVisible(false); // atm( czyli widok bankomatu
    }
    //przestań pokazywać pole tekstowe login i hasło i przyciski zaloguj, zarejestruj, pokaż widok "bankomatu"
    private void setAtmView(AccountDetails accountDetails) {
        loggedLabel.setText("Zalogowano: " + accountDetails.getLogin());
        balanceReadonlyField.setText("Stan konta: " + accountDetails.getBalance());
        newValueField.setText("");
        Login.setVisible(false);
        atm.setVisible(true);
    }


}
