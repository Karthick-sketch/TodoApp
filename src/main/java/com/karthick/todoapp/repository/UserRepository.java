package com.karthick.todoapp.repository;

import com.karthick.todoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT id, name FROM users WHERE email = ? AND password = ?;", nativeQuery = true)
    Map<String, String> findByEmailAndPassword(String email, String password);
}
