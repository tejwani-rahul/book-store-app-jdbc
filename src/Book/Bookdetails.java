package Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Bookdetails extends JFrame {

    Conn conn = new Conn();
    ArrayList<Book> bookData = new ArrayList<>(); // Store book data

    JFrame frame;
    JPanel topPanel;
    JPanel panel;
    JButton searchButton;
    JTextField searchField;
    JButton orderNowButton;
    JTextArea quantityNum;

    int quantity = 1; // Initial quantity
    static int Userid;

    public Bookdetails(Book book) throws MalformedURLException {
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
        panel.setBackground(new Color(15,15,15));
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
        titlePanel.setBackground(new Color(15,15,15));
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
        descriptionTextArea.setBackground(new Color(15,15,15));
        descriptionTextArea.setForeground(Color.WHITE);
        descriptionTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setFont(new Font("Arial",Font.PLAIN,14));
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

        if(book.getStock() > 0 ){
            InOrNotstockLabel.setText("In Stock");
            InOrNotstockLabel.setForeground(Color.GREEN);
        }else {
            InOrNotstockLabel.setText("Not In Stock");
            InOrNotstockLabel.setForeground(Color.RED);

        }





        // Decrease Button
        JButton decsButton = new JButton("-");
        decsButton.setBounds(480, 290, 50, 50);
        decsButton.setFocusable(false);
        decsButton.setBackground(new Color(15,15,15));
        decsButton.setFont(new Font("Arial", Font.PLAIN, 22));
        decsButton.setBorder(BorderFactory.createLineBorder(new Color(15,15,15)));
        decsButton.setForeground(Color.WHITE);

        decsButton.addActionListener(e ->{

            if (quantity > 1) {
                quantity--;
                quantityNum.setText(Integer.toString(quantity));
            }

        });
        panel.add(decsButton);

// Quantity Text Area
        quantityNum = new JTextArea("  " + Integer.toString(quantity));
        quantityNum.setBounds(535 , 300, 50, 40); // Increase the width
        quantityNum.setEditable(false); // Make it non-editable
        quantityNum.setBackground(new Color(15,15,15));
        quantityNum.setForeground(Color.WHITE);
        quantityNum.setFont(new Font("Arial", Font.PLAIN, 24)); // Set font size
        panel.add(quantityNum);

// Increase Button
        JButton incButton = new JButton("+");
        incButton.setBounds(590, 290, 50, 50); // Increase the width and height
        incButton.setFocusable(false);
        incButton.setBackground(new Color(15,15,15));
        incButton.setForeground(Color.WHITE);
        incButton.setBorder(BorderFactory.createLineBorder(new Color(15,15,15)));
        incButton.setFont(new Font("Arial", Font.PLAIN, 22));
        incButton.addActionListener(e ->{

            quantity++;
            quantityNum.setText(Integer.toString(quantity));

        });
        panel.add(incButton);





        // Add to Cart Button
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setBounds(500, 370, 160, 35); // Adjust position and size as needed
        addToCartButton.setFocusable(false);
        addToCartButton.setBackground(new Color(15,15,15));
        addToCartButton.setFont(new Font("Arial",Font.PLAIN,16));
        //addToCartButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addToCartButton.setForeground(Color.WHITE);

        addToCartButton.addActionListener(e -> {

            try {

                // Userid = conn.getUserIdByEmail(LoginPage.email);
                if(book.getStock() >0){
                conn.addToCart(book.getBookid(), quantity, conn.user);
                // Provide feedback to the user (optional)
                JOptionPane.showMessageDialog(frame, "Item added to cart successfully!");}

                else {
                    JOptionPane.showMessageDialog(frame, "Item is Out of Stock!");

                }


            } catch (Exception ex) {
                ex.printStackTrace(); // Handle the exception appropriately
            }

        });
        panel.add(addToCartButton);

// Order Now Button
        orderNowButton = new JButton("Order Now");
        orderNowButton.setBounds(700, 370, 160, 35); // Adjust position and size as needed
        orderNowButton.setFocusable(false);
        orderNowButton.setBackground(new Color(15,15,15));
        orderNowButton.setFont(new Font("Arial",Font.PLAIN,16));
        //orderNowButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        orderNowButton.setForeground(Color.WHITE);

        orderNowButton.addActionListener(e -> {
            try {
                ArrayList<Book> bookdata = new ArrayList<Book>();

                if (book.getStock() <= 0 || quantity > book.getStock()) {
                    // If the book is out of stock, display a message to the user
                    JOptionPane.showMessageDialog(frame, "This item is currently out of stock.");
                } else {
                    // If the book is in stock, proceed to the checkout page
                    bookdata.add(book);

                    frame.dispose();
                    new OrderNow((quantity * book.getPrice()),bookdata,quantity);
                }
            } catch (Exception ex) {
                ex.printStackTrace(); // Handle the exception appropriately
            }
        });
        panel.add(orderNowButton);
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