package com.karthick.todoapp.service;

import com.karthick.todoapp.common.APIResponse;
import com.karthick.todoapp.entity.Todo;
import com.karthick.todoapp.exception.BadRequestException;
import com.karthick.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    @Autowired
    UserService userService;

    public APIResponse findTodosByUserId(int userId) {
        if (userService.isUserNotPresent(userId)) {
            throw new BadRequestException("Not authorized user");
        }
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(todoRepository.findByUserId(userId));
        return apiResponse;
    }

    private long getTodayDateMillis() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date today = dateFormat.parse(dateFormat.format(new Date()));
        return today.getTime();
    }

    public APIResponse findTodosByUserIdAndSectionId(int sectionId, int userId) throws ParseException {
        if (userService.isUserNotPresent(userId)) {
            throw new BadRequestException("Not authorized user");
        }

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(switch (sectionId) {
            case 1 -> todoRepository.fetchDueTodayTodos(userId, getTodayDateMillis());
            case 2 -> todoRepository.fetchOverdueTodos(userId, getTodayDateMillis());
            case 3 -> todoRepository.fetchDueLaterTodos(userId, getTodayDateMillis());
            case 4 -> todoRepository.fetchCompletedTodos(userId);
            default -> throw new BadRequestException("Invalid section id");
        });
        return apiResponse;
    }

    public APIResponse createNewTodo(Todo todo) {
        if (userService.isUserNotPresent(todo.getUserId())) {
            throw new BadRequestException("Not authorized user");
        }
        APIResponse apiResponse = new APIResponse();
        try {
            todo.setStatus(false);
            apiResponse.setData(todoRepository.save(todo));
        } catch (AssertionError e) {
            throw new BadRequestException(e.getMessage());
        }
        return apiResponse;
    }

    public APIResponse updateTodoStatus(int id) {
        Optional<Todo> todoOptional = todoRepository.findById(id);
        if (todoOptional.isEmpty()) {
            throw new NoSuchElementException("todo not found");
        }

        APIResponse apiResponse = new APIResponse();
        Todo todo = todoOptional.get();
        todo.setStatus(!todo.getStatus());
        apiResponse.setData(todoRepository.save(todo));
        return apiResponse;
    }

/* ---- will do later ----
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
*/

    public void deleteTodoById(int id) {
        if (todoRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("todo not found");
        }
        todoRepository.deleteById(id);
    }
}
