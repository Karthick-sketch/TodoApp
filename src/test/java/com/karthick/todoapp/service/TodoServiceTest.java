package com.karthick.todoapp.service;

import com.karthick.todoapp.common.APIResponse;
import com.karthick.todoapp.entity.Todo;
import com.karthick.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoService todoService;

/*
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;
*/

    private Todo getDummyTodo() {
        return new Todo(1, "Learn Unit Testing", 1686355200000L, false, 1);
    }

    /*
    private Optional<User> getDummyUser() {
        return Optional.of(new User(1, "Ezio", "ezio@email.com", "ac2"));
    }
    */

    /* need to fix
    @Test
    public void testFindTodosByUserId() {
        int userId = 1;
        List<Todo> mockTodos = new ArrayList<>();
        mockTodos.add(getDummyTodo());

        // Mock the TodoRepository behavior
        Mockito.when(todoRepository.findByUserId(userId)).thenReturn(mockTodos);

        Mockito.when(userRepository.findById(userId)).thenReturn(getDummyUser());

        // Call the service method
        APIResponse result = todoService.findTodosByUserId(userId);

        // Assert the result
        assertNotNull(result);
        List<Todo> resultTodos = (List<Todo>) result.getData();
        assertEquals(userId, resultTodos.get(0).getId());
        assertEquals("Learn Unit Testing", resultTodos.get(0).getTitle());
        Mockito.verify(todoRepository, Mockito.times(1)).findByUserId(userId);
    }
    */

    /* need to find UserService cause
    @Test
    public void testCreateNewUser() {
        return;
    }
    */

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

        Todo todo = (Todo) apiResponse.getData();
        assertEquals(mockTodoId, todo.getId());
        assertEquals("Learn Unit Testing", todo.getTitle());
        assertEquals(1686355200000L, todo.getDueDate());
        assertTrue(todo.getStatus());
        assertEquals(1, todo.getUserId());

        Mockito.verify(todoRepository, Mockito.times(1)).findById(mockTodoId);
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
