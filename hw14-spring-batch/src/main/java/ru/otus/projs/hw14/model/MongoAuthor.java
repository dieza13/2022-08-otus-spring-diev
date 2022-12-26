package ru.otus.projs.hw14.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(value = "author")
public class MongoAuthor implements EntityWithStringId{

    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("lastName")
    private String lastName;
    @Transient
    private Long oldId;


    public static MongoAuthor toMongoEntity(Author author) {
        return new MongoAuthor(null, author.getName(), author.getLastName(), author.getId());
    }

}
