package Book;

import javax.swing.*;
import java.awt.*;

public class LoginPage {


    static String email;
    String password;

    JFrame frame;
    JPanel panel;
    JTextField usernameFeild;
    JPasswordField passwordField;
    JLabel usernameLabel;
    JLabel passwordLabel;
    JLabel iconLabel;
    JLabel loginLabel;
    JButton loginButton;
    JLabel signupLabel;
    JButton signUpButton;


    LoginPage() {
        // Setting Up variables
        frame = new JFrame();
        panel = new JPanel();
        usernameFeild = new JTextField();
        passwordField = new JPasswordField();
        usernameLabel = new JLabel("Email");
        passwordLabel = new JLabel("Password");
        loginButton = new JButton("Login");
        signupLabel = new JLabel("Create new Account");
        signUpButton = new JButton("Sign up");



        // Setting up WelcomeLabel
        loginLabel = new JLabel("Login");
        loginLabel.setForeground(Color.WHITE);
        loginLabel.setBounds(580, 135, 200, 40);
        loginLabel.setFont(new Font("Times New Roman", Font.PLAIN, 33));

        // Setting up Username Label and Field
        usernameLabel.setBounds(475, 200, 70, 20);
        usernameLabel.setForeground(Color.WHITE);
        usernameFeild.setBounds(475, 220, 300, 30);
        usernameFeild.setBackground(new Color(15,15,15));
        usernameFeild.setForeground(Color.WHITE);
        usernameFeild.setCaretColor(Color.WHITE);

        // Setting up Password Label and Field
        passwordLabel.setBounds(475, 260, 70, 20);
        passwordLabel.setForeground(Color.WHITE);
        passwordField.setBounds(475, 280, 300, 30);
        passwordField.setBackground(new Color(15,15,15));
        passwordField.setForeground(Color.WHITE);
        //passwordField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        passwordField.setCaretColor(Color.WHITE);

        // Setting up Login Button
        loginButton.setBounds(475, 335, 300, 30);
        loginButton.setFocusable(false);
        loginButton.setBackground(new Color(112, 191, 255, 255));

        // Action Listener For Login Button
        loginButton.addActionListener(e -> {
             email = usernameFeild.getText();
             password = new String(passwordField.getPassword());

            try {
                Conn jdbc = new Conn();
                boolean[] loginResult = jdbc.search_Login(email, password);
                if (loginResult[0]) { // Login successful
                    if (loginResult[1]) {
                        // Open admin interface
                        frame.dispose();
                        new AdminInterface();
                    } else {

                        frame.dispose();
                        new UserInterface();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Please check your email and password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An error occurred while logging in: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Optional: Print the stack trace for debugging purposes
            }
        });

        // Setting up Signup Button and Label
        signupLabel.setBounds(520, 415, 200, 20);
        signupLabel.setForeground(Color.WHITE);
        signupLabel.setFont(new Font(null, Font.PLAIN, 10));

        signUpButton.setBounds(610, 415, 60, 20);
        signUpButton.setFont(new Font(null, Font.PLAIN, 10));
        signUpButton.setFocusable(false);
        signUpButton.setForeground(new Color(112, 191, 255, 255));
        signUpButton.setBorder(BorderFactory.createLineBorder(new Color(15,15,15)));
        signUpButton.setBackground(new Color(15,15,15));

        signUpButton.addActionListener(e -> {
            if (e.getSource() == signUpButton) {
                frame.dispose();
                new SignUpPage();
            }
        });

        // Adding everything to the panel
        panel.setLayout(null);

        panel.add(loginLabel);
        panel.add(usernameLabel);
        panel.add(passwordLabel);
        panel.add(usernameFeild);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(signUpButton);
        panel.add(signupLabel);
        panel.setBackground(new Color(15,15,15));
        frame.add(panel, BorderLayout.CENTER);

        // Setting Up Frame
        frame.setSize(1350, 745); // Set frame size to 1000x700
        frame.setVisible(true);
        frame.setTitle("Book Store");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
