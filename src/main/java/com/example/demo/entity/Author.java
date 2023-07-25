package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * booksToString -> authorsToString -> booksToString ... etc.
 *
 * IMPORTANT -> USE DTOS
 *
 * ALWAYS OVERRIDE EQUALS AND HASHCODE
 *
 * PREFER IDENTITY INSTEAD OF SEQUENCE, SOME DATABASE VENDORS DOES NOT KNOW ABOUT SEQUENCES.
 *
 */
@Entity
@Table(name = "authors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("authors")
    private List<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Author author = (Author) o;
        return getId() != null && Objects.equals(getId(), author.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
