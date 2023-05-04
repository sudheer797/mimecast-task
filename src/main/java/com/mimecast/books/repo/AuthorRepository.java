package com.mimecast.books.repo;

import com.mimecast.books.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends MongoRepository<Author, Long> {
    Optional<Author> findByName(String name);
}
