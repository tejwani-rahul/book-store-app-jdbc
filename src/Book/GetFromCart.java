package Book;

public class GetFromCart {
    private int cartid;
    private int userid;
    private int bookid;
    private int quantity;
    private String status;



    // Constructor with parameters
    public GetFromCart(int cartid, int userid, int bookid, int quantity, String status) {
        this.cartid = cartid;
        this.userid = userid;
        this.bookid = bookid;
        this.quantity = quantity;
        this.status = status;
    }

    // Getters and setters
    public int getCartid() {
        return cartid;
    }


    public int getUserid() {
        return userid;
    }


    public int getBookid() {
        return bookid;
    }




    public int getQuantity() {

        return quantity;
    }


    public String getStatus() {
        return status;
    }

}
