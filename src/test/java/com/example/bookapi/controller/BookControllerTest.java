package com.example.bookapi.controller;

import com.example.bookapi.DemoApplication;
import com.example.bookapi.dto.BookDTO;
import com.example.bookapi.entity.Book;
import com.example.bookapi.repository.BookRepository;
import com.example.bookapi.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private LogHeaderUsername logHeaderUsername;

    BookDTO sampleBookDTO = new BookDTO(1L, "random", "", 2, null);


    @Test
    void getBookById() throws Exception {
        when(bookService.getBookById(1L))
                .thenReturn(new ResponseEntity<>(sampleBookDTO, HttpStatus.OK));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/books/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value("random"));
    }

    @Test
    void createNewBook() throws Exception {
        when(bookService.saveBook(sampleBookDTO)).thenReturn(new ResponseEntity<>(sampleBookDTO, HttpStatus.CREATED));
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/books")
                        .content(asJsonString(sampleBookDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").exists());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}












