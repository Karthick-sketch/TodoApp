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

    public boolean isUserPresent(int id) {
        return userRepository.findById(id).isPresent();
    }

    public APIResponse findUserById(int id) {
        APIResponse apiResponse = new APIResponse();
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchElementException("expecting user is not found");
        }
        apiResponse.setData(user);
        return apiResponse;
    }

    public APIResponse createNewUser(User user) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(userRepository.save(user));
        return apiResponse;
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public APIResponse updateUserByFields(int id, Map<String, Object> fields) {
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
}
