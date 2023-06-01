class SearchCriteria {
    
    private String title;
    private String author;
    private  String genre;
    private int publicationYear;
    private double minPrice;
    private double maxPrice;

    public SearchCriteria(String title, String author,String genre, int publicationYear, double minPrice, double maxPrice) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;

    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;

    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }
}
