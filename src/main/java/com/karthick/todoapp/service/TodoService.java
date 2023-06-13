package com.karthick.todoapp.service;

import com.karthick.todoapp.common.APIResponse;
import com.karthick.todoapp.entity.Todo;
import com.karthick.todoapp.exception.BadRequestException;
import com.karthick.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public APIResponse findTodosByUserId(int userId) {
        APIResponse apiResponse = new APIResponse();
        List<Todo> todos = todoRepository.findByUserId(userId);
        if (todos.isEmpty()) {
            throw new NoSuchElementException("expecting asset is not found");
        }
        apiResponse.setData(todos);
        return apiResponse;
    }

    public APIResponse createNewTodo(Todo todo) {
        APIResponse apiResponse = new APIResponse();
        try {
            todo.setStatus(false);
            apiResponse.setData(todoRepository.save(todo));
        } catch (AssertionError e) {
            throw new BadRequestException(e.getMessage());
        }
        return apiResponse;
    }

    public APIResponse updateTodoByFields(int id, Map<String, Object> fields) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isEmpty()) {
            throw new NoSuchElementException("expecting todo is not found");
        }
        APIResponse apiResponse = new APIResponse();
        try {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Todo.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, todo.get(), value);
                }
            });
            apiResponse.setData(todoRepository.save(todo.get()));
        } catch (AssertionError e) {
            throw new BadRequestException(e.getMessage());
        }
        return apiResponse;
    }

    public void deleteTodoById(int id) {
        todoRepository.deleteById(id);
    }
}
