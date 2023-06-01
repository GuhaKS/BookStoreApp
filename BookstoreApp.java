import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BookstoreApp {
    private static Scanner scanner;
    private static BookInventory bookInventory;
    static Logger logger;

    private static String filePath;

    public static void main(String[] args) {
        bookInventory = new BookInventory();
        scanner = new Scanner(System.in);
        logger = Logger.getLogger(BookstoreApp.class.getName());
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("BookstoreApp.properties"));
            filePath = properties.getProperty("Logpath");
            FileHandler fileHandler = new FileHandler(filePath, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {

            System.out.println("\nBook Inventory System");
            System.out.println("1. Add a book");
            System.out.println("2. Search a book");
            System.out.println("3. Display all books");
            System.out.println("4. Update book details");
            System.out.println("5. Remove a book");
            System.out.println("6. Generate Report");
            System.out.println("7. Exit");
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
                    updateBook();
                    break;
                case 5:
                    removeBook();
                    break;
                case 6:
                    generateReport();
                    break;
                case 7:
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
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        System.out.print("Publication Year: ");
        int publicationYear = scanner.nextInt();
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();

        Book book = new Book(title, author,genre, publicationYear, price, quantity);
        bookInventory.addBook(book);
        System.out.println("Book added: " + book.getTitle());
        logger.info("Book added :"+book.toInventoryString());
    }

    protected static void updateBook() {
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
            logger.info("Title updated : new Title => "+newTitle+" Old Title: "+book.getTitle());
            book.setTitle(newTitle);
        }

        System.out.println("Enter the new author (leave blank to keep the same): ");
        String newAuthor = scanner.nextLine();
        if (!newAuthor.isEmpty()) {
            logger.info("Author updated : New author => "+newAuthor+" Old author: "+book.getAuthor());
            book.setAuthor(newAuthor);
        }

        System.out.println("Enter the new publication year (leave blank to keep the same): ");
        String newPublicationYear = scanner.nextLine();
        if (!newPublicationYear.isEmpty()) {
            logger.info("Publication year updated : New publication year => "+newPublicationYear+" Old Publication year: "+book.getPublicationYear());
            book.setPublicationYear(Integer.parseInt(newPublicationYear));
        }

        System.out.println("Enter the new price (leave blank to keep the same): ");
        String newPrice = scanner.nextLine();
        if (!newPrice.isEmpty()) {
            logger.info("Title updated : New price => "+newPrice+" Old price: "+book.getPrice());
            book.setPrice(Double.parseDouble(newPrice));
        }

        System.out.println("Enter the new quantity (leave blank to keep the same): ");
        String newQuantity = scanner.nextLine();
        if (!newQuantity.isEmpty()) {
            logger.info("Title updated : New quantity => "+newQuantity+" Old quantity: "+book.getQuantity());
            book.setQuantity(Integer.parseInt(newQuantity));
        }
        bookInventory.updateBook(book);
        /*System.out.println("Book details updated: " + book.toInventoryString());
        logger.info("Book details updated: "+ book.toInventoryString());
        BookInventory.saveBooksToFile(bookInventory.updateBook(book));*/
    }

    protected static void removeBook() {
        System.out.println("Enter the ID of the book to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Book book = bookInventory.findBookById(id);
        if (book == null) {
            System.out.println("Book not found.");
        } else {
            bookInventory.removeBook(book);
        }
    }

    protected static void searchBook() {
        System.out.println("Search By:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Genre");
        System.out.println("4. Publication Year");
        System.out.println("5. Price Range");
        System.out.println("6. Cancel");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        SearchCriteria criteria;
        String keyword;
        List<Book> results;
        switch (choice) {
            case 1:
                System.out.print("Enter the title keyword: ");
                keyword = scanner.nextLine();
                logger.info("Searched with Title keyword: "+keyword);
                criteria = new SearchCriteria(keyword,null,null,0,0.0,0.0);
                results = bookInventory.searchBooks(criteria);
          //      SearchCriteria
                displaySearchResults(results);
                break;
            case 2:
                System.out.print("Enter the author keyword: ");
                keyword = scanner.nextLine();
                logger.info("Searched with Author keyword: "+keyword);
                criteria = new SearchCriteria(null,keyword,null,0,0.0,0.0);
                results = bookInventory.searchBooks(criteria);
                        //bookInventory.searchBooksByAuthor(keyword);
                displaySearchResults(results);
                break;
            case 3:
                System.out.print("Enter the genre keyword: ");
                keyword = scanner.nextLine();
                logger.info("Searched with genre keyword: "+keyword);
                criteria = new SearchCriteria(null,null,keyword,0,0.0,0.0);
                results = bookInventory.searchBooks(criteria);
                //bookInventory.searchBooksByAuthor(keyword);
                displaySearchResults(results);
                break;
            case 4:
                System.out.print("Enter the publication year: ");
                int publicationYear = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                logger.info("Searched with publication keyword: "+publicationYear);
                criteria = new SearchCriteria(null,null,null,publicationYear,0.0,0.0);
                results = bookInventory.searchBooks(criteria);
                    //    bookInventory.searchBooksByPublicationYear(publicationYear);
                displaySearchResults(results);
                break;
            case 5:
                System.out.print("Enter the minimum price: ");
                double minPrice = scanner.nextDouble();
                System.out.print("Enter the maximum price: ");
                double maxPrice = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character
                logger.info("Searched with Price range: maximum price:"+maxPrice+" & minimum price:"+minPrice);
                criteria = new SearchCriteria(null,null,null,0,minPrice,maxPrice);
                results = bookInventory.searchBooks(criteria);
                     //   bookInventory.searchBooksByPriceRange(minPrice, maxPrice);
                displaySearchResults(results);
                break;
            case 6:
                logger.info("Search canceled");
                System.out.println("Search canceled.");
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    protected static void generateReport(){
        bookInventory.generateReport();
    }

    protected static void displaySearchResults(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("Search Results:");
            books.stream().forEach(book -> System.out.println(book.toInventoryString()));
        }
    }

    protected static void displayAllBooks() {
        List<Book> books = bookInventory.getBooks();
        if (books.isEmpty()) {
            System.out.println("No books in the inventory.");
        } else {
            System.out.println("Book Inventory:");
            System.out.println("ID\tTitle\t\tAuthor\t\tGenre\t\tPublication Year\tPrice\tQuantity");
            books.stream().forEach(book -> System.out.println(book.toInventoryString()));
        }
    }


}