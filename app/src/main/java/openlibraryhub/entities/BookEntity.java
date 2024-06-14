package openlibraryhub.entities;

public class BookEntity extends Entity {
    private Integer id;
    private String title;
    private String author;
    private String section;
    private Integer pages;
    private Integer year;
    private Integer stock;

    public BookEntity() {}

    public BookEntity(String title, String author,
                      String section, Integer pages,
                      Integer year, Integer stock) {
        this.title = title;
        this.author = author;
        this.section = section;
        this.pages = pages;
        this.year = year;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public BookEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BookEntity setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getSection() {
        return section;
    }

    public BookEntity setSection(String section) {
        this.section = section;
        return this;
    }

    public Integer getPages() {
        return pages;
    }

    public BookEntity setPages(Integer pages) {
        this.pages = pages;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public BookEntity setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Integer getStock() {
        return stock;
    }

    public BookEntity setStock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public String toString() {
        return "ID: " + id + "\n" +
               "Título: " + title + "\n" +
               "Autor: " + author + "\n" +
               "Seção: " + section + "\n" +
               "Páginas: " + pages + "\n" +
               "Ano: " + year + "\n" +
               "Estoque: " + stock + "\n";
    }
}
