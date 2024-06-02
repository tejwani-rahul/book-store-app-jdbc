package Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;


public class CheckoutPage extends JFrame {
    JFrame frame;
    JPanel topPanel;
    JPanel mainPanel;
    JButton backButton;
    JButton checkoutButton;
    JButton searchButton;

    JLabel expiryDateLabel;
    JTextField expiryDateField;
    JTextField searchField;
    JLabel cardNumberLabel;
    JTextField cardNumberField;
    JLabel cvvLabel;
    JTextField cvvField;

    JLabel nameLabel;
    JLabel emailLabel;
    JLabel shippingAddressLabel;
    JLabel paymentMethodLabel;
    JLabel totalAmountLabel;


    JTextField nameField;
    JTextField emailField;
    JTextField shippingAddressField;

    private List<GetFromCart> activeCartItems;

    int orderId;

    Conn conn = new Conn();

    public CheckoutPage(double totalprice, List<GetFromCart> activeCartItems) {
        frame = new JFrame();

        this.activeCartItems = activeCartItems;

        Gui(totalprice);

        addCardFields();

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

    public void Gui(double totalprice) {



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

            System.out.println("in search action listener");

            String text = searchField.getText();
            if (!text.equals("Search") || text.equals("")) {

                frame.dispose(); // Close the cart window
                UserInterface userInterface = new UserInterface();
                userInterface.searchBooks(text); // Pass the search query to the UserInterface
            }
        });
        topPanel.add(searchButton);

        // MainPanel
        mainPanel = new JPanel();
        mainPanel.setBounds(2, 60, 1335, 558);
        mainPanel.setBackground(new Color(15,15,15));
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(15,15,15)));
        mainPanel.setLayout(null);


// Name
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(100, 100, 70, 30);
        nameLabel.setForeground(Color.WHITE);

        nameField = new JTextField();
        nameField.setBounds(100, 130, 400, 30);
        nameField.setBackground(new Color(15,15,15));
        nameField.setForeground(Color.WHITE);
        nameField.setCaretColor(Color.WHITE);

// Email
        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(100, 180, 70, 30);
        emailLabel.setForeground(Color.WHITE);

        emailField = new JTextField();
        emailField.setBounds(100, 210, 400, 30);
        emailField.setBackground(new Color(15,15,15));
        emailField.setForeground(Color.WHITE);
        emailField.setCaretColor(Color.WHITE);

// Shipping Address
        shippingAddressLabel = new JLabel("Shipping Address:");
        shippingAddressLabel.setBounds(100, 260, 150, 30);
        shippingAddressLabel.setForeground(Color.WHITE);

        shippingAddressField = new JTextField();
        shippingAddressField.setBounds(100, 290, 400, 30);
        shippingAddressField.setBackground(new Color(15,15,15));
        shippingAddressField.setForeground(Color.WHITE);
        shippingAddressField.setCaretColor(Color.WHITE);

// Payment Method
        paymentMethodLabel = new JLabel("Payment Method:");
        paymentMethodLabel.setBounds(100, 340, 200, 30);
        paymentMethodLabel.setForeground(Color.WHITE);

        String[] paymentMethods = {"Card", "Cash on Delivery"};
        JComboBox<String> paymentMethodComboBox = new JComboBox<>(paymentMethods);
        paymentMethodComboBox.setBounds(100, 370, 400, 30);
        paymentMethodComboBox.setBackground(new Color(15,15,15));
        paymentMethodComboBox.setForeground(Color.WHITE);
        ((JLabel) paymentMethodComboBox.getRenderer()).setForeground(Color.WHITE);
        ((JLabel) paymentMethodComboBox.getRenderer()).setBackground(Color.WHITE);
        paymentMethodComboBox.setSelectedIndex(0); // Set default selection




            paymentMethodComboBox.addActionListener(e -> {

                String selectedPaymentMethod = (String) paymentMethodComboBox.getSelectedItem();
                System.out.println("Selected payment method: " + selectedPaymentMethod); // Add this line for debugging
                if (selectedPaymentMethod != null) {
                    if (selectedPaymentMethod.equals("Card")) {
                        // Show credit card fields
                        addCardFields();
                    } else if(selectedPaymentMethod.equals("Cash on Delivery"))  {
                        // Remove credit card fields if other payment method is selected
                        removeCardFields();
                    }
                    else
                        removeCardFields();
                }
            });

// Add payment method components to mainPanel


// Total Amount
        totalAmountLabel = new JLabel("Total Amount:          " + totalprice);
        totalAmountLabel.setBounds(100, 50, 200, 30);
        totalAmountLabel.setForeground(Color.WHITE);




        checkoutButton = new JButton("Check Out");
        checkoutButton.setBounds(400, 500, 300, 40);
        checkoutButton.setFocusable(false);
        checkoutButton.setBackground(new Color(112, 191, 255, 255));

        checkoutButton.addActionListener(e -> {
            // Extract data from text fields
            String name = nameField.getText();
            String email = emailField.getText();
            String shippingAddress = shippingAddressField.getText();
            String paymentMethod = (String) paymentMethodComboBox.getSelectedItem();
            String cardNumber ; // Get the text from the cardNumberField JTextField
            String cvv ; // Get the text from the cvvField JTextField
            String expiryDate ; // Get the text from the expiryDateField JTextField


            java.util.Date currentDate = new java.util.Date();
            java.sql.Date shipmentDate = new java.sql.Date(currentDate.getTime());
            java.sql.Date paymentDate = new java.sql.Date(currentDate.getTime());


            // Call the updateBook method
            try {
                String shipmentStatus = "Pending"; // Set initial status
                int orderId = conn.insertOrder(Conn.user, name, email, totalprice, shipmentStatus);

                // Insert shipment information
                String trackingNumber = generateTrackingNumber(10); // You need to implement this method

                conn.insertShipment(orderId, trackingNumber, shipmentDate, shippingAddress);


                int paymentId =conn.insertPayment(Conn.user, orderId,paymentDate, paymentMethod, totalprice);

                if (paymentMethod != null && paymentMethod.equals("Card")) {
                    cardNumber = cardNumberField.getText();
                    cvv = cvvField.getText();
                    expiryDate = expiryDateField.getText();
                    conn.insertCardInfo(paymentId, cardNumber, cvv, expiryDate);
                }


                activeCartItems = conn.retrieveCart(Conn.user);

                for (GetFromCart cartItem : activeCartItems) {
                    Book book = conn.retrieveBookById(cartItem.getBookid());
                    double bookPrice = book.getPrice();
                    int bookId = book.getBookid();
                    int quantity = cartItem.getQuantity();
                    conn.insertOrderDetails(orderId, bookId, quantity, bookPrice);
                }


                conn.updateCartStatus(Conn.user);


                frame.dispose();
                new OrderConfirm(name,trackingNumber);


                JOptionPane.showMessageDialog(null, "Order placed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Failed to place order: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Optional: Print the stack trace for debugging purposes
            }
        });



        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);
        mainPanel.add(shippingAddressLabel);
        mainPanel.add(shippingAddressField);
        mainPanel.add(paymentMethodLabel);
        mainPanel.add(paymentMethodComboBox);
        mainPanel.add(totalAmountLabel);


        mainPanel.add(checkoutButton);
    }


        public static String generateTrackingNumber(int length) {
            // Generate a UUID
            String uuid = UUID.randomUUID().toString();

            // Remove hyphens from the UUID
            uuid = uuid.replace("-", "");

            // Truncate or pad the UUID to the desired length
            if (uuid.length() < length) {
                // If the UUID is shorter than the desired length, pad it with zeros
                uuid = uuid + "0".repeat(length - uuid.length());
            } else if (uuid.length() > length) {
                // If the UUID is longer than the desired length, truncate it
                uuid = uuid.substring(0, length);
            }

            return uuid;
        }

    private void addCardFields() {
         cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setBounds(600, 100, 200, 30);
        cardNumberLabel.setForeground(Color.WHITE);

         cardNumberField = new JTextField();
        cardNumberField.setBounds(600, 130, 400, 30);
        cardNumberField.setBackground(new Color(15,15,15));
        cardNumberField.setForeground(Color.WHITE);
        cardNumberField.setCaretColor(Color.WHITE);

         cvvLabel = new JLabel("CVV:");
        cvvLabel.setBounds(600, 180, 200, 30);
        cvvLabel.setForeground(Color.WHITE);

         cvvField = new JTextField();
        cvvField.setBounds(600, 210, 400, 30);
        cvvField.setBackground(new Color(15,15,15));
        cvvField.setForeground(Color.WHITE);
        cvvField.setCaretColor(Color.WHITE);

        expiryDateLabel = new JLabel("Expiry Date:");
        expiryDateLabel.setBounds(600, 260, 200, 30);
        expiryDateLabel.setForeground(Color.WHITE);

        expiryDateField = new JTextField();
        expiryDateField.setBounds(600, 290, 400, 30);
        expiryDateField.setBackground(new Color(15,15,15));
        expiryDateField.setForeground(Color.WHITE);
        expiryDateField.setCaretColor(Color.WHITE);

        // Add credit card fields to mainPanel
        mainPanel.add(cardNumberLabel);
        mainPanel.add(cardNumberField);
        mainPanel.add(cvvLabel);
        mainPanel.add(cvvField);
        mainPanel.add(expiryDateLabel);
        mainPanel.add(expiryDateField);

        // Repaint the mainPanel to reflect the changes
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    private void removeCardFields() {
        // Remove credit card fields from mainPanel
        // You can remove the components by referencing them or by clearing all components from the mainPanel
        mainPanel.remove(cardNumberLabel);
        mainPanel.remove(cardNumberField);
        mainPanel.remove(cvvLabel);
        mainPanel.remove(cvvField);
        mainPanel.remove(expiryDateLabel);
        mainPanel.remove(expiryDateField);

        // Repaint the mainPanel to reflect the changes
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    public static boolean validateExpiryDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false); // Set lenient to false to ensure strict date parsing
        try {
            sdf.parse(dateStr); // Try to parse the date string
            return true; // If parsing is successful, return true
        } catch (ParseException e) {
            return false; // If parsing fails, return false
        }
    }


    }



