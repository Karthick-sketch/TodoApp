package com.karthick.todoapp.service;

import com.karthick.todoapp.common.APIResponse;
import com.karthick.todoapp.entity.User;
import com.karthick.todoapp.exception.BadRequestException;
import com.karthick.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public boolean isUserNotPresent(int id) {
        return userRepository.findById(id).isEmpty();
    }

    public APIResponse findUserById(int id) {
        APIResponse apiResponse = new APIResponse();
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchElementException("expecting user is not found");
        }
        apiResponse.setData(user.get());
        return apiResponse;
    }

    public APIResponse createNewUser(User user) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(userRepository.save(user));
        return apiResponse;
    }

    public void deleteUserById(int id) {
        if (isUserNotPresent(id)) {
            throw new NoSuchElementException("expecting user is not found");
        }
        userRepository.deleteById(id);
    }

    public APIResponse updateUserByFields(int id, Map<String, String> fields) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchElementException("expecting user is not found");
        }
        APIResponse apiResponse = new APIResponse();
        try {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(User.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, user.get(), value);
                }
            });
            apiResponse.setData(userRepository.save(user.get()));
        } catch (AssertionError e) {
            throw new BadRequestException(e.getMessage());
        }
        return apiResponse;
    }

    public APIResponse signIn(Map<String, String> cred) {
        String email = cred.get("email");
        String pwd = cred.get("password");
        if (email != null && pwd != null) {
            throw new BadRequestException("Invalid credentials");
        }
        Map<String, String> user = userRepository.findByEmailAndPassword(email, pwd);
        if (user.isEmpty()) {
            throw new BadRequestException("Invalid credentials");
        }

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(user);
        return apiResponse;
    }
}
