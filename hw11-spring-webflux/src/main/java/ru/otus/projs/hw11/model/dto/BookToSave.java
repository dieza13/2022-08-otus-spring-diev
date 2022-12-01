package ru.otus.projs.hw11.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.projs.hw11.model.Author;
import ru.otus.projs.hw11.model.Book;
import ru.otus.projs.hw11.model.Genre;

@Data
@AllArgsConstructor
public class BookToSave {

    private String id;
    private String name;
    private String authorId;
    private String genreId;

    public static BookToSave toDto(Book book) {
        return new BookToSave(book.getId(), book.getName(),book.getAuthor().getId(),book.getGenre().getId());
    }

    public Book toDomain(Author author, Genre genre) {
        return new Book(getId(), getName(),author, genre);
    }
}
