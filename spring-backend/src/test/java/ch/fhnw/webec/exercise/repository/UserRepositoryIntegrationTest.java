package ch.fhnw.webec.exercise.repository;

import ch.fhnw.webec.exercise.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import jakarta.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@DataJpaTest
public class UserRepositoryIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindAll() {
        var users = this.userRepository.findAll();

        assertEquals(3, users.size());
        assertEquals("Test user 1", users.get(0).getUsername());
        assertEquals("Test user 2", users.get(1).getUsername());
        assertEquals("Test user 3", users.get(2).getUsername());
    }

    @Test
    public void testSaveUser() {
        var user = new User("Testuser", "password111", "admin");

        assertEquals(1, this.userRepository.findAll().size());

        var savedUser = this.userRepository.save(user);

        assertEquals(4, this.userRepository.findAll().size());
        assertEquals("Testuser", savedUser.getUsername());
        assertEquals("password1111", savedUser.getPassword());
        assertEquals("admin", savedUser.getRole());
    }

    @Test
    public void testSaveInvalidUser() {
        assertThrows(ConstraintViolationException.class, () -> {
            this.userRepository.save(new User());
        });
    }

    @Test
    public void testUpdateUser() {
        var user = this.userRepository.findById("1").get();

        assertEquals("User 1", user.getUsername());

        user.setUsername("Updated test user 1");
        var savedUser = this.userRepository.save(user);

        assertEquals(3, this.userRepository.findAll().size());
        assertEquals("Updated test user 1", savedUser.getUsername());
    }

    @Test
    public void testDeleteUser() {
        var user = this.userRepository.findById("1").get();

        assertEquals(3, this.userRepository.findAll().size());

        this.userRepository.delete(user);

        assertEquals(2, this.userRepository.findAll().size());
    }

}

