package com.example.bookapi.repository;

import com.example.bookapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = SqlQueries.BOOKS_WITH_AT_LEAST_2_AUTHORS, nativeQuery = true)
    List<Book> getBooksWithAtLeast2Authors();
}
