package Book;

//SignUp page



import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUpPage {



    JFrame frame ;
    JPanel panel ;
    JTextField usernameFeild ;
    JTextField emailFeild;
    JPasswordField passwordField;
    JPasswordField confirmpasswordField;
    JLabel usernameLabel ;
    JLabel emailLabel;
    JLabel passwordLabel ;
    JLabel ConfirmpasswordLabel;
    JLabel signupLabel;

    JButton signUpButton;
    JButton backButton;

    SignUpPage() {


        // Setting Up variables
        frame = new JFrame();
        panel = new JPanel();
        usernameFeild = new JTextField();
        emailFeild = new JTextField();
        passwordField = new JPasswordField();
        confirmpasswordField = new JPasswordField();
        usernameLabel = new JLabel("Username");
        emailLabel = new JLabel("Email");
        passwordLabel = new JLabel("Password");
        ConfirmpasswordLabel = new JLabel("Confirm Password");
        signUpButton = new JButton("Sign up");
        backButton = new JButton("<");



        //Setting  Sign up
        signupLabel = new JLabel("Sign Up");
        signupLabel.setForeground(Color.WHITE);
        signupLabel.setBounds(580,108,200,30);
        signupLabel.setFont(new Font("Times New Roman",Font.PLAIN,25));


        //SEtting up Username LAbel And Feild
        usernameLabel.setBounds(475,160,70,20);
        usernameLabel.setForeground(Color.WHITE);
        usernameFeild.setBounds(475,180,300,30);
        usernameFeild.setBackground(new Color(15,15,15));
        usernameFeild.setForeground(Color.WHITE);
        usernameFeild.setCaretColor(Color.WHITE);



        //setting Up email Label and Feild
        emailLabel.setBounds(475,220,70,20);
        emailLabel.setForeground(Color.WHITE);
        emailFeild.setBounds(475,240,300,30);
        emailFeild.setBackground(new Color(15,15,15));
        emailFeild.setForeground(Color.WHITE);
        emailFeild.setCaretColor(Color.WHITE);



        //Setting up password Label and Feild
        passwordLabel.setBounds(475,280,70,20);
        passwordLabel.setForeground(Color.WHITE);
        passwordField.setBounds(475,300,300,30);
        passwordField.setBackground(new Color(15,15,15));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);

        ConfirmpasswordLabel.setBounds(475,340,130,20);
        ConfirmpasswordLabel.setForeground(Color.WHITE);
        confirmpasswordField.setBounds(475,360,300,30);
        confirmpasswordField.setBackground(new Color(15,15,15));
        confirmpasswordField.setForeground(Color.WHITE);
        confirmpasswordField.setCaretColor(Color.WHITE);

        backButton.setBounds(1,1,40,30);
        backButton.setFocusable(false);
        backButton.setForeground(Color.white);
        backButton.setFont(new Font(null,Font.PLAIN,8));
        backButton.setBackground(new Color(15,15,15));
        backButton.addActionListener(e ->{
            if(e.getSource()==backButton){
                frame.dispose();
                new LoginPage();
            }
        });



        //Setting up Login Button
        signUpButton.setBounds(475,420,300,30);
        signUpButton.setFocusable(false);
        signUpButton.setBackground(new Color(112,191,255,255));



        signUpButton.addActionListener(e ->{
            Conn jdbc = new Conn();
            try {
                String email = emailFeild.getText();
                String uname= usernameFeild.getText();
                String password = String.valueOf(passwordField.getPassword());
                String confirmpass = String.valueOf(confirmpasswordField.getPassword());
                if (jdbc.search(email)){
                    JOptionPane.showMessageDialog(panel, "Account already exixt", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    if(confirmpass.equals(password)){
                        jdbc.insertUserinfo(uname, email, password);
                        System.out.println("Account created...!");
                    }else{
                        JOptionPane.showMessageDialog(panel, "Password does not Match", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }

                }
            } catch (Exception ex) {
                Logger.getLogger(SignUpPage.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(e.getSource().equals(signUpButton))
            {
                frame.dispose();
                new LoginPage();
            }


        });


        //Adding everyThing in panel
        panel.setLayout(null);
        panel.add(signupLabel);
        panel.add(usernameLabel);
        panel.add(emailLabel);
        panel.add(passwordLabel);
        panel.add(usernameFeild);
        panel.add(emailFeild);
        panel.add(passwordField);
        panel.add(ConfirmpasswordLabel);
        panel.add(confirmpasswordField);
        panel.add(signUpButton);
        panel.add(backButton);
        panel.setBackground(new Color(15,15,15));
        frame.add(panel,BorderLayout.CENTER);

        //Setting Up Frame
        frame.setSize(1350, 745);
        frame.setVisible(true);
        frame.setTitle("Book Store");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}


