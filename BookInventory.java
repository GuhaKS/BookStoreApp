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
        this.books = getBookList();
        nextBookId = 1;
    }
    
    public void addBook(Book book) {
        book.setId(nextBookId++);
        books.add(book);
        saveBooksToFile(books);
    }

    public Book findBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
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
    
    public List<Book> getBookList() {
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
                int publicationYear = Integer.parseInt(parts[3]);
                double price = Double.parseDouble(parts[4]);
                int quantity = Integer.parseInt(parts[5]);
                return new Book(id, title, author, publicationYear, price, quantity);
            }).collect(Collectors.toList());
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
}
