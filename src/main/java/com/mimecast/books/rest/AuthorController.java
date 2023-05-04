package com.mimecast.books.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mimecast.books.dto.AuthorDTO;
import com.mimecast.books.model.Author;
import com.mimecast.books.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping("/save")
    public ResponseEntity<String> createAuthor(@RequestBody AuthorDTO authorDTO) throws JsonProcessingException {
        /*
            todo: implement
         */
        authorService.createAuthor(authorDTO);
        return new ResponseEntity<>("Author Created", HttpStatus.CREATED);
    }

    //todo: implement getAuthor
    @GetMapping("/get/{name}")
    public ResponseEntity<Author> getAuthor(@PathVariable String name){
        Author author = authorService.getAuthorData(name);
        if(author != null){
            return  new ResponseEntity<>(author, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}

