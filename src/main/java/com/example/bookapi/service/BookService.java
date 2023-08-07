package com.example.bookapi.service;

import com.example.bookapi.controller.BookController;
import com.example.bookapi.dto.AuthorDTO;
import com.example.bookapi.dto.BookDTO;
import com.example.bookapi.entity.Author;
import com.example.bookapi.entity.Book;
import com.example.bookapi.exception.AuthorNotFoundException;
import com.example.bookapi.exception.BookNotFoundException;
import com.example.bookapi.repository.AuthorRepository;
import com.example.bookapi.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final Logger LOG = LoggerFactory.getLogger(BookService.class);
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private ModelMapper modelMapper;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    public BookService(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> bookDTOs = bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
        LOG.info("Retrieving all books from DB.");
        return new ResponseEntity<>(bookDTOs, HttpStatus.OK);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<BookDTO> getBookById(Long book_id) {
        Optional<Book> optionalBook = bookRepository.findById(book_id);
        if(optionalBook.isPresent()) {
            BookDTO bookDTO = modelMapper.map(optionalBook.get(), BookDTO.class);
            return new ResponseEntity<>(bookDTO, HttpStatus.OK);
        }
        else {
            throw new BookNotFoundException(book_id);
        }
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public ResponseEntity<BookDTO> saveBook(BookDTO bookDTO) {
        if(bookDTO.getPages() < 0) {
            LOG.warn("The number of pages of the book cannot be negative.");
        }
        Book book = modelMapper.map(bookDTO, Book.class);
        Book savedBook = bookRepository.save(book);
        BookDTO bookDTO1 = modelMapper.map(savedBook, BookDTO.class);
        return new ResponseEntity<>(bookDTO1, HttpStatus.CREATED);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<BookDTO> deleteBookById(Long book_id) {
        Optional<Book> optionalBookbook = bookRepository.findById(book_id);
        if(optionalBookbook.isPresent()) {
            BookDTO bookDTO = modelMapper.map(optionalBookbook.get(), BookDTO.class);
            bookRepository.deleteById(book_id);
            LOG.info("Deleting book with id " + book_id);
            return new ResponseEntity<>(bookDTO, HttpStatus.OK);
        }
        else {
            LOG.error("Book with id " + book_id + " does not exist.");
            throw new BookNotFoundException(book_id);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<BookDTO> assignExistingAuthorToBook(Long book_id, Long author_id) {
        Optional<Book> optionalBook = bookRepository.findById(book_id);
        Optional<Author> optionalAuthor = authorRepository.findById(author_id);
        if(optionalBook.isPresent() && optionalAuthor.isPresent()) {
            Book book = optionalBook.get();
            Author author = optionalAuthor.get();
            book.getAuthors().add(author);
            Book savedBook = bookRepository.save(book);
            BookDTO bookDTO = modelMapper.map(savedBook, BookDTO.class);
            return new ResponseEntity<>(bookDTO, HttpStatus.OK);
        }
        else if(optionalBook.isEmpty()) {
            throw new BookNotFoundException(book_id);
        }
        else {
            throw new AuthorNotFoundException(author_id);
        }
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<List<BookDTO>> getBooksWithAtLeast2Authors() {
        List<BookDTO> bookDTOS = bookRepository.getBooksWithAtLeast2Authors().stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookDTOS, HttpStatus.OK);
    }
}
