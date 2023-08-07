package com.example.bookapi.controller;

import com.example.bookapi.dto.AuthorDTO;
import com.example.bookapi.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authorDTOS = authorService.getAllAuthors();
        return new ResponseEntity<>(authorDTOS, HttpStatus.OK);
    }

    @GetMapping("authors-with-no-books")
    public ResponseEntity<List<AuthorDTO>> getAuthorsWithNoBooksAssigned() {
        List<AuthorDTO> authorDTOS = authorService.getAuthorsWithNoAssignedBooks();
        return new ResponseEntity<>(authorDTOS, HttpStatus.OK);
    }

    @GetMapping("{author_id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long author_id) {
        AuthorDTO authorDTO = authorService.getAuthorById(author_id);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody AuthorDTO authorDTO) {
        AuthorDTO savedAuthor = authorService.saveAuthor(authorDTO);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }

    @DeleteMapping("{author_id}")
    public ResponseEntity<AuthorDTO> deleteAuthor(@PathVariable Long author_id) {
        AuthorDTO deletedAuthor = authorService.deleteAuthorById(author_id);
        return new ResponseEntity<>(deletedAuthor, HttpStatus.OK);
    }
}
