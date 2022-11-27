package ru.otus.projs.hw10.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.projs.hw10.model.Author;
import ru.otus.projs.hw10.service.AuthorService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер по работе с Author")
@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorService authorService;

    @Test
    void authorList_return2Authors() throws Exception {

        List<Author> authors = List.of(createAuthor(11L), createAuthor(12L));
        when(authorService.findAll()).thenReturn(authors);

        mvc.perform(get("/api/author"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(authors)));

    }

    @Test
    void getAuthorById() throws Exception {

        Author author = createAuthor(11L);
        when(authorService.getAuthorById(11L)).thenReturn(author);

        mvc.perform(get("/api/author/11").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(author)));

    }

    @Test
    void saveAuthor() throws Exception {
        Author author = createAuthor(1L);
        when(authorService.saveAuthor(any())).thenReturn(author);
        String expectedResult = mapper.writeValueAsString(author);

        mvc.perform(post("/api/author")
                .contentType(APPLICATION_JSON)
                .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(author)));

    }

    @Test
    void deleteAuthor_ok() throws Exception {

        Author author = new Author();
        doNothing().when(authorService).deleteAuthor(anyLong());
        mvc.perform(delete("/api/author/1", author))
                .andExpect(status().isOk());
        verify(authorService, times(1)).deleteAuthor(1L);

    }
    private Author createAuthor(long id) {
        return new Author(id, "name" + id, "lastname" + id);
    }
}