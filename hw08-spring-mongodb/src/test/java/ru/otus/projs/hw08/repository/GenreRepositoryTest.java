package ru.otus.projs.hw08.repository;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.projs.hw08.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@EnableMongock
@DataMongoTest
@DisplayName("Репозиторий по работе с Genre")
class GenreRepositoryTest {

    private static final String WANTED_GENRE_ID = "1";

    @Autowired
    private GenreRepository genreRepo;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void getById_FindGenreById() {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(WANTED_GENRE_ID));
        var genre = mongoTemplate.find(query, Genre.class);
        var genreFromRepo = genreRepo.findById(WANTED_GENRE_ID);
        assertThat(genreFromRepo.get().getId()).isEqualTo(genre.get(0).getId());

    }

    @Test
    void save_newGenre() {

        var saveGenre = genreRepo.save(new Genre("6", "6"));
        assertThat(saveGenre.getId()).isNotNull();
        var searchedGenre = genreRepo.findById(saveGenre.getId());

        assertThat(searchedGenre).isPresent()
                .matches(b -> b.get().getName().equals(saveGenre.getName()));

    }

    @Test
    void delete_Genre() {

        Genre genreSaved = genreRepo.save(new Genre("7", "777"));
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(genreSaved.getId()));
        var genre2Del = mongoTemplate.find(query, Genre.class);
        assertThat(genre2Del).hasSize(1);
        genreRepo.deleteById(genre2Del.get(0).getId());
        assertThat(mongoTemplate.find(query, Genre.class)).hasSize(0);

    }
}