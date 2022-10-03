package ru.otus.projs.hw05.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
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
        return jdbc.query("select id, name from genre", new GenreMapper());
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

            jdbc.update(
                    "insert into genre(name) values (:name)",
                    params,
                    kh
            );
            return new Genre(kh.getKey().longValue(), genre.getName());

        } else {
            jdbc.update("update genre set name = :name where id = :id", params);
            return genre;
        }
    }

    @Override
    public void delete(Long id) {
        Map key = Map.of("id", id);
        jdbc.update("update book set genre_id = null where genre_id = :id", key);
        jdbc.update("delete from genre where id = :id", key);
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
