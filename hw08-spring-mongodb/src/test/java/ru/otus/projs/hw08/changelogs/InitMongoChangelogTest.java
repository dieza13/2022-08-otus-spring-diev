package ru.otus.projs.hw08.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.projs.hw08.model.Author;
import ru.otus.projs.hw08.model.Book;
import ru.otus.projs.hw08.model.BookComment;
import ru.otus.projs.hw08.model.Genre;
import ru.otus.projs.hw08.repository.AuthorRepository;
import ru.otus.projs.hw08.repository.BookCommentRepository;
import ru.otus.projs.hw08.repository.BookRepository;
import ru.otus.projs.hw08.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@ChangeLog(order = "init-test")
public class InitMongoChangelogTest {

    private List<Author> authors = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();
    private List<Book> books = new ArrayList<>();

    @ChangeSet(order = "001", id = "initAuthorsTest", author = "diev", runAlways = true)
    public void initAuthors(AuthorRepository repository){
        Stream.of(new Author("1", "111","111"),
                  new Author("2", "222","222"),
                  new Author("3", "333","333"))
                .forEach(a -> authors.add(repository.save(a)));
    }

    @ChangeSet(order = "002", id = "initGenresTest", author = "diev", runAlways = true)
    public void initGenres(GenreRepository repository){
        Stream.of(new Genre("1", "1111"),
                  new Genre("2", "2222"),
                  new Genre("3", "3333"))
                .forEach(g -> genres.add(repository.save(g)));
    }

    @ChangeSet(order = "003", id = "initBooksTest", author = "diev", runAlways = true)
    public void initBooks(BookRepository repository){
        Stream.of(new Book("1", "11111", authors.get(2),genres.get(1)),
                  new Book("2", "22222", authors.get(0),genres.get(0)),
                  new Book("3", "33333", authors.get(1),genres.get(0)),
                  new Book("4", "44444", authors.get(0),genres.get(2)),
                  new Book("5", "55555", authors.get(0),genres.get(0)))
                .forEach(b -> books.add(repository.save(b)));
    }

    @ChangeSet(order = "004", id = "initBookCommentsTest", author = "diev", runAlways = true)
    public void initBookComments(BookCommentRepository repository){
        Stream.of(new BookComment("1", "Читал", books.get(0)),
                  new BookComment("2", "Понравилось", books.get(0)),
                  new BookComment("3", "Читал", books.get(1)),
                  new BookComment("4", "Прикольно", books.get(1)),
                  new BookComment("5", "Читал", books.get(2)),
                  new BookComment("6", "Интересно", books.get(2)),
                  new BookComment("7", "Читал", books.get(3)),
                  new BookComment("8", "Хорошо", books.get(3)),
                  new BookComment("9", "Интересно", books.get(3)),
                  new BookComment("10", "Гуд", books.get(4)),
                  new BookComment("11", "Норм", books.get(4)))
                .forEach(repository::save);
    }


}
