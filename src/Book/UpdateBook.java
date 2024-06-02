package Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;


public class UpdateBook extends JFrame {
    JFrame frame;
    JPanel topPanel;
    JPanel mainPanel;



    JButton updateButton;

    JLabel isbnLabel;
    JLabel titleLabel;
    JLabel authorLabel;
    JLabel PublisherLabel;
    JLabel PublishDateLabel;
    JLabel priceLabel;
    JLabel stockLabel;
    JLabel imageUrlLabel;
    JLabel descriptionLabel;

    JLabel updateLabel;


    JTextField isbnField;
    JTextField titleField;
    JTextField authorField;

    JTextField PublisherField;
    JTextField PublishDateField;

    JTextField priceField;
    JTextField stockField;
    JTextField imageUrlField;
    JTextField descriptionField;


    Conn jdbc = new Conn();
    public UpdateBook(Book book) throws MalformedURLException {
        frame = new JFrame();
        mainPanel = new JPanel();
        topPanel = new JPanel();

    Gui(book);



        try {
         //   List<Book> books = conn.retrieveBooks();

            //isbnField.setText(books.getIsbn()());
            isbnField.setText(book.getIsbn());
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            PublisherField.setText(book.getPublisher());
            // Assuming publishDate is a Date object, convert it to String format before setting
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String publishDateString = sdf.format(book.getPublishDate());
            PublishDateField.setText(publishDateString);
            priceField.setText(String.valueOf(book.getPrice()));
            stockField.setText(String.valueOf(book.getStock()));
            imageUrlField.setText(book.getImageUrl());
            descriptionField.setText(book.getDescription());

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

    public void Gui(Book book) throws MalformedURLException {


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


        ImageIcon icon = new ImageIcon(new URL(book.getImageUrl()));
        Image image_icon = icon.getImage().getScaledInstance(300, 500, Image.SCALE_SMOOTH);
        ImageIcon Scaled_icon = new ImageIcon(image_icon);
        JLabel imageLabel = new JLabel(Scaled_icon);
        imageLabel.setBounds(20, 100, 300, 500);
        mainPanel.add(imageLabel);




        updateLabel = new JLabel("Update Book");
        updateLabel.setBounds(50,50,300,40);
        updateLabel.setForeground(Color.WHITE);
            updateLabel.setFont(new Font("",Font.BOLD,35));


        isbnLabel = new JLabel("ISBN");
        isbnLabel.setBounds(400,120,70,20);
        isbnLabel.setForeground(Color.WHITE);

        isbnField = new JTextField();
        isbnField.setBounds(400,140,300,30);
        isbnField.setBackground(new Color(15,15,15));
        isbnField.setForeground(Color.WHITE);
        isbnField.setCaretColor(Color.WHITE);


        titleLabel = new JLabel("Title");
        titleLabel.setBounds(400,180,70,20);
        titleLabel.setForeground(Color.WHITE);

        titleField = new JTextField();
        titleField.setBounds(400,200,300,30);
        titleField.setBackground(new Color(15,15,15));
        titleField.setForeground(Color.WHITE);
        titleField.setCaretColor(Color.WHITE);


        authorLabel = new JLabel("Author");
        authorLabel.setBounds(400,240,70,20);
        authorLabel.setForeground(Color.WHITE);

        authorField = new JTextField();
        authorField.setBounds(400,260,300,30);
        authorField.setBackground(new Color(15,15,15));
        authorField.setForeground(Color.WHITE);
        authorField.setCaretColor(Color.WHITE);




        PublisherLabel = new JLabel("Publisher");
        PublisherLabel.setBounds(400,300,70,20);
        PublisherLabel.setForeground(Color.WHITE);

        PublisherField = new JTextField();
        PublisherField.setBounds(400,320,300,30);
        PublisherField.setBackground(new Color(15,15,15));
        PublisherField.setForeground(Color.WHITE);
        PublisherField.setCaretColor(Color.WHITE);



        PublishDateLabel = new JLabel("PublishDate");
        PublishDateLabel.setBounds(400,360,70,20);
        PublishDateLabel.setForeground(Color.WHITE);

        PublishDateField = new JTextField();
        PublishDateField.setBounds(400,380,300,25);
        PublishDateField.setBackground(new Color(15,15,15));
        PublishDateField.setForeground(Color.WHITE);
        PublishDateField.setCaretColor(Color.WHITE);




        priceLabel = new JLabel("Price");
        priceLabel.setBounds(400,420,70,20);
        priceLabel.setForeground(Color.WHITE);

        priceField = new JTextField();
        priceField.setBounds(400,440,300,30);
        priceField.setBackground(new Color(15,15,15));
        priceField.setForeground(Color.WHITE);
        priceField.setCaretColor(Color.WHITE);



        stockLabel = new JLabel("Stock");
        stockLabel.setBounds(800,120,70,20);
        stockLabel.setForeground(Color.WHITE);

        stockField = new JTextField();
        stockField.setBounds(800,140,300,30);
        stockField.setBackground(new Color(15,15,15));
        stockField.setForeground(Color.WHITE);
        stockField.setCaretColor(Color.WHITE);



        imageUrlLabel = new JLabel("ImageUrl");
        imageUrlLabel.setBounds(800,180,70,20);
        imageUrlLabel.setForeground(Color.WHITE);

        imageUrlField = new JTextField();
        imageUrlField.setBounds(800,200,300,30);
        imageUrlField.setBackground(new Color(15,15,15));
        imageUrlField.setForeground(Color.WHITE);
        imageUrlField.setCaretColor(Color.WHITE);


        descriptionLabel = new JLabel("Description");
        descriptionLabel.setBounds(800,240,70,20);
        descriptionLabel.setForeground(Color.WHITE);

        descriptionField = new JTextField();
        descriptionField.setBounds(800,260,300,30);
        descriptionField.setBackground(new Color(15,15,15));
        descriptionField.setForeground(Color.WHITE);
        descriptionField.setCaretColor(Color.WHITE);



        updateButton = new JButton("Update");
        updateButton.setBounds(600,520,300,40);
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusable(false);
        updateButton.setBackground(new Color(112,191,255,255));

        updateButton.addActionListener(e ->{
            // Extract data from text fields
            String isbn = isbnField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String publisher = PublisherField.getText();
            String publishDate = PublishDateField.getText(); // Assuming it's a String representation of the date
            String price = priceField.getText();
            String stock = stockField.getText();
            String imageUrl = imageUrlField.getText();
            String description = descriptionField.getText();


            // Call the updateBook method
            try {
                // Pass the extracted data to the updateBook method
                jdbc.updateBook(book.getBookid(),isbn, title, author,publisher,publishDate, price, stock, imageUrl, description);
                JOptionPane.showMessageDialog(null, "Book updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An error occurred while updating the book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Optional: Print the stack trace for debugging purposes
            }
        });






        mainPanel.add(isbnLabel);
        mainPanel.add(isbnField);
        mainPanel.add(titleLabel);
        mainPanel.add(titleField);
        mainPanel.add(authorLabel);
        mainPanel.add(authorField);

        mainPanel.add(PublisherLabel);
        mainPanel.add(PublisherField);
        mainPanel.add(PublishDateLabel);
        mainPanel.add(PublishDateField);

        mainPanel.add(priceLabel);
        mainPanel.add(priceField);
        mainPanel.add(stockLabel);
        mainPanel.add(stockField);
        mainPanel.add(imageUrlLabel);
        mainPanel.add(imageUrlField);
        mainPanel.add(descriptionLabel);
        mainPanel.add(descriptionField);
        mainPanel.add(updateButton);
        mainPanel.add(updateLabel);

    }


}
