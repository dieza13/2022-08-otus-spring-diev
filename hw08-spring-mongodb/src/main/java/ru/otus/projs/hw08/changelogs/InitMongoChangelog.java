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

@ChangeLog(order = "init-task")
public class InitMongoChangelog {

    private final List<Author> authors = new ArrayList<>();
    private final List<Genre> genres = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();

    @ChangeSet(order = "000", id = "dropDB", author = "diev", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "diev", runAlways = true)
    public void initAuthors(AuthorRepository repository){
        Stream.of(new Author(null, "Михаил","Булгаков"),
                  new Author(null, "Джек","Лондон"),
                  new Author(null, "Иван","Ефремов"))
                .forEach(a -> authors.add(repository.save(a)));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "diev", runAlways = true)
    public void initGenres(GenreRepository repository){
        Stream.of(new Genre(null, "Повесть"),
                  new Genre(null, "Научная фантастика"),
                  new Genre(null, "Рассказ"))
                .forEach(g -> genres.add(repository.save(g)));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "diev", runAlways = true)
    public void initBooks(BookRepository repository){
        Stream.of(new Book(null, "Лезвие бритвы", authors.get(2),genres.get(1)),
                  new Book(null, "Собачье сердце", authors.get(0),genres.get(0)),
                  new Book(null, "Белый клык", authors.get(1),genres.get(0)),
                  new Book(null, "Морфий", authors.get(0),genres.get(2)),
                  new Book(null, "Роковые яйца", authors.get(0),genres.get(0)))
                .forEach(b -> books.add(repository.save(b)));
    }

    @ChangeSet(order = "004", id = "initBookComments", author = "diev", runAlways = true)
    public void initBookComments(BookCommentRepository repository){
        Stream.of(new BookComment(null, "Читал", books.get(0)),
                  new BookComment(null, "Понравилось", books.get(0)),
                  new BookComment(null, "Читал", books.get(1)),
                  new BookComment(null, "Прикольно", books.get(1)),
                  new BookComment(null, "Читал", books.get(2)),
                  new BookComment(null, "Интересно", books.get(2)),
                  new BookComment(null, "Читал", books.get(3)),
                  new BookComment(null, "Хорошо", books.get(3)),
                  new BookComment(null, "Интересно", books.get(3)),
                  new BookComment(null, "Гуд", books.get(4)),
                  new BookComment(null, "Норм", books.get(4)))
                .forEach(repository::save);
    }


}
