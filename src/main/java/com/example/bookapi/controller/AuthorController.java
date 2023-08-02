package com.example.bookapi.controller;

import com.example.bookapi.dto.AuthorDTO;
import com.example.bookapi.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return authorService.getAllAuthors();
    }

    @GetMapping("authors-with-no-books")
    public ResponseEntity<List<AuthorDTO>> getAuthorsWithNoBooksAssigned() {
        return authorService.getAuthorsWithNoAssignedBooks();
    }

    @GetMapping("{author_id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long author_id) {
        return authorService.getAuthorById(author_id);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorService.saveAuthor(authorDTO);
    }

    @DeleteMapping("{author_id}")
    public ResponseEntity<AuthorDTO> deleteAuthor(@PathVariable Long author_id) {
        return authorService.deleteAuthorById(author_id);
    }
}
