package com.example.bookapi.repository;

import com.example.bookapi.dto.AuthorDTO;
import com.example.bookapi.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = SqlQueries.AUTHORS_WITH_NO_ASSIGNED_BOOK_QUERY, nativeQuery = true)
    List<Author> getAuthorsWithNoAssignedBooks();
}
