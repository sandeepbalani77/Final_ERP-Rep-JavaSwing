package com.erp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.erp.entity.User;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindById() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("secret");

        userRepository.save(user);

        Optional<User> found = userRepository.findById("admin");
        assertThat(found).isPresent();
        assertThat(found.get().getPassword()).isEqualTo("secret");
    }

    @Test
    void testFindByIdNotFound() {
        Optional<User> found = userRepository.findById("nonexistent");
        assertThat(found).isEmpty();
    }
}
