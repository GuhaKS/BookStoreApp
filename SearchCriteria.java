import java.util.List;
import java.util.stream.Collectors;

public class SearchCriteria{

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
        return books.stream().filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase())).collect(Collectors.toList());
    }
    
    public List<Book> searchBooksByAuthor(List<Book> books) {
        return books.stream().filter(book -> book.getTitle().toLowerCase().contains(author.toLowerCase())).collect(Collectors.toList());
    }
    
    public List<Book> searchBooksByPublicationYear(List<Book> books) {
        return books.stream().filter(book -> book.getId() == publicationYear).collect(Collectors.toList());
    }
    
    public List<Book> searchBooksByPriceRange(List<Book> books) {
        return books.stream().filter(book -> book.getPrice()>= minPrice && book.getPrice() <= maxPrice).collect(Collectors.toList());
    }

}
