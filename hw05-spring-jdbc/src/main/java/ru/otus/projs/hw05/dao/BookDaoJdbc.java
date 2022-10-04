package ru.otus.projs.hw05.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.projs.hw05.exception.dao.BookAuthorNotSetException;
import ru.otus.projs.hw05.exception.dao.BookGenreNotSetException;
import ru.otus.projs.hw05.model.Author;
import ru.otus.projs.hw05.model.Book;
import ru.otus.projs.hw05.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDao authorDao;


    @Override
    public List<Book> findAll() {
        return jdbc.query(
                "select b.id, b.name, b.author_id, b.genre_id, a.name author_name, a.lastname author_lastname, g.name genre_name " +
                      "from book b, author a, genre g " +
                     "where b.genre_id = g.id and b.author_id = a.id" +
                        " order by b.id",
                new BookMapper()
        );
    }

    @Override
    public Book getById(Long id) {
        return jdbc.queryForObject(
                "select b.id, b.name, b.author_id, b.genre_id, a.name author_name, a.lastname author_lastname, g.name genre_name " +
                        "from book b, author a, genre g " +
                        "where b.genre_id = g.id and b.author_id = a.id and b.id = :id",
                Map.of("id", id),
                new BookMapper()
        );
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
        MapSqlParameterSource params = convertBook2Map(book);

        if (book.getId() == null || book.getId() <= 0) {
            return insertBook(book, params, kh);
        } else {
            return updateBook(book, params, kh);
        }
    }

    @Override
    public void delete(Long id) {
        jdbc.update("delete from book where id = :id", Map.of("id", id));
    }

    private Book insertBook(
            Book book,
            MapSqlParameterSource params,
            GeneratedKeyHolder kh
    ) {
        jdbc.update(
                "insert into book(name, author_id, genre_id) values (:name, :author_id, :genre_id)",
                params,
                kh
        );
        return new Book(
                kh.getKey().longValue(),
                book.getName(),
                book.getAuthor(),
                book.getGenre()
        );
    }

    private Book updateBook(
            Book book,
            MapSqlParameterSource params,
            GeneratedKeyHolder kh
    ) {
        jdbc.update("update book set name = :name, author_id = :author_id, genre_id = :genre_id where id = :id", params);
        return book;
    }

    private MapSqlParameterSource convertBook2Map(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("name", book.getName());
        if (book.getAuthor() == null || book.getAuthor().getId() == null) {
            throw new BookAuthorNotSetException();
        }
        params.addValue("author_id", book.getAuthor().getId());
        if (book.getGenre() == null || book.getGenre().getId() == null) {
            throw new BookGenreNotSetException();
        }
        params.addValue( "genre_id", book.getGenre().getId());
        return params;
    }

    private class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            long author_id = rs.getLong("author_id");
            long genre_id = rs.getLong("genre_id");
            String authorName = rs.getString("author_name");
            String authorLastName = rs.getString("author_lastname");
            String genreName = rs.getString("genre_name");
            return new Book(id, name, new Author(author_id, authorName, authorLastName), new Genre(genre_id, genreName));
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
