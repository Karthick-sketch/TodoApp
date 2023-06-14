package com.karthick.todoapp.controller;

import com.karthick.todoapp.common.APIResponse;
import com.karthick.todoapp.entity.Todo;
import com.karthick.todoapp.service.TodoService;
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

@WebMvcTest(TodosController.class)
public class TodosControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TodoService todoService;

    private Todo getDummyTodo() {
        return new Todo(1, "Learn Unit Testing", 1686355200000L, false, 1);
    }

    @Test
    public void testGetTodosByUserId() throws Exception {
        // create dummy todo
        Todo mockTodo = getDummyTodo();
        List<Todo> todos = new ArrayList<>();
        todos.add(mockTodo);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(todos);

        // mock the dummy todo
        Mockito.when(todoService.findTodosByUserId(mockTodo.getUserId())).thenReturn(apiResponse);

        // verify the api response with mock data
        mockMvc.perform(MockMvcRequestBuilders.get("/todos/{user_id}", mockTodo.getUserId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("Learn Unit Testing"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].dueDate").value(1686355200000L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].status").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].userId").value(1));
    }

    @Test
    public void testCreateNewTodo() throws Exception {
        String requestBody = "{ \"title\" : \"Learn Unit Testing\", \"dueDate\" : 1686663862675, \"userId\" : 1 }";

        Todo mockTodo = getDummyTodo();
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(mockTodo);

        Mockito.when(todoService.createNewTodo(Mockito.any(Todo.class))).thenReturn(apiResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Learn Unit Testing"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.dueDate").value(1686355200000L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(1));
    }

    @Test
    public void testUpdateTodoByFields() throws Exception {
        String requestBody = "{ \"title\" : \"Learn Docker\", \"dueDate\" : 1686700800000, \"status\" : true }";

        Todo mockTodo = new Todo(1, "Learn Docker", 1686700800000L, true, 1);
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(mockTodo);

        Map<String, Object> fields = new HashMap<>();
        fields.put("title", "Learn Docker");
        fields.put("dueDate", 1686700800000L);
        fields.put("status", true);

        Mockito.when(todoService.updateTodoByFields(mockTodo.getId(), fields)).thenReturn(apiResponse);

        mockMvc.perform(MockMvcRequestBuilders.patch("/todo/{id}", mockTodo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Learn Docker"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.dueDate").value(1686700800000L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(1));
    }

    @Test
    public void testDeleteTodoById() throws Exception {
        Mockito.doNothing().when(todoService).deleteTodoById(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/todo/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
