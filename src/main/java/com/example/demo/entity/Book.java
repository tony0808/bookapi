package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

/**
 * AVOID USING LOMBOK ANNOTATIONS IN MANAGED ENTITIES, SEEMS TO BE BUGGY.
 * ADDED FETCHTYPE EAGER TO LOAD ALWAYS THE MANY TO MANY PROPS
 * INCLUDED @ToString(onlyExplicitlyIncluded = true) WHICH AVOIDS A STACKOVERFLOW WHEN
 * booksToString -> authorsToString -> booksToString ... etc. also ignored the properties of
 * books and authors respectively
 * to break the recursive cycle
 *
 * REMOVED UNNECESSARY REFERENCE TO ID, IS KNOWS BY DEFAULT THAT THE NAME IS ID
 *
 * IMPORTANT -> USE DTOS
 *
 * ALWAYS OVERRIDE EQUALS AND HASHCODE
 *
 * PREFER IDENTITY INSTEAD OF SEQUENCE, SOME DATABASE VENDORS DOES NOT KNOW ABOUT SEQUENCES.
 *
 */
@Entity
@Table(name = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int pages;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_fk"),
            inverseJoinColumns = @JoinColumn(name = "author_fk"))
    @JsonIgnoreProperties("books")
    private List<Author> authors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return getId() != null && Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
