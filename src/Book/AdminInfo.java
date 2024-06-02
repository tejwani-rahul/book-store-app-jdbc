package Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;


public class AdminInfo extends JFrame {
    JFrame frame;
    JPanel topPanel;
    JPanel mainPanel;

    JButton editButton;


    JLabel confirmPasswordLabel;

    JLabel usernameLabel;
    JLabel emailLabel;
    JLabel passwordLabel;




    JTextField usernameField;
    JTextField emailField;
    JTextField passwordField;

    JTextField confirmPasswordField;



    Conn conn = new Conn();
    public AdminInfo() throws MalformedURLException {

        frame = new JFrame();
        mainPanel = new JPanel();
        topPanel = new JPanel();

        setupUI();


        try {
            User user = conn.retrieveUserById(conn.user);

            // Display user information in GUI components
            usernameField.setText(user.getName());
            emailField.setText(user.getEmail());
            passwordField.setText(user.getPassword());
            confirmPasswordField.setText(user.getPassword());


        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred while inserting the book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Optional: Print the stack trace for debugging purposes
        }











        // Adding every thing in Frame
        frame.add(mainPanel);
        frame.add(topPanel);



        // Setting up the Frame
        frame.setSize(1350, 745);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setTitle("Book Store");
        frame.getContentPane().setBackground(new Color(15,15,15));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setupUI()  {


        topPanel = new JPanel(); // New top panel
        topPanel.setBounds(2, 2, 1335, 60); // Set bounds
        topPanel.setBackground(new Color(15, 15, 15)); // Set background color
        //topPanel.setBorder(BorderFactory.createLineBorder(new Color(32, 33, 35, 255)));
        topPanel.setLayout(null);




        ImageIcon userIcon = new ImageIcon("D:\\BSCS-IV\\BookStore\\src\\user.png"); // Replace "cart_icon.png" with the path to your icon file

        Image userimg = userIcon.getImage();
        Image userresizedImg = userimg.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Adjust the width and height as needed
        userIcon = new ImageIcon(userresizedImg);
        JButton usericonButton = new JButton(userIcon);
        usericonButton.setBounds(1080,7,40,40);
        usericonButton.setFocusable(false);
        usericonButton.setBackground(new Color(15,15,15));
        usericonButton.setBorder(BorderFactory.createLineBorder(new Color(15, 15, 15)));
        usericonButton.setVerticalTextPosition(SwingConstants.CENTER);
        usericonButton.addActionListener(e->{
            frame.dispose();
            try {
                new AdminInfo();
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        });
        topPanel.add(usericonButton);

//        JLabel usernameLabel = new JLabel(Conn.username);
//        usernameLabel.setBounds(35,150,125,25);
//        usernameLabel.setForeground(Color.WHITE);
//        usericonLabel.setFont(new Font("",Font.PLAIN,20));
//        topPanel.add(usernameLabel);





        ImageIcon homeIcon = new ImageIcon("D:\\BSCS-IV\\BookStore\\src\\home.png"); // Replace "cart_icon.png" with the path to your icon file

        Image homeimg = homeIcon.getImage();
        Image homeresizedImg = homeimg.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Adjust the width and height as needed
        homeIcon = new ImageIcon(homeresizedImg);

        JButton homeButton = new JButton(homeIcon);
        homeButton.setBounds(1130, 7, 40, 40);
        homeButton.setFocusable(false);
        homeButton.setBackground(new Color(15,15,15));
        homeButton.setBorder(BorderFactory.createLineBorder(new Color(15, 15, 15)));
        homeButton.setVerticalTextPosition(SwingConstants.CENTER);
        homeButton.addActionListener(e -> {
            frame.dispose();
            new AdminInterface();
        });

        topPanel.add(homeButton);




        ImageIcon insertIcon = new ImageIcon("D:\\BSCS-IV\\BookStore\\src\\insert.png"); // Replace "insert_icon.png" with the path to your icon file

// Resize the icon
        Image insertImg = insertIcon.getImage();
        Image insertresizedInsertImg = insertImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Adjust the width and height as needed
        insertIcon = new ImageIcon(insertresizedInsertImg);

// JButton for InsertBooks
        JButton insertBooksButton = new JButton( insertIcon); // Add spaces before the text to create padding
        insertBooksButton.setBounds(1180, 7, 40, 40);
        insertBooksButton.setFocusable(false);
        insertBooksButton.setBackground(new Color(15, 15, 15));
        insertBooksButton.setBorder(BorderFactory.createLineBorder(new Color(15,15,15)));
        insertBooksButton.addActionListener(e -> {
            frame.dispose();
            new InsertBooks();
        });
        topPanel.add(insertBooksButton);


// Load the icon
        ImageIcon hiddenIcon = new ImageIcon("D:\\BSCS-IV\\BookStore\\src\\hidden.png"); // Replace "hidden_icon.png" with the path to your icon file
        Image hiddenImg = hiddenIcon.getImage();
        Image resizedHiddenImg = hiddenImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Adjust the width and height as needed
        hiddenIcon = new ImageIcon(resizedHiddenImg);

// JButton for Hidden Books
        JButton hiddenBooksButton = new JButton( hiddenIcon); // Add spaces before the text to create padding
        hiddenBooksButton.setBounds(1230, 7, 40, 40);
        hiddenBooksButton.setFocusable(false);
        hiddenBooksButton.setBackground(new Color(15, 15, 15));
        hiddenBooksButton.setBorder(BorderFactory.createLineBorder(new Color(15,15,15)));
        hiddenBooksButton.addActionListener(e -> {
            frame.dispose();
            new HiddenBooks();
        });
        topPanel.add(hiddenBooksButton);




        // JButton for Logout
        ImageIcon logoutIcon = new ImageIcon("D:\\BSCS-IV\\BookStore\\src\\logout.png"); // Replace "logout_icon.png" with the path to your icon file
        Image logoutimg = logoutIcon.getImage();
        Image logoutresizedImg = logoutimg.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Adjust the width and height as needed
        logoutIcon = new ImageIcon(logoutresizedImg);

// JButton for Logout
        JButton logoutButton = new JButton( logoutIcon); // Add spaces before the text to create padding
        logoutButton.setBounds(1278, 7, 40, 40);
        logoutButton.setFocusable(false);
        logoutButton.setBackground(new Color(15, 15, 15));
        logoutButton.setBorder(BorderFactory.createLineBorder(new Color(15, 15, 15)));
        logoutButton.setVerticalTextPosition(SwingConstants.CENTER); // Center the text vertically

        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginPage();
        });

        topPanel.add(logoutButton);






        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("D:\\BSCS-IV\\BookStore\\src\\font2.otf")).deriveFont(28f);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle font loading exception
        }

        JLabel bookLabel = new JLabel("Book");
        bookLabel.setBounds(2, 5, 148, 40);
        bookLabel.setFont(customFont);
        bookLabel.setForeground(new Color(112, 191, 255, 255));
        topPanel.add(bookLabel);

        JLabel storeLabel = new JLabel("Store");
        storeLabel.setBounds(120, 5, 148, 40);
        storeLabel.setFont(customFont);
        storeLabel.setForeground(Color.WHITE);
        topPanel.add(storeLabel);



        JTextField searchField = new JTextField("Search");
        searchField.setBounds(300, 10, 650, 35);
        searchField.setBackground(Color.black);
        searchField.setForeground(Color.WHITE);
        searchField.setCaretColor(Color.WHITE);
        searchField.setBorder(BorderFactory.createLineBorder(new Color(68, 70, 84, 255)));
        searchField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (searchField.getText().equals("Search")) {
                    searchField.setText("");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search");
                }
            }
        });

        topPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(960, 10, 100, 35);
        searchButton.setFocusable(false);
        searchButton.setBackground(Color.black);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createLineBorder(new Color(68, 70, 84, 255)));
        searchButton.addActionListener(e -> {



            String text = searchField.getText();
            if (!text.equals("Search") || text.equals("")) {

                frame.dispose(); // Close the cart window
                AdminInterface adminInterface = new AdminInterface();
                adminInterface.searchBooks(text); // Pass the search query to the UserInterface
            }
        });
        topPanel.add(searchButton);


        // MainPanel

        mainPanel.setBounds(2, 60, 1132, 660);
        mainPanel.setBackground(new Color(15,15,15));
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(15,15,15)));
        mainPanel.setLayout(null);




        JLabel userTextLabel = new JLabel("User");
        userTextLabel.setBounds(100,50,300,40);
        userTextLabel.setForeground(Color.WHITE);
        userTextLabel.setFont(new Font("",Font.BOLD,35));
        mainPanel.add(userTextLabel);





        ImageIcon icon = new ImageIcon("D:\\BSCS-IV\\BookStore\\src\\bigusericon.png");
        Image image_icon = icon.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);
        ImageIcon Scaled_icon = new ImageIcon(image_icon);
        JLabel imageLabel = new JLabel(Scaled_icon);
        imageLabel.setBounds(20, 100, 300, 500);
        mainPanel.add(imageLabel);










//        updateLabel = new JLabel("Update Book");
//        updateLabel.setBounds(50,50,300,40);
//        updateLabel.setForeground(Color.WHITE);
//        updateLabel.setFont(new Font("",Font.BOLD,35));


        usernameLabel = new JLabel("Name");
        usernameLabel.setBounds(400,120,70,20);
        usernameLabel.setForeground(Color.WHITE);

        usernameField = new JTextField();
        usernameField.setBounds(400,140,300,30);
        usernameField.setBackground(new Color(15,15,15));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);


        emailLabel = new JLabel("Email");
        emailLabel.setBounds(400,180,70,20);
        emailLabel.setForeground(Color.WHITE);

        emailField = new JTextField();
        emailField.setBounds(400,200,300,30);
        emailField.setBackground(new Color(15,15,15));
        emailField.setForeground(Color.WHITE);
        emailField.setCaretColor(Color.WHITE);


        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(400,240,70,20);
        passwordLabel.setForeground(Color.WHITE);

        passwordField = new JTextField();
        passwordField.setBounds(400,260,300,30);
        passwordField.setBackground(new Color(15,15,15));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);




        confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setBounds(400,300,100,20);
        confirmPasswordLabel.setForeground(Color.WHITE);

        confirmPasswordField = new JTextField();
        confirmPasswordField.setBounds(400,320,300,30);
        confirmPasswordField.setBackground(new Color(15,15,15));
        confirmPasswordField.setForeground(Color.WHITE);
        confirmPasswordField.setCaretColor(Color.WHITE);




        editButton = new JButton("Edit");
        editButton.setBounds(500,420,100,40);
        editButton.setForeground(Color.WHITE);
        editButton.setFocusable(false);
        editButton.setBackground(new Color(112,191,255,255));

        editButton.addActionListener(e ->{
            // Extract data from text fields
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getText());
            String confirmPassword = new String(confirmPasswordField.getText());

            try {
                String currentUserEmail = conn.retrieveUserById(Conn.user).getEmail();

                if (!email.equals(currentUserEmail) && conn.search(email)) {
                    JOptionPane.showMessageDialog(mainPanel, "Account already exists", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (confirmPassword.equals(password)) {
                        conn.updateUser(Conn.user, username, password, email);
                        System.out.println("Account info updated...!");

                        // Show message dialog for successful update
                        JOptionPane.showMessageDialog(mainPanel, "Account information updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Password does not match", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An error occurred while updating the account: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Optional: Print the stack trace for debugging purposes
            }
        });






        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        mainPanel.add(confirmPasswordLabel);
        mainPanel.add(confirmPasswordField);

        mainPanel.add(editButton);


    }




}
