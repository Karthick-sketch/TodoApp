package com.karthick.todoapp.controller;

import com.karthick.todoapp.common.APIResponse;
import com.karthick.todoapp.entity.User;
import com.karthick.todoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(value = "http://localhost:5173/")
@RestController
public class UsersController {
    @Autowired
    UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<APIResponse> getUserById(@PathVariable int id) {
        APIResponse apiResponse = userService.findUserById(id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PostMapping("/user/signin")
    public ResponseEntity<APIResponse> signIn(@RequestBody Map<String, String> cred) {
        APIResponse apiResponse = userService.signIn(cred);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PostMapping("/user")
    public ResponseEntity<APIResponse> createNewUser(@RequestBody User user) {
        APIResponse apiResponse = userService.createNewUser(user);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<APIResponse> getUserById(@PathVariable int id, @RequestBody Map<String, String> fields) {
        APIResponse apiResponse = userService.updateUserByFields(id, fields);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUserById(@PathVariable int id) {
        userService.deleteUserById(id);
    }
}
