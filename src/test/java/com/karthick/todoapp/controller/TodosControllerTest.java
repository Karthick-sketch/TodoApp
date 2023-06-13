package com.karthick.todoapp.controller;

import com.karthick.todoapp.common.APIResponse;
import com.karthick.todoapp.entity.Todo;
import com.karthick.todoapp.service.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(TodosController.class)
public class TodosControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TodoService todoService;

    @Test
    public void testGetTodosByUserId() throws Exception {
        // create dummy todo
        Todo mockTodo = new Todo(1, "Learn Unit Testing", 1686355200000L, true, 1);

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].status").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].userId").value(1));
    }
}
