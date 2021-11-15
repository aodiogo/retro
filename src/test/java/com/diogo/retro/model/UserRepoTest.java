package com.diogo.retro.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepoTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsername_HappyPath_ShouldReturn1User() throws
            Exception {
        // Given
        User user = new User();
        user.setUsername("shazin");
        user.setPassword("shaz980");
        user.setRole("USER");
        testEntityManager.persist(user);
        testEntityManager.flush();

        // When
        User actual = userRepository.findByUsername("shazin");

        // Then
        assertThat(actual).isEqualTo(user);
    }

    @Test
    public void save_HappyPath_ShouldSave1User() throws Exception {
        // Given
        User user = new User();
        user.setUsername("shazin");
        user.setPassword("shaz980");
        user.setRole("USER");

        // When
        User actual = userRepository.save(user);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
    }
}