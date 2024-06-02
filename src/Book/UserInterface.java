package Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class UserInterface extends JFrame {
    // Define class variables and components
    private JFrame frame;
    private JPanel topPanel;
    private JPanel mainPanel;
   // private JPanel topPanel;
    private JButton searchButton;
    private JTextField searchField;
    private JScrollPane scrollPane;
    private Conn conn;
    int quantity = 1;

    public UserInterface() {
        frame = new JFrame();
        conn = new Conn();

        // Initialize components and setup UI
        setupUI();
        displayAllBooks(); // Display all books by default

        // Adding components to the frame

        frame.add(topPanel);
        frame.add(scrollPane);

        // Setting up the Frame
        frame.setSize(1350, 740);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setTitle("Book Store");
        frame.getContentPane().setBackground(new Color(15,15,15));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setupUI() {
        // SidePanel
//        sidePanel = new JPanel();
//        sidePanel.setBounds(2, 2, 197, 700);
//        sidePanel.setBackground(Color.black);
//        sidePanel.setBorder(BorderFactory.createLineBorder(new Color(32, 33, 35, 255)));
//        sidePanel.setLayout(null);


        topPanel = new JPanel(); // New top panel
        topPanel.setBounds(2, 2, 1335, 60); // Set bounds
        topPanel.setBackground(new Color(15, 15, 15)); // Set background color
        //topPanel.setBorder(BorderFactory.createLineBorder(new Color(32, 33, 35, 255)));
        topPanel.setLayout(null);


        // MainPanel
        mainPanel = new JPanel(new GridLayout(0, 6, 15, 15)); // Use GridLayout with 4 columns and spacing
        mainPanel.setBackground(new Color(15, 15, 15));
        //mainPanel.setBackground(new Color(68, 70, 84, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));



        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBounds(2, 60, 1332, 645); // Adjusted bounds to fit the frame size
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scroll bar
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(15, 15, 15)));
        scrollPane.getViewport().setBackground(new Color(32, 33, 35, 255)); // Set background color of scroll pane


    
        ImageIcon userIcon = new ImageIcon("D:\\BSCS-IV\\BookStore\\src\\user.png"); // Replace "cart_icon.png" with the path to your icon file

        Image userimg = userIcon.getImage();
        Image userresizedImg = userimg.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Adjust the width and height as needed
        userIcon = new ImageIcon(userresizedImg);
        JButton usericonButton = new JButton(userIcon);
        usericonButton.setBounds(1100,7,40,40);
        usericonButton.setFocusable(false);
        usericonButton.setBackground(new Color(15,15,15));
        usericonButton.setBorder(BorderFactory.createLineBorder(new Color(15, 15, 15)));
        usericonButton.setVerticalTextPosition(SwingConstants.CENTER);
        usericonButton.addActionListener( e ->{
            frame.dispose();
            try {
                new UserInfo();
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
        homeButton.setBounds(1160, 7, 40, 40);
        homeButton.setFocusable(false);
        homeButton.setBackground(new Color(15,15,15));
        homeButton.setBorder(BorderFactory.createLineBorder(new Color(15, 15, 15)));
        homeButton.setVerticalTextPosition(SwingConstants.CENTER);
        homeButton.addActionListener(e -> {
            frame.dispose();
            new UserInterface();
        });

        topPanel.add(homeButton);



        ImageIcon cartIcon = new ImageIcon("D:\\BSCS-IV\\BookStore\\src\\cart.png"); // Replace "cart_icon.png" with the path to your icon file

// Resize the icon
        Image cartimg = cartIcon.getImage();
        Image cartresizedImg = cartimg.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Adjust the width and height as needed
        cartIcon = new ImageIcon(cartresizedImg);

// JButton for Cart
        JButton cartButton = new JButton( cartIcon); // Add spaces before the text to create padding
        cartButton.setBounds(1220, 7, 40, 40);
        cartButton.setFocusable(false);
        cartButton.setBackground(new Color(15, 15, 15));
        cartButton.setBorder(BorderFactory.createLineBorder(new Color(15, 15, 15)));
        cartButton.setVerticalTextPosition(SwingConstants.CENTER); // Center the text vertically

        cartButton.addActionListener(e -> {
            frame.dispose();
            new Cart();
        });

        topPanel.add(cartButton);

        // JButton for Logout
        ImageIcon logoutIcon = new ImageIcon("D:\\BSCS-IV\\BookStore\\src\\logout.png"); // Replace "logout_icon.png" with the path to your icon file

// Resize the icon
        Image logoutimg = logoutIcon.getImage();
        Image logoutresizedImg = logoutimg.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Adjust the width and height as needed
        logoutIcon = new ImageIcon(logoutresizedImg);

// JButton for Logout
        JButton logoutButton = new JButton( logoutIcon); // Add spaces before the text to create padding
        logoutButton.setBounds(1280, 7, 40, 40);
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

        searchField = new JTextField("Search");
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

        searchButton = new JButton("Search");
        searchButton.setBounds(960, 10, 100, 35);
        searchButton.setFocusable(false);
        searchButton.setBackground(Color.black);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createLineBorder(new Color(68, 70, 84, 255)));
        searchButton.addActionListener(e -> {
            String text = searchField.getText();
            if (!text.equals("Search") || text.equals("")) {
                displaySearchedBooks(text);
            }
            if (text.equals("")){
                displayAllBooks();
            }
        });
        topPanel.add(searchButton);

    }

    private void displayAllBooks() {
        try {
            List<Book> books = conn.retrieveBooks();
            displayBooks(books);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void displayBooks(List<Book> books) throws MalformedURLException {
        mainPanel.removeAll(); // Clear previous components




        int panelWidth = 195; // Width of each panel
        int panelHeight = 330; // Height of each panel
        int numPanels = 2; // Number of panels
        int panelSpacing = 10; // Spacing between panels
        int panelsPerRow = 6; // Number of panels per row

        int row = 0; // Row counter
        int column = 0; // Column counter

        System.out.println("Number of books: " + books.size());
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            row = i / panelsPerRow; // Divide index by panels per row to get row
            column = i % panelsPerRow; // Modulus gives the remainder, which is the column

            if (book.getStatus().equals("Active")) {


                // Create and configure book panel
                JPanel bookPanel = new JPanel(); // Create a new panel for each book
                bookPanel.setBackground(Color.BLACK); // Set background color
                bookPanel.setPreferredSize(new Dimension(panelWidth, panelHeight)); // Set panel size
                bookPanel.setLayout(null);
                bookPanel.setBorder(BorderFactory.createLineBorder(new Color(51, 51, 51))); // Set border


                ImageIcon icon = new ImageIcon(new URL(book.getImageUrl()));
                Image image_icon = icon.getImage().getScaledInstance(180, 230, Image.SCALE_SMOOTH);
                ImageIcon Scaled_icon = new ImageIcon(image_icon);
                JLabel imageLabel = new JLabel(Scaled_icon);
                imageLabel.setBounds(2, 2, 190, 240);
                bookPanel.add(imageLabel);


                JLabel titleLabel = new JLabel(book.getTitle());
                titleLabel.setForeground(Color.white);
                titleLabel.setBounds(2, 245, 190, 15);
                bookPanel.add(titleLabel);

                JLabel authorLabel = new JLabel("Author: " + book.getAuthor());
                authorLabel.setForeground(Color.white);
                authorLabel.setBounds(2, 262, 190, 13);
                bookPanel.add(authorLabel);

                JLabel priceLabel = new JLabel("Price: $" + book.getPrice());
                priceLabel.setForeground(Color.white);
                priceLabel.setBounds(2, 278, 190, 13);
                bookPanel.add(priceLabel);


                JButton addToCartButton = new JButton("Add to Cart");
                addToCartButton.setBounds(2, 298, 200, 30); // Adjust position and size as needed
                addToCartButton.setForeground(Color.WHITE);
                addToCartButton.setFocusable(false);
                addToCartButton.setBackground(Color.BLACK);
                addToCartButton.setBorder(BorderFactory.createLineBorder(new Color(15, 15, 15)));

                addToCartButton.addActionListener(e -> {

                    try {

                        if(book.getStock() >0) {
                            conn.addToCart(book.getBookid(), quantity, Conn.user);
                            JOptionPane.showMessageDialog(frame, "Item added to cart successfully!");
                        }

                        else {
                            JOptionPane.showMessageDialog(frame, "Item is Out of Stock!");

                        }

                    } catch (Exception ex) {
                        ex.printStackTrace(); // Handle the exception appropriately
                    }

                });
                bookPanel.add(addToCartButton);


                bookPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Open new class when panel is clicked
                        try {
                            frame.dispose();
                            new Bookdetails(book); // Assuming BookDetails class takes a Book object as parameter

                        } catch (MalformedURLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });


                int x = column * (panelWidth + panelSpacing); // Calculate x position
                int y = row * (panelHeight + panelSpacing); // Calculate y position
                bookPanel.setBounds(x, y, panelWidth, panelHeight); // Set bounds for book panel

                mainPanel.add(bookPanel); // Add book panel to main panel

                // Update row and column counters
                column++;
                if (column >= panelsPerRow) {
                    column = 0;
                    row++;
                }
            }
        }

        mainPanel.revalidate(); // Revalidate main panel
        mainPanel.repaint(); // Repaint main panel
    }



    private void displaySearchedBooks(String author) {
        try {
            List<Book> books = conn.retrieveBooksByAuthor(author);
            displayBooks(books);
        } catch (SQLException | MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public void searchBooks(String searchText) {
        if (!searchText.isEmpty() && !searchText.equals("Search")) {
            displaySearchedBooks(searchText); // Display searched books based on the search text
        } else {
            displayAllBooks(); // Display all books if search text is empty or default
        }
    }




}