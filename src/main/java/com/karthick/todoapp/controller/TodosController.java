package com.karthick.todoapp.controller;

import com.karthick.todoapp.common.APIResponse;
import com.karthick.todoapp.entity.Todo;
import com.karthick.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TodosController {
    @Autowired
    TodoService todoService;

    @GetMapping("/todos/{userId}")
    public ResponseEntity<APIResponse> getAllTodosByUserId(@PathVariable int userId) {
        APIResponse apiResponse = todoService.findTodosByUserId(userId);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PostMapping("/todo")
    public ResponseEntity<APIResponse> createTodo(@RequestBody Todo todo) {
        APIResponse apiResponse = todoService.createNewTodo(todo);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PatchMapping("/todo/{id}")
    public ResponseEntity<APIResponse> updateTodoStatus(@PathVariable int id, @RequestBody Map<String, Object> fields) {
        APIResponse apiResponse = todoService.updateTodoByFields(id, fields);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @DeleteMapping("/todo/{id}")
    public void deleteTodo(@PathVariable int id) {
        todoService.deleteTodoById(id);
    }
}
