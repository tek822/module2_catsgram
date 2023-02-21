package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final Map<String, User> users = new HashMap<>();

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public User add(User user) {
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

    public User update(User user) {
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

    public User findUserByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidEmailException("Не задан email пользователя");
        }
            return users.get(email);
    }
}
