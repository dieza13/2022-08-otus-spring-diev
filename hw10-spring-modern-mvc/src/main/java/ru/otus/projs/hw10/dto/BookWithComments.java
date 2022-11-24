package ru.otus.projs.hw10.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.projs.hw10.model.Book;
import ru.otus.projs.hw10.model.BookComment;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookWithComments {
    private Book book;
    private List<BookComment> comments;
}
