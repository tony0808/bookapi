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
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@SpringBootTest
class DemoApplicationTests {

	@Container
	public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:11")
			.withUsername("tony0808")
			.withPassword("pass123")
			.withDatabaseName("bookdb")
			.withCopyToContainer(MountableFile.forClasspathResource("db/"), "/docker-entrypoint-initdb.d/");

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
		BookDTO bookDTO = bookService.getBookById(30L).getBody();
		assertEquals("1984", bookDTO.getTitle());
	}

	@Test
	void deleteBookTest() {
		BookDTO bookDTO = bookService.getBookById(10L).getBody();
		assert bookDTO != null;
		assertEquals("The Great Gatsby", bookDTO.getTitle());
		bookService.deleteBookById(10L);
		Exception exception = assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1L));
	}

	@Test
	void countBooksTest() {
		assertEquals(4, Objects.requireNonNull(bookService.getAllBooks().getBody()).size());
	}

	@Test
	void contextLoads() throws InterruptedException {
		System.out.println("Context loads: " + container.getJdbcUrl());
	}

}
