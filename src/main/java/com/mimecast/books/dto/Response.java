package com.mimecast.books.dto;

import com.mimecast.books.model.Author;
import org.springframework.http.HttpStatus;

public class Response {
    private Author author;
    private String message;
    private String errorMessage;
    private HttpStatus httpStatus;

    public Response(String message, String errorMessage, HttpStatus httpStatus) {
        this.message = message;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public Response(Author author, String message, String errorMessage, HttpStatus httpStatus) {
        this.author = author;
        this.message = message;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
