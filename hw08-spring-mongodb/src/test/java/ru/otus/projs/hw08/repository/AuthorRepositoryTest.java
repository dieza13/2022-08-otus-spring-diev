package ru.otus.projs.hw08.repository;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.projs.hw08.model.Author;

import static org.assertj.core.api.Assertions.assertThat;

@EnableMongock
@DataMongoTest
@DisplayName("Репозиторий по работе с Author")
class AuthorRepositoryTest {

    private static final String WANTED_AUTHOR_ID = "1";

    @Autowired
    private AuthorRepository authorRepo;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void getById_FindAuthorById() {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(WANTED_AUTHOR_ID));
        var author = mongoTemplate.find(query, Author.class);
        var authorFromRepo = authorRepo.findById(WANTED_AUTHOR_ID);
        assertThat(authorFromRepo.get().getId()).isEqualTo(author.get(0).getId());

    }

    @Test
    void save_newAuthor() {

        var saveAuthor = authorRepo.save(new Author("9", "999", "999"));
        assertThat(saveAuthor.getId()).isNotNull();
        var searchedAuthor = authorRepo.findById(saveAuthor.getId());

        assertThat(searchedAuthor.get()).isNotNull()
                .matches(b -> b.getName().equals(saveAuthor.getName()))
                .matches(b -> b.getLastName().equals(saveAuthor.getLastName()));

    }

    @Test
    void delete_Author() {

        Author authorSaved = authorRepo.save(new Author("8", "888", "888"));
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(authorSaved.getId()));
        var author2Del = mongoTemplate.find(query, Author.class);
        assertThat(author2Del).hasSize(1);
        authorRepo.deleteById(author2Del.get(0).getId());
        assertThat(mongoTemplate.find(query, Author.class)).hasSize(0);

    }
}