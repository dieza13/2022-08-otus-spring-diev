package ru.otus.projs.hw10.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.projs.hw10.model.Genre;
import ru.otus.projs.hw10.service.GenreService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер по работе с Genre")
@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;

    @Test
    void genreList_return2Genres() throws Exception {

        List<Genre> genres = List.of(createGenre(11L), createGenre(12L));
        when(genreService.findAll()).thenReturn(genres);

        mvc.perform(get("/api/genre"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genres)));

    }

    @Test
    void getGenreById() throws Exception {

        Genre genre = createGenre(11L);
        when(genreService.getGenreById(11L)).thenReturn(genre);

        mvc.perform(get("/api/genre/11").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genre)));

    }

    @Test
    void saveGenre() throws Exception {
        Genre genre = createGenre(1L);
        when(genreService.saveGenre(any())).thenReturn(genre);
        String expectedResult = mapper.writeValueAsString(genre);

        mvc.perform(post("/api/genre")
                .contentType(APPLICATION_JSON)
                .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genre)));

    }

    @Test
    void deleteGenre_ok() throws Exception {

        Genre genre = new Genre();
        doNothing().when(genreService).deleteGenre(anyLong());
        mvc.perform(delete("/api/genre/1", genre))
                .andExpect(status().isOk());
        verify(genreService, times(1)).deleteGenre(1L);

    }
    private Genre createGenre(long id) {
        return new Genre(id, "name" + id);
    }
}