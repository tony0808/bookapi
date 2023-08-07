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
        List<BookDTO> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("scope")
    public void logUsernameFromHeader(@RequestHeader("firstname") String firstname, @RequestHeader("lastname") String lastname) {
        logHeaderUsername.printUsername(firstname, lastname);
    }

    @GetMapping("{book_id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long book_id) {
        BookDTO bookDTO = bookService.getBookById(book_id);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @GetMapping("at-least-2-authors")
    public ResponseEntity<List<BookDTO>> getBooksWithAtLeast2Authors() {
        List<BookDTO> booksWithAtLeast2Authors = bookService.getBooksWithAtLeast2Authors();
        return new ResponseEntity<>(booksWithAtLeast2Authors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO bookDTO) {
        BookDTO bookDTO1 = bookService.saveBook(bookDTO);
        return new ResponseEntity<>(bookDTO1, HttpStatus.CREATED);
    }

    @PutMapping("{book_id}/{author_id}")
    public ResponseEntity<BookDTO> assignAuthorToBook(@PathVariable Long book_id, @PathVariable Long author_id) {
        BookDTO bookDTO = bookService.assignExistingAuthorToBook(book_id, author_id);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @DeleteMapping("{book_id}")
    public ResponseEntity<BookDTO> deleteBookById(@PathVariable Long book_id) {
        BookDTO bookDTO = bookService.deleteBookById(book_id);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }
}
