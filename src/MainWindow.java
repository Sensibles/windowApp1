import model.AccountDetails;
import service.AtmService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    private JPanel panel1;
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

    private AtmService atmService = new AtmService();

    private void createUIComponents() {
        // TODO: place custom component creation code here
        panel1 = new JPanel();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MazeBank ATM");
        frame.setContentPane(new MainWindow().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public MainWindow() {
        setLoginView();
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAction(() -> login());
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
        String login = loginField.getText();
        String password = passwordField.getText();
        AccountDetails accountDetails = atmService.login(login, password);
        JOptionPane.showMessageDialog(panel1, "Witaj, " + accountDetails.getLogin());
        setAtmView(accountDetails);
    }

    private void logout() {
        atmService.logout();
        setLoginView();
    }

    private void register() {
        String login = loginField.getText();
        String password = passwordField.getText();
        atmService.register(login, password);
        JOptionPane.showMessageDialog(panel1, "Pomyślnie zarejestrowano użytkownika: " + login);
    }

    private void deposit() {
        String balanceValue = newValueField.getText();
        double parsedValue = parseStringToDouble(balanceValue);
        double newValue = atmService.deposit(parsedValue);
        balanceReadonlyField.setText("Stan konta: " + newValue);
    }

    private void withdraw() {
        String balanceValue = newValueField.getText();
        double parsedValue = parseStringToDouble(balanceValue);
        double newValue = atmService.withdraw(parsedValue);
        balanceReadonlyField.setText("Stan konta: " + newValue);
    }

    private double parseStringToDouble(String value) {
        try {
            double parsedValue = Double.valueOf(value);
            if(parsedValue < 0)
                throw new RuntimeException("Podana wartość musi być dodatnia!");
            return parsedValue;
        } catch (NumberFormatException ex){
            throw new RuntimeException("Podana wartość powinna składać się tylko z cyfr!");
        }
    }

    private void setLoginView() {
        Login.setVisible(true);
        atm.setVisible(false);
    }

    private void setAtmView(AccountDetails accountDetails) {
        loggedLabel.setText("Zalogowano: " + accountDetails.getLogin());
        balanceReadonlyField.setText("Stan konta: " + accountDetails.getBalance());
        newValueField.setText("");
        Login.setVisible(false);
        atm.setVisible(true);
    }


}
