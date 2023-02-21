package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;

    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll() {
        return posts;
    }

    public Post create(Post post) {
        String email = post.getAuthor();
        if (email == null || email.isBlank()) {
            throw new InvalidEmailException("Не задан email пользователя");
        }
        if (userService.findUserByEmail(email) == null){
            throw new UserNotFoundException("Пользователь " + email + " не найден");
        }
        posts.add(post);
        return post;
    }
}
