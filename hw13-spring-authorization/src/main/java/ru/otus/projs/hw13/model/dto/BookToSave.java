package ru.otus.projs.hw13.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.projs.hw13.model.Author;
import ru.otus.projs.hw13.model.Book;
import ru.otus.projs.hw13.model.Genre;

@Data
@AllArgsConstructor
public class BookToSave {

    private long id;
    private String name;
    private Author author;
    private Genre genre;

    public static BookToSave toDto(Book book) {
        return new BookToSave(book.getId(), book.getName(),book.getAuthor(),book.getGenre());
    }

    public Book toDomain() {
        return new Book(getId(), getName(),getAuthor(),getGenre());
    }

}
