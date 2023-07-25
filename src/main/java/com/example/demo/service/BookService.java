package com.example.demo.service;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    public Book addAuthorToBook(Long bookId, Long authorId) {
        Book book = bookRepository.findById(bookId).get();
        Author author = authorRepository.findById(authorId).get();
        book.getAuthors().add(author);
        return persistBook(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book persistBook(Book book) {
        return bookRepository.save(book);
    }
}
