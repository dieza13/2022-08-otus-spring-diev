package ru.otus.projs.hw13.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.projs.hw13.config.SecurityConfig;
import ru.otus.projs.hw13.model.Author;
import ru.otus.projs.hw13.model.Book;
import ru.otus.projs.hw13.model.dto.BookToSave;
import ru.otus.projs.hw13.model.Genre;
import ru.otus.projs.hw13.service.BookService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер по работе с Book")
@Import(SecurityConfig.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private UserDetailsService userDetailsService;

    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    void bookList_return2Books() throws Exception {

        List<Book> books = List.of(createBook(11L), createBook(12L));
        when(bookService.findAll()).thenReturn(books);

        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(books)));

    }

    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    void getBookById() throws Exception {

        Book book = createBook(11L);
        when(bookService.getBookById(11L)).thenReturn(book);

        mvc.perform(get("/api/book/11").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(book)));

    }

    @WithMockUser(username = "admin", authorities = "ROLE_MANAGER")
    @Test
    void saveBook() throws Exception {
        Book book = createBook(1L);
        when(bookService.saveBook(any())).thenReturn(book);
        String expectedResult = mapper.writeValueAsString(BookToSave.toDto(book));

        mvc.perform(post("/api/book").with(csrf())
                .contentType(APPLICATION_JSON)
                .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(BookToSave.toDto(book))));

    }

    @WithMockUser(username = "admin", authorities = "ROLE_MANAGER")
    @Test
    void deleteBook_ok() throws Exception {

        Book book = new Book();
        doNothing().when(bookService).deleteBook(anyLong());
        mvc.perform(delete("/api/book/1", book).with(csrf()))
                .andExpect(status().isOk());
        verify(bookService, times(1)).deleteBook(1L);

    }

    @WithAnonymousUser
    @Test
    void bookList_errorOnBadUser() throws Exception {

        List<Book> books = List.of(createBook(11L), createBook(12L));
        when(bookService.findAll()).thenReturn(books);

        mvc.perform(get("/api/book"))
                .andExpect(status().isForbidden());

    }

    @WithAnonymousUser
    @Test
    void getBookById_errorOnBadUser() throws Exception {

        Book book = createBook(11L);
        when(bookService.getBookById(11L)).thenReturn(book);

        mvc.perform(get("/api/book/11").contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @WithAnonymousUser
    @Test
    void saveBook_errorOnBadUser() throws Exception {
        Book book = createBook(1L);
        when(bookService.saveBook(any())).thenReturn(book);
        String expectedResult = mapper.writeValueAsString(BookToSave.toDto(book));

        mvc.perform(post("/api/book").with(csrf())
                .contentType(APPLICATION_JSON)
                .content(expectedResult))
                .andExpect(status().isForbidden());

    }

    @WithAnonymousUser
    @Test
    void deleteBook_errorOnBadUser() throws Exception {

        Book book = new Book();
        doNothing().when(bookService).deleteBook(anyLong());
        mvc.perform(delete("/api/book/1", book).with(csrf()))
                .andExpect(status().isForbidden());

    }



    private Book createBook(long id) {
        return new Book(id, "Book" + id,
                new Author(id, "name" + id, "lastname" + id),
                new Genre(id, "Genre"));
    }
}