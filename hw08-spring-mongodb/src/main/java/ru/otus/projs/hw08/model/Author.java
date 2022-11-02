package ru.otus.projs.hw08.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Author {

    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("lastName")
    private String lastName;

}
