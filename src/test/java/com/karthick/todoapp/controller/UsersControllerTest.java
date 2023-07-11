package com.karthick.todoapp.controller;

import com.karthick.todoapp.common.APIResponse;
import com.karthick.todoapp.entity.User;
import com.karthick.todoapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebMvcTest(UsersController.class)
public class UsersControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    private User getDummyUser() {
        return new User(1, "Niko", "niko@rockstargames.com", "gta4");
    }

    @Test
    public void testGetUsersByUserId() throws Exception {
        // create dummy user
        User mockUser = getDummyUser();
        List<User> users = new ArrayList<>();
        users.add(mockUser);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(users);

        // mock the dummy user
        Mockito.when(userService.findUserById(mockUser.getId())).thenReturn(apiResponse);

        // verify the api response with mock data
        mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", mockUser.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value("Niko"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].email").value("niko@rockstargames.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].password").value("gta4"));
    }

    @Test
    public void testCreateNewUser() throws Exception {
        String requestBody = "{ \"name\" : \"Niko\", \"email\" : \"niko@rockstargames.com\", \"password\" : \"gta4\" }";

        User mockUser = getDummyUser();
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(mockUser);

        Mockito.when(userService.createNewUser(Mockito.any(User.class))).thenReturn(apiResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("Niko"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("niko@rockstargames.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.password").value("gta4"));
    }

    @Test
    public void testUpdateUserByFields() throws Exception {
        String requestBody = "{ \"name\" : \"Niko Bellic\", \"email\" : \"niko.bellic@rockstargames.com\", \"password\" : \"gtaiv\" }";

        User mockUser = new User(1, "Niko Bellic", "niko.bellic@rockstargames.com", "gtaiv");
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(mockUser);

        Map<String, String> fields = new HashMap<>();
        fields.put("name", "Niko Bellic");
        fields.put("email", "niko.bellic@rockstargames.com");
        fields.put("password", "gtaiv");

        Mockito.when(userService.updateUserByFields(mockUser.getId(), fields)).thenReturn(apiResponse);

        mockMvc.perform(MockMvcRequestBuilders.patch("/user/{id}", mockUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("Niko Bellic"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("niko.bellic@rockstargames.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.password").value("gtaiv"));
    }

    @Test
    public void testDeleteUserById() throws Exception {
        Mockito.doNothing().when(userService).deleteUserById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
