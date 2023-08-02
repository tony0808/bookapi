package com.example.bookapi.repository;

public class SqlQueries {

    public static final String AUTHORS_WITH_NO_ASSIGNED_BOOK_QUERY = "select authors.id, authors.name, authors.age \n" +
            "from authors LEFT JOIN books_authors\n" +
            "on authors.id = books_authors.author_fk\n" +
            "where books_authors.author_fk is null;";
    public static final String BOOKS_WITH_AT_LEAST_2_AUTHORS = "select * \n" +
            "from books JOIN \n" +
            "(select books_authors.book_fk\n" +
            "from books LEFT JOIN books_authors on books.id = books_authors.book_fk\n" +
            "group by books_authors.book_fk\n" +
            "having count(*)>= 2) as x\n" +
            "on books.id = x.book_fk;";
}
