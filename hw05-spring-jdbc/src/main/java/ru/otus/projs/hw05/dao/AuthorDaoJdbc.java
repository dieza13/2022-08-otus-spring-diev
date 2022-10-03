package ru.otus.projs.hw05.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.projs.hw05.model.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Author> findAll() {
        return jdbc.query("select id, name, lastname from author", new AuthorMapper());
    }

    @Override
    public Author getById(Long id) {
        return jdbc.queryForObject("select id, name, lastname from author where id = :id", Map.of("id", id), new AuthorMapper());
    }

    @Override
    public Author save(Author author) {

        GeneratedKeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", author.getId());
        params.addValue("name", author.getName());
        params.addValue("lastname", author.getLastName());


        if (author.getId() == null || author.getId() <= 0) {

            jdbc.update(
                    "insert into author(name, lastname) values (:name, :lastname)",
                    params,
                    kh
            );
            return new Author(kh.getKey().longValue(), author.getName(), author.getLastName());

        } else {
            jdbc.update("update author set name = :name, lastname = :lastname where id = :id", params);
            return author;
        }
    }

    @Override
    public void delete(Long id) {
        Map key = Map.of("id", id);
        jdbc.update("update book set author_id = null where author_id = :id", key);
        jdbc.update("delete from author where id = :id", key);
    }

    private class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String lastname = rs.getString("lastname");
            return new Author(id, name, lastname);
        }
    }

}
