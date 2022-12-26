package ru.otus.projs.hw13.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.projs.hw13.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий по работе с User")
@DataJpaTest
class UserRepositoryTest {

    private static final long WANTED_USER_ID = 1;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TestEntityManager em;


    @Test
    void getByUsername_FindUserByUsername() {

        var user = em.find(User.class, WANTED_USER_ID);
        var userFromRepo = userRepo.findById(WANTED_USER_ID);
        assertThat(userFromRepo).isPresent().get().usingRecursiveComparison().isEqualTo(user);
        assertThat(userFromRepo.get().getRoles().size()).isEqualTo(2);

    }


}