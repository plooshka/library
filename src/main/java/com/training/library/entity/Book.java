package com.training.library.entity;

import java.io.Serializable;

public class Book implements Serializable {

    private Long id;

    private String bookName;

    private String author;

    public Book(){
    }

    public Book(String bookName, String author) {
        this.bookName = bookName;
        this.author = author;
    }

    public Book(Long id, String bookName, String author) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
