package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 *
 * CONSIDER USING SERVICES WHICH WRAP THE REPOSITORIES AND USES TRANSACTIONS
 * EXAMPLE CLASSES @AuthorService.class, @BookService.class
 *
 */
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        System.out.println(book.get().getAuthors());
        return ResponseEntity.of(book);
    }

    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        Book book1 = bookRepository.save(book);
        return new ResponseEntity<>(book1, HttpStatus.CREATED);
    }

    @PostMapping("{book_id}/{author_id}")
    public ResponseEntity<Book> assignAuthorToBook(@PathVariable Long book_id, @PathVariable Long author_id) {
        return ResponseEntity.ok(bookService.addAuthorToBook(book_id, author_id));
    }

    @DeleteMapping("{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }
}
