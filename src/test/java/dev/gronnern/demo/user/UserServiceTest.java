package dev.gronnern.demo.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeAll
    public static void setUp(@Autowired UserService userService) {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        userService.saveUser(user);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("testuser2");
        user.setPassword("password");
        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser.getId());
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    public void testGetUsers() {
        List<User> users = userService.getUsers();
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("testuser", users.getFirst().getUsername());
    }

    @Test
    public void testGetUserByUsername() {
        User fetchedUser = userService.getUserByUsername("testuser");
        assertNotNull(fetchedUser);
        assertEquals("testuser", fetchedUser.getUsername());
    }
}
