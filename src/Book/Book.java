package Book;

import java.sql.Date;

public class Book {
    private int bookid;
    private  String isbn;
    private String title;
    private String author;
    private double price;
    private int stock;
    private String imageUrl;
    private String description;
    private String publisher;
    private Date publishDate;
    private String status;

    // Constructor
    public Book(int bookid,String isbn,String title, String author, double price, int stock, String imageUrl, String description, String publisher, Date publishDate, String status) {
        this.bookid= bookid;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.description = description;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.status = status;
    }

    // Getters
    public int getBookid() {
        return bookid;
    }
    public String getIsbn() {
        return isbn;
    }
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getPublisher() {
        return publisher;
    }

    public Date getPublishDate() {
        return publishDate;
    }
    public String getStatus() {
        return status;
    }
}
