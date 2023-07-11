package com.karthick.todoapp.service;

import com.karthick.todoapp.common.APIResponse;
import com.karthick.todoapp.entity.User;
import com.karthick.todoapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private User getDummyUser() {
        return new User(1, "Ezio", "ezio@email.com", "ac2");
    }

    @Test
    public void testFindUserById() {
        int userID = 1;
        Optional<User> mockUser = Optional.of(getDummyUser());
        Mockito.when(userRepository.findById(userID)).thenReturn(mockUser);

        APIResponse apiResponse = userService.findUserById(userID);
        assertNotNull(apiResponse);

        User user = (User) apiResponse.getData();
        assertEquals(userID, user.getId());
        assertEquals("Ezio", user.getName());
        assertEquals("ezio@email.com", user.getEmail());
        assertEquals("ac2", user.getPassword());

        Mockito.verify(userRepository, Mockito.times(1)).findById(userID);
    }

    @Test
    public void testCreateNewUser() {
        User mockUser1 = getDummyUser();
        User mockUser2 = getDummyUser();

        Mockito.when(userRepository.save(mockUser1)).thenReturn(mockUser2);

        APIResponse apiResponse = userService.createNewUser(mockUser1);
        assertNotNull(apiResponse);

        User user = (User) apiResponse.getData();
        assertEquals(mockUser1.getId(), user.getId());
        assertEquals(mockUser1.getName(), user.getName());
        assertEquals(mockUser1.getEmail(), user.getEmail());
        assertEquals(mockUser1.getPassword(), user.getPassword());

        Mockito.verify(userRepository, Mockito.times(1)).save(mockUser1);
    }

    @Test
    public void testDeleteUserById() {
        int mockUserID = 1;
        Optional<User> mockUser = Optional.of(getDummyUser());
        Mockito.when(userRepository.findById(mockUserID)).thenReturn(mockUser);

        Mockito.doNothing().when(userRepository).deleteById(mockUserID);

        userService.deleteUserById(mockUserID);

        Mockito.verify(userRepository, Mockito.times(1)).findById(mockUserID);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(mockUserID);
    }

    @Test
    public void testUpdateUserByFields() {
        int mockUserId = 1;
        Map<String, String> userFields = new HashMap<>();
        userFields.put("name", "Ezio Auditore");
        userFields.put("email", "ezio.auditore@ac.com");
        userFields.put("password", "ac2br");

        Optional<User> mockUser1 = Optional.of(getDummyUser());
        User mockUser2 = new User(1, "Ezio Auditore", "ezio.auditore@ac.com", "ac2br");

        Mockito.when(userRepository.findById(mockUserId)).thenReturn(mockUser1);

        Mockito.when(userRepository.save(mockUser1.get())).thenReturn(mockUser2);

        APIResponse apiResponse = userService.updateUserByFields(mockUserId, userFields);
        assertNotNull(apiResponse);

        User user = (User) apiResponse.getData();
        assertEquals(user.getId(), mockUserId);
        assertEquals(userFields.get("name"), user.getName());
        assertEquals(userFields.get("email"), user.getEmail());
        assertEquals(userFields.get("password"), user.getPassword());

        Mockito.verify(userRepository, Mockito.times(1)).findById(mockUserId);
        Mockito.verify(userRepository, Mockito.times(1)).save(mockUser1.get());
    }
}
