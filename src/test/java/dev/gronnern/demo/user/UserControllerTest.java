package dev.gronnern.demo.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testGetUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                        .with(user("user").password("pass").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUser() throws Exception {
        User user = new User();
        user.setUsername("user");

        when(userRepository.findByUsername("user")).thenReturn(user);

        mockMvc.perform(get("/api/users/user")
                        .with(user("user").password("pass").roles("USER")))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.save(any())).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .with(user("user").password("pass").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }
}

