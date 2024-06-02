package Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminBookdetail extends JFrame {

    Conn conn = new Conn();
    ArrayList<Book> bookData = new ArrayList<>(); // Store book data

    JFrame frame;
    JPanel topPanel;
    JPanel panel;

    JButton hidebookButton;
    JTextArea quantityNum;

    int quantity = 1; // Initial quantity
    static int Userid;

    public AdminBookdetail(Book book) throws MalformedURLException {
        frame = new JFrame();




        setupUI();
        displayBook(book);


        frame.add(topPanel);
        frame.add(panel);

        // Setting up the Frame
        frame.setSize(1350, 745);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setTitle("Book Store");
        frame.getContentPane().setBackground(new Color(15,15,15));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void displayBook(Book book) throws MalformedURLException {


        panel = new JPanel();
        panel.setBounds(2, 60, 1335, 700);
        panel.setBackground(new Color(15, 15, 15));
        panel.setBorder(BorderFactory.createLineBorder(new Color(15, 15, 15)));
        panel.setLayout(null);

        ImageIcon icon = new ImageIcon(new URL(book.getImageUrl()));
        Image image_icon = icon.getImage().getScaledInstance(450, 600, Image.SCALE_SMOOTH);
        ImageIcon Scaled_icon = new ImageIcon(image_icon);
        JLabel imageLabel = new JLabel(Scaled_icon);
        imageLabel.setBounds(20, 20, 450, 600);
        panel.add(imageLabel);

// Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(480, 20, 800, 120);
        titlePanel.setBackground(new Color(15, 15, 15));
        titlePanel.setBorder(BorderFactory.createLineBorder(new Color(15, 15, 15)));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS)); // Use BoxLayout along Y_AXIS

// Title Label with word wrapping
        JLabel titleLabel = new JLabel("<html><body style='width:600px'>" + book.getTitle() + "</body></html>");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26)); // Set font size and style
        titlePanel.add(titleLabel); // Add the title label to the title panel

        panel.add(titlePanel); // Add the title panel to the main panel


        JLabel descriptionTextLabel = new JLabel("Description:");
        descriptionTextLabel.setForeground(Color.WHITE);
        descriptionTextLabel.setBounds(480, 440, 100, 30);
        descriptionTextLabel.setFont(new Font("Arial", Font.BOLD, 15)); // Set font size
        panel.add(descriptionTextLabel);


        JTextArea descriptionTextArea = new JTextArea(book.getDescription());
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setBackground(new Color(15, 15, 15));
        descriptionTextArea.setForeground(Color.WHITE);
        descriptionTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(descriptionTextArea);
        scrollPane.setBounds(480, 470, 650, 150);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scrollPane);


//        JLabel descriptionLabel = new JLabel("<html><body style='width:500px'>" + book.getDescription() + "</body></html>");
//        descriptionLabel.setForeground(Color.WHITE);
//        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font size and style
//        descriptionPanel.add(descriptionLabel); // Add the description label to the description panel
//
//        panel.add(descriptionPanel); // Add the description panel to the main panel


        JLabel authorLabel = new JLabel("Author: " + book.getAuthor());
        authorLabel.setForeground(Color.WHITE);
        authorLabel.setBounds(480, 140, 300, 30);
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // Set font size
        panel.add(authorLabel);

        JLabel priceLabel = new JLabel("Price: $" + book.getPrice());
        priceLabel.setForeground(Color.WHITE);
        priceLabel.setBounds(480, 190, 200, 30);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // Set font size
        panel.add(priceLabel);


        JLabel stockLabel = new JLabel("Stock: ");
        stockLabel.setForeground(Color.WHITE);
        stockLabel.setBounds(480, 240, 150, 30); // Adjust position and size
        stockLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font size
        panel.add(stockLabel);

        JLabel InOrNotstockLabel = new JLabel();
        InOrNotstockLabel.setBounds(560, 240, 200, 30); // Adjust position and size
        InOrNotstockLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font size
        panel.add(InOrNotstockLabel);

        if (book.getStock() > 0) {
            InOrNotstockLabel.setText("In Stock");
            InOrNotstockLabel.setForeground(Color.GREEN);
        } else {
            InOrNotstockLabel.setText("Not In Stock");
            InOrNotstockLabel.setForeground(Color.RED);

        }



        // Add to Cart Button
        JButton updateButton = new JButton("Update");
        updateButton.setBounds(500, 370, 160, 35); // Adjust position and size as needed
        updateButton.setFocusable(false);
        updateButton.setBackground(new Color(15, 15, 15));
        updateButton.setFont(new Font("Arial", Font.PLAIN, 16));
        //addToCartButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        updateButton.setForeground(Color.WHITE);
        updateButton.addActionListener(e -> {
            frame.dispose();
            try {
                new UpdateBook(book);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        });

        panel.add(updateButton);


// Order Now Button
        hidebookButton = new JButton("Hide Book");
        hidebookButton.setBounds(700, 370, 160, 35); // Adjust position and size as needed
        hidebookButton.setFocusable(false);
        hidebookButton.setBackground(new Color(15, 15, 15));
        hidebookButton.setFont(new Font("Arial", Font.PLAIN, 16));
        //orderNowButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        hidebookButton.setForeground(Color.WHITE);
        hidebookButton.addActionListener(e -> {

            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to hide this book?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                try {
                    conn.updateBookStatus(book.getBookid(),"Hidden");
                    conn.deleteHiddenBookFromCart(book.getBookid());

                    JOptionPane.showMessageDialog(null, "Book hidden successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException ex) {
                    // Handle any SQL exceptions
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred while hidding the book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(hidebookButton);
    }

    private void setupUI(){

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

    }


}