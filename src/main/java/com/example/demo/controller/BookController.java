package com.example.demo.controller;

import com.example.demo.entity.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Book;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        books.forEach(book -> System.out.println(book));
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
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
        Book book = bookRepository.findById(book_id).get();
        Author author = authorRepository.findById(author_id).get();
        book.getAuthors().add(author);
        Book book1 = bookRepository.save(book);
        System.out.println(book1);
        return new ResponseEntity<>(book1, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }
}
