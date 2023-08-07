package com.example.bookapi.controller;

import com.example.bookapi.dto.BookDTO;
import com.example.bookapi.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    private BookService bookService;
    private final LogHeaderUsername logHeaderUsername;

    private final Logger LOG = LoggerFactory.getLogger(BookController.class);

    @Autowired
    public BookController(BookService bookService, LogHeaderUsername logHeaderUsername) {
        this.bookService = bookService;
        this.logHeaderUsername = logHeaderUsername;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("scope")
    public void logUsernameFromHeader(@RequestHeader("firstname") String firstname, @RequestHeader("lastname") String lastname) {
        logHeaderUsername.printUsername(firstname, lastname);
    }

    @GetMapping("{book_id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long book_id) {
        return bookService.getBookById(book_id);
    }

    @GetMapping("at-least-2-authors")
    public ResponseEntity<List<BookDTO>> getBooksWithAtLeast2Authors() {
        return bookService.getBooksWithAtLeast2Authors();
    }

    @PostMapping
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO bookDTO) {
        return bookService.saveBook(bookDTO);
    }

    @PutMapping("{book_id}/{author_id}")
    public ResponseEntity<BookDTO> assignAuthorToBook(@PathVariable Long book_id, @PathVariable Long author_id) {
        return bookService.assignExistingAuthorToBook(book_id, author_id);
    }

    @DeleteMapping("{book_id}")
    public ResponseEntity<BookDTO> deleteBookById(@PathVariable Long book_id) {
        return bookService.deleteBookById(book_id);
    }
}
