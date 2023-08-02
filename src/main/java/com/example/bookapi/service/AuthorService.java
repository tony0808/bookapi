package com.example.bookapi.service;

import com.example.bookapi.dto.AuthorDTO;
import com.example.bookapi.entity.Author;
import com.example.bookapi.exception.AuthorNotFoundException;
import com.example.bookapi.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;
    private ModelMapper modelMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authorDTOS = authorRepository.findAll().stream()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(authorDTOS, HttpStatus.OK);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<AuthorDTO> getAuthorById(Long author_id) {
        Optional<Author> authorOptional = authorRepository.findById(author_id);
        if(authorOptional.isPresent()) {
            AuthorDTO authorDTO = modelMapper.map(authorOptional.get(), AuthorDTO.class);
            return new ResponseEntity<>(authorDTO, HttpStatus.OK);
        }
        else {
            throw new AuthorNotFoundException(author_id);
        }
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public ResponseEntity<AuthorDTO> saveAuthor(AuthorDTO authorDTO) {
        Author author = modelMapper.map(authorDTO, Author.class);
        Author savedAuthor = authorRepository.save(author);
        AuthorDTO authorDTO1 = modelMapper.map(savedAuthor, AuthorDTO.class);
        return new ResponseEntity<>(authorDTO1, HttpStatus.CREATED);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<AuthorDTO> deleteAuthorById(Long author_id) {
        Optional<Author> authorOptional = authorRepository.findById(author_id);
        if(authorOptional.isPresent()) {
            AuthorDTO authorDTO = modelMapper.map(authorOptional.get(), AuthorDTO.class);
            authorRepository.deleteById(author_id);
            return new ResponseEntity<>(authorDTO, HttpStatus.OK);
        }
        else {
            throw new AuthorNotFoundException(author_id);
        }
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<List<AuthorDTO>> getAuthorsWithNoAssignedBooks() {
        List<AuthorDTO> authorDTOS = authorRepository.getAuthorsWithNoAssignedBooks().stream()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(authorDTOS, HttpStatus.OK);
    }

}
