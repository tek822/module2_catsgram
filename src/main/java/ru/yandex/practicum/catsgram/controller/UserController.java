package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<String, User> users = new HashMap<>();

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        String email = user.getEmail();
        if (email == null || email.isBlank()) {
            throw new InvalidEmailException("Не задан email пользователя");
        }
        if (users.containsKey(email)) {
            throw new UserAlreadyExistException("Пользователь с email: " + email + " уже существует");
        }
        users.put(email, user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        String email = user.getEmail();
        if (email == null || email.isBlank()) {
            throw new InvalidEmailException("Не задан email пользователя");
        }
        if (users.containsKey(email)) {
            users.replace(email, user);
        } else {
            users.put(email, user);
        }
        return user;
    }

}
