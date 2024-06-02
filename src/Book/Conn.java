package Book;



import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Conn {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

static int user ;
//static String username;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore",
                    "root", "1234");
            System.out.println("Connection ok ...!");

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            user = getUserIdByEmail(LoginPage.email) ;
            // username =getUsernameByEmail(LoginPage.email);

        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    boolean search(String email) throws Exception {
        ps = con.prepareStatement("select * from user where email= ? ");

        ps.setString(1, email);

        rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("in search at true");
            return true;
        }
        else {
            System.out.println("in search at false");
            return false;
        }
    }

    void insertUserinfo(String uname, String email, String password) throws Exception {
        ps = con.prepareStatement("insert into user (Username, Email, Password) values (?, ?, ?)");

        ps.setString(1, uname);
        ps.setString(2, email);
        ps.setString(3, password);

        ps.executeUpdate();
    }


    public void updateUserInfo (int userid,String username,String email, String password) throws Exception {


        ps = con.prepareStatement("UPDATE User SET Username = ?, Password = ?, Email = ? WHERE UserID = ?");

        ps.setString(1,username);
        ps.setString(2, password);
        ps.setString(3, email);
        ps.setInt(10,userid);

        ps.executeUpdate();
    }



    public boolean[] search_Login(String email, String password) throws Exception {
        boolean[] result = new boolean[2]; // Index 0 for login result, Index 1 for role (true for admin, false for customer)
        ps = con.prepareStatement("select * from user where Email= ? and Password= ? ");
        ps.setString(1, email);
        ps.setString(2, password);

        rs = ps.executeQuery();
        if (rs.next()) {
            result[0] = true; // Login successful
            String role = rs.getString("Role");
            result[1] = role.equals("Admin");
        } else {
            result[0] = false; // Login failed
        }
        return result;
    }



    public int getUserIdByEmail(String email) throws SQLException {

        int userId = -1; // Default value indicating user not found
        ps = con.prepareStatement("SELECT UserID FROM User WHERE Email = ?");
        ps.setString(1, email);
        rs = ps.executeQuery();
        if (rs.next()) {
            userId = rs.getInt("UserID");
        }
        System.out.println(userId);
        System.out.println("in get id by email "+userId );
        return userId;
    }



    public User retrieveUserById(int userId) throws Exception {
        User user = null;
        String query = "SELECT * FROM User WHERE UserID = ?";

        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("Username");
                String password = rs.getString("Password");
                String email = rs.getString("Email");

                user = new User(userId, name, password, email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return user;
    }



    public void updateUser(int userId, String name, String password, String email) throws Exception {
        ps = con.prepareStatement("UPDATE user SET Username = ?, Password = ?, Email = ? WHERE UserID = ?");

        ps.setString(1, name);
        ps.setString(2, password);
        ps.setString(3, email);
        ps.setInt(4, userId);

        ps.executeUpdate();
    }



    void insertBook(String isbn, String title, String author, String publisher, String publishDate, String input_price, String input_stock, String image_url, String description) throws Exception {
        int stock = Integer.parseInt(input_stock);
        double price = Double.parseDouble(input_price);

        // Convert String to java.sql.Date for publishDate
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = sdf.parse(publishDate);
        Date sqlPublishDate = new Date(date.getTime());

        // Check if the book already exists
        if (checkBookExists(isbn, title)) {
            // Book already exists, show error message or handle accordingly
            JOptionPane.showMessageDialog(null, "The book already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit the method without inserting the book
        }

        // Prepare and execute the INSERT statement
        ps = con.prepareStatement("INSERT INTO book (ISBN, Title, Author, Publisher, PublishDate, Price, QuantityAvailable, ImageURL, Description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        ps.setString(1, isbn);
        ps.setString(2, title);
        ps.setString(3, author);
        ps.setString(4, publisher);
        ps.setDate(5, sqlPublishDate);
        ps.setDouble(6, price);
        ps.setInt(7, stock);
        ps.setString(8, image_url);
        ps.setString(9, description);

        ps.executeUpdate();
    }

    // Helper method to check if the book already exists
    boolean checkBookExists(String isbn, String title) throws SQLException {
        PreparedStatement checkStatement = con.prepareStatement("SELECT * FROM book WHERE ISBN = ? OR Title = ?");
        checkStatement.setString(1, isbn);
        checkStatement.setString(2, title);
        ResultSet resultSet = checkStatement.executeQuery();
        return resultSet.next(); // Returns true if a matching book is found
    }





    public List<Book> retrieveBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book";

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int bookid = rs.getInt("BookID");
                String isbn = rs.getString("ISBN");
                String title = rs.getString("Title");
                String author = rs.getString("Author");
                double price = rs.getDouble("Price");
                int stock = rs.getInt("QuantityAvailable");
                String imageUrl = rs.getString("ImageURL");
                String description = rs.getString("Description");
                String publisher = rs.getString("Publisher");
                Date publishDate = rs.getDate("PublishDate");
                String status = rs.getString("Status");

                Book book = new Book(bookid,isbn,title, author, price, stock, imageUrl, description, publisher, publishDate,status);
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return books;
    }






    public void updateBook(int bookid,String isbn, String title, String author, String publisher, String publishDate, String input_price, String input_stock, String image_url, String description) throws Exception {

        int stock = Integer.parseInt(input_stock);
        double price = Double.parseDouble(input_price);


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = sdf.parse(publishDate);
        java.sql.Date sqlPublishDate = new java.sql.Date(date.getTime());

        ps = con.prepareStatement("UPDATE book SET ISBN = ?, Title = ?, Author = ?, Publisher = ?, PublishDate = ?, Price = ?, QuantityAvailable = ?, ImageURL = ?, Description = ? WHERE BookID = ?");

        ps.setString(1,isbn);
        ps.setString(2, title);
        ps.setString(3, author);
        ps.setString(4, publisher);
        ps.setDate(5,sqlPublishDate);
        ps.setDouble(6, price);
        ps.setInt(7, stock);
        ps.setString(8, image_url);
        ps.setString(9, description);
        ps.setInt(10,bookid);

        ps.executeUpdate();
    }




    public void addToCart(int bookID, int quantity, int userId) throws SQLException {
        // Check if the item already exists in the user's cart
        String checkQuery = "SELECT * FROM ShoppingCart WHERE UserID = ? AND BookID = ? AND Status = 'active'";
        try (PreparedStatement checkStatement = con.prepareStatement(checkQuery)) {
            checkStatement.setInt(1, userId);
            checkStatement.setInt(2, bookID);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                // Item already exists in the cart, update the quantity
                int cartID = resultSet.getInt("CartID");
                int currentQuantity = resultSet.getInt("Quantity");
                int newQuantity = currentQuantity + quantity; // Add the new quantity to the existing quantity
                updateQuantityInCart(cartID, newQuantity);
                System.out.println("Quantity updated successfully in the cart.");
            } else {
                // Item does not exist in the cart, insert a new entry
                String insertQuery = "INSERT INTO ShoppingCart (UserID, BookID, Quantity, Status) VALUES (?, ?, ?, 'active')";
                try (PreparedStatement insertStatement = con.prepareStatement(insertQuery)) {
                    insertStatement.setInt(1, userId);
                    insertStatement.setInt(2, bookID);
                    insertStatement.setInt(3, quantity);
                    insertStatement.executeUpdate();
                    System.out.println("Item added to cart successfully.");
                }
            }
        }
    }


// if admin hides a book it will get deleted from user cart
    public void deleteHiddenBookFromCart(int bookId) throws SQLException {
        String sql = "DELETE FROM ShoppingCart WHERE BookID = ? AND Status = 'Active'";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Item deleted successfully from the cart.");
            } else {
                System.out.println("Item not found in the cart or it is not active.");
            }
    }

    }




// delete book from cart id user it self wants to
    public void deleteFromCart(int cartId, int bookid) throws SQLException {
        String sql = "DELETE FROM ShoppingCart WHERE CartID = ? AND BookID = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, cartId);
            preparedStatement.setInt(2, bookid);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Item deleted successfully from the cart.");
            } else {
                System.out.println("Item not found in the cart.");
            }
        }
    }

    public List<GetFromCart> retrieveCart(int userId) throws SQLException {
        List<GetFromCart> gfc = new ArrayList<>();
        String query = "SELECT * FROM ShoppingCart WHERE UserID = ?";

        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int cartid = rs.getInt("CartID");
                int userid = rs.getInt("UserID");
                int bookid = rs.getInt("BookID");
                int quantity = rs.getInt("Quantity");
                String status = rs.getString("Status");

                GetFromCart cart = new GetFromCart(cartid, userid, bookid, quantity, status);
                gfc.add(cart);
            }
        } catch (SQLException e) {
            throw e; // Re-throw the exception to be handled in the calling method
        } finally {
            // Close resources
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return gfc;
    }

    public Book retrieveBookById(int bookId) throws SQLException {
        Book book = null;
        String query = "SELECT * FROM book WHERE BookID = ?";

        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();

            if (rs.next()) {
                int bookid = rs.getInt("BookID");
                String isbn = rs.getString("ISBN");
                String title = rs.getString("Title");
                String author = rs.getString("Author");
                double price = rs.getDouble("Price");
                int stock = rs.getInt("QuantityAvailable");
                String imageUrl = rs.getString("ImageURL");
                String description = rs.getString("Description");
                String publisher = rs.getString("Publisher");
                Date publishDate = rs.getDate("PublishDate");
                String status = rs.getString("Status");


                book = new Book(bookid, isbn, title, author, price, stock, imageUrl, description, publisher, publishDate,status);
            }
        } catch (SQLException e) {
            throw e; // Re-throw the exception to be handled in the calling method
        }finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }

        return book;
    }



    public void updateQuantityInCart(int cartId, int newQuantity) throws SQLException {
        PreparedStatement statement = null;

        try {
            // Prepare the SQL statement
            String sql = "UPDATE ShoppingCart SET Quantity = ? WHERE CartID = ?";
            statement = con.prepareStatement(sql);

            // Set parameters
            statement.setInt(1, newQuantity);
            statement.setInt(2, cartId);

            // Execute the update
            statement.executeUpdate();
        } finally {
            // Close the statement
            if (statement != null) {
                statement.close();
            }
        }
    }





    public int insertOrder(int userId, String name, String email, double totalPrice,String shipmentstatues) throws SQLException {
        int orderId = -1;
        try {
            // Prepare the INSERT statement to add the order details
            String insertOrderQuery = "INSERT INTO Orders (UserID, FullName, Email, TotalPrice,ShipmentStatus) VALUES (?, ?, ?, ?,?)";
            PreparedStatement insertOrderStatement = con.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);
            insertOrderStatement.setInt(1, userId);
            insertOrderStatement.setString(2, name);
            insertOrderStatement.setString(3, email);
            insertOrderStatement.setDouble(4, totalPrice);
            insertOrderStatement.setString(5, shipmentstatues);

            // Execute the INSERT statement
            insertOrderStatement.executeUpdate();

            // Retrieve the generated order ID
            ResultSet generatedKeys = insertOrderStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve generated order ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return orderId;
    }


    public void insertShipment(int orderId, String trackingNumber, java.sql.Date shipmentDate, String shippingAddress) throws SQLException {
        // SQL query to insert the shipment into the database
        String query = "INSERT INTO shipment (OrderID, ShipmentDate, DeliveryAddress, TrackingNumber) VALUES ( ?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(query)) {
            // Set the values for the prepared statement
            statement.setInt(1, orderId);
            statement.setDate(2, shipmentDate);
            statement.setString(3, shippingAddress);
            statement.setString(4, trackingNumber);


            // Execute the query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Shipment inserted successfully.");
            } else {
                System.out.println("Failed to insert shipment.");
            }
        }
    }




    public int insertPayment(int userId, int orderId,java.sql.Date paymentDate, String paymentMethod, double amountPaid) throws SQLException {
        // SQL query to insert payment information into the database
        int paymentId;

        String query = "INSERT INTO Payment (UserID, OrderID,PaymentDate, PaymentMethod, AmountPaid) VALUES (?, ?, ?, ?,?)";

        try (PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // Set the values for the prepared statement
            statement.setInt(1, userId);
            statement.setInt(2, orderId);
            statement.setDate(3, paymentDate);
            statement.setString(4, paymentMethod);
            statement.setDouble(5, amountPaid);

            // Execute the query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                // Get the generated payment ID
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                     paymentId = generatedKeys.getInt(1);
                    System.out.println("Payment inserted successfully with ID: " + paymentId);
                    return paymentId; // Return the payment ID
                } else {
                    throw new SQLException("Failed to retrieve payment ID.");
                }
            } else {
                System.out.println("Failed to insert payment.");
                return -1; // Return -1 indicating failure
            }
        }
    }


    public void insertOrderDetails(int orderId, int bookId, int quantity, double price) throws SQLException {
        // Retrieve the current stock of the book
        Book book = retrieveBookById(bookId);
        int currentStock = book.getStock();

        // Check if the quantity ordered is greater than the available stock
        if (quantity > currentStock) {
            System.out.println("Not enough stock available for book with ID: " + bookId);
            return; // Exit the method without inserting order details
        }

        // Proceed to insert order details if there is enough stock
        String query = "INSERT INTO OrderDetails (OrderID, BookID, Quantity, Price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(query)) {
            // Set the values for the prepared statement
            statement.setInt(1, orderId);
            statement.setInt(2, bookId);
            statement.setInt(3, quantity);
            statement.setDouble(4, price);

            // Execute the query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                // If the order details were inserted successfully, update the stock of the book
                updateBookStock(bookId, quantity);
                System.out.println("Order detail inserted successfully.");
            } else {
                System.out.println("Failed to insert order detail.");
            }
        }
    }


    private void updateBookStock(int bookId, int quantityOrdered) throws SQLException {
        // Retrieve the current stock of the book
        Book book = retrieveBookById(bookId);
        int currentStock = book.getStock();

        // Calculate the new stock after deducting the quantity ordered
        int newStock = currentStock - quantityOrdered;

        // Update the stock of the book in the database
        String updateQuery = "UPDATE book SET QuantityAvailable = ? WHERE BookID = ?";
        try (PreparedStatement updateStatement = con.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, newStock);
            updateStatement.setInt(2, bookId);
            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Stock updated successfully for book with ID: " + bookId);
            } else {
                System.out.println("Failed to update stock for book with ID: " + bookId);
            }
        }
    }





    public void updateCartStatus(int userId) throws SQLException {
        // SQL query to update cart items status to inactive
        String query = "UPDATE ShoppingCart SET Status = 'inactive' WHERE UserID = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Cart items status updated successfully.");
            } else {
                System.out.println("No cart items found for the user.");
            }
        }
    }

    public void updateBookStatus(int bookid, String status) throws SQLException {
        // SQL query to update book status
        String query = "UPDATE book SET Status = ? WHERE BookID = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, bookid);

            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book status updated successfully.");
            } else {
                System.out.println("No book found with the specified ID.");
            }
        }
    }


    public List<Book> retrieveBooksByAuthor(String author) throws SQLException {
        List<Book> books = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT * FROM book WHERE Author = ?");
            ps.setString(1, author);
            rs = ps.executeQuery();

            while (rs.next()) {
                int bookid = rs.getInt("BookID");
                String isbn = rs.getString("ISBN");
                String title = rs.getString("Title");
                String authorName = rs.getString("Author");
                double price = rs.getDouble("Price");
                int stock = rs.getInt("QuantityAvailable");
                String imageUrl = rs.getString("ImageURL");
                String description = rs.getString("Description");
                String publisher = rs.getString("Publisher");
                Date publishDate = rs.getDate("PublishDate");
                String status = rs.getString("Status");

                Book book = new Book(bookid, isbn, title, authorName, price, stock, imageUrl, description, publisher, publishDate,status);
                books.add(book);
            }
        } finally {
            // Close resources
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
        return books;
    }


    public int getQuantityInCart(int cartId) throws SQLException {
        int quantity = 0;

        String query = "SELECT Quantity FROM ShoppingCart WHERE CartID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                quantity = rs.getInt("Quantity");
            }
        } catch (SQLException e) {
            throw e;
        }

        return quantity;
    }



    public void insertCardInfo(int PaymentID, String cardNumber, String cvv, String expiryDate) throws SQLException {
        String query = "INSERT INTO Card (PaymentID, CardNumber, ExpiryDate, CVV) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(query)) {
            // Set the values for the prepared statement
            statement.setInt(1, PaymentID);
            statement.setString(2, cardNumber);

            // Check if expiryDate is not null or empty
            if (expiryDate == null || expiryDate.isEmpty()) {
                throw new IllegalArgumentException("Expiry date cannot be null or empty");
            }

            // Convert the expiry date to YYYY-MM-DD format
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date parsedDate;
            try {
                parsedDate = dateFormat.parse(expiryDate);
            } catch (ParseException e) {
                System.err.println("Failed to parse expiry date: " + expiryDate);
                throw e; // Rethrow the exception to handle it outside
            }
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
            statement.setDate(3, sqlDate);

            statement.setString(4, cvv);

            // Execute the query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Credit card information inserted successfully.");
            } else {
                System.out.println("Failed to insert credit card information.");
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred while inserting card information: " + e.getMessage());
            throw e; // Rethrow exception to signal failure to the caller
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected exception occurred: " + e.getMessage());
        }
    }






}


