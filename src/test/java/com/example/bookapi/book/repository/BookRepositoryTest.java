package com.example.bookapi.book.repository;

import com.example.bookapi.entity.Book;
import com.example.bookapi.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.assertj.core.api.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    void checkIfBookExistsTest() {
        Book book = new Book(1L, "btitle", "bgenre", 2, null);
        bookRepository.save(book);
        boolean expected = bookRepository.existsById(1L);
        assertThat(expected).isTrue();
    }

    @Test
    void checkIfBookDoesNotExist() {
        Long book_id = 1L;
        boolean expected = bookRepository.existsById(book_id);
        assertThat(expected).isFalse();
    }
}