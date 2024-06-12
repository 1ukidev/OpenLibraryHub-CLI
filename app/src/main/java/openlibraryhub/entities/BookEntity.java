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

    public void setId(Integer id) {
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
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
