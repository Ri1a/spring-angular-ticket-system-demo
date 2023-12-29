package ch.fhnw.webec.exercise.controller;

import ch.fhnw.webec.exercise.model.User;
import ch.fhnw.webec.exercise.repository.UserRepository;
import io.pebbletemplates.boot.autoconfigure.PebbleAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(PebbleAutoConfiguration.class)
@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;


    @Test
    @WithMockUser
    public void testGetAllUsers() throws Exception {
        User user1 = new User("username1", "password1", Set.of("ROLE_ADMIN"));
        User user2 = new User("username2", "password2", Set.of("ROLE_ADMIN"));
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        this.mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("username1")))
            .andExpect(content().string(containsString("username2")));

        verify(userRepository, times(1)).findAll();
    }

    @Test
    @WithMockUser
    public void testGetOneUser() throws Exception {
        UUID userId = UUID.randomUUID();
        User user1 = new User("username1", "password1", Set.of("ROLE_ADMIN"));
        user1.setId(userId.toString());

        when(userRepository.findById(userId.toString())).thenReturn(Optional.of(user1));

        this.mockMvc.perform(get("/users/" + userId))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("username1")));

        verify(userRepository, times(1)).findById(userId.toString());
    }
}
