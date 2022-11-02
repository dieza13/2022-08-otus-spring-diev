package ru.otus.projs.hw08.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class BookComment {

    @Id
    private String id;
    @Field("comment")
    private String comment;
    @DBRef
    @Field("book")
    private Book book;

}
