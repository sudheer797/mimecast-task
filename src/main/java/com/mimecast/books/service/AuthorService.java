package com.mimecast.books.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mimecast.books.dto.AuthorDTO;
import com.mimecast.books.dto.ResponseDTO;
import com.mimecast.books.dto.Results;
import com.mimecast.books.model.Author;
import com.mimecast.books.model.Books;
import com.mimecast.books.repo.AuthorRepository;
import org.apache.tomcat.util.json.JSONParser;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class AuthorService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private Environment environment;

    public void getAuthor(String name){

    }

    public void createAuthor(AuthorDTO authorDTO) throws JsonProcessingException {
        Author author = new Author();
        author.setId(sequenceGeneratorService.generateSequence(Author.SEQUENCE_NAME));
        author.setName(authorDTO.getName());
        String endpoint = environment.getProperty("api-endpoint","https://api.nytimes.com/svc/books/v3/reviews.json");
        String uriTemplate = UriComponentsBuilder.fromHttpUrl(endpoint)
                .queryParam("author", "{author}")
                .queryParam("api-key", "{api-key}")
                .encode()
                .toUriString();
        System.out.println("URL: "+uriTemplate);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> reqEntity = new HttpEntity<>(headers);

        Map<String,String> params = new HashMap<>();
        params.put("author",authorDTO.getName());
        params.put("api-key",environment.getProperty("api-key","dLDfinAvb02iT3Qsw9kamyqYBAUXJmEc"));
        ResponseEntity<Object> response = restTemplate
            .exchange(uriTemplate, HttpMethod.GET, reqEntity, Object.class, params);
        System.out.println("Response: "+response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        String responseString = mapper.writeValueAsString(response.getBody());
        ResponseDTO responseDTO = mapper.readValue(responseString, ResponseDTO.class);
//        ResponseDTO responseDTO = response.getBody();
        List<Books> booksList = new ArrayList<>();
        for(Results r: responseDTO.getResults()){
            Books book = new Books();
            book.setBookName(r.getBookTitle());
            book.setSummary(r.getSummary());
            booksList.add(book);
        }
        author.setBooks(booksList);
        authorRepository.save(author);
    }

    public Author getAuthorData(String name){
        Optional<Author> authOpt = authorRepository.findByName(name);
        if(authOpt.isPresent())
            return authOpt.get();
        return null;
    }
}
