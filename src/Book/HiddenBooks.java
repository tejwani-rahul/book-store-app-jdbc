package Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL; // Import URL from java.net
import java.sql.SQLException;
import java.util.List; // Import List from java.util

public class HiddenBooks extends JFrame {
    JFrame frame;
    JPanel sidePanel;
    JPanel topPanel;
    JPanel mainPanel;
    JButton searchButton;
    JButton logoutButton;

    JTextField searchField;
    JScrollPane scrollPane;
    JButton InsertbooksButton;

    Conn conn = new Conn();


    public HiddenBooks(){

        frame = new JFrame();
        conn = new Conn();

        setupUI();
        displayAllBooks(); // Display all books by default

        frame.add(topPanel);

        frame.add(scrollPane);

        // Setting up the Frame
        frame.setSize(1350, 745);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setTitle("Book Store");
        frame.getContentPane().setBackground(new Color(15,15,15));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        int panelWidth = 192; // Width of each panel
        int panelHeight = 330; // Height of each panel
        int numPanels = 2; // Number of panels
        int panelSpacing = 10; // Spacing between panels
        int panelsPerRow = 4; // Number of panels per row

        int row = 0; // Row counter
        int column = 0; // Column counter



        for (Book book : books) {
            if (book.getStatus().equals("Hidden")) {
                JPanel panel = new JPanel(); // Create a new panel for each book
                panel.setBackground(Color.BLACK); // Set background color
                panel.setPreferredSize(new Dimension(panelWidth, panelHeight)); // Set panel size
                panel.setLayout(null);
                panel.setBorder(BorderFactory.createLineBorder(new Color(51, 51, 51))); // Set border


                ImageIcon icon = new ImageIcon(new URL(book.getImageUrl()));
                Image image_icon = icon.getImage().getScaledInstance(180, 230, Image.SCALE_SMOOTH);
                ImageIcon Scaled_icon = new ImageIcon(image_icon);
                JLabel imageLabel = new JLabel(Scaled_icon);
                imageLabel.setBounds(2, 2, 190, 240);
                panel.add(imageLabel);


                JLabel titleLabel = new JLabel(book.getTitle());
                titleLabel.setForeground(Color.WHITE);
                titleLabel.setBounds(2, 245, 190, 15);

                JLabel authorLabel = new JLabel("Author: " + book.getAuthor());
                authorLabel.setForeground(Color.WHITE);
                authorLabel.setBounds(2, 262, 190, 13);

                JLabel priceLabel = new JLabel("Price: $" + book.getPrice());
                priceLabel.setForeground(Color.WHITE);
                priceLabel.setBounds(2, 278, 100, 13);


                JButton updateButton = new JButton("Update");
                updateButton.setBounds(10, 295, 80, 30);
                updateButton.setFocusable(false);
                updateButton.setBackground(Color.BLACK);
                updateButton.setForeground(Color.WHITE);
                updateButton.setBorder(BorderFactory.createLineBorder(new Color(15,15,15)));

                updateButton.addActionListener(e -> {
                    frame.dispose();
                    try {
                        new UpdateBook(book);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }
                });


                JButton displayButton = new JButton("Display");
                displayButton.setBounds(105, 295, 80, 30);
                displayButton.setFocusable(false);
                displayButton.setBackground(Color.BLACK);
                displayButton.setForeground(Color.WHITE);
                displayButton.setBorder(BorderFactory.createLineBorder(new Color(15,15,15)));

                displayButton.addActionListener(e -> {

                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this book?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.YES_OPTION) {
                        try {
                            conn.updateBookStatus(book.getBookid(),"Active");

                            JOptionPane.showMessageDialog(null, "Book is on Display successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                            displayAllBooks();

                        } catch (SQLException ex) {
                            // Handle any SQL exceptions
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "An error occurred while displaying the book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });


                panel.add(titleLabel);
                panel.add(authorLabel);
                panel.add(priceLabel);
                panel.add(updateButton);
                panel.add(displayButton);


                int x = panelSpacing + (panelWidth + panelSpacing) * column;
                int y = panelSpacing + (panelHeight + panelSpacing) * row;
                panel.setBounds(x, y, panelWidth, panelHeight); // Set bounds for each panel
                mainPanel.add(panel);

                // Update row and column counters
                column++;
                if (column >= panelsPerRow) {
                    column = 0;
                    row++;
                }
            }
        }


        // Calculate preferred size for scroll pane
        int mainPanelWidth = panelSpacing + (panelWidth + panelSpacing) * panelsPerRow;
        int mainPanelHeight = panelSpacing + (panelHeight + panelSpacing) * (row + 1);
        Dimension preferredSize = new Dimension(mainPanelWidth, mainPanelHeight);
        mainPanel.setPreferredSize(preferredSize);

        mainPanel.revalidate();
        mainPanel.repaint();

    }







    private void displaySearchedBooks(String author) {
        try {
            List<Book> books = conn.retrieveBooksByAuthor(author);
            displayBooks(books);
        } catch (SQLException | MalformedURLException e) {
            e.printStackTrace();
        }
    }


    private void setupUI(){

        // TopPanel
        topPanel = new JPanel(); // New top panel
        topPanel.setBounds(2, 2, 1350, 60); // Set bounds
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




}

