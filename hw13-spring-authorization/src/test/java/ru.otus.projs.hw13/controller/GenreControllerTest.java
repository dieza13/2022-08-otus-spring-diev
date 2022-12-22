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
import ru.otus.projs.hw13.model.Genre;
import ru.otus.projs.hw13.service.GenreService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Контроллер по работе с Genre")
@Import(SecurityConfig.class)
@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private UserDetailsService userDetailsService;

    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    void genreList_return2Genres() throws Exception {

        List<Genre> genres = List.of(createGenre(11L), createGenre(12L));
        when(genreService.findAll()).thenReturn(genres);

        mvc.perform(get("/api/genre"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genres)));

    }

    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    @Test
    void getGenreById() throws Exception {

        Genre genre = createGenre(11L);
        when(genreService.getGenreById(11L)).thenReturn(genre);

        mvc.perform(get("/api/genre/11").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genre)));

    }

    @WithMockUser(username = "admin", authorities = "ROLE_MANAGER")
    @Test
    void saveGenre() throws Exception {
        Genre genre = createGenre(1L);
        when(genreService.saveGenre(any())).thenReturn(genre);
        String expectedResult = mapper.writeValueAsString(genre);

        mvc.perform(post("/api/genre").with(csrf())
                .contentType(APPLICATION_JSON)
                .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genre)));

    }

    @WithMockUser(username = "admin", authorities = "ROLE_MANAGER")
    @Test
    void deleteGenre_ok() throws Exception {

        Genre genre = new Genre();
        doNothing().when(genreService).deleteGenre(anyLong());
        mvc.perform(delete("/api/genre/1", genre).with(csrf()))
                .andExpect(status().isOk());
        verify(genreService, times(1)).deleteGenre(1L);

    }

    @WithAnonymousUser
    @Test
    void genreList_errorOnBadUser() throws Exception {

        List<Genre> genres = List.of(createGenre(11L), createGenre(12L));
        when(genreService.findAll()).thenReturn(genres);

        mvc.perform(get("/api/genre"))
                .andExpect(status().isForbidden());

    }

    @WithAnonymousUser
    @Test
    void getGenreById_errorOnBadUser() throws Exception {

        Genre genre = createGenre(11L);
        when(genreService.getGenreById(11L)).thenReturn(genre);

        mvc.perform(get("/api/genre/11").contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @WithAnonymousUser
    @Test
    void saveGenre_errorOnBadUser() throws Exception {
        Genre genre = createGenre(1L);
        when(genreService.saveGenre(any())).thenReturn(genre);
        String expectedResult = mapper.writeValueAsString(genre);

        mvc.perform(post("/api/genre").with(csrf())
                .contentType(APPLICATION_JSON)
                .content(expectedResult))
                .andExpect(status().isForbidden());

    }

    @WithAnonymousUser
    @Test
    void deleteGenre_errorOnBadUser() throws Exception {

        Genre genre = new Genre();
        doNothing().when(genreService).deleteGenre(anyLong());
        mvc.perform(delete("/api/genre/1", genre).with(csrf()))
                .andExpect(status().isForbidden());

    }
    private Genre createGenre(long id) {
        return new Genre(id, "name" + id);
    }
}