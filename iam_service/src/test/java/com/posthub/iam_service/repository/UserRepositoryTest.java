package com.posthub.iam_service.repository;

import com.posthub.iam_service.model.entity.User;
import com.posthub.iam_service.model.enums.RegistrationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

//    @Test
//    public void whenFindByUsername_thenReturnUser() {
//        // given
//        User user = new User();
//        user.setUsername("testuser");
//        user.setPassword("password");
//        user.setEmail("testuser@example.com");
//        user.setRegistrationStatus(RegistrationStatus.ACTIVE);
//        entityManager.persist(user);
//        entityManager.flush();
//
//        // when
//        Optional<User> found = userRepository.findByUsername(user.getUsername());
//
//        // then
//        assertThat(found).isPresent();
//        assertThat(found.get().getUsername()).isEqualTo(user.getUsername());
//    }
}
