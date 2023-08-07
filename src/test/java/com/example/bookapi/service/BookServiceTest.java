package com.example.bookapi.service;

import com.example.bookapi.dto.BookDTO;
import com.example.bookapi.entity.Book;
import com.example.bookapi.repository.BookRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookServiceTest {

    private BookService bookService;
    private BookRepository bookRepository;
    private Book sampleBook;
    private List<Book> sampleBookList;

    @BeforeEach
    void setup() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository, new ModelMapper());
        sampleBook = createSampleBook(1L, "", "", 2);
        sampleBookList = createListOfSampleBooks();
    }

    @Test
    void getAllBooks() {
        when(bookRepository.findAll()).thenReturn(sampleBookList);
        ResponseEntity<List<BookDTO>> res = bookService.getAllBooks();
        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getBookById() {
        Optional<Book> bookOptional = Optional.ofNullable(sampleBook);
        when(bookRepository.findById(1L)).thenReturn(bookOptional);
        ResponseEntity<BookDTO> res = bookService.getBookById(1L);
        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void saveBook() {
        Book savedBook = createSampleBook(2L, "", "", 3);
        BookDTO bookDTO = new ModelMapper().map(sampleBook, BookDTO.class);
        when(bookRepository.save(sampleBook)).thenReturn(savedBook);
        ResponseEntity<BookDTO> res = bookService.saveBook(bookDTO);
        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void deleteBookById() {
        Optional<Book> bookOptional = Optional.ofNullable(sampleBook);
        when(bookRepository.findById(1L)).thenReturn(bookOptional);
        ResponseEntity<BookDTO> res = bookService.deleteBookById(1L);
        Assertions.assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    List<Book> createListOfSampleBooks() {
        List<Book> books = new ArrayList<>();
        for(int i=1; i<4; i++) {
            books.add(createSampleBook((long) i, "", "", 2));
        }
        return books;
    }

    Book createSampleBook(Long id, String title, String genre, int pages) {
        Book sampleBook = new Book();
        sampleBook.setId(id);
        sampleBook.setTitle(title);
        sampleBook.setGenre(genre);
        sampleBook.setPages(pages);
        sampleBook.setAuthors(null);
        return sampleBook;
    }
}