package com.mimecast.books.dto;

import com.mimecast.books.model.Books;

import java.util.List;

public class AuthorDTO {
    private String name;
    private List<Books> books;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }
}
