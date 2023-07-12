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
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;

    @Mock
    UserService userService;

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

        Mockito.when(userService.isUserNotPresent(userId)).thenReturn(false);

        // Call the service method
        APIResponse result = todoService.findTodosByUserId(userId);

        // Assert the result
        assertNotNull(result);
        List<Todo> resultTodos = (List<Todo>) result.getData();
        for (Todo todo : resultTodos) {
            assertEquals(1, todo.getId());
            assertEquals("Learn Unit Testing", todo.getTitle());
            assertEquals(1686355200000L, todo.getDueDate());
            assertFalse(todo.getStatus());
            assertEquals(userId, todo.getUserId());
        }

        Mockito.verify(userService, Mockito.times(1)).isUserNotPresent(userId);
        Mockito.verify(todoRepository, Mockito.times(1)).findByUserId(userId);
    }

    @Test
    public void testCreateNewUser() {
        int userId = 1;
        Mockito.when(userService.isUserNotPresent(userId)).thenReturn(false);

        Todo mockTodo1 = getDummyTodo();
        Todo mockTodo2 = getDummyTodo();
        Mockito.when(todoRepository.save(mockTodo1)).thenReturn(mockTodo2);

        APIResponse apiResponse = todoService.createNewTodo(mockTodo1);
        assertNotNull(apiResponse);

        Todo todo = (Todo) apiResponse.getData();
        assertEquals(1, todo.getId());
        assertEquals("Learn Unit Testing", todo.getTitle());
        assertEquals(1686355200000L, todo.getDueDate());
        assertFalse(todo.getStatus());
        assertEquals(userId, todo.getUserId());

        Mockito.verify(userService, Mockito.times(1)).isUserNotPresent(userId);
        Mockito.verify(todoRepository, Mockito.times(1)).save(mockTodo1);
    }

    @Test
    public void testUpdateTodoStatus() {
        int mockTodoId = 1;
        Optional<Todo> mockTodo1 = Optional.of(getDummyTodo());
        Mockito.when(todoRepository.findById(mockTodoId)).thenReturn(mockTodo1);

        Todo mockTodo2 = getDummyTodo();
        mockTodo2.setStatus(true);
        Mockito.when(todoRepository.save(mockTodo1.get())).thenReturn(mockTodo2);

        APIResponse apiResponse = todoService.updateTodoStatus(mockTodoId);
        assertNotNull(apiResponse);

        Map<String, Boolean> todoStatus = (Map<String, Boolean>) apiResponse.getData();
        assertTrue(todoStatus.get("status"));

        Mockito.verify(todoRepository, Mockito.times(1)).findById(mockTodoId);
        Mockito.verify(todoRepository, Mockito.times(1)).save(mockTodo1.get());
    }

    @Test
    public void testDeleteTodoById() {
        int mockTodoID = 1;
        Optional<Todo> mockTodo = Optional.of(getDummyTodo());
        Mockito.when(todoRepository.findById(mockTodoID)).thenReturn(mockTodo);

        Mockito.doNothing().when(todoRepository).deleteById(mockTodoID);

        todoService.deleteTodoById(mockTodoID);

        Mockito.verify(todoRepository, Mockito.times(1)).findById(mockTodoID);
        Mockito.verify(todoRepository, Mockito.times(1)).deleteById(mockTodoID);
    }
}
