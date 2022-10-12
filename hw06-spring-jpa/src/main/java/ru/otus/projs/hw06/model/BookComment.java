package ru.otus.projs.hw06.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "book_comment")
public class BookComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public String toString() {
        return "Comment(id=" + id + ", comment=" + comment + ")";
    }
}
