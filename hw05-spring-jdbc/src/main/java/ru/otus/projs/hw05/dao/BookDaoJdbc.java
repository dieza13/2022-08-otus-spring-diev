package ru.otus.projs.hw05.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.projs.hw05.model.Author;
import ru.otus.projs.hw05.model.Book;
import ru.otus.projs.hw05.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;


    @Override
    public List<Book> findAll() {
        return jdbc.query("select id, name, author_id, genre_id from book", new BookMapper());
    }

    @Override
    public Book getById(Long id) {
        Book book = jdbc.queryForObject("select id, name, author_id, genre_id from book where id = :id", Map.of("id", id), new BookMapper());
        if (book != null && book.getId() != null) {
            Long aId = Optional.ofNullable(book.getAuthor()).orElse(new Author()).getId();
            Long gId = Optional.ofNullable(book.getGenre()).orElse(new Genre()).getId();
            if (aId != null && aId > 0) {
                book.setAuthor(authorDao.getById(book.getAuthor().getId()));
            }
            if (gId != null && gId > 0) {
                book.setGenre(genreDao.getById(book.getGenre().getId()));
            }
        }
        return book;
    }

    @Override
    public List<Book> getByAuthorId(long id) {
        Author author = authorDao.getById(id);
        return jdbc.query(
                "select b.id, b.name, g.id genre_id, g.name genre_name " +
                      "from book b, genre g " +
                     "where g.id = b.genre_id and b.author_id = :id",
                Map.of("id", id),
                new BookByAuthorMapper(author)
        );
    }

    @Override
    public Book save(Book book) {

        GeneratedKeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("name", book.getName());
        params.addValue(
                "author_id",
                Optional
                        .ofNullable(book.getAuthor())
                        .orElse(new Author(null,null,null)).getId());
        params.addValue(
                "genre_id",
                Optional
                        .ofNullable(book.getGenre()).orElse(new Genre(null,null)).getId());


        if (book.getId() == null || book.getId() <= 0) {

            jdbc.update(
                    "insert into book(name, author_id, genre_id) values (:name, :author_id, :genre_id)",
                    params,
                    kh
            );
            return new Book(kh.getKey().longValue(), book.getName(),book.getAuthor(), book.getGenre());

        } else {
            jdbc.update("update book set name = :name, author_id = :author_id, genre_id = :genre_id where id = :id", params);
            return book;
        }
    }

    @Override
    public void delete(Long id) {
        jdbc.update("delete from book where id = :id", Map.of("id", id));
    }

    private class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            long author_id = rs.getLong("author_id");
            long genre_id = rs.getLong("genre_id");
            return new Book(id, name, new Author(author_id, null, null), new Genre(genre_id, null));
        }
    }

    @RequiredArgsConstructor
    private class BookByAuthorMapper implements RowMapper<Book> {

        private final Author author;

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            Long g_id = rs.getLong("genre_id");
            String g_name = rs.getString("genre_name");
            return new Book(id, name, author, new Genre(g_id, g_name));
        }
    }
}
