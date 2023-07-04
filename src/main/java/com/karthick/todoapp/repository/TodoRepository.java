package com.karthick.todoapp.repository;

import com.karthick.todoapp.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByUserId(int userId);

    @Query(value = "SELECT * FROM todos WHERE user_id = ? AND NOT status AND DUE_DATE < ?;", nativeQuery = true)
    List<Todo> fetchOverdueTodos(int userId, long dueDate);

    @Query(value = "SELECT * FROM todos WHERE user_id = ? AND NOT status AND DUE_DATE = ?;", nativeQuery = true)
    List<Todo> fetchDueTodayTodos(int userId, long dueDate);

    @Query(value = "SELECT * FROM todos WHERE user_id = ? AND NOT status AND DUE_DATE > ?;", nativeQuery = true)
    List<Todo> fetchDueLaterTodos(int userId, long dueDate);

    @Query(value = "SELECT * FROM todos WHERE user_id = ? AND status;", nativeQuery = true)
    List<Todo> fetchCompletedTodos(int userId);
}
