package com.karthick.todoapp.service;

import com.karthick.todoapp.common.APIResponse;
import com.karthick.todoapp.entity.Todo;
import com.karthick.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoService todoService;

    private Todo getDummyTodo() {
        return new Todo(1, "Learn Unit Testing", 1686355200000L, false, 1);
    }

    @Test
    public void testFindTodosByUserId() {
        int userId = 1;
        List<Todo> mockTodos = new ArrayList<>();
        mockTodos.add(getDummyTodo());

        // Mock the TodoRepository behavior
        Mockito.when(todoRepository.findByUserId(userId)).thenReturn(mockTodos);

        // Call the service method
        APIResponse result = todoService.findTodosByUserId(userId);

        // Assert the result
        assertNotNull(result);
        List<Todo> resultTodos = (List<Todo>) result.getData();
        assertEquals(userId, resultTodos.get(0).getId());
        assertEquals("Learn Unit Testing", resultTodos.get(0).getTitle());
        Mockito.verify(todoRepository, Mockito.times(1)).findByUserId(userId);
    }
}