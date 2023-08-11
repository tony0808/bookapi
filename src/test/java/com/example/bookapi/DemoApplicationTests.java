package com.example.bookapi;

import com.example.bookapi.dto.BookDTO;
import com.example.bookapi.entity.Book;
import com.example.bookapi.exception.BookNotFoundException;
import com.example.bookapi.repository.BookRepository;
import com.example.bookapi.service.BookService;
import lombok.ToString;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@SpringBootTest
class DemoApplicationTests {

	@Container
	public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
			.withUsername("tony0808")
			.withPassword("pass123")
			.withDatabaseName("bookdb");

	@Autowired
	private BookService bookService;

	@BeforeAll
	static void beforeAll() {
		container.start();
	}


	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.password", container::getPassword);
	}

	@Test
	void retrieveBookTest() {
		bookService.saveBook(new BookDTO(1L, "titleA", "genreA", 200, null));
		BookDTO bookDTO = bookService.getBookById(1L).getBody();
		assertEquals("titleA", bookDTO.getTitle());
	}

	@Test
	void deleteBookTest() {
		bookService.saveBook(new BookDTO(1L, "titleA", "genreA", 200, null));
		BookDTO bookDTO = bookService.getBookById(1L).getBody();
		assertEquals("titleA", bookDTO.getTitle());
		bookService.deleteBookById(1L);
		Exception exception = assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1L));
	}

	@Test
	void countBooksTest() {
		bookService.saveBook(new BookDTO(1L, "titleA", "genreA", 200, null));
		bookService.saveBook(new BookDTO(2L, "titleB", "genreB", 200, null));
		bookService.saveBook(new BookDTO(3L, "titleC", "genreC", 200, null));
		bookService.saveBook(new BookDTO(4L, "titleD", "genreD", 200, null));
		assertEquals(4, bookService.getAllBooks().getBody().size());
	}

	@Test
	void contextLoads() {
		System.out.println("Context loads: " + container.getJdbcUrl());
	}

}
