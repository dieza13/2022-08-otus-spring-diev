package ru.otus.projs.hw06.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

import static ru.otus.projs.hw06.model.Book.BOOK_AUTHOR_GENRE_GRAPH;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "book")
@NamedEntityGraph(name = BOOK_AUTHOR_GENRE_GRAPH,
        attributeNodes = {@NamedAttributeNode("author"),@NamedAttributeNode("genre")})
public class Book {

    public static final String BOOK_AUTHOR_GENRE_GRAPH = "book-author_genre-graph";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    public String toString() {
        return "Book(id=" + id + ", name=" + name + ", author=" + author + ", genre=" + genre;
    }


}
