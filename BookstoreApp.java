import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {

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

class BookInventory {
    private List<Book> books;
    private int nextBookId;
    public BookInventory() {
        this.books = getBookList();
        nextBookId = 1;
    }

    public void addBook(Book book) {
        book.setId(nextBookId++);
        books.add(book);
        saveBooksToFile(books);

    }

    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }
    public List<Book> updateBook(){
        return books;
    }

    public Book removeBookById(int id) {
        Book book = findBookById(id);
        if (book != null) {
            books.remove(book);
            saveBooksToFile(books);
        }
        return book;
    }
    public static void saveBooksToFile(List<Book> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("D:\\microsoft\\Bookinventory\\BookstoreApp\\BookstoreApp.txt"))) {

            for (Book book : books) {
                writer.println(book.toFileString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Book> getBookList() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\microsoft\\Bookinventory\\BookstoreApp\\BookstoreApp.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    int publicationYear = Integer.parseInt(parts[3]);
                    double price = Double.parseDouble(parts[4]);
                    int quantity = Integer.parseInt(parts[5]);
                    Book book = new Book(id, title, author, publicationYear, price, quantity);
                    books.add(book);
                    if (id >= nextBookId) {
                        nextBookId = id + 1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> searchBooksByTitle(String title) {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setTitle(title);
        return searchCriteria.searchBooksTitle(books);
    }

    public List<Book> searchBooksByAuthor(String author) {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setAuthor(author);
        return searchCriteria.searchBooksByAuthor(books);
    }

    public List<Book> searchBooksByPublicationYear(int publicationYear) {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setPublicationYear(publicationYear);
        return searchCriteria.searchBooksByPublicationYear(books);
    }

    public List<Book> searchBooksByPriceRange(double minPrice, double maxPrice) {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setPrice(minPrice,maxPrice);
        return searchCriteria.searchBooksByPriceRange(books);
    }
}

class BookstoreApp {
    private static Scanner scanner;
    private static BookInventory bookInventory;

    public static void main(String[] args) {
        bookInventory = new BookInventory();
        scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nBook Inventory System");
            System.out.println("1. Add a book");
            System.out.println("2. Search a book");
            System.out.println("3. Display all books");
            System.out.println("4. Update book details");
            System.out.println("5. Remove a book");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    displayAllBooks();
                    break;
                case 4:
                    updateBookDetails();
                    break;
                case 5:
                    removeBook();
                    break;
                case 6:
                    System.out.println("Exiting the program...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    protected static void addBook() {
        System.out.println("Enter the book details:");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Publication Year: ");
        int publicationYear = scanner.nextInt();
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();

        Book book = new Book(title, author, publicationYear, price, quantity);
        bookInventory.addBook(book);
        System.out.println("Book added: " + book.getTitle());
    }

    protected static void searchBook() {
        System.out.println("Search By:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Publication Year");
        System.out.println("4. Price Range");
        System.out.println("5. Cancel");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String keyword;
        List<Book> results;
        switch (choice) {
            case 1:
                System.out.print("Enter the title keyword: ");
                keyword = scanner.nextLine();
                results = bookInventory.searchBooksByTitle(keyword);
                displaySearchResults(results);
                break;
            case 2:
                System.out.print("Enter the author keyword: ");
                keyword = scanner.nextLine();
                results = bookInventory.searchBooksByAuthor(keyword);
                displaySearchResults(results);
                break;
            case 3:
                System.out.print("Enter the publication year: ");
                int publicationYear = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                results = bookInventory.searchBooksByPublicationYear(publicationYear);
                displaySearchResults(results);
                break;
            case 4:
                System.out.print("Enter the minimum price: ");
                double minPrice = scanner.nextDouble();
                System.out.print("Enter the maximum price: ");
                double maxPrice = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character
                results = bookInventory.searchBooksByPriceRange(minPrice, maxPrice);
                displaySearchResults(results);
                break;
            case 5:
                System.out.println("Search canceled.");
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    protected static void displaySearchResults(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("Search Results:");
            for (Book book : books) {
                System.out.println(book.toInventoryString());
            }
        }
    }

    protected static void displayAllBooks() {
        List<Book> books = bookInventory.getBookList();
        if (books.isEmpty()) {
            System.out.println("No books in the inventory.");
        } else {
            System.out.println("Book Inventory:");
            System.out.println("ID\tTitle\t\tAuthor\t\tPublication Year\tPrice\tQuantity");
            for (Book book : books) {
                System.out.println(book.toInventoryString());
            }
        }
    }

    protected static void updateBookDetails() {
        System.out.println("Enter the ID of the book to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Book book = bookInventory.findBookById(id);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.println("Enter the new title (leave blank to keep the same): ");
        String newTitle = scanner.nextLine();
        if (!newTitle.isEmpty()) {
            book.setTitle(newTitle);
        }

        System.out.println("Enter the new author (leave blank to keep the same): ");
        String newAuthor = scanner.nextLine();
        if (!newAuthor.isEmpty()) {
            book.setAuthor(newAuthor);
        }

        System.out.println("Enter the new publication year (leave blank to keep the same): ");
        String newPublicationYear = scanner.nextLine();
        if (!newPublicationYear.isEmpty()) {
            book.setPublicationYear(Integer.parseInt(newPublicationYear));
        }

        System.out.println("Enter the new price (leave blank to keep the same): ");
        String newPrice = scanner.nextLine();
        if (!newPrice.isEmpty()) {
            book.setPrice(Double.parseDouble(newPrice));
        }

        System.out.println("Enter the new quantity (leave blank to keep the same): ");
        String newQuantity = scanner.nextLine();
        if (!newQuantity.isEmpty()) {
            book.setQuantity(Integer.parseInt(newQuantity));
        }

        System.out.println("Book details updated: " + book.toInventoryString());
        BookInventory.saveBooksToFile(bookInventory.updateBook());
    }

    protected static void removeBook() {
        System.out.println("Enter the ID of the book to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Book book = bookInventory.removeBookById(id);
        if (book == null) {
            System.out.println("Book not found.");
        } else {
            System.out.println("Book removed: " + book.getTitle());
        }
    }
}

class SearchCriteria{

    private String title;
    private String author;
    private int publicationYear;
    private double minPrice;
    private double maxPrice;

    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setPrice(double minPrice,double maxPrice){
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
    public List<Book> searchBooksTitle(List<Book> books) {
        List<Book> searchResults = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                searchResults.add(book);
            }
        }
        return searchResults;
    }
    public List<Book> searchBooksByAuthor(List<Book> books) {
        List<Book> searchResults = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                searchResults.add(book);
            }
        }
        return searchResults;
    }
    public List<Book> searchBooksByPublicationYear(List<Book> books) {
        List<Book>  searchResults = new ArrayList<>();
        for (Book book : books) {
            if (book.getPublicationYear() == (publicationYear)) {
                searchResults.add(book);
            }
        }
        return searchResults;
    }
    public List<Book> searchBooksByPriceRange(List<Book> books) {
        List<Book> searchResults = new ArrayList<>();
        for (Book book : books) {
            if (book.getPrice() >= minPrice && book.getPrice() <= maxPrice) {
                searchResults.add(book);
            }
        }
        return searchResults;
    }

}
