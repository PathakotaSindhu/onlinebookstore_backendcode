package com.example.bookstore.Model;
import jakarta.persistence.*;
@Entity
public class Book {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  @Column(unique = true)
    private String title;
    private String author;

    private Double price;

    @Lob  // ✅ Allows large data like base64 strings
    @Column(name = "image_base64", columnDefinition = "LONGTEXT")
    private String imageBase64;

    @Lob  // ✅ Allows storing long text descriptions
    @Column(columnDefinition = "TEXT")
    private String description;

    // Constructors
    public Book() {
    }

    public Book(String title, String author, Double price, String imageBase64, String description) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.imageBase64 = imageBase64;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

