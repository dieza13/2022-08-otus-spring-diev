package ru.otus.projs.hw16.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "book")
@NamedEntityGraph(name = Book.BOOK_AUTHOR_GENRE_GRAPH,
        attributeNodes = {@NamedAttributeNode("author"),@NamedAttributeNode("genre")})
public class Book {

    public static final String BOOK_AUTHOR_GENRE_GRAPH = "book-author_genre-graph";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    public String toString() {
        return "Book(id=" + id + ", name=" + name + ", author=" + author + ", genre=" + genre;
    }


}
