package Book;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List; // Import List from java.util

public class Cart extends JFrame {
    private JFrame frame;
    private JPanel topPanel;
    private JButton searchButton;
    private JTextField searchField;
    private JPanel mainPanel;
    private JPanel bottomPanel;
    private JScrollPane scrollPane;
    private double totalPrice;
    private List<GetFromCart> cartItems;
    private Conn conn;

    // Constants for panel layout
    private final int panelWidth = 250;
    private final int panelHeight = 435;
    private final int panelSpacing = 10;
    private final int panelsPerRow = 4;

    public Cart() {
        frame = new JFrame();
        bottomPanel = new JPanel();
        bottomPanel.setBounds(2, 650, 1335, 40);
        bottomPanel.setBackground(Color.black);
        bottomPanel.setLayout(null);
        //bottomPanel();

        topPanel = new JPanel(); // New top panel
        topPanel.setBounds(2, 2, 1335, 60); // Set bounds
        topPanel.setBackground(new Color(15, 15, 15)); // Set background color
        topPanel.setLayout(null);

        topPanel();


        mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(15, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBounds(2, 60, 1332, 590);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(15, 15, 15)));
        scrollPane.getViewport().setBackground(new Color(32, 33, 35, 255));

        try {
            conn = new Conn();
            cartItems = conn.retrieveCart(Conn.user);

            if (!cartItems.isEmpty()) {
                for (GetFromCart cartItem : cartItems) {
                    displayCartItem(cartItem, conn);
                }
            } else {
                displayEmptyCartMessage();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int mainPanelWidth = panelSpacing + (panelWidth + panelSpacing) * panelsPerRow;
        int mainPanelHeight = panelSpacing + (panelHeight + panelSpacing) * ((cartItems.size() - 1) / panelsPerRow + 1);
        Dimension preferredSize = new Dimension(mainPanelWidth, mainPanelHeight);
        mainPanel.setPreferredSize(preferredSize);

        // Setting up the Frame
        frame.setSize(1350, 745);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setTitle("Book Store");
        frame.getContentPane().setBackground(new Color(15, 15, 15));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adding components to the frame
        frame.add(topPanel);
        frame.add(scrollPane);
        frame.add(bottomPanel);
        bottomPanel();;
    }

    private void displayCartItem(GetFromCart cartItem, Conn conn) throws SQLException {
        if (cartItem.getStatus().equals("Active")) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.BLACK);
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.setLayout(null);

            Book book = null;
            try {
                book = conn.retrieveBookById(cartItem.getBookid());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (book != null) {



                try {
                    URI uri = new URI(book.getImageUrl());
                    ImageIcon icon = new ImageIcon(uri.toURL());
                    //ImageIcon icon = new ImageIcon(new URL(book.getImageUrl()));
                    Image image_icon = icon.getImage().getScaledInstance(240, 300, Image.SCALE_SMOOTH);
                    ImageIcon Scaled_icon = new ImageIcon(image_icon);
                    JLabel imageLabel = new JLabel(Scaled_icon);
                    imageLabel.setBounds(5, 0, 240, 300);
                    panel.add(imageLabel);
                } catch (MalformedURLException | URISyntaxException ex) {
                    ex.printStackTrace();
                    // Display a placeholder image or handle the error in another way
                }


                JLabel titleLabel = new JLabel(book.getTitle());
                titleLabel.setForeground(Color.WHITE);
                titleLabel.setBounds(5, 310, 240, 20);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 15)); // Set font size and style
                panel.add(titleLabel);




                final int[] quantity = {conn.getQuantityInCart(cartItem.getCartid())};
                double bookprice = book.getPrice();


                double totalBookPrice = book.getPrice() * conn.getQuantityInCart(cartItem.getCartid());

                // Add label to display total price for this book
                JLabel totalPriceLabel = new JLabel("Total Price: $" + totalBookPrice);
                totalPriceLabel.setForeground(Color.WHITE);
                totalPriceLabel.setBounds(5, 340, 240, 20); // Adjust the position as needed
                totalPriceLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Adjust font size as needed
                panel.add(totalPriceLabel);



                JTextArea quantityNum = new JTextArea("  " + Integer.toString(quantity[0]));
                quantityNum.setBounds(50, 370, 30, 20); // Increase the width
                quantityNum.setEditable(false); // Make it non-editable
                quantityNum.setBackground(Color.BLACK);
                quantityNum.setForeground(Color.WHITE);
                quantityNum.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size
                panel.add(quantityNum);


                JButton decsButton = new JButton("-");
                decsButton.setBounds(5, 370, 30, 20); // Increase the width and height
                decsButton.setFocusable(false);
                decsButton.setFont(new Font("Arial", Font.PLAIN, 14));
                decsButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                decsButton.setBackground(Color.BLACK);
                decsButton.setForeground(Color.WHITE);

                decsButton.addActionListener(e -> {
                    try {
                        if (quantity[0] > 1) {
                            quantity[0]--;
                            quantityNum.setText(Integer.toString(quantity[0])); // Update the quantity displayed on the panel

                            // Update the quantity in the database
                            conn.updateQuantityInCart(cartItem.getCartid(), quantity[0]);

                            // Update the individual price label
                            double updatedTotalBookPrice = bookprice * quantity[0];
                            totalPriceLabel.setText("Total Price: $" + updatedTotalBookPrice);

                            // Update the total price display
                            bottomPanel(); // Update the total price display when the quantity changes
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace(); // Handle the exception appropriately
                    }
                });

                panel.add(decsButton);


                // Increase Button
                JButton incButton = new JButton("+");
                incButton.setBounds(90, 370, 30, 20); // Increase the width and height
                incButton.setFocusable(false);
                incButton.setBackground(Color.BLACK);
                incButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                incButton.setForeground(Color.WHITE);
                incButton.setFont(new Font("Arial", Font.PLAIN, 14));
                incButton.addActionListener(e -> {
                    try {
                        quantity[0]++;
                        quantityNum.setText(Integer.toString(quantity[0])); // Update the quantity displayed on the panel

                        // Update the quantity in the database
                        conn.updateQuantityInCart(cartItem.getCartid(), quantity[0]);

                        // Update the individual price label
                        double updatedTotalBookPrice = bookprice * quantity[0];
                        totalPriceLabel.setText("Total Price: $" + updatedTotalBookPrice);

                        // Update the total price display
                        bottomPanel(); // Update the total price display when the quantity changes
                    } catch (SQLException ex) {
                        ex.printStackTrace(); // Handle the exception appropriately
                    }
                });
                panel.add(incButton);


                JButton removeFromCartButton = new JButton("Remove From Cart");
                removeFromCartButton.setBounds(5, 400, 240, 30); // Increase the width and height
                removeFromCartButton.setFocusable(false);
                removeFromCartButton.setBackground(Color.BLACK);
                removeFromCartButton.setForeground(Color.WHITE);
                removeFromCartButton.setFont(new Font("Arial", Font.PLAIN, 12));
                int bookid = book.getBookid() ;


                removeFromCartButton.addActionListener(e -> {
                    try {
                        conn.deleteFromCart(cartItem.getCartid(), bookid);
                        cartItems.remove(cartItem);
                        mainPanel.remove(panel);
                        repositionCartItems();
                        mainPanel.revalidate();
                        mainPanel.repaint();
                        bottomPanel();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                panel.add(removeFromCartButton);
                mainPanel.add(panel);
                repositionCartItems();
            }
        }
    }

    private void repositionCartItems() {
        int row = 0;
        int column = 0;
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                int x = panelSpacing + (panelWidth + panelSpacing) * column;
                int y = panelSpacing + (panelHeight + panelSpacing) * row;
                panel.setBounds(x, y, panelWidth, panelHeight);
                column++;
                if (column >= panelsPerRow) {
                    column = 0;
                    row++;
                }
            }
        }
    }

    private void displayEmptyCartMessage() {
        JLabel emptyCartLabel = new JLabel("Your cart is empty.");
        emptyCartLabel.setForeground(Color.WHITE);
        emptyCartLabel.setBounds(10, 10, 200, 30);
        mainPanel.add(emptyCartLabel);
    }

    private void bottomPanel() {
        totalPrice = 0;
        for (GetFromCart cartItem : cartItems) {
            if (cartItem.getStatus().equals("Active")) {
                try {
                    Book book = conn.retrieveBookById(cartItem.getBookid());
                    double bookPrice = book.getPrice();
                    int quantity = conn.getQuantityInCart(cartItem.getCartid());
                    totalPrice += (quantity * bookPrice);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        bottomPanel.removeAll();
        JLabel totalPriceLabel = new JLabel("Total Price: $" + totalPrice);
        totalPriceLabel.setForeground(Color.WHITE);
        totalPriceLabel.setBounds(1010, 7, 150, 30);
        bottomPanel.add(totalPriceLabel);

        JButton orderButton = new JButton("Order Now");
        orderButton.setBounds(1170, 7, 150, 30);
        orderButton.setForeground(Color.WHITE);
        orderButton.setFocusable(false);
        orderButton.setBackground(Color.black);
        orderButton.setForeground(Color.WHITE);
        bottomPanel.add(orderButton);
        orderButton.addActionListener(e -> {
            try {
                List<GetFromCart> activeCartItems = new ArrayList<>();
                if (totalPrice > 0) {
                    for (GetFromCart cartItem : cartItems) {
                        if (cartItem.getStatus().equals("Active")) {
                            activeCartItems.add(cartItem);
                            Book book = conn.retrieveBookById(cartItem.getBookid());
                            int quantityInCart = conn.getQuantityInCart(cartItem.getCartid());
                            if (book.getStock() < quantityInCart) {
                                JOptionPane.showMessageDialog(frame, "Not enough stock for item: " + book.getTitle());
                                return;
                            }
                        }
                    }
                    frame.dispose();
                    new CheckoutPage(totalPrice, activeCartItems);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        bottomPanel.revalidate();
        bottomPanel.repaint();
    }

    private void topPanel(){

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
            public void mouseClicked(MouseEvent e) {
                searchField.setText("");
            }
        });

        topPanel.add(searchField);

        searchButton = new JButton("Search");
        searchButton.setBounds(960, 10, 100, 35);
        searchButton.setFocusable(false);
        searchButton.setBackground(Color.black);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createLineBorder(new Color(68, 70, 84, 255)));
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

            System.out.println("in search action listener");

            String text = searchField.getText();
            if (!text.equals("Search") || text.equals("")) {

                frame.dispose(); // Close the cart window
                UserInterface userInterface = new UserInterface();
                userInterface.searchBooks(text); // Pass the search query to the UserInterface
            }
        });
        topPanel.add(searchButton);


    }

}
