public class Book {

    private int id;
    private String title;
    private String author;
    private int publicationYear;
    private double price;
    private int quantity;

    public Book(String title, String author, int publicationYear, double price, int quantity) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.price = price;
        this.quantity = quantity;
    }

    public Book(int id, String title, String author, int publicationYear, double price, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters and setters...
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String toInventoryString() {
        return id + "\t" + title + "\t\t" + author + "\t\t" + publicationYear + "\t\t" + price + "\t" + quantity;
    }
    public String toFileString() {
        return id + "," + title + "," + author + "," + publicationYear + "," + price + "," + quantity;
    }
}
