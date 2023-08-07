package com.example.bookapi.dto;

import com.example.bookapi.entity.Book;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDTO {
    private Long id;
    private String name;
    private int age;

    @JsonIgnoreProperties("authors")
    private List<Book> books;
}
