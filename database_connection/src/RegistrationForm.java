import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;

public class RegistrationForm extends JDialog {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton submitButton;
    private JButton cancelButton;
    private JButton resetButton;
    private JPanel registerPanel;
    private JTextField textField2;

    public RegistrationForm(JFrame parent){
        super(parent);
        setTitle("Register a new account");
        setLocationRelativeTo(parent);
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(450, 474));
//


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the values from the text fields
                String name = textField1.getText();
                char[] password = passwordField1.getPassword();
                String passwordString = new String(password);
                String email = textField2.getText();

                // Validate password
                if (passwordString.contains(" ")) {
                    JOptionPane.showMessageDialog(null, "Password cannot contain spaces", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (password.length > 15 || password.length < 8) {
                    JOptionPane.showMessageDialog(null, "Password must be between 8 and 15 characters in length.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String upperCaseChars = "(.*[A-Z].*)";
                String lowerCaseChars = "(.*[a-z].*)";
                String numbers = "(.*[0-9].*)";
                String specialChars = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?].*$)";

                if (!passwordString.matches(upperCaseChars) || !passwordString.matches(lowerCaseChars)) {
                    JOptionPane.showMessageDialog(null, "Password must contain at least one uppercase and one lowercase letter", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (!passwordString.matches(numbers)) {
                    JOptionPane.showMessageDialog(null, "Password must contain at least one number", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (!passwordString.matches(specialChars)) {
                    JOptionPane.showMessageDialog(null, "Password must contain at least one special character", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Call the method to update the database
                connect_to_postgres.connection(name, passwordString, email);
                JOptionPane.showMessageDialog(null, "Registration Successful.");

                // Clear fields
                textField1.setText("");
                passwordField1.setText("");
                textField2.setText("");
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText(" ");
                passwordField1.setText("");
                textField2.setText("");
            }
        });

        setVisible(true);
    }
    public static void main(String[] args){
        RegistrationForm myForm = new RegistrationForm(null);
    }

}











