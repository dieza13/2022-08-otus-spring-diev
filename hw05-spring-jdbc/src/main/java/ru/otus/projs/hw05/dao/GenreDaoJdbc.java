package ru.otus.projs.hw05.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.projs.hw05.model.Author;
import ru.otus.projs.hw05.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Genre> findAll() {
        return jdbc.query("select id, name from genre order by id", new GenreMapper());
    }

    @Override
    public Genre getById(Long id) {
        return jdbc.queryForObject("select id, name from genre where id = :id", Map.of("id", id), new GenreMapper());
    }

    @Override
    public Genre save(Genre genre) {

        GeneratedKeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", genre.getId());
        params.addValue("name", genre.getName());


        if (genre.getId() == null || genre.getId() <= 0) {
            return insertGenre(genre, params, kh);
        } else {
            return updateGenre(genre, params, kh);
        }
    }

    @Override
    public void delete(Long id) {
        Map key = Map.of("id", id);
        jdbc.update("delete from genre where id = :id", key);
    }

    private MapSqlParameterSource convertAuthor2Map(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", author.getId());
        params.addValue("name", author.getName());
        params.addValue("lastname", author.getLastName());
        return params;
    }

    private Genre insertGenre(
            Genre genre,
            MapSqlParameterSource params,
            GeneratedKeyHolder kh
    ) {
        jdbc.update(
                "insert into genre(name) values (:name)",
                params,
                kh
        );
        return new Genre(kh.getKey().longValue(), genre.getName());
    }

    private Genre updateGenre(
            Genre genre,
            MapSqlParameterSource params,
            GeneratedKeyHolder kh
    ) {
        jdbc.update("update genre set name = :name where id = :id", params);
        return genre;
    }

    private class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
    }


}
