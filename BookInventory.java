import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

class BookInventory {
    private List<Book> books;
    private int nextBookId;
    private static String filePath;
    public BookInventory() {
        this.books = getBooks();
        nextBookId = 1;
    }
    public void addBook(Book book) {
        book.setId(nextBookId++);
        books.add(book);
        saveBooksToFile(books);
    }

    public void updateBook(Book book){
        System.out.println("Book details updated: " + book.toInventoryString());
        BookstoreApp.logger.info("Book details updated: "+ book.toInventoryString());
        saveBooksToFile(books);
    }
    public void removeBook(Book book) {
        if (book != null) {
            books.remove(book);
            saveBooksToFile(books);
        }
    }
    public List<Book> getBooks() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("BookstoreApp.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        filePath = properties.getProperty("Filepath");
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            books = reader.lines().map(line->{
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                String author = parts[2];
                String genre = parts[3];
                int publicationYear = Integer.parseInt(parts[4]);
                double price = Double.parseDouble(parts[5]);
                int quantity = Integer.parseInt(parts[6]);
                return new Book(id, title, author, genre, publicationYear, price, quantity);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> searchBooks(SearchCriteria criteria){

        if(criteria.getTitle() != null){
            return books.stream().filter(book -> book.getTitle().toLowerCase().contains(criteria.getTitle().toLowerCase())).collect(Collectors.toList());
        }
        else if(criteria.getAuthor() != null){
            return books.stream().filter(book -> book.getTitle().toLowerCase().contains(criteria.getAuthor().toLowerCase())).collect(Collectors.toList());
        }
        else if (criteria.getPublicationYear()>0) {
            return books.stream().filter(book -> book.getPublicationYear() == criteria.getPublicationYear()).collect(Collectors.toList());
        }
        else if(criteria.getMinPrice()>0.0 && criteria.getMaxPrice()>0.0){
            return books.stream().filter(book -> book.getPrice()>= criteria.getMinPrice() && book.getPrice() <= criteria.getMaxPrice()).collect(Collectors.toList());
        }
        else if(criteria.getGenre() !=null){
            return books.stream().filter(book -> book.getGenre().toLowerCase().contains(criteria.getGenre().toLowerCase())).collect(Collectors.toList());
        }
        return null;
    }

    public void generateReport() {

        int totalBooks = books.size();
        double totalPrice = books.stream().mapToDouble(Book::getPrice).sum();
        Book bookWithHighestPrice = books.stream().max(Comparator.comparingDouble(Book::getPrice)).get();
        Book bookWithLowestPrice = books.stream().min(Comparator.comparingDouble(Book::getPrice)).get();
        double averagePrice = totalPrice / totalBooks;

        System.out.println("Inventory Report");
        System.out.println("Total number of books: " + totalBooks);
        System.out.println("Average price: " + averagePrice);
        System.out.println("Book with the highest price: " + bookWithHighestPrice.getTitle());
        System.out.println("Book with the lowest price: " + bookWithLowestPrice.getTitle());
    }

    public Book findBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static void saveBooksToFile(List<Book> books) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("BookstoreApp.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        filePath = properties.getProperty("Filepath");
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            books.stream().map(Book::toFileString).forEach(writer::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}