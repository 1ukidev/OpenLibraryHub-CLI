package openlibraryhub.entities;

import openlibraryhub.interfaces.Entity;

public class BookEntity extends Entity {
    private Integer id;
    private String title;
    private String author;
    private String section;
    private Integer pages;
    private Integer year;
    private Integer stock;

    public BookEntity() {
        
    }

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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}