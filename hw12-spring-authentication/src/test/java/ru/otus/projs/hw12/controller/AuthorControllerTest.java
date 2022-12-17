package ru.otus.projs.hw12.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.otus.projs.hw12.model.Author;
import ru.otus.projs.hw12.model.dto.UserInfo;
import ru.otus.projs.hw12.service.AuthorService;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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

    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    void authorList_return2Authors() throws Exception {

        List<Author> authors = List.of(createAuthor(11L), createAuthor(12L));
        when(authorService.findAll()).thenReturn(authors);

        mvc.perform(get("/api/author"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(authors)));

    }

    @ParameterizedTest
    @MethodSource("provideArgs4GetAuthorById")
    void getAuthorById(Author author, long authorId, ResultMatcher status, UserInfo userInfo) throws Exception {

        when(authorService.getAuthorById(authorId)).thenReturn(author);
        if (userInfo != null) {
            mvc.perform(get("/api/author/" + authorId).with(user(userInfo)).contentType(APPLICATION_JSON))
                    .andExpect(status)
                    .andExpect(content().json(mapper.writeValueAsString(author)));
        } else {
            mvc.perform(get("/api/author/" + authorId).contentType(APPLICATION_JSON))
                    .andExpect(status);
        }
    }

    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    void saveAuthor() throws Exception {
        Author author = createAuthor(1L);
        when(authorService.saveAuthor(any())).thenReturn(author);
        String expectedResult = mapper.writeValueAsString(author);

        mvc.perform(post("/api/author").with(csrf())
                .contentType(APPLICATION_JSON)
                .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(author)));

    }

    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    void deleteAuthor_ok() throws Exception {

        Author author = new Author();
        doNothing().when(authorService).deleteAuthor(anyLong());
        mvc.perform(delete("/api/author/1", author).with(csrf()))
                .andExpect(status().isOk());
        verify(authorService, times(1)).deleteAuthor(1L);

    }

    @WithAnonymousUser
    @Test
    void authorList_return2Authors_errorOnBadUser() throws Exception {

        List<Author> authors = List.of(createAuthor(11L), createAuthor(12L));
        when(authorService.findAll()).thenReturn(authors);

        mvc.perform(get("/api/author"))
                .andExpect(status().isUnauthorized());

    }

    @WithAnonymousUser
    @Test
    void saveAuthor_errorOnBadUser() throws Exception {
        Author author = createAuthor(1L);
        when(authorService.saveAuthor(any())).thenReturn(author);
        String expectedResult = mapper.writeValueAsString(author);

        mvc.perform(post("/api/author").with(csrf())
                .contentType(APPLICATION_JSON)
                .content(expectedResult))
                .andExpect(status().isUnauthorized());

    }
    @WithAnonymousUser
    @Test
    void deleteAuthor_errorOnBadUser() throws Exception {

        Author author = new Author();
        doNothing().when(authorService).deleteAuthor(anyLong());
        mvc.perform(delete("/api/author/1", author).with(csrf()))
                .andExpect(status().isUnauthorized());

    }


    private static Stream<Arguments> provideArgs4GetAuthorById() {
        return Stream.of(
                Arguments.of(createAuthor(11L), 11L, status().isOk(), new UserInfo("fedor", "1")),
                Arguments.of(createAuthor(12L), 12L, status().isUnauthorized(),null)
        );
    }



    private static Author createAuthor(long id) {
        return new Author(id, "name" + id, "lastname" + id);
    }
}